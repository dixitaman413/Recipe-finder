package com.example.recipefinderapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("search.php")
    Call<MealResponse> searchMeals(@Query("s") String meal);
}