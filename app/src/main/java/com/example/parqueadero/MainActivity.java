package com.example.parqueadero;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Tiquete(View view){
        Intent tiquete = new Intent(this, TiqueteActivity.class);
        startActivity(tiquete);}


    public void Vehiculo(View view){
        Intent vehiculo = new Intent(this, VehiculoActivity.class);
        startActivity(vehiculo);
    }
}