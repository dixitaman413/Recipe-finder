package com.example.recipefinderapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.searchEditText);
        button = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mealName =
                        editText.getText().toString();

                searchRecipe(mealName);
            }
        });
    }

    private void searchRecipe(String mealName) {

        ApiService apiService =
                RetrofitClient.getRetrofitInstance()
                        .create(ApiService.class);

        Call<MealResponse> call =
                apiService.searchMeals(mealName);

        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call,
                                   Response<MealResponse> response) {

                if (response.body() != null &&
                        response.body().getMeals() != null) {

                    List<Meal> meals =
                            response.body().getMeals();

                    MealAdapter adapter =
                            new MealAdapter(meals);

                    recyclerView.setAdapter(adapter);

                } else {

                    Toast.makeText(MainActivity.this,
                            "No Recipes Found",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call,
                                  Throwable t) {

                Toast.makeText(MainActivity.this,
                        "Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}