package com.example.ciphers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ciphers.Algorithms.OneTimePadAlgorithm;
import com.example.ciphers.Algorithms.PlayfairCipherAlgorithm;
import com.example.ciphers.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class PlayfairCipherActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText plainText, key, cipherText, cipherTextToPlaintext;
    MaterialButton encryptMessage, decryptMessage;
    ImageView copyKeyText, copyCipherText;
    TextView keyMatrixTextView;
    private char[][] keyTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playfair_cipher);

        toolbar = findViewById(R.id.toolbar);
        plainText = findViewById(R.id.plainText);
        key = findViewById(R.id.key);
        cipherText = findViewById(R.id.cipherText);
        encryptMessage = findViewById(R.id.encryptMessage);
        decryptMessage = findViewById(R.id.decryptMessage);
        copyKeyText = findViewById(R.id.copyKeyText);
        copyCipherText = findViewById(R.id.copyCipherText);
        cipherTextToPlaintext = findViewById(R.id.cipherTextToPlaintext);
        keyMatrixTextView = findViewById(R.id.keyMatrixTextView);

        encryptMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!plainText.getText().toString().isEmpty() && !key.getText().toString().isEmpty()) {
                    String keyword = key.getText().toString().toUpperCase().trim();
                    keyTable = generateKeyTable(keyword);
                    keyTable(keyTable);
                    String message = plainText.getText().toString().toUpperCase().trim();
                    String encryptedText = encryption(message);
                    cipherText.setText(encryptedText);
                }else{
                    Toast.makeText(getApplicationContext(), "Plaintext or key cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        decryptMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cipherText.getText().toString().isEmpty() && !key.getText().toString().isEmpty()) {
                    String message = cipherText.getText().toString().toUpperCase().trim();
                    String decryptedText = decryption(message);
                    cipherTextToPlaintext.setText(decryptedText);
                }else{
                    Toast.makeText(getApplicationContext(), "Ciphertext or key cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyKeyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!key.getText().toString().isEmpty()) {
                    int shiftValue = Integer.parseInt(key.getText().toString().toUpperCase());
                    ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", String.valueOf(shiftValue));
                    clipboard.setPrimaryClip(clip);
                }else{
                    Toast.makeText(getApplicationContext(), "Key is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyCipherText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = cipherText.getText().toString().toUpperCase();
                if(text.length() != 0) {
                    ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", text);
                    clipboard.setPrimaryClip(clip);
                }else{
                    Toast.makeText(getApplicationContext(), "CipherText is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.algorithm){
            Intent intent = new Intent(PlayfairCipherActivity.this, PlayfairCipherAlgorithm.class);
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

    private char[][] generateKeyTable(String key) {
        key = key.toUpperCase().replace("J", "I");
        char[][] keyTable = new char[5][5];
        List<Character> keyList = new ArrayList<>();

        // Create a list of unique characters in the key
        for (char c : key.toCharArray()) {
            if (!keyList.contains(c)) {
                keyList.add(c);
            }
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J' && !keyList.contains(c)) {
                keyList.add(c);
            }
        }

        int index = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                keyTable[i][j] = keyList.get(index++);
            }
        }

        return keyTable;
    }

    private String preprocessText(String text) {
        return text.toUpperCase().replaceAll("[^A-Z ]", "");
    }

    private String encryption(String plainText) {
        plainText = preprocessText(plainText);
        StringBuilder cipherText = new StringBuilder();

        for (int i = 0; i < plainText.length(); i += 2) {
            char firstChar = plainText.charAt(i);
            char secondChar;

            if (i + 1 < plainText.length()) {
                secondChar = plainText.charAt(i + 1);
            } else {
                // If the plaintext has an odd length, append 'X' to make it even
                secondChar = 'X';
            }

            if (firstChar == ' ' || secondChar == ' ') {
                // If either character is a space, append it directly
                cipherText.append(firstChar);
                cipherText.append(secondChar);
            } else {
                int[] firstPosition = findCharPosition(firstChar);
                int[] secondPosition = findCharPosition(secondChar);

                // Apply the Playfair cipher rules
                if (firstPosition[0] == secondPosition[0]) {
                    // Same row
                    cipherText.append(keyTable[firstPosition[0]][(firstPosition[1] + 1) % 5]);
                    cipherText.append(keyTable[secondPosition[0]][(secondPosition[1] + 1) % 5]);
                } else if (firstPosition[1] == secondPosition[1]) {
                    // Same column
                    cipherText.append(keyTable[(firstPosition[0] + 1) % 5][firstPosition[1]]);
                    cipherText.append(keyTable[(secondPosition[0] + 1) % 5][secondPosition[1]]);
                } else {
                    // Form a rectangle
                    cipherText.append(keyTable[firstPosition[0]][secondPosition[1]]);
                    cipherText.append(keyTable[secondPosition[0]][firstPosition[1]]);
                }
            }
        }

        return cipherText.toString();
    }

    private String decryption(String cipherText) {
        cipherText = preprocessText(cipherText);
        StringBuilder plainText = new StringBuilder();

        for (int i = 0; i < cipherText.length(); i += 2) {
            char firstChar = cipherText.charAt(i);
            char secondChar;

            if (i + 1 < cipherText.length()) {
                secondChar = cipherText.charAt(i + 1);
            } else {
                // If the ciphertext has an odd length, append 'X' to make it even
                secondChar = 'X';
            }

            if (firstChar == ' ' || secondChar == ' ') {
                // If either character is a space, append it directly
                plainText.append(firstChar);
                plainText.append(secondChar);
            } else {
                int[] firstPosition = findCharPosition(firstChar);
                int[] secondPosition = findCharPosition(secondChar);

                // Apply the Playfair cipher decryption rules
                if (firstPosition[0] == secondPosition[0]) {
                    // Same row
                    plainText.append(keyTable[firstPosition[0]][(firstPosition[1] - 1 + 5) % 5]);
                    plainText.append(keyTable[secondPosition[0]][(secondPosition[1] - 1 + 5) % 5]);
                } else if (firstPosition[1] == secondPosition[1]) {
                    // Same column
                    plainText.append(keyTable[(firstPosition[0] - 1 + 5) % 5][firstPosition[1]]);
                    plainText.append(keyTable[(secondPosition[0] - 1 + 5) % 5][secondPosition[1]]);
                } else {
                    // Form a rectangle
                    plainText.append(keyTable[firstPosition[0]][secondPosition[1]]);
                    plainText.append(keyTable[secondPosition[0]][firstPosition[1]]);
                }
            }
        }

        return plainText.toString();
    }


    private int[] findCharPosition(char c) {
        int[] position = new int[2];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keyTable[i][j] == c) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }

        return position;
    }

    private void keyTable(char[][] printTable) {
        StringBuilder keyMatrixText = new StringBuilder("Playfair Cipher Key Matrix:\n\n");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                keyMatrixText.append(printTable[i][j]).append("  ");
            }
            keyMatrixText.append("\n");
        }
        displayKeyMatrix(keyMatrixText.toString());
    }

    private void displayKeyMatrix(String matrixText) {
        if (keyMatrixTextView != null) {
            keyMatrixTextView.setText(matrixText);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}