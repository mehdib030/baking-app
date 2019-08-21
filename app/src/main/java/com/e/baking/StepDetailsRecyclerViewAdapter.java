package com.e.baking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.e.baking.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsRecyclerViewAdapter extends RecyclerView.Adapter<StepDetailsRecyclerViewAdapter.ViewHolder> {

    private int mNumberOfItems;
    private List<Step> steps;
    private LayoutInflater mLayoutInflater;
    private RecipeRecyclerViewAdapter.ItemClickListener mItemClickListener;
    private Context context;

    public StepDetailsRecyclerViewAdapter(List<Step> steps, int numberOfItems, Context context, BtnClickListener btnClickListener){
        this.steps=steps;
        this.mNumberOfItems=numberOfItems;
        this.mLayoutInflater=LayoutInflater.from(context);
        this.context=context;
    }


    @NonNull
    @Override
    public StepDetailsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.linearlayout_steps,parent,false);
        return new StepDetailsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Step step = this.steps.get(position);

        viewHolder.mShortDescriptionTextView.setText(step.getShortDescription());

    }

    @Override
    public int getItemCount() {
        return this.mNumberOfItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        @BindView(R.id.step_short_description)
        TextView mShortDescriptionTextView;
        // @BindView(R.id.recipe_servings)TextView mServingsTextView;
        // @BindView(R.id.recipe_image)ImageView mRecipeImageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mItemClickListener != null) mItemClickListener.onItemClick(view,getAdapterPosition());
        }

    }

    public void setClickListener(RecipeRecyclerViewAdapter.ItemClickListener itemClickListener){
        this.mItemClickListener=itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface BtnClickListener {
        public abstract void onBtnClick(int position);
    }
}
