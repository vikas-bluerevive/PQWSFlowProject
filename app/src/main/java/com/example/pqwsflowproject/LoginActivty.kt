package com.example.pqwsflowproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pqwsflowproject.databinding.ActivityLoginBinding
import com.example.pqwsflowproject.databinding.ActivityMainBinding
import com.example.pqwsflowproject.model.LoginResponse
import com.example.pqwsflowproject.model.LoginResponse2
import com.example.pqwsflowproject.ui.theme.PQWSFlowProjectTheme
import com.example.pqwsflowproject.utils.CommonFunction
import com.example.pqwsflowproject.viewmodels.LoginViewModel
import com.example.pqwsflowproject.viewmodels.MainActivityViewModel

class LoginActivty: FragmentActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var loginViewModel: LoginViewModel
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

        binding = ActivityLoginBinding.inflate(layoutInflater)
        if(prefs.getBoolean("LoginKey",false)== true){
            val intent =     Intent(this, MainActivity::class.java)

            ContextCompat.startActivity(this, intent, null)
            finish()

        }else {

            setContentView(binding.root)
        }
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.materialButton3.setOnClickListener{


            if(!isValidEmail(binding.editEmail.text.toString())){

               Toast.makeText(this,"Enter valid Email",Toast.LENGTH_LONG).show()
            }else if(!isValidPassword(binding.editPassword.text.toString())){

                Toast.makeText(this,"Enter valid Password",Toast.LENGTH_LONG).show()
            }else{

                loginViewModel.loginIntoApp(binding.editEmail.text.toString(),binding.editPassword.text.toString())

                /*val intent =     Intent(binding.materialButton3.context, MainActivity::class.java)

                ContextCompat.startActivity(binding.materialButton3.context, intent, null)*/

               /* prefs.edit().putBoolean("LoginKey", true).commit()
                val intent = Intent(binding.materialButton3.context, MainActivity::class.java)

                ContextCompat.startActivity(binding.materialButton3.context, intent, null)
                finish()*/

            }


        }

        loginViewModel.feedBackMessage.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
        })

        loginViewModel.progressBar.observe(this, Observer<Boolean> {
            if (it) {
                CommonFunction.showProgressBar(this,"Loading..")
            } else {
                CommonFunction.hideProgressBar()
            }
        })

        loginViewModel.successfullyLogin.observe(this, Observer {
                 var loginRes : LoginResponse2? = it
       //     Log.e("LoginResponse","log in response "+ loginRes.name+"   "+loginRes.status+" "+loginRes.userId)

            loginRes?.let {
                  if(it.success ==true) {
                      prefs.edit()     .putBoolean("LoginKey", true).commit()
                      val intent = Intent(binding.materialButton3.context, MainActivity::class.java)

                      ContextCompat.startActivity(binding.materialButton3.context, intent, null)
                      finish()
                  }else{

                      Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
                  }
            }
           /* val intent =     Intent(binding.materialButton3.context, MainActivity::class.java)

            ContextCompat.startActivity(binding.materialButton3.context, intent, null)*/
        })


   /* setContent {
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

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        if (password.length < 8) return false
        if (password.filter { it.isDigit() }.firstOrNull() == null) return false
       // if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
        //if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

        return true
    }

}