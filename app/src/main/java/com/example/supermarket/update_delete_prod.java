package com.example.supermarket;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import android.graphics.BitmapFactory;
import java.sql.Connection;
import java.sql.Statement;

public class update_delete_prod extends AppCompatActivity {

    Connection connection;
    View v;
    EditText Title;
    EditText Count;
    ImageView Image;
    Product prod;
    String img=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_prod);

        Title = findViewById(R.id.et_Title_upd);
        Count = findViewById(R.id.et_Count_upd);
        Image = findViewById(R.id.Image_upd);
        prod = getIntent().getParcelableExtra("Products");
        Title.setText(prod.getTitle());
        Count.setText(Integer.toString(prod.getCount()));
        Image.setImageBitmap(getImgBitmap(prod.getImage()));
        v = findViewById(com.google.android.material.R.id.ghost_view);
    }

    public void onClickImage(View view)
    {
        try {
            Intent intentChooser = new Intent();
            intentChooser.setType("image/*");
            intentChooser.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intentChooser, 1);
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Ошибка", Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int request, int result, @Nullable Intent data) {
        try {
            super.onActivityResult(request, result, data);
            if (request == 1 && data != null && data.getData() != null) {
                if (result == RESULT_OK) {
                    Log.d("MyLog", "Image URI : " + data.getData());
                    Image.setImageURI(data.getData());
                    Bitmap bitmap = ((BitmapDrawable) Image.getDrawable()).getBitmap();
                    toString(bitmap);
                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Ошибка", Toast.LENGTH_LONG).show();
        }
    }
    public String toString(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            img = Base64.getEncoder().encodeToString(b);
            return img;
        }
        return "";
    }

    private Bitmap getImgBitmap(String encodedImg) {
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] b = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                b = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return BitmapFactory.decodeResource(update_delete_prod.this.getResources(),
                R.drawable.icon);
    }

    public void onClickUpdate(View v)
    {
        try
        {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
            String str="";
            if (connection != null)
            {
                str = "UPDATE Products Set Title = '"+Title.getText() +"', Count = '"+Count.getText() + "', Image = '"+img+ "' WHERE ID = "+prod.getID()+"";
                Statement statement = connection.createStatement();
                Toast.makeText(this, "Успешное изменение данных!", Toast.LENGTH_LONG).show();
                statement.executeUpdate(str);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
        catch (Exception ex)
        {
            Log.e("Error", ex.getMessage());
        }
    }

    public void onClickDel(View v)
    {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
            if (connection != null)
            {
                String str = "DELETE FROM Products WHERE ID = "+prod.getID()+"";
                Statement statement = connection.createStatement();
                Toast.makeText(this, "Успешное удаление записи!", Toast.LENGTH_LONG).show();
                statement.executeUpdate(str);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
        catch (Exception ex)
        {
            Log.e("Error", ex.getMessage());
        }
    }

    public void onClickBack(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}