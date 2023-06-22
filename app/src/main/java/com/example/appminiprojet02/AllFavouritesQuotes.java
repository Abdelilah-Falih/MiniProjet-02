package com.example.appminiprojet02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.appminiprojet02.Adapters.RvAdapter;
import com.example.appminiprojet02.Database.FavoutiteQuotesDatabase.FavouriteQuotesDB;
import com.example.appminiprojet02.Models.Quote;
import com.example.appminiprojet02.databinding.ActivityAllFavouritesQuotesBinding;

import java.util.ArrayList;

public class AllFavouritesQuotes extends AppCompatActivity {

    ActivityAllFavouritesQuotesBinding binding;
    View root;
    FavouriteQuotesDB database;
    RvAdapter adapter;
    ArrayList<Quote> quotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_favourites_quotes);

        binding = ActivityAllFavouritesQuotesBinding.inflate(getLayoutInflater());
        root = binding.getRoot();
        setContentView(root);

        database = new FavouriteQuotesDB(this);
        quotes = database.getAllQuotes();
        adapter = new RvAdapter(this, quotes);

        binding.rvFavourteQuotes.setAdapter(adapter);
        binding.rvFavourteQuotes.setLayoutManager(new LinearLayoutManager(this));






    }
}