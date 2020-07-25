package com.cibertec.androidexamen3.adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cibertec.androidexamen3.ComprobanteActivity;
import com.cibertec.androidexamen3.R;
import com.cibertec.androidexamen3.entidad.Comprobante;

import java.util.List;

public class ComprobanteAdapter extends ArrayAdapter<Comprobante> {

    private Context context;
    private List<Comprobante> comprobantes;

    public ComprobanteAdapter(Context context, int resource, List<Comprobante> comprobantes) {
        super(context, resource, comprobantes);
        this.context = context;
        this.comprobantes = comprobantes;
    }

    @Override
    public View getView(final int pos, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_list, parent, false);


        TextView txtId = (TextView) rowView.findViewById(R.id.txtListViewID);
        TextView txtFechaPago = (TextView) rowView.findViewById(R.id.txtListViewName);

        txtId.setText(String.format("#ID: %d", comprobantes.get(pos).getIdComprobante()));
        txtFechaPago.setText(String.format("FECHA PAGO: %s", comprobantes.get(pos).getFechaPago()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ComprobanteActivity.class);
                intent.putExtra("var_id", String.valueOf(comprobantes.get(pos).getIdComprobante()));
                intent.putExtra("var_fechapago", comprobantes.get(pos).getFechaPago());
                intent.putExtra("var_idpedido", String.valueOf(comprobantes.get(pos).getIdPedido()));
                intent.putExtra("var_idcliente", String.valueOf(comprobantes.get(pos).getIdCliente()));
                intent.putExtra("var_idusuario", String.valueOf(comprobantes.get(pos).getIdUsuario()));
                intent.putExtra("var_metodo", "VER");
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
