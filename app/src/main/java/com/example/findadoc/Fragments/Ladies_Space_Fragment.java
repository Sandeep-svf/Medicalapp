package com.example.findadoc.Fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findadoc.MainActivity;
import com.example.findadoc.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class Ladies_Space_Fragment extends Fragment {


    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.newspaper,
            R.drawable.pills,
            R.drawable.medicine,
            R.drawable.laborat_icon,
          };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_ladies__space_, container, false);
        viewPager = (ViewPager)v.findViewById(R.id.viewpager);
        tabLayout = (TabLayout)v.findViewById(R.id.tabs);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        setupViewPager(viewPager,adapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        setupTabtitle();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String anme= String.valueOf(tabLayout.getTabAt(position).getText());
                MainActivity.title.setText(anme);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return v;
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }
    private void setupTabtitle() {
        tabLayout.getTabAt(0).setText(getResources().getString(R.string.ne));
        tabLayout.getTabAt(1).setText(getResources().getString(R.string.birth_contro));
        tabLayout.getTabAt(2).setText(getResources().getString(R.string.fertiliy));
        tabLayout.getTabAt(3).setText(getResources().getString(R.string.pregnancy));


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setupViewPager(ViewPager viewPager, ViewPagerAdapter adapter)
    {
        {
            adapter.addFragment(new News_Fragment());
            adapter.addFragment(new Birth_Control_Fragment());
            adapter.addFragment(new Fertility_Fragment());
            adapter.addFragment(new Pregenancy_Fragment());

        }

        viewPager.setAdapter(adapter);
    }
    static class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);


        }


    }

}