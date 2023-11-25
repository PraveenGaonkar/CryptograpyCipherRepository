package com.example.ciphers.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ciphers.Fragment.CaesarCipherDecryptionFragment;
import com.example.ciphers.Fragment.CaesarCipherEncryptionFragment;

public class ViewPagerCaesarCipherAdapter extends FragmentPagerAdapter {
    public ViewPagerCaesarCipherAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new CaesarCipherEncryptionFragment();
        }else{
            return new CaesarCipherDecryptionFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "Encryption";
        }else{
            return "Decryption";
        }
    }
}
