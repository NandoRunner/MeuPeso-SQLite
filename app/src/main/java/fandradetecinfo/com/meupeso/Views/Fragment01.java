package fandradetecinfo.com.meupeso.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import fandradetecinfo.com.meupeso.Controllers.BalancaDigitalController;
import fandradetecinfo.com.meupeso.Models.BalancaDigital;
import fandradetecinfo.com.meupeso.PrefsHandler;
import fandradetecinfo.com.meupeso.R;
import fandradetecinfo.com.meupeso.RegistroAdapter;

public class Fragment01 extends Fragment
{
    private String peso;
    private String gordura;
    private String hidratacao;
    private String musculo;
    private String osso;

    private View vw;
    private Context ctx;

    PrefsHandler prefs;

	private ListView minhaLista;

    private BalancaDigitalController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        vw = inflater.inflate(R.layout.frag_01, container, false);

        FloatingActionButton fab = (FloatingActionButton) vw.findViewById(R.id.fabFrag01);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tratarAdicionarRegistro();
            }
        });
		
		minhaLista = (ListView) vw.findViewById(R.id.lstRegistro);
        registerForContextMenu(minhaLista);

        this.controller = new BalancaDigitalController(getActivity());

        return vw;
    }


    @Override
    public void onResume() {
        carregaLista();
        super.onResume();
    }
	
	private void carregaLista() {

        List<BalancaDigital> lstRegistro = controller.getLista(ctx);

        RegistroAdapter adapter = new RegistroAdapter(lstRegistro, getActivity());

        this.minhaLista.setAdapter(adapter);
    }
	
    private void tratarAdicionarRegistro()
    {
        Intent objIntent = new Intent(getActivity(), RegistroActivity.class);
        startActivity(objIntent);
    }


    public static Fragment01 newInstance(String text)
    {
        Fragment01 f = new Fragment01();
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
