package com.example.proiectmesotavariantafinala.ui.gallery;

import android.R.layout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proiectmesotavariantafinala.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    Spinner spinnerElev, spinnerMaterie;
    ListView listViewNote;
    Button btnAfiseazaNota;
    String textElev,textMaterie;

    DatabaseReference dbElev, dbMaterie, dbNote;

    ValueEventListener listenerElev, listenerMaterie, listenerNota;

    ArrayAdapter<String> adapterElev, adapterMaterie, adapterNote;
   private ArrayList<String> listElev, listMaterie, listNote;

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        spinnerElev=root.findViewById(R.id.spinnerElev);
        spinnerMaterie=root.findViewById(R.id.spinnerMaterie);
        listViewNote=root.findViewById(R.id.listView);
        btnAfiseazaNota=root.findViewById(R.id.btnNota);


        dbElev= FirebaseDatabase.getInstance().getReference("Elevi").child("Nume");
        dbMaterie= FirebaseDatabase.getInstance().getReference("Elevi").child("Materii");

        listElev= new ArrayList<>();
        listMaterie= new ArrayList<>();
        listNote=new ArrayList<>();


        adapterElev=new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,listElev);
        adapterMaterie=new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,listMaterie);
       adapterNote=new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_selectable_list_item,listNote);

        spinnerElev.setAdapter(adapterElev);
        spinnerMaterie.setAdapter(adapterMaterie);
        listViewNote.setAdapter(adapterNote);
        retriveSpinerElev();
        retriveSpinerMaterie();
        //retriveListNota();

       // final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
    //            textView.setText(s);
            }
        });


        btnAfiseazaNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               textElev=spinnerElev.getSelectedItem().toString().trim();
                textMaterie=spinnerMaterie.getSelectedItem().toString().trim();
                dbNote= FirebaseDatabase.getInstance().getReference("Elevi").child("Note").child(textElev).child(textMaterie);
listNote.clear();
                retriveListNota();


            }
        });



        return root;
    }


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

    }

    public void retriveListNota(){
        listenerNota =  dbNote.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item:dataSnapshot.getChildren()){

                    listNote.add(item.getValue().toString());
                }
                adapterNote.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}