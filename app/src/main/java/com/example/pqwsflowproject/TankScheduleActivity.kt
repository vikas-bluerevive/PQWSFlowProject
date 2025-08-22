package com.example.pqwsflowproject


import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pqwsflowproject.adapter.TimeSlotAndWaterLevelAdapter
import com.example.pqwsflowproject.databinding.ActivityTankScheduleBinding
import com.example.pqwsflowproject.model.FlowValues
import com.example.pqwsflowproject.model.HoursItem2
import com.example.pqwsflowproject.model.ScheduleSucessResponse
import com.example.pqwsflowproject.model.WaterSummaryResponse
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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

class TankScheduleActivity : FragmentActivity(), DatePickerDialog.OnDateSetListener,
TimePickerDialog.OnTimeSetListener{

    private lateinit var binding: ActivityTankScheduleBinding
    private var  date_time :String = ""
    private lateinit var  datePickerDialog :DatePickerDialog
    private  lateinit var  timePickerDialog: TimePickerDialog

    private lateinit var mainActivityViewModel: MainActivityViewModel

    private lateinit var pref: SharedPreferences

    private lateinit var  hourFlowList : MutableList<HoursItem2>
    private var hoursFlowItems : ArrayList<FlowValues> = ArrayList()

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

        pref = getSharedPreferences("PrefMode", MODE_PRIVATE)
        val tankName= intent.getExtras()?.getString("TankName")

        binding.tankText?.setText(tankName)




        val instantWaterCode = arrayOf("Instant Water Supply", "true", "false")
        val instantWaterCodeAdapter =
            ArrayAdapter<CharSequence>(
                this,
                R.layout.spinner_dropdown,
                instantWaterCode
            )

        binding.spinner2?.adapter = instantWaterCodeAdapter

        binding.spinner2?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                p2: Int,
                p3: Long
            ) {
               if(p2 == 1){
                   binding.editTimeSchedule.isEnabled = false
                   binding.relativeLayout2.visibility =  View.GONE

               }else if(p2 ==2){
                   binding.editTimeSchedule.isEnabled = true
                   binding.relativeLayout2.visibility = View.VISIBLE
               }else {
                   binding.editTimeSchedule.isEnabled = true
                   binding.relativeLayout2.visibility = View.VISIBLE
               }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        })
      /*  if(binding.switch1?.isChecked == true){
            binding.editTimeSchedule.isEnabled = false
            binding.relativeLayout2.visibility =  View.GONE
        }else{
            binding.editTimeSchedule.isEnabled = true
            binding.relativeLayout2.visibility = View.VISIBLE
        }

        binding.switch1?.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
              if(isChecked){

                  binding.editTimeSchedule.isEnabled = false
                  binding.relativeLayout2.visibility =  View.GONE
              }else{
                  binding.editTimeSchedule.isEnabled = true
                  binding.relativeLayout2.visibility = View.VISIBLE
              }
            }
        })
*/
        mainActivityViewModel.progressBar.observe(this, Observer<Boolean> {
            if (it) {
                CommonFunction.showProgressBar(this,"Loading..")
            } else {
                CommonFunction.hideProgressBar()
            }
        })
        binding.normalContinuousSlider?.value = 3000F

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
            val scheduleSucessResponse : ScheduleSucessResponse? = it
            scheduleSucessResponse?.let{

                Toast.makeText(this,it.data , Toast.LENGTH_LONG).show()
            }
        })

        mainActivityViewModel.createInstantSchedule.observe(this, Observer{
            val scheduleSucessResponse  : ScheduleSucessResponse? = it
            scheduleSucessResponse?.let{
                Toast.makeText(this,it.data , Toast.LENGTH_LONG).show()
            }

        })

        binding.editTimeSchedule.setOnClickListener {
            datePicker()
        }
        binding.editTimeSchedule.setFocusable(false)


        val waterToBeFilledCode = arrayOf("Select Water to be filled", "water1", "water2","water3")

        val waterToBeFilledCodeAdapter =
            ArrayAdapter<CharSequence>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                waterToBeFilledCode
            )


        binding.spinner4?.adapter = waterToBeFilledCodeAdapter


        binding.materialButton3.setOnClickListener {
            val spinnerInstantSelection = binding.spinner2?.selectedItemPosition

            Log.e("InstantCheck","Instant checks are "+binding.editTimeSchedule.text.toString().equals("") + "  "+spinnerInstantSelection)
            if(!binding.editTimeSchedule.text.toString().equals(" ")){

              //  if(binding.switch1?.isChecked == true){
                    if(spinnerInstantSelection ==1){
                  mainActivityViewModel.createScedule(pref.getInt("sourceId",0),pref.getInt("supplyId",0),binding.editTimeSchedule.text.toString())
                }else if(spinnerInstantSelection == 2){
                   // else if(binding.switch1?.isChecked == false){
                    mainActivityViewModel.createInstantSchedule(pref.getInt("sourceId",0),pref.getInt("supplyId",0))

                }
            }else{


            }

        }

        mainActivityViewModel.waterSummaryRes.observe(this,Observer{
            val waterSummaryRes : WaterSummaryResponse? = it
            waterSummaryRes?.let{
                hourFlowList = it.data?.hours as MutableList<HoursItem2>

                for((index, values) in hourFlowList.withIndex()){
                    var timeslot =""
                     if(index < 10){
                        timeslot = "" + (index+1) + " Am" + " - " + (index + 2) + " Am"
                    }else if(index == 10){
                        timeslot = "" + (index+1)  + " Am" + " - " + (index + 2)  + " Pm"

                    }else if(index == 11){
                         timeslot = "" + (index+1)  + " Pm" + " - " + 1  + " Pm"
                    } else if(index > 11 && index < 22){
                        timeslot = "" + ( (index + 1) -12) + " Pm" + " - " +( (index + 2) - 12) + " Pm"
                    }else if(index == 22){

                         timeslot = "" + ((index + 1) -12)  + " Pm" + " - " +( (index + 2) - 12)  + " Am"
                    } else if(index == 23){

                        timeslot = "" + 12 + " Am" + " - " +  1 + " Am"
                    }

                   val flowValues = FlowValues(timeslot, values.waterVolumeLitres as Double?)
                    hoursFlowItems.add(flowValues)
                }
                //var flowValue = FlowValues()

               Log.e("WaterSummaryRes","water summary res is "+waterSummaryRes)
                val waterLevelAndTimeSlotAdapter = TimeSlotAndWaterLevelAdapter(hoursFlowItems)
                binding.recyclerTimeAndlevelListing?.let {
                    it.layoutManager = LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,false)
                }
                binding.recyclerTimeAndlevelListing?.let { it.adapter = waterLevelAndTimeSlotAdapter }
            }
        })


        val waterLevelAndTimeSlotAdapter = TimeSlotAndWaterLevelAdapter(hoursFlowItems)
        binding.recyclerTimeAndlevelListing?.let {
            it.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false)
        }
        binding.recyclerTimeAndlevelListing?.let { it.adapter = waterLevelAndTimeSlotAdapter }

        val c: Calendar = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)

        val date  = ""+mDay+"-"+"0"+(mMonth+1)+"-"+mYear
        val date1 =  ""+mYear+"-"+"0"+(mMonth+1)+"-"+mDay
        binding.dateText?.setText(date1)
        mainActivityViewModel.getWaterSummary(pref.getInt("supplyId",0),date1)
        binding.imageView12?.setOnClickListener {

            datePickerCalender(mYear,mMonth,mDay)

        }

        val lineChart = binding.lineChart
        setupLineChartStyle(lineChart)
        loadChartData(lineChart)

        var url = pref.getString("ImageUri","")
