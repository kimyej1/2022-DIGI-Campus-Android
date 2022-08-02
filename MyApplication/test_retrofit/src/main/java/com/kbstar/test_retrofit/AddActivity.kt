package com.kbstar.test_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.kbstar.test_retrofit.databinding.ActivityAddBinding
import com.kbstar.test_retrofit.dto.Board
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AddActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddBinding
    lateinit var board: Board
    var mode="add"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Activity : 어디선가 꼭 intent를 발생시켜야 실행된다.
        // 자신을 실행시킨 intent를 획득하고, 그곳에 extra 데이터가 있는지를 판단한다.
        intent.getParcelableExtra<Board>("dto")?.let {  // let으로 넘어왔다는건 not null = 뭔가 있다는 것!
            board = it
            mode = "update"
            // main에서 전달한 데이터 화면에 찍어준다
            binding.titleView.setText(it.subject)
            binding.contentView.setText(it.content)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_save) {
            if(binding.titleView.text.isNotEmpty() && binding.contentView.text.isNotEmpty()) {
                val boardService = (applicationContext as MyApplication).boardService
                if(mode == "add") {
                    // 신규 글 입력
                    board = Board(
                        0,
                        binding.titleView.text.toString(),
                        binding.contentView.text.toString(),
                        "kimyeji",
                        Date(),
                        0
                    )
                    val call = boardService.addBoard(board)
                    call.enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            val result = response.body()!!
                            if(result == "success") {
                                // add 성공 -> 화면을 메인으로 전환하면서, 유저 입력값을 넘겨서 메인에 항목 추가되게
                                intent.putExtra("result", board)    // main에서 result로 받고있음
                                setResult(RESULT_OK, intent)
                                // 자신을 종료시켜서 시스템에 의해 자동으로 이전화면으로 돌아가게
                                finish()
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            t.printStackTrace()
                            call.cancel()
                        }
                    })
                } else {
                    // update..
                    board.subject = binding.titleView.text.toString()
                    board.content = binding.contentView.text.toString()

                    val call = boardService.updateBoard(board)
                    call.enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            val result = response.body()!!
                            if(result == "success") {
                                // update 성공 -> 화면을 메인으로 전환하면서, 유저 입력값을 넘겨서 메인에서 처리하도록
                                intent.putExtra("result", board)    // main에서 result로 받고있음
                                setResult(RESULT_OK, intent)
                                // 자신을 종료시켜서 시스템에 의해 자동으로 이전화면으로 돌아가게
                                finish()
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            t.printStackTrace()
                            call.cancel()
                        }
                    })
                }
            } else {
                // 입력 없이 save 눌렀을 때
                Toast.makeText(this, "require data...", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}