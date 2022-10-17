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


public class Pharmacies_All extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.hand,
            R.drawable.store,R.drawable.pharmacy};

//    R.drawable.eye_test
//    ,R.drawable.vaccine,R.drawable.pharmacy
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_pharmacies__all, container, false);
        viewPager = (ViewPager)v.findViewById(R.id.viewpager);
        tabLayout = (TabLayout)v.findViewById(R.id.tabs);
        Health_Directory_Fragment.ViewPagerAdapter adapter = new Health_Directory_Fragment.ViewPagerAdapter(getChildFragmentManager());
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
      /*  tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
        tabLayout.getTabAt(5).setIcon(tabIcons[5]);*/


    }
    private void setupTabtitle() {
        tabLayout.getTabAt(0).setText(getResources().getString(R.string.pharmacy_duuty));
        tabLayout.getTabAt(1).setText(getResources().getString(R.string.parapharmacy));
        tabLayout.getTabAt(2).setText(getResources().getString(R.string.pharm));
       /* tabLayout.getTabAt(3).setText(getResources().getString(R.string.opticians));
        tabLayout.getTabAt(4).setText(getResources().getString(R.string.ver));
        tabLayout.getTabAt(5).setText(getResources().getString(R.string.drugs_styore));*/



    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setupViewPager(ViewPager viewPager, Health_Directory_Fragment.ViewPagerAdapter adapter)
    {
        {
            adapter.addFragment(new Pharmacy_on_duty());
            adapter.addFragment(new ParaPharmacy_Fragment());
            adapter.addFragment(new Pharmacies());
           /* adapter.addFragment(new Opticiens_Fragment());
            adapter.addFragment(new Verterinaries_Fragment());
            adapter.addFragment(new Drug_Store_second_Fragment());*/

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