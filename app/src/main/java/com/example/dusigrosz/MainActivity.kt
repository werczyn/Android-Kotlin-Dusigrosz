package com.example.dusigrosz

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

const val ADD_REQUEST_CODE = 0
const val EDIT_REQUEST_CODE = 1

class MainActivity : AppCompatActivity() {

    private val sumaTextView by lazy { findViewById<TextView>(R.id.sumaTextView) }
    private val dluznikListView by lazy { findViewById<ListView>(R.id.dluznikListView) }
    private var listaDluznikow = mutableListOf<Dluznik>()
    private var adapter: ArrayAdapter<Dluznik>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wyswietlListe()
        wyswietlSume(listaDluznikow)

        dluznikListView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DluznikActivity::class.java)
            intent.putExtra("dluznik", listaDluznikow[position])
            intent.putExtra("index", position)
            startActivityForResult(intent, EDIT_REQUEST_CODE)
        }

        dluznikListView.setOnItemLongClickListener { _, _, position, _ ->
            val dialog = DeleteDialog(listaDluznikow, adapter, position, sumaTextView)
            dialog.isCancelable = false
            dialog.show(supportFragmentManager, "deleteDialog")
            true
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null && resultCode == Activity.RESULT_OK) {
            if (requestCode == ADD_REQUEST_CODE) {
                val dluznik = data.getSerializableExtra("dluznik") as Dluznik
                listaDluznikow.add(dluznik)
                adapter?.notifyDataSetChanged()
            }
            if (requestCode == EDIT_REQUEST_CODE) {
                val dluznik = data.getSerializableExtra("dluznik") as Dluznik
                val index = data.getIntExtra("index", -1)
                if (index >= 0) {
                    listaDluznikow[index] = dluznik
                }
                adapter?.notifyDataSetChanged()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
        wyswietlSume(listaDluznikow)
    }

    fun dodajDluznikaClicked(view: View) {
        val intent = Intent(this, DluznikActivity::class.java)
        startActivityForResult(intent, ADD_REQUEST_CODE)
    }

    private fun wyswietlListe() {
        listaDluznikow = mutableListOf(
            Dluznik("Jan", "Biedny", 200.0),
            Dluznik("Michal", "Płaski", 1000.0)
        )
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaDluznikow)
        dluznikListView.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    private fun wyswietlSume(listaDluznikow: MutableList<Dluznik>) {
        var suma = 0.0
        listaDluznikow.forEach { suma += it.dlug }
        sumaTextView.text = resources.getString(R.string.suma)+" : $suma"
    }

}
