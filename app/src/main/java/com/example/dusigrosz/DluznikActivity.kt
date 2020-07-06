package com.example.dusigrosz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class DluznikActivity : AppCompatActivity() {

    private val imieEditText by lazy { findViewById<EditText>(R.id.imieEditText) }
    private val nazwiskoEditText by lazy { findViewById<EditText>(R.id.nazwiskoEditText) }
    private val dlugEditText by lazy { findViewById<EditText>(R.id.dlugEditText) }
    private val dodajButton by lazy { findViewById<Button>(R.id.dodajButton) }
    private var index: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dluznik)

        if (intent.hasExtra("dluznik") && intent.hasExtra("index")) {
            dodajButton.text = "Edytuj"
            val dluznik = intent.getSerializableExtra("dluznik") as Dluznik
            imieEditText.setText(dluznik.imie)
            nazwiskoEditText.setText(dluznik.nazwisko)
            dlugEditText.setText(dluznik.dlug.toString())
            index = intent.getIntExtra("index", -1)
        }

    }

    fun symulujClick(view: View) {
        if (imieEditText.text.isNotEmpty() && nazwiskoEditText.text.isNotEmpty() && dlugEditText.text.isNotEmpty()) {
            val dluznik = stworzDluznika()
            val intent = Intent(this, SymulacjaActivity::class.java)
            intent.putExtra("dluznik", dluznik)
            startActivity(intent)
        }
    }

    fun cofnijButtonClick(view: View) {
        if (intent.hasExtra("dluznik") && intent.hasExtra("index")) {
            val dialog = CofnijZmianyDialog(this)
            dialog.isCancelable = false
            dialog.show(supportFragmentManager, "cofnijZmianyDialog")
        } else {
            finish()
        }
    }

    fun dodajButtonClicked(view: View) {
        if (imieEditText.text.isNotEmpty() && nazwiskoEditText.text.isNotEmpty() && dlugEditText.text.isNotEmpty()) {
            val dluznik = stworzDluznika()
            if (index != null) {
                if (index!! >= 0) {
                    setResult(Activity.RESULT_OK, Intent().apply {
                        putExtra("dluznik", dluznik)
                        val i = index
                        putExtra("index", i)
                    })
                }
            } else {
                setResult(Activity.RESULT_OK, Intent().apply {
                    putExtra("dluznik", dluznik)
                })
            }
            finish()
        }

    }

    private fun stworzDluznika(): Dluznik {
        val imie = imieEditText.text.toString()
        val nazwisko = nazwiskoEditText.text.toString()
        val dlug = dlugEditText.text.toString().toDouble()
        return Dluznik(imie, nazwisko, dlug)
    }

}
