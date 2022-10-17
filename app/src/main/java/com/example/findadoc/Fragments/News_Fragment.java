package com.example.findadoc.Fragments;

import static com.example.findadoc.Retrofir.Api_client.BASE_IMAGE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.findadoc.Adapter.Brand_Adapter;
import com.example.findadoc.Adapter.NewsAdapter;
import com.example.findadoc.Adapter.News_Adapter;
import com.example.findadoc.Adapter.SlidingImage_Collabos_Adapter;
import com.example.findadoc.Model.Brand_Med_Response;
import com.example.findadoc.Model.Brand_Medicne_Model;
import com.example.findadoc.Model.SLider_Model;
import com.example.findadoc.Model.dev.sam.NewsModel;
import com.example.findadoc.Model.dev.sam.NewsRecord;
import com.example.findadoc.Model.dev.sam.NewsResult;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class News_Fragment extends Fragment {
    RecyclerView news_list_layout;
    List<SLider_Model> sLider_models=new ArrayList<>();
    List<Slider_Model_Api_Data> slider_model_api_dataList = new ArrayList<>();
    List<NewsRecord> newsResultList =new ArrayList<>();
    private static ViewPager mPager;
    private static int currentPage = 0,currentPage_new = 0;
    private static int NUM_PAGES = 0, NUM_PAGES_new = 0;
    SharedPreferences sharedPreferences;
    private String language;
//    private CirclePageIndicator indicator;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_news_, container, false);
//        indicator = v.findViewById(R.id.indicator);

        sharedPreferences=getActivity().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language=sharedPreferences.getString("language_text","en");


        news_list_layout=v.findViewById(R.id.news_list_layout);
        mPager = v.findViewById(R.id.pager);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        news_list_layout.setLayoutManager(linearLayoutManager);

      //  News_Adapter news_adapter=new News_Adapter(getActivity());
//        news_list_layout.setAdapter(news_adapter);
        Log.e("check", "Running ************************************** ");

        // newsAPI
        getNews();
        PackageInfo info;
        try {
            info = getActivity().getPackageManager().getPackageInfo("com.you.name", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash_key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        return v;
    }

    private void getNews() {

            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();
        Log.e("hdfkjsf",language);
            Call<NewsModel> call = Api_client.getClient().news("7c538f486ea815187d12c54c3646d71e",
                    language);
            call.enqueue(new Callback<NewsModel>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String   message = response.body().getMessage();
                            String success = response.body().getSuccess();


                            if (success.equals("true") || success.equals("True"))
                            {

                                newsResultList = response.body().getRecord();

                                Log.e("check", "newsResultList: "+String.valueOf(newsResultList.size()));

                                Log.e("dfs", String.valueOf(newsResultList.size()));

                                NewsAdapter news_adapter = new NewsAdapter(newsResultList,getActivity());
                                news_list_layout.setAdapter(news_adapter);

                                add_data();
                                // Sliding News
                                init();

                            }
                            else {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }


                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                switch (response.code()) {
                                    case 400:
                                        Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 401:
                                        Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 404:
                                        Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 500:
                                        Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 503:
                                        Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 504:
                                        Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 511:
                                        Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                        break;
                                }

                            } catch (Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (
                            Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<NewsModel> call, Throwable t) {
                    if (t instanceof IOException) {
                        Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    } else {
                        Log.e("conversion issue", t.getMessage());
                        Toast.makeText(getActivity(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            });


        }


    private void add_data() {

        int size = newsResultList.size();
        if(size >= 3)
        {
            size = 3;
        }else if (size == 2)
        {
            size = 2;
        }else if (size == 1)
        {
            size = 1;
        }

        try {
            Slider_Model_Api_Data slider_model_api_data;

            for(int i = 0; i < size ; i++)
            {

                String heading = newsResultList.get(i).getHeadingEn();
                String created_at = newsResultList.get(i).getCreatedAt();

                slider_model_api_data = new Slider_Model_Api_Data();

                slider_model_api_data.setImage(BASE_IMAGE + newsResultList.get(i).getNewsImage());
                slider_model_api_data.setTitle(heading);
                slider_model_api_data.setSub_title(created_at);

                Log.e("heading",": "+heading);
                Log.e("heading",": "+created_at);
                Log.e("heading",": "+BASE_IMAGE + newsResultList.get(i).getNewsImage());

                slider_model_api_dataList.add(slider_model_api_data);
            }

            Log.e("heading","#####################################################################");

            for(int i = 0; i < size; i++)
            {
                Log.e("heading",": "+slider_model_api_dataList.get(i).getTitle());
                Log.e("heading",": "+slider_model_api_dataList.get(i).getSub_title());
                Log.e("heading",": "+slider_model_api_dataList.get(i).getImage());
            }

            SLider_Model sLider_model=new SLider_Model();
            sLider_model.setImage(Integer.parseInt(BASE_IMAGE+newsResultList.get(0).getNewsImage()));
            sLider_model.setTitle((getResources().getString(Integer.parseInt(newsResultList.get(0).getHeadingEn()))));
            sLider_model.setSub_title((getResources().getString(Integer.parseInt(newsResultList.get(0).getCreatedAt()))));
            sLider_models.add(sLider_model);
            sLider_model=new SLider_Model();
            sLider_model.setImage(Integer.parseInt(BASE_IMAGE+newsResultList.get(1).getNewsImage()));
            sLider_model.setTitle((getResources().getString(Integer.parseInt(newsResultList.get(1).getHeadingEn()))));
            sLider_model.setSub_title((getResources().getString(Integer.parseInt(newsResultList.get(1).getCreatedAt()))));
            sLider_models.add(sLider_model);


            Log.e("check","add_data Running ......");

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
    private void init()
    {
        Log.e("check", "slider_model_api_data: "+String.valueOf(slider_model_api_dataList.size()));
       /* for (int i = 0; i < IMAGES.length; i++)
            ImagesArray.add(IMAGES[i]);*/

        mPager.setAdapter(new SlidingImage_Collabos_Adapter(getActivity(), slider_model_api_dataList));
//        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().scaledDensity;

        //Set circle indicator radius
        //indicator.setFillColor(0xFFFFFFFF);
        // indicator.setStrokeColor(0xFFFFFFFF);
        // indicator.setStrokeWidth(0);
//        indicator.setRadius(5 * density);

        NUM_PAGES = slider_model_api_dataList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable()
        {
            public void run()
            {
                if (currentPage == NUM_PAGES)
                {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                handler.post(Update);
            }
        }, 10000 , 10000);

        // Pager listener over indicator
/*
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
*/
    }

}