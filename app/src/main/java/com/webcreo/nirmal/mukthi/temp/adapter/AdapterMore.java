package com.webcreo.nirmal.mukthi.temp.adapter;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.activity.ActivityHomeServices;
import com.webcreo.nirmal.mukthi.temp.model.ModelBase;
import com.webcreo.nirmal.mukthi.temp.helper.HelperAnimationItem;
import com.webcreo.nirmal.mukthi.temp.utils.Tools;

import java.util.List;

public class AdapterMore extends RecyclerView.Adapter
{
    private List<ModelBase> services;
    private Context context;

    public AdapterMore(Context context, List<ModelBase> services)
    {
        this.services = services;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_more, parent, false);
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
        //Tools.loadImageDrawable(context,viewHolderSub.image, services.get(position).getImageDrawable());

        if(services.get(position).isHasIcon()){
            viewHolderSub.image.setTag(viewHolderSub.image.getId(), services.get(position).getId());
            Tools.loadFirebaseImage(context,services.get(position).getIcon(),viewHolderSub.image);
            //Tools.loadImageDrawable(mContext,viewHolderSub.image, category.getImageDrawable());
        }
        viewHolderSub.name.setTag(viewHolderSub.image.getId(), services.get(position).getId());
        viewHolderSub.name.setText(services.get(position).getName());


        viewHolderSub.itemView.setTag(viewHolderSub.itemView.getId(), services.get(position).getId());
        viewHolderSub.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityHomeServices.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",services.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
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

        Tools.applyFontStyle(context,viewHolderSub.itemView, Tools.FontTypeFace.font_Quattrocento, Tools.FontStyle.normal);
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

        public ViewHolderSub(View itemView)
        {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.id_image);
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
            HelperAnimationItem.animate(view, on_attach ? position : -1, HelperAnimationItem.FADE_IN);
            lastPosition = position;
            System.out.println("test vjm position" + position);
            System.out.println("test vjm last position" + lastPosition);
        }
    }
}