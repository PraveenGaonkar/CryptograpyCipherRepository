package com.example.ciphers.Fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ciphers.R;
import com.google.android.material.button.MaterialButton;

public class CaesarCipherEncryptionFragment extends Fragment {

    EditText plainText, key, cipherText;
    MaterialButton encryptMessage;
    ImageView copyKeyText, copyCipherText;
    public CaesarCipherEncryptionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_caesar_cipher_encryption, container, false);

        plainText = view.findViewById(R.id.plainText);
        key = view.findViewById(R.id.key);
        cipherText = view.findViewById(R.id.cipherText);
        encryptMessage = view.findViewById(R.id.encryptMessage);
        copyKeyText = view.findViewById(R.id.copyKeyText);
        copyCipherText = view.findViewById(R.id.copyCipherText);

        encryptMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = plainText.getText().toString().toUpperCase();
                if(!message.isEmpty() && !key.getText().toString().isEmpty()) {
                    int shiftValue = Integer.parseInt(key.getText().toString().toUpperCase());
                    String encryptedText = encryption(message, shiftValue);
                    cipherText.setText(encryptedText);
                }else{
                    Toast.makeText(getContext(), "Plaintext or key cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyKeyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!key.getText().toString().isEmpty()) {
                    int shiftValue = Integer.parseInt(key.getText().toString().toUpperCase());
                    ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", String.valueOf(shiftValue));
                    clipboard.setPrimaryClip(clip);
                }else{
                    Toast.makeText(getContext(), "Key is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyCipherText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = cipherText.getText().toString().toUpperCase();
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

    public static String encryption(String message, int shift) {
        StringBuilder result = new StringBuilder();

        for (char character : message.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                result.append((char) ((character - base + shift) % 26 + base));
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }
}