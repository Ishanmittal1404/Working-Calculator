package com.example.practical3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import java.util.Stack;

import com.example.practical3.R;
public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    @SuppressLint("StaticFieldLeak")
    private static EditText display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        display = findViewById(R.id.textView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private void text(String str) {
        String s = display.getText().toString();
        int cursor = display.getSelectionStart();
        if ("Enter".equals(s)) {
            display.setText(str);
            display.setSelection(0);
        } else {
            display.setText(String.format("%s%s", s, str));
            display.setSelection(cursor + 1);
        }
    }


    public void clearBtn(View view) {
        display.setText("");
    }

    public void leftBracket(View v) {
        text("(");
    }

    public void rightBracket(View v) {
        text(")");
    }

    public void divide(View v) {
        text("/");
    }

    public void multiply(View v) {
        text("*");
    }

    public void minus(View v) {
        text("-");
    }

    public void btn_add(View v) {
        text("+");
    }

    public void btn_zero(View v) {
        text("0");
    }

    public void btn_one(View v) {
        text("1");
    }

    public void btn_two(View v) {
        text("2");
    }

    public void btn_three(View v) {
        text("3");
    }

    public void btn_four(View v) {
        text("4");
    }

    public void btn_five(View v) {
        text("5");
    }

    public void btn_six(View v) {
        text("6");
    }

    public void btn_seven(View v) {
        text("7");
    }

    public void btn_eight(View v) {
        text("8");
    }

    public void btn_nine(View v) {
        text("9");
    }

    public void btn_dot(View v) {
        text(".");
    }

    public void equals(View v) {

        String expression = display.getText().toString();
        expression = expression.replaceAll("÷", "/");
        expression = expression.replaceAll("x", "*");

        char[] tokens = expression.toCharArray();
        Stack<Float> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < tokens.length; i++)
        {

            if (tokens[i] == ' ')
                continue;

            if (tokens[i] >= '0' && tokens[i] <= '9')
            {
                StringBuilder sbuf = new StringBuilder();

                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9') {
                    sbuf.append(tokens[i++]);
                }
                values.push((float)Integer.parseInt(sbuf.toString()));
                i--;
            }


            else if (tokens[i] == '(') {
                ops.push(tokens[i]);
            }

            else if (tokens[i] == ')')
            {
                while (ops.peek() != '(') values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.pop();
            }

            else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/')
            {
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                ops.push(tokens[i]);
            }
        }

        while (!ops.empty())
            values.push(applyOp(ops.pop(),
                    values.pop(),
                    values.pop()));

        String op = values.pop().toString();
        if(op.contains(".0") && op.indexOf(".")==op.length()-2)
        {
            int idx=op.indexOf(".");
            op=op.substring(0, idx);
        }
        display.setText(op);
        display.setSelection(display.getText().length());

    }

    public static boolean hasPrecedence(char op1, char op2)
    {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        return (op1 != '*' && op1 != '/') ||
                (op2 != '+' && op2 != '-');
    }
    public static float applyOp(char op, float b, float a)
    {
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if(b!=0)
                    return a / b;
        }
        return 0;
    }

}