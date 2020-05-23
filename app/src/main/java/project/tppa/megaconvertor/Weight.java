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

public class Weight extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        setContentView(R.layout.activity_weight);

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
                    convertWeight();
                else
                    outputResult.setText("");
            }

        });


        fromSpinner = findViewById(R.id.fromSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_style, getResources().getStringArray(R.array.weight));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        fromSpinner.setSelection(4);
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
    private void convertWeight() {

        double newWeight = 0;
        double kilograms;

        kilograms = convertKilograms();
        String to = toSpinner.getSelectedItem().toString();

        if(to.equals("Grams"))
            newWeight = kilograms*1000;
        if(to.equals("Kilograms"))
            newWeight = kilograms;
        if(to.equals("Tonnes"))
            newWeight = kilograms/1000;
        if(to.equals("Ounces"))
            newWeight = kilograms / 0.02834952;
        if(to.equals("Pounds"))
            newWeight = kilograms / 0.45359237;
        if(to.equals("Stones"))
            newWeight = kilograms / 6.35029318;

        DecimalFormat precision = new DecimalFormat("#.######");
        outputResult.setText(String.valueOf(precision.format(newWeight)));
    }

    private double convertKilograms() {

        double oldWeight =  0;
        double kilograms = 0;
        oldWeight = parseDouble(editText.getText().toString());

        String from = fromSpinner.getSelectedItem().toString();

        if(from.equals("Grams"))
            kilograms = oldWeight/1000;
        if(from.equals("Kilograms"))
            kilograms = oldWeight;
        if(from.equals("Tonnes"))
            kilograms = oldWeight*1000;
        if(from.equals("Ounces"))
            kilograms = oldWeight * 0.02834952;
        if(from.equals("Pounds"))
            kilograms = oldWeight * 0.45359237;
        if(from.equals("Stones"))
            kilograms = oldWeight * 6.35029318;

        return kilograms;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.fromSpinner)
        {
            inputText.setText(adapterView.getSelectedItem().toString());
            try
            {
                convertWeight();
            }
            catch (Exception e)
            {}
        }

        if (adapterView.getId() == R.id.toSpinner)
        {
            outputText.setText(adapterView.getSelectedItem().toString());
            try
            {
                convertWeight();
            }
            catch (Exception e)
            {}
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}