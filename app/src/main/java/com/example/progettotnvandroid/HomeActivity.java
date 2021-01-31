package com.example.progettotnvandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.progettotnvandroid.model.Utente;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.progettotnvandroid.LoginActivity.LOGIN_ACTIVITY_PATH;

public class HomeActivity extends AppCompatActivity {

    TextView userTextView, passwordTextView, cittaTextView, dataNascitaTextView, welcomeTextView;
    LinearLayout testoAdmin;
    Button btnLogout, btnModificaPassword, btnGestioneUtenti;

    /* Model */
    Utente utenteLoggato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /* Riferimenti widget */
        userTextView = findViewById(R.id.user);
        welcomeTextView = findViewById(R.id.welcome_text);
        passwordTextView = findViewById(R.id.password);
        cittaTextView = findViewById(R.id.citta);
        dataNascitaTextView = findViewById(R.id.data_nascita);
        btnLogout = findViewById(R.id.btn_logout);
        btnModificaPassword = findViewById(R.id.btn_modifica_password);
        btnGestioneUtenti = findViewById(R.id.btn_gestisci_utenti);
        testoAdmin = findViewById(R.id.text_admin);

        //recupero dell'intent
        Intent intent = getIntent();
        Serializable serializedLoggedUser = intent.getSerializableExtra(LOGIN_ACTIVITY_PATH);

        //per non avere errori nel caso non arrivi l'oggetto Utente
        if(serializedLoggedUser instanceof Utente){
            //non fare nulla
        }else{
            utenteLoggato = new Utente();
        }

        utenteLoggato = (Utente) serializedLoggedUser;

        /* valorizzazione dei widgets */

        //User
        userTextView.setText( utenteLoggato.getUsername());
        //welcome text
        welcomeTextView.setText( "Benvenuto " + utenteLoggato.getUsername() + "!" );
        //Password
        passwordTextView.setText( utenteLoggato.getPassword() );
        //Città
        cittaTextView.setText( utenteLoggato.getCitta() );
        //Data nascita
        //da data a stringa
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
        String dataUtenteLoggato = sdf.format(utenteLoggato.getDataNascita());
        dataNascitaTextView.setText( dataUtenteLoggato );

        /* Gestione admin */
        if(utenteLoggato.isAdmin()){
            testoAdmin.setVisibility(View.VISIBLE);
            btnGestioneUtenti.setVisibility(View.VISIBLE);
        }

        /* Listeners */

        //Pulsante logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //torna alla login
                goToLoginActivity();

            }
        });

        //Pulsante modifica password
        btnModificaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToModificaPasswordActivity();
            }
        });

        //Pulsante gestione utenti
        btnGestioneUtenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGestisciUtentiActivity();
            }
        });
    }


    @Override
    /**
     * Evita di tornare all'activity precedente con il tasto indietro
     */
    public void onBackPressed() {
        return;
    }

    /**
     * Reindirizzamento alla LoginActivity
     */
    private void goToLoginActivity() {
        Intent intent = new Intent(HomeActivity.this,
                LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Reindirizzamento alla activity gestione utente
     */
    private void goToGestisciUtentiActivity() {

        Intent intent = new Intent(HomeActivity.this,
                GestisciUtentiActivity.class);
        startActivity(intent);
    }

    /**
     * Reindirizzamento alla activity modifica password
     */
    private void goToModificaPasswordActivity() {

        Intent intent = new Intent(HomeActivity.this,
                ModificaPasswordActivity.class);
        startActivity(intent);
    }

}