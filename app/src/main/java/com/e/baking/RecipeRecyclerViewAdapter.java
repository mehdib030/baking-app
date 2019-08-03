package com.e.baking;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.baking.model.Recipe;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    private int mNumberOfItems;
    private List<Recipe> recipes;
    private LayoutInflater mLayoutInflater;
    private ItemClickListener mItemClickListener;
    private Context context;


    public RecipeRecyclerViewAdapter(List<Recipe> recipes,int numberOfItems,Context context){
        this.recipes=recipes;
        this.mNumberOfItems=numberOfItems;
        this.mLayoutInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public RecipeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //View view = mLayoutInflater.inflate(R.layout.linearlayout_recipes,parent,false);
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.linearlayout_recipes,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Recipe recipe = this.recipes.get(position);

        viewHolder.mNameTextView.setText(recipe.getName());
        viewHolder.mServingsTextView.setText(recipe.getServings());

    }

    @Override
    public int getItemCount() {
        return this.mNumberOfItems;
    }

    public String getItem(int id){
        return recipes.get(id).getName();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        @BindView(R.id.recipe_name) TextView mNameTextView;
        @BindView(R.id.recipe_servings)TextView mServingsTextView;
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

    public void setClickListener(ItemClickListener itemClickListener){
        this.mItemClickListener=itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
