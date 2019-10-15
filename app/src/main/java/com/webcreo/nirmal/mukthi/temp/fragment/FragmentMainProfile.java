package com.webcreo.nirmal.mukthi.temp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.activity.ActivityMain;
import com.webcreo.nirmal.mukthi.temp.adapter.AdapterProfileOptions;
import com.webcreo.nirmal.mukthi.temp.model.ModelBase;
import com.webcreo.nirmal.mukthi.temp.utils.Tools;

import java.util.List;

public class FragmentMainProfile extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_profile, container, false);
        initXmlIds(view);
        initComponent(view);
        return view;
    }

    private void initXmlIds(View view) {
        //mNoContent = (ImageView) view.findViewById(R.id.id_no_content);
        mOptionsRecycler = (RecyclerView) view.findViewById(R.id.id_options);
        mLogout = (TextView) view.findViewById(R.id.logout);
    }

    private FirebaseAuth.AuthStateListener authListener;
    private TextView mUserEmail;
    private TextView mUserPhone;
    private void initComponent(View view) {
        //initNoContent();

        mUserPhone = (TextView) view.findViewById(R.id.user_phone);
        mUserEmail = (TextView) view.findViewById(R.id.user_email);

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                }
                else {
                    Crashlytics.setUserIdentifier(user.getPhoneNumber());
                    mUserPhone.setText(user.getPhoneNumber());
                    mUserEmail.setText(user.getEmail());
                }
            }
        };

        initOptions();
        logout();
    }

    private TextView mLogout;
    private void logout() {
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityMain)getActivity()).logout();
            }
        });
    }

    private ImageView mNoContent;
    private void initNoContent()
    {
        Tools.loadImageDrawable(getContext(), mNoContent,R.drawable.bg_no_item_cactus);
    }

    private RecyclerView mOptionsRecycler;
    private AdapterProfileOptions mOptionsAdapter;
    List<ModelBase> mOptions;
    private void initOptions() {

        mOptions = new ModelBase().getContents(getContext(),"contents.json","profile_options",false);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mOptionsRecycler.setLayoutManager(linearLayoutManager);

        mOptionsAdapter = new AdapterProfileOptions(getContext(), mOptions);
        mOptionsAdapter.notifyDataSetChanged();

        mOptionsRecycler.setAdapter(mOptionsAdapter);
        mOptionsRecycler.setItemAnimator(new DefaultItemAnimator());
        mOptionsRecycler.setNestedScrollingEnabled(false);
    }
}