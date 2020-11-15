package com.armanco.state_button_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.armanco.state_button.StateButton
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        disableButton?.setOnClickListener {
            disableButton?.state = StateButton.Companion.State.DISABLED
            disableButton?.text = "Disabled"
        }
        loadButton?.setOnClickListener {
            loadButton?.state = StateButton.Companion.State.LOADING
            loadButton?.text = "Loading"
        }
        errorButton?.setOnClickListener {
            errorButton?.state = StateButton.Companion.State.ERROR
            errorButton?.text = "Error"
        }
    }
}