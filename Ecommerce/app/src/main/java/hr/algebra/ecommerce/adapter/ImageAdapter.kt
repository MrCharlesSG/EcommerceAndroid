package hr.algebra.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.ecommerce.R
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

class ImageAdapter (
    private val context: Context,
    private val images: List<String>,
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvLeft = itemView.findViewById<TextView>(R.id.tvProductsLeft)
        private val ivImage = itemView.findViewById<ImageView>(R.id.ivProductImageCard)

        fun bind(image: String, position: Int, positionsLeft:Int){
            tvLeft.text = (position+1).toString()+"/"+positionsLeft.toString()
            Picasso.get()
                .load(File(image))
                .error(R.drawable.error_loading_image)
                .transform(RoundedCornersTransformation(50, 3))
                .into(ivImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.image_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position], position, images.size)
    }

    override fun getItemCount() = images.size

}