package fandradetecinfo.com.meupeso.Views;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import fandradetecinfo.com.meupeso.Controllers.BalancaDigitalController;
import fandradetecinfo.com.meupeso.MainActivity;
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

        ctx = getActivity().getBaseContext();
        prefs = new PrefsHandler(ctx);

        ImageButton myImgBtn = (ImageButton) vw.findViewById(R.id.imageButton);
        myImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                tratarAdicionarUsuario();
            }
        });

        Spinner mySpn = (Spinner) vw.findViewById(R.id.spinnerUsuario);
        mySpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                MainActivity.usuario = String.valueOf(position + 1);
                prefs.carregar(vw, MainActivity.usuario);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // sometimes you need nothing here
            }
        });

        EditText myTxt = (EditText) vw.findViewById(R.id.txtData);
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        myTxt.setText(df.format("dd/MM/yyyy", new Date()));
        myTxt.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
             @Override
             public void onFocusChange(View v1, boolean hasFocus)
             {
                 tratarData(vw, hasFocus);
             }
        });

        Button myBtn = (Button) vw.findViewById(R.id.btnGravar);
        myBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v1)
            {
                gravarRegistro(vw);
            }
        });
        myBtn.setFocusableInTouchMode(true);
        myBtn.requestFocus();

        return vw;
    }

    private void tratarAdicionarUsuario()
    {
        Intent objIntent = new Intent(getActivity(), UsuarioActivity.class);
        startActivity(objIntent);
    }

    private void tratarData(View v1, boolean hasFocus)
    {
        if (hasFocus)
        {
            Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date

            DatePickerDialog datePicker = new DatePickerDialog(this.getActivity(),
                    R.style.AppTheme, datePickerListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH));

            datePicker.setCancelable(false);
            datePicker.setTitle("Selecione a data");
            datePicker.getWindow().setLayout(600, 1000);
            datePicker.show();
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            month1 = month1.length() == 1 ? "0" + month1 : month1;
            day1 = day1.length() == 1 ? "0" + day1 : day1;
            EditText tvDt = (EditText) vw.findViewById(R.id.txtData);

            tvDt.setText(day1 + "/" + month1 + "/" + year1);
        }
    };

    private boolean gravarRegistro(View v1)
    {
        try
        {
            this.controller = new BalancaDigitalController(getActivity());

            if(!controller.validarDados()) return false;

            controller.pegarDoFormulario();

            if(controller.registroExistente()) {
                controller.alertarRegistroExistente();
                return false;
            }

            if(prefs.registroAnteriorIdentico(controller.getModel())) {
                controller.alertarRegistroAnteriorIdentico();
                return false;
            }

            controller.inserir();

            prefs.salvar(controller.getModel());
            /*
            handler=new DBHandler(getActivity().getBaseContext());//getting the context object
            handler.open();
            long id = handler.InserirBalancaDigital(minhaBalanca);
            handler.close();
            */

            Toast.makeText(getActivity(), "Registro gravado!", Toast.LENGTH_SHORT).show();
            Log.i("LogX", "Registro gravado!");
            return true;
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("LogX", e.getMessage());
            return false;
        }
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
