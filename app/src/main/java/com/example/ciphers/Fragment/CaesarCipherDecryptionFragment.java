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

public class CaesarCipherDecryptionFragment extends Fragment {

    EditText cipherText,key,plainText;
    MaterialButton decryptMessage;
    ImageView copyKey,copyPlainText;

    public CaesarCipherDecryptionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_caesar_cipher_decryption, container, false);

        cipherText = view.findViewById(R.id.cipherText);
        key = view.findViewById(R.id.key);
        plainText = view.findViewById(R.id.plainText);
        decryptMessage = view.findViewById(R.id.decryptMessage);
        copyKey = view.findViewById(R.id.copyKeyText);
        copyPlainText = view.findViewById(R.id.copyPlainText);

        decryptMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = cipherText.getText().toString().toUpperCase();
                if(!message.isEmpty() && !key.getText().toString().isEmpty()) {
                    int k = Integer.parseInt(key.getText().toString().toUpperCase());
                    String decryptedText = decryption(message, 26 - k);
                    plainText.setText(decryptedText);
                }else{
                    Toast.makeText(getContext(), "Ciphertext or key cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = key.getText().toString().toUpperCase();
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
                String text = plainText.getText().toString().toUpperCase();
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

    public static String decryption(String message, int shift) {
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