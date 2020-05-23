package project.tppa.megaconvertor;

import android.annotation.SuppressLint;
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

import java.text.DecimalFormat;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Pressure extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        setContentView(R.layout.activity_pressure);

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
                if(editText.getText().toString().length() != 0)
                    convertPressure();
                else
                    outputResult.setText("");
            }

        });


        fromSpinner = findViewById(R.id.fromSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_style, getResources().getStringArray(R.array.pressure));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        fromSpinner.setSelection(1);
        fromSpinner.setOnItemSelectedListener(this);

        toSpinner = findViewById(R.id.toSpinner);
        toSpinner.setAdapter(adapter);
        toSpinner.setSelection(2);
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
    private void convertPressure() {

        double newPressure = 0;
        double bars;

        bars = convertBars();
        String to = toSpinner.getSelectedItem().toString();

        if(to.equals("Pascals"))
            newPressure = bars*100000;
        if(to.equals("Bars"))
            newPressure = bars;
        if(to.equals("Torrs"))
            newPressure = bars*750.06;

        DecimalFormat precision = new DecimalFormat("#.##########");
        outputResult.setText(String.valueOf(precision.format(newPressure)));
    }

    private double convertBars() {

        double oldPressure =  0;
        double bars = 0;
        oldPressure = parseDouble(editText.getText().toString());

        String from = fromSpinner.getSelectedItem().toString();

        if(from.equals("Pascals"))
            bars = oldPressure/100000;
        if(from.equals("Bars"))
            bars = oldPressure;
        if(from.equals("Torrs"))
            bars = oldPressure/750.06;

        return bars;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.fromSpinner)
        {
            inputText.setText(adapterView.getSelectedItem().toString());
            try
            {
                convertPressure();
            }
            catch (Exception e)
            {}
        }

        if (adapterView.getId() == R.id.toSpinner)
        {
            outputText.setText(adapterView.getSelectedItem().toString());
            try
            {
                convertPressure();
            }
            catch (Exception e)
            {}
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}