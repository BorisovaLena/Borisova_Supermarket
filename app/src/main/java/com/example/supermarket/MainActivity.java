package com.example.supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Connection connection;
    List<Product> data;
    View v;
    ListView listView;
    Adapter pAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v = findViewById(com.google.android.material.R.id.ghost_view);
        GetTextFormSql(v);
    }

    public void enterMobile() {
        pAdapter.notifyDataSetInvalidated();
        listView.setAdapter(pAdapter);
    }
    
    public void GetTextFormSql(View v) {
        data = new ArrayList<Product>();
        listView = findViewById(R.id.BD_Product);
        pAdapter = new Adapter(MainActivity.this, data);
        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();

            if (connection != null) {
                String query = "Select * From Products";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next())
                {
                    Product tempProd = new Product(
                            resultSet.getInt("ID"),
                            resultSet.getString("Title"),
                            Integer.parseInt(resultSet.getString("Count")),
                            resultSet.getString("Image")
                    );
                    data.add(tempProd);
                    pAdapter.notifyDataSetInvalidated();
                }
                connection.close();
            }
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
                }
        enterMobile();
    }
    public void onClick_btnAdd(View v)
    {
        Intent intent = new Intent(this, add_prod.class);
        startActivity(intent);
    }
}