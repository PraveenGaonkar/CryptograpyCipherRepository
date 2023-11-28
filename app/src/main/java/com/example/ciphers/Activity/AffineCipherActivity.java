package com.example.ciphers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ciphers.Algorithms.AESAlgorithm;
import com.example.ciphers.Algorithms.AffineCipherAlgorithm;
import com.example.ciphers.R;
import com.google.android.material.button.MaterialButton;

public class AffineCipherActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText plainText, cipherText, value1, value2,cipherTextToPlaintext;
    MaterialButton encryptMessage, decryptMessage;
    ImageView copyCipherText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affine_cipher);

        toolbar = findViewById(R.id.toolbar);
        cipherText = findViewById(R.id.cipherText);
        value1 = findViewById(R.id.value1);
        value2 = findViewById(R.id.value2);
        plainText = findViewById(R.id.plainText);
        encryptMessage = findViewById(R.id.encryptMessage);
        decryptMessage = findViewById(R.id.decryptMessage);
        copyCipherText = findViewById(R.id.copyCipherText);
        cipherTextToPlaintext = findViewById(R.id.cipherTextToPlaintext);

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        encryptMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!plainText.getText().toString().isEmpty() && (!value1.getText().toString().isEmpty() && !value1.getText().toString().isEmpty())) {
                    String message = plainText.getText().toString().toUpperCase().trim();
                    int v1 = Integer.parseInt(value1.getText().toString().toUpperCase().trim());
                    int v2 = Integer.parseInt(value2.getText().toString().toUpperCase().trim());
                    if (isCoprime(v1, 26)) {
                        String encryptedText = encryption(message, v1, v2);
                        cipherText.setText(encryptedText);
                    } else {
                        Toast.makeText(getApplicationContext(), "The value of 'a' (must be coprime to 26)", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Plaintext or key cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        decryptMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cipherText.getText().toString().isEmpty() && (!value1.getText().toString().isEmpty() && !value1.getText().toString().isEmpty())) {
                    String encryptedText = cipherText.getText().toString().toUpperCase().trim();
                    int v1 = Integer.parseInt(value1.getText().toString().toUpperCase().trim());
                    int v2 = Integer.parseInt(value2.getText().toString().toUpperCase().trim());
                    String decryptedText = decryption(encryptedText, v1, v2);
                    cipherTextToPlaintext.setText(decryptedText);
//                    cipherTextToPlaintext.setText(plainText.getText().toString().toUpperCase().trim());
                }else{
                    Toast.makeText(getApplicationContext(), "Ciphertext or key cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyCipherText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = cipherText.getText().toString().toUpperCase().trim();
                if(text.length() != 0) {
                    ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", text);
                    clipboard.setPrimaryClip(clip);
                }else{
                    Toast.makeText(getApplicationContext(), "CipherText is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Encryption method
    public static String encryption(String plaintext, int a, int b) {
        StringBuilder ciphertext = new StringBuilder();

        for (char character : plaintext.toCharArray()) {
            if (Character.isLetter(character)) {
                int x = (int) character - (Character.isUpperCase(character) ? 'A' : 'a');
                int encryptedChar = (a * x + b) % 26;
                if (encryptedChar < 0) {
                    encryptedChar += 26; // Handle negative values
                }
                encryptedChar += (Character.isUpperCase(character) ? 'A' : 'a');
                ciphertext.append((char) encryptedChar);
            } else {
                ciphertext.append(character);
            }
        }

        return ciphertext.toString();
    }

    public static String decryption(String ciphertext, int a, int b) {
        int aInverse = modInverse(a, 26);

        StringBuilder plaintext = new StringBuilder();

        for (char character : ciphertext.toCharArray()) {
            if (Character.isLetter(character)) {
                int y = (int) character - (Character.isUpperCase(character) ? 'A' : 'a');
                int decryptedChar = (aInverse * (y - b)) % 26;
                if (decryptedChar < 0) {
                    decryptedChar += 26; // Handle negative values
                }
                decryptedChar += (Character.isUpperCase(character) ? 'A' : 'a');
                plaintext.append((char) decryptedChar);
            } else {
                plaintext.append(character);
            }
        }

        return plaintext.toString();
    }


    // Helper method to calculate the modular multiplicative inverse
    private static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1;
    }

    // Helper method to check if two numbers are coprime
    private static boolean isCoprime(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a == 1;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.algorithm){
            Intent intent = new Intent(AffineCipherActivity.this, AffineCipherAlgorithm.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}