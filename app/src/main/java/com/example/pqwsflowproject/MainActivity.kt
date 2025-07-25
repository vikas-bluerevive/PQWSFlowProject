package com.example.pqwsflowproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.pqwsflowproject.databinding.ActivityMainBinding
import com.example.pqwsflowproject.ui.theme.PQWSFlowProjectTheme
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val fragment: Fragment? = null
            when (item.itemId) {

                R.id.page_1 -> {
                    /* val newFragment: Fragment = HomeFragment()
                     val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                     ft.replace(binding.frameContainer.id, newFragment).commit()*/

                    val newFragment: Fragment = DashBoardFragment()
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(binding.frameid.id, newFragment).commit()


                }
                R.id.page_2 -> {

                    /*    val newFragment: Fragment = ProductListFragment()
                        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                        ft.replace(binding.frameContainer.id, newFragment).commit()*/
                    val newFragment: Fragment = TankSchedulerFragment()
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
                    val newFragment: Fragment = SettingFragment()
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(binding.frameid.id, newFragment).commit()
                }
            }
            true
        })
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

