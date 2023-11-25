package com.example.ciphers.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ciphers.Fragment.OneTimePadDecryptionFragment;
import com.example.ciphers.Fragment.OneTimePadEncryptionFragment;

public class ViewPagerOneTimePadAdapter extends FragmentPagerAdapter {
    public ViewPagerOneTimePadAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new OneTimePadEncryptionFragment();
        }else{
            return new OneTimePadDecryptionFragment();
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
