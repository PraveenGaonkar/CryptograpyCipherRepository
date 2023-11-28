package com.example.ciphers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ciphers.Adapter.CipherListAdapter;
import com.example.ciphers.Model.CipherModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    CipherListAdapter cipherListAdapter;
    ArrayList<CipherModel> arrCipher = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recylerCipher);
        setSupportActionBar(toolbar);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        cipherListAdapter = new CipherListAdapter(MainActivity.this, getCipherList());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cipherListAdapter);
    }

    private ArrayList<CipherModel> getCipherList(){
        arrCipher.add(new CipherModel("Caesar Cipher"));
        arrCipher.add(new CipherModel("Vigenere Cipher"));
        arrCipher.add(new CipherModel("One Time Pad"));
        arrCipher.add(new CipherModel("Playfair Cipher"));
        arrCipher.add(new CipherModel("Affine Cipher"));
        arrCipher.add(new CipherModel("AES"));
        return arrCipher;
    }
}