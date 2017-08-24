package fandradetecinfo.com.meupeso.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import fandradetecinfo.com.meupeso.Controllers.UsuarioController;
import fandradetecinfo.com.meupeso.MainActivity;
import fandradetecinfo.com.meupeso.Models.Usuario;
import fandradetecinfo.com.meupeso.R;
import fandradetecinfo.com.meupeso.UsuarioAdapter;

public class Fragment00 extends Fragment
{
    private Context ctx;

	private ListView minhaLista;

    private UsuarioController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View vw = inflater.inflate(R.layout.frag_00, container, false);

        FloatingActionButton fab = (FloatingActionButton) vw.findViewById(R.id.fabFrag00);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tratarAdicionarUsuario();
            }
        });
		
		minhaLista = (ListView) vw.findViewById(R.id.lstUsuario);
        registerForContextMenu(minhaLista);

        this.controller = new UsuarioController(getActivity());

        return vw;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public void onResume() {
        carregaLista();
        super.onResume();
    }
	
	private void carregaLista() {

        List<Usuario> lstUsuario = controller.getLista(ctx);

        UsuarioAdapter adapter = new UsuarioAdapter(lstUsuario, getActivity());

        this.minhaLista.setAdapter(adapter);
    }
	
    private void tratarAdicionarUsuario()
    {
        Intent objIntent = new Intent(getActivity(), UsuarioActivity.class);
        startActivity(objIntent);
    }


    public static Fragment00 newInstance(String text)
    {
        Fragment00 f = new Fragment00();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
