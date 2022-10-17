package com.example.findadoc.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.findadoc.Activity.Login_Activity;
import com.example.findadoc.Adapter.Categories_Space_Adapter;
import com.example.findadoc.Model.Categoried_Model;
import com.example.findadoc.Model.Categories_Response;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class My_fav_Fragment extends Fragment {


    public static TabLayout tabLayout;
    RelativeLayout content;
    Button login_btn;
    RelativeLayout relative_layout;
    SharedPreferences sharedPreferences;
    LinearLayout labeled_layout;


    FrameLayout frame_layout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_my_fav_, container, false);
        tabLayout = (TabLayout)v.findViewById(R.id.tabs);
        relative_layout=v.findViewById(R.id.relative_layout);
        content=v.findViewById(R.id.content);
        login_btn=v.findViewById(R.id.login_btn);


        sharedPreferences = getActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        String user_name=sharedPreferences.getString("user_name","");

        if(!user_name.equals(""))
        {
            tabLayout.setVisibility(View.VISIBLE);
            content.setVisibility(View.VISIBLE);
            relative_layout.setVisibility(View.GONE);
            getChildFragmentManager().beginTransaction().replace(R.id.content,new Hospital_Fav_Fragment()).commit();
        }
        else
        {
            tabLayout.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            relative_layout.setVisibility(View.VISIBLE);
            login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(), Login_Activity.class);
                    startActivity(intent);
                }
            });
        }
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.hospitals)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.clicnic)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.doctor)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.labora)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.radiology)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.parapharmacy)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.pharm)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.opticians)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.ver)));
//        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.drugs_styore)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.pharmacy_duuty)));





        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tab.getPosition();
                switch (tab.getPosition())
                {
                    case 0:
                        getChildFragmentManager().beginTransaction().replace(R.id.content,new Hospital_Fav_Fragment()).commit();
                        break;
                    case 1:
                        getChildFragmentManager().beginTransaction().replace(R.id.content,new Clinic_Fav_Fragment()).commit();
                        break;
                    case 2:
                        getChildFragmentManager().beginTransaction().replace(R.id.content,new Doctor_Fav_Fragment()).commit();
                        break;
                    case 3:
                        getChildFragmentManager().beginTransaction().replace(R.id.content,new Laboratory_Fav_Fragment()).commit();
                        break;
                    case 4:
                        getChildFragmentManager().beginTransaction().replace(R.id.content,new Radiolog_Fav_Fragment()).commit();
                        break;
                    case 5:
                        getChildFragmentManager().beginTransaction().replace(R.id.content,new Parapharmacy_Fav_Fragment()).commit();
                        break;
                    case 6:
                        getChildFragmentManager().beginTransaction().replace(R.id.content,new Pharmacy_fav_Support_Fragment()).commit();
                        break;
                    case 7:
                        getChildFragmentManager().beginTransaction().replace(R.id.content,new Opticiens_Favourite_Fragmenet()).commit();
                        break;
                    case 8:
                        getChildFragmentManager().beginTransaction().replace(R.id.content,new Vertin_Animal_Dr_Fav_Fragment()).commit();
                        break;
                    case 9:
                        getChildFragmentManager().beginTransaction().replace(R.id.content,new Pharmacy_on_duty_fav()).commit();


                }



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;
    }


  /*  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setupViewPager(ViewPager viewPager, Health_Directory_Fragment.ViewPagerAdapter adapter)
    {
        {
            adapter.addFragment(new Hospital_Fav_Fragment());
            adapter.addFragment(new Clinic_Fav_Fragment());
            adapter.addFragment(new Doctor_Fav_Fragment());
            adapter.addFragment(new Laboratory_Fav_Fragment());
            adapter.addFragment(new Radiolog_Fav_Fragment());
        }

        viewPager.setAdapter(adapter);
    }
    private void setupTabtitle() {
        tabLayout.getTabAt(0).setText(getResources().getString(R.string.hospitals));
        tabLayout.getTabAt(1).setText(getResources().getString(R.string.clicnic));
        tabLayout.getTabAt(2).setText(getResources().getString(R.string.doctor));
        tabLayout.getTabAt(3).setText(getResources().getString(R.string.labora));
        tabLayout.getTabAt(4).setText(getResources().getString(R.string.radiology));

    }

    static class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private boolean swipeable = false;
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
*/
}