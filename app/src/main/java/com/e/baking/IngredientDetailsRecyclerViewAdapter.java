package com.e.baking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.baking.model.Ingredient;
import com.e.baking.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientDetailsRecyclerViewAdapter extends RecyclerView.Adapter<IngredientDetailsRecyclerViewAdapter.ViewHolder> {


    private int mNumberOfItems;
    private List<Ingredient> ingredients;
    private LayoutInflater mLayoutInflater;
    private RecipeRecyclerViewAdapter.ItemClickListener mItemClickListener;
    private Context context;

    public IngredientDetailsRecyclerViewAdapter(List<Ingredient> ingredients, Context context, int numberOfItems, BtnClickListener btnClickListener){
        this.ingredients=ingredients;
        this.mNumberOfItems=numberOfItems;
        this.mLayoutInflater=LayoutInflater.from(context);
        this.context=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.linearlayout_ingredients,parent,false);
        return new IngredientDetailsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Ingredient ingredient = this.ingredients.get(position);

        viewHolder.mNameTextView.setText(ingredient.getIngredient());
        viewHolder.mQuantityTextView.setText(ingredient.getQuantity());
    }

    @Override
    public int getItemCount() {
        return this.mNumberOfItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        @BindView(R.id.ingredient_name)
        TextView mNameTextView;
        @BindView(R.id.ingredient_quantity)
        TextView mQuantityTextView;


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
