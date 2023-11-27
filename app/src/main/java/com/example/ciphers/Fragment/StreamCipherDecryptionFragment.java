package com.example.ciphers.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ciphers.R;
public class StreamCipherDecryptionFragment extends Fragment {

    public StreamCipherDecryptionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stream_cipher_decryption, container, false);

        return view;
    }
}