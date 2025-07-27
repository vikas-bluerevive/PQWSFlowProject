package com.example.pqwsflowproject

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.pqwsflowproject.databinding.SettingsScreenBinding


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

        binding.textView24.setOnClickListener{
            val settingsIntent: Intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(Settings.EXTRA_APP_PACKAGE, activity?.getPackageName())
                .putExtra(Settings.EXTRA_CHANNEL_ID, "Channel ID")
            startActivity(settingsIntent)
        }
        binding.textView30.setOnClickListener{
            if(bool == false) {
                bool = true
                binding.textView30.setText("Dark")
                activity?.application?.setTheme(R.style.Theme_PQWSFlowProject3)
             //   activity?.setTheme(bool)
                //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
               /* val intent =     Intent(binding.textView30.context, MainActivity::class.java)

                ContextCompat.startActivity(binding.textView30.context, intent, null)
                activity?.finish()*/
            }else{
                bool = false
                binding.textView30.setText("Light")
                //activity?.setTheme(bool)
                activity?.application?.setTheme(R.style.Theme_PQWSFlowProject2)
                //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
             /*   val intent =     Intent(binding.textView30.context, MainActivity::class.java)

                ContextCompat.startActivity(binding.textView30.context, intent, null)
                activity?.finish()*/
            }
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