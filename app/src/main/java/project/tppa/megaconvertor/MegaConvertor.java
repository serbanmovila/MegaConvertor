package project.tppa.megaconvertor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MegaConvertor extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mega_convertor);

        RelativeLayout temperature = findViewById(R.id.temperatureLayout);
        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTemperature();
            }
        });

        RelativeLayout length = findViewById(R.id.lengthLayout);
        length.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLength();
            }
        });

        RelativeLayout time = findViewById(R.id.timeLayout);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTime();
            }
        });

        RelativeLayout weight = findViewById(R.id.weightLayout);
        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWeight();
            }
        });

        RelativeLayout pressure = findViewById(R.id.pressureLayout);
        pressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPressure();
            }
        });

        RelativeLayout currency = findViewById(R.id.currencyLayout);
        currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCurrency();
            }
        });

        downloadThread.start();

    }

    Thread downloadThread = new Thread() {

        @SuppressLint({"WrongConstant", "ShowToast"})
        public void run() {
            sharedPreferences = getSharedPreferences("currencyRates", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            try {

                Document doc = Jsoup.connect("https://www.x-rates.com/table/?from=EUR&amount=1").get();

                editor.putFloat("EUR", 1);

                String GBPRate = doc.select("#content > div:nth-child(1) > div > div.col2.pull-right.module.bottomMargin > " +
                        "div.moduleContent > table:nth-child(4) > tbody > tr:nth-child(2) > td:nth-child(2) > a").text();
                editor.putFloat("GBP", Float.parseFloat(GBPRate));

                String RONRate = doc.select("#content > div:nth-child(1) > div > div.col2.pull-right.module.bottomMargin > " +
                      "div.moduleContent > table.tablesorter.ratesTable > tbody > tr:nth-child(38) > td:nth-child(2) > a").text();
                editor.putFloat("RON", Float.parseFloat(RONRate));

                String CHFRate = doc.select("#content > div:nth-child(1) > div > div.col2.pull-right.module.bottomMargin > " +
                        "div.moduleContent > table:nth-child(4) > tbody > tr:nth-child(7) > td:nth-child(2) > a").text();
                editor.putFloat("CHF", Float.parseFloat(CHFRate));

                String USDRate = doc.select("#content > div:nth-child(1) > div > div.col2.pull-right.module.bottomMargin > " +
                        "div.moduleContent > table:nth-child(4) > tbody > tr:nth-child(1) > td:nth-child(2) > a").text();
                editor.putFloat("USD", Float.parseFloat(USDRate));

                editor.apply();

            }
            catch (Exception ignored) {
            }
        }
    };

    public void openTemperature(){
        Intent intent = new Intent(this, Temperature.class);
        startActivity(intent);
    }

    public void openLength(){
        Intent intent = new Intent(this, Length.class);
        startActivity(intent);
    }

    public void openTime(){
        Intent intent = new Intent(this, Time.class);
        startActivity(intent);
    }

    public void openWeight(){
        Intent intent = new Intent(this, Weight.class);
        startActivity(intent);
    }

    public void openPressure(){
        Intent intent = new Intent(this, Pressure.class);
        startActivity(intent);
    }

    public void openCurrency(){
        Intent intent = new Intent(this, Currency.class);
        startActivity(intent);
    }
}
