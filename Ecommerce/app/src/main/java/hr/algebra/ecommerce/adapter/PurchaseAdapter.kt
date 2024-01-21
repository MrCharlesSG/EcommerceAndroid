package hr.algebra.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.ecommerce.R
import hr.algebra.ecommerce.model.Purchase
import hr.algebra.ecommerce.ui.sheets.PurchasedProductsSheet
import hr.algebra.ecommerce.utils.getMoneyFormat
import java.time.LocalDate

class PurchaseAdapter(
    private val context: Context,
    private val purchases: List<Purchase>
) : RecyclerView.Adapter<PurchaseAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvPrice = itemView.findViewById<TextView>(R.id.tvTotalPrice)

        fun bind(purchase: Purchase) {
            val localDate = LocalDate.ofEpochDay(purchase._id)
            tvTitle.text = localDate.toString()
            tvPrice.text = getMoneyFormat(purchase.price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.purchase_card, parent, false)
        )
    }

    override fun getItemCount() = purchases.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            startBottomSheet(position)
        }
        // holder.setCardListener(position, context, products[position])
        holder.bind(purchases[position])
    }

    private fun startBottomSheet(position: Int) {
        val bottomSheetFragment = PurchasedProductsSheet(purchases[position])
        bottomSheetFragment.show(
            (context as AppCompatActivity).supportFragmentManager,
            bottomSheetFragment.tag
        )


    }

}