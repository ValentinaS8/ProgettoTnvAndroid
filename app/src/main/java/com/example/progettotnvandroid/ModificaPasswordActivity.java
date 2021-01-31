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
import android.widget.TextView;
import android.widget.Toast;

import com.example.progettotnvandroid.model.Utente;

import java.io.Serializable;

import static com.example.progettotnvandroid.LoginActivity.UTENTE_PATH;

public class ModificaPasswordActivity extends AppCompatActivity {

    //TODO-AGGIUNGERE CONTROLLO SU TASTIERA A SCOMPARSA SUI BOTTONI

    EditText nuovaPassword, confermaPassword;
    TextView userTextView, vecchiaPasswordTextView;
    Button btn_aggiornaPassword, btn_home;
    Utente utenteLoggato = null;

    /* Gestione della chiusura tastiera quando sono su una EditText e clicco sul layout globale */
    private Context myContext;
    private Activity myActivity;
    private ConstraintLayout myCLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_password);

        //recupero dati dalla schermata home
        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra(UTENTE_PATH);
        utenteLoggato = (Utente) obj;

        //recupero gli id dei widget
        nuovaPassword = findViewById(R.id.nuova_password);
        confermaPassword = findViewById(R.id.conferma_password);
        btn_aggiornaPassword = findViewById(R.id.btn_aggiorna_password);
        btn_home = findViewById(R.id.btn_home);
        userTextView = findViewById(R.id.user);
        vecchiaPasswordTextView = findViewById(R.id.password);

        /* valorizzazione dei widgets */

        //User
        userTextView.setText(utenteLoggato.getUsername());
        vecchiaPasswordTextView.setText(utenteLoggato.getPassword());


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

                hideKeyboard(v);
                //se i campi sono validi
                if (validazioneFormCambiaPassword(utenteLoggato)) {
                    //salva la nuova password nel profilo dell'utente
                    utenteLoggato.setPassword(nuovaPassword.getText().toString());
                    //Conferma di modifica password
                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            "Password modificata correttamente!",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                goToHomeActivity(utenteLoggato);
            }
        });

    }

    private boolean validazioneFormCambiaPassword(Utente utenteLoggato) {

        String campoNuovaPassword = nuovaPassword.getText().toString();
        String passwordPrecedente = utenteLoggato.getPassword();
        String campoConfermaPassword;

        //se la nuova password inserita è uguale a quella precedente
        if (campoNuovaPassword.equals(passwordPrecedente)) {
            confermaPassword.setError("Inserire una password diversa dalla precedente!");
            return false;
        } else {
            confermaPassword.setError(null);
        }

        //se il campo nuova password è vuoto
        if (nuovaPassword.getText().length() == 0) {
            nuovaPassword.setError("Il campo non può esssere vuoto!");
            return false;
        } else {
            nuovaPassword.setError(null);
        }

        //se il campo conferma password è vuoto
        if (confermaPassword.getText().length() == 0) {
            confermaPassword.setError("Il campo non può esssere vuoto!");
            return false;
        } else {
            confermaPassword.setError(null);
        }

        //se le password nei 2 campi non coincidono

        /*faccio un nuovo assegnamento di "campoNuovaPassword"
        perchè la password potrebbe essere stata cambiata
        più volte all'interno della stessa sessione*/
        campoNuovaPassword = nuovaPassword.getText().toString();
        campoConfermaPassword = confermaPassword.getText().toString();


        if (!(campoNuovaPassword.equals(campoConfermaPassword))) {
            confermaPassword.setError("Le password non coincidono!");
            return false;
        } else {
            confermaPassword.setError(null);
        }

        //validazione ok
        return true;

    }

    /**
     * Reindirizzamento alla HomeActivity
     *
     * @param utenteLoggato
     */
    private void goToHomeActivity(Utente utenteLoggato) {
        Intent intent = new Intent(ModificaPasswordActivity.this,
                HomeActivity.class);
        intent.putExtra(UTENTE_PATH, utenteLoggato);
        startActivity(intent);
    }


    /**
     * Nasconde la tastiera al click della View passata in firma
     *
     * @param view
     */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}