package com.kbstar.myapplication.recycler;


import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;


import com.kbstar.myapplication.databinding.ItemBagBinding;
import com.kbstar.myapplication.helper.DBHelper;
import com.kbstar.myapplication.product.BagActivity;
import com.kbstar.myapplication.vo.CartVO;
import com.kbstar.myapplication.vo.ProductVO;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

class BagViewHolder extends RecyclerView.ViewHolder {

    ItemBagBinding binding;

    BagViewHolder(ItemBagBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
public class BagAdapter extends RecyclerView.Adapter<BagViewHolder> {

    private ArrayList<CartVO> items;
    private Context context;

    Bitmap bitmap;
    String imageUrl = "http://10.10.223.89:7777/resources/images/";

    DecimalFormat myFormatter = new DecimalFormat("###,###");

    public MutableLiveData<CartVO> bagDeleteLiveData = new MutableLiveData<>();

    public BagAdapter(Context context, ArrayList<CartVO> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public BagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BagViewHolder(ItemBagBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BagViewHolder holder, int position) {

        CartVO cart = items.get(position);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(imageUrl + cart.getImageId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

        try{
            thread.join();

            holder.binding.bagName.setText(cart.getName());
            holder.binding.bagPrice.setText(myFormatter.format(cart.getPrice()) + " 원 / 개");
            holder.binding.bagCnt.setText(cart.getCnt() + " 개");
            holder.binding.bagImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // click event
        holder.binding.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = new DBHelper(context, "testdb", null, 1).getWritableDatabase();
                String sql = "DELETE FROM myCart WHERE item='" + cart.getName() + "';";
                db.execSQL(sql);

                bagDeleteLiveData.postValue(cart);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
