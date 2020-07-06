package com.example.dusigrosz

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class CofnijZmianyDialog(private val dluznikActivity: DluznikActivity) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Czy na pewno chcesz cofnąć zmiany?")
            .setMessage("Czy na pewno chcesz cofnąć zmiany?")
            .setPositiveButton("Tak") { _, _ ->
                dluznikActivity.finish()
            }
            .setNegativeButton("Nie") { dialog, _ -> dialog.cancel() }
            .setCancelable(false)
            .create()
    }

}