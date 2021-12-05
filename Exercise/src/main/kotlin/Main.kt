import tornadofx.*

fun main() {
    var recording = Record()
    val benchPress1 = Anaerobic("bench press", 60, 5, 12)
    val benchPress2 = Anaerobic("bench press", 65, 5, 12)
    val benchPress3 = Anaerobic("bench press", 50, 5, 12)
    val benchPress4 = Anaerobic("bench press", 55, 5, 12)
    recording.add(1, benchPress1)
    recording.add(1, benchPress2)
    recording.add(2, benchPress3)
    recording.add(4, benchPress4)

    val cycle1 = Aerobic("cycle", "1:0:0")
    val cycle2 = Aerobic("cycle", "0:30:00")
    recording.add(1, cycle1)
    recording.add(3, cycle2)

    println(recording.getMax("bench press").getData()["exWeight"])
    println(recording.getMax("cycle").getData()["exTime"])
    println(recording.getAvg("bench press"))
    println(recording.getAvg("cycle"))

    launch<main_App>()
}