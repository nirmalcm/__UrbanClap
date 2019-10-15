package com.webcreo.nirmal.mukthi.temp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.activity.ActivityMain;
import com.webcreo.nirmal.mukthi.temp.utils.Tools;

public class FragmentBookingsHistory extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookings_history, container, false);
        initXmlIds(view);
        initComponent(view);
        return view;
    }

    private void initXmlIds(View view) {
        mImage = (ImageView) view.findViewById(R.id.id_image);
        mBookNow = (CardView) view.findViewById(R.id.id_book_now);
    }

    private CardView mBookNow;
    private void initComponent(View view) {
        //initNoContent();
        initImage();
        mBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityMain) getActivity()).selectHomeFragment();
            }
        });
    }

    private ImageView mImage;
    private void initImage()
    {
        Tools.loadImageDrawable(getContext(), mImage,R.drawable.img_no_feed);
    }
}