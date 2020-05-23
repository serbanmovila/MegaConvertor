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

public class Time extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        setContentView(R.layout.activity_time);

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
                    convertTime();
                else
                    outputResult.setText("");
            }

        });


        fromSpinner = findViewById(R.id.fromSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_style, getResources().getStringArray(R.array.time));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        fromSpinner.setSelection(2);
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
    private void convertTime() {

        double newTime = 0;
        double hours;

        hours = convertHours();
        String to = toSpinner.getSelectedItem().toString();

        if(to.equals("Seconds"))
            newTime = hours*3600;
        if(to.equals("Minutes"))
            newTime = hours*60;
        if(to.equals("Hours"))
            newTime = hours;
        if(to.equals("Days"))
            newTime = hours / 24;
        if(to.equals("Weeks"))
            newTime = hours / 168;
        if(to.equals("Months"))
            newTime = hours / 720;
        if(to.equals("Years"))
            newTime = hours / 8760;

        DecimalFormat precision = new DecimalFormat("#.##########");
        outputResult.setText(String.valueOf(precision.format(newTime)));
    }

    private double convertHours() {

        double oldTime =  0;
        double hours = 0;
        oldTime = parseDouble(editText.getText().toString());

        String from = fromSpinner.getSelectedItem().toString();

        if(from.equals("Seconds"))
            hours = oldTime/3600;
        if(from.equals("Minutes"))
            hours = oldTime/60;
        if(from.equals("Hours"))
            hours = oldTime;
        if(from.equals("Days"))
            hours = oldTime * 24;
        if(from.equals("Weeks"))
            hours = oldTime * 168;
        if(from.equals("Months"))
            hours = oldTime * 720;
        if(from.equals("Years"))
            hours = oldTime * 8760;

        return hours;

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.fromSpinner)
        {
            inputText.setText(adapterView.getSelectedItem().toString());
            try
            {
                convertTime();
            }
            catch (Exception e)
            {}
        }

        if (adapterView.getId() == R.id.toSpinner)
        {
            outputText.setText(adapterView.getSelectedItem().toString());
            try
            {
                convertTime();
            }
            catch (Exception e)
            {}
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}