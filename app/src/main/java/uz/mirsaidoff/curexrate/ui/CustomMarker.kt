package uz.mirsaidoff.curexrate.ui

import android.content.Context
import androidx.annotation.LayoutRes
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import kotlinx.android.synthetic.main.cutom_chart_marker.view.*

class CustomMarker(context: Context, @LayoutRes layoutRes: Int) : MarkerView(context, layoutRes) {
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        val value = e?.y?.toDouble() ?: 0.0
        var resText = ""
        resText = if(value.toString().length > 8){
            "Val: " + value.toString().substring(0,7)
        } else{
            "Val: $value"
        }
        tv_value.text = resText
        super.refreshContent(e, highlight)
    }
}