//        var uri : Uri = url as Uri
        val imageUri = Uri.parse(url)

        binding.imageView1.setImageURI(imageUri)
    }

    private fun datePickerCalender(mYear: Int, mMonth: Int, mDay: Int) {

     //   var datePickerDialog2 = DatePickerDialog(this,
     //       { view, year, monthOfYear, dayOfMonth ->
     //           date_time = dayOfMonth.toString() + "-"+"0" + (monthOfYear + 1) + "-" + year

                //*************Call Time Picker Here ********************
    ///            binding.dateText?.setText(date_time)
    //            var date =""+year+"-"+"0"+(monthOfYear + 1)+"-"+dayOfMonth.toString()
    ///             mainActivityViewModel.getWaterSummary(pref.getInt("supplyId",0),date)

    //        }, mYear, mMonth, mDay
   //     )
   //     datePickerDialog2.show()
   //     datePickerDialog2.getDatePicker().setMaxDate(System.currentTimeMillis());

    }


    private fun datePicker() {

        // Get Current Date
        val c: Calendar = Calendar.getInstance()
        var mYear = c.get(Calendar.YEAR)
        var mMonth = c.get(Calendar.MONTH)
        var mDay = c.get(Calendar.DAY_OF_MONTH)
        var datpickerListener = object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(
                view: DatePickerDialog?,
                year: Int,
                monthOfYear: Int,
                dayOfMonth: Int
            ) {
                timePicker(year, monthOfYear,dayOfMonth)
            }
        }

        datePickerDialog =   DatePickerDialog.newInstance(datpickerListener, mYear, mMonth, mDay);
        datePickerDialog.setThemeDark(false);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setTitle("Date Picker");
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog")


        datePickerDialog.setOnDateSetListener(datpickerListener)

      datePickerDialog.setMinDate(c);










       //  datePickerDialog = DatePickerDialog(this,
       //     { view, year, monthOfYear, dayOfMonth ->
       //         date_time = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                //*************Call Time Picker Here ********************
      //          timePicker()
     //       }, mYear, mMonth, mDay
     //   )
     //   datePickerDialog.show()
     //   datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000




    }
    private fun timePicker(mYear: Int,mMonth: Int, mDay: Int) {
        // Get Current Time
        val c = Calendar.getInstance()
        var mHour = c[Calendar.HOUR_OF_DAY]
        var mMinute = c[Calendar.MINUTE]

        var timepickerListener = object: TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(
                view: TimePickerDialog?,
                hourOfDay: Int,
                minute: Int,
                second: Int
            ) {
                val calendar = Calendar.getInstance()
                calendar.set(mYear, mMonth, mDay,  hourOfDay,minute)

                val gmtFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                /* val gmtTime: TimeZone = TimeZone.getTimeZone("GMT")
                 gmtFormat.setTimeZone(gmtTime)*/
                val mydate = "" + gmtFormat.format(calendar.time)
                // binding.editTimeSchedule.setText(date_time + " " + hourOfDay + ":" + minute)
                binding.editTimeSchedule.setText(mydate)
            }
        }

        // Launch Time Picker Dialog

         timePickerDialog = TimePickerDialog.newInstance(timepickerListener , mHour, mMinute,false );
                timePickerDialog.setThemeDark(false);
                //timePickerDialog.showYearPickerFirst(false);
                timePickerDialog.setTitle("Time Picker");



                timePickerDialog.show(getFragmentManager(), "TimePickerDialog");



        timePickerDialog.setOnTimeSetListener(timepickerListener )

        // Launch Time Picker Dialog
      //  timePickerDialog = TimePickerDialog(this,
      //      { view, hourOfDay, minute ->
     //           mHour = hourOfDay
     //           mMinute = minute
     //           val calendar = Calendar.getInstance()
     //           calendar.set(datePickerDialog.datePicker.getYear(), datePickerDialog.datePicker.getMonth(), datePickerDialog.datePicker.getDayOfMonth(), mHour,mMinute)

      //          val gmtFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                /* val gmtTime: TimeZone = TimeZone.getTimeZone("GMT")
                 gmtFormat.setTimeZone(gmtTime)*/
      //          val mydate = "" + gmtFormat.format(calendar.time)
               // binding.editTimeSchedule.setText(date_time + " " + hourOfDay + ":" + minute)
      //          binding.editTimeSchedule.setText(mydate)

     //           Log.e("DateTime","date time set is "+mydate)
                //et_show_date_time.setText()
    //        }, mHour, mMinute, false
    //    )
     //   timePickerDialog.show()



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

    override fun onDateSet(
        view: DatePickerDialog?,
        year: Int,
        monthOfYear: Int,
        dayOfMonth: Int
    ) {

    }

    override fun onTimeSet(
        view: TimePickerDialog?,
        hourOfDay: Int,
        minute: Int,
        second: Int
    ) {

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
