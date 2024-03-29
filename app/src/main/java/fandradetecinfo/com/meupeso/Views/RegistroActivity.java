package fandradetecinfo.com.meupeso.Views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import fandradetecinfo.com.meupeso.Controllers.BalancaDigitalController;
import fandradetecinfo.com.meupeso.MainActivity;
import fandradetecinfo.com.meupeso.PrefsHandler;
import fandradetecinfo.com.meupeso.R;

public class RegistroActivity extends AppCompatActivity
{

    PrefsHandler prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarBalancaDigital);
        setSupportActionBar(toolbar);


        Context ctx = getBaseContext();
        prefs = new PrefsHandler(ctx);

        Spinner mySpn = (Spinner) findViewById(R.id.spinnerUsuario);
        mySpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                MainActivity.usuario = String.valueOf(position + 1);
                prefs.carregar(findViewById(android.R.id.content), MainActivity.usuario);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // sometimes you need nothing here
            }
        });


        EditText myTxt = (EditText) findViewById(R.id.txtData);
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        myTxt.setText(df.format("dd/MM/yyyy", new Date()));
        myTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v1, boolean hasFocus) {
                tratarData(hasFocus);
            }
        });


        if (null != toolbar) {
            toolbar.setNavigationIcon(R.drawable.back_w_48px);

            toolbar.setTitle("Registro Novo");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavUtils.navigateUpFromSameTask(RegistroActivity.this);
                }
            });
            toolbar.inflateMenu(R.menu.menu_registro);

        }

        EditText myTxtFocus = (EditText) findViewById(R.id.txtPeso);

        myTxtFocus.requestFocus();
    }

    private void tratarAdicionarUsuario()
    {
        Intent objIntent = new Intent(this, UsuarioActivity.class);
        startActivity(objIntent);
    }

    private void tratarData(boolean hasFocus)
    {
        if (hasFocus)
        {
            Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date

            DatePickerDialog datePicker = new DatePickerDialog(this,
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
                              int selectedMonth, int selectedDay) 
		{
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            month1 = month1.length() == 1 ? "0" + month1 : month1;
            day1 = day1.length() == 1 ? "0" + day1 : day1;
            EditText tvDt = (EditText) findViewById(R.id.txtData);

            tvDt.setText(day1 + "/" + month1 + "/" + year1);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_registro, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.salvar_registro:
                if (gravarRegistro())
                    NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean gravarRegistro()
    {
        try
        {
            BalancaDigitalController controller = new BalancaDigitalController(this);

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


            Log.i("LogX", "Registro gravado!");
            return true;
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("LogX", e.getMessage());
            return false;
        }
    }

}
