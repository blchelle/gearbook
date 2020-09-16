package com.example.gearbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddGearActivity extends AppCompatActivity {

    private EditText editTextMaker;
    private EditText editTextPrice;
    private EditText editTextDescription;
    private EditText editTextDateYear;
    private EditText editTextDateMonth;
    private EditText editTextDateDay;
    private EditText editTextComment;
    private Button buttonAddGear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gear);

        editTextMaker = findViewById(R.id.editTextMaker);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDateYear = findViewById(R.id.editTextDateYear);
        editTextDateMonth = findViewById(R.id.editTextDateMonth);
        editTextDateDay = findViewById(R.id.editTextDateDay);
        editTextComment = findViewById(R.id.editTextComment);
        buttonAddGear = findViewById(R.id.buttonAddGear);

        editTextMaker.addTextChangedListener(this.textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            buttonAddGear.setEnabled(!editTextMaker.getText().toString().trim().isEmpty());
            buttonAddGear.setClickable(!editTextMaker.getText().toString().trim().isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void handleOnAddGearButtonClick(View view) {
        String maker = editTextMaker.getText().toString().trim();
        Float price = Float.parseFloat(editTextPrice.getText().toString().trim());
        String description = editTextDescription.getText().toString().trim();
        Integer year = Integer.parseInt(editTextDateYear.getText().toString().trim());
        Integer month = Integer.parseInt(editTextDateMonth.getText().toString().trim());
        Integer day = Integer.parseInt(editTextDateDay.getText().toString().trim());
        String comment = editTextComment.getText().toString().trim();

        Date date = new GregorianCalendar(year, month, day).getTime();

        Gear newGear;
        if (comment.isEmpty()) {
            newGear = new Gear(date, maker, description, price);
        }
        else {
            newGear = new Gear(date, maker, description, price, comment);
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("NEW_GEAR", newGear);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}