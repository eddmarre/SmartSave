package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
//Eddie
public class UserCryptoWallet extends AppCompatActivity {
    EditText symbol, amountOwned;
    TextView output,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_crypto_wallet);
        getSupportActionBar().setTitle("Crypto Wallet");

        symbol = findViewById(R.id.walletInsertedCryptoSymbol);
        output = findViewById(R.id.currentCryptoPrice);
        amountOwned = findViewById(R.id.cyrptoAmountOwned);
        title=findViewById(R.id.myCryptoWallet);

        output.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);

        GetUserCryptoWallet();
    }

    public void insertWalletButton(View view) {
        CryptoWalletTask cryptoWalletTask = new CryptoWalletTask();
        //Set Search Symbol
        cryptoWalletTask.SetSymbol(symbol.getText().toString());
        //Start api connection
        cryptoWalletTask.execute();
    }


    public class CryptoWalletTask extends AsyncTask<String, String, String> {
        String result;
        String SYMBOL;
        //MUST INSERT YOUR OWN KEY TO USE
        //GET KEY FROM https://www.alphavantage.co/support/#api-key
        String APIKey = "50BZM4QNXYEYF7K3";

        public void SetSymbol(String symbol) {
            SYMBOL = symbol;
        }

        //Start connecting to internet and retrieve data
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder sb = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection = null;
            try {//"https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_DAILY&symbol=BTC&market=USD&apikey=50BZM4QNXYEYF7K3;
                String uri = "https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_DAILY&symbol=" + SYMBOL + "&market=USD&apikey=" + APIKey;
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
                // JSONObject metaData=root.getJSONObject("Meta Data");
                JSONObject timeSeriesDaily = root.getJSONObject("Time Series (Digital Currency Daily)");

//                String companySymbol=metaData.getString("2. Symbol");
//                String lastRefreshed=metaData.getString("3. Last Refreshed");
//                String timeZone=metaData.getString("5. Time Zone");
//
                JSONArray dates = timeSeriesDaily.names();

                //put all data into a list
                ArrayList<StockData> dailyStock = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < dates.length(); i++) {
                    String stockDate = dates.getString(i);
                    String openValue = timeSeriesDaily.getJSONObject(stockDate).getString("1a. open (USD)");
                    String highValue = timeSeriesDaily.getJSONObject(stockDate).getString("2a. high (USD)");
                    String lowValue = timeSeriesDaily.getJSONObject(stockDate).getString("3a. low (USD)");
                    String closeValue = timeSeriesDaily.getJSONObject(stockDate).getString("4a. close (USD)");
                    // String volumeValue=timeSeriesDaily.getJSONObject(stockDate).getString("5. volume");

                    float iOpenValue = Float.parseFloat(openValue);
                    float iHighValue = Float.parseFloat(highValue);
                    float iLowValue = Float.parseFloat(lowValue);
                    float iCloseValue = Float.parseFloat(closeValue);
                    // int iVolumeValue=Integer.parseInt(volumeValue);

                    //create a daily stock
                    dailyStock.add(new StockData(stockDate, iOpenValue, iHighValue, iLowValue, iCloseValue,/*iVolumeValue*/0));
                }
                //reverses data in chart to appear the proper way
                Collections.reverse(dailyStock);

                for (StockData stock :
                        dailyStock) {
                    sb.append(stock.toString());
                }

                //create company's information from data obtained
                Company currentCompany = new Company(SYMBOL, dailyStock);
                Float latestPrice = currentCompany.getLastStockPrice();
                Float actualAmountOwned = Float.parseFloat(amountOwned.getText().toString());
                InsertIntoCryptoWallet(SYMBOL, actualAmountOwned, latestPrice);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void InsertIntoCryptoWallet(String Symbol, float amountOwned, float LatestPrice) {
        //gets current real time, shows day of week, current time, and the calender date
        Date currentTime = Calendar.getInstance().getTime();

        SQLTableManager tableManager = new SQLTableManager(this);

        tableManager.InsertCryptoWallet(getUserName(), Symbol, amountOwned, LatestPrice, currentTime.toString());
        GetUserCryptoWallet();
    }

    private void GetUserCryptoWallet() {
        SQLTableManager tableManager = new SQLTableManager(this);
        //create temp data holder
        Cursor cryptoWalletInformation = tableManager.GetTableData("Account_User_Crypto_Wallet");
        //create a list to store all the data from the table
        ArrayList<myCryptoWallet> cryptoWallet = new ArrayList<>();
        //make sure table isnt empty
        if (cryptoWalletInformation.getCount() == 0) {

        } else {
            //if not empty try
            try {
                //reads all data from the database and compares to what we are looking for
                while (cryptoWalletInformation.moveToNext()) {
                    String user = cryptoWalletInformation.getString(0);
                    String cryptoSymbol = cryptoWalletInformation.getString(1);
                    Float amountOwned = cryptoWalletInformation.getFloat(2);
                    Float currentPrice = cryptoWalletInformation.getFloat(3);
                    String date = cryptoWalletInformation.getString(4);
                    cryptoWallet.add(new myCryptoWallet(user, cryptoSymbol, amountOwned, currentPrice, date));
                }
                //toss error if nothing was found in the data search
            } catch (Exception e) {

            }
        }
        //for every entry of the currently logged in user put it into a string
        StringBuilder sb= new StringBuilder();
        for (myCryptoWallet entry :
                cryptoWallet) {
            if (entry.user.equals(getUserName()))
            {
                sb.append(entry.toString());
            }
        }
        title.setVisibility(View.VISIBLE);
        output.setVisibility(View.VISIBLE);
        output.setText(sb.toString());
    }
    //get the logged in username
    private String getUserName() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String currentUserName = sharedPreferences.getString("text", "");
        return currentUserName;
    }

    //data storage class
    private class myCryptoWallet {
        String user;
        String symbol;
        float amountOwned;
        float currentPrice;
        String purchaseDate;

        public myCryptoWallet(String user, String symbol, float amountOwned, float currentPrice, String purchaseDate) {
            this.user = user;
            this.symbol = symbol;
            this.amountOwned = amountOwned;
            this.currentPrice = currentPrice;
            this.purchaseDate = purchaseDate;
        }

        @Override
        public String toString()
        {
            return symbol +"\namount owned:\t"+amountOwned+"\npurchase price:\t"+currentPrice+"\npurchase date: "+purchaseDate+"\n\n";
        }
    }
}