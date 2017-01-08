package com.billy.grayshadow.billy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class PriceToDb extends AppCompatActivity {

    public static DatabaseHandler db;
    private static final int BUTTON_DONE = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.e("MOHIT", "1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHandler(getApplicationContext());
        db.deleteData();
        askNoOfItems();
    }

    private void createViews()
    {
        Log.e("MOHIT", "2");
        TextView tvNoOfItems = (TextView) findViewById(R.id.tvNoOfItems);
        LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout);
        String stringNoOfItems = tvNoOfItems.getText().toString();
        int noOfItems=0;

        if(!stringNoOfItems.equals(""))
            noOfItems = Integer.parseInt(stringNoOfItems);

        for (int i=1; i<=noOfItems; i++)
        {
            EditText et = new EditText(this);
            et.setId(i+1000);
            et.setHint("Item "+i);
            et.setMinLines(1);
            et.setMaxLines(1);
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
            et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            ll.addView(et);
        }

        Button btnDone = new Button(this);
        btnDone.setText("DONE");
        btnDone.setId(BUTTON_DONE);
        ll.addView(btnDone);

        final int finalIntNoOfItems = noOfItems;
        btnDone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Boolean boolCheckEditTexts = checkEditTexts(finalIntNoOfItems);
                if(!boolCheckEditTexts)
                {
                    new AlertDialog.Builder(PriceToDb.this)
                            .setTitle("Check prices")
                            .setCancelable(false)
                            .setMessage("Price of one or more item(s) has not been entered")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else
                {

                    EditText editTextTemp;

                    //db.deleteData();

                    for(int i=1; i<=finalIntNoOfItems; i++)
                    {
                        editTextTemp = (EditText) findViewById(i+1000);
                        Log.e("MOHIT", editTextTemp.getText().toString());
                        db.addPrice(i, Float.parseFloat(editTextTemp.getText().toString()));
                    }
                    Intent intent = new Intent(getApplicationContext(), CheckCommonItems.class);
                    getApplicationContext().startActivity(intent);
                }
            }
        });
    }

    private Boolean checkEditTexts(int intNoOfItems)
    {
        Log.e("MOHIT", "3");
        EditText editTextTemp;
        for(int i=1; i<=intNoOfItems; i++)
        {
            editTextTemp = (EditText) findViewById(i+1000);
            if(editTextTemp.getText().toString().isEmpty())
                return false;
        }
        return true;
    }

    private void askNoOfItems()
    {
        Log.e("MOHIT", "4");
        final TextView tvNoOfItems = (TextView) findViewById(R.id.tvNoOfItems);

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id)
                            {
                                tvNoOfItems.setText(userInput.getText());
                                createViews();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
}