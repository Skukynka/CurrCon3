package com.example.uzivatel.currcon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class CalcFromCZK extends AppCompatActivity {

    String currencies[];
    Spinner spinner;
    ArrayList<Mena> meny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_from_czk);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Selection of the spinner
        spinner = (Spinner) findViewById(R.id.spinner_currency);


        meny = Helper.getCurrencies();
        String curr2[] = new String[meny.size()];

        for (int i = 0; i < meny.size(); i++) {
            // System.out.println(list.get(i));
            curr2[i] = meny.get(i).getKrajina() + " - " + meny.get(i).getKod() + " - " + meny.get(i).getMena();
        }

        currencies = curr2;

        // Application of the Array to the Spinner

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currencies);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);


 /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        */

    }

    public void calc(View v) {
        String inputString = ((EditText)findViewById(R.id.etAmountInput)).getText().toString();
        if (inputString.equalsIgnoreCase("")) {
            inputString="0";
        }
        int amountinput=Integer.parseInt(inputString);
        int menaIndex = (int)spinner.getSelectedItemId();

        Mena mena = meny.get(menaIndex);

        double result = amountinput * mena.getMnozstvo() / mena.getKurz();

        TextView tvResult = (TextView) findViewById(R.id.tvResult);
        tvResult.setText(String.format("%,2f",result)+" "+ mena.getKod());



    }
}
