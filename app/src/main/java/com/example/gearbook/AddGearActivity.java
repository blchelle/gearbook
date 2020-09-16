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

        // Find each of the significant form items
        editTextMaker = findViewById(R.id.editTextMaker);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDateYear = findViewById(R.id.editTextDateYear);
        editTextDateMonth = findViewById(R.id.editTextDateMonth);
        editTextDateDay = findViewById(R.id.editTextDateDay);
        editTextComment = findViewById(R.id.editTextComment);
        buttonAddGear = findViewById(R.id.buttonAddGear);

        // Add a listener to each of the text fields
        // This is so that the button can determine if it should be enabled
        editTextMaker.addTextChangedListener(this.textWatcher);
        editTextPrice.addTextChangedListener(this.textWatcher);
        editTextDescription.addTextChangedListener(this.textWatcher);
        editTextDateYear.addTextChangedListener(this.textWatcher);
        editTextDateMonth.addTextChangedListener(this.textWatcher);
        editTextDateDay.addTextChangedListener(this.textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            buttonAddGear.setEnabled(checkTextViewsForContent());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /**
     * Checks to see if each of the required text fields have content
     * @return True if each of the text fields have content, False otherwise
     */
    private boolean checkTextViewsForContent() {
        boolean maker = !editTextMaker.getText().toString().trim().isEmpty();
        boolean price = !editTextPrice.getText().toString().trim().isEmpty();
        boolean description = !editTextDescription.getText().toString().trim().isEmpty();
        boolean year = !editTextDateYear.getText().toString().trim().isEmpty();
        boolean month = !editTextDateMonth.getText().toString().trim().isEmpty();
        boolean day = !editTextDateDay.getText().toString().trim().isEmpty();

        return maker && price && description && year && month && day;
    }

    public void handleOnAddGearButtonClick(View view) {
        String maker = editTextMaker.getText().toString().trim();
        Float price = Float.parseFloat(editTextPrice.getText().toString().trim());
        String description = editTextDescription.getText().toString().trim();
        int year = Integer.parseInt(editTextDateYear.getText().toString().trim());
        int month = Integer.parseInt(editTextDateMonth.getText().toString().trim());
        int day = Integer.parseInt(editTextDateDay.getText().toString().trim());
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