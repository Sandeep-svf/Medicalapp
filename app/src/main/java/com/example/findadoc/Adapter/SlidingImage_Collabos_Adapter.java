package com.example.findadoc.Adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;


import com.bumptech.glide.Glide;
import com.example.findadoc.Fragments.Slider_Model_Api_Data;
import com.example.findadoc.R;

import java.io.File;
import java.util.List;

public class SlidingImage_Collabos_Adapter extends PagerAdapter
{

    List<Slider_Model_Api_Data> sLider_models;
    private LayoutInflater inflater;
    private Context context;

    public SlidingImage_Collabos_Adapter(Context context, List<Slider_Model_Api_Data> sLider_models)
    {
        this.context = context;
        this.sLider_models=sLider_models;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }

    @Override
    public int getCount()
    {
        return sLider_models.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position)
    {
        View imageLayout = inflater.inflate(R.layout.sliding_image_layout, view, false);
        assert imageLayout != null;


        final ImageView imageView = (ImageView)imageLayout.findViewById(R.id.sliding_image);
        TextView title=(TextView)imageLayout.findViewById(R.id.title) ;
        TextView sub_title=(TextView)imageLayout.findViewById(R.id.sub_title);


        title.setText(sLider_models.get(position).getTitle());
        sub_title.setText(sLider_models.get(position).getSub_title());

        Glide.with(context).load(sLider_models.get(position).getImage()).into(imageView);
        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader)
    {

    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
