package com.kbstar.test3_androidx

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kbstar.test3_androidx.databinding.FragmentOneBinding
import com.kbstar.test3_androidx.databinding.ItemRecyclerviewBinding

// 항목의 뷰를 가지는 역할 (ViewHolder)
class MyViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

// 항목을 구성해주는 역할 (Adapter)
class MyAdapter(val datas: MutableList<String>): RecyclerView.Adapter<MyViewHolder>() {

    // 항목 갯수를 판단하기 위해서 호출
    override fun getItemCount(): Int {
        return datas.size
    }

    // 항목을 구성하기 위한 viewHolder을 준비하기 위해 자동으로 호출
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // 항목 하나당 한번씩 호출되는.. (항목 구성, 데이터 출력, 이벤트 등록 등..)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.itemData.text = datas[position] // layout > item_recyclerview.xml 의 id = item_data
    }
}

class MyDecoration(val context: Context): RecyclerView.ItemDecoration() {
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        // 뷰의 크기 획득
        val width = parent.width
        val height = parent.height

        // drawing 하기 위한 이미지 사이즈 획득
        val dr = ResourcesCompat.getDrawable(context.resources, R.drawable.kbo, null)
        val drWidth = dr?.intrinsicWidth
        val drHeight = dr?.intrinsicHeight

        // 중앙에 이미지를 그리기 위한 좌표 계산
        val left = width/2 - drWidth?.div(2) as Int
        val top = height/2 - drHeight?.div(2) as Int

        // image drawing
        c.drawBitmap(
            BitmapFactory.decodeResource(context.resources, R.drawable.kbo),
            left.toFloat(),
            top.toFloat(),
            null
        )
    }

    // 각 항목을 꾸미기 위해서 호출
    override fun getItemOffsets(
        outRect: Rect,  // 항목이 출력되는 사각형 정보
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        // 항목의 Index값 획득
        val index = parent.getChildAdapterPosition(view) +1 // 계산 편의성을 위해 1부터 시작..(원래 인덱스는 0부터!)
        if(index % 3 == 0) {    // 세개씩 묶여있는듯하게
            outRect.set(10,10,10,60)
        } else {
            outRect.set(10,10,10,0)
        }

        view.setBackgroundColor(Color.parseColor("#28A0FF"))
        ViewCompat.setElevation(view, 20.0f)
    }
}

class OneFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOneBinding.inflate(inflater, container, false)

        // 리사이클러뷰 데이터 준비
        val datas = mutableListOf<String>()
        for(i in 1..9) {
            datas.add("Item $i")
        }

        // 리사이클러뷰에 구성요소 등록
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = MyAdapter(datas)
        binding.recyclerView.addItemDecoration(MyDecoration(activity as Context))
        return binding.root
    }
}