package cr.ac.una.escinf.proyectoaerolinea.fragments;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TabHost;

import java.text.DateFormat;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.BindView;

import butterknife.Unbinder;
import cr.ac.una.escinf.proyectoaerolinea.R;

public class Tab1 extends Fragment implements DatePickerDialog.OnDateSetListener {

    public Tab1() {
    }

    @BindView(R.id.ida_y_vuelta_btn)
    RadioButton idaVuelta;
    @BindView(R.id.solo_ida_btn)
    RadioButton ida;
    @BindView(R.id.desde_spinner)
    Spinner origen;
    @BindView(R.id.hacia_spinner)
    Spinner destino;
    @BindView(R.id.fecha_ida_layout)
    TextInputLayout fechaIda_ly;
    @BindView(R.id.fecha_vuelta_layout)
    TextInputLayout fecha_vuelta_ly;
    @BindView(R.id.fecha_ida_editText)
    EditText fechaIda_et;
    @BindView(R.id.fecha_vuelta_editText)
    EditText fecha_vuelta_et;

    @BindView(R.id.next_tab1)
    Button next;

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab1, container, false);
        unbinder = ButterKnife.bind(this, view);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabHost host = (TabHost) getActivity().findViewById(android.R.id.tabhost);
                host.setCurrentTab(1);
            }
        });

        fechaIda_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getFragmentManager(), "date picker");
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(calendar.getTime());

        fechaIda_et.setText(currentDateString);
    }
}
