package com.example.progettotnvandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.progettotnvandroid.model.UserList;
import com.example.progettotnvandroid.model.Utente;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class RegistrazioneActivity extends AppCompatActivity {

    EditText username, password, confermaPassword, cittaProvenienza, dataNascita;
    Button btn_registrati;
    Date dataNascitaNuovoUtente = null;

    /* Gestione della chiusura tastiera quando sono su una EditText e clicco sul layout globale */
    private Context myContext;
    private Activity myActivity;
    private LinearLayout mainLinearLayout;


    //gestione date picker
    DatePickerDialog datePicker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        /* Riferimenti widget */
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confermaPassword = findViewById(R.id.conferma_password);
        cittaProvenienza = findViewById(R.id.citta_di_provenienza);
        btn_registrati = findViewById(R.id.btn_registrati);

        dataNascita = findViewById(R.id.data_di_nascita);
        dataNascita.setInputType(InputType.TYPE_NULL);
        DatePickerDialog datePicker;



        /* Gestione della chiusura tastiera quando sono su una EditText e clicco sul layout globale */

        // Get the application context
        myContext = getApplicationContext();
        myActivity = RegistrazioneActivity.this;

        // Get the widget reference from XML layout
        mainLinearLayout = (LinearLayout) findViewById(R.id.mainLinearLayout);

        // Set a click listener for CoordinatorLayout (layout di sfondo della nostra activity)
        mainLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
            }
        });

        //gestione date picker
        dataNascita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);

                     DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrazioneActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                Date selectedDate = parseDate(year + "-" + monthOfYear+1 + "-" + dayOfMonth);

                                if(selectedDate.before(new Date())){
                                    dataNascita.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }else{
                                    Toast toast = Toast.makeText(
                                            getApplicationContext(),
                                            "Data non valida!",
                                            Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                            }
                        }, year, month, day);

                        //Settiamo la data massima selezionabile - posso impostare una data inferiore alla data odierna
                        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());

                        datePickerDialog.show();
                }
        });

        btn_registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername, inputPassword, inputConfermaPassword, inputCitta, inputDataNascita;

                //nasconde tastiera al click
                hideKeyboard(v);

                inputUsername = username.getText().toString();
                inputPassword = password.getText().toString();
                inputConfermaPassword = confermaPassword.getText().toString();
                inputCitta = cittaProvenienza.getText().toString();
                inputDataNascita = dataNascita.getText().toString();

                //verifica correttezza parametri inseriti
                Boolean formValido = validazioneStringheFormNuovoUtente(inputUsername, inputPassword,
                        inputConfermaPassword, inputCitta, inputDataNascita);



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

        //se lo username esiste già (deve essere univoco)
        if(!(UserList.isUsernameDisponibile(inputUsername))) {
            username.setError("Username non disponibile!");
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
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * String to date
     * @param date
     * @return
     */
    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}