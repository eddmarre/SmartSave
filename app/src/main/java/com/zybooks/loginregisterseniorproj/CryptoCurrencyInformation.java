package com.zybooks.loginregisterseniorproj;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CryptoCurrencyInformation extends AppCompatActivity {
    TextView text;
    CandleStickChart chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_currency_information);

        getSupportActionBar().setTitle("Daily Crypto");
       // Get data sent over through activity intent
        Bundle extras=getIntent().getExtras();
        String symbol="";
        if(extras!=null)
        {
            symbol=extras.getString("SYMBOL");
            text=findViewById(R.id.testText);
            text.setText(symbol);
        }

        chart=(CandleStickChart) findViewById(R.id.cryptoChart);

        //Create new async task
        CryptoSearchTask cryptoSearchTask=new CryptoSearchTask();
        //Set Search Symbol
        cryptoSearchTask.SetSymbol(symbol);
        //Start api connection
        cryptoSearchTask.execute();
    }

    //Eddie
    public class CryptoSearchTask extends AsyncTask<String,String,String>
    {
        String result;
        String SYMBOL;
        //MUST INSERT YOUR OWN KEY TO USE
        //GET KEY FROM https://www.alphavantage.co/support/#api-key
        String APIKey="50BZM4QNXYEYF7K3";

        public void SetSymbol(String symbol)
        {
            SYMBOL=symbol;
        }

        //Start connecting to internet and retrieve data
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder sb = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection = null;
            try {//"https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_DAILY&symbol=BTC&market=USD&apikey=50BZM4QNXYEYF7K3;
                String uri = "https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_DAILY&symbol="+SYMBOL+"&market=USD&apikey="+APIKey;
                url= new URL(uri);
                urlConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String inputLine = bufferedReader.readLine();
                while (inputLine != null) {
                    sb.append(inputLine);
                    inputLine = bufferedReader.readLine();
                }
                result= sb.toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally {
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
                JSONObject timeSeriesDaily=root.getJSONObject("Time Series (Digital Currency Daily)");

//                String companySymbol=metaData.getString("2. Symbol");
//                String lastRefreshed=metaData.getString("3. Last Refreshed");
//                String timeZone=metaData.getString("5. Time Zone");
//
                JSONArray dates =timeSeriesDaily.names();

                //put all data into a list
                ArrayList<StockData> dailyStock = new ArrayList<>();
                StringBuilder sb=new StringBuilder();
                for (int i=0;i<dates.length();i++)
                {
                    String stockDate=dates.getString(i);
                    String openValue=timeSeriesDaily.getJSONObject(stockDate).getString("1a. open (USD)");
                    String highValue=timeSeriesDaily.getJSONObject(stockDate).getString("2a. high (USD)");
                    String lowValue=timeSeriesDaily.getJSONObject(stockDate).getString("3a. low (USD)");
                    String closeValue=timeSeriesDaily.getJSONObject(stockDate).getString("4a. close (USD)");
                   // String volumeValue=timeSeriesDaily.getJSONObject(stockDate).getString("5. volume");

                    float iOpenValue= Float.parseFloat(openValue);
                    float iHighValue=Float.parseFloat(highValue);
                    float iLowValue=Float.parseFloat(lowValue);
                    float iCloseValue=Float.parseFloat(closeValue);
                   // int iVolumeValue=Integer.parseInt(volumeValue);

                    //create a daily stock
                    dailyStock.add(new StockData(stockDate,iOpenValue,iHighValue,iLowValue,iCloseValue,/*iVolumeValue*/0));
                }
                for (StockData stock :
                        dailyStock) {
                    sb.append(stock.toString());
                }
                //text.setText(sb.toString());
                //create company's information from data obtained
                Company currentCompany= new Company(SYMBOL,dailyStock);
                //populate the chart with the company's data
               setCandleStickChart(currentCompany);
            } catch (Exception e) {
                e.printStackTrace();
                text.setText("error setting data");
            }
        }
    }
    //Eddie
    public void setCandleStickChart(Company company)
    {
        ArrayList<StockData> companyStock=company.getCompanyStockPrices();
        //gets all daily dates for company stock, shortens date to format MM-DD and adds it to list
        ArrayList<String> stockDate=new ArrayList<>();
        for (StockData stock:companyStock
        ) {
            String date=stock.getDateTime().substring(5);
            stockDate.add(date);
        }
        //X values for the candle chart
        ValueFormatter valueFormatter =new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return stockDate.get((int) value);
            }
        };
        //Creates the graph entries from the api
        ArrayList<CandleEntry> candleEntries=new ArrayList<>();
        int i=0;
        for (StockData stock:companyStock
        ) {
            candleEntries.add(new CandleEntry(i,stock.getHigh(),stock.getLow(),stock.getOpen(),stock.getClose()));
            i++;
        }

        //sets up the chart's appearance and makes the company symbol the chart's label
        CandleDataSet candleDataSet=new CandleDataSet(candleEntries,company.getCompanySymbol());
        candleDataSet.setColor(Color.WHITE);
        candleDataSet.setShadowColor(Color.BLACK);
        candleDataSet.setShadowWidth(.8f);
        candleDataSet.setDecreasingColor(Color.RED);
        candleDataSet.setDecreasingPaintStyle(Paint.Style.FILL);
        candleDataSet.setIncreasingColor(Color.GREEN);
        candleDataSet.setIncreasingPaintStyle(Paint.Style.FILL);
        candleDataSet.setNeutralColor(Color.WHITE);
        candleDataSet.setValueTextColor(Color.WHITE);
        candleDataSet.setDrawValues(false);


        //actually populates the chart with the appearance settings
        CandleData candleData= new CandleData(candleDataSet);
        chart.setData(candleData);
        chart.setBackgroundColor(Color.TRANSPARENT);
        chart.animateXY(1000,1000);

        chart.getXAxis().setGranularity(1f);
        chart.getXAxis().setValueFormatter(valueFormatter);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getXAxis().setTextSize(15f);

        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getAxisLeft().setGranularity(.5f);
        chart.getAxisLeft().setLabelCount(5);
        chart.getAxisLeft().setTextSize(15f);

        chart.getAxisRight().setEnabled(false);

        chart.getLegend().setTextColor(Color.WHITE);
        chart.getLegend().setTextSize(15f);

        Description stockDescription=new Description();
        stockDescription.setText("Daily Stock Value");
        stockDescription.setTextColor(Color.WHITE);
        stockDescription.setTextSize(15f);
        chart.setDescription(stockDescription);
    }
}