package com.example.supermarket;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class Adapter extends BaseAdapter{
    private Context mContext;

    public Adapter(Context mContext, List<Product> prodList) {
        this.mContext = mContext;
        this.prodList = prodList;
    }

    List<Product> prodList;

    @Override
    public int getCount() {
        return prodList.size();
    }

    @Override
    public Object getItem(int i) {
        return prodList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return prodList.get(i).getID();
    }

    private Bitmap getUserImage(String encodedImg) {
        if (encodedImg != null && !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else
            return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_prod, null);

        TextView Title = v.findViewById(R.id.tv_Title);
        TextView Count = v.findViewById(R.id.tv_Count);
        ImageView Image = v.findViewById(R.id.Img);

        Product prod = prodList.get(position);
        Title.setText(prod.getTitle());
        Count.setText(Integer.toString(prod.getCount()));

        Image.setImageBitmap(getUserImage(prod.getImage()));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,update_delete_prod.class);
                intent.putExtra("Products",prod);
                mContext.startActivity(intent);
            }
        });
        return v;

    }

}