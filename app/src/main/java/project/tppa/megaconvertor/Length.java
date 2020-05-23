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

public class Length extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        setContentView(R.layout.activity_length);

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
                    convertLength();
                else
                    outputResult.setText("");
            }

        });


        fromSpinner = findViewById(R.id.fromSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_style, getResources().getStringArray(R.array.length));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        fromSpinner.setSelection(1);
        fromSpinner.setOnItemSelectedListener(this);

        toSpinner = findViewById(R.id.toSpinner);
        toSpinner.setAdapter(adapter);
        toSpinner.setSelection(4);
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
    private void convertLength() {

        double newLength = 0;
        double metres;

        metres = convertMetres();
        String to = toSpinner.getSelectedItem().toString();

        if(to.equals("Centimeters"))
            newLength = metres/100;
        if(to.equals("Metres"))
            newLength = metres;
        if(to.equals("Kilometres"))
            newLength = metres/1000;
        if(to.equals("Inches"))
            newLength = metres / 0.0254;
        if(to.equals("Feet"))
            newLength = metres / 0.3048;
        if(to.equals("Yards"))
            newLength = metres * 1.0936;
        if(to.equals("Miles"))
            newLength = metres * 0.00062137;

        DecimalFormat precision = new DecimalFormat("#.########");
        outputResult.setText(String.valueOf(precision.format(newLength)));
    }

    private double convertMetres() {

        double oldLength = 0;
        double metres = 0;
        oldLength = parseDouble(editText.getText().toString());

        String from = fromSpinner.getSelectedItem().toString();

        if(from.equals("Centimeters"))
            metres = oldLength/100;
        if(from.equals("Metres"))
            metres = oldLength;
        if(from.equals("Kilometres"))
            metres = oldLength*1000;
        if(from.equals("Inches"))
            metres = oldLength * 0.0254;
        if(from.equals("Feet"))
            metres = oldLength * 0.3048;
        if(from.equals("Yards"))
            metres = oldLength / 1.0936;
        if(from.equals("Miles"))
            metres = oldLength / 0.00062137;

        return metres;

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.fromSpinner)
        {
            inputText.setText(adapterView.getSelectedItem().toString());
            try
            {
                convertLength();
            }
            catch (Exception e)
            {}
        }

        if (adapterView.getId() == R.id.toSpinner)
        {
            outputText.setText(adapterView.getSelectedItem().toString());
            try
            {
                convertLength();
            }
            catch (Exception e)
            {}
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}