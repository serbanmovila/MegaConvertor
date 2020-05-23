package project.tppa.megaconvertor;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Currency extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView inputText;
    TextView outputText;
    TextView outputResult;

    EditText editText;

    Spinner fromSpinner;
    Spinner toSpinner;

    ImageButton switchButton;

    SharedPreferences sharedPreferences;

    ImageView fromFlag;
    ImageView toFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        inputText = findViewById(R.id.inputText);
        outputText = findViewById(R.id.outputText);
        outputResult = findViewById(R.id.outputResult);

        fromFlag = findViewById(R.id.fromFlag);
        toFlag = findViewById(R.id.toFlag);

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
                    convertCurrency();
                else
                    outputResult.setText("");
            }

        });


        fromSpinner = findViewById(R.id.fromSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_style, getResources().getStringArray(R.array.currency));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);

        fromSpinner.setSelection(1);
        changeFromFlag(fromSpinner.getSelectedItem().toString());

        fromSpinner.setOnItemSelectedListener(this);

        toSpinner = findViewById(R.id.toSpinner);
        toSpinner.setAdapter(adapter);

        toSpinner.setSelection(0);
        changeToFlag(toSpinner.getSelectedItem().toString());

        outputText.setText(toSpinner.getSelectedItem().toString());
        toSpinner.setOnItemSelectedListener(this);

        switchButton = findViewById(R.id.switchButton);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int aux = fromSpinner.getSelectedItemPosition();

                fromSpinner.setSelection(toSpinner.getSelectedItemPosition());
                toSpinner.setSelection(aux);
                changeFromFlag(fromSpinner.getSelectedItem().toString());
                changeToFlag(toSpinner.getSelectedItem().toString());

            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void convertCurrency() {

        double newCurrency = 0;
        double EUR;

        sharedPreferences = getSharedPreferences("currencyRates", MODE_APPEND);

        EUR = convertEUR();
        String to = toSpinner.getSelectedItem().toString();

        newCurrency = EUR*sharedPreferences.getFloat(to, 0);


        DecimalFormat precision = new DecimalFormat("#.######");
        outputResult.setText(String.valueOf(precision.format(newCurrency)));
    }

    private double convertEUR() {

        double oldCurrency =  0;
        double EUR = 0;

        oldCurrency = parseDouble(editText.getText().toString());

        String from = fromSpinner.getSelectedItem().toString();

        EUR = oldCurrency/sharedPreferences.getFloat(from, 0);

        return EUR;
    }

    public void changeFromFlag(String newFlag)
    {
        if(newFlag.equals("EUR"))
            fromFlag.setImageResource(R.drawable.euro);
        if(newFlag.equals("RON"))
            fromFlag.setImageResource(R.drawable.ron);
        if(newFlag.equals("GBP"))
            fromFlag.setImageResource(R.drawable.gbp);
        if(newFlag.equals("CHF"))
            fromFlag.setImageResource(R.drawable.chf);
        if(newFlag.equals("USD"))
            fromFlag.setImageResource(R.drawable.usd);
    }

    public void changeToFlag(String newFlag)
    {
        if(newFlag.equals("EUR"))
            toFlag.setImageResource(R.drawable.euro);
        if(newFlag.equals("RON"))
            toFlag.setImageResource(R.drawable.ron);
        if(newFlag.equals("GBP"))
            toFlag.setImageResource(R.drawable.gbp);
        if(newFlag.equals("CHF"))
            toFlag.setImageResource(R.drawable.chf);
        if(newFlag.equals("USD"))
            toFlag.setImageResource(R.drawable.usd);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.fromSpinner)
        {
            inputText.setText(adapterView.getSelectedItem().toString());
            changeFromFlag(fromSpinner.getSelectedItem().toString());
            try
            {
                convertCurrency();
            }
            catch (Exception e)
            {}
        }

        if (adapterView.getId() == R.id.toSpinner)
        {
            outputText.setText(adapterView.getSelectedItem().toString());
            changeToFlag(toSpinner.getSelectedItem().toString());
            try
            {
                convertCurrency();
            }
            catch (Exception e)
            {}
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}