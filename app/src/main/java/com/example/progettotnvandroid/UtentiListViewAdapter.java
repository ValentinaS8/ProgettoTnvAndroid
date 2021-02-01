package com.example.progettotnvandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.progettotnvandroid.model.Utente;

import java.util.List;

public class UtentiListViewAdapter extends ArrayAdapter<Utente> {

    public UtentiListViewAdapter(Context context, int textViewResourceId,
                                 List<Utente> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.utenti_list_view_row, null);

        TextView nome = (TextView)convertView.findViewById(R.id.textViewName);
        TextView numero = (TextView)convertView.findViewById(R.id.textViewNumber);

        //TODO da completare gestione listview

        Utente u = getItem(position);
        nome.setText(u.getUsername());
        numero.setText("123456");


        return convertView;
    }
}
