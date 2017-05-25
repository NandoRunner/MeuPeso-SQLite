package fandradetecinfo.com.meupeso.Views;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fandradetecinfo.com.meupeso.Controllers.BalancaDigitalController;
import fandradetecinfo.com.meupeso.MainActivity;
import fandradetecinfo.com.meupeso.R;
import fandradetecinfo.com.meupeso.Relatorio;

public class BalancaDigitalFrag03 extends Fragment {

    private BalancaDigitalController controller;
    private View vw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vw = inflater.inflate(R.layout.frag_03, container, false);

        TextView tv = (TextView) vw.findViewById(R.id.tvFrag03);
        tv.setText(getArguments().getString("msg") + MainActivity.usuario);

        this.controller = new BalancaDigitalController(getActivity());

        return vw;
    }

    public static BalancaDigitalFrag03 newInstance(String text) {

        BalancaDigitalFrag03 f = new BalancaDigitalFrag03();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null)
            {
                a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                this.montarGrid();
                Log.i("LogX", "Frag 03 - Carregou grid");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void montarGrid()
    {
        List<String> listHeader = new ArrayList<String>();
        listHeader.add("Periodo");
        listHeader.add("Peso");
        listHeader.add("Gord.");
        listHeader.add("Hidr.");
        listHeader.add("Musc.");

        GridView gridViewHeader=(GridView) vw.findViewById(R.id.grvFrag03Header);

        GridView gridView = (GridView) vw.findViewById(R.id.grvFrag03);
 
        controller.carregarGrid(listHeader, gridViewHeader, gridView, Relatorio.Medias);
    }

}