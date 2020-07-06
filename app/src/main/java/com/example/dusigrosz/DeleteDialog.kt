package com.example.dusigrosz

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DeleteDialog(
    private val listaDluznikow: MutableList<Dluznik>,
    private val adapter: ArrayAdapter<Dluznik>?,
    private val position: Int,
    private val sumaTextView: TextView
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Czy na pewno chcesz usunąć dłużnika?")
            .setMessage("Czy na pewno chcesz usunąć dłużnika?")
            .setPositiveButton("Tak") { _, _ ->
                listaDluznikow.removeAt(position)
                adapter?.notifyDataSetChanged()
                wyswietlSume(listaDluznikow)
            }
            .setNegativeButton("Nie") { dialog, _ -> dialog.cancel() }
            .setCancelable(false)
            .create()
    }

    private fun wyswietlSume(listaDluznikow: MutableList<Dluznik>) {
        var suma = 0.0
        listaDluznikow.forEach { suma += it.dlug }
        sumaTextView.text = resources.getString(R.string.suma) + ": $suma"
    }
}