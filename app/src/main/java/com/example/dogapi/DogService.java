package com.example.dogapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DogService {

    String BASE_URL = "https://dog.ceo/api/";

    @GET("breed/{breed}/images/random")
    Call<Dog> getRandomImageByBreed(@Path("breed") String breed);
}