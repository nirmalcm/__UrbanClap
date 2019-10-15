package com.webcreo.nirmal.mukthi.temp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.model.ModelBase;

public class FragmentHelpDescription extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help_description, container, false);
        initXmlIds(view);
        initComponent(view);
        return view;
    }

    private TextView mHeading;
    private TextView mDescription;
    private void initXmlIds(View view) {
        mHeading = (TextView) view.findViewById(R.id.id_title);
        mDescription = (TextView) view.findViewById(R.id.id_description);
    }

    private ModelBase mContent;
    private void initComponent(View view) {
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            mContent = (ModelBase) getArguments().getSerializable("data");
        }

        mHeading.setText(mContent.getName());
        mDescription.setText(mContent.getDescription());
    }
}