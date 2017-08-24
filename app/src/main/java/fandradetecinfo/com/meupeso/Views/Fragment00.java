package fandradetecinfo.com.meupeso.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Usuario usuarioSelecionado = (Usuario) minhaLista.getAdapter().getItem(info.position);

        final MenuItem itemPadrao = menu.add("Definir como Usuário Padrão");
        final MenuItem itemEditar = menu.add("Editar Usuário");
        final MenuItem itemApagar = menu.add("Apagar Usuário");

        itemPadrao.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MainActivity.usuario = String.valueOf(usuarioSelecionado.getId());
                String msg = "Usuário " + MainActivity.usuario + " selecionado!";
                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                return false;
            }
        });
        itemEditar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        itemApagar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                apagarUsuario(usuarioSelecionado.getId());
                return false;
            }
        });
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

    private boolean apagarUsuario(long id)
    {
        try
        {
            controller.getModel().setId(id);
            controller.apagar();
            carregaLista();

            Log.i("LogX", "Usuário apagado!");
            return true;
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("LogX", e.getMessage());
            return false;
        }
    }
}
