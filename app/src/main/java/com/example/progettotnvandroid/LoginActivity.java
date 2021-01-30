package com.example.progettotnvandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button btnAccedi, btnRegistrati;

    public final static String loginActivityPackage = "com.example.progettotnvandroid.LoginActivity";




    private Context mContext;
    private Activity mActivity;

    private ConstraintLayout mCLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);





        // Get the application context
        mContext = getApplicationContext();
        mActivity = LoginActivity.this;

        // Get the widget reference from XML layout
        mCLayout = (ConstraintLayout) findViewById(R.id.constraint_layout);

        // Set a click listener for CoordinatorLayout
        mCLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the input method manager
                InputMethodManager inputMethodManager = (InputMethodManager)
                        view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                // Hide the soft keyboard
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
            }
        });


        /*****************************************************************************************************/


        /* Model */
        //Gestione creazione prima utenza di admin

        //String username, String password, String citta, Date dataNascita, boolean isAdmin
        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALIAN);
        Date dateAdmin = new Date();
        String dateAdminString = sdf.format(dateAdmin);

        UserList.addUtente(new Utente("admin", "admin", "admin", dateAdmin, true));



        System.out.println(dateAdminString);

        /* Riferimenti widget */
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnAccedi = findViewById(R.id.btn_accedi);
        btnRegistrati = findViewById(R.id.btn_registrati);


        /* Listeners */


        btnAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Validazione form */
                if(validateFormLogin()){

                    /* Check login */
                    checkLogin();
                }





            }
        });

        btnRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }


    private boolean checkLogin() {

        //TODO recuperare lista utenti, controllare se esiste un utente con
        // quello username e se la password è quella

        UserList.getAllUtenti();

        Toast toast = Toast.makeText(
                getApplicationContext(),
                "Login non valida!",
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();


        return true;
    }

    private boolean validateFormLogin() {

        if(username.getText().length() == 0) {
            username.setError("Username vuoto!");
            return false;
        }else{
            username.setError(null);
        }

        if(password.getText().length() == 0) {
            password.setError("Username vuoto!");
            return false;
        }else{
            password.setError(null);
        }

        //validazione ok
        return true;
    }
}