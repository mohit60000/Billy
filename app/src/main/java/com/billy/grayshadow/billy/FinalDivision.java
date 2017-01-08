package com.billy.grayshadow.billy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FinalDivision extends AppCompatActivity {
    private float floatArrayFinalCost[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_division);

        LinearLayout ll = (LinearLayout)findViewById(R.id.activity_final_division);
        Intent intent = getIntent();
        floatArrayFinalCost = intent.getFloatArrayExtra("floatArrayFinalCost");

        for(int i=1; i<=7; i++)
        {
            TextView tv = new TextView(this);
            tv.setText("User "+i+" : $"+floatArrayFinalCost[i-1]);
            ll.addView(tv);
        }
    }
}
