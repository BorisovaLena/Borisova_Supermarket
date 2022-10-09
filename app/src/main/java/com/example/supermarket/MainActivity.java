package com.example.supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

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
    Spinner spinner;
    Context nContext;
    String[] i = {"по возрастанию","по убыванию"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v = findViewById(com.google.android.material.R.id.ghost_view);
        GetTextFormSql(v);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, i);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner=findViewById(R.id.spiner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0)
                {
                    sortTitle();
                }
                else
                {
                    sortTitle2();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
         });
    }

    public void sortTitle()
    {
        String str = "SELECT * FROM Products ORDER BY Title";
        sort(str);
    }
    public void sortTitle2()
    {
        String str = "SELECT * FROM Products ORDER BY Title DESC";
        sort(str);
    }

    public void sort(String str)
    {
        data = new ArrayList<Product>();
        listView = findViewById(R.id.BD_Product);
        pAdapter = new Adapter(MainActivity.this, data);

       try
       {
           ConnectionHelper connectionHelper = new ConnectionHelper();
           connection = connectionHelper.connectionClass();
           if (connection != null)
           {
               String s = str;
               Statement statement = connection.createStatement();
               ResultSet resultSet = statement.executeQuery(s);
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
       catch (SQLException throwables)
       {
           throwables.printStackTrace();
       }
        enterMobile();

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