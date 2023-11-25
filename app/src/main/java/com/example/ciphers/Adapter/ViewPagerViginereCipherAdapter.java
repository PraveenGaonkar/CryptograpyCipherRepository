package com.example.ciphers.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ciphers.Fragment.OneTimePadDecryptionFragment;
import com.example.ciphers.Fragment.OneTimePadEncryptionFragment;
import com.example.ciphers.Fragment.ViginereCipherDecryptionFragment;
import com.example.ciphers.Fragment.ViginereCipherEncryptionFragment;

public class ViewPagerViginereCipherAdapter extends FragmentPagerAdapter {
    public ViewPagerViginereCipherAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new ViginereCipherEncryptionFragment();
        }else{
            return new ViginereCipherDecryptionFragment();
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
