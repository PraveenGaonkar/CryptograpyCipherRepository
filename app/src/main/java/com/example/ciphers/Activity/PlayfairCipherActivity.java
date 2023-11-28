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
private static char[][] keyMatrix;
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
                    createKeyMatrix(keyword);
                    keyTable(keyMatrix);
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

    private static void createKeyMatrix(String key) {
        keyMatrix = new char[5][5];
        String keyString = sanitizeKey(key);
        int index = 0;

        // Fill the key matrix with unique letters from the key
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                keyMatrix[i][j] = keyString.charAt(index++);
            }
        }
    }

    private static String sanitizeKey(String key) {
        // Remove duplicate letters from the key and replace 'J' with 'I'
        StringBuilder sanitizedKey = new StringBuilder();
        boolean[] seen = new boolean[26];

        for (char c : key.toCharArray()) {
            if (c == 'J') {
                c = 'I';
            }
            if (!seen[c - 'A']) {
                sanitizedKey.append(c);
                seen[c - 'A'] = true;
            }
        }

        // Fill the rest of the key matrix with remaining unused letters
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J' && !seen[c - 'A']) {
                sanitizedKey.append(c);
            }
        }

        return sanitizedKey.toString();
    }

    private static String encryption(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i += 2) {
            char first = plaintext.charAt(i);
            char second = (i + 1 < plaintext.length()) ? plaintext.charAt(i + 1) : 'X';

            // Skip spaces
            if (Character.isWhitespace(first)) {
                ciphertext.append(first);
                i--; // Revisit the same position for the next pair
                continue;
            }

            int[] firstPos = findPosition(first);
            int[] secondPos = findPosition(second);

            if (firstPos[0] == secondPos[0]) { // Same row
                ciphertext.append(keyMatrix[firstPos[0]][(firstPos[1] + 1) % 5]);
                ciphertext.append(keyMatrix[secondPos[0]][(secondPos[1] + 1) % 5]);
            } else if (firstPos[1] == secondPos[1]) { // Same column
                ciphertext.append(keyMatrix[(firstPos[0] + 1) % 5][firstPos[1]]);
                ciphertext.append(keyMatrix[(secondPos[0] + 1) % 5][secondPos[1]]);
            } else { // Forming a rectangle
                ciphertext.append(keyMatrix[firstPos[0]][secondPos[1]]);
                ciphertext.append(keyMatrix[secondPos[0]][firstPos[1]]);
            }
        }

        return ciphertext.toString();
    }

    private static String decryption(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i += 2) {
            char first = ciphertext.charAt(i);
            char second = ciphertext.charAt(i + 1);

            // Skip spaces
            if (Character.isWhitespace(first)) {
                plaintext.append(first);
                i--; // Revisit the same position for the next pair
                continue;
            }

            int[] firstPos = findPosition(first);
            int[] secondPos = findPosition(second);

            if (firstPos[0] == secondPos[0]) { // Same row
                plaintext.append(keyMatrix[firstPos[0]][(firstPos[1] - 1 + 5) % 5]);
                plaintext.append(keyMatrix[secondPos[0]][(secondPos[1] - 1 + 5) % 5]);
            } else if (firstPos[1] == secondPos[1]) { // Same column
                plaintext.append(keyMatrix[(firstPos[0] - 1 + 5) % 5][firstPos[1]]);
                plaintext.append(keyMatrix[(secondPos[0] - 1 + 5) % 5][secondPos[1]]);
            } else { // Forming a rectangle
                plaintext.append(keyMatrix[firstPos[0]][secondPos[1]]);
                plaintext.append(keyMatrix[secondPos[0]][firstPos[1]]);
            }
        }

        return plaintext.toString();
    }

    private static int[] findPosition(char c) {
        int[] result = new int[2];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keyMatrix[i][j] == c) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }

        return result;
    }
}