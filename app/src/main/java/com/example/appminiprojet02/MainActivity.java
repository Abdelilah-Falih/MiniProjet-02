package com.example.appminiprojet02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

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

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding ;
    View root ;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        root = binding.getRoot();
        setContentView(root);
        sharedPreferences = getSharedPreferences("favorite-quotes",MODE_PRIVATE);
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