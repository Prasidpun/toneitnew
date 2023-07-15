package com.example.toneit;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class TabLayoutAdapter extends FragmentPagerAdapter {
    private  final ArrayList<Fragment>fragments=new ArrayList<>();
    private  final ArrayList<String>title_name=new ArrayList<>();
    public TabLayoutAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    public void addFrame(Fragment fragment,String title){
        fragments.add(fragment);
        title_name.add(title);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title_name.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}

