package com.apps.alberto.eur2leu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv_info, tv_coin1, tv_coin2;
    private EditText et_1, et_2;
    private Button bt_clean;

    private final double CHANGE = 74.4430;
    private final String COIN_1 = "EUR";
    private final String COIN_2 = "RUB";

    private boolean skip = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set money info
        tv_info = (TextView) findViewById(R.id.tv_info);
        if (CHANGE > 0) {
            tv_info.setText(String.format("1 %s = %.3f %s\n100 %s = %.3f %s", COIN_1, CHANGE, COIN_2, COIN_2, 100 / CHANGE, COIN_1));
        }
        else {
            tv_info.setText(String.format("1 %s = %.3f %s\n100 %s = %.3f %s", COIN_1, 100 / CHANGE, COIN_2, COIN_2, CHANGE, COIN_1));
        }

        // Set Currency Names and hist
        tv_coin1 = (TextView) findViewById(R.id.tv_coin1);
        tv_coin2 = (TextView) findViewById(R.id.tv_coin2);
        tv_coin1.setText(COIN_1);
        tv_coin2.setText(COIN_2);

        et_1 = (EditText) findViewById(R.id.et_eur);
        et_2 = (EditText) findViewById(R.id.et_leu);
        bt_clean = (Button) findViewById(R.id.bt_clean);

        et_1.setHint(COIN_1);
        et_2.setHint(COIN_2);


        // Convert COIN_1 to COIN_2
        et_1.addTextChangedListener(new TextWatcher() {

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
                        double c2 = Double.parseDouble(s.toString()) * CHANGE;
                        c2 = roundMoney(c2);
                        et_2.setText(String.valueOf(c2));
                    }
                    else {
                        skip = false;
                    }
                }
            }
        });

        // Convert COIN_1 to COIN_1
        et_2.addTextChangedListener(new TextWatcher() {

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
                        double c1 = Double.parseDouble(s.toString()) / CHANGE;
                        c1 = roundMoney(c1);
                        et_1.setText(String.valueOf(c1));
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
                et_1.setText("");
                et_2.setText("");
            }
        });


        et_2.requestFocus();

//
//        RelativeLayout ll_2 = (RelativeLayout) findViewById(R.id.activity_main);
//
//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(et_leu, InputMethodManager.SHOW_IMPLICIT);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //InputMethodManager imm2 = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm2.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


//        imm.toggleSoftInputFromWindow(
//                ll_2.getApplicationWindowToken(),
//                InputMethodManager.SHOW_FORCED, 0);

    }

    private double roundMoney(double val) {
        val = Math.round(val * 1000);
        return val/1000;
    }
}
