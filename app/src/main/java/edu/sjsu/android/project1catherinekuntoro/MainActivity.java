package edu.sjsu.android.project1catherinekuntoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText principleEditText;
    private TextView resultTextView;
    private TextView valueInterestTextView;

    private SeekBar interestRateSeekBar;

    private RadioButton years15RadioButton;
    private RadioButton years20RadioButton;
    private RadioButton years30RadioButton;

    private CheckBox addTaxCheckBox;

    //Attributes that will be passed on to the CalculateMortgage class
    double principle;
    double interest;
    int years;
    boolean addTaxOrNot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Principal value -------------------------------------------------
        principleEditText = findViewById(R.id.principle_editText);


        //Result edit text initialize
        resultTextView = findViewById(R.id.result_textView);
        //resultTextView.setText("Press CALCULATE for monthly payments.");

        //Interest Rate SeekBar -------------------------------------------------
        valueInterestTextView = findViewById(R.id.value_interest_rate_textView);

        interestRateSeekBar = findViewById(R.id.interest_rate_seekBar);
        interestRateSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            //Invoked whenever any changes are made on the SeekBar
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //To make the result into one decimal place, incrementing by 0.1
                double result = Double.valueOf(progress) / 10.0;
                interest = result;
                valueInterestTextView.setText(new StringBuilder().append(String.valueOf(result)).append("%").toString());
            }

            @Override
            //Invoked at the start of the user's touch event. Automatically called when the thumb drags the SeekBar
            public void onStartTrackingTouch(SeekBar seekBar) { //Not needed
            }

            @Override
            //Automatically invoked at the end of user touch event.
            public void onStopTrackingTouch(SeekBar seekBar) { //Not needed
            }
        });
        //Years -------------------------------------------------
        years15RadioButton = findViewById(R.id.years_15_radioButton);
        years20RadioButton = findViewById(R.id.years_20_radioButton);
        years30RadioButton = findViewById(R.id.years_30_radioButton);

        years15RadioButton.setChecked(true);

         //Add interest check box
         addTaxCheckBox = findViewById(R.id.include_tax_checkBox);
    }

    //Uninstall -------------------------------------------------
    public void onClickUninstall(View view) {
        Intent delete = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + getPackageName()));
        startActivity(delete);
    }

    //Results -------------------------------------------------
    public void onClickCalculate(View view) {

        //Principle; including the two input validations
        if (principleEditText.length() == 0) {
            //If the user didn't write any value
            resultTextView.setText("Please enter the principle.\n" + "Then press CALCULATE for monthly payments");
            Toast.makeText(this, "Invalid Input!",
                    Toast.LENGTH_LONG).show();
            return;

        }
        //Checking for two decimal places, if there is any decimal numbers
        String principleInput = principleEditText.getText().toString();
        if(principleInput.contains(".")){

            char secondLastCharacter = principleInput.charAt(principleInput.length()-2);
            char thirdLastCharacter = principleInput.charAt(principleInput.length()-3);

            //If the number is two or one decimal places, the period will be at the second or third last character
            if(secondLastCharacter == '.' || thirdLastCharacter == '.'){
                //Valid answer; within 2 decimal places
                principle = Double.parseDouble(principleInput);
            } else {
                //The input is not within two decimal places
                resultTextView.setText("Please enter a valid number. 2 decimal digits max\n" +
                        "Then press CALCULATE for monthly payments");
                Toast.makeText(this, "Invalid Input!",
                        Toast.LENGTH_LONG).show();
                return;
            }
        //For non-decimal inputs
        } else {
            principle = Double.parseDouble(principleInput);
        }

        //Interest
        String interestText= valueInterestTextView.getText().toString();
        String numbersOnly = interestText.substring(0 , interestText.length()-1); //to remove the % when parsing for double
        interest = Double.parseDouble(numbersOnly);

        //Years
        if (years15RadioButton.isChecked()) {
            years = 15;
        } else if (years20RadioButton.isChecked()) {
            years = 20;
        } else if (years30RadioButton.isChecked()) {
            years = 30;
        }

        //Add tax or not
        if (addTaxCheckBox.isChecked()) {
            addTaxOrNot = true;
        } else {
            addTaxOrNot = false;
        }

        CalculateMortgage cm = new CalculateMortgage(principle, interest, years);
        //Rounding the results
        double result = cm.calculateMortgage(addTaxOrNot);
        double rounded = (double) Math.round(result * 100.0) / 100.0;

        resultTextView.setText(new StringBuilder().append("Monthly payment: ").append(rounded).toString());

    }


}