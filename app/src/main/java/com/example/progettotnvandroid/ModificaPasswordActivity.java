package com.example.progettotnvandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.progettotnvandroid.model.Utente;

import java.io.Serializable;

import static com.example.progettotnvandroid.LoginActivity.LOGIN_ACTIVITY_PATH;

public class ModificaPasswordActivity extends AppCompatActivity {

    //TODO-AGGIUNGERE CONTROLLO SU TASTIERA A SCOMPARSA SUI BOTTONI

    EditText nuovaPassword, confermaPassword;
    Button btn_aggiornaPassword, btn_home;
    Utente utente;

    /* Gestione della chiusura tastiera quando sono su una EditText e clicco sul layout globale */
    private Context myContext;
    private Activity myActivity;
    private ConstraintLayout myCLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_password);

        //recupero gli id dei widget
        nuovaPassword = findViewById(R.id.nuova_password);
        confermaPassword = findViewById(R.id.conferma_password);
        btn_aggiornaPassword = findViewById(R.id.btn_aggiorna_password);
        btn_home = findViewById(R.id.btn_home);

        //recupero dati dalla schermata home
        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra(LOGIN_ACTIVITY_PATH);

        /***************************************************************************************************/
        /* Gestione della chiusura tastiera quando sono su una EditText e clicco sul layout globale */

        // Get the application context
        myContext = getApplicationContext();
        myActivity = ModificaPasswordActivity.this;

        // Get the widget reference from XML layout
        myCLayout = (ConstraintLayout) findViewById(R.id.constraint_layout);

        // Set a click listener for CoordinatorLayout (layout di sfondo della nostra activity)
        myCLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
            }
        });
        /*****************************************************************************************************/

        //alla pressione del bottone aggiorna password
        btn_aggiornaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utente = (Utente) obj;

                //se i campi sono validi
                if(validazioneFormCambiaPassword(utente))
                {
                    //fai cambiare la password
                }
            }
        });
    }

    private boolean validazioneFormCambiaPassword(Utente utente) {

        //se la nuova password inserita è uguale a quella precedente
        if(nuovaPassword.getText().equals(utente.getPassword())) {
            confermaPassword.setError("Inserire una password diversa dalla precedente!");
            return false;
        }else{
            confermaPassword.setError(null);
        }

        //se il campo nuova password è vuoto
        if(nuovaPassword.getText().length() == 0) {
            nuovaPassword.setError("Il campo non può esssere vuoto!");
            return false;
        }else{
            nuovaPassword.setError(null);
        }

        //se il campo conferma password è vuoto
        if(confermaPassword.getText().length() == 0) {
            confermaPassword.setError("Il campo non può esssere vuoto!");
            return false;
        }else{
            confermaPassword.setError(null);
        }

        //se le password nei 2 campi non coincidono
        if(!(nuovaPassword.getText().equals(confermaPassword.getText()))) {
            confermaPassword.setError("Le password non coincidono!");
            return false;
        }else{
            confermaPassword.setError(null);
        }

        //validazione ok
        return true;
    }

    /**
     * Nasconde la tastiera al click della View passata in firma
     * @param view
     */
    private void hideKeyboard(View view){
        // Get the input method manager
        InputMethodManager inputMethodManager = (InputMethodManager)
                view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        // Hide the soft keyboard
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}