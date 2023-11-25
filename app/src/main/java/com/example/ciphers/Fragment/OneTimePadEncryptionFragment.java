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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ciphers.R;
import com.google.android.material.button.MaterialButton;

import java.util.Random;

public class OneTimePadEncryptionFragment extends Fragment {

    EditText plainText,key,cipherText;
    MaterialButton generateKey, encryptMessage;
    private static final int MAX = 26;
    String message;
    ImageView copyKey, copyCipherText;
    public OneTimePadEncryptionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_time_pad_encryption, container, false);

        plainText = view.findViewById(R.id.plainText);
        key = view.findViewById(R.id.key);
        cipherText = view.findViewById(R.id.cipherText);
        generateKey = view.findViewById(R.id.generateKey);
        encryptMessage = view.findViewById(R.id.encryptMessage);
        copyKey = view.findViewById(R.id.copyKey);
        copyCipherText = view.findViewById(R.id.copyCipherText);

        generateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!plainText.getText().toString().isEmpty()) {
                    message = plainText.getText().toString().toUpperCase().trim();
                    String randomKey = generateRandomKey(message.length());
                    key.setText(randomKey.toUpperCase());
                }else{
                    Toast.makeText(getContext(), "PlainText is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        encryptMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!plainText.getText().toString().isEmpty() && !key.getText().toString().isEmpty()) {
                    message = plainText.getText().toString().toUpperCase().trim();
                    String k = key.getText().toString().toUpperCase().trim();
                    if (message.length() == k.length()) {
                        String encryptedText = encryption(message, k);
                        cipherText.setText(encryptedText);
                    } else {
                        Toast.makeText(getContext(), "PlainText length is not matching with key length", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "PlainText or key cannot be empty", Toast.LENGTH_SHORT).show();
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

    private String generateRandomKey(int n) {
        char[] ALPHABET = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        StringBuilder res = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            res.append(ALPHABET[random.nextInt(MAX)]);
        }

        return res.toString();
    }

    public String encryption(String text, String key) {
        StringBuilder cipherText = new StringBuilder();

        int[] cipher = new int[key.length()];

        for (int i = 0; i < key.length(); i++) {
            cipher[i] = (text.charAt(i) - 'A' + key.charAt(i) - 'A') % 26;
        }

        for (int i = 0; i < key.length(); i++) {
            if (cipher[i] < 0) {
                cipher[i] += 26;
            }
            int x = cipher[i] + 'A';
            cipherText.append((char) x);
        }

        return cipherText.toString();
    }
}