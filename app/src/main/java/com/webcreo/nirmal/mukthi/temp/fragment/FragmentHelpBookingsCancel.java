package com.webcreo.nirmal.mukthi.temp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.adapter.AdapterHelpOptions;
import com.webcreo.nirmal.mukthi.temp.model.ModelBase;

import java.util.ArrayList;

public class FragmentHelpBookingsCancel extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help_bookings_cancel, container, false);
        initXmlIds(view);
        receiveIntent();
        initNoContent();
        //initToolbar();
        //initComponent(view);
        return view;
    }

    private void initToolbar() {

    }

    private void receiveIntent() {
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            mOptions = (ArrayList<ModelBase>)getArguments().getSerializable("data");
            mTitle.setText(getArguments().getString("heading"));
        }
    }

    private void initXmlIds(View view) {
        mNoContent = (FrameLayout) view.findViewById(R.id.id_no_content);

        mOptionsRecycler = (RecyclerView) view.findViewById(R.id.id_options);
        mTitle = (TextView) view.findViewById(R.id.id_title);
    }

    private TextView mTitle;
    private void initComponent(View view) {
        initNoContent();
        initHaveContent();
        initOptions();
    }

    private void initHaveContent() {
        mNoContent.setVisibility(View.INVISIBLE);
    }

    private FrameLayout mNoContent;
    private void initNoContent() {
        mNoContent.setVisibility(View.VISIBLE);
    }

    private RecyclerView mOptionsRecycler;
    private AdapterHelpOptions mOptionsAdapter;
    ArrayList<ModelBase> mOptions;
    private void initOptions() {

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mOptionsRecycler.setLayoutManager(linearLayoutManager);

        mOptionsAdapter = new AdapterHelpOptions(getContext(), mOptions);
        mOptionsAdapter.notifyDataSetChanged();

        mOptionsRecycler.setAdapter(mOptionsAdapter);
        mOptionsRecycler.setItemAnimator(new DefaultItemAnimator());
        mOptionsRecycler.setNestedScrollingEnabled(false);
    }

}