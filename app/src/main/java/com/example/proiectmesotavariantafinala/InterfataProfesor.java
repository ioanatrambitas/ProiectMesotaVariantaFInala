package com.example.proiectmesotavariantafinala;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Random;

import static android.widget.Toast.makeText;

public class InterfataProfesor extends AppCompatActivity {
FrameLayout frameLayout1, frameLayout2, frameLayout3;
    Button selectFile, upload;
    TextView notification;
    Uri pdfUri; //adresa pdf
    TextView nume;

    FirebaseStorage storage; //pentru incarcare fisier
    FirebaseDatabase database; // pentru memorare adresa fisier
    ProgressDialog progressDialog;

    Spinner spinner;
    String string="";
    ValueEventListener listener;
    DatabaseReference databaseReference;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;

/////////////////////////////////////////////////////////////////////////

    Spinner spinnerElev, spinnerMaterie, spinnerNota;
    String textElev,textMaterie, textNota;

    DatabaseReference dbElev, dbMaterie, dbNote;

    ValueEventListener listenerElev, listenerMaterie, listenerNota;

    ArrayAdapter<String> adapterElev, adapterMaterie, adapterNote;
    ArrayList<String> listElev, listMaterie, listNote;

   TextView etElev, etMaterie, etNota;
    FirebaseDatabase databaseAdd;
    DatabaseReference refElev, refMaterie, refNota;
    int i=0;
    String randomString=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfata_profesor);

        frameLayout1=findViewById(R.id.frameCatalog);
        frameLayout2=findViewById(R.id.frameLectii);
        frameLayout3=findViewById(R.id.frameTeme);

        spinner=findViewById(R.id.spinnerUpload);
        databaseReference= FirebaseDatabase.getInstance().getReference("Elevi").child("Materii");
        arrayList=new ArrayList<>();

        arrayAdapter=new ArrayAdapter<String>(InterfataProfesor.this,
                android.R.layout.simple_list_item_1,arrayList);

        functiaSetSpinner();

        spinner.setAdapter(arrayAdapter);

        storage = FirebaseStorage.getInstance(); //returneaza un obiect Firebase Storage
        database = FirebaseDatabase.getInstance(); // returneaza un obiect Firebase Databse

        selectFile = findViewById(R.id.selectFile);
        upload = findViewById(R.id.upload);
        notification = findViewById(R.id.notification);
        nume=findViewById(R.id.nameFile);

        frameLayout1.setVisibility(View.VISIBLE);
        frameLayout2.setVisibility(View.INVISIBLE);
        frameLayout3.setVisibility(View.INVISIBLE);

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(InterfataProfesor.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    selectPdf();
                }
                else
                {
                    ActivityCompat.requestPermissions(InterfataProfesor.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pdfUri != null)
                    uploadFile(pdfUri);
                else
                    makeText(InterfataProfesor.this, "Selecteaza fisier", Toast.LENGTH_SHORT).show();
            }

            private void uploadFile(final Uri pdfUri) {
                progressDialog = new ProgressDialog(InterfataProfesor.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setTitle("Incarcare fisier...");
                progressDialog.show();

                final String fileName =System.currentTimeMillis()+"";
                final String fileNameFinal=nume.getText().toString()+"-"+fileName;
                string=spinner.getSelectedItem().toString().trim();

                //    StorageReference reference =  storage.child("uploads/"+System.currentTimeMillis()+".pdf");

                //      StorageReference storageReference = storage.getRFeference();
                final StorageReference storageReference = storage.getReference();

                storageReference.child("Documente").child(string).child(fileNameFinal).putFile(pdfUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Toast.makeText(InterfataProfesor.this, "Se verifica", Toast.LENGTH_SHORT).show();
                                String url =taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                                //      Task<Uri> url =taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                DatabaseReference reference = database.getReference();

                                // Map<String,String> doc = new HashMap<>();
                                //  doc.put("url", url);
                                reference.child("Documente").child(string).child(fileNameFinal).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                            Toast.makeText(InterfataProfesor.this, "Fisier incarcat cu succes", Toast.LENGTH_SHORT).show();
                                        else

                                            Toast.makeText(InterfataProfesor.this, "Eroare incarcare fisier1", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                //  startActivity(new Intent(this,UploadFragmentElev.class));
                                //        Intent intent = new Intent(getContext(), UploadFragmentElev.class);
                                //       startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(InterfataProfesor.this, "Eroare incarcare fisier2", Toast.LENGTH_SHORT).show();
                        e.getMessage();
                        e.printStackTrace();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                        int currentProgress= (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        progressDialog.setProgress(currentProgress);
                        if (currentProgress==100) {
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    progressDialog.dismiss(); // yourMethod();
                                }
                            }, 5000);
                            notification.setText("Alegeti alt fisier");
                            nume.setText("");
                            stergeUri();

                            Uri pdfUri = null;

                        }
                    }
                });

            }

        });
