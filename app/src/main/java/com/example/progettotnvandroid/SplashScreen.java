package com.example.progettotnvandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Thread background = new Thread(){
            public void run(){
                try {
                    sleep(1000); //1s = 1000

                    //ora possiamo passare alla main activity
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);


                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        background.start();
    }
}