package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerFrom, spinnerTo;
    EditText inputValue;
    Button btnConvert;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerFrom = findViewById(R.id.spinnerform);
        spinnerTo = findViewById(R.id.spinnerto);
        inputValue = findViewById(R.id.inputValue);
        btnConvert = findViewById(R.id.btnConvert);
        resultText = findViewById(R.id.resultText);

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr = inputValue.getText().toString().trim();

                if (inputStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                double value = Double.parseDouble(inputStr);
                String from = spinnerFrom.getSelectedItem().toString();
                String to = spinnerTo.getSelectedItem().toString();

                double result = convertValue(from, to, value);

                if (result == -999999) {
                    resultText.setText("Invalid conversion");
                } else {
                    resultText.setText("Result: " + result);
                }
            }
        });
    }

    private double convertValue(String from, String to, double value) {

        if (from.equals(to)) {
            return value;
        }

        // Currency
        if (isCurrency(from) && isCurrency(to)) {
            double usdValue = toUSD(from, value);
            return fromUSD(to, usdValue);
        }

        // Fuel efficiency
        if (from.equals("mpg") && to.equals("km/L")) {
            return value * 0.425;
        }
        if (from.equals("km/L") && to.equals("mpg")) {
            return value / 0.425;
        }

        // Volume
        if (from.equals("gallon") && to.equals("liters")) {
            return value * 3.785;
        }
        if (from.equals("liters") && to.equals("gallon")) {
            return value / 3.785;
        }

        // Distance
        if (from.equals("nautical mile") && to.equals("kilometers")) {
            return value * 1.852;
        }
        if (from.equals("kilometers") && to.equals("nautical mile")) {
            return value / 1.852;
        }

        // Temperature
        if (from.equals("Celsius") && to.equals("Fahrenheit")) {
            return (value * 1.8) + 32;
        }
        if (from.equals("Fahrenheit") && to.equals("Celsius")) {
            return (value - 32) / 1.8;
        }
        if (from.equals("Celsius") && to.equals("Kelvin")) {
            return value + 273.15;
        }
        if (from.equals("Kelvin") && to.equals("Celsius")) {
            return value - 273.15;
        }
        if (from.equals("Fahrenheit") && to.equals("Kelvin")) {
            return ((value - 32) / 1.8) + 273.15;
        }
        if (from.equals("Kelvin") && to.equals("Fahrenheit")) {
            return ((value - 273.15) * 1.8) + 32;
        }

        return -999999;
    }

    private boolean isCurrency(String unit) {
        return unit.equals("USD") || unit.equals("AUD") || unit.equals("EUR")
                || unit.equals("JPY") || unit.equals("GBP");
    }

    private double toUSD(String from, double value) {
        switch (from) {
            case "USD":
                return value;
            case "AUD":
                return value / 1.55;
            case "EUR":
                return value / 0.92;
            case "JPY":
                return value / 148.50;
            case "GBP":
                return value / 0.78;
            default:
                return 0;
        }
    }

    private double fromUSD(String to, double value) {
        switch (to) {
            case "USD":
                return value;
            case "AUD":
                return value * 1.55;
            case "EUR":
                return value * 0.92;
            case "JPY":
                return value * 148.50;
            case "GBP":
                return value * 0.78;
            default:
                return 0;
        }
    }
}