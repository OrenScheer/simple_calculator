package com.example.simple_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnAdd, btnSubtract, btnMultiply, btnDivide, btnEquals, btnClear,
            btnBackspace, btnPlusMinus, btnCE, btnDecimal;
    TextView displayEquation, displayResult;
    float firstVal, secondVal;
    boolean add, sub, mul, div;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSubtract = (Button) findViewById(R.id.btnSubtract);
        btnMultiply = (Button) findViewById(R.id.btnMultiply);
        btnDivide = (Button) findViewById(R.id.btnDivide);
        btnEquals = (Button) findViewById(R.id.btnEquals);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnBackspace = (Button) findViewById(R.id.btnBackspace);
        btnPlusMinus = (Button) findViewById(R.id.btnPlusMinus);
        btnCE = (Button) findViewById(R.id.btnCE);
        btnPlusMinus = (Button) findViewById(R.id.btnPlusMinus);
        btnDecimal = (Button) findViewById(R.id.btnDecimal);

        displayEquation = (TextView) findViewById(R.id.displayEquation);
        displayResult = (TextView) findViewById(R.id.displayResult);

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToResultDisplay("0");
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToResultDisplay("1");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToResultDisplay("2");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               writeToResultDisplay("3");
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToResultDisplay("4");
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToResultDisplay("5");
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToResultDisplay("6");
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToResultDisplay("7");
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToResultDisplay("8");
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToResultDisplay("9");
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) { // "C" clears both the current number and the equation
                clearEquation();
                clearResult();
            }
        });
        btnCE.setOnClickListener(new View.OnClickListener() { // "CE" just clears the current number
            @Override
            public void onClick(View V) {
                clearResult();
            }
        });
        btnBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                String currentString = (String) displayResult.getText();
                if (currentString.equals("") || currentString.length() == 1) { // Backspacing nothing or a single digit gives 0
                    displayResult.setText("0");
                }
                else {
                    displayResult.setText(currentString.substring(0, currentString.length() - 1));
                }
            }
        });
        btnPlusMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                if (!displayResult.getText().equals("0")) { // Can't have negative zero
                    displayResult.setText("-" + displayResult.getText());
                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                addOperator('+');
            }
        });
        btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                addOperator('-');
            }
        });
        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                addOperator('×');
            }
        });
        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                addOperator('÷');
            }
        });
        btnDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                String currentResult = (String) displayResult.getText();
                String currentEquation = (String) displayEquation.getText();
                if (currentResult.equals("ERROR") || (currentEquation.length() > 0 && currentEquation.charAt(currentEquation.length() - 1) == '=')) {
                    // Can't put a decimal after an error or the previous answer, it should be a 0.something
                    currentResult = "0";
                    displayResult.setTextColor(Color.parseColor("#000000"));
                    clearEquation();
                }
                displayResult.setText(currentResult + "."); // Otherwise add the decimal point
            }
        });
        btnEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                addOperator('='); // End equation with an equals sign
                try {
                    float result = evaluate((String) displayEquation.getText());
                    if (Math.floor(result) == result) { // If it's a whole number
                        displayResult.setText(Integer.toString((int) result)); // Don't show the trailing .0
                    }
                    else {
                        displayResult.setText(Float.toString(result));
                    }
                }
                catch (Exception e) { // Most likely dividing by zero, but any other issue with the expression that evaluate() can't figure out will be caught
                    displayResult.setText("ERROR");
                    displayResult.setTextColor(Color.parseColor("#FF0000")); // Draw the user's attention by making it red
                }
            }
        });

    }
    private void writeToResultDisplay(String s) { // Called each time a single digit is pressed
        String currentText = (String) displayResult.getText();
        String currentEquation = (String) displayEquation.getText();
        if (currentText.equals("ERROR")) { // Restart from scratch if an error
            currentText = "0";
            displayResult.setTextColor(Color.parseColor("#000000"));
        }
        if (currentEquation.length() > 0 && currentEquation.charAt(currentEquation.length() - 1) == '=') {
            // If equals has been pressed and user presses digit instead of operator, start new equation
            clearEquation();
            currentText = "0";
        }
        if (currentText.equals("0")) { // If there is only a 0 currently in the result field
            displayResult.setText(s); // Then no need to have a leading 0
        }
        else {
            displayResult.setText(currentText + s); // Otherwise add to the end of the number on the screen
        }
    }
    private void clearEquation() {
        displayEquation.setText("");
    }
    private void clearResult() {
        displayResult.setText("0");
        displayResult.setTextColor(Color.parseColor("#000000")); // In case there was an error, revert text colour to black
    }
    private void addOperator(char operator) { // Called any time an operator is pressed
        String currentEquation = (String) displayEquation.getText();
        String currentNumber = (String) displayResult.getText();
        if (currentNumber.equals("ERROR")) {
            currentNumber = "0";
        }
        if (currentEquation.length() > 0 && currentEquation.charAt(currentEquation.length() - 1) == '=') {
            currentEquation = ""; // We're starting a new equation
        }
        if (currentNumber.charAt(0) == '-') {
            displayEquation.setText(currentEquation + "(" + currentNumber + ")" + operator); // For clarity, put negative numbers in brackets
        } else {
            displayEquation.setText(currentEquation + currentNumber + operator); // Add number on screen and operator to equation display
        }
        clearResult(); // Put a 0 back in the result field when waiting for the next number
    }
    private float evaluate(String equation) { // Called whenever the user presses =
        Stack<Float> operands = new Stack<Float>(); // Use two stacks to evaluate infix expression
        Stack<Character> operators = new Stack<Character>();
        int i = 0;
        while (i < equation.length() - 1) { // -1 since the last character will always be an equals sign
            char cur = equation.charAt(i);
            if (Character.isDigit(cur) || cur == '(') { // Calculator does not support brackets, but brackets are displayed around negative numbers
                int j = i; // See how many digits the number is
                if (cur == '(') j++; // Start parsing float from after the bracket
                while (j < equation.length() && (Character.isDigit(equation.charAt(j)) || equation.charAt(j) == '.' || (cur == '(' && equation.charAt(j) == '-'))) {
                    j++;
                }
                if (cur == '(') i++; // Convert string to float starting from after the bracket
                operands.push(Float.parseFloat(equation.substring(i, j)));
                if (cur == '(' && j < equation.length() && equation.charAt(j) == ')') j++; // Move past closing bracket;
                i = j; // Move main counter to one index past counter
            }
            else { // Operator
                while (!operators.isEmpty() && precedence(equation.charAt(i)) <= precedence(operators.peek())) { // If this operator is lower or the same precedence, keep performing operations
                        float operand2 = operands.pop();
                        float operand1 = operands.pop();
                        operands.push(calculate(operand1, operand2, operators.pop()));
                }
                operators.push(cur); // Add this operator to stack
                i++;
            }
        }
        while (!operators.isEmpty()) { // Do any remaining operations
            float operand2 = operands.pop();
            float operand1 = operands.pop();
            operands.push(calculate(operand1, operand2, operators.pop()));
        }
        return operands.pop(); // There will always be one last operand on the stack, which is the answer
    }
    private int precedence(char operator) { // Establish operator precedence
        if (operator == '+' || operator == '-') {
            return 1;
        }
        else if (operator == '*' || operator == '/' || operator == '×' || operator == '÷') { // Different symbols for future proofing
            return 2;
        }

        throw new IllegalArgumentException("This is not a valid operator.");
    }
    private float calculate(float operand1, float operand2, char operator) { // Perform a single operation
        if (operand2 == 0 && (operator == '/' || operator == '÷')) throw new ArithmeticException("divide by zero"); // Will be caught in equals OnClickListener() and error will be displayed
        if (operator == '+') return operand1 + operand2;
        else if (operator == '-') return operand1 - operand2;
        else if (operator == '*' || operator == '×') return operand1 * operand2;
        else if (operator == '/' || operator == '÷') return operand1 / operand2;

        throw new IllegalArgumentException("This is not a valid operator.");
    }
}