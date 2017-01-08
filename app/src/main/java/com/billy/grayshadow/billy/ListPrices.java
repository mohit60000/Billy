package com.billy.grayshadow.billy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

import static com.billy.grayshadow.billy.PriceToDb.db;

public class ListPrices extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_prices);
        ArrayList<String> stringArrayListNumbers = new ArrayList<String>();
        Intent intent = getIntent();

        stringArrayListNumbers = intent.getStringArrayListExtra("stringArrayListNumbers");

        final LinearLayout ll = (LinearLayout) findViewById(R.id.activity_list_prices);
        final int finalIntPriceListSize = stringArrayListNumbers.size();
        for(int i=0; i<finalIntPriceListSize; i++)
        {
            LinearLayout llTemp = new LinearLayout(this);
            llTemp.setOrientation(LinearLayout.HORIZONTAL);
            llTemp.setId(i+5000);

            EditText etPrice = new EditText(this);
            etPrice.setText(stringArrayListNumbers.get(i));
            etPrice.setMinLines(1);
            etPrice.setMaxLines(1);
            etPrice.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
            etPrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etPrice.setId(i+4000);

            Button btnRemoveEt = new Button(this);
            btnRemoveEt.setText("Remove");
            final int rowPointer = i;
            btnRemoveEt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ll.removeView(findViewById(rowPointer+5000));
                }
            });

            llTemp.addView(btnRemoveEt);
            llTemp.addView(etPrice);

            ll.addView(llTemp);
        }
        Button btnDone = new Button(this);
        btnDone.setText("DONE");
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countEditTexts(finalIntPriceListSize);
            }
        });
        ll.addView(btnDone);
    }

    private void countEditTexts(int finalIntPriceListSize) {
        int counter=1;
        for(int i=0; i<finalIntPriceListSize; i++)
        {
            if(findViewById(i+4000) != null)
            {
                Log.e("MOHIT", "id="+(i+4000));
                db.addPrice(counter, Float.parseFloat(((EditText)findViewById(i+4000)).getText().toString()));
                counter++;
            }
        }
        Intent intent = new Intent(ListPrices.this, CheckCommonItems.class);
        ListPrices.this.startActivity(intent);
    }
}
