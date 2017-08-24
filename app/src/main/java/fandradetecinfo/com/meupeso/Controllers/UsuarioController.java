package fandradetecinfo.com.meupeso.Controllers;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fandradetecinfo.com.meupeso.Models.Usuario;

import fandradetecinfo.com.meupeso.R;

/**
 * Created by Fernando on 10/02/2017.
 */


public class UsuarioController extends _BaseController {

    private Usuario model;

    private EditText etNome;
    private EditText etAltura;
    private EditText etData;
    private Spinner spSexo;

    public UsuarioController(Activity activity) {

        this.activity = activity;
        this.model = new Usuario(activity.getBaseContext());

        this.etNome = (EditText) activity.findViewById(R.id.txtUsrNome);
        this.etAltura = (EditText) activity.findViewById(R.id.txtUsrAltura);
        this.etData = (EditText) activity.findViewById(R.id.txtUsrData);
        this.spSexo = (Spinner) activity.findViewById(R.id.spnUsrSexo);
    }

    public Usuario getModel() {
        return model;
    }

    public void setModel(Usuario model) {
        this.model = model;
    }

    public void pegarDoFormulario()
    {
        model.setNome(etNome.getText().toString());
        model.setAltura(etAltura.getText().toString());
        model.setDataNascimento(etData.getText().toString());
        model.setSexo(String.valueOf(spSexo.getSelectedItemPosition()));
    }

    /*
    public void colocarNoFormulario(Usuario usuario)
    {
        etNome.setText(usuario.getNome());
        etAltura.setText(usuario.getAltura());
        etData.setText(usuario.getDataNascimento());
        //spSexo.setSelection();

        this.usuario = usuario;
    }*/

    public boolean validarDados()
    {
        if (!validarCampo(etNome)) return false;
        if (!validarLista(spSexo, "Sexo", 1)) return false;
        if (!validarCampo(etAltura)) return false;
        return validarCampo(etData);

    }

    public boolean usuarioExistente()
    {
        String sql = "SELECT count(*) FROM usuario WHERE nome = ?";
        String args[] = { model.getNome() };

		model.open();
        boolean ret = model.exists(sql, args);
        model.close();

        return ret;
    }

    public void alertarUsuarioExistente()
    {
        montarAlerta("Meu Peso Diário ->  Novo Usuário", "Usuário já cadastrado!");
    }

    public void inserir()
    {
        ContentValues content = new ContentValues();
        content.put("nome", model.getNome());
        content.put("sexo_masculino", model.getSexo());
        content.put("altura", model.getAltura());
        content.put("data_nascimento", model.getDataNascimento());

        model.open();

        model.insert(content);

        model.close();
    }

    public void apagar()
    {
        model.open();

        model.deleteById(String.valueOf(model.getId()));

        model.close();
    }

    public List<Usuario> getLista(Context ctx){

        List<Usuario> lstRegistro = new ArrayList<Usuario>();
        model.open();

        try
        {
            Cursor c = model.exibirRegistros();

            while ((c.moveToNext())) {

                Usuario reg = new Usuario(ctx);

                reg.setId(c.getLong(c.getColumnIndex("id")));
                reg.setNome(c.getString(c.getColumnIndex("nome")));
                reg.setDataNascimento(c.getString(c.getColumnIndex("data_nascimento")));
                reg.setAltura(c.getString(c.getColumnIndex("altura")));
                reg.setSexo(c.getString(c.getColumnIndex("sexo_masculino")));

                lstRegistro.add(reg);


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
}
