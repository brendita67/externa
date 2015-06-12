package com.example.brendaitzel.externa;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends ActionBarActivity implements  View.OnClickListener{
    private EditText txtTexto;
    private Button btnGuardar, btnAbrir;
    private static final int READ_BLOCK_SIZE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTexto = (EditText) findViewById(R.id.txtArchivo);
        btnGuardar = (Button)findViewById(R.id.btnGuardar);
        btnAbrir = (Button)findViewById(R.id.btnAbrir);
        btnGuardar.setOnClickListener(this);
        btnAbrir.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        File sdCard, directory, file = null;

        try {

            if (Environment.getExternalStorageState().equals("mounted")) {


                sdCard = Environment.getExternalStorageDirectory();

                if (v.equals(btnGuardar)) {
                    String str = txtTexto.getText().toString();

                    FileOutputStream fout = null;
                    try {
                        directory = new File(sdCard.getAbsolutePath() + "/Mis archivos");

                        directory.mkdirs();

                        file = new File(directory, "brenda.txt");

                        fout = new FileOutputStream(file);


                        OutputStreamWriter ows = new OutputStreamWriter(fout);
                        ows.write(str);
                        ows.flush();
                        ows.close();

                        Toast.makeText(getBaseContext(), "El archivo creado en la memoria externa ", Toast.LENGTH_SHORT).show();

                        txtTexto.setText("");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                if (v.equals(btnAbrir)) {
                    try {

                        directory = new File(sdCard.getAbsolutePath() + "/Mis archivos");

                        file = new File(directory, "brenda.txt");


                        FileInputStream fin = new FileInputStream(file);


                        InputStreamReader isr = new InputStreamReader(fin);

                        char[] inputBuffer = new char[READ_BLOCK_SIZE];
                        String str = "";


                        int charRead;
                        while ((charRead = isr.read(inputBuffer)) > 0) {

                            String strRead = String.copyValueOf(inputBuffer, 0, charRead);
                            str += strRead;

                            inputBuffer = new char[READ_BLOCK_SIZE];
                        }
                        txtTexto.setText(str);

                        isr.close();

                        Toast.makeText(getBaseContext(), "El archivo ha sido cargado", Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }else{
                Toast.makeText(getBaseContext(), "El almacenamineto externo no se encuentra disponible", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {

        }
    }
}
