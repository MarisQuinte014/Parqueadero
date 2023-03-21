package com.example.parqueadero;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TiqueteActivity extends AppCompatActivity {

    EditText jetnumeroTiquete, jetfecha, jetplaca;

    TextView jtvtiquetePos, jtvmensualidad; //confirmar si mensualidad si va aca (por ser primaria de otra tabla)

    CheckBox jcbactivo;


    String codigo, fecha, placa, activo;

    long respuesta;

    byte sw;




    ClsOpenHelper admin=new ClsOpenHelper(this,"TblTiquete.db",null,1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiquete);

        //ocultar la barra de titulo por defecto y asociar objetos Xml y Java
        getSupportActionBar().hide();

        // Ocultar la barra de titulo por defecto y asociar objetos Xml y Java entre la logica (java) y la parte grafica (xml)

        jetnumeroTiquete=findViewById(R.id.etnumeroTiquete);
        jetfecha=findViewById(R.id.etfecha);
        jetplaca=findViewById(R.id.etplaca);
        jtvtiquetePos=findViewById(R.id.tvtiquetePos);
        jtvmensualidad=findViewById(R.id.tvmensualidad);
        jcbactivo=findViewById(R.id.cbactivo);
        sw=0;

    }
        //Metodo guardar
        public void Guardar(View view){

        //Definir un contenedor para llevar la informacion a la base de datos
        ContentValues registro=new ContentValues();

        codigo=jetnumeroTiquete.getText().toString();
        fecha=jetfecha.getText().toString();
        placa=jetplaca.getText().toString();
        jtvtiquetePos.getText().toString();

        // aca van las condiciones para guardar

        //Llenar el contenedor

        registro.put("codigo", codigo);
        registro.put("fecha",fecha);
        registro.put("placa",placa);
        registro.put("activo",activo);
        //registro.put("mensualidad",valor_mensualidad);


        //Abrir conexion a la base de datos
            SQLiteDatabase db = admin.getWritableDatabase();

            if (sw == 0)
            respuesta=db.insert("Tbltiquete",null,registro);
            else{
                sw=0;
                respuesta=db.update("TblTiquete",registro,"codigo='"+codigo+"'",null);
            }
            if (respuesta > 0){
                Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
                Limpiar_campos();
            }else{
                Toast.makeText(this, "Error guardando registro", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
        //Fin llenar contenedor

    public void Anular(View view){
        if (sw == 0){
            Toast.makeText(this, "Para anular debe primero buscar", Toast.LENGTH_SHORT).show();
            jetnumeroTiquete.requestFocus();
        }else{
            SQLiteDatabase db=admin.getWritableDatabase();
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


        //Metogo regresar

        public void Regresar(View view){
            Intent regresar = new Intent(this, MainActivity.class);
            startActivity(regresar);

        }

        //Metodo limpiar

        public void Limpiar (View view) {
        Limpiar_campos();}
        public void Limpiar_campos(){
            jetnumeroTiquete.setText("");
            jetfecha.setText("");
            jetplaca.setText("");
            jtvtiquetePos.setText("");
            // definir como este campo de mensualidad
            jcbactivo.setChecked(false);

        }

}