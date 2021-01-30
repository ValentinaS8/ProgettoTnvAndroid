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

import com.example.progettotnvandroid.model.UserList;
import com.example.progettotnvandroid.model.Utente;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button btnAccedi, btnRegistrati;

    public final static String LOGIN_ACTIVITY_PATH = "com.example.progettotnvandroid.LoginActivity";



    /* Gestione della chiusura tastiera quando sono su una EditText e clicco sul layout globale */
    private Context myContext;
    private Activity myActivity;
    private ConstraintLayout myCLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        /* Riferimenti widget */
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnAccedi = (Button) findViewById(R.id.btn_accedi);
        btnRegistrati = findViewById(R.id.btn_registrati);

        /***************************************************************************************************/
        /* Gestione della chiusura tastiera quando sono su una EditText e clicco sul layout globale */

        // Get the application context
        myContext = getApplicationContext();
        myActivity = LoginActivity.this;

        // Get the widget reference from XML layout
        myCLayout = (ConstraintLayout) findViewById(R.id.constraint_layout);

        // Set a click listener for CoordinatorLayout
        myCLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
            }
        });
        /*****************************************************************************************************/


        /* Model */
        //Gestione creazione prima utenza di admin
        /*DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALIAN);
        Date dateAdmin = new Date();
        String dateAdminString = sdf.format(dateAdmin);*/

        String adminBirthdayDateString="01/01/1970";
        Date adminBirthdayDate= null;
        try {
            adminBirthdayDate = new SimpleDateFormat("dd/MM/yyyy").parse(adminBirthdayDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        UserList.addUtente(new Utente("admin", "admin", "Pompu", adminBirthdayDate, true));


        /* Listeners */

        //Bottone accedi
        btnAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKeyboard(view);

                /* Validazione form */
                if(validateFormLogin()){

                    /* Check login */
                    Utente utenteLoggato = getUtenteByLogin();

                    if(utenteLoggato != null) {
                        //utente trovato
                        goToHomeACtivity(utenteLoggato);

                    }else{
                        //utente non trovato!
                        Toast toast = Toast.makeText(
                                getApplicationContext(),
                                "Login non valida!",
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            }
        });

        //Bottone registrati
        btnRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    private void goToHomeACtivity(Utente utenteLoggato) {
        Intent intent = new Intent(LoginActivity.this,
                RegistrazioneActivity.class);
        intent.putExtra(LOGIN_ACTIVITY_PATH, utenteLoggato);
        startActivity(intent);
    }


    /**
     * Cerca un utente e se lo trova lo restituisce, altrimenti null
     * @return utente o null
     */
    private Utente getUtenteByLogin() {

        //TODO recuperare lista utenti, controllare se esiste un utente con
        // quello username e se la password è quella

        List<Utente> utenti =  UserList.getAllUtenti();

        Utente utenteTrovato = null;

        /* Dati del form */
        String inputUsername = username.getText().toString();
        String inputPassword = password.getText().toString();

        for (Utente utente: utenti) {
            //controllo la login
            if(utente.getUsername().equals(inputUsername) &&
                    utente.getPassword().equals(inputPassword)){
                //clono l'utente trovato
                utenteTrovato = Utente.clonaUtente(utente);
            }
        }
        return utenteTrovato;
    }


    /**
     * Validazione del form login
     * @return boolean
     */
    private boolean validateFormLogin() {

        if(username.getText().length() == 0) {
            username.setError("Username vuoto!");
            return false;
        }else{
            username.setError(null);
        }

        if(password.getText().length() == 0) {
            password.setError("Password vuota!");
            return false;
        }else{
            password.setError(null);
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