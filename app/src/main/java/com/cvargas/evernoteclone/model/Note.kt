package com.cvargas.evernoteclone.model

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


data class Note(
    var id: Int = 0,
    var title: String? = null,
    var desc: String? = null,
    var date: String? = null,
    var body: String? = null
) {
    @Inject
    constructor() : this(0)

    val createdDate: String
        get() {
            if (this.date.isNullOrBlank()) return ""

            return try {
                val locale = Locale("pt", "Br")
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", locale)
                val resultDate =
                    SimpleDateFormat("MMM yyyy", locale).format(dateFormat.parse(date!!)!!)
                resultDate.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }
            } catch (e: ParseException) {
                ""
            }
        }
}