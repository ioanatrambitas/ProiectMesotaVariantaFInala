package com.example.proiectmesotavariantafinala.ui.downloadElev;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.proiectmesotavariantafinala.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DownloadFragmentElev extends Fragment {
    Spinner spinner;
    Button btnDownload;
    String text;

    DatabaseReference databaseReference;

    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    private ArrayList<String> list;

    private DownloadModelElev slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(DownloadModelElev.class);
        View root = inflater.inflate(R.layout.fragment_elev_download, container, false);

        spinner=root.findViewById(R.id.spinner);

        btnDownload=root.findViewById(R.id.buttonDownload);


        databaseReference= FirebaseDatabase.getInstance().getReference("Teme");

        list= new ArrayList<>();



        adapter=new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,list);


        spinner.setAdapter(adapter);
        retriveLectii();

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text=spinner.getSelectedItem().toString().trim();
                String url = "www.facebook.ro";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });


        return root;
    }


    public void retriveLectii(){
        listener =  databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item:dataSnapshot.getChildren()){

                    list.add(item.getValue().toString());
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}