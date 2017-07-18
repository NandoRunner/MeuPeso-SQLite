package fandradetecinfo.com.meupeso.Controllers;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import fandradetecinfo.com.meupeso.MainActivity;
import fandradetecinfo.com.meupeso.Models.BalancaDigital;
import fandradetecinfo.com.meupeso.R;
import fandradetecinfo.com.meupeso.Relatorio;

/**
 * Created by Fernando on 14/02/2017.
 */

public class BalancaDigitalController extends _BaseController {

    private BalancaDigital model;

    private EditText etData;
    private EditText etPeso;
    private EditText etGordura;
    private EditText etHidratacao;
    private EditText etMusculo;
    private EditText etOsso;
    private Spinner spUsuario;

    private Map<Integer, String> mapSexo = new Hashtable<>();

    public BalancaDigitalController(Activity activity) {

        this.activity = activity;
        this.model = new BalancaDigital(activity.getBaseContext());

        this.etData = (EditText) activity.findViewById(R.id.txtData);
        this.etPeso = (EditText) activity.findViewById(R.id.txtPeso);
        this.etGordura = (EditText) activity.findViewById(R.id.txtGordura);
        this.etHidratacao = (EditText) activity.findViewById(R.id.txtHidratacao);
        this.etMusculo = (EditText) activity.findViewById(R.id.txtMusculo);
        this.etOsso = (EditText) activity.findViewById(R.id.txtOsso);
        this.spUsuario = (Spinner) activity.findViewById(R.id.spinnerUsuario);

        mapSexo.put(1, "M");
        mapSexo.put(0, "F");
    }

    public BalancaDigital getModel() {
        return model;
    }

    public void setModel(BalancaDigital model) {
        this.model = model;
    }

    public void pegarDoFormulario()
    {
        model.setPeso(etPeso.getText().toString());
        model.setGordura(etGordura.getText().toString());
        model.setHidratacao(etHidratacao.getText().toString());
        model.setMusculo(etMusculo.getText().toString());
        model.setOsso(etOsso.getText().toString());
        model.setId_usuario(String.valueOf(spUsuario.getSelectedItemPosition()));
        model.setData_registro(etData.getText().toString());
    }

    public boolean validarDados()
    {
        if (!validarLista(spUsuario, "Usuário", 0)) return false;
        if (!validarCampo(etData)) return false;
        if (!validarCampo(etPeso)) return false;
        if (!validarCampo(etGordura)) return false;
        if (!validarCampo(etHidratacao)) return false;
        if (!validarCampo(etMusculo)) return false;
        if (!validarCampo(etOsso)) return false;
        return true;
    }

    public boolean registroExistente()
    {
        String sql = "SELECT count(*) FROM balancadigital"
                + " WHERE data_registro = ?"
                + " AND id_usuario = ?";
        String args[] = { model.getData_registro(), model.getId_usuario() };

        model.open();

        boolean ret = model.exists(sql, args);

        model.close();

        return ret;
    }

    public void alertarRegistroAnteriorIdentico()
    {
        montarAlerta("Meu Peso Diário ->  Gravar", "Usuário com registro anterior idêntico!");
    }

    public void alertarRegistroExistente()
    {
        montarAlerta("Meu Peso Diário ->  Gravar", "Usuário/Data já possui registro!");
    }

    public void inserir()
    {

        ContentValues content = new ContentValues();
        content.put("id_usuario", model.getId_usuario());
        content.put("data_registro", model.getData_registro());
        content.put("peso", model.getPeso());
        content.put("gordura", model.getGordura());
        content.put("hidratacao", model.getHidratacao());
        content.put("musculo", model.getMusculo());
        content.put("osso", model.getOsso());

        model.open();

        model.insert(content);

        model.close();
    }

    public List<BalancaDigital> getLista(Context ctx){

        List<BalancaDigital> lstRegistro = new ArrayList<BalancaDigital>();
        model.open();
        int maxRegistros = 6;
        int curRegistro = 1;
        try
        {
            Cursor c = model.exibirRegistros();

            while ((c.moveToNext())) {

                BalancaDigital reg = new BalancaDigital(ctx);

                reg.setId_usuario(c.getString(c.getColumnIndex("id_usuario")));
                reg.setData(c.getString(c.getColumnIndex("data_registro")));
                reg.setPeso(c.getString(c.getColumnIndex("peso")));

                lstRegistro.add(reg);

                if (++curRegistro > maxRegistros) break;
            }
            c.close();
        }catch(Exception e)
        {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("LogX", e.getMessage());
        }
        model.close();

        return lstRegistro;
    }

    public void carregarGrid(List<String> listHeader, GridView gridViewHeader, GridView gridView, Relatorio rel)
    {
        gridViewHeader.setAdapter(null);
        gridViewHeader.setAdapter(new ArrayAdapter<String>(activity.getBaseContext(), R.layout.cell, listHeader));

        gridView.setAdapter(null);
        ArrayList<String> list=new ArrayList<String>();
        ArrayAdapter adapter=new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item,list);

        model.open();
        try
        {
            Cursor c;
            int maxcol = 5;

            switch (rel)
            {
                case Registros:
                    c = model.exibirRegistros(MainActivity.usuario);
                    break;

                case Medias:
                    c = model.exibirMedias(MainActivity.usuario);
                    break;

                default:
                    c = model.exibirTotais();
                    maxcol = 6;
                    break;
            }

            if(c.moveToFirst())
            {
                do
                {
                    for (int i = 0; i < maxcol; i++) {
                        
						if (c.getColumnName(i).equals("sexo"))
							list.add(mapSexo.get(Integer.valueOf(c.getString(i))));
						else
							list.add(c.getString(i));
                    }
                    gridView.setAdapter(adapter);
                }while(c.moveToNext());
            }
            else
            {
                Toast.makeText(activity, "Nenhum registro encontrado", Toast.LENGTH_SHORT).show();
                Log.i("LogX", "Nenhum registro encontrado");
            }
        }catch(Exception e)
        {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("LogX", e.getMessage());
        }
        model.close();
    }

}
