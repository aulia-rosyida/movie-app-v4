package com.dicoding.auliarosyida.moviesapp.fav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class FavActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav)

        val tvChat = findViewById<TextView>(R.id.tv_chat)
        tvChat.text = "Hello ! \n Welcome to Favorite Feature"
    }
}