package com.example.parqueadero;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class TiqueteActivity extends AppCompatActivity {

    EditText jetnumeroTiquete, jetfecha, jetplaca;

    TextView jtvtiquetePos, jtvmensualidad; //confirmar si mensualidad si va aca (por ser primaria de otra tabla)

    CheckBox jcbactivo;

    // por la imagen debo colocar algo aca?

    String codigo, fecha, placa, activo;

    ClsOpenHelper admin=new ClsOpenHelper(this,"Parqueadero.db",null,1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiquete);

        //ocultar la barra de titulo por defecto y asociar objetos Xml y Java
        getSupportActionBar().hide();

        // Ocultar la barra de titulo por defecto y asociar objetos Xml y Javaon entre la logica (java) y la parte grafica (xml)

        jetnumeroTiquete=findViewById(R.id.etnumeroTiquete);
        jetfecha=findViewById(R.id.etfecha);
        jetplaca=findViewById(R.id.etplaca);
        jtvtiquetePos=findViewById(R.id.tvtiquetePos);
        jtvmensualidad=findViewById(R.id.tvmensualidad);
        jcbactivo=findViewById(R.id.cbactivo);

    }
        //Metogo regresar

        public void Regresar(View view){
            Intent regresar = new Intent(this, MainActivity.class);
            startActivity(regresar);

        }


}