import java.io.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class DataIO {
    fun fileRead() : Map<Calendar, MutableList<Exercise>>{
        // Return Map
        var record = mutableMapOf<Calendar, MutableList<Exercise>>()
        // Calendar Instance
        val cal = Calendar.getInstance()
        // Test File Path
        val path = "C:\\test\\exercise.txt"
        try{
            val listOfLines = mutableListOf<String>()
            // Add each line to list
            File(path).bufferedReader().useLines { lines ->
                lines.forEach{
                    listOfLines.add(it)
                }
            }
            for (line in listOfLines){
                // Split each line
                val parts = line.split('|')
                // Length of parsed line
                val length = parts.size
                val date = parts[0].toInt()

                // Set Year, Month, DAY
                cal.add(Calendar.YEAR, date/10000)
                cal.add(Calendar.MONTH, date/100)
                cal.add(Calendar.DAY_OF_MONTH, date%100)
                when (length) {
                    3-> {   // For Aerobic
                        // Not existing in list
                        if (record[cal] == null)
                            record[cal] = mutableListOf(Aerobic(parts[1], parts[2].toInt()))
                        // Existing in list
                        else {
                            val list: MutableList<Exercise>? = record[cal]
                            list?.add(Aerobic(parts[1], parts[2].toInt()))
                            record[cal] = list!!
                        }
                    }
                    5 -> {  // For Anaerobic
                        // Not existing in list
                        if (record[cal] == null)
                            record[cal] = mutableListOf(Anaerobic(parts[1], parts[2].toInt(), parts[3].toInt(), parts[4].toInt()))
                        // Existing in list
                        else {
                            val list: MutableList<Exercise>? = record[cal]
                            list?.add(Anaerobic(parts[1], parts[2].toInt(), parts[3].toInt(), parts[4].toInt()))
                            record[cal] = list!!
                        }
                    }
                    else -> {
                        println("Type Error. Cannot be Inserted")
                    }
                }
            }
        }catch (e: Exception){
            println(e.message)
        }
        return record
    }

    fun fileWrite(exercise:Exercise){
        val path = "C:\\test\\exercise.txt"

        val strBuilder = StringBuilder()
        val format1 = SimpleDateFormat("yyyyMMdd")
        val todayDate = format1.format(LocalDate.now())
        if (exercise is Aerobic){
            strBuilder.append(todayDate)
            strBuilder.append('|')
            strBuilder.append(exercise.name)
            strBuilder.append('|')
            strBuilder.append(exercise.getTime())

        }
        else {
            exercise as Anaerobic
            strBuilder.append(todayDate)
            strBuilder.append('|')
            strBuilder.append(exercise.name)
            strBuilder.append('|')
            strBuilder.append(exercise.getWeight())
            strBuilder.append('|')
            strBuilder.append(exercise.getSet())
            strBuilder.append('|')
            strBuilder.append(exercise.getRep())
        }
        try{
            File(path).bufferedWriter().write(strBuilder.toString())
        }catch (e: Exception){
            println(e.message)
        }
    }
}