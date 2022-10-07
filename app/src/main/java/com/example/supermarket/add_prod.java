package com.example.supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.sql.Connection;
import java.sql.Statement;
import android.widget.Toast;



public class add_prod extends AppCompatActivity {

    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prod);
    }
    public void onClickAdd(View v) {
        EditText Title = findViewById(R.id.et_Title);
        EditText Count = findViewById(R.id.et_Count);
        ImageView Image = findViewById(R.id.Image);

        try
        {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();

            String str="";

            if (connection != null)
            {
                if(Image==null)
                {
                    str = "INSERT INTO Products (Title, Count) VALUES ('"+Title.getText() +"','"+Count.getText() + "')";
                }
                else
                {
                    str = "INSERT INTO Products (Title, Count, Image) VALUES ('"+Title.getText() +"','"+Count.getText() + "','"+ Image+"')";
                }
                Statement statement = connection.createStatement();
                Toast.makeText(this, "Успешное добавление!", Toast.LENGTH_LONG).show();
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