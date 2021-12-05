import tornadofx.*
import java.text.SimpleDateFormat

fun main() {
    // For Test
    val rec : Record = Record()
    val io : DataIO = DataIO()
    rec.dailyRecord = io.fileRead()
    rec.add("20211205", Anaerobic("squat", 60, 3, 20))
    rec.add("20211205", Anaerobic("bench", 50, 4, 10))
    io.fileWrite(rec)
    println(rec.getAvg("squat"))
    launch<main_App>()
}
