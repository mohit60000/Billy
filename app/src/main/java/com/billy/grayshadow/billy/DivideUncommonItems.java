package com.billy.grayshadow.billy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import static com.billy.grayshadow.billy.PriceToDb.db;
import static java.lang.System.in;

public class DivideUncommonItems extends AppCompatActivity {
    private ArrayList<Integer> arrayListCommonItems;
    private float floatArrayFinalCost[];
    private int intItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divide_uncommon_items);

        arrayListCommonItems = new ArrayList<Integer>();
        floatArrayFinalCost = new float[7];

        int i=1;

        Intent intent = getIntent();
        arrayListCommonItems = intent.getIntegerArrayListExtra("arrayListCommonItems");
        Arrays.fill(floatArrayFinalCost, 0.0f);

        divideCommonItems();
        intItemCount = db.getPricesCount();
        while(arrayListCommonItems.contains(i) && i<=intItemCount)
            i++;
        changeQuestion(i);
    }

    private void divideCommonItems() {
        float floatPriceOfItem = 0.0f, floatCostPerHead = 0.0f;
        for (int intItemNumber : arrayListCommonItems) {
            floatPriceOfItem = db.getPrice(intItemNumber);
            floatCostPerHead = floatPriceOfItem/7;
            for (int i=0; i<7; i++)
                floatArrayFinalCost[i] += floatCostPerHead;
        }
    }

    private void changeQuestion(final int finalIntItemNumber) {
        TextView tvMessage = (TextView) findViewById(R.id.textView);
        tvMessage.setText("Select users of item "+finalIntItemNumber);

        for (int i=0; i<7; i++) {
            CheckBox cbTemp = (CheckBox) findViewById(R.id.cb3001+i);
            cbTemp.setChecked(false);
        }

        Button btnDone = (Button) findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intFinalNumber = finalIntItemNumber;
                float floatPriceOfItem = 0.0f, floatCostPerHead = 0.0f;
                int intHeadCount=0;
                for (int i=0; i<7; i++) {
                    CheckBox cbTemp = (CheckBox) findViewById(R.id.cb3001+i);
                    if (cbTemp.isChecked())
                        intHeadCount++;
                }
                floatPriceOfItem = db.getPrice(intFinalNumber);
                floatCostPerHead = floatPriceOfItem/intHeadCount;

                for (int i=0; i<7; i++) {
                    CheckBox cbTemp = (CheckBox) findViewById(R.id.cb3001+i);
                    if (cbTemp.isChecked())
                        floatArrayFinalCost[i] += floatCostPerHead;
                }
                Log.e("MOHIT",intFinalNumber+" "+intItemCount);
                intFinalNumber++;
                while(arrayListCommonItems.contains(intFinalNumber) && intFinalNumber<=intItemCount)
                    intFinalNumber++;
                Log.e("MOHIT",intFinalNumber+"");
                if(intFinalNumber <= intItemCount)
                    changeQuestion(intFinalNumber);
                else
                {
                    Intent intent = new Intent(DivideUncommonItems.this, FinalDivision.class);
                    intent.putExtra("floatArrayFinalCost", floatArrayFinalCost);
                    DivideUncommonItems.this.startActivity(intent);
                }
            }
        });
    }
}
