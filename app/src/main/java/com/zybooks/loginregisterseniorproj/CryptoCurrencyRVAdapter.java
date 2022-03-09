package com.zybooks.loginregisterseniorproj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
//Jordan
public class CryptoCurrencyRVAdapter extends RecyclerView.Adapter<CryptoCurrencyRVAdapter.CurrencyViewholder> {
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private ArrayList<CryptoCurrencyModel> currencyModels;
    private Context context;

    public CryptoCurrencyRVAdapter(ArrayList<CryptoCurrencyModel> currencyModels, Context context) {
        this.currencyModels = currencyModels;
        this.context = context;
    }

    public void filterList(ArrayList<CryptoCurrencyModel> filterllist) {

        currencyModels = filterllist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CryptoCurrencyRVAdapter.CurrencyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.activity_crypto_currency_rv_adapter, parent, false);
        return new CryptoCurrencyRVAdapter.CurrencyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoCurrencyRVAdapter.CurrencyViewholder holder, int position) {

        CryptoCurrencyModel modal = currencyModels.get(position);
        holder.nameTV.setText(modal.getName());
        holder.rateTV.setText("$ " + df2.format(modal.getPrice()));
        holder.symbolTV.setText(modal.getSymbol());
    }

    @Override
    public int getItemCount() {

        return currencyModels.size();
    }


    public class CurrencyViewholder extends RecyclerView.ViewHolder {
        private TextView symbolTV, rateTV, nameTV;

        public CurrencyViewholder(@NonNull View itemView) {
            super(itemView);

            symbolTV = itemView.findViewById(R.id.idTVSymbol);
            rateTV = itemView.findViewById(R.id.idTVRate);
            nameTV = itemView.findViewById(R.id.idTVName);
        }
    }
}

