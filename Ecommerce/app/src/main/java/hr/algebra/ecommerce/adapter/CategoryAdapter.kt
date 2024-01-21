package hr.algebra.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.ecommerce.Observer
import hr.algebra.ecommerce.R
import hr.algebra.ecommerce.model.CategoryEcommerce

class CategoryAdapter (
    private val context: Context,
    private val categories: List<CategoryEcommerce>,
    private val observer: Observer
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val llCategoryCard = itemView.findViewById<LinearLayout>(R.id.llCategoryCard)

        fun bind(category: CategoryEcommerce){
            tvTitle.text=category.name
            if(!category.isSelected()){
                llCategoryCard.setBackgroundResource(R.drawable.circle_shape_border_main_color)
                tvTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.main_color))
            } else {
                llCategoryCard.setBackgroundResource(R.drawable.circle_shape_border_and_background)
                tvTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.category_card, parent, false)
        )
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            selectCategory(position)
        }
        holder.bind(categories[position])
    }

    private fun selectCategory(position: Int) {
        CategoryEcommerce.categorySelected = categories[position]
        observer.updateView()
        notifyDataSetChanged()
    }

}