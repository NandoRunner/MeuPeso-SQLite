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

import fandradetecinfo.com.meupeso.Controllers.BalancaDigitalController;
import fandradetecinfo.com.meupeso.PrefsHandler;
import fandradetecinfo.com.meupeso.R;

public class BalancaDigitalFrag01 extends Fragment
{
    private String peso;
    private String gordura;
    private String hidratacao;
    private String musculo;
    private String osso;

    private View vw;
    private Context ctx;

    PrefsHandler prefs;

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

        return vw;
    }

    private void tratarAdicionarRegistro()
    {
        Intent objIntent = new Intent(getActivity(), BalancaDigitalActivity.class);
        startActivity(objIntent);
    }


    public static BalancaDigitalFrag01 newInstance(String text)
    {
        BalancaDigitalFrag01 f = new BalancaDigitalFrag01();
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
