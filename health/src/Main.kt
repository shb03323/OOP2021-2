fun main() {
    var recording = Record()
    val benchPress1 = Anaerobic("bench press", 60, 5, 12)
    val benchPress2 = Anaerobic("bench press", 65, 5, 12)
    val benchPress3 = Anaerobic("bench press", 50, 5, 12)
    val benchPress4 = Anaerobic("bench press", 55, 5, 12)
    recording.record(1, benchPress1)
    recording.record(1, benchPress2)
    recording.record(2, benchPress3)
    recording.record(4, benchPress4)

    val cycle1 = Aerobic("cycle", "1:0:0")
    val cycle2 = Aerobic("cycle", "0:30:00")
    recording.record(1, cycle1)
    recording.record(3, cycle2)

    println(recording.getMax("bench press").getData()["exWeight"])
    println(recording.getMax("cycle").getData()["exTime"])
    println(recording.getAvg("bench press"))
    println(recording.getAvg("cycle"))
    println(recording.getRecord(1))
}