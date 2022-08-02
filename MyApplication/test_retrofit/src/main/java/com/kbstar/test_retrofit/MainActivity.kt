package com.kbstar.test_retrofit

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kbstar.test_retrofit.databinding.ActivityMainBinding
import com.kbstar.test_retrofit.dto.Board
import com.kbstar.test_retrofit.recyclerview.MyAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    // 다른 액티비티를 실행하기 위해서는 intent를 시스템에 발생시켜야 한다

    // 단순히 다른 액티비티가 실행만 되면 되고, 되돌아왔을 떄 특별히 처리할 일이 없다 -> startActivity
    // 되돌아올 때 사후처리가 있다 -> startActivityForResult() -> intent 발생 => deprecated..
    lateinit var addLauncher: ActivityResultLauncher<Intent>
    var boardList = mutableListOf<Board>()  // 서버 데이터
    var adapter = MyAdapter(this, boardList)
    var mode="add"  // 순수 개발자 알고리즘에서 필요해서.. add를 실행시키는 경우가 'add'와 'update' 구분

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        val dividerDecoration = DividerItemDecoration(
            this, LinearLayoutManager(this).orientation
        )
        dividerDecoration.setDrawable(resources.getDrawable(R.drawable.divider))
        binding.recyclerView.addItemDecoration(dividerDecoration)

        // 목록 데이터를 서버에서 가져와야 함
        val boardService = (applicationContext as MyApplication).boardService
        val call = boardService.listBoard()
        call.enqueue(object : Callback<MutableList<Board>>{
            override fun onResponse(
                call: Call<MutableList<Board>>,
                response: Response<MutableList<Board>>
            ) {
                boardList.addAll(response.body() ?: mutableListOf())
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<MutableList<Board>>, t: Throwable) {
                t.printStackTrace()
                call.cancel()
            }
        })

        // AddActivity에서 되돌아올때..
        addLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()    // 요청 처리자.. intent 발생자
        ){
            // 되돌아왔을 때 callback
            if(mode == "add") {
                // AddActivity에서 넘긴 데이터 받아서..
                it.data?.getParcelableExtra<Board>("result")?.let {
                    boardList.add(0, it)    // 제일 위에 추가할거야 (index = 0)
                    adapter.notifyDataSetChanged()
                }
            } else {
                // update...
                // AddActivity에서 넘긴 데이터 받아서..
                it.data?.getParcelableExtra<Board>("result")?.apply {
                    boardList.forEachIndexed{ index, board ->
                        if(idx == board.idx) {
                            boardList.set(index, this)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            }
        }
        binding.floatingActionButton.setOnClickListener{
            mode = "add"
            // AddActivity 실행..
            val intent = Intent(this, AddActivity::class.java)
            addLauncher.launch(intent)
        }

        adapter.updateLiveData.observe(this) {
            // livedata에 데이터를 postValue() 하는 순간 실행
            mode = "update"
            // AddActivity 실행..
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra("dto", it)
            addLauncher.launch(intent)
        }

        adapter.deleteLiveData.observe(this) {
            // 서버 연동
            val call = boardService.deleteBoard(it)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    // 성공 시
                    val result = response.body()!!
                    if(result == "success"){
                        // 화면 갱신.. 항목 삭제
                        boardList.remove(it)
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    // 실패 시
                    t.printStackTrace()
                    call.cancel()
                }
            })
        }
    }
}