package fandradetecinfo.com.meupeso.Models;

import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Fernando on 10/02/2017.
 */

public class Usuario extends _BaseModel implements Serializable  {

    private long id;
    private String nome;
    private String sexo;
    private String altura;
    private String dataNascimento;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario(Context ctx)
    {
        super(ctx);
        this.table = "usuario";
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

    public void setSexoTratado(String sexo) {
        this.sexo = sexo;
    }
    public void setAltura(String altura) {
        this.altura = altura.replace(',', '.');
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getIdade() {
        try {
            return String.valueOf(calculaIdade(getDataNascimento()));

        } catch (ParseException pe) {
            return pe.getMessage();
        }
    }

    public Cursor exibirRegistros()
    {
        try
        {
            String sql = "SELECT id, nome, altura, data_nascimento, "
                    + " case when " +
                    "sexo_masculino = 1 then 'M' else 'F' end as sexo"
                    + " FROM usuario"
                    + " ORDER BY id";
            return buscarCursor(sql);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public String contaDias(String dataInicialBR, String dataFinalBR) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setLenient(false);
        Date dataInicio = df.parse(dataInicialBR);
        Date dataFim = df.parse(dataFinalBR);
        long dt = (dataFim.getTime() - dataInicio.getTime()) + 3600000;
        Long diasCorridosAnoLong = (dt / 86400000L);
        Integer diasDecorridosInt = Integer.valueOf(diasCorridosAnoLong.toString());
        String diasDecorridos = String.valueOf(diasDecorridosInt); //Sem numeros formatados;
        return diasDecorridos;
    }

    public BigDecimal calculaIdade(String dataDoMeuNascimento) throws ParseException{
        BigDecimal qtdDias = new BigDecimal(contaDias(dataDoMeuNascimento,getDataDiaBr()));
        BigDecimal ano = new BigDecimal(365.25);
        BigDecimal idade = qtdDias.divide(ano,0, RoundingMode.DOWN);
        return idade;
    }
}
