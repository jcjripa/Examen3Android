package com.cibertec.androidexamen3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cibertec.androidexamen3.adaptador.ComprobanteAdapter;
import com.cibertec.androidexamen3.entidad.Comprobante;
import com.cibertec.androidexamen3.servicio.ServicioRest;
import com.cibertec.androidexamen3.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    ListView listView;
    ComprobanteAdapter adaptadorListView;
    ServicioRest servicio;
    List<Comprobante> list = new ArrayList<Comprobante>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Comprobante");

        btnAdd = (Button) findViewById(R.id.btnAdd);

        // Al adaptador se le pasa la data y el diseño
        listView = (ListView) findViewById(R.id.listView);

        // Se crea la conexion al servicio
        servicio = ConnectionRest.getConnection().create(ServicioRest.class);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensaje("Se pulsó el agregar");
                Intent intent = new Intent(MainActivity.this, ComprobanteActivity.class);
                intent.putExtra("var_metodo", "REGISTRAR");
                startActivity(intent);
            }
        });

        //Para cargar los datos al inicio
        listData();
    }


    public void listData(){
        Call<List<Comprobante>> call = servicio.listaComprobante();
        call.enqueue(new Callback<List<Comprobante>>() {
            @Override
            public void onResponse(Call<List<Comprobante>> call, Response<List<Comprobante>> response) {
                if(response.isSuccessful()){
                    //Aqui es donde obtiene la data y se coloca en el list
                    mensaje("Listado exitoso");
                    list = response.body();
                    adaptadorListView = new ComprobanteAdapter(MainActivity.this, R.layout.activity_list, list);
                    listView.setAdapter(adaptadorListView);
                }
            }

            @Override
            public void onFailure(Call<List<Comprobante>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    void mensaje(String msg){
        Toast toast1 =  Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG);
        toast1.show();
    }
}
