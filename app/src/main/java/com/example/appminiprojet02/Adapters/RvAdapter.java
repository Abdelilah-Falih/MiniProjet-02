package com.example.appminiprojet02.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appminiprojet02.Models.Quote;
import com.example.appminiprojet02.R;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    ArrayList<Quote> quotes;
    Context context;

    public RvAdapter(Context context, ArrayList<Quote> quotes){
        this.context = context;
        this.quotes = quotes;
    }
    @NonNull
    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favourite_quote, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.ViewHolder holder, int position) {
        Quote quote = quotes.get(position);
        holder.tv_quote_favourite.setText(quote.getQuote());
        holder.tv_author_favourite.setText(quote.getAuthor());
        holder.tv_id_favourite.setText("#"+quote.getId());
        if (position%2 == 0){
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_quote_favourite, tv_author_favourite, tv_id_favourite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_quote_favourite = itemView.findViewById(R.id.tv_quote_favourite);
            tv_author_favourite = itemView.findViewById(R.id.tv_author_favourite);
            tv_id_favourite = itemView.findViewById(R.id.tv_id_favourite);
        }
    }
}
