package com.example.ciphers.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ciphers.Fragment.StreamCipherDecryptionFragment;
import com.example.ciphers.Fragment.StreamCipherEncryptionFragment;

public class ViewPagerStreamCipherAdapter extends FragmentPagerAdapter {
    public ViewPagerStreamCipherAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new StreamCipherEncryptionFragment();
        }else{
            return new StreamCipherDecryptionFragment();
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
