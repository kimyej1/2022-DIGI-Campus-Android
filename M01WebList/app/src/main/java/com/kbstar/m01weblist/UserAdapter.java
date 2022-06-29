package com.kbstar.m01weblist;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.DataViewHolder> {

    private ArrayList<UserData> userList = null;
    private Activity context = null;

    public UserAdapter(Activity context, ArrayList<UserData> list) {
        this.context = context;
        this.userList = list;
    }

    class DataViewHolder extends RecyclerView.ViewHolder {

        // Variables
        private TextView tv_idx, tv_name, tv_id, tv_level;

        // Inner Class
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tv_idx = itemView.findViewById(R.id.tv_idx);
            this.tv_id = itemView.findViewById(R.id.tv_id);
            this.tv_name = itemView.findViewById(R.id.tv_name);
            this.tv_level = itemView.findViewById(R.id.tv_level);
        }
    }

    // Implement Methods
    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);

        DataViewHolder viewHolder = new DataViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.tv_idx.setText( userList.get(position).getUserIdx() );
        holder.tv_id.setText( userList.get(position).getUserId() );
        holder.tv_name.setText( userList.get(position).getUserName() );
        holder.tv_level.setText( userList.get(position).getUserLevel() );

        String printLevel = "";
        if(userList.get(position).getUserLevel().equals("1"))
            printLevel = "Member";
        else if(userList.get(position).getUserLevel().equals("2"))
            printLevel = "Admin";
        else
            printLevel = "etc";

        holder.tv_level.setText(printLevel);

        // name 눌렀을 때 이벤트 발생
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("XXXXX", "Click idx = " + holder.tv_idx.getText().toString());

                Intent intent = new Intent(context, InfoActivity.class);
                intent.putExtra("idx", holder.tv_idx.getText().toString());
                context.startActivity(intent);
            }
        });
    }

//    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//
//                @Override
//                public void onActivityResult(ActivityResult result) {
//
//                }
//            }
//    );

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
