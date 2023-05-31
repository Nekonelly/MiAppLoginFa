package com.example.miapploginfa

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.CallbackManager.Factory.create
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import java.util.Arrays


private  var callbackManager= CallbackManager.Factory.create();
private lateinit var loginButton: LoginButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.miapploginfa.R.layout.activity_main)
        loginButton = findViewById<View>(com.example.miapploginfa.R.id.login_button) as LoginButton
        loginButton.setOnClickListener{
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult) {
                        val token = result.accessToken
                        val credential = FacebookAuthProvider.getCredential(token.token)
                        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this@MainActivity, "Funcionó", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@MainActivity, "Algo salió mal", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onCancel() {
                        Toast.makeText(this@MainActivity, "Canceló", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(error: FacebookException) {
                        Toast.makeText(this@MainActivity, "Ocurrió un error", Toast.LENGTH_SHORT).show()
                    }
                }
            )

        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


}