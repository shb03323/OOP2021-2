import tornadofx.*
import javafx.*
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Insets
import java.util.*

class UI : View() {
    override val root = borderpane {
        prefWidth = 800.0
        prefHeight = 600.0

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
                action {  }
            }
            button("Modify")
            button("Delete")
        }
    }
}

private val dates = listOf(
    DailyExercise(Date(2021,12,5), Anaerobic("bench press", 60, 5, 12)),
    DailyExercise(Date(2021,12,6), Anaerobic("bench press", 50, 5, 12))
).asObservable()

class DailyExercise(val date : Date, val exercise: Exercise) {

}

class main_App : App(UI::class)
