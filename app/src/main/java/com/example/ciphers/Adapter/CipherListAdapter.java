package com.example.ciphers.Adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ciphers.Activity.AESActivity;
import com.example.ciphers.Activity.AffineCipherActivity;
import com.example.ciphers.Activity.CaesarCipherActivity;
import com.example.ciphers.Activity.DESActivity;
import com.example.ciphers.Activity.OneTimePadActivity;
import com.example.ciphers.Activity.PlayfairCipherActivity;
import com.example.ciphers.Activity.VigenereCipherActivity;
import com.example.ciphers.Model.CipherModel;
import com.example.ciphers.R;

import java.util.ArrayList;

public class CipherListAdapter extends RecyclerView.Adapter<CipherListAdapter.ViewHolder> {

    Context context;
    ArrayList<CipherModel> cipherList;

    public CipherListAdapter(Context context, ArrayList<CipherModel> cipherList) {
        this.context = context;
        this.cipherList = cipherList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cipher_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cipherName.setText(cipherList.get(position).getName());
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if("Caesar Cipher".equals(cipherList.get(holder.getAdapterPosition()).getName())){
                    intent = new Intent(context, CaesarCipherActivity.class);
                }else if("Vigenere Cipher".equals(cipherList.get(holder.getAdapterPosition()).getName())){
                    intent = new Intent(context, VigenereCipherActivity.class);
                }else if("One Time Pad".equals(cipherList.get(holder.getAdapterPosition()).getName())){
                    intent = new Intent(context, OneTimePadActivity.class);
                }else if("Playfair Cipher".equals(cipherList.get(holder.getAdapterPosition()).getName())){
                    intent = new Intent(context, PlayfairCipherActivity.class);
                }else if("Affine Cipher".equals(cipherList.get(holder.getAdapterPosition()).getName())){
                    intent = new Intent(context, AffineCipherActivity.class);
                }else if("Advanced Encryption Algorithm".equals(cipherList.get(holder.getAdapterPosition()).getName())){
                    intent = new Intent(context, AESActivity.class);
                }else {
                    intent = new Intent(context, DESActivity.class);
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + cipherList.size());
        return cipherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView cipherName;
        LinearLayout itemLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cipherName = itemView.findViewById(R.id.cipherName);
            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }
}
