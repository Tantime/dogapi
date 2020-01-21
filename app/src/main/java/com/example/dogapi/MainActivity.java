package com.example.dogapi;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    /*
     * 1. So you found API...how is the data sent to us?
     *      a. What kind of model classes will we need to match the JSON?
     *      b. make sure the model variables match the json keys
     *      c. make sure you have an empty constructor with no params
     * 2. Make sure that your manifest has the right permissions for internet access
     *
     * 3. Set up retrofit
     *      a. add the library to the project
     *      b. create the interface to match our api
     *      c. in the activity, create the retrofit object & service object
     *
     */

    private Button buttonRandom;
    private ImageView imageViewDog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireWidgets();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DogService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DogService dogService = retrofit.create(DogService.class);

        Call<Dog> dogCall = dogService.getRandomImageByBreed("spaniel");
        // gotta wait for liam to go outside, get the data, and return with the data
        dogCall.enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(Call<Dog> call, Response<Dog> response) {
                // ANY CODE THAT DEPENDS ON THE RESULT OF THE SEARCH HAS TO GO HERE
                Dog foundDog = response.body();
                // check if the body isn't null
                if(foundDog != null) {
                    Toast.makeText(MainActivity.this, foundDog.getMessage(), Toast.LENGTH_SHORT).show();
                    Picasso.get().load(foundDog.getMessage()).into(imageViewDog);
                }
            }

            @Override
            public void onFailure(Call<Dog> call, Throwable t) {
                // TOAST THE FAILURE
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        buttonRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomDog(dogService);
            }
        });
    }

    private void randomDog(DogService dogService) {
        Call<Dog> dogCall = dogService.getRandomImageByBreed("hound");
        dogCall.enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(Call<Dog> call, Response<Dog> response) {
                // ANY CODE THAT DEPENDS ON THE RESULT OF THE SEARCH HAS TO GO HERE
                Dog foundDog = response.body();
                // check if the body isn't null
                if (foundDog != null) {
                    Toast.makeText(MainActivity.this, foundDog.getMessage(), Toast.LENGTH_SHORT).show();
                    Picasso.get().load(foundDog.getMessage()).into(imageViewDog);
                }
            }

            @Override
            public void onFailure(Call<Dog> call, Throwable t) {
                // TOAST THE FAILURE
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void wireWidgets() {
        buttonRandom = findViewById(R.id.button_main_random);
        imageViewDog = findViewById(R.id.imageView_main_dogImage);
    }
}