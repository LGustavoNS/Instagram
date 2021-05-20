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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.the_spartan_of_war.instagram.R;
import com.the_spartan_of_war.instagram.helper.SettingsFirebase;
import com.the_spartan_of_war.instagram.model.User;

public class RegisterActivity extends AppCompatActivity {

    /**Declarando Variaveis*/
    private EditText registerName;
    private EditText registerEmail;
    private EditText registerPassword;
    private Button btnRegisterAll;
    private ProgressBar progressBarRegister;

    private User user;

    /**Objeto de Autenticação no Firebase*/
    private FirebaseAuth authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        startComponents();

        /**Cadastrando usuario*/
        progressBarRegister.setVisibility(View.GONE);
        /**Configurando quando o Button for pressionado realizar cadastro no Firebase.*/
        btnRegisterAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBarRegister.setVisibility(View.VISIBLE);
                String txtName      = registerName.getText().toString();
                String txtEmail     = registerEmail.getText().toString();
                String txtPassword  = registerPassword.getText().toString();

                /**Condição para validação de dados preenchidos NOME*/
                if ( !txtName.isEmpty() ) {
                    /**Condição para validação de dados preenchidos EMAIL*/
                    if ( !txtEmail.isEmpty() ) {
                        /**Condição para validação de dados preenchidos SENHA*/
                        if ( !txtPassword.isEmpty() ) {

                            /**Criando comunicação dos obejtos User.Java com a validação dos dados*/
                            user = new User();
                            user.setName( txtName );
                            user.setEmail( txtEmail );
                            user.setPassword( txtPassword );
                            /**Salvando o objeto usuario*/
                            register( user );

                        }else {
                            Toast.makeText(RegisterActivity.this,
                                    "Cadastre uma senha",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(RegisterActivity.this,
                                "Inserir E-email completo!!!",
                                Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(RegisterActivity.this,
                            "Inserir Nome completo!!!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /**Criado metodo para cadastrar usuario, validando email e senha.*/
    public void register( User user ){

        authentication = SettingsFirebase.getFirebaseAuthentication();
        authentication.createUserWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(
                this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful() ) {

                            progressBarRegister.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this,
                                    "Cadastro realizado com sucesso \n" + " Bem-Vindo ao Instagram",
                                    Toast.LENGTH_SHORT).show();

                            /**Redirecionando o User para a tela central (Feed Instagram)
                             * quando o login for realizado com sucesso*/
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();

                        }
                        /**Metodo para tratamento de erro no cadastro*/
                        else {

                            progressBarRegister.setVisibility(View.GONE);
                            /**Metodo para tratamento de erro no cadastro*/
                            String errorException = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                errorException = "Digite uma senha mais forte!";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                errorException = "Por favor digite um e-mail válido";
                            } catch (FirebaseAuthUserCollisionException e) {
                                errorException = "Conta já cadastrada";
                            } catch (Exception e) {
                                errorException = "ao cadastrar usuário: " + e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(RegisterActivity.this,
                                    "Erro" + errorException,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void startComponents () {

        /**Declarando a comunicação entre a variavel e o ID da Tela activity_register*/
        registerName        = findViewById(R.id.edtRegisterName);
        registerEmail       = findViewById(R.id.edtRegisterEmail);
        registerPassword    = findViewById(R.id.edtRegisterPassword);
        btnRegisterAll      = findViewById(R.id.btnRegister);
        progressBarRegister = findViewById(R.id.progressRegister);

        /** Metodo o FOCO ao abrir o App seja o campo registerName"*/
        registerName.requestFocus();

    }

}