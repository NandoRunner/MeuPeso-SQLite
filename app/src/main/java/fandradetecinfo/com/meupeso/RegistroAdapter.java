package fandradetecinfo.com.meupeso;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fandradetecinfo.com.meupeso.Models.BalancaDigital;

/**
 * Created by Fernando on 14/07/2017.
 */

public class RegistroAdapter extends BaseAdapter {

    private final List<BalancaDigital> registros;
    private final Activity activity;

    public RegistroAdapter(List<BalancaDigital> registros, Activity activity) {
        this.registros = registros;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return this.registros.size();
    }

    @Override
    public Object getItem(int position) {
        return this.registros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.registros.get(position).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View linha = convertView;

        BalancaDigital registro = registros.get(position);

        if (linha == null)
        {
            linha = this.activity.getLayoutInflater().inflate(R.layout.cell_frag_01, parent, false);
        }

        TextView data = (TextView) linha.findViewById(R.id.txtRegData);
        TextView id_usuario = (TextView) linha.findViewById(R.id.txtRegUsuario);
        TextView peso = (TextView) linha.findViewById(R.id.txtRegPeso);

        if (position % 2 == 0)
        {
            linha.setBackgroundColor(activity.getResources().getColor(R.color.actionbar_fg_color));
        }
        else
        {
            linha.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        data.setText(String.valueOf(registro.getData_registro()));
        id_usuario.setText(registro.getId_usuario());
        peso.setText(registro.getPeso());
        return linha;
    }

}
