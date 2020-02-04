package com.example.proiectmesotavariantafinala.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proiectmesotavariantafinala.Logare;
import com.example.proiectmesotavariantafinala.MainActivity;
import com.example.proiectmesotavariantafinala.R;

public class HomeFragment extends Fragment {
    TextView mEmail;


    TextView b11,b21,b22,b23,b31,b32,b41,b51;


    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView= root.findViewById(R.id.text_home);
        b11= root.findViewById(R.id.acasa_id11);
        b21= root.findViewById(R.id.acasa_id21);
        b22= root.findViewById(R.id.acasa_id22);
        b23= root.findViewById(R.id.acasa_id23);
        b31= root.findViewById(R.id.acasa_id31);
        b32= root.findViewById(R.id.acasa_id32);
        b41= root.findViewById(R.id.acasa_id41);
        b51= root.findViewById(R.id.acasa_id51);



        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v=Q_O-NUN6LYY&feature=emb_logo";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });

        b21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v=M1oBKqUTG6I&feature=youtu.be";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });

        b22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.bzb.ro/stire/aniversati-cu-un-ministru-mesota-150-de-ani-trei-zile-de-sarbatoare-a138915";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });


        b23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://photos.google.com/share/AF1QipNSlqLD2j8xu-tCP-tlGY2810bM4NX7bg-hkkMGQZh281gBHNccMmLusynp71pH0Q?key=NWRIMlBKX3VaZExMYWNHLWhzU0Q3RzctZzZYeU5R";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });
        b31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://mesota.ro/nou/pdf/2020-astronomie.pdf";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });
        b32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://mesota.ro/nou/pdf/2020-limbi_clasice.pdf";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });

        b41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Info1.class));



            }
        });

        b51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Info2.class));


            }
        });










        return root;
    }
}