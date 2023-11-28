package com.example.ciphers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ciphers.Algorithms.AESAlgorithm;
import com.example.ciphers.Algorithms.DESAlgorithm;
import com.example.ciphers.R;
import com.google.android.material.button.MaterialButton;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText plainText, key, cipherText, cipherTextToPlaintext;
    MaterialButton encryptMessage, decryptMessage,generateKey;
    String encryptedText;
    SecretKey secretKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aesacitivity);

        toolbar = findViewById(R.id.toolbar);
        plainText = findViewById(R.id.plainText);
        key = findViewById(R.id.key);
        cipherText = findViewById(R.id.cipherText);
        encryptMessage = findViewById(R.id.encryptMessage);
        decryptMessage = findViewById(R.id.decryptMessage);
        generateKey = findViewById(R.id.generateKey);
        cipherTextToPlaintext = findViewById(R.id.cipherTextToPlaintext);

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        generateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!plainText.getText().toString().isEmpty()) {
                    try {
                        secretKey = generateAESKey();
                        String keyText = android.util.Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT);
                        key.setText(keyText);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    Toast.makeText(AESActivity.this, "Plaintext cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }

        });

        encryptMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!plainText.getText().toString().isEmpty() && !key.getText().toString().isEmpty()){
                    try {
                        String message = plainText.getText().toString().trim().toUpperCase();
                        encryptedText = encryption(message, secretKey);
                        cipherText.setText(encryptedText);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(AESActivity.this, "Plaintext or key cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        decryptMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cipherText.getText().toString().isEmpty()) {
                    try {
                        String decryptedText = decryption(encryptedText, secretKey);
                        cipherTextToPlaintext.setText(decryptedText);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(AESActivity.this, "Ciphertext cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256); // Use 128, 192, or 256 bits key size
        return keyGenerator.generateKey();
    }

    private String encryption(String plainText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return android.util.Base64.encodeToString(encryptedBytes, android.util.Base64.DEFAULT);
    }

    private String decryption(String encryptedText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedBytes = android.util.Base64.decode(encryptedText, android.util.Base64.DEFAULT);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.algorithm){
            Intent intent = new Intent(AESActivity.this, AESAlgorithm.class);
            startActivity(intent);
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}