fun main() {
    // For Test
    val rec : Record = Record()
    val io : DataIO = DataIO()
    rec.dailyRecord = io.fileRead()
    println(rec.getAvg("squat"))
    io.fileWrite(Aerobic("Jogging", "120"))
    io.fileWrite(Anaerobic("Squat", 100, 3, 10))
}