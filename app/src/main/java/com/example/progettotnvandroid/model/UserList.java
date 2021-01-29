package com.example.progettotnvandroid.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserList {

    //Struttura Mappa per salvare i record di Utente
    static Map<Integer, Utente> utentiMap = new HashMap<>();
    static int lastIndexUtente = 0;

    /**
     * Aggiunge un utente alla lista utenti
     * @param utente
     * @return
     */
    static public int addUtente(Utente utente){
        //incremento id prestito
        utente.setId(++lastIndexUtente);

        utentiMap.put((int) utente.getId(), utente);
        return 1;
    }

    /**
     * Ricerca un utente by id
     * @param id
     * @return prestito
     */
    public static Utente getUtenteById(int id){
        return utentiMap.get((int) id);
    }

    /**
     * Restituisce tutti gli utenti
     * @return ArrayList di tutti gli utenti
     */
    public static List<Utente> getAllUtenti() {
        Collection<Utente> utentiList = utentiMap.values();
        return new ArrayList<>(utentiList);
    }


}