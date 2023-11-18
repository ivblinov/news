package com.example.news.ui.news_screen

class DataConverter {
    var year: String = ""
    var month: String = ""
    var day: String = ""
    var hour: String = ""
    var minute: String = ""
    var isMidday: String = ""

    fun generateData() = "${monthMap[month]} $day, $year | $hour:$minute $isMidday"
    fun generateDay() = "$year$month$day"

    companion object {
        val monthMap = mutableMapOf(
            "01" to "Jan",
            "02" to "Feb",
            "03" to "Mar",
            "04" to "Apr",
            "05" to "May",
            "06" to "Jun",
            "07" to "Jul",
            "08" to "Aug",
            "09" to "Sep",
            "10" to "Oct",
            "11" to "Nov"
        )
    }
}