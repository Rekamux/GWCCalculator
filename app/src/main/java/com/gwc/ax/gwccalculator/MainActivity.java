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
    private int[] operatorButtons = {R.id.buttonPlus, R.id.buttonMinus, R.id.buttonTimes, R.id.buttonDivide};
    // The ID of the "=" button
    private int equalButton = R.id.buttonEqual;
    // TextView used to display the output
    private TextView screenTextView;
    // The value of the two operand
    private int operandValues[] = {0, 0};
    // The last pressed operator
    private String lastOperator = "+";
    // Which operand is being filled
    private int operandIndex;
    // Whether we have started populating the operand
    private boolean hasPopulatedOperand;
    // Whether the operand is negative
    private boolean isNegative;

    // Show the screenValue in the screenTextView
    private void updateScreenTextView(int screenValue)
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
        // Show 0 on the screen
        updateScreenTextView(0);
        // At first, we haven't input anything to the operands
        hasPopulatedOperand = false;
        // We'll start by filling the first operand
        operandIndex = 0;
        // By default the operand isn't negative
        isNegative = false;

        // All the numeric buttons use the NumericButtonListener listener
        NumericButtonListener numericButtonListener = new NumericButtonListener();
        for (int id : numericButtons)
        {
            findViewById(id).setOnClickListener(numericButtonListener);
        }

        // All the operator buttons use the OperatorButtonListener listener
        OperatorButtonListener operatorButtonListener = new OperatorButtonListener();
        for (int id : operatorButtons)
        {
            findViewById(id).setOnClickListener(operatorButtonListener);
        }

        // The equal sign uses the EqualButtonListener listener
        EqualButtonListener equalButtonListener = new EqualButtonListener();
        findViewById(equalButton).setOnClickListener(equalButtonListener);
    }

    private class NumericButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            // If we were showing a result, we need to replace it with the number
            if (!hasPopulatedOperand)
            {
                // We have now an input
                hasPopulatedOperand = true;
                // Make sure the new number we are going to fill is at zero
                operandValues[operandIndex] = 0;
            }
            // We know that the View that has been clicked on is actually a Button
            Button button = (Button) v;
            // Gather the text shown by the button
            String text = button.getText().toString();
            // Gather the int value of the button
            int value = Integer.valueOf(text);
            // If the operand is positive
            if (!isNegative)
            {
                // Math: when you add a digit at the right of an integer, you multiply it by 10 and add that digit
                operandValues[operandIndex] = operandValues[operandIndex] * 10 + value;
            }
            // Else if it's negative
            else
            {
                // Math: when you add a digit at the right of anegative integer, you multiply it by 10 and substract that digit
                operandValues[operandIndex] = operandValues[operandIndex] * 10 - value;
            }
            // Show the result on the screen
            updateScreenTextView(operandValues[operandIndex]);
        }
    }

    private class EqualButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            // It only works if we have populated the second operand
            if (hasPopulatedOperand && operandIndex == 1)
            {
                int result = operandValues[0];
                // If the last operator was +
                if (lastOperator.equals("+"))
                {
                    // We add the second value
                    result += operandValues[1];
                }
                // Else if it was -
                else if (lastOperator.equals("-"))
                {
                    // We substract the second value
                    result -= operandValues[1];
                }
                // Else if it was "*"
                else if (lastOperator.equals("*"))
                {
                    // We multiply by the second value
                    result *= operandValues[1];
                }
                // Else if it was "/"
                else if (lastOperator.equals("/"))
                {
                    // We divide by the second value
                    result /= operandValues[1];
                }
                // Show the result on the screen
                updateScreenTextView(result);
                // Select the first operand again
                operandIndex = 0;
                // We are done populating operands for now
                hasPopulatedOperand = false;
                // Back to assuming the number will be positive
                isNegative = false;
            }
        }
    }

    private class OperatorButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            // We know that the View that has been clicked on is actually a Button
            Button button = (Button) v;
            // Gather the text shown by the button
            String operator = button.getText().toString();
            // This click only works if we have populated the first number
            if (hasPopulatedOperand && operandIndex == 0)
            {
                // Store the operator in the lastOperator
                lastOperator = operator;
                // We select the second operand
                operandIndex = 1;
                // We have not populated it yet
                hasPopulatedOperand = false;
                // Back to assuming it's positive
                isNegative = false;
            }
            // If we have no input and the - operator, it's a change of sign
            else if (!hasPopulatedOperand && operator.equals("-"))
            {
                // Invert the sign
                isNegative = !isNegative;
                // If we are negative
                if (isNegative)
                {
                    // Show the minus sign on the screen
                    screenTextView.setText("-");
                }
                else
                {
                    // Remove the minus sign and show 0
                    screenTextView.setText("0");
                }
            }
        }
    }
}