package com.example.supermarket;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import android.content.Intent;
import android.util.Log;

public class add_product extends AppCompatActivity {

    Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
    }

    public void onClickAdd(View v) {
        EditText Title = findViewById(R.id.et_Title);
        EditText Count = findViewById(R.id.et_Count);
        ImageView Image = findViewById(R.id.Image);

        ConnectionHelper connectionHelper = new ConnectionHelper();
        connection = connectionHelper.connectionClass();

        String str="";

        try
        {
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
                statement.executeUpdate(str);
            }
        }
        catch (Exception ex)
        {
            Log.e("Error", ex.getMessage());
        }
    }
}
