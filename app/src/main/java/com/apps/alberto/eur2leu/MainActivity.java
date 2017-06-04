package com.apps.alberto.eur2leu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText et_eur;
    private EditText et_leu;
    private Button bt_clean;

    private final double CHANGE = 4.56829639;
    private boolean skip = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_eur = (EditText) findViewById(R.id.et_eur);
        et_leu = (EditText) findViewById(R.id.et_leu);
        bt_clean = (Button) findViewById(R.id.bt_clean);


        // Convert Euro to Leu
        et_eur.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0) {
                    if (!skip) {
                        skip = true;
                        double leu = Double.parseDouble(s.toString()) * CHANGE;
                        leu = roundMoney(leu);
                        et_leu.setText(String.valueOf(leu));
                    }
                    else {
                        skip = false;
                    }
                }
            }
        });

        // Convert Leu to Eur
        et_leu.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0) {
                    if (!skip) {
                        skip = true;
                        double eur = Double.parseDouble(s.toString()) / CHANGE;
                        eur = roundMoney(eur);
                        et_eur.setText(String.valueOf(eur));
                    }
                    else {
                        skip = false;
                    }
                }
            }
        });

        bt_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_eur.setText("");
                et_leu.setText("");
            }
        });


    }

    private double roundMoney(double val) {
        val = Math.round(val * 1000);
        return val/1000;
    }
}
