package fandradetecinfo.com.meupeso.Models;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by Fernando on 10/02/2017.
 */

public class Usuario extends _BaseModel implements Serializable  {

    private String TABLE_NAME = "usuario";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;
    private String nome;
    private String sexo;
    private String altura;
    private String dataNascimento;


    public Usuario(Context ctx)
    {
        super(ctx);
        this.table = TABLE_NAME;
    }


    public String getNome() {
        return nome;
    }

    public String getSexo() {
        return sexo;
    }

    public String getAltura() {
        return altura;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo == "1" ? "1" : "0";
    }

    public void setAltura(String altura) {
        this.altura = altura.replace(',', '.');
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }


}
