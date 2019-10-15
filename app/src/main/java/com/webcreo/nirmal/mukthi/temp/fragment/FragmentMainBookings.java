package com.webcreo.nirmal.mukthi.temp.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class FragmentMainBookings extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_bookings, container, false);
        initXmlIds(view);
        initComponent(view);
        return view;
    }

    private void initXmlIds(View view) {
        //mNoContent = (ImageView) view.findViewById(R.id.id_no_content);
        view_pager = (ViewPager) view.findViewById(R.id.view_pager);
        tab_layout = (TabLayout) view.findViewById(R.id.tab_layout);
    }

    private void initComponent(View view) {
        //initNoContent();
        initViewPagerWithTabs();
    }

    private ImageView mNoContent;
    private void initNoContent()
    {
        Tools.loadImageDrawable(getContext(), mNoContent,R.drawable.bg_no_item_cactus);
    }

    private ViewPager view_pager;
    private TabLayout tab_layout;
    private void initViewPagerWithTabs() {
        setupViewPager(view_pager);
        tab_layout.setupWithViewPager(view_pager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
            init();
        }

        private void init() {
            mFragmentList.add(new FragmentBookingsOngoing());
            mFragmentList.add(new FragmentBookingsHistory());
            mFragmentTitleList.add("ONGOING");
            mFragmentTitleList.add("HISTORY");
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}