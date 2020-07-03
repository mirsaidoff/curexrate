package uz.mirsaidoff.curexrate.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.details_fragment.*
import org.koin.android.ext.android.inject
import uz.mirsaidoff.curexrate.R
import uz.mirsaidoff.curexrate.common.showAlertDialog
import uz.mirsaidoff.curexrate.data.model.WeeklyRate
import java.text.NumberFormat
import kotlin.math.roundToInt

class DetailsFragment : Fragment(R.layout.details_fragment) {

    companion object {
        fun newInstance(currency: String) = DetailsFragment().apply {
            this.arguments = bundleOf("currency" to currency)
        }
    }

    private val viewModel: DetailsViewModel by inject()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val arguments = arguments
        updateChartData(listOf())
        viewModel.getLastSevenDayUpdate(arguments?.getString("currency"))
        initSubscriptions()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.update) refreshWeekRates()
        return super.onOptionsItemSelected(item)
    }

    private fun refreshWeekRates() {
        viewModel.getLastSevenDayUpdate(arguments?.getString("currency"))
    }

    private fun initSubscriptions() {
        viewModel.apply {
            resultLive.observe(viewLifecycleOwner) { updateChartData(it) }
            errorLive.observe(viewLifecycleOwner) { requireContext().showAlertDialog(it) }
            progressLive.observe(viewLifecycleOwner) {
                progress.isVisible = it
                chart.isVisible = !it
            }
        }
    }

    private fun updateChartData(data: List<WeeklyRate>) {
        val entries = mutableListOf<Entry>()

        var i = 1F
        for (it in data) {
            entries.add(Entry(i, it.rate.rate.toFloat()))
            i++
        }

        val dataSet = LineDataSet(entries, "Exchange rate")

        dataSet.color = ColorTemplate.getHoloBlue()
        dataSet.setDrawValues(false)
        dataSet.setDrawFilled(true)
        dataSet.lineWidth = 3f
        dataSet.circleRadius = 6f
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 16f

        chart.xAxis.labelRotationAngle = 0f
        chart.xAxis.axisMaximum = 7F + 0.1f
        chart.axisRight.isEnabled = false
        chart.description.text = "Days"
        chart.marker = CustomMarker(requireContext(), R.layout.cutom_chart_marker)

        chart.data = LineData(dataSet)
        chart.animateX(2000, Easing.EaseInExpo)
        chart.invalidate()
        chart.setNoDataText("No data is available to show")
    }

}
