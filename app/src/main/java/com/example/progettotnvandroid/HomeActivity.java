package com.example.progettotnvandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    /**
     * Evita di tornare all'activity precedente con il tasto indietro
     */
    public void onBackPressed() {
        return;
    }
}