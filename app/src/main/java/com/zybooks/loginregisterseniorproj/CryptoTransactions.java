//Jordan
package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class CryptoTransactions extends AppCompatActivity {
public EditText _btcvalueid;
public EditText _recipientID;
public EditText _makeReadOnly;
public EditText _descid;
public EditText _usdvalueid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_transactions);

      _btcvalueid = findViewById(R.id.btcvalueid);
      _recipientID = findViewById(R.id.recipientid);
      _descid = findViewById(R.id.descid);
      _usdvalueid = findViewById(R.id.usdvalueid);
      _makeReadOnly = findViewById(R.id.usdvalueid);
      _makeReadOnly.setFocusable(false);


      _btcvalueid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) // Updates live data
            {
            _usdvalueid.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                CryptoConverter _crpytoconverter = new CryptoConverter();
//                _crpytoconverter.setFromCurrency("BTC");
//                _crpytoconverter.setToCurrency("USD");
//                _crpytoconverter.execute();
            }
        });

    }

    public void convertonclick(View view) {
        CryptoConverter _crpytoconverter = new CryptoConverter();
        _crpytoconverter.setFromCurrency("BTC");
        _crpytoconverter.setToCurrency("USD");
        _crpytoconverter.execute();
    }

    public class CryptoConverter extends AsyncTask<String, String, String> {
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
                Float numberOwned = Float.parseFloat(_btcvalueid.getText().toString());
                PriceConverterCalculator calculator = new PriceConverterCalculator(exchangeRateFloat, numberOwned);
                Float currentExchangeRate = calculator.calculatePrice();
                double cutnums = (double)currentExchangeRate;
                String cutstring = cutnums + "";
                cutstring = new DecimalFormat("#.0#").format(cutnums);
                String cutstr2 = cutstring.substring(0,5);
                _usdvalueid.setText("$" + cutstring);

            } catch (Exception e) {
                e.printStackTrace();
                //text message for errors
                String errorString="can't convert from "+fromCurrency+" to "+toCurrency+".\n Try flipping searches or a different combination.";
            }
        }
    }
    public void doSend(View view) {

        Intent intent=new Intent(CryptoTransactions.this,CryptoTransactionsList.class);
        intent.putExtra("BTCVal",_btcvalueid.getText().toString());
        intent.putExtra("Recip",_recipientID.getText().toString());
        intent.putExtra("DescID",_descid.getText().toString());
        intent.putExtra("UsdID",_usdvalueid.getText().toString());
        startActivity(intent);
    }


}