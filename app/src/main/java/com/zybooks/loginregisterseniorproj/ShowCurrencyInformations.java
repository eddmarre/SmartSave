package com.zybooks.loginregisterseniorproj;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//Eddie
public class ShowCurrencyInformations extends AppCompatActivity {
    Spinner fromCurrencySpinner, toCurrencySpinner;
    EditText fromCurrencyNumber, toCurrencyNumber;
    public String currentFrom, currentTo;
    public String currentFromCode;
    public String currentToCode;

    public ArrayList<String> symbolNames = new ArrayList<>();
    public ArrayList<String> symbolCode = new ArrayList<>();
    public ArrayList<String> symbolSymbols = new ArrayList<>();
    public ArrayList<String> symbolNamesAndCode =new ArrayList<>();

    public TextView errorTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_currency_informations);
        getSupportActionBar().setTitle("Currency Exchange");

        errorTextView=findViewById(R.id.errorText);

        fromCurrencySpinner = findViewById(R.id.fromCurrencyDropDown);
        toCurrencySpinner = findViewById(R.id.toCurrencyDropDown);

        fromCurrencyNumber = findViewById(R.id.fromCurrencyNumber);
        toCurrencyNumber = findViewById(R.id.toCurrencyNumber);
        toCurrencyNumber.setFocusable(false);

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
                currencyInformations.add(new CurrencyInformation(currencyJSON.getString("symbol"), currencyJSON.getString("usym"), currencyJSON.getString("name")));
            }

            //put data into lists
            for (CurrencyInformation currentCurrency : currencyInformations
            ) {
                symbolNames.add(currentCurrency.getCurrencyName());
                symbolCode.add(currentCurrency.getCode());
                symbolSymbols.add(currentCurrency.getSymbol());
                symbolNamesAndCode.add(currentCurrency.getCurrencyName()+" ("+currentCurrency.getCode()+")");
            }

            ArrayAdapter<String> toAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, symbolNamesAndCode);
            toCurrencySpinner.setAdapter(toAdapter);
            toCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //get current selection and append name to fit into api requirements
                    currentTo = toCurrencySpinner.getSelectedItem().toString();
                    String code=currentTo.substring(currentTo.lastIndexOf("("));
                    String updatedCode=code.replace("(","");
                    String finalCode=updatedCode.replace(")","");
                    currentToCode=finalCode.replace(" ","");
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            ArrayAdapter<String> fromAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, symbolNamesAndCode);
            fromCurrencySpinner.setAdapter(fromAdapter);

            fromCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //get current selection and append name to fit into api requirements
                    currentFrom = fromCurrencySpinner.getSelectedItem().toString();
                    String code=currentFrom.substring(currentFrom.lastIndexOf("("));
                    String updatedCode=code.replace("(","");
                    String finalcode =updatedCode.replace(")","");
                    currentFromCode=finalcode.replace(" ","");
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void convertCurrency(View view) {
        try {
            CrytproForeignExchangeSearchTask crytproForeignExchangeSearchTask = new CrytproForeignExchangeSearchTask();
            //Set from company & to company
            crytproForeignExchangeSearchTask.setFromCurrency(currentFromCode);
            crytproForeignExchangeSearchTask.setToCurrency(currentToCode);
            //Start api connection
            crytproForeignExchangeSearchTask.execute();
        } catch (Exception e) {

        }
    }

    public void flipConversion(View view) {
        //flips the data seen from left and right drop down menus
        int fromCurrencySelection=fromCurrencySpinner.getSelectedItemPosition();
        fromCurrencySpinner.setSelection(toCurrencySpinner.getSelectedItemPosition());
        toCurrencySpinner.setSelection(fromCurrencySelection);
    }


    public class CrytproForeignExchangeSearchTask extends AsyncTask<String, String, String> {
        String result;

        String fromCurrency = "";
        String toCurrency = "";
        //MUST INSERT YOUR OWN KEY TO USE
        //GET KEY FROM https://www.alphavantage.co/support/#api-key
        String APIKey = "50BZM4QNXYEYF7K3";

        public void setFromCurrency(String fromCurrency) {
            this.fromCurrency = fromCurrency;
        }

        public void setToCurrency(String toCurrency) {
            this.toCurrency = toCurrency;
        }

        //Start connecting to internet and retrieve data
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder sb = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                String uri = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=" + fromCurrency + "&to_currency=" + toCurrency + "&apikey=" + APIKey;
                url = new URL(uri);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String inputLine = bufferedReader.readLine();
                while (inputLine != null) {
                    sb.append(inputLine);
                    inputLine = bufferedReader.readLine();
                }
                result = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            //nonsense return because of String return type
            return sb.toString();
        }

        //after internet connection is established
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject root = new JSONObject(result);
                JSONObject foriengExchangeRate = root.getJSONObject("Realtime Currency Exchange Rate");
                //get information
                String fromCurrencyCode = foriengExchangeRate.getString("1. From_Currency Code");
                String fromCurrencyName = foriengExchangeRate.getString("2. From_Currency Name");
                String toCurrencyCode = foriengExchangeRate.getString("3. To_Currency Code");
                String toCurrecnyName = foriengExchangeRate.getString("4. To_Currency Name");
                String exchangeRate = foriengExchangeRate.getString("5. Exchange Rate");
                String lastRefreshed = foriengExchangeRate.getString("6. Last Refreshed");
                String timeZone = foriengExchangeRate.getString("7. Time Zone");
                String bidPrice = foriengExchangeRate.getString("8. Bid Price");
                String askPrice = foriengExchangeRate.getString("9. Ask Price");
                //Price conversion
                Float exchangeRateFloat = Float.parseFloat(exchangeRate);
                Float numberOwned = Float.parseFloat(fromCurrencyNumber.getText().toString());
                PriceConverterCalculator calculator = new PriceConverterCalculator(exchangeRateFloat, numberOwned);
                Float currentExchangeRate = calculator.calculatePrice();
                toCurrencyNumber.setText(currentExchangeRate.toString());
                errorTextView.setText("");

            } catch (Exception e) {
                e.printStackTrace();
                //text message for errors
                String errorString="can't convert from "+fromCurrency+" to "+toCurrency+".\n Try flipping searches or a different combination.";
                errorTextView.setText(errorString);
            }
        }
    }
}