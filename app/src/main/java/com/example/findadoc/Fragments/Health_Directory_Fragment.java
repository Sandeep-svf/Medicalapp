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


public class Health_Directory_Fragment extends Fragment {


    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.hso,
            R.drawable.clicnic,
            R.drawable.doctor,
            R.drawable.laborat_icon,
            R.drawable.radiology,
            R.drawable.eye_test,
            R.drawable.vaccine};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_health__directory_, container, false);
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
       /* if(tabLayout.getTabAt(0).getText().toString().equals("Hospitals"))
        {
            MainActivity.title.setText("Hospitals");
        }
        else if(tabLayout.getTabAt(1).getText().toString().equals("Clinics"))
        {
            MainActivity.title.setText("Clinics");
        }
        else
        {
            MainActivity.title.setText("Home");
        }*/
        return v;
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
        tabLayout.getTabAt(5).setIcon(tabIcons[5]);
        tabLayout.getTabAt(6).setIcon(tabIcons[6]);
//        tabLayout.getTabAt(7).setIcon(tabIcons[7]);
    }
        private void setupTabtitle() {
            tabLayout.getTabAt(0).setText(getResources().getString(R.string.hospitals));
            tabLayout.getTabAt(1).setText(getResources().getString(R.string.clicnic));
            tabLayout.getTabAt(2).setText(getResources().getString(R.string.doctor));
            tabLayout.getTabAt(3).setText(getResources().getString(R.string.labora));
            tabLayout.getTabAt(4).setText(getResources().getString(R.string.radiology));
            tabLayout.getTabAt(5).setText(getResources().getString(R.string.opticians));
            tabLayout.getTabAt(6).setText(getResources().getString(R.string.ver));
//            tabLayout.getTabAt(7).setText(getResources().getString(R.string.drugs_styore));

        }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setupViewPager(ViewPager viewPager, ViewPagerAdapter adapter)
    {
        {
            adapter.addFragment(new Hospital_Fragment());
            adapter.addFragment(new Clicnic_Fragment());
            adapter.addFragment(new Doctor_Fragment());
            adapter.addFragment(new Labouratery_Fragment());
            adapter.addFragment(new Radiology_Fragment());
            adapter.addFragment(new Opticiens_Fragment());
            adapter.addFragment(new Verterinaries_Fragment());

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