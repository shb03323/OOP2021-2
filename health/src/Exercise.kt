import java.util.*

// parent class
abstract class Exercise {
    lateinit var name : String

    // get data of Exercise
    abstract fun getData() : Map<String, Any>
    // get name of Exercise
    fun getExName() : String { return name }
}

// Aerobic Exercise : inheritance of Exercise class
class Aerobic : Exercise {
    private var exTime : String

    // use name, exercise time property
    constructor(_name : String, _exTime : String) {
        name = _name
        exTime = _exTime
    }

    // get data of Aerobic Exercise by Map collection
    override fun getData(): Map<String, Any> {
        val timeOfSec = timeToSec(exTime)  // make time string to integer
        return mapOf<String, Any>("name" to name, "exTime" to timeOfSec)
    }

    // get Exercise Time
    fun getExTime() : String { return exTime }

    private fun timeToSec(exTime: String) : Int {
        val splitTime = exTime.split(":")  // create input format
        var result : Int = 0  // add hour * 3600, min * 60, sec * 1 in result
        var divider : Int = 3600  // to get result time, we used divider to divide time format

        // wrong input format
        if(splitTime.size != 3) {
            println("Wrong input format!")
            return 0
        }

        // make time format of second
        for(i in splitTime) {
            result += i.toInt() * divider
            divider /= 60
        }

        return result
    }
}

// AnAerobic Exercise : inheritance of Exercise class
class Anaerobic : Exercise {
    private var exWeight : Int
    private var exSet : Int
    private var exRep : Int

    // use name, weight, set, reps property
    constructor(_name : String, _exWeight : Int, _exSet : Int, _exRep : Int) {
        name = _name
        exWeight = _exWeight
        exSet = _exSet
        exRep = _exRep
    }
    // get weight of Exercise
    fun getExWeight() : Int { return exWeight}
    // get set of Exercise
    fun getExSet() : Int { return exSet}
    // get repetition of Exercise
    fun getExRep() : Int { return exRep}
    // get data of AnAerobic Exercise by Map collection
    override fun getData() : Map<String, Any> {
        return mapOf<String, Any>("name" to name, "exWeight" to exWeight, "exSet" to exSet, "exRep" to exRep)
    }
}
