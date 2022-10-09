package com.example.supermarket;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.InputStream;
import java.util.List;

public class Adapter extends BaseAdapter{

    protected Context Context;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener=listener;
    }

    public Adapter(Context Context, List<Product> prodList) {
        this.Context = Context;
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

    public static Bitmap loadContactPhoto(ContentResolver cr, long id, Context context) {
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
        if (input == null) {
            Resources res = context.getResources();
            return BitmapFactory.decodeResource(res, R.drawable.icon);
        }
        return BitmapFactory.decodeStream(input);
    }

    public Bitmap getUserImage(String encodedImg)
    {
        if(encodedImg!=null && !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
            return BitmapFactory.decodeResource(Context.getResources(), R.drawable.icon);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View v = View.inflate(Context,R.layout.item_prod,null);

        TextView Title = v.findViewById(R.id.tv_Title);
        TextView Count = v.findViewById(R.id.tv_Count);
        ImageView Image = v.findViewById(R.id.Img);

        Product prod = prodList.get(position);
        Title.setText(prod.getTitle());
        Count.setText(Integer.toString(prod.getCount()));

       Image.setImageBitmap(getUserImage(prod.getImage()));

       v.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(Context,update_delete_prod.class);
               intent.putExtra("Products",prod);
               Context.startActivity(intent);
           }
       });
        return v;
    }
}