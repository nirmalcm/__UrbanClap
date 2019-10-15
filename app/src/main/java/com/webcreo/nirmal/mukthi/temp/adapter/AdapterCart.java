package com.webcreo.nirmal.mukthi.temp.adapter;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.model.ModelBase;
import com.webcreo.nirmal.mukthi.temp.helper.HelperAnimationItem;
import com.webcreo.nirmal.mukthi.temp.utils.Tools;

import java.util.List;

public class AdapterCart extends RecyclerView.Adapter
{
    private List<ModelBase> services;
    private Context context;

    public AdapterCart(Context context, List<ModelBase> services)
    {
        this.services = services;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cart, parent, false);
        return new ViewHolderSub(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        final ViewHolderSub viewHolderSub = (ViewHolderSub) holder;

        final ModelBase category = services.get(position);

        if (category.isHasName()){
            viewHolderSub.name.setTag(viewHolderSub.name.getId(), category.getId());
            viewHolderSub.name.setText(category.getName());
        }

        if (category.isHasDescription()){
            viewHolderSub.description.setTag(viewHolderSub.description.getId(), category.getId());
            viewHolderSub.description.setText(category.getDescription());
        }

        viewHolderSub.delete.setTag(viewHolderSub.delete.getId(), category.getId());
        viewHolderSub.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        TextView description;
        ImageButton delete;

        public ViewHolderSub(View itemView)
        {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            description = (TextView) itemView.findViewById(R.id.description);
            delete = (ImageButton) itemView.findViewById(R.id.delete);
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