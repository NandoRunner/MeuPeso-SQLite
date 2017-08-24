package fandradetecinfo.com.meupeso.Views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import fandradetecinfo.com.meupeso.Controllers.UsuarioController;
import fandradetecinfo.com.meupeso.R;

/**
 * Created by Fernando on 09/01/2017.
 */

public class UsuarioActivity extends AppCompatActivity
{
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarUsuario);
        setSupportActionBar(toolbar);

        EditText myTxt = (EditText) findViewById(R.id.txtUsrData);
        myTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v1, boolean hasFocus) {
                tratarData(hasFocus);
            }
        });


        if (null != toolbar) {
            toolbar.setNavigationIcon(R.drawable.back_w_48px);

            toolbar.setTitle("Usuário Novo");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavUtils.navigateUpFromSameTask(UsuarioActivity.this);
                }
            });
            toolbar.inflateMenu(R.menu.menu_usuario);
        }
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
            EditText tvDt = (EditText) findViewById(R.id.txtUsrData);
            tvDt.setText(day1 + "/" + month1 + "/" + year1);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
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

            case R.id.salvar_usuario:
                if (gravarUsuario())
                    NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean gravarUsuario()
    {
        try
        {
			UsuarioController controller = new UsuarioController(this);
            if(!controller.validarDados()) return false;

            controller.pegarDoFormulario();

            if(controller.usuarioExistente()) {
                controller.alertarUsuarioExistente();
                return false;
            }

            controller.inserir();

            Log.i("LogX", "Usuário cadastrado!");
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
