package com.webcreo.nirmal.mukthi.temp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.activity.ActivityMain;
import com.webcreo.nirmal.mukthi.temp.model.ModelBase;
import com.webcreo.nirmal.mukthi.temp.helper.HelperAnimationItem;
import com.webcreo.nirmal.mukthi.temp.helper.HelperAnimation;

import java.util.ArrayList;

public class FragmentMainHelp extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_help, container, false);
        initXmlIds(view);
        initProgressBar(view);
        initComponent(view);
        return view;
    }

    private void initXmlIds(View view) {
        mProgressBar = view.findViewById(R.id.id_progress_bar);
        mManageBookings = (TextView) view.findViewById(R.id.id_manage_bookings);
        mCancelAndReshedule = (TextView) view.findViewById(R.id.id_cancel_and_reshedule);
        mAccountSettings = (TextView) view.findViewById(R.id.id_account_settings);
        mBookingServices = (TextView) view.findViewById(R.id.id_booking_services);
        mPayingForServices = (TextView) view.findViewById(R.id.id_paying_for_services);
        mAGuideToMukthi = (TextView) view.findViewById(R.id.id_a_guide_to_mukthi);
    }

    private View mProgressBar;
    private void initProgressBar(final View view) {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setAlpha(1.0f);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HelperAnimation.fadeOut(mProgressBar);
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initComponent(view);
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        }, 1000 + 400);
    }

   private void initComponent(View view) {
        initManageBookings();
        initCancelAndReshedule();
        initAccountSettings();
        initBookingServices();
        initPayingForServices();
        initAGuideToMukthi();
    }

    private TextView mManageBookings;
    private void initManageBookings() {
        mManageBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FragmentHelpBookingsManage();
                loadFragment(fragment);
                Bundle bundle = new Bundle();
                ArrayList<ModelBase> bookingServices = new ModelBase().getContents(getContext(),"contents.json","booking_services",false);
                bundle.putSerializable("data",bookingServices);
                bundle.putString("heading",mManageBookings.getText().toString());
                fragment.setArguments(bundle);
            }
        });
    }

    private TextView mCancelAndReshedule;
    private void initCancelAndReshedule() {
        mCancelAndReshedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FragmentHelpBookingsCancel();
                loadFragment(fragment);
                Bundle bundle = new Bundle();
                ArrayList<ModelBase> bookingServices = new ModelBase().getContents(getContext(),"contents.json","booking_services",false);
                bundle.putSerializable("data",bookingServices);
                bundle.putString("heading",mCancelAndReshedule.getText().toString());
                fragment.setArguments(bundle);
            }
        });
    }

    private TextView mAccountSettings;
    private void initAccountSettings() {
        mAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private TextView mBookingServices;
    private void initBookingServices() {
        mBookingServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FragmentHelpOptions();
                loadFragment(fragment);
                Bundle bundle = new Bundle();
                ArrayList<ModelBase> bookingServices = new ModelBase().getContents(getContext(),"contents.json","booking_services",false);
                bundle.putSerializable("data",bookingServices);
                bundle.putString("heading",mBookingServices.getText().toString());
                fragment.setArguments(bundle);
            }
        });
    }

    private TextView mPayingForServices;
    private void initPayingForServices() {
        mPayingForServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FragmentHelpOptions();
                loadFragment(fragment);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",new ModelBase().getContents(getContext(),"contents.json","booking_services",false));
                bundle.putString("heading",mPayingForServices.getText().toString());
                fragment.setArguments(bundle);
            }
        });
    }

    private TextView mAGuideToMukthi;
    private void initAGuideToMukthi() {
        mAGuideToMukthi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FragmentHelpOptions();
                loadFragment(fragment);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",new ModelBase().getContents(getContext(),"contents.json","booking_services",false));
                bundle.putString("heading",mAGuideToMukthi.getText().toString());
                fragment.setArguments(bundle);
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        ((ActivityMain) getActivity()).removeToolbar();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.setCustomAnimations(HelperAnimationItem.RIGHT_LEFT, HelperAnimationItem.FADE_IN);
        transaction.addToBackStack("yes");
        transaction.commit();
    }
}