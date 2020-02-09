package com.example.proiectmesotavariantafinala;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Logare extends AppCompatActivity {


    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    Button btnProf;
    EditText editTextProf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logare);
        setContentView((R.layout.activity_logare));

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.parola);
        progressBar = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.buttonLog);
        mCreateBtn = findViewById(R.id.textView2);
        editTextProf=findViewById(R.id.editTextProf);
        btnProf=findViewById(R.id.btnAutProf);


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                progressBar.setVisibility(View.VISIBLE);





//Conditi pentru email si parola
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Lipsa email");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Lipsa parola");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Parola trebuie sa aiba minim 6 caractere");
                    return;
                }


                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(Logare.this, "Utilizator logat cu succes!", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(), InterfataElev.class));
                        } else {
                            Toast.makeText(Logare.this, "Eroare!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    }
                });

            }
        });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 startActivity(new Intent(getApplicationContext(), InterfataProfesor.class));
            }
        });


    }

    public void autProf(View view) {
        String codAcces=editTextProf.getText().toString();

        if(equals(codAcces)==equals("T1i2f92m07"))
            startActivity(new Intent(getApplicationContext(), InterfataProfesor.class));
        else {
            Toast.makeText(Logare.this, "Cod de acces incorect!", Toast.LENGTH_SHORT).show();
editTextProf.setText("");
        }
    }

    public void vizibilitateProf(View view) {
        btnProf.setVisibility(View.VISIBLE);
        editTextProf.setVisibility(View.VISIBLE);

    }
}
