package com.e.baking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.e.baking.model.Ingredient

class IngredientDetailsRecyclerViewAdapter(
    private val ingredients: List<Ingredient>,
    context: Context,
    private val mNumberOfItems: Int,
    btnClickListener: BtnClickListener
) : RecyclerView.Adapter<IngredientDetailsRecyclerViewAdapter.ViewHolder>() {

    private var mItemClickListener: RecipeRecyclerViewAdapter.ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.linearlayout_ingredients, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val ingredient = ingredients[position]
        viewHolder.mNameTextView?.text = ingredient.ingredient
        viewHolder.mQuantityTextView?.text = ingredient.quantity
    }

    override fun getItemCount(): Int = mNumberOfItems

    fun setClickListener(itemClickListener: RecipeRecyclerViewAdapter.ItemClickListener) {
        mItemClickListener = itemClickListener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        @JvmField
        @BindView(R.id.ingredient_name)
        var mNameTextView: TextView? = null

        @JvmField
        @BindView(R.id.ingredient_quantity)
        var mQuantityTextView: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            mItemClickListener?.onItemClick(view, adapterPosition)
        }
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    interface BtnClickListener {
        fun onBtnClick(position: Int)
    }
}
