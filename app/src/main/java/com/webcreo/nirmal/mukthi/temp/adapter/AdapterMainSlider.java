package com.webcreo.nirmal.mukthi.temp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.model.ModelBase;
import com.webcreo.nirmal.mukthi.temp.utils.Tools;

import java.util.List;

public class AdapterMainSlider extends PagerAdapter {
        private LayoutInflater layoutInflater;
        private List<ModelBase> contents;
        private Context context;

        public AdapterMainSlider(Context context, List<ModelBase> contents) {
            this.contents = contents;
            this.context = context;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.adapter_main_slider, container, false);
            ImageView imgVw = (ImageView) view.findViewById(R.id.id_content_image);
            Tools.loadFirebaseImage(context,contents.get(position).getImage(),imgVw);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return contents.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }