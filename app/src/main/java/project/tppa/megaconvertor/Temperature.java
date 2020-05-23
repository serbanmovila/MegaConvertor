package project.tppa.megaconvertor;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.GenericArrayType;
import java.text.DecimalFormat;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Temperature extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView inputText;
    TextView outputText;
    TextView outputResult;

    EditText editText;

    Spinner fromSpinner;
    Spinner toSpinner;

    ImageButton switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        inputText = findViewById(R.id.inputText);
        outputText = findViewById(R.id.outputText);
        outputResult = findViewById(R.id.outputResult);

        editText = findViewById(R.id.inputEdit);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editText.getText().toString().length() != 0 && !editText.getText().toString().equals("-"))
                    convertTemperature();
                else
                    outputResult.setText("");
            }

        });


        fromSpinner = findViewById(R.id.fromSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_style, getResources().getStringArray(R.array.temperature));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        fromSpinner.setOnItemSelectedListener(this);

        toSpinner = findViewById(R.id.toSpinner);
        toSpinner.setAdapter(adapter);
        toSpinner.setSelection(1);
        outputText.setText(toSpinner.getSelectedItem().toString());
        toSpinner.setOnItemSelectedListener(this);

        switchButton = findViewById(R.id.switchButton);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int aux = fromSpinner.getSelectedItemPosition();
                fromSpinner.setSelection(toSpinner.getSelectedItemPosition());
                toSpinner.setSelection(aux);

            }
        });

    }

    @SuppressLint("DefaultLocale")
    private void convertTemperature() {

        double newTemp = 0;
        double celsius;

        celsius = convertCelsius();
        String to = toSpinner.getSelectedItem().toString();

        if(to.equals("Celsius"))
            newTemp = celsius;
        if(to.equals("Fahrenheit"))
            newTemp = celsius*9/5 + 32;
        if(to.equals("Kelvin"))
            newTemp = celsius + 273.15;

        DecimalFormat precision = new DecimalFormat("#.##");
        outputResult.setText(String.valueOf(precision.format(newTemp)));
    }

    private double convertCelsius() {

        double oldTemp = 0;
        double celsius = 0;
        oldTemp = parseDouble(editText.getText().toString());

        String from = fromSpinner.getSelectedItem().toString();

        if(from.equals("Celsius"))
            celsius = oldTemp;
        if(from.equals("Fahrenheit"))
            celsius = (oldTemp-32)*5/9;
        if(from.equals("Kelvin"))
            celsius = oldTemp - 273.15;

        return celsius;

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.fromSpinner)
        {
            inputText.setText(adapterView.getSelectedItem().toString());
            try
            {
                convertTemperature();
            }
            catch (Exception e)
            {}
        }

        if (adapterView.getId() == R.id.toSpinner)
        {
            outputText.setText(adapterView.getSelectedItem().toString());
            try
            {
                convertTemperature();
            }
            catch (Exception e)
            {}
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
