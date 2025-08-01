package com.example.pqwsflowproject

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.pqwsflowproject.Interface.TileClick
import com.example.pqwsflowproject.databinding.ActivityMainBinding
import com.example.pqwsflowproject.ui.theme.PQWSFlowProjectTheme
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityMainBinding
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var newFragment: Fragment = DashBoardFragment().also {
           it.TileClick(object : TileClick{
                override fun tileClick(click: Boolean) {
                   if(click){
                       val fragment: Fragment = TankSchedulerFragment()
                       val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                       ft.add(binding.frameid.id, fragment).commit()
                   }
                }
            })
        }

        var ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(binding.frameid.id, newFragment).commit()

        binding.bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val fragment: Fragment? = null
            when (item.itemId) {

                R.id.page_1 -> {
                    /* val newFragment: Fragment = HomeFragment()
                     val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                     ft.replace(binding.frameContainer.id, newFragment).commit()*/

                    val newFragment: Fragment = DashBoardFragment().also {
                        it.TileClick(object : TileClick{
                            override fun tileClick(click: Boolean) {
                                if(click){
                                    val fragment: Fragment = TankSchedulerFragment()
                                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                                    ft.add(binding.frameid.id, fragment).commit()
                                }
                            }
                        })
                    }
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(binding.frameid.id, newFragment).commit()


                }
                R.id.page_2 -> {

                    /*    val newFragment: Fragment = ProductListFragment()
                        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                        ft.replace(binding.frameContainer.id, newFragment).commit()*/
                  //  val newFragment: Fragment = TankSchedulerFragment()

                    val newFragment: Fragment = SchedulesFragment()
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(binding.frameid.id, newFragment).commit()

                }
                R.id.page_3 -> {

                    val newFragment: Fragment = MapScreenFragment()
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(binding.frameid.id, newFragment).commit()


                }
                R.id.page_4 -> {
                    val newFragment: Fragment = AlertFragment()
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(binding.frameid.id, newFragment).commit()
                }
                R.id.page_5 -> {
                    val newFragment: Fragment = SettingFragment().also {
                        it.TileClick(object : TileClick{
                            override fun tileClick(click: Boolean) {
                                if(click){
                                   binding.bottomNavigation.setSelectedItemId(R.id.page_3);
                                }
                            }
                        })
                    }
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(binding.frameid.id, newFragment).commit()
                }
            }
            true
        })
       /* val permissionState =
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        // If the permission is not granted, request it.
        // If the permission is not granted, request it.
        if (permissionState == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }*/

        /*FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
           //     storeTokenInFirestore(userId, token)
                Log.e("FCM", "Tken is "+ token)
            } else {
                Log.e("FCM", "Failed to fetch token", task.exception)
            }
        }*/
    }
        /*setContent {
            PQWSFlowProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }*/
    }
fun Activity.setTheme(night :Boolean) {

    // Or even have more than two theme styles
    this.setTheme(if (night) R.style.Theme_PQWSFlowProject3 else R.style.Theme_PQWSFlowProject2)
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PQWSFlowProjectTheme {
        Greeting("Android")
    }
}

