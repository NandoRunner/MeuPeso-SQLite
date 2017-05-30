package fandradetecinfo.com.meupeso.Controllers;

import android.content.ContentValues;
import android.widget.EditText;
import android.widget.Spinner;

import fandradetecinfo.com.meupeso.Models.Usuario;
import fandradetecinfo.com.meupeso.R;
import fandradetecinfo.com.meupeso.Views.UsuarioActivity;

/**
 * Created by Fernando on 10/02/2017.
 */

public class UsuarioController extends _BaseController {

    private Usuario model;

    private EditText etNome;
    private EditText etAltura;
    private EditText etData;
    private Spinner spSexo;

    public UsuarioController(UsuarioActivity activity) {

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
        if (!validarCampo(etData)) return false;

        return true;
    }

    public void alertarUsuarioExistente()
    {
        montarAlerta("Novo Usuário", "Usuário já cadastrado!");
    }

    public boolean usuarioExistente()
    {
        String sql = "SELECT count(*) FROM usuario WHERE nome = ?";
        String args[] = { model.getNome() };

        return model.exists(sql, args);
    }

    public void inserir()
    {
        ContentValues content = new ContentValues();
        content.put("nome", model.getNome());
        content.put("sexo_masculino", model.getSexo());
        content.put("altura", model.getAltura());
        content.put("data_nascimento", model.getDataNascimento());

        model.insert(content);
    }
}
