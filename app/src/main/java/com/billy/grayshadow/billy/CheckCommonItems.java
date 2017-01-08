package com.billy.grayshadow.billy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.billy.grayshadow.billy.PriceToDb.db;

public class CheckCommonItems extends AppCompatActivity {
    private static final int BUTTON_DONE = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_common_items);

        final ArrayList<Integer> arrayListCommonItems = new ArrayList<Integer>();

        LinearLayout ll = (LinearLayout) findViewById(R.id.activity_check_common_items);
        //DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        final int intItemCount = db.getPricesCount();

        for (int i = 1; i <= intItemCount; i++) {
            CheckBox cb = new CheckBox(this);
            cb.setText("Item " + i + " : Price " + db.getPrice(i));
            cb.setId(i + 2000);
            ll.addView(cb);
        }

        Button btnDone = new Button(this);
        btnDone.setText("DONE");
        btnDone.setId(BUTTON_DONE);
        ll.addView(btnDone);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 1; i <= intItemCount; i++) {
                    CheckBox cbTemp = (CheckBox) findViewById(i + 2000);
                    if (cbTemp.isChecked())
                        arrayListCommonItems.add(i);
                }
                Intent intent = new Intent(CheckCommonItems.this, DivideUncommonItems.class);
                intent.putExtra("arrayListCommonItems", arrayListCommonItems);
                CheckCommonItems.this.startActivity(intent);
            }
        });
    }
}
