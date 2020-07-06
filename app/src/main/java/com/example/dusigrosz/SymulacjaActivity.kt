package com.example.dusigrosz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SymulacjaActivity : AppCompatActivity() {

    private val imieTextView by lazy { findViewById<TextView>(R.id.imieTextView) }
    private val nazwiskoTextView by lazy { findViewById<TextView>(R.id.nazwiskoTextView) }
    private val dlugTextView by lazy { findViewById<TextView>(R.id.dlugTextView) }
    private val odsetkiTextView by lazy { findViewById<TextView>(R.id.odsetkiTextView) }
    private val pretkoscEditText by lazy { findViewById<EditText>(R.id.pretkoscEditText) }
    private val procentEditText by lazy { findViewById<EditText>(R.id.procentEditText) }
    private val playButton by lazy { findViewById<FloatingActionButton>(R.id.playButton) }
    private val handler = Handler(Looper.getMainLooper())
    private var dluznik: Dluznik? = null
    private var czyZapalzowany = false
    private var sumaOdsetek = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symulacja)

        wyswietlDluznika()
    }

    private fun wyswietlDluznika() {
        if (intent.hasExtra("dluznik")) {
            dluznik = intent.getSerializableExtra("dluznik") as Dluznik
            imieTextView.text = dluznik?.imie
            nazwiskoTextView.text = dluznik?.nazwisko
            dlugTextView.text = dluznik?.dlug.toString()
        }
    }

    private val runnable = object : Runnable {
        override fun run() {
            var odsetki = 0.0
            val procent = procentEditText.text.toString().toDouble() / 100
            var dlug = dlugTextView.text.toString().toDouble()
            if (czyZapalzowany) {
                handler.postDelayed(this, 1000)
                odsetki += dlug * procent
                dlug += odsetki
                dlug -= pretkoscEditText.text.toString().toDouble()
                sumaOdsetek += odsetki
                if (dlug <= 0) {
                    dlug = 0.0
                    handler.removeCallbacks(this)
                    playButton.performClick()
                }
                runOnUiThread {
                    dlugTextView.text = dlug.toString()
                }
            } else {
                handler.removeCallbacks(this)
            }
        }
    }

    fun symulate(view: View) {
        if (pretkoscEditText.text.isNotEmpty() && procentEditText.text.isNotEmpty() && dlugTextView.text.toString()
                .toDouble() > 0
        ) {
            czyZapalzowany = if (czyZapalzowany) {
                (view as FloatingActionButton).setImageResource(R.drawable.ic_play_arrow_black_24dp)
                odsetkiTextView.text = sumaOdsetek.toString()
                odsetkiTextView.visibility = View.VISIBLE
                pretkoscEditText.isEnabled = true
                procentEditText.isEnabled = true
                false
            } else {
                (view as FloatingActionButton).setImageResource(R.drawable.ic_pause_black_24dp)
                handler.post(runnable)
                pretkoscEditText.isEnabled = false
                procentEditText.isEnabled = false
                true
            }
        }
    }

}
