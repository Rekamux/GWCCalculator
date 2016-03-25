package com.gwc.ax.gwccalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
{
    // IDs of all the numeric buttons
    private int[] numericButtons = {R.id.buttonOne, R.id.buttonTwo, R.id.buttonThree, R.id.buttonFour, R.id.buttonFive, R.id.buttonSix, R.id.buttonSeven, R.id.buttonEight, R.id.buttonNine, R.id.buttonZero}; // The other numeric buttons will be added later
    // IDs of all the operator buttons
    private int[] operatorButtons = {}; // They will be added later
    // The ID of the "=" button
    private int equalButton = R.id.buttonEqual;
    // TextView used to display the output
    private TextView screenTextView;
    // The actual value of the expression
    private int screenValue;
    // The value of the pressed numeric button
    private int numericButtonValue;

    // Show the screenValue in the screenTextView
    private void updateScreenTextView()
    {
        screenTextView.setText(Integer.toString(screenValue));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Find the TextView
        screenTextView = (TextView) findViewById(R.id.textScreen);
        // The screen value starts at 0
        screenValue = 0;
        // Show the value on the screen
        updateScreenTextView();
        // The numeric button value is at 0
        numericButtonValue = 0;

        // All the numeric buttons use the NumericButtonListener listener
        NumericButtonListener numericButtonListener = new NumericButtonListener();
        for (int id : numericButtons)
        {
            findViewById(id).setOnClickListener(numericButtonListener);
        }

        EqualButtonListener equalButtonListener = new EqualButtonListener();
        findViewById(equalButton).setOnClickListener(equalButtonListener);
    }

    private class NumericButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            // We know that the View that has been clicked on is actually a Button
            Button button = (Button) v;
            // Gather the text shown by the button
            String text = button.getText().toString();
            // Extract the numeric value of that text and store it in the numeric button value
            numericButtonValue = Integer.valueOf(text);
            // Show it on the screen
            screenTextView.setText(text);
        }
    }

    private class EqualButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            // When you click on it, add the value above to the current value
            screenValue = screenValue + numericButtonValue;
            // Show the result on the screen
            updateScreenTextView();
        }
    }
}