//////////////////////////////////////////////////////////////////////////////////////////


        spinnerElev=findViewById(R.id.spinnerElev);
        spinnerMaterie=findViewById(R.id.spinnerMaterie);
        etElev=findViewById(R.id.editElev);
        etMaterie=findViewById(R.id.editMaterie);
        etNota=findViewById(R.id.editNota);





        dbElev= FirebaseDatabase.getInstance().getReference("Elevi").child("Nume");
        dbMaterie= FirebaseDatabase.getInstance().getReference("Elevi").child("Materii");

        listElev= new ArrayList<>();
        listMaterie= new ArrayList<>();


        adapterElev=new ArrayAdapter<String>(InterfataProfesor.this,
                android.R.layout.simple_list_item_1,listElev);
        adapterMaterie=new ArrayAdapter<String>(InterfataProfesor.this,
                android.R.layout.simple_list_item_1,listMaterie);


        spinnerElev.setAdapter(adapterElev);
        spinnerMaterie.setAdapter(adapterMaterie);
        retriveSpinerElev();
        retriveSpinerMaterie();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED)

            selectPdf();

        else
            makeText(InterfataProfesor.this, "Va rugam verificati permisiunile...", Toast.LENGTH_SHORT).show();
    }


    private void selectPdf()
    {
        //ofera selectarea fisierului utilizand file manager


        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }
    private void stergeUri()
    {
        pdfUri=null;

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //verifica daca utilizatorul a selectat sau nu un fisier
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();  //returneaza adresa fisier selectat
            notification.setText("Un fisier selectat: " + data.getData().getLastPathSegment());
        }
        else {

            makeText(InterfataProfesor.this, "Va rugam selectati fisierul", Toast.LENGTH_SHORT).show();
        }


    }
    public void functiaSetSpinner(){
        listener =  databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item:dataSnapshot.getChildren()){

                    arrayList.add(item.getValue().toString());
                }
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
//////////////////////////////////////////////////////////////////////////////////////////////

    public void retriveSpinerElev(){
        listenerElev =  dbElev.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item:dataSnapshot.getChildren()){

                    listElev.add(item.getValue().toString());
                }
                adapterElev.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void retriveSpinerMaterie(){
        listenerMaterie =  dbMaterie.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item:dataSnapshot.getChildren()){

                    listMaterie.add(item.getValue().toString());
                }

                adapterMaterie.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseAdd = FirebaseDatabase.getInstance();
        refElev = database.getReference("Elevi");
        refMaterie=database.getReference("Elevi");
        refNota=database.getReference("Elevi");

    }


    public void btn(View view) {
        frameLayout1.setVisibility(View.VISIBLE);
        frameLayout2.setVisibility(View.INVISIBLE);
        frameLayout3.setVisibility(View.INVISIBLE);
    }

    public void btn2(View view) {

        frameLayout1.setVisibility(View.INVISIBLE);
        frameLayout2.setVisibility(View.VISIBLE);
        frameLayout3.setVisibility(View.INVISIBLE);


    }

    public void btn3(View view) {
        frameLayout1.setVisibility(View.INVISIBLE);
        frameLayout2.setVisibility(View.INVISIBLE);
        frameLayout3.setVisibility(View.VISIBLE);

    }

    public void adaugaElev(View view)
        {
            refElev.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //getValues();
                    String str=etElev.getText().toString().trim();
                    refElev.child("Nume").child(str).setValue(str);
                    Toast.makeText(InterfataProfesor.this, "Adaugat cu succes", Toast.LENGTH_LONG).show();
                    spinnerElev.setAdapter(null);
                    spinnerElev.setAdapter(adapterElev);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }




    public void adaugaMaterie(View view) {
        refMaterie.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //getValues();
                String str=etMaterie.getText().toString().trim();
                refMaterie.child("Materii").child(str).setValue(str);
                Toast.makeText(InterfataProfesor.this, "Adaugat cu succes", Toast.LENGTH_LONG).show();
                spinnerMaterie.setAdapter(null);
                spinnerMaterie.setAdapter(adapterMaterie);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void adaugaNota(View view) {
i=0;
randomString=null;
        refNota.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(i==0) {i++;
                    final int min = 100000;
                    final int max = 999999;
                    final int random = new Random().nextInt((max - min) + 1) + min;
                     randomString=null;
                randomString =random+"";}
                String itemElev=spinnerElev.getSelectedItem().toString().trim();
                String itemMaterie=spinnerMaterie.getSelectedItem().toString().trim();
                String str=etNota.getText().toString().trim();
                refNota.child("Note").child(itemElev).child(itemMaterie).child(randomString).setValue(str);

                  Toast.makeText(InterfataProfesor.this, "Adaugat cu succes", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


}
