package com.velja.financeapp.Activities.IntroActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.velja.financeapp.Activities.IntroActivity.screens.IntroScreen
import com.velja.financeapp.Activities.DashboardActivity.MainActivity

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            IntroScreen (onStartClick = {
                startActivity(Intent(this, MainActivity::class.java))
            })
        }
    }
}