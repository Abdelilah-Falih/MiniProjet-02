package com.example.appminiprojet02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appminiprojet02.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding ;
    View root ;
    SharedPreferences sharedPreferences;
    ArrayList<String> colors_label = new ArrayList<>();
    String[] colors_values = {"#FFFFFFFF","#FFA07A", "#DDA0DD", "#98FB98", "#6495ED"};
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        root = binding.getRoot();
        setContentView(root);

        sharedPreferences = getSharedPreferences("favorite-quotes",MODE_PRIVATE);

        colors_label.add("Default");
        colors_label.add("LightSalmon");
        colors_label.add("Plum");
        colors_label.add("PaleGreen");
        colors_label.add("CornflowerBlue");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colors_label);
        binding.spinnerMain.setAdapter(adapter);

        int bgColorIndex = sharedPreferences.getInt("bg-color-index", 0);
        if(bgColorIndex != 0){
            binding.getRoot().setBackgroundColor(Color.parseColor(colors_values[bgColorIndex]));
            binding.spinnerMain.setSelection(bgColorIndex, true);
        }


        binding.spinnerMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String backgroundColor = colors_values[position];
                    binding.getRoot().setBackgroundColor(Color.parseColor(backgroundColor));
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("bg-color-index", position);
                    editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        String quote = sharedPreferences.getString("quote", null);
        if(quote == null){
            loadQuote();
        }else {
            String author = sharedPreferences.getString("author",null);
            binding.tvQuoteMain.setText(quote);
            binding.tvAuthorMain.setText(author);
            binding.tbPinUnpinMain.setChecked(true);
        }

        binding.tbPinUnpinMain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(isChecked){
                    editor.putString("quote", binding.tvQuoteMain.getText().toString());
                    editor.putString("author", binding.tvAuthorMain.getText().toString());
                }else{
                    editor.remove("quote");
                    editor.remove("author");
                }
                editor.apply();
            }
        });






    }



    private void loadQuote(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://dummyjson.com/quotes/random";

        JsonObjectRequest jsonObject = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    binding.tvQuoteMain.setText(response.getString("quote"));
                    binding.tvAuthorMain.setText(response.getString("author"));
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
}