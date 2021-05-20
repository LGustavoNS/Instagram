package com.the_spartan_of_war.instagram.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsFirebase {

    /**Declarando variaveis  de forma estatica*/
    private static DatabaseReference referenceFirebase;
    private static FirebaseAuth referenceAuthentication;

    /**retorna a instancia do database*/
    public static DatabaseReference getFirebaseDatabase () {
        if( referenceFirebase == null ) {
            referenceFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenceFirebase;
    }

    /**retorna a instancia da Autenticação no Firebase*/
    public static  FirebaseAuth getFirebaseAuthentication(){
        if ( referenceAuthentication == null ) {
            referenceAuthentication = FirebaseAuth.getInstance();
        }
        return referenceAuthentication;
    }

}
