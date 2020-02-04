package com.example.proiectmesotavariantafinala;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.view.textclassifier.ConversationAction;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.proiectmesotavariantafinala.Logare;
import com.example.proiectmesotavariantafinala.R;
//import com.example.proiectmesotavariantafinala.ui.gallery.GalleryFragment;
//import com.example.proiectmesotavariantafinala.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText vNume,vEmail,vParola,vCNP;
    Button vButonInreg;
    TextView vButonLog;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference ref;
    String s;

    Elev user = new Elev();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        vNume = findViewById(R.id.nume);
        vEmail = findViewById(R.id.email);
        vParola = findViewById(R.id.parola);
        vCNP = findViewById(R.id.cnp);
        vButonInreg = findViewById(R.id.buttonReg);
        vButonLog = findViewById(R.id.textView2);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Elevi");

        vButonInreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String sEmail = vEmail.getText().toString().trim();
                String sParola = vParola.getText().toString().trim();

                progressBar.setVisibility(View.VISIBLE);


                //Conditi pentru email si parola
                if (TextUtils.isEmpty(sEmail)) {
                    vEmail.setError("Lipsa email");
                    return;
                }
                if (TextUtils.isEmpty(sParola)) {
                    vParola.setError("Lipsa parola");
                    return;
                }
                if (sParola.length() < 6) {
                    vParola.setError("Parola trebuie sa aiba minim 6 caractere");
                    return;
                }

                if (fAuth.getCurrentUser() != null) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }


//Inregistrarea utilizatorului in baza de date

                fAuth.createUserWithEmailAndPassword(sEmail, sParola).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( task.isSuccessful()) {
                            Exception exception = task.getException();
                            Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();

                            startActivity(new Intent(getApplicationContext(), Logare.class));
                        } else {
                            Exception exception = task.getException();
                            Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();


                        }
                    }
                });
                adaugareElev();
                startActivity(new Intent(getApplicationContext(), Logare.class));

            }
        });



        vButonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              startActivity(new Intent(getApplicationContext(), Logare.class));
            //    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
           //     startActivity(browserIntent);

            }
        });

    }
    private void getValues()
    {
        user.setName(vNume.getText().toString());
        user.setPassword(vParola.getText().toString());
     //   user.setCNP(vCNP.getText().toString());
        user.setEmail(vEmail.getText().toString());


    }

void adaugareElev()
    {
    ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            getValues();

            String idelev=vCNP.getText()+"";


            ref.child(idelev).setValue(user);

            Toast.makeText(MainActivity.this, "Adaugat cu succes ", Toast.LENGTH_LONG).show();



        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

}




}

