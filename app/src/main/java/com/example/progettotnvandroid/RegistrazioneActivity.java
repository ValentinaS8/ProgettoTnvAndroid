package com.example.progettotnvandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.progettotnvandroid.model.UserList;
import com.example.progettotnvandroid.model.Utente;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrazioneActivity extends AppCompatActivity {

    EditText username, password, confermaPassword, cittaProvenienza, dataNascita;
    Button btn_registrati;
    Date dataNascitaNuovoUtente = null;

    /* Gestione della chiusura tastiera quando sono su una EditText e clicco sul layout globale */
    private Context myContext;
    private Activity myActivity;
    private ConstraintLayout myCLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        /* Riferimenti widget */
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confermaPassword = findViewById(R.id.conferma_password);
        cittaProvenienza = findViewById(R.id.citta_di_provenienza);
        dataNascita = findViewById(R.id.data_di_nascita);
        btn_registrati = findViewById(R.id.btn_registrati);

        /***************************************************************************************************/
        /* Gestione della chiusura tastiera quando sono su una EditText e clicco sul layout globale */

        // Get the application context
        myContext = getApplicationContext();
        myActivity = RegistrazioneActivity.this;

        // Get the widget reference from XML layout
        myCLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        // Set a click listener for CoordinatorLayout (layout di sfondo della nostra activity)
        myCLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
            }
        });
        /*****************************************************************************************************/

        btn_registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername, inputPassword, inputConfermaPassword, inputCitta, inputDataNascita;
                inputUsername = username.getText().toString();
                inputPassword = password.getText().toString();
                inputConfermaPassword = confermaPassword.getText().toString();
                inputCitta = cittaProvenienza.getText().toString();
                inputDataNascita = dataNascita.getText().toString();

                //verifica correttezza parametri inseriti
                Boolean formValido = validazioneStringheFormNuovoUtente(inputUsername, inputPassword,
                        inputConfermaPassword, inputCitta, inputDataNascita);

                hideKeyboard(v);

                //se sono stati riempiti tutti i campi dei form controllo che la data abbia un formato valido
                if(formValido)
                {
                    dataNascitaNuovoUtente = validazioneDataNascitaFormNuovoUtente(inputDataNascita);
                }

                if(dataNascitaNuovoUtente != null)
                {
                    //creazione nuovo utente
                    Utente nuovoUtente = new Utente(inputUsername, inputPassword,
                            inputCitta, dataNascitaNuovoUtente, false);

                    //aggiunta nuovo utente alla mappa
                    UserList.addUtente(nuovoUtente);

                    //passa ad activity login
                    goToLoginActivity();
                }
            }
        });
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(RegistrazioneActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean validazioneStringheFormNuovoUtente(String inputUsername, String inputPassword,
                                               String inputConfermaPassword, String inputCitta, String inputDataNascita) {
        //verifica campi vuoti

        //se il campo username è vuoto
        if(inputUsername.length() == 0) {
            username.setError("Il campo non può esssere vuoto!");
            return false;
        }else{
            username.setError(null);
        }

        //se il campo password è vuoto
        if(inputPassword.length() == 0) {
            password.setError("Il campo non può esssere vuoto!");
            return false;
        }else{
            password.setError(null);
        }

        //se il campo conferma password è vuoto
        if(inputConfermaPassword.length() == 0) {
            confermaPassword.setError("Il campo non può esssere vuoto!");
            return false;
        }else{
            confermaPassword.setError(null);
        }

        //verifica che le due password coincidano
        if(!(inputPassword.equals(inputConfermaPassword))) {
            confermaPassword.setError("Le password non coincidono!");
            return false;
        }else{
            confermaPassword.setError(null);
        }

        //se il campo città di provenienza è vuoto
        if(inputCitta.length() == 0) {
            cittaProvenienza.setError("Il campo non può esssere vuoto!");
            return false;
        }else{
            cittaProvenienza.setError(null);
        }

        //se il campo data di nascita è vuoto
        if(inputDataNascita.length() == 0) {
            dataNascita.setError("Il campo non può esssere vuoto!");
            return false;
        }else{
            dataNascita.setError(null);
        }
        //validazione ok
        return true;
    }

    private Date validazioneDataNascitaFormNuovoUtente(String inputDataNascita)
    {
        //conversione data nascita
        try {
            dataNascita.setError(null);
            Date dataNascita = new SimpleDateFormat("dd/MM/yyyy").parse(inputDataNascita);
            return dataNascita;
        } catch (ParseException e) {
            dataNascita.setError("Formato data non corretto!");
            return null;
        }
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