package fandradetecinfo.com.meupeso;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fandradetecinfo.com.meupeso.Models.Usuario;

/**
 * Created by Fernando on 22/02/2017.
 */

public class UsuarioAdapter extends BaseAdapter {

    private final List<Usuario> usuarios;
    private final Activity activity;

    public UsuarioAdapter(List<Usuario> usuarios, Activity activity) {
        this.usuarios = usuarios;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return this.usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return this.usuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.usuarios.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View linha = convertView;
        Usuario usuario = usuarios.get(position);

        if (linha == null)
        {
            linha = this.activity.getLayoutInflater().inflate(R.layout.cell_usuario, parent, false);
        }

        TextView id = (TextView) linha.findViewById(R.id.txtUsuarioID);
        TextView nome = (TextView) linha.findViewById(R.id.txtUsuarioNome);
        TextView sexo = (TextView) linha.findViewById(R.id.txtUsuarioSexo);
        TextView altura = (TextView) linha.findViewById(R.id.txtUsuarioAltura);
        TextView dataNascimento = (TextView) linha.findViewById(R.id.txtUsuarioDataNascimento);

        if (position % 2 == 0)
        {
            linha.setBackgroundColor(activity.getResources().getColor(R.color.actionbar_fg_color));
        }
        else
        {
            linha.setBackgroundColor(activity.getResources().getColor(R.color.actionbar_bg_color));
        }
        id.setText(String.valueOf(usuario.getId()));
        nome.setText(usuario.getNome());
        sexo.setText(usuario.getSexo());
        altura.setText(usuario.getAltura());
        dataNascimento.setText(usuario.getDataNascimento());


        return linha;
    }
}
