package com.example.pqwsflowproject

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pqwsflowproject.databinding.ActivityTankScheduleBinding
import com.example.pqwsflowproject.model.ScheduleSucessResponse
import com.example.pqwsflowproject.utils.CommonFunction
import com.example.pqwsflowproject.viewmodels.MainActivityViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar


class TankScheduleActivity : FragmentActivity() {

    private lateinit var binding: ActivityTankScheduleBinding
    private var  date_time :String = ""
    private lateinit var  datePickerDialog :DatePickerDialog
    private  lateinit var  timePickerDialog: TimePickerDialog

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs =
            PreferenceManager.getDefaultSharedPreferences(this)
        val yourLocked: Boolean = prefs.getBoolean("locked", false)
        if(yourLocked){
            setTheme(R.style.Theme_PQWSFlowProject3)
        }else{
            setTheme(R.style.Theme_PQWSFlowProject2)
        }
        super.onCreate(savedInstanceState)

        binding = ActivityTankScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // setContentView(R.layout.activity_tank_schedule)
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)




        val instantWaterCode = arrayOf("Instant Water Supply", "true", "false")
        var instantWaterCodeAdapter =
            ArrayAdapter<CharSequence>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                instantWaterCode
            )

        binding.spinner2.adapter = instantWaterCodeAdapter

        binding.spinner2.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                p2: Int,
                p3: Long
            ) {
               if(p2 == 1){
                   binding.editTimeSchedule.isEnabled = false

               }else if(p2 ==2){
                   binding.editTimeSchedule.isEnabled = true
               }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        })

        mainActivityViewModel.progressBar.observe(this, Observer<Boolean> {
            if (it) {
                CommonFunction.showProgressBar(this,"Loading..")
            } else {
                CommonFunction.hideProgressBar()
            }
        })

        /*var timeScheduleWaterSupplyCode = arrayOf("Time Schedule Water Supply", "time1", "time2","time3")
        var timeScheduleWaterSupplyCodeAdapter =
            ArrayAdapter<CharSequence>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                timeScheduleWaterSupplyCode
            )


        binding.spinner3.adapter = timeScheduleWaterSupplyCodeAdapter

        binding.spinner3.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                 datePicker()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }


        })*/

        mainActivityViewModel.createSchedule.observe(this,Observer{
            var scheduleSucessResponse : ScheduleSucessResponse? = it
            scheduleSucessResponse?.let{

                Toast.makeText(this,it.data , Toast.LENGTH_LONG).show()
            }
        })

        mainActivityViewModel.createInstantSchedule.observe(this, Observer{
            var scheduleSucessResponse  : ScheduleSucessResponse? = it
            scheduleSucessResponse?.let{
                Toast.makeText(this,it.data , Toast.LENGTH_LONG).show()
            }

        })

        binding.editTimeSchedule.setOnClickListener {
            datePicker()
        }
        binding.editTimeSchedule.setFocusable(false);


        val waterToBeFilledCode = arrayOf("Select Water to be filled", "water1", "water2","water3")

        var waterToBeFilledCodeAdapter =
            ArrayAdapter<CharSequence>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                waterToBeFilledCode
            )


        binding.spinner4.adapter = waterToBeFilledCodeAdapter


        binding.materialButton3.setOnClickListener {
            var spinnerInstantSelection = binding.spinner2.selectedItemPosition

            Log.e("InstantCheck","Instant checks are "+binding.editTimeSchedule.text.toString().equals("") + "  "+spinnerInstantSelection)
            if(!binding.editTimeSchedule.text.toString().equals(" ")){

                if(spinnerInstantSelection == 2){

                  mainActivityViewModel.createScedule(3,2,binding.editTimeSchedule.text.toString())
                }else if(spinnerInstantSelection == 1){
                    mainActivityViewModel.createInstantSchedule(3,2)

                }
            }else{


            }

        }


        val lineChart = binding.lineChart
        setupLineChartStyle(lineChart)
        loadChartData(lineChart)
    }


    private fun datePicker() {

        // Get Current Date
        val c: Calendar = Calendar.getInstance()
        var mYear = c.get(Calendar.YEAR)
        var mMonth = c.get(Calendar.MONTH)
        var mDay = c.get(Calendar.DAY_OF_MONTH)
         datePickerDialog = DatePickerDialog(this,
            { view, year, monthOfYear, dayOfMonth ->
                date_time = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                //*************Call Time Picker Here ********************
                timePicker()
            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
    }
    private fun timePicker() {
        // Get Current Time
        val c = Calendar.getInstance()
        var mHour = c[Calendar.HOUR_OF_DAY]
        var mMinute = c[Calendar.MINUTE]

        // Launch Time Picker Dialog
        timePickerDialog = TimePickerDialog(this,
            { view, hourOfDay, minute ->
                mHour = hourOfDay
                mMinute = minute
                val calendar = Calendar.getInstance()
                calendar.set(datePickerDialog.datePicker.getYear(), datePickerDialog.datePicker.getMonth(), datePickerDialog.datePicker.getDayOfMonth(), mHour,mMinute)

                val gmtFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                /* val gmtTime: TimeZone = TimeZone.getTimeZone("GMT")
                 gmtFormat.setTimeZone(gmtTime)*/
                val mydate = "" + gmtFormat.format(calendar.time)
               // binding.editTimeSchedule.setText(date_time + " " + hourOfDay + ":" + minute)
                binding.editTimeSchedule.setText(mydate)

                Log.e("DateTime","date time set is "+mydate)
                //et_show_date_time.setText()
            }, mHour, mMinute, false
        )
        timePickerDialog.show()



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
