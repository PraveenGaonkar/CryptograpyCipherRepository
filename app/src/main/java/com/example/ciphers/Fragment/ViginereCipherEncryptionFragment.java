package com.example.ciphers.Fragment;

import static android.content.ContentValues.TAG;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ciphers.R;
import com.google.android.material.button.MaterialButton;

public class ViginereCipherEncryptionFragment extends Fragment {

    EditText plainText, key, cipherText;
    MaterialButton encryptMessage;
    ImageView copyKeyText, copyCipherText;
    public ViginereCipherEncryptionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_viginere_cipher_encryption, container, false);

        plainText = view.findViewById(R.id.plainText);
        key = view.findViewById(R.id.key);
        cipherText = view.findViewById(R.id.cipherText);
        encryptMessage = view.findViewById(R.id.encryptMessage);
        copyKeyText = view.findViewById(R.id.copyKeyText);
        copyCipherText = view.findViewById(R.id.copyCipherText);

        encryptMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = plainText.getText().toString().toUpperCase().trim();
                String k = key.getText().toString().toUpperCase().trim();
                if(!message.isEmpty() && !key.getText().toString().isEmpty()) {
                    String encryptedText = encryption(message, k);
                    cipherText.setText(encryptedText);
                }else{
                    Toast.makeText(getContext(), "Plaintext or key cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyKeyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyText = key.getText().toString().toUpperCase().trim();
                if(keyText.length() != 0) {
                    ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", keyText);
                    clipboard.setPrimaryClip(clip);
                }else{
                    Toast.makeText(getContext(), "Key is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyCipherText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = cipherText.getText().toString().toUpperCase().trim();
                if(text.length() != 0) {
                    ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", text);
                    clipboard.setPrimaryClip(clip);
                }else{
                    Toast.makeText(getContext(), "CipherText is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public static String encryption(String plainText, String key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++) {
            char character = plainText.charAt(i);
            if (character == ' ') {
                result.append(' ');
                continue;
            }
            int charCode = (int) character;
            int shift = key.charAt(i % key.length()) - 65;
            char encryptedChar = (char) (((charCode + shift - 65) % 26) + 65);
            result.append(encryptedChar);
        }
        return result.toString();
    }
}