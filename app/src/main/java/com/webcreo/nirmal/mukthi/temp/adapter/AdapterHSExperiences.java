package com.webcreo.nirmal.mukthi.temp.adapter;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.model.ModelBase;
import com.webcreo.nirmal.mukthi.temp.helper.HelperAnimationItem;
import com.webcreo.nirmal.mukthi.temp.utils.Tools;

import java.util.List;

public class AdapterHSExperiences extends RecyclerView.Adapter
{
    private List<ModelBase> services;
    private Context context;

    public AdapterHSExperiences(Context context, List<ModelBase> services)
    {
        this.services = services;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_hs_experiences, parent, false);
        return new ViewHolderSub(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        final ViewHolderSub viewHolderSub = (ViewHolderSub) holder;


        //Picasso.with(context)
          //      .load(services.get(position).getImageDrawable())
            //    .into(viewHolderSub.image);

        viewHolderSub.image.setTag(viewHolderSub.image.getId(), services.get(position).getId());
        Tools.loadImageDrawable(context,viewHolderSub.image, services.get(position).getImageDrawable());

        viewHolderSub.name.setTag(viewHolderSub.image.getId(), services.get(position).getId());
        viewHolderSub.name.setText(services.get(position).getName());

        viewHolderSub.description.setTag(viewHolderSub.description.getId(), services.get(position).getId());
        viewHolderSub.description.setText(services.get(position).getName());

        setAnimation(viewHolderSub.itemView, position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StateListAnimator stateListAnimator = AnimatorInflater.loadStateListAnimator(
                    viewHolderSub.itemView.getContext(),
                    R.drawable.lift_inward
            );
            viewHolderSub.itemView.setStateListAnimator(stateListAnimator);
        }
    }

    @Override
    public int getItemCount()
    {
        return services.size();
    }

    public class ViewHolderSub extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name;
        TextView description;

        public ViewHolderSub(View itemView)
        {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.id_image);
            name = (TextView) itemView.findViewById(R.id.id_name);
            description = (TextView) itemView.findViewById(R.id.id_same_day_delivery);
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
            HelperAnimationItem.animate(view, on_attach ? position : -1, HelperAnimationItem.FADE_IN);
            lastPosition = position;
        }
    }
}