import java.io.*
import java.nio.file.*
import java.text.SimpleDateFormat
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
                            record[cal] = mutableListOf(Aerobic(parts[1].lowercase(Locale.getDefault()), parts[2]))
                        // Existing in list
                        else {
                            val list: MutableList<Exercise>? = record[cal]
                            list?.add(Aerobic(parts[1], parts[2]))
                            record[cal] = list!!
                        }
                    }
                    5 -> {  // For Anaerobic
                        // Not existing in list
                        if (record[cal] == null)
                            record[cal] = mutableListOf(Anaerobic(parts[1].lowercase(Locale.getDefault()), parts[2].toInt(), parts[3].toInt(), parts[4].toInt()))
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
        // Format of Today Date
        val format1 = SimpleDateFormat("yyyyMMdd", Locale.KOREA)
        val todayDate = format1.format(Date().time)
        // File Write Format for Aerobic Exercise
        if (exercise is Aerobic){
            strBuilder.append(todayDate)
            strBuilder.append('|')
            strBuilder.append(exercise.getExName())
            strBuilder.append('|')
            strBuilder.append(exercise.getExTime())
            strBuilder.append("\n")
        }
        // File Write Format for Anaerobic Exercise
        else {
            exercise as Anaerobic
            strBuilder.append(todayDate)
            strBuilder.append('|')
            strBuilder.append(exercise.getExName())
            strBuilder.append('|')
            strBuilder.append(exercise.getExWeight())
            strBuilder.append('|')
            strBuilder.append(exercise.getExSet())
            strBuilder.append('|')
            strBuilder.append(exercise.getExRep())
            strBuilder.append("\n")
        }
        try{
            // File write
            Files.write(Paths.get(path), strBuilder.toString().toByteArray(), StandardOpenOption.APPEND)
            println("write completed $strBuilder")
        }catch (e: Exception){
            println(e.message)
        }
    }
}