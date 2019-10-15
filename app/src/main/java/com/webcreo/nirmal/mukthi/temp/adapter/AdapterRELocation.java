package com.webcreo.nirmal.mukthi.temp.adapter;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.activity.ActivityHomeService;
import com.webcreo.nirmal.mukthi.temp.model.ModelBase;
import com.webcreo.nirmal.mukthi.temp.helper.HelperAnimationItem;
import com.webcreo.nirmal.mukthi.temp.utils.Tools;

import java.util.List;

public class AdapterRELocation extends RecyclerView.Adapter
{
    private List<ModelBase> mCategories;
    private Context mContext;

    public AdapterRELocation(Context context, List<ModelBase> categories)
    {
        this.mCategories = categories;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_re_locations, parent, false);
        return new ViewHolderSub(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        final ViewHolderSub viewHolderSub = (ViewHolderSub) holder;

        final ModelBase category = mCategories.get(position);

        if (category.modelRealEstate.isHasLocation()){
            viewHolderSub.location.setTag(viewHolderSub.location.getId(), category.getId());
            viewHolderSub.location.setText(category.modelRealEstate.getLocation());
        }

        if (category.modelRealEstate.isHasScore()){
            viewHolderSub.score.setTag(viewHolderSub.score.getId(), category.getId());
            viewHolderSub.score.setText(category.modelRealEstate.getScore());
        }

        if(category.isHasBackground()){
            viewHolderSub.image.setTag(viewHolderSub.image.getId(), category.getId());
            Tools.loadFirebaseImage(mContext,category.getBackground(),viewHolderSub.image);
            //Tools.loadImageDrawable(mContext,viewHolderSub.image, category.getImageDrawable());
        }

        viewHolderSub.content.setTag(viewHolderSub.content.getId(), category.getId());
        viewHolderSub.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ActivityHomeService.class);
                intent.putExtra("data", category);
                mContext.startActivity(intent);
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
        return mCategories.size();
    }

    public class ViewHolderSub extends RecyclerView.ViewHolder
    {
        CardView content;
        ImageView image;
        TextView location;
        TextView score;

        public ViewHolderSub(View itemView)
        {
            super(itemView);
            content = (CardView) itemView.findViewById(R.id.content);
            image = (ImageView) itemView.findViewById(R.id.image);
            location = (TextView) itemView.findViewById(R.id.location);
            score = (TextView) itemView.findViewById(R.id.score);
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
            HelperAnimationItem.animate(view, on_attach ? position : -1, HelperAnimationItem.RIGHT_LEFT);
            lastPosition = position;
        }
    }
}
