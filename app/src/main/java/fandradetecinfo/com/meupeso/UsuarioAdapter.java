package fandradetecinfo.com.meupeso;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
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
        TextView idade = (TextView) linha.findViewById(R.id.txtUsuIdade);
        TextView idd = (TextView) linha.findViewById(R.id.txtUsuID);
        TextView sexo = (TextView) linha.findViewById(R.id.txtUsuSexo);

        ArrayList<TextView> lstTV = new ArrayList<>();

        for( int i = 0; i < parent.getChildCount(); i++ )
            if( parent.getChildAt( i ) instanceof TextView )
                lstTV.add( (TextView) parent.getChildAt( i ) );


//        lstTV.add(nome);
//        lstTV.add(altura);
//        lstTV.add(idade);
//        lstTV.add(idd);
//        lstTV.add(sexo);

        int color = position % 2 != 0 ? R.color.colorWhite : R.color.colorBlack;

        for( TextView aux : lstTV )
        {
            aux.setTextColor(activity.getResources().getColor(color));
        }
		
        linha.setBackgroundColor(activity.getResources().getColor((position % 2 != 0 ?
                    R.color.colorPrimary : R.color.actionbar_fg_color)));
					
					
        nome.setText(usuario.getNome());
        altura.setText(usuario.getAltura());
        idade.setText(usuario.getIdade());
        sexo.setText(usuario.getSexo());
        idd.setText(String.valueOf(usuario.getId()));

        return linha;
    }
}
