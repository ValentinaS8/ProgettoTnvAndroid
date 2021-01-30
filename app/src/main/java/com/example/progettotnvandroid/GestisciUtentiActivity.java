package com.example.progettotnvandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;


public class GestisciUtentiActivity extends AppCompatActivity {

    ListView simpleList;
    String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};
    int flags[] = {R.drawable.corgi, R.drawable.corgi, R.drawable.corgi, R.drawable.corgi, R.drawable.corgi, R.drawable.corgi};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gestisci_utenti);
        simpleList = (ListView) findViewById(R.id.gestione_utenti_list_view);
        GestioneUtentiAdapter customAdapter = new GestioneUtentiAdapter(getApplicationContext(), countryList, flags);
        simpleList.setAdapter(customAdapter);



    }
}