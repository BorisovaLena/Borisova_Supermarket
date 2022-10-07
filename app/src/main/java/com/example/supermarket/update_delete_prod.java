package com.example.supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;

public class update_delete_prod extends AppCompatActivity {

    Connection connection;
    View v;
    EditText Title;
    EditText Count;
    ImageView Image;
    Product prod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_prod);

        Title = findViewById(R.id.et_Title_upd);
        Count = findViewById(R.id.et_Count_upd);
        Image = findViewById(R.id.Image_upd);
        v = findViewById(com.google.android.material.R.id.ghost_view);
        prod = getIntent().getParcelableExtra("Products");
        Title.setText(prod.getTitle());
        Count.setText(prod.getCount());
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
                if(Image==null)
                {
                    str = "UPDATE Products Set Title = '"+Title.getText() +"', Count = '"+Count.getText() + "' WHERE ID = "+prod.getID()+"";
                }
                else
                {
                    str = "UPDATE Products Set Title = '"+Title.getText() +"', Count = '"+Count.getText() + "', Image = '"+Image+ "' WHERE ID = "+prod.getID()+"";
                }
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
}