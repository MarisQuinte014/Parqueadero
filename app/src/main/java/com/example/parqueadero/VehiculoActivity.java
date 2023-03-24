package com.example.parqueadero;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class VehiculoActivity extends AppCompatActivity {

    RadioButton jcbmoto, jcbcarro;
    CheckBox jcbactivo;
    EditText jetplaca, jetmarca, jetmodelo;
    TextView jtvvalormensualidad;
    String placa, modelo, marca, tipo_vehiculo, activo;
    int valor_mensualidad;
    long respuesta;
    byte sw;
    ClsOpenHelper admin = new ClsOpenHelper(this,"TblVehiculo.db",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);
        //ocultar la barra de titulo por defecto y asociar objetos Xml y Java
        getSupportActionBar().hide();
        jcbmoto = findViewById(R.id.cbmoto);
        jcbcarro = findViewById(R.id.cbcarro);
        jcbactivo = findViewById(R.id.cbactivo);
        jetplaca = findViewById(R.id.tvplaca);
        jetmarca = findViewById(R.id.tvmarca);
        jetmodelo = findViewById(R.id.tvmodelo);
        jtvvalormensualidad = findViewById(R.id.tvvalormensualidad);
        sw = 0;
        activo = "Si";
    }

    public void GuardarVehiculo(View view){
        placa = jetplaca.getText().toString();
        modelo = jetmodelo.getText().toString();
        marca = jetmarca.getText().toString();

        if(placa.isEmpty() || modelo.isEmpty() || marca.isEmpty()){
            Toast.makeText(this, "La placa, el modelo y la marca son obligatorios para guardar", Toast.LENGTH_SHORT).show();
            jetplaca.requestFocus();
        }
        else {
            if(jcbcarro.isChecked()){
                jtvvalormensualidad.setText("45000");
                tipo_vehiculo = "Carro";
                valor_mensualidad = 45000;
            }else{
                jtvvalormensualidad.setText("100000");
                tipo_vehiculo = "Moto";
                valor_mensualidad = 100000;
            }
        }

        //Definir un contenedor para llevar la informacion a la base datsos
        ContentValues registro = new ContentValues();

        //Llenar el contenedor
        registro.put("placa", placa);
        registro.put("tipo",tipo_vehiculo);
        registro.put("marca",marca);
        registro.put("modelo",modelo);
        registro.put("mensualidad",valor_mensualidad);
        registro.put("activo",activo);


        //Abrir conexion a la base de datos
        SQLiteDatabase db = admin.getWritableDatabase();

        if(sw == 0){
            respuesta = db.insert("TblVehiculo",null,registro);
        }else{
            sw = 0;
            respuesta = db.update("TblVehiculo",registro, "placa = '" + placa + "'",null);
        }

        if(respuesta > 0){
            Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
            Limpiar_campos();
        }else{
            Toast.makeText(this, "Error guardando registro", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void ConsultarVehiculo(View view){
        placa = jetplaca.getText().toString();
        if(placa.isEmpty()){
            Toast.makeText(this, "Placa requerida para consultar", Toast.LENGTH_SHORT).show();
            jetplaca.requestFocus();
        }else{
            SQLiteDatabase db = admin.getReadableDatabase();
            Cursor fila = db.rawQuery("select * from TblVehiculo where placa = '" + placa + "'", null);
            //db.close();

            if(fila.moveToNext()){
                sw = 1;
                jetmodelo.setText(fila.getString(3));
                jetmarca.setText(fila.getString(2));
                jtvvalormensualidad.setText(fila.getString(5));

                if(fila.getString(1).equals("Carro")){
                    jcbcarro.setChecked(true);
                }else {
                    jcbmoto.setChecked(true);
                }
            }else{
                Toast.makeText(this, "Placa no registrada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void LimpiarVehiculo(View view){
        Limpiar_campos();
    }

    public void AnularVehiculo(View view){
        if(sw == 0){
            Toast.makeText(this, "Para anular debes consular", Toast.LENGTH_SHORT).show();
            jetplaca.requestFocus();
        }
        else{
            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("activo","No");
            respuesta = db.update("TblVehiculo", registro,"placa = '" + placa + "'",null);
            if(respuesta > 0){
                Toast.makeText(this, "Registro anulado", Toast.LENGTH_SHORT).show();
                Limpiar_campos();
            }else{
                Toast.makeText(this, "Error anulando", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

    public void Limpiar_campos(){
        jetplaca.setText("");
        jetmodelo.setText("");
        jetmarca.setText("");
        jtvvalormensualidad.setText("50000");
        jetplaca.requestFocus();
        sw = 0;
    }
}