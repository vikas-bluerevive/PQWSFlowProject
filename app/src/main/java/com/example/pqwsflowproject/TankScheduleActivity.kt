package com.example.pqwsflowproject

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.pqwsflowproject.databinding.ActivityMainBinding
import com.example.pqwsflowproject.databinding.ActivityTankScheduleBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter

class TankScheduleActivity : FragmentActivity() {

    private lateinit var binding: ActivityTankScheduleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTankScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // setContentView(R.layout.activity_tank_schedule)



        val instantWaterCode = arrayOf("Instant Water Supply", "true", "false")
        var instantWaterCodeAdapter =
            ArrayAdapter<CharSequence>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                instantWaterCode
            )

        binding.spinner2.adapter = instantWaterCodeAdapter

        val timeScheduleWaterSupplyCode = arrayOf("Time Schedule Water Supply", "time1", "time2","time3")
        var timeScheduleWaterSupplyCodeAdapter =
            ArrayAdapter<CharSequence>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                timeScheduleWaterSupplyCode
            )

        binding.spinner3.adapter = timeScheduleWaterSupplyCodeAdapter

        val waterToBeFilledCode = arrayOf("Select Water to be filled", "water1", "water2","water3")

        var waterToBeFilledCodeAdapter =
            ArrayAdapter<CharSequence>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                waterToBeFilledCode
            )


        binding.spinner4.adapter = waterToBeFilledCodeAdapter


        val lineChart = binding.lineChart
        setupLineChartStyle(lineChart)
        loadChartData(lineChart)
    }
    private fun setupLineChartStyle(chart: LineChart) {
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.setTouchEnabled(true)
        chart.setPinchZoom(true)
        chart.setDrawGridBackground(false)

        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.textColor = Color.GRAY
        xAxis.valueFormatter = DateAxisValueFormatter()

        val leftAxis = chart.axisLeft
        leftAxis.setDrawGridLines(true)
        leftAxis.gridColor = Color.parseColor("#E0E0E0")
        leftAxis.textColor = Color.GRAY
        leftAxis.axisLineColor = Color.TRANSPARENT
        leftAxis.axisMinimum = 30000f
        leftAxis.valueFormatter = YAxisValueFormatter()

        chart.axisRight.isEnabled = false
    }
    private fun loadChartData(chart: LineChart) {
        val entries = ArrayList<Entry>()
        entries.add(Entry(24f, 32000f))
        entries.add(Entry(25f, 34000f))
        entries.add(Entry(26f, 33500f))
        entries.add(Entry(27f, 36000f))
        entries.add(Entry(28f, 39000f))
        entries.add(Entry(29f, 38000f))
        entries.add(Entry(30f, 48000f))

        val dataSet = LineDataSet(entries, "Water Needed")

        // --- STYLING CHANGES ARE APPLIED BELOW ---

        // --- FIX 2: Set Line Mode to Linear ---
        // This ensures the line is drawn with sharp, straight segments instead of curves.
        dataSet.mode = LineDataSet.Mode.LINEAR

        // 1. Set Line Color and Width
        dataSet.color = Color.parseColor("#3B82F6") // The exact blue from the image
        dataSet.lineWidth = 3f // A slightly thicker line

        // 2. Disable drawing values on the chart
        dataSet.setDrawValues(false)

        // 3. Style the Data Point Circles
        dataSet.setDrawCircles(true)
        dataSet.circleRadius = 8f // Make the outer circle larger
        dataSet.setCircleColor(Color.parseColor("#3B82F6")) // Blue outer circle
        dataSet.setDrawCircleHole(true)
        dataSet.circleHoleRadius = 4f // Make the inner hole larger
        dataSet.circleHoleColor = Color.WHITE // White inner circle

        // 4. Configure the Gradient Fill
        dataSet.setDrawFilled(true)
        dataSet.fillDrawable = ContextCompat.getDrawable(this, R.drawable.chart_gradient)
        dataSet.fillAlpha = 85 // Make the gradient slightly more transparent

        // --- END OF STYLING CHANGES ---

        chart.data = LineData(dataSet)
        chart.invalidate() // Refresh the chart
    }

}
// Custom class to format Y-Axis labels (e.g., 35000 -> "35K")
class YAxisValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return "${(value / 1000).toInt()}K"
    }
}

// Custom class to format X-Axis labels (e.g., 24.0 -> "Nov 24")
class DateAxisValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return "Nov ${value.toInt()}"
    }
}
