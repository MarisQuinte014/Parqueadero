package com.example.parqueadero;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TiqueteActivity extends AppCompatActivity {

    EditText jetnumeroTiquete, jetfecha, jetplacatiquete;
    TextView jtvtiquetePos, jtvmensualidad, jtvmodelotiquete;
    Switch jcbactivo;
    String codigo, fecha, placa;
    long respuesta;
    byte sw;
    ClsOpenHelper tiquete = new ClsOpenHelper(this,"TblTiquete.db",null,1);
    ClsOpenHelper admint = new ClsOpenHelper(this,"TblVehiculo.db",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiquete);

        //ocultar la barra de titulo por defecto y asociar objetos Xml y Java
        getSupportActionBar().hide();

        // Ocultar la barra de titulo por defecto y asociar objetos Xml y Java entre la logica (java) y la parte grafica (xml)
        jetnumeroTiquete=findViewById(R.id.etnumeroTiquete);
        jetfecha=findViewById(R.id.etfecha);
        jetplacatiquete=findViewById(R.id.etplaca);
        jtvtiquetePos=findViewById(R.id.tvtiquetePos);
        jtvmensualidad=findViewById(R.id.tvmensualidad);
        jcbactivo=findViewById(R.id.cbactivo);
        jtvmodelotiquete = findViewById(R.id.tvmodelotiquetes);
        sw=0;
    }
        public void Guardar(View view){

        codigo = jetnumeroTiquete.getText().toString();
        fecha  = jetfecha.getText().toString();
        placa  = jetplacatiquete.getText().toString();

        ContentValues registryEtiquette = new ContentValues();

            registryEtiquette.put("codigo",codigo);
            registryEtiquette.put("fecha",fecha);
            registryEtiquette.put("placa",placa);

            SQLiteDatabase db = admint.getWritableDatabase();
            SQLiteDatabase ticket = tiquete.getWritableDatabase();
            Cursor filav = db.rawQuery("select * from TblVehiculo where placa = '" + placa + "'",null);

            if(filav.moveToNext()){
                if(filav.getString(4).equals("Si")) {
                    respuesta = ticket.insert("TblTiquete",null,registryEtiquette);

                    if (respuesta > 0){
                        Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
                        Limpiar_campos();
                    }else{
                        Toast.makeText(this, "Error guardando registro", Toast.LENGTH_SHORT).show();
                    }
                    //db.close();
                }else{
                    Toast.makeText(this, "No podemos agregar ticket porque el vehiculo no esta activo", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "No podemos agregar ticket porque el vehiculo no existe", Toast.LENGTH_SHORT).show();
            }
        }

        public void Anular(View view){
        if (sw == 0){
            Toast.makeText(this, "Para anular debe primero buscar", Toast.LENGTH_SHORT).show();
            jetnumeroTiquete.requestFocus();
        }else{
            SQLiteDatabase db = tiquete.getWritableDatabase();
            ContentValues registro=new ContentValues();
            registro.put("activo","No");
            respuesta=db.update("TblTiquete",registro,"codigo='"+codigo+"'",null);
            if (respuesta > 0){
                Toast.makeText(this, "Registro anulado", Toast.LENGTH_SHORT).show();
                Limpiar_campos();
            }else{
                Toast.makeText(this, "Error anulando registro", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

        public void Regresar(View view){
            Intent regresar = new Intent(this, MainActivity.class);
            startActivity(regresar);
        }

        public void Consultar(View view){
            codigo = jetnumeroTiquete.getText().toString();

            if(codigo.isEmpty()){
                Toast.makeText(this, "Código requerido para consultar", Toast.LENGTH_SHORT).show();
                jetnumeroTiquete.requestFocus();
            }else{
                SQLiteDatabase db = tiquete.getReadableDatabase();
                Cursor consultCodio = db.rawQuery("select * from TblTiquete where codigo = '" + codigo + "'", null);
                //db.close();

                if(consultCodio.moveToNext()){
                    sw = 1;
                    jetnumeroTiquete.setText(consultCodio.getString(0));
                    jetfecha.setText(consultCodio.getString(1));
                    jetplacatiquete.setText(consultCodio.getString(2));
                    if(consultCodio.getString(3).equals("Si")){
                        jcbactivo.setChecked(true);
                    }else{
                        jcbactivo.setChecked(false);
                    }
                    placa = jetplacatiquete.getText().toString();
                    SQLiteDatabase dbd = admint.getReadableDatabase();
                    Cursor filat = dbd.rawQuery("select modelo,marca from TblVehiculo where placa = '" + placa + "' ", null);
                    if(filat.moveToNext()){
                        jtvmensualidad.setText(filat.getString(0));
                        jtvmodelotiquete.setText(filat.getString(1));
                    }
                }else{
                    Toast.makeText(this, "Código no registrado", Toast.LENGTH_SHORT).show();
                }
            }
        }


        public void ConsultarPlaca(View view){
            placa = jetplacatiquete.getText().toString();

            if(placa.isEmpty()){
                Toast.makeText(this, "Placa requerido para consultar", Toast.LENGTH_SHORT).show();
                jetplacatiquete.requestFocus();
            }else{
                SQLiteDatabase db = admint.getReadableDatabase();
                Cursor consultVehi = db.rawQuery("select modelo,marca from TblVehiculo where placa = '" + placa + "'", null);

                if(consultVehi.moveToNext()){
                    sw = 1;
                    jtvmodelotiquete.setText(consultVehi.getString(0));
                    jtvmensualidad.setText(consultVehi.getString(1));


                    SQLiteDatabase dbd = tiquete.getReadableDatabase();
                    Cursor filat = dbd.rawQuery("select codigo,fecha,activo from TblTiquete where placa = '" + placa + "' ", null);
                    if(filat.moveToNext()){
                        jetnumeroTiquete.setText(filat.getString(0));
                        jetfecha.setText(filat.getString(1));

                        if(filat.getString(2).equals("Si")){
                            jcbactivo.setChecked(true);
                        }else {
                            jcbactivo.setChecked(false);
                        }
                    }
                }else{
                    Toast.makeText(this, "Placa no registrada", Toast.LENGTH_SHORT).show();
                }
            }
        }

        public void LimpiarTiquete (View view) {
            Limpiar_campos();
        }

        public void Limpiar_campos(){
            jetnumeroTiquete.setText("");
            jetfecha.setText("");
            jetplacatiquete.setText("");
            jcbactivo.setChecked(false);
        }

}