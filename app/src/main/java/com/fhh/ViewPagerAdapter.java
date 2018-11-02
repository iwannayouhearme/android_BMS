package com.fhh;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setList(List<Fragment> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }
}
