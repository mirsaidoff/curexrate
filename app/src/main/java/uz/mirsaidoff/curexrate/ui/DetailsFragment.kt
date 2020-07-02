package uz.mirsaidoff.curexrate.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.details_fragment.*
import org.koin.android.ext.android.inject
import uz.mirsaidoff.curexrate.R
import uz.mirsaidoff.curexrate.common.showAlertDialog
import uz.mirsaidoff.curexrate.data.model.WeeklyRate
import kotlin.math.roundToInt

class DetailsFragment : Fragment(R.layout.details_fragment) {

    companion object {
        fun newInstance(currency: String) = DetailsFragment().apply {
            this.arguments = bundleOf("currency" to currency)
        }
    }

    private val viewModel: DetailsViewModel by inject()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val arguments = arguments
        viewModel.getLastSevenDayUpdate(arguments?.getString("currency"))
        initSubscriptions()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.update) refreshWeekRates()
        return super.onOptionsItemSelected(item)
    }

    private fun refreshWeekRates() {
        TODO("Not yet implemented")
    }

    private fun initSubscriptions() {
        viewModel.resultLive.observe(viewLifecycleOwner) { updateChartData(it) }
        viewModel.errorLive.observe(viewLifecycleOwner) { requireContext().showAlertDialog(it) }
    }

    private fun updateChartData(data: List<WeeklyRate>) {
        val entries = mutableListOf<BarEntry>()
        val axisValues = mutableListOf<String>()

        for ((i, it) in data.withIndex()) {
            val barEntry = BarEntry(i.toFloat(), it.rate.rate.toFloat())
            barEntry.data = it.date
            entries.add(barEntry)
            axisValues.add(it.date)
        }
        val dataSet = BarDataSet(entries, "")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        chart.data = BarData(dataSet)
        chart.invalidate()
        chart.animateXY(1000, 1000)
    }

}
