package com.kbstar.myapplication.recycler;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kbstar.myapplication.databinding.ItemProductBinding;
import com.kbstar.myapplication.product.ItemActivity;
import com.kbstar.myapplication.vo.ProductVO;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

class ProductViewHolder extends RecyclerView.ViewHolder {
    ItemProductBinding binding;

    ProductViewHolder(ItemProductBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }
}

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private ArrayList<ProductVO> items;
    private Context context;
    private String userName;

    Bitmap bitmap;
    String imageUrl = "http://10.10.223.89:7777/resources/images/";

    DecimalFormat myFormatter = new DecimalFormat("###,###");

    public ProductAdapter(Context context, ArrayList<ProductVO> items, String userName){
        this.context = context;
        this.items = items;
        this.userName = userName;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ProductViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        ProductVO product = items.get(position);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(imageUrl + product.getImageId());
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

            holder.binding.itemDesc.setText(product.getDescription());
            holder.binding.itemName.setText(product.getName());
            holder.binding.itemPrice.setText(myFormatter.format(product.getPrice()) + " 원 / 개");
            holder.binding.itemImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // click event
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ItemActivity.class);
                intent.putExtra("product", product);
                intent.putExtra("userName", userName);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}