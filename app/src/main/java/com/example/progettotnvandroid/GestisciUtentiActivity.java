package com.example.progettotnvandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.progettotnvandroid.model.UserList;

import java.util.LinkedList;
import java.util.List;

public class GestisciUtentiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestisci_utenti);

        setContentView(R.layout.utenti_list_view);
        ListView listView = (ListView)findViewById(R.id.listViewUtenti);

        UtentiListViewAdapter adapter = new UtentiListViewAdapter(this, R.layout.utenti_list_view_row, UserList.getAllUtenti());
        listView.setAdapter(adapter);
    }
}