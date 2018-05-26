package cr.ac.una.escinf.proyectoaerolinea.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Currency;

import cr.ac.una.escinf.proyectoaerolinea.R;
import cr.ac.una.escinf.proyectoaerolinea.fragments.DatePickerFragment;
import cr.ac.una.escinf.proyectoaerolinea.fragments.Tab1;
import cr.ac.una.escinf.proyectoaerolinea.fragments.Tab2;
import cr.ac.una.escinf.proyectoaerolinea.fragments.Tab3;

public class BookFlightActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    private FragmentTabHost tabHost;

    EditText fecha_ida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_flight);
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(),android.R.id.tabcontent);


        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Busca Vuelo"),
                Tab1.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Selecciona tu asiento"),
                Tab2.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Check Out"),
                Tab3.class, null);

        /*tabHost.getTabWidget().getChildTabViewAt(1).setEnabled(false);
        tabHost.getTabWidget().getChildTabViewAt(2).setEnabled(false);*/



        LayoutInflater factory = getLayoutInflater();
        View textView = factory.inflate(R.layout.activity_tab1, null);

         fecha_ida = (EditText) textView.findViewById(R.id.fecha_ida_editText);

        fecha_ida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BookFlightActivity.this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(calendar.getTime());

        fecha_ida.setText(currentDateString);

    }
}
