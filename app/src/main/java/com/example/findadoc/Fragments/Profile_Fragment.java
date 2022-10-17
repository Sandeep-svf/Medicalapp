package com.example.findadoc.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.findadoc.Activity.Login_Activity;
import com.example.findadoc.Activity.Profile_Detail_Activity;
import com.example.findadoc.Activity.Sponsers_Activity;
import com.example.findadoc.Activity.Web_View_Activity;
import com.example.findadoc.MainActivity;
import com.example.findadoc.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.api.client.testing.util.LogRecordingHandler;

import de.hdodenhof.circleimageview.CircleImageView;


public class Profile_Fragment extends Fragment {


    TextView share_btn;
    Button login_text;
    SharedPreferences sharedPreferences;
    CircleImageView profile_image;
    RelativeLayout logout_layout,personal_info_layout;
    RelativeLayout sponser_layout;
    RelativeLayout privacy_policies_layout,about_us_relative,term_condition_layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_profile_, container, false);
        Log.e("DADADA","OO");
        sharedPreferences = getActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        String user_name=sharedPreferences.getString("user_name","");
        String photo=sharedPreferences.getString("user_profile","");
        String family_name=sharedPreferences.getString("last_name","");
        login_text=v.findViewById(R.id.login_text);
        share_btn=v.findViewById(R.id.share_btn);
        profile_image=v.findViewById(R.id.profile_image);
        sponser_layout=v.findViewById(R.id.sponser_layout);
        logout_layout=v.findViewById(R.id.logout_layout);
        personal_info_layout=v.findViewById(R.id.personal_info_layout);
        privacy_policies_layout=v.findViewById(R.id.privacy_policies_layout);
        term_condition_layout=v.findViewById(R.id.term_condition_layout);
        about_us_relative=v.findViewById(R.id.about_us_relative);
        sponser_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Sponsers_Activity.class);
                startActivity(intent);
            }
        });
        about_us_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Web_View_Activity.class);
                intent.putExtra("key","3");
                startActivity(intent);
            }
        });

        term_condition_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Web_View_Activity.class);
                intent.putExtra("key","1");
                startActivity(intent);
            }
        });
        privacy_policies_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Web_View_Activity.class);
                intent.putExtra("key","2");
                startActivity(intent);
            }
        });

        if(!user_name.equals(""))
        {


            login_text.setText(user_name+" "+family_name);

            personal_info_layout.setVisibility(View.VISIBLE);
            logout_layout.setVisibility(View.VISIBLE);
            logout_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginManager.getInstance().logOut();
                    GoogleSignInOptions gso = new GoogleSignInOptions.
                            Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                            build();
                    GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
                    googleSignInClient.signOut();

                    sharedPreferences = getActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("user_name", "");
                    editor.putString("last_name", "");
                    editor.putString("user_profile", "");

                    editor.putString("user_email", "");
                    editor.putString("person_id", "");
                    editor.apply();
                    Intent intent=new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

        }
        else
        {
            personal_info_layout.setVisibility(View.GONE);
            logout_layout.setVisibility(View.GONE);
            login_text.setText(getResources().getString(R.string.sign_up_sign_in));
            login_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),Login_Activity.class);
                    startActivity(intent);

                }
            });
        }
        if(photo.equals(""))
        {
            Log.e("DADADA","AA");
            Glide.with(getActivity()).load(R.drawable.save).into(profile_image);
        }
        else
        {
            Log.e("DADADA","BB");
            Glide.with(getActivity()).load(photo).into(profile_image);
        }
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTextUrl();
            }
        });
        personal_info_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Profile_Detail_Activity.class);
                startActivity(intent);
            }
        });
        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Login_Activity.class);
                startActivity(intent);

            }
        });


        return v;
    }
    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, "http://www.codeofaninja.com");

        startActivity(Intent.createChooser(share, "Share link!"));
    }

}