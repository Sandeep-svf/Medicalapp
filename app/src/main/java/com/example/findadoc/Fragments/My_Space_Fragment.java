package com.example.findadoc.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findadoc.MainActivity;
import com.example.findadoc.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class My_Space_Fragment extends Fragment {

   private TabLayout spac_tabs;
   private ViewPager spac_viewpager;
   SharedPreferences sharedPreferences;
   private String staffStatus;

    private int[] tabIcons = {
            R.drawable.fav,
            R.drawable.contact_us,
            R.drawable.email_icon,
    R.drawable.profile};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_my__space_, container, false);


        sharedPreferences = getActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        staffStatus = sharedPreferences.getString("staffStatus", "");

        Log.e("staffStatus","staffStatus*****"+staffStatus);



        spac_tabs=(TabLayout)v.findViewById(R.id.spac_tabs);
        spac_viewpager=(ViewPager) v.findViewById(R.id.spac_viewpager);
        setupViewPager(spac_viewpager);
        spac_tabs.setupWithViewPager(spac_viewpager);
        setupTabIcons();
        setupTabtitle();
        spac_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String anme= String.valueOf(spac_tabs.getTabAt(position).getText());
                MainActivity.title.setText(anme);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return  v;
    }
    private void setupTabIcons() {
        spac_tabs.getTabAt(0).setIcon(tabIcons[0]);

        spac_tabs.getTabAt(1).setIcon(tabIcons[1]);
        spac_tabs.getTabAt(2 ).setIcon(tabIcons[2]);
        if(staffStatus.equals("1"))
        {
            spac_tabs.getTabAt(3).setIcon(tabIcons[3]);
        }

    }
    private void setupTabtitle() {
        spac_tabs.getTabAt(0).setText(getResources().getString(R.string.my_fav));

        spac_tabs.getTabAt(1).setText(getResources().getString(R.string.my_profile));
        spac_tabs.getTabAt(2).setText(getResources().getString(R.string.contact_us));
        if(staffStatus.equals("1"))
        {
             spac_tabs.getTabAt(3).setText(getResources().getString(R.string.new_en));
        }

    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter_two adapter = new ViewPagerAdapter_two(getChildFragmentManager());
        {
            adapter.addFragment(new My_fav_Fragment());
            adapter.addFragment(new Profile_Fragment());
            adapter.addFragment(new Contact_Us_Fragment());
            if(staffStatus.equals("1"))
            {
                adapter.addFragment(new New_Enrty_Fragment());
            }


        }

        viewPager.setAdapter(adapter);
    }
    static class ViewPagerAdapter_two extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter_two(FragmentManager manager) {
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