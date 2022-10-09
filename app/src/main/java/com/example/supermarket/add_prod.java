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

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Base64;

import android.widget.TextView;
import android.widget.Toast;

public class add_prod extends AppCompatActivity {

    EditText Title, Count;
    TextView status;
    ImageView Image;
    Connection connection;
    String ConnectionResult = "";
    String img=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prod);

        Title = findViewById(R.id.et_Title);
        Count = findViewById(R.id.et_Count);
        Image = findViewById(R.id.Image);
    }
    @Override
    protected void onActivityResult(int request, int result, @Nullable Intent data) {
        try {
            super.onActivityResult(request, result, data);
            if (request == 1 && data != null && data.getData() != null) {
                if (result == RESULT_OK) {
                    Log.d("MyLog", "Image URI : " + data.getData());

                    Image.setImageURI(data.getData());
                    Bitmap bitmap = ((BitmapDrawable) Image.getDrawable()).getBitmap();
                    encodeImg(bitmap);

                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(add_prod.this,"Ошибка", Toast.LENGTH_LONG).show();
        }
    }

    public String encodeImg(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            img = Base64.getEncoder().encodeToString(b);
            return img;
        }
        return "";
    }

    public void onClickAdd(View v) {


        try
        {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();

            String str="";

            if (connection != null)
            {
                str = "INSERT INTO Products (Title, Count, Image) VALUES ('"+Title.getText() +"','"+Count.getText() + "','"+ img+"')";
                Statement statement = connection.createStatement();
                Toast.makeText(this, "Успешное добавление записи!", Toast.LENGTH_LONG).show();
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
}