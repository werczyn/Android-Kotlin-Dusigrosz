package com.example.dusigrosz

import android.util.Log
import java.io.Serializable

data class Dluznik(var imie: String, var nazwisko: String, var dlug: Double) : Serializable {
    override fun toString(): String {
        return "$imie $nazwisko $dlug zł"
    }

}

