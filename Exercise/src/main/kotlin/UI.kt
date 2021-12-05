import javafx.scene.Parent
import tornadofx.*
import javafx.stage.StageStyle
import java.awt.TextField
import java.util.*

class UI : View() {
    override val root = borderpane {
        prefWidth = 800.0
        prefHeight = 600.0
        rec.dailyRecord = io.fileRead()

        top {
            menubar {
                menu("Edit") {
                    item("Add")
                    item("Modify")
                    item("Delete")
                }
                menu("Search") {
                    item("Exercise")
                    item("Average")
                    item("Max condition")
                }
            }
        }

        center = tableview(dates) {
            readonlyColumn("Date", DailyExercise::date)
            readonlyColumn("Exercise", DailyExercise::exercise)
        }

        bottom = hbox {
            prefHeight = 50.0
            button("Add") {
                prefWidth = 100.0
                action {
                    find<AddFragment>().openModal(stageStyle = StageStyle.UTILITY)
                }
            }
            button("Modify")
            button("Delete")
        }
    }
}

class AddFragment: Fragment() {
    override val root = form {
        prefWidth = 200.0
        prefHeight = 100.0

        button("Aerobic") {
            action {
                close()
                find<AerobicFragment>().openModal(stageStyle = StageStyle.UTILITY)
            }
        }
        button("Anaerobic") {
            action {
                close()
                find<AnaerobicFragment>().openModal(stageStyle = StageStyle.UTILITY)
            }
        }
    }
}

class AerobicFragment: Fragment() {
    private var name : javafx.scene.control.TextField by singleAssign()
    private var hour : javafx.scene.control.TextField by singleAssign()
    private var min : javafx.scene.control.TextField by singleAssign()
    private var sec : javafx.scene.control.TextField by singleAssign()

    override val root = form {
        prefWidth = 250.0
        prefHeight = 100.0

        fieldset {
            field("Exercise Name") {
                name = textfield()
            }

            hbox {
                field("Time") {
                    hour =textfield()
                    label(":")
                    min = textfield()
                    label(":")
                    sec = textfield()
                }
            }
        }

        button("commit") {
            setOnAction {
                println(name.text)
            }
        }
    }
}

class AnaerobicFragment: Fragment() {
    override val root: Parent
        get() = TODO("Not yet implemented")
}

private val dates = listOf(
    DailyExercise("2020", Anaerobic("bench press", 60, 5, 12)),
    DailyExercise("2021", Anaerobic("bench press", 50, 5, 12))
).asObservable()

class DailyExercise(val date : String, val exercise: Exercise) {

}

private val rec = Record()
private val io : DataIO = DataIO()

class main_App : App(UI::class)
