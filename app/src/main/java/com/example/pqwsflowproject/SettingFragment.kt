package com.example.pqwsflowproject

import android.app.Activity
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.pqwsflowproject.Interface.TileClick
import com.example.pqwsflowproject.databinding.SettingsScreenBinding
import com.github.dhaval2404.imagepicker.ImagePicker


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var bool :Boolean = false

    var tileClick :TileClick?=null
    var tileClick2 :TileClick?=null

    private lateinit var pref: SharedPreferences

    private  lateinit var mProfileUri :Uri
    private lateinit var binding : SettingsScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingsScreenBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        pref = activity?.getSharedPreferences("PrefMode", MODE_PRIVATE)!!
        binding.constraint1.setOnClickListener{
            val settingsIntent: Intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(Settings.EXTRA_APP_PACKAGE, activity?.getPackageName())
                .putExtra(Settings.EXTRA_CHANNEL_ID, "Channel ID")
            startActivity(settingsIntent)
        }

        val prefs =
            PreferenceManager.getDefaultSharedPreferences(activity) // getActivity() for Fragment
         bool = prefs.getBoolean("locked", false)

        if(bool){
            binding.textView30.setText("Dark")
        }else{
            binding.textView30.setText("Light")
        }


        binding.imageView7.setOnClickListener {
            tileClick?.tileClick(true)

        }

        var url = pref.getString("ImageUri","")
//        var uri : Uri = url as Uri
        val imageUri = Uri.parse(url)

        binding.imageView1.setImageURI(imageUri)



        binding.imageView11.setOnClickListener {
            tileClick2?.tileClick(true)
        }
        var preference  = activity?.getPreferences(Context.MODE_PRIVATE)

        binding.logoutBack.setOnClickListener {
            prefs.edit().putBoolean("LoginKey", false).commit()
            val intent = Intent(binding.logoutBack.context, LoginActivty::class.java)

            ContextCompat.startActivity(binding.logoutBack.context, intent, null)
            activity?. finish()
        }


        binding.textView30.setOnClickListener{
            if(bool == false) {
                bool = true
                binding.textView30.setText("Dark")
                val statusLocked = prefs.edit().putBoolean("locked",  bool).commit()
               // activity?.application?.setTheme(R.style.Theme_PQWSFlowProject3)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
             //   activity?.setTheme(bool)
                //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
               /* val intent =     Intent(binding.textView30.context, MainActivity::class.java)

                ContextCompat.startActivity(binding.textView30.context, intent, null)
                activity?.finish()*/
               // activity?.intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                TaskStackBuilder.create(activity)
                    .addNextIntent(Intent(activity, MainActivity::class.java))
                    .addNextIntent(activity?.intent?.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                    .startActivities()

                activity?.finish()
            }else{
                bool = false
                binding.textView30.setText("Light")
                val statusLocked = prefs.edit().putBoolean("locked",  bool).commit()
                //activity?.setTheme(bool)
                //activity?.application?.setTheme(R.style.Theme_PQWSFlowProject2)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
             /*   val intent =     Intent(binding.textView30.context, MainActivity::class.java)

                ContextCompat.startActivity(binding.textView30.context, intent, null)
                activity?.finish()*/
               // activity?.intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                TaskStackBuilder.create(activity)
                    .addNextIntent(Intent(activity, MainActivity::class.java))
                    .addNextIntent(activity?.intent?.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                    .startActivities()

                    activity?.finish()
            }
        }


        binding.imageView1.setOnClickListener {
            // galleryLauncher.launch("image/*")

            ImagePicker.with(this)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

    }

    open fun TileClick(tileClicked: TileClick){
        tileClick = tileClicked
    }

    open fun TileClick2(tileClicked2: TileClick){
        tileClick2 = tileClicked2
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                mProfileUri = fileUri
                pref.edit().putString("ImageUri", mProfileUri.toString()).commit()
                binding.imageView1.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}