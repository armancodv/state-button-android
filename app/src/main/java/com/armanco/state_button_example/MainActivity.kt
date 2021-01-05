package com.armanco.state_button_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.armanco.state_button.StateButton
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val handler = Handler()
    private val runnable: Runnable = object: Runnable {
        val states = StateButton.Companion.State.values()
        var count = 0
        override fun run() {
            autoChangeButton?.state = states[count % states.size]
            count ++
            handler.postDelayed(this, 2000)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handler.postDelayed(runnable, 2000)
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }
}