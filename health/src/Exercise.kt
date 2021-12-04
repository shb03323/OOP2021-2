open class Exercise {
    var name : String = ""

    //fun getData() : Exercise
}

class Aerobic : Exercise {
    private var time : Int

    constructor(_name : String, _time : Int) {
        name = _name
        time = _time
    }

    //override fun getData()
}

class Anaerobic : Exercise {
    private var weight : Int = 0
    private var exerciseSet : Int = 0
    private var rep : Int = 0

    constructor(_name : String, _weight : Int, _exerciseSet : Int, _rep : Int) {
        name = _name
        weight = _weight
        exerciseSet = _exerciseSet
        rep = _rep
    }
}

