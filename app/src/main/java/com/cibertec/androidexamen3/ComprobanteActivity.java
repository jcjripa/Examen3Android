package com.cibertec.androidexamen3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cibertec.androidexamen3.entidad.Comprobante;
import com.cibertec.androidexamen3.servicio.ServicioRest;
import com.cibertec.androidexamen3.util.ConnectionRest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComprobanteActivity extends AppCompatActivity {

    ServicioRest servicio;
    EditText edtId, edtFechaPago,edtPedido,edtCliente;
    Spinner spnUsuario;
    Button btnRegistrar;
    TextView txtId;
    final String metodo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprobante);

        setTitle("Comprobante");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtId = (TextView) findViewById(R.id.txtIdComprobante);
        edtId = (EditText) findViewById(R.id.edtIdComprobante);
        edtFechaPago = (EditText) findViewById(R.id.edtComprobanteFP);
        edtPedido = (EditText) findViewById(R.id.edtComprobantePedido);
        edtCliente = (EditText) findViewById(R.id.edtComprobanteCliente);
        spnUsuario = (Spinner) findViewById(R.id.spnComprobanteUsuario);
        btnRegistrar = (Button) findViewById(R.id.btnComprobanteRegistrar);

        servicio = ConnectionRest.getConnection().create(ServicioRest.class);
        Bundle extras = getIntent().getExtras();
        final String metodo = extras.getString("var_metodo");
        final String var_id = extras.getString("var_id");

        if (metodo.equals("VER")) {
            String var_fechapago = extras.getString("var_fechapago");
            String var_idpedido = extras.getString("var_idpedido");
            String var_idcliente = extras.getString("var_idcliente");
            String var_idusuario = extras.getString("var_idusuario");

            edtId.setText(var_id);
            edtFechaPago.setText(var_fechapago);
            edtPedido.setText(var_idpedido);
            edtCliente.setText(var_idcliente);
            selectValue(spnUsuario, var_idusuario);
            edtId.setFocusable(false);
        }else if (metodo.equals("REGISTRAR")) {
            txtId.setVisibility(View.INVISIBLE);
            edtId.setVisibility(View.INVISIBLE);
        }

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Comprobante c = new Comprobante();
                c.setFechaPago(edtFechaPago.getText().toString());
                c.setIdPedido(Integer.parseInt(edtPedido.getText().toString()));
                c.setIdCliente(Integer.parseInt(edtCliente.getText().toString()));
                c.setIdUsuario(Integer.parseInt(spnUsuario.getSelectedItem().toString()));

                if (metodo.equals("VER")) {
                    c.setIdComprobante(Integer.parseInt(var_id));
                } else if (metodo.equals("REGISTRAR")) {
                    mensaje("Se pulsó agregar");
                    add(c);
                }

                Intent intent = new Intent(ComprobanteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void add(Comprobante c) {
        Call<Comprobante> call = servicio.registraComprobante(c);
        call.enqueue(new Callback<Comprobante>() {
            @Override
            public void onResponse(Call<Comprobante> call, Response<Comprobante> response) {
                if (response.isSuccessful()) {
                    mensaje("Registro exitoso");
                }
            }
            @Override
            public void onFailure(Call<Comprobante> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void mensaje(String msg) {
        Toast toast1 = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast1.show();
    }

    private void selectValue(Spinner spinner, Object value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

}
