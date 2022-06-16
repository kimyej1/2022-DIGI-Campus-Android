package com.kbstar.i04movie;

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

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    // person adapter에 있는 view holder를 arraylist로 관리하려고

    private ArrayList<Movie> items = new ArrayList<Movie>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // person_item.xml 파일로 inflate (person이 필요한 만큼 뷰홀더 생성 -> 10명 정보 있으면 뷰홀더 10개 -> 메모리 자리 10개 필요! : 틀만 만드는 역할)

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Binding : holder에 resource 할당

        Movie item = items.get(position);  // arrayList에 10개 들어있는데, 그중에 3번째꺼 불러줘! 이렇게 포지션정보 주면서 값 가져오기
        holder.setItem(item);               // 가져와서 뷰홀더에 값 셋팅
    }

    @Override
    public int getItemCount() {
        return items.size();                // 갯수를 이거를 통해(참조해서) 세는듯함,, 이게 0이면 item이 많아도 안보여줌 -> 무조건 overriding!
    }

    // 내부에서 쓸건 아니고, Main 등 외부에서 데이터 쉽게 추가하려고 만들어둠(API)
    public void addItem(Movie movie) {
        items.add(movie);
    }

    public void alterItem(int idx, Movie movie) {
        items.set(idx, movie);
    }

    public void removeItem(int idx) {
        items.remove(idx);
    }

    public Movie getItem(int idx) {
        return items.get(idx);
    }

    // 한번에 리스트 형태를 만들어서 넣기
    public void setItems(ArrayList<Movie> items){
        this.items = items;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
        }

        public void setItem(Movie item) {
            textView.setText(item.getTitle());
            textView2.setText(item.getAudiCnt());
        }
    }
}