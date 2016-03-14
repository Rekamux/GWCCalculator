package com.gwc.ax.gwccalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
{
    // IDs of all the numeric buttons
    private int[] numericButtons = {R.id.buttonOne}; // The other numeric buttons will be added later
    // IDs of all the operator buttons
    private int[] operatorButtons = {}; // They will be added later
    // TextView used to display the output
    private TextView screenTextView;
    // The actual value of the expression
    private int screenValue;

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

        // All the numeric buttons use the NumericButtonListener listener
        NumericButtonListener numericButtonListener = new NumericButtonListener();
        for (int id : numericButtons)
        {
            findViewById(id).setOnClickListener(numericButtonListener);
        }
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
            // Extract the numeric value of that text
            int buttonValue = Integer.valueOf(text);
            // Do math with the existing value
            screenValue = screenValue + buttonValue;
            // Show the result on the screen
            updateScreenTextView();
        }
    }
}