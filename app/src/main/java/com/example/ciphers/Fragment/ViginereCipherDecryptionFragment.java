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

public class ViginereCipherDecryptionFragment extends Fragment {

    EditText cipherText,key,plainText;
    MaterialButton decryptMessage;
    ImageView copyKey,copyPlainText;
    public ViginereCipherDecryptionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viginere_cipher_decryption, container, false);

        cipherText = view.findViewById(R.id.cipherText);
        key = view.findViewById(R.id.key);
        plainText = view.findViewById(R.id.plainText);
        decryptMessage = view.findViewById(R.id.decryptMessage);
        copyKey = view.findViewById(R.id.copyKey);
        copyPlainText = view.findViewById(R.id.copyPlainText);

        decryptMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = cipherText.getText().toString().toUpperCase().trim();
                String k = key.getText().toString().toUpperCase().trim();
                if(!message.isEmpty() && !key.getText().toString().isEmpty()) {
                    String decryptedText = decryption(message, k);
                    plainText.setText(decryptedText);
                }else{
                    Toast.makeText(getContext(), "Ciphertext or key cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = key.getText().toString().toUpperCase().trim();
                if(text.length() != 0) {
                    ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", text);
                    clipboard.setPrimaryClip(clip);
                }else{
                    Toast.makeText(getContext(), "Key is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyPlainText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = plainText.getText().toString().toUpperCase().trim();
                if(text.length() != 0) {
                    ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", text);
                    clipboard.setPrimaryClip(clip);
                }else{
                    Toast.makeText(getContext(), "PlainText is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public static String decryption(String cipherText, String key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i++) {
            char character = cipherText.charAt(i);
            if (character == ' ') {
                result.append(' ');
                continue;
            }
            int charCode = (int) character;
            int shift = key.charAt(i % key.length()) - 65;
            char decryptedChar = (char) (((charCode - shift - 65 + 26) % 26) + 65);
            result.append(decryptedChar);
        }
        return result.toString();
    }
}