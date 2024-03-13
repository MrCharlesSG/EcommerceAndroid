package hr.algebra.ecommerce.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import hr.algebra.ecommerce.App
import hr.algebra.ecommerce.R
import hr.algebra.ecommerce.databinding.FragmentTotalExpendedBinding
import hr.algebra.ecommerce.model.Purchase
import hr.algebra.ecommerce.utils.getMoneyFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TotalExpendedFragment : Fragment() {

    private var _binding: FragmentTotalExpendedBinding? = null
    private val binding get() = _binding!!
    private lateinit var purchases : List<Purchase>
    private var totalExpended = 0

    private fun loadPurchases() {
        GlobalScope.launch (Dispatchers.Main) {
            (context?.applicationContext as App).getPurchaseAS().getAllPurchasesAndTotal{
                totalExpended= it.first
                purchases = it.second
                setupChart()
                setupTotalPrice()
            }
        }
    }

    private fun setupTotalPrice() {
        binding.tvTotalPrice.text = getMoneyFormat(totalExpended)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTotalExpendedBinding.inflate(inflater, container, false)
        loadPurchases()
        return binding.root
    }

    private fun setupChart() {
        val lineChart: LineChart = binding.chart

        val dataEntries = mutableListOf<Entry>()
        purchases.forEach {
            dataEntries.add(
                Entry(
                    it._id.toFloat(),
                    it.price.toFloat()
                )
            )
        }

        val dataSet = LineDataSet(dataEntries, "Price")
        dataSet.color = context?.let { ContextCompat.getColor(it, R.color.main_color) }!!
        dataSet.lineWidth = 2f

        val xAxis: XAxis = lineChart.xAxis
        xAxis.valueFormatter = DateValueFormatter()
        val yAxis = lineChart.axisLeft
        yAxis.setDrawGridLines(false)

        val lineData = LineData(dataSet)
        lineChart.data = lineData

        lineChart.description.text=""
        lineChart.invalidate()
        lineChart.animateX(1000)
    }

    inner class DateValueFormatter : ValueFormatter() {
        private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return dateFormat.format(Date(value.toLong()))
        }
    }
}