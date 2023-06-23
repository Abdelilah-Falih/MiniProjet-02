package com.example.appminiprojet02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
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



        registerForContextMenu(binding.btnChangeStyleFavourite);





    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("chose a style for the layout ");
        menu.add(0, v.getId(), 0, "List");
        menu.add(0, v.getId(), 0, "Grid");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("List")) {
            binding.rvFavourteQuotes.setLayoutManager(new LinearLayoutManager(this));
        }else {
            binding.rvFavourteQuotes.setLayoutManager(new GridLayoutManager(this, 2));
        }
        return super.onContextItemSelected(item);
    }
}