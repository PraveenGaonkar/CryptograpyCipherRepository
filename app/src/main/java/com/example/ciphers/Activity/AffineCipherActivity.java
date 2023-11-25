package com.example.ciphers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ciphers.R;
import com.google.android.material.button.MaterialButton;

public class AffineCipherActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText plainText, key, cipherText, value1, value2,cipherTextToPlaintext;
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
        copyCipherText =findViewById(R.id.copyCipherText);
        cipherTextToPlaintext =findViewById(R.id.cipherTextToPlaintext);

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
                    if (areCoprime(v1, v2)) {
                        String encryptedText = encryption(message, v1, v2);
                        cipherText.setText(encryptedText);
                    } else {
                        Toast.makeText(getApplicationContext(), "Numbers are not coprime", Toast.LENGTH_SHORT).show();
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
                    cipherTextToPlaintext.setText(plainText.getText().toString().toUpperCase().trim());
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Function to calculate the greatest common divisor (GCD) using Euclidean Algorithm
    static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Function to check if two numbers are coprime
    static boolean areCoprime(int num1, int num2) {
        return gcd(num1, num2) == 1;
    }

    public static String encryption(String plaintext, int a, int b) {
        StringBuilder ciphertext = new StringBuilder();

        for (char ch : plaintext.toCharArray()) {
            if (Character.isLetter(ch)) {
                char encryptedChar = encryptChar(ch, a, b);
                ciphertext.append(encryptedChar);
            } else {
                // Keep non-alphabetic characters unchanged
                ciphertext.append(ch);
            }
        }

        return ciphertext.toString();
    }

    // Encrypts a single character using Affine cipher
    private static char encryptChar(char ch, int a, int b) {
        int m = 26; // size of the English alphabet

        // Convert character to uppercase for simplicity
        ch = Character.toUpperCase(ch);

        // Apply Affine cipher formula: E(x) = (ax + b) mod m
        int x = ch - 'A';
        int encryptedCharValue = (a * x + b) % m;

        // Ensure the result is positive
        if (encryptedCharValue < 0) {
            encryptedCharValue += m;
        }

        // Convert back to character
        char encryptedChar = (char) (encryptedCharValue + 'A');

        return encryptedChar;
    }
}