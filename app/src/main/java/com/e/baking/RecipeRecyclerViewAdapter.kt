package com.e.baking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.e.baking.model.Recipe

class RecipeRecyclerViewAdapter(
    private val recipes: List<Recipe>,
    private val mNumberOfItems: Int,
    context: Context
) : RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder>() {

    private var mItemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.linearlayout_recipes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        viewHolder.mNameTextView?.text = recipe.name
        viewHolder.mServingsTextView?.text = recipe.servings
    }

    override fun getItemCount(): Int = mNumberOfItems

    fun getItem(id: Int): String? = recipes[id].name

    fun setClickListener(itemClickListener: ItemClickListener) {
        mItemClickListener = itemClickListener
    }

    fun getAdapter(): RecyclerView.Adapter<*> = this

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        @JvmField
        @BindView(R.id.recipe_name)
        var mNameTextView: TextView? = null

        @JvmField
        @BindView(R.id.recipe_servings)
        var mServingsTextView: TextView? = null

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
}
