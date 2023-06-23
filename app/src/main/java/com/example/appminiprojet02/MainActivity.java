package com.example.appminiprojet02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appminiprojet02.Database.FavoutiteQuotesDatabase.FavouriteQuotesDB;
import com.example.appminiprojet02.Models.Quote;
import com.example.appminiprojet02.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding ;
    View root ;
    SharedPreferences sharedPreferences;
    FavouriteQuotesDB database;
    String[] colors_names;
    String[] colors_codes;
    int whichColorPosition;

    boolean isLiked ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        root = binding.getRoot();
        setContentView(root);
        sharedPreferences = getSharedPreferences("favorite-quotes",MODE_PRIVATE);
        database = new FavouriteQuotesDB(this);

        colors_names = getResources().getStringArray(R.array.colors_label);
        colors_codes = getResources().getStringArray(R.array.colors_cods);

        int quote_id = sharedPreferences.getInt("_id", -1);
        if(quote_id == -1){
            loadQuote();
        }else {
            String quote = sharedPreferences.getString("quote",null);
            String author = sharedPreferences.getString("author",null);
            binding.tvIdQuote.setText("#"+quote_id);
            binding.tvQuoteMain.setText(quote);
            binding.tvAuthorMain.setText(author);
            binding.tbPinUnpinMain.setChecked(true);
            isLiked = !database.isFavourite(quote_id);
            changeImage();
        }

        binding.btnShowFavouriteQuotes.setOnClickListener(v->{
            startActivity(new Intent(this, AllFavouritesQuotes.class));
        });

        binding.tbPinUnpinMain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                int id = Integer.parseInt(binding.tvIdQuote.getText().toString().substring(1));
                String quote = binding.tvQuoteMain.getText().toString();
                String author = binding.tvAuthorMain.getText().toString();
                if(isChecked){
                    editor.putInt("_id", id);
                    editor.putString("quote", quote);
                    editor.putString("author", author);
                    database.addQuote(new Quote(id,quote, author));
                    isLiked = true;
                    binding.ibLikeDeslike.setImageResource(R.drawable.ic_like);
                }else{
                    editor.clear();
                }
                editor.apply();
            }
        });


        binding.ibLikeDeslike.setOnClickListener(v->{
            int id = Integer.parseInt(binding.tvIdQuote.getText().toString().substring(1));
            if(isLiked) {
                database.deleteQuote(id);
                if(binding.tbPinUnpinMain.isChecked()){
                    binding.tbPinUnpinMain.setChecked(false);
                }
            }
            else {
                String quote_text = binding.tvQuoteMain.getText().toString();
                String quote_author = binding.tvAuthorMain.getText().toString();
                database.addQuote(new Quote(id, quote_text, quote_author));
            }
            changeImage();
        });

        binding.ivChangeLayoutBg.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("chose a color");
            builder.setItems(new CharSequence[]{"Default", "LightSalmon", "Plum", "PaleGreen", "CornflowerBlue"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    root.setBackgroundColor(Color.parseColor(colors_codes[which]));
                }
            });
            


            builder.show();
        });


        registerForContextMenu(binding.ivChangeLayoutBg);
    }

    void changeImage(){
        if(isLiked) binding.ibLikeDeslike.setImageResource(R.drawable.ic_deslike);
        else binding.ibLikeDeslike.setImageResource(R.drawable.ic_like);
        isLiked = !isLiked;
    }



    private void loadQuote(){
        RequestQueue queue = Volley.newRequestQueue(this);
       int x =  new Random().nextInt(4)+95;
        String url = "https://dummyjson.com/quotes/random";

        JsonObjectRequest jsonObject = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int id =response.getInt("id");
                    binding.tvIdQuote.setText("#"+id);
                    binding.tvQuoteMain.setText(response.getString("quote"));
                    binding.tvAuthorMain.setText(response.getString("author"));
                    isLiked = !database.isFavourite(id);
                    changeImage();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonObject);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        root.setBackgroundColor(Color.parseColor(colors_codes[item.getGroupId()]));
        item.setEnabled(false);
        whichColorPosition = item.getGroupId();
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("chose a color");
        int id = 0;

        for (String color: colors_names) {
            menu.add(id, v.getId(), 0, color);
            id++;
        }

    }
}