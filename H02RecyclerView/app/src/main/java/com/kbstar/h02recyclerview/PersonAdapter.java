package com.kbstar.h02recyclerview;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/*
    Adapter : textView, imageView 등을 제어하는 역할
        adapter class : interface 중에 필요하는 기능만 쓰는거..
*/

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder>{
    // person adapter에 있는 view holder를 arraylist로 관리하려고

    private ArrayList<Person> items = new ArrayList<Person>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // person_item.xml 파일로 inflate (person이 필요한 만큼 뷰홀더 생성 -> 10명 정보 있으면 뷰홀더 10개 -> 메모리 자리 10개 필요! : 틀만 만드는 역할)

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.person_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Binding : holder에 resource 할당

        Person item = items.get(position);  // arrayList에 10개 들어있는데, 그중에 3번째꺼 불러줘! 이렇게 포지션정보 주면서 값 가져오기
        holder.setItem(item);               // 가져와서 뷰홀더에 값 셋팅
    }

    @Override
    public int getItemCount() {
        return items.size();                // 갯수를 이거를 통해(참조해서) 세는듯함,, 이게 0이면 item이 많아도 안보여줌 -> 무조건 overriding!
    }

    public void addItem(Person person) {    // person adapter 내부에서 쓸건 아니고, main에서 데이터 쉽게 추가하려고 만들어둠
        items.add(person);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;      // name
        TextView textView2;     // mobile

        public ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체 속에 있는 이름/전화번호 객체를 참조
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
        }

        public void setItem(Person item) {
            textView.setText(item.getName());
            textView2.setText(item.getMobile());
        }
    }
}