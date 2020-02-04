package com.example.proiectmesotavariantafinala.ui.tools;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proiectmesotavariantafinala.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.makeText;

public class ToolsFragment extends Fragment {

    Button selectFile, upload;
    TextView notification;
    Uri pdfUri; //adresa pdf
    TextView nume;

    FirebaseStorage storage; //pentru incarcare fisier
    FirebaseDatabase database; // pentru memorare adresa fisier
    ProgressDialog progressDialog;

    private ToolsViewModel toolsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);


        storage = FirebaseStorage.getInstance(); //returneaza un obiect Firebase Storage
        database = FirebaseDatabase.getInstance(); // returneaza un obiect Firebase Databse

        selectFile = root.findViewById(R.id.selectFile);
        upload = root.findViewById(R.id.upload);
        notification = root.findViewById(R.id.notification);
        nume=root.findViewById(R.id.nameFile);



        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    selectPdf();
                }
                else
                {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pdfUri != null)
                    uploadFile(pdfUri);
                else
                    makeText(getActivity(), "Selecteaza fisier", Toast.LENGTH_SHORT).show();
            }

            private void uploadFile(final Uri pdfUri) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setTitle("Incarcare fisier...");
                progressDialog.show();

                final String fileName =System.currentTimeMillis()+"";
                final String fileNameFinal=nume.getText().toString()+"-"+fileName;


                //    StorageReference reference =  storage.child("uploads/"+System.currentTimeMillis()+".pdf");

                //      StorageReference storageReference = storage.getRFeference();
                final StorageReference storageReference = storage.getReference();

                storageReference.child("Teme").child(fileNameFinal).putFile(pdfUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Toast.makeText(getActivity(), "Se verifica", Toast.LENGTH_SHORT).show();
                                String url =taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                                //      Task<Uri> url =taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                DatabaseReference reference = database.getReference();

                                // Map<String,String> doc = new HashMap<>();
                                //  doc.put("url", url);
                                reference.child("Teme").child(fileNameFinal).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                            Toast.makeText(getActivity(), "Fisier incarcat cu succes", Toast.LENGTH_SHORT).show();
                                        else

                                            Toast.makeText(getActivity(), "Eroare incarcare fisier1", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                 //  startActivity(new Intent(this,ToolsFragment.class));
                                 //        Intent intent = new Intent(getContext(), ToolsFragment.class);
                                 //       startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getActivity(), "Eroare incarcare fisier2", Toast.LENGTH_SHORT).show();
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
                            stergeUri();

                           Uri pdfUri = null;

                        }
                    }
                });

            }

        });




        return root;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED)

            selectPdf();

        else
            makeText(getContext(), "Va rugam verificati permisiunile...", Toast.LENGTH_SHORT).show();
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

            makeText(getActivity(), "Va rugam selectati fisierul", Toast.LENGTH_SHORT).show();
        }


    }



}