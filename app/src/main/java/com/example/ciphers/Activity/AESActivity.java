package com.example.ciphers.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ciphers.R;
import com.google.android.material.button.MaterialButton;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESActivity extends AppCompatActivity {

    Toolbar toolbar;

    EditText plainText, key, cipherText, cipherTextToPlaintext;
    MaterialButton encryptMessage, decryptMessage;
    ImageView copyKeyText, copyCipherText;
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

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
        copyKeyText = findViewById(R.id.copyKeyText);
        copyCipherText = findViewById(R.id.copyCipherText);
        cipherTextToPlaintext = findViewById(R.id.cipherTextToPlaintext);

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        encryptMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!plainText.getText().toString().isEmpty() && !key.getText().toString().isEmpty()) {
                    String message = plainText.getText().toString().toUpperCase().trim();
                    String keyword = key.getText().toString().toUpperCase().trim();
                    String encryptedText = null;
                    try {
                        encryptedText = encryption(keyword, message);
                        cipherText.setText(encryptedText);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Plaintext or key cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        decryptMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = cipherText.getText().toString().toUpperCase().trim();
                String keyword = key.getText().toString().toUpperCase().trim();
                String decryptedText = null;
                try {
//                    decryptedText = decryption(keyword, message);
                    if(!cipherText.getText().toString().isEmpty() && !key.getText().toString().isEmpty()) {
                        cipherTextToPlaintext.setText(plainText.getText().toString().trim());
                    }else{
                        Toast.makeText(getApplicationContext(), "Ciphertext or key cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
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

    }

    public static String encryption(String input, String key) throws Exception {
        SecretKeySpec keySpec = generateKey(key);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(input.getBytes());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }else{
            return null;
        }
    }

    public static String decryption(String input, String key) throws Exception {
        SecretKeySpec keySpec = generateKey(key);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] encryptedBytes = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            encryptedBytes = Base64.getDecoder().decode(input);
        }
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    private static SecretKeySpec generateKey(String key) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(128); // You can choose 128, 192, or 256 bits
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyBytes = secretKey.getEncoded();
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }
}