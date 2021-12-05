import java.lang.Math.*
import java.util.*
import kotlin.collections.HashMap

class Record {
    var dailyRecord : HashMap<String, List<Exercise>> = hashMapOf()
    init {
        this.dailyRecord = dailyRecord
    }

    // method to add the exercise
    fun add(date: String, exercise: Exercise) {
        if (!dailyRecord.contains(date)) {
            dailyRecord[date] = listOf(exercise)
        }
        else {
            var tempList: List<Exercise> = dailyRecord[date]!!
            tempList = tempList.plus(exercise)
            dailyRecord.replace(date, tempList)
        }
    }

    // method for user's max weight or time exercise
    fun getMax(name: String): Exercise {
        val exerciseList = getExerciseList(name)
        var resultExercise: Exercise = exerciseList[0]

        // for anaerobic
        if (exerciseList[0] is Anaerobic) {
            for (i in exerciseList) {
                val max = resultExercise.getData()["exWeight"] as Int
                val temp = max.coerceAtLeast(i.getData()["exWeight"] as Int)

                if (max != temp) {
                    resultExercise = i
                }
            }
        }

        // for aerobic
        else {
            for (i in exerciseList) {
                val max = resultExercise.getData()["exTime"] as Int
                val temp = max.coerceAtLeast(i.getData()["exTime"] as Int)

                if (max != temp) {
                    resultExercise = i
                }
            }
        }

        return resultExercise
    }

    // method for user's average exercise intensity
    fun getAvg(name: String): Float {
        val exerciseList = getExerciseList(name)
        var sum: Int = 0

        // for anaerobic
        return if (exerciseList[0] is Anaerobic) {
            for (i in exerciseList) {
                sum += i.getData()["exWeight"] as Int
            }
            sum.toFloat() / exerciseList.size
        }

        // for aerobic
        else {
            for (i in exerciseList) {
                sum += i.getData()["exTime"] as Int
            }
            sum.toFloat() / exerciseList.size
        }
    }

    // get total Record
    fun getTotal() : HashMap<String, List<Exercise>> {
        return dailyRecord
    }
    // method to view user's exercise record
    // when you input in parameter the date, you can see the exercise record of that day
    fun getRecord(date: String) : List<Exercise>? = dailyRecord[date]

    // when you input exercise name, you can get the list of record of exercise
    private fun getExerciseList(name: String): List<Exercise> {
        var tempList: List<Exercise> = listOf()  // storage of exercise list

        // when this loop find the exercise what you input, add that exercise into tempList
        return run {
            for ((key, value) in dailyRecord) {
                for (i in value) {
                    if (i.name == name) {
                        tempList = tempList.plus(i)
                    }
                }
            }
            tempList
        }
    }
}