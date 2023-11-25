package com.example.ciphers.Fragment;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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

public class OneTimePadDecryptionFragment extends Fragment {

    EditText cipherText,key,plainText;
    MaterialButton decryptMessage;
    ImageView copyKeyText,copyPlainText;
    public OneTimePadDecryptionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_time_pad_decryption, container, false);

        cipherText = view.findViewById(R.id.cipherText);
        key = view.findViewById(R.id.key);
        plainText = view.findViewById(R.id.plainText);
        decryptMessage = view.findViewById(R.id.decryptMessage);
        copyKeyText = view.findViewById(R.id.copyKey);
        copyPlainText = view.findViewById(R.id.copyPlainText);

        decryptMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cipherText.getText().toString().isEmpty() && !key.getText().toString().isEmpty()) {
                    String message = cipherText.getText().toString().toUpperCase().trim();
                    String k = key.getText().toString().toUpperCase().trim();
                    if (message.length() == k.length()) {
                        String decryptedText = decryption(message, k);
                        plainText.setText(decryptedText);
                    } else {
                        Toast.makeText(getContext(), "CipherText length is not matching with key length", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Ciphertext or key cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyKeyText.setOnClickListener(new View.OnClickListener() {
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

    public static String decryption(String s, String key) {
        StringBuilder plainText = new StringBuilder();

        int[] plain = new int[key.length()];

        for (int i = 0; i < key.length(); i++) {
            plain[i] = s.charAt(i) - 'A' - (key.charAt(i) - 'A');
        }

        for (int i = 0; i < key.length(); i++) {
            if (plain[i] < 0) {
                plain[i] = plain[i] + 26;
            }
        }

        for (int i = 0; i < key.length(); i++) {
            int x = plain[i] + 'A';
            plainText.append((char) x);
        }

        return plainText.toString();
    }
}