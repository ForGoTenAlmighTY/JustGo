package com.example.justgo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    String[] items1= {"Malls","Restaurant","Theme park","Cities"};
    String[] items2= {"Kilometers (km)","Miles (mi)"};

    AutoCompleteTextView autoCompleteTextView1,autoCompleteTextView2;
    ArrayAdapter<String> adapter1,adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_settings);
        autoCompleteTextView1= findViewById(R.id.autoComplete1);

        autoCompleteTextView2= findViewById(R.id.autoComplete2);

        adapter1= new ArrayAdapter<String>(this,R.layout.dropdownitem,items1);
        adapter2= new ArrayAdapter<String>(this,R.layout.dropdownitem,items2);

        autoCompleteTextView1.setAdapter(adapter1);

        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item= adapterView.getItemAtPosition(i).toString();
                Toast.makeText(Settings.this, "Item: "+item, Toast.LENGTH_SHORT).show();
            }
        });

       autoCompleteTextView2.setAdapter(adapter2);

      autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item= adapterView.getItemAtPosition(i).toString();
                Toast.makeText(Settings.this, "Item: "+item, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void backDashbord(View view) {
        startActivity(new Intent(getApplicationContext(), HomePage.class));
    }
}