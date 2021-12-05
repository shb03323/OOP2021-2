fun main() {
    // For Test
    val io : DataIO = DataIO()
    io.fileRead()
    io.fileWrite(Aerobic("Jogging", "120"))
    io.fileWrite(Anaerobic("Squat", 100, 3, 10))
}