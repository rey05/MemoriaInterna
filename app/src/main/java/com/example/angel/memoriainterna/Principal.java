package com.example.angel.memoriainterna;

import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Principal extends ActionBarActivity implements OnClickListener {

    //Declaramos los atributos que se van a utilizar
    private EditText txtTexto;
    private Button btnGuardar, btnAbrir;
    private static final int READ_BLOCK_SIZE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        //Aqui enlazamos los elementos graficos con el codigo
        txtTexto = (EditText) findViewById(R.id.txtArchivo);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnAbrir = (Button) findViewById(R.id.btnAbrir);
        btnGuardar.setOnClickListener(this);
        btnAbrir.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v.equals(btnGuardar)) {

            String str = txtTexto.getText().toString();

            //Clase que permite grabar texto en un archivo

            FileOutputStream fout=null;
            try {

                //Metodo que escribe y abre un archivo con un nombre especifico
                //la constante MODE_WORLD_READABLE indica que este archivo lo puede
                //leer cualquier aplicacion

                fout = openFileOutput("archivoTexto.text", MODE_WORLD_READABLE);

                //CONVIERTE UN STREAM DE CARACTERES EN UN STREAM DE BYTES

                OutputStreamWriter ows = new OutputStreamWriter(fout);

                ows.write(str);//Escribe en el buffer y la cadena de texto

                ows.flush();//devuelve lo que hay en el buffer al archivo

                ows.close();//Cierra el archivo de texto


            } catch (IOException e) {

                //TODO Auto-generated catch block
                e.printStackTrace();
            }
            Toast.makeText(getBaseContext(), "El archivo se ha almacenado!!!", Toast.LENGTH_SHORT).show();
            txtTexto.setText("");
        }

        if (v.equals(btnAbrir)) {

            try{
            //Se lee el archivo dle texto indicado

            FileInputStream fin = openFileInput("archivoTexto.txt");
            InputStreamReader isr = new InputStreamReader(fin);

            char[] inputBuffer = new char[READ_BLOCK_SIZE     ];
            String str="";

            //se lee el archivo de texto mientras no se llega al final de el

            int charRead;
            while ((charRead=isr.read(inputBuffer))>0){

                //Se lee por bloques de 100 caracteres
                //ya que se desconoce el tamaño de texto
                //y se va copiando a una cadena de texto

                String strRead = String.copyValueOf(inputBuffer, 0, charRead);
                str += strRead;

                inputBuffer = new char[READ_BLOCK_SIZE];
            }

            //Se muestra el texto leido en la caja de texto
            txtTexto.setText(str);

            isr.close();

           Toast.makeText(getBaseContext(), "El archivo ha sido cargado", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {

            //TODO: handle exception

            e.printStackTrace();

        }
        }

    }
}
