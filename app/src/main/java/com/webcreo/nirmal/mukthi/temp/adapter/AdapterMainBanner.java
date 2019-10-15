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

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.model.ModelBase;
import com.webcreo.nirmal.mukthi.temp.utils.Tools;

import java.util.List;

public class AdapterMainBanner extends RecyclerView.Adapter
{
    private List<ModelBase> contents;
    private Context context;

    public AdapterMainBanner(Context context, List<ModelBase> contents)
    {
        this.contents = contents;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, String viewModel, int pos);
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_main_banner, parent, false);
        return new ViewHolderSub(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        final ViewHolderSub viewHolderSub = (ViewHolderSub) holder;

        final ModelBase category = contents.get(position);

        //Picasso.with(context)
        //      .load(category.getImageDrawable())
        //    .into(viewHolderSub.image);

        viewHolderSub.image.setTag(viewHolderSub.image.getId(), category.getId());
        if(category.isHasImage()){
            if (category.isHasImageDrawable()){
                Tools.loadImageDrawable(context,viewHolderSub.image, category.getImageDrawable());
            }
            else {
                if (category.isHasImagePath()){
                    Tools.loadFirebaseImage(context, category.getImagePath(),category.getImage(),viewHolderSub.image);
                }
                else {
                    Tools.loadFirebaseImage(context,category.getImage(),viewHolderSub.image);
                }
            }
        }

        viewHolderSub.itemView.setTag(viewHolderSub.itemView.getId(), contents.get(position).getId());
        viewHolderSub.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent trendingClickIntent = new Intent(context, RecyclerViewActivity.class);
                //trendingClickIntent.putExtra("KEY_SELECTED_PRODUCT", contents.get(position));
                //context.startActivity(trendingClickIntent);
            }
        });

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
        return contents.size();
    }

    public class ViewHolderSub extends RecyclerView.ViewHolder
    {
        ImageView image;

        public ViewHolderSub(View itemView)
        {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.id_content_image);
        }
    }
}