package com.the_spartan_of_war.instagram.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.the_spartan_of_war.instagram.R;
import com.the_spartan_of_war.instagram.helper.SettingsFirebase;
import com.the_spartan_of_war.instagram.model.User;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail;
    private EditText loginPassword;
    private Button btnEnterAll;
    private ProgressBar progressLogin;

    private User user;

    private FirebaseAuth authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /**verificationUserLogin();*/
        startComponents();

        progressLogin.setVisibility(View.GONE);
        /**Criando metodo de login do usuario já cadastrado"*/
        btnEnterAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtEmail = loginEmail.getText().toString();
                String txtPassword = loginPassword.getText().toString();

                /**Condição para validação do E-mail*/
                if (!txtEmail.isEmpty()) {
                    /**Condição para validação do E-mail*/
                    if (!txtPassword.isEmpty()) {

                        user = new User();
                        user.setEmail(txtEmail);
                        user.setPassword(txtPassword);
                        validateLogin(user);

                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Informe a senha",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Informe seu E-mail",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void verificationUserLogin(){
        authentication = SettingsFirebase.getFirebaseAuthentication();
        if (authentication.getCurrentUser() != null ){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    /** Metodo para validar Login no App"*/
    public void validateLogin(User user) {
        progressLogin.setVisibility(View.VISIBLE);
        authentication = SettingsFirebase.getFirebaseAuthentication();

        authentication.signInWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful() ) {
                    progressLogin.setVisibility( View.GONE );
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    /** Metodo para erro ao realizar Login no App"*/
                    Toast.makeText(LoginActivity.this,
                            "Erro ao realizar login",
                            Toast.LENGTH_SHORT).show();
                    progressLogin.setVisibility( View.GONE );
                }
            }
        });
    }

    /**Criando comunicação entre as telas de login e  cadastro ao pressionar o "Button"*/
    public void screenRegister (View view) {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity( i );
    }

    public void startComponents () {

        /**Declarando a comunicação entre a variavel e o ID da Tela activity_register*/
        loginEmail      = findViewById(R.id.edtLoginEmail);
        loginPassword   = findViewById(R.id.edtLoginPassword);
        btnEnterAll     = findViewById(R.id.btnEnter);
        progressLogin   = findViewById(R.id.progressLogin);

        /** Metodo o FOCO ao abrir o App seja o campo loginEmail"*/
        loginEmail.requestFocus();

    }

}