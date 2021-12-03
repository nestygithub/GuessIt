package com.chiki.guessit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    //Binding
  //  private lateinit var binding: ActivityMainBinding

    //Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setContentView(R.layout.activity_main)
    }
}
