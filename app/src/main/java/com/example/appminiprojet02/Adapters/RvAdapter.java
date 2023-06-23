package com.example.appminiprojet02.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.TextKeyListener;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appminiprojet02.Models.Quote;
import com.example.appminiprojet02.R;

import java.time.format.TextStyle;
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
        Spannable text_qoute = new SpannableString(quote.getQuote());
        Spannable text_author = new SpannableString(quote.getAuthor());

        text_qoute.setSpan(
                new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL
                ),
                0,
                text_qoute.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        text_qoute.setSpan(
                new ForegroundColorSpan(Color.BLACK)
                ,
                0,
                1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        text_qoute.setSpan(
                new RelativeSizeSpan(1.5f),
                0,
                1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        text_qoute.setSpan(
                new StyleSpan(Typeface.BOLD),
                0,
                1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        text_author.setSpan(
                new UnderlineSpan(),
                0,
                text_author.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        holder.tv_quote_favourite.setText(text_qoute);
        holder.tv_author_favourite.setText(text_author);
        holder.tv_id_favourite.setText("#"+quote.getId());
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
