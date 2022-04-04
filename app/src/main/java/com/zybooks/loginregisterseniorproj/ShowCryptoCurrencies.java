package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ShowCryptoCurrencies extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_crypto_currencies);
        getSupportActionBar().setTitle("All Crypto Currencies");
        textView=findViewById(R.id.showCryptoCurrenciesTextView);
        getCurrency();
    }

    public void getCurrency() {
        String currencyResult;
        String cryptoResult;
        StringBuilder currencysb = new StringBuilder();
        StringBuilder cryptosb = new StringBuilder();
        String cryptoCurrencySymbols = "symbols.json";
        String currencySymbols = "currencies.json";
        try {

            InputStream currencyInputStream = getResources().getAssets().open(currencySymbols);
            BufferedInputStream currencyBufferedInputStream = new BufferedInputStream(currencyInputStream);

            InputStreamReader currencyInputStreamReader = new InputStreamReader(currencyBufferedInputStream);
            BufferedReader currencyBufferedReader = new BufferedReader(currencyInputStreamReader);

            String inputLine = currencyBufferedReader.readLine();
            while (inputLine != null) {
                currencysb.append(inputLine);
                inputLine = currencyBufferedReader.readLine();
            }
            currencyResult = currencysb.toString();

            InputStream cryptoInputStream = getResources().getAssets().open(cryptoCurrencySymbols);
            BufferedInputStream cryptoBufferedInputStream = new BufferedInputStream(cryptoInputStream);

            InputStreamReader cryptoInputStreamReader = new InputStreamReader(cryptoBufferedInputStream);
            BufferedReader crytpoBufferedReader = new BufferedReader(cryptoInputStreamReader);

            String inputLine1 = crytpoBufferedReader.readLine();
            while (inputLine1 != null) {
                cryptosb.append(inputLine1);
                inputLine1 = crytpoBufferedReader.readLine();
            }
            cryptoResult = cryptosb.toString();

            createCurrencyList(currencyResult, cryptoResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createCurrencyList(String currency, String crypto) {
        ArrayList<CurrencyInformation> currencyInformations = new ArrayList<>();
        ArrayList<CurrencyInformation> cryptoInformation = new ArrayList<>();
        //read in data from local json filse
        try {
            JSONArray currencyRoot = new JSONArray(currency);
            for (int i = 0; i < currencyRoot.length(); i++) {
                JSONObject currencyJSON = currencyRoot.getJSONObject(i);
                currencyInformations.add(new CurrencyInformation(currencyJSON.getString("cc"), currencyJSON.getString("symbol"), currencyJSON.getString("name")));
            }
            JSONArray crytoRoot = new JSONArray(crypto);
            for (int i = 0; i < crytoRoot.length(); i++) {
                JSONObject currencyJSON = crytoRoot.getJSONObject(i);
                cryptoInformation.add(new CurrencyInformation(currencyJSON.getString("symbol"), currencyJSON.getString("usym"), currencyJSON.getString("name")));
            }

            //put data into lists
            StringBuilder allCryptos=new StringBuilder();
//            StringBuilder code= new StringBuilder();
//            StringBuilder symbols=new StringBuilder();
            for (CurrencyInformation currentCurrency : cryptoInformation
            ) {
                allCryptos.append(currentCurrency.getCurrencyName()+"\t"+currentCurrency.getCode()+"\n");
//                code.append(currentCurrency.getCode());
//                symbols.append(currentCurrency.getSymbol());
//                symbolNames.add(currentCurrency.getCurrencyName());
//                symbolCode.add(currentCurrency.getCode());
//                symbolSymbols.add(currentCurrency.getSymbol());
            }
            textView.setText(allCryptos.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}