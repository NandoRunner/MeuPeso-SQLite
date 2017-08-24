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
            linha = this.activity.getLayoutInflater().inflate(R.layout.cell_frag_00, parent, false);
        }

        TextView nome = (TextView) linha.findViewById(R.id.txtUsuNome);
        TextView altura = (TextView) linha.findViewById(R.id.txtUsuAltura);
        TextView dataNascimento = (TextView) linha.findViewById(R.id.txtUsuDataNascimento);

        if (position % 2 == 0)
        {
            linha.setBackgroundColor(activity.getResources().getColor(R.color.actionbar_fg_color));
        }
        else
        {
            linha.setBackgroundColor(activity.getResources().getColor(R.color.actionbar_bg_color));
        }
        nome.setText(usuario.getNome());
        altura.setText(usuario.getAltura());
        dataNascimento.setText(usuario.getDataNascimento());


        return linha;
    }
}
