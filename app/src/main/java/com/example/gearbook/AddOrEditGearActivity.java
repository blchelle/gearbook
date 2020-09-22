package com.example.gearbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * This class is reused for both adding and editing gear
 * If it is used for editing then the info for the gear being edited will be passed as an intent
 * If it is used for adding no additional info will be passed in the intent
 */
public class AddOrEditGearActivity extends AppCompatActivity {

    private EditText editTextMaker;
    private EditText editTextPrice;
    private EditText editTextDescription;
    private EditText editTextComment;
    private Button buttonAddGear;

    // The Year, Month and Day of Month have their own text fields
    private EditText editTextDateYear;
    private EditText editTextDateMonth;
    private EditText editTextDateDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_gear);

        // Find each of the significant form items
        editTextMaker = findViewById(R.id.editTextMaker);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDateYear = findViewById(R.id.editTextDateYear);
        editTextDateMonth = findViewById(R.id.editTextDateMonth);
        editTextDateDay = findViewById(R.id.editTextDateDay);
        editTextComment = findViewById(R.id.editTextComment);
        buttonAddGear = findViewById(R.id.buttonAddGear);

        // If some gear was passed with the intent, then that means that the
        // activity will be used for editing
        Gear gear = getIntent().getParcelableExtra("GEAR");
        if (gear != null) {
            // If the code has entered here, then we know we are editing some gear
            // So we should pre-fill all the fields with the gear's information

            editTextMaker.setText(gear.getMaker());
            editTextDescription.setText(gear.getDescription());
            editTextPrice.setText(gear.getPrice().toString());
            editTextComment.setText(gear.getComment());

            // Extract the Year, Month and Date from the gear's date
            Date gearDate = gear.getDate();
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("MST"));
            calendar.setTime(gearDate);

            // Convert the gear date to YYYY-MM-DD format
            editTextDateYear.setText(String.format("%04d", calendar.get(Calendar.YEAR)));
            editTextDateMonth.setText(String.format("%02d", calendar.get(Calendar.MONTH)));
            editTextDateDay.setText(String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)));

            // Change the button text to show that the activity is in 'edit' mode
            buttonAddGear.setText("Confirm Changes");
        }

        // Add a listener to each of the text fields
        // This is so that the button can determine if it should be enabled
        editTextMaker.addTextChangedListener(this.textWatcher);
        editTextPrice.addTextChangedListener(this.textWatcher);
        editTextDescription.addTextChangedListener(this.textWatcher);
        editTextDateYear.addTextChangedListener(this.textWatcher);
        editTextDateMonth.addTextChangedListener(this.textWatcher);
        editTextDateDay.addTextChangedListener(this.textWatcher);
        editTextComment.addTextChangedListener(this.textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            buttonAddGear.setEnabled(checkTextViewsForContent());
        }

        @Override
        public void afterTextChanged(Editable s) {}
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
        resultIntent.putExtra("GEAR", newGear);

        // If the intent passed an index, then we know that we are editing some gear and we
        // need to pass the index back so that the main activity knows which gear to replace
        int index = getIntent().getIntExtra("GEAR_INDEX", -1);
        if (index != -1) {
            resultIntent.putExtra("GEAR_INDEX", index);
        }

        setResult(RESULT_OK, resultIntent);
        finish();
    }
}