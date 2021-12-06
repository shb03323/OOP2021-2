import java.io.*
import java.util.*
import kotlin.collections.HashMap

class DataIO {
    fun fileRead() : HashMap<String, List<Exercise>>{
        // Return Map Type
        var record = hashMapOf<String, List<Exercise>>()
        // Test File Path
        val path = "./exercise.txt"
        try{
            val inputStream : InputStream = File(path).inputStream()
            val listOfLines = mutableListOf<String>()
            // Add each line to list
            inputStream.reader().useLines { lines ->
                lines.forEach{
                    listOfLines.add(it)
                }
            }
            for (line in listOfLines){
                // Split each line
                val parts = line.split('|')
                // Length of parsed line
                val length = parts.size
                // Date String
                var date = parts[0]

                when (length) {
                    3-> {   // For Aerobic
                        // Not existing in list
                        if (!record.contains(date))
                            record[date] = listOf(Aerobic(parts[1].lowercase(Locale.getDefault()), parts[2]))
                        // Existing in list
                        else {
                            var list: List<Exercise>? = record[date]
                            list = list?.plus(Aerobic(parts[1], parts[2]))
                            record[date] = list!!
                        }
                    }
                    5 -> {  // For Anaerobic
                        // Not existing in list
                        if (!record.contains(date))
                            record[date] = listOf(Anaerobic(parts[1].lowercase(Locale.getDefault()), parts[2].toInt(), parts[3].toInt(), parts[4].toInt()))
                        // Existing in list
                        else {
                            var list: List<Exercise>? = record[date]
                            list = list?.plus(Anaerobic(parts[1], parts[2].toInt(), parts[3].toInt(), parts[4].toInt()))
                            record[date] = list!!
                        }
                    }
                    else -> {
                        println("Type Error. Cannot be Inserted")
                    }
                }
            }
            inputStream.close()
        }catch (e: Exception){
            println(e.message)
        }
        return record
    }

    fun fileWrite(record: Record){
        val path = "./exercise.txt"

        val rec = record.dailyRecord
        val keys = rec.keys
        val strBuilder: StringBuilder = StringBuilder()
        for (key in keys){
            val list = rec[key]
            if (list != null) {
                for (ex in list){
                    strBuilder.append(key)
                    strBuilder.append('|')
                    if (ex is Aerobic){
                        strBuilder.append(ex.getExName())
                        strBuilder.append('|')
                        strBuilder.append(ex.getExTime())
                        strBuilder.append("\n")
                    } else{
                        ex as Anaerobic
                        strBuilder.append(ex.getExName())
                        strBuilder.append('|')
                        strBuilder.append(ex.getExWeight())
                        strBuilder.append('|')
                        strBuilder.append(ex.getExSet())
                        strBuilder.append('|')
                        strBuilder.append(ex.getExRep())
                        strBuilder.append("\n")
                    }
                }
            }
        }
        try{
            // File write
            File(path).delete()
            FileWriter(path, true).use{it.write(strBuilder.toString())}
        }catch (e: Exception){
            println(e.message)
        }

        /* val strBuilder = StringBuilder()
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
        }catch (e: Exception){
            println(e.message)
        } */
    }
}