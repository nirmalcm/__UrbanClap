package com.webcreo.nirmal.mukthi.temp.adapter;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.fragment.FragmentHelpDescription;
import com.webcreo.nirmal.mukthi.temp.model.ModelBase;
import com.webcreo.nirmal.mukthi.temp.helper.HelperAnimationItem;

import java.util.List;

public class AdapterHelpOptions extends RecyclerView.Adapter
{
    private List<ModelBase> services;
    private Context context;

    public AdapterHelpOptions(Context context, List<ModelBase> services)
    {
        this.services = services;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_help_options, parent, false);
        return new ViewHolderSub(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        final ViewHolderSub viewHolderSub = (ViewHolderSub) holder;

        viewHolderSub.name.setTag(viewHolderSub.name.getId(), services.get(position).getId());
        viewHolderSub.name.setText(services.get(position).getName());

        viewHolderSub.itemView.setTag(viewHolderSub.itemView.getId(), services.get(position).getId());
        viewHolderSub.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentHelpCenterCommon = new FragmentHelpDescription();
                loadFragment(fragmentHelpCenterCommon);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",services.get(position));
                fragmentHelpCenterCommon.setArguments(bundle);
            }
        });

        //setAnimation(viewHolderSub.itemView, position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StateListAnimator stateListAnimator = AnimatorInflater.loadStateListAnimator(
                    viewHolderSub.itemView.getContext(),
                    R.drawable.lift_inward
            );
            //viewHolderSub.itemView.setStateListAnimator(stateListAnimator);
        }
    }

    @Override
    public int getItemCount()
    {
        return services.size();
    }

    public class ViewHolderSub extends RecyclerView.ViewHolder
    {
        TextView name;

        public ViewHolderSub(View itemView)
        {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.id_name);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        super.onAttachedToRecyclerView(recyclerView);
    }

    private int lastPosition = -1;
    private boolean on_attach = true;

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            HelperAnimationItem.animate(view, on_attach ? position : -1, HelperAnimationItem.LEFT_RIGHT);
            lastPosition = position;
        }
    }

    private void loadFragment(Fragment fragment) {
        AppCompatActivity appCompatActivity = (AppCompatActivity)context;
        FragmentTransaction transaction =appCompatActivity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}