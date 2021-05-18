package com.dicoding.auliarosyida.academy.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity.CENTER
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.auliarosyida.academy.R

/**
 * termasuk customView
 *
 * Ketika Anda meng-extend kelas AppCompatButton, Anda bisa memanggil metode-metode yang ada di dalam kelas tersebut.
 * */
class MyButton: AppCompatButton {

    private var enabledBackground: Drawable? = null
    private var disabledBackground: Drawable? = null
    private var textColour: Int = 0

    /**
     * Ketika Anda membuat sebuah CustomView,
     * Anda perlu menambahkan konstruktor pada kelas tersebut.
     * Dan mengapa harus 3? Karena, kebutuhan dari masing masing peranti itu berbeda-beda.
     *
     * ketika Anda akan menambahkan aksi kepada CustomView maka Anda perlu menambahkan di tiap constructor-nya.
     * */
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    /**
     * Sebuah aksi, seperti onClick, onTouch dan aksi lainya maka perlu diletakkan di konstruktor.
     * Akan tetapi, jika ada perubahan bentuk, taruhlah kodenya dalam metode onDraw().
     * Metode ini bawaan dari kelas CustomView.
     * */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if (isEnabled) enabledBackground else disabledBackground
        setTextColor(textColour)
        textSize = 12f
        gravity = CENTER
    }

    private fun init() {
        val resources = resources
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button)
        disabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_disable)
        textColour = ContextCompat.getColor(context, android.R.color.background_light)
    }
}