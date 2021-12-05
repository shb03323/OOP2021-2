import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.DatePicker
import javafx.scene.control.RadioButton
import javafx.scene.control.ToggleGroup
import tornadofx.*
import javafx.stage.StageStyle
import java.time.format.DateTimeFormatter

private val rec = Record()
private val io : DataIO = DataIO()
private var dates = rec.getList().asObservable()
private var tempDate : String = ""
private var tempName : String = ""
private var tempAerobic = Aerobic("temp", "0")
private var tempAnaerobic = Anaerobic("temp", 0, 0, 0)
private var tempAvg : Float = 0.0F

class UI : View() {
    override val root = borderpane {
        rec.dailyRecord = io.fileRead()
        dates = rec.getList().asObservable()

        center = tableview(dates) {
            readonlyColumn("Date", DailyExercise::date) {
                prefWidth = 100.0
            }
            readonlyColumn("Exercise", DailyExercise::exName) {
                prefWidth = 700.0
            }
        }

        bottom = gridpane {
            prefHeight = 50.0

            button("Add") {
                prefWidth = 100.0
                gridpaneConstraints {
                    columnIndex = 0
                    marginTop = 15.0
                    marginLeftRight(50.0)
                }

                action {
                    close()
                    find<AddFragment>().openModal(stageStyle = StageStyle.UTILITY)
                }
            }

            button("Modify") {
                prefWidth = 100.0
                gridpaneConstraints {
                    columnIndex = 1
                    marginTop = 15.0
                    marginLeftRight(50.0)
                }

                action {
                    close()
                    find<ModifyFragment>().openModal(stageStyle = StageStyle.UTILITY)
                }
            }

            button("Delete") {
                prefWidth = 100.0
                gridpaneConstraints {
                    columnIndex = 2
                    marginTop = 15.0
                    marginLeftRight(50.0)
                }

                action {
                    close()
                    find<DeleteFragment>().openModal(stageStyle = StageStyle.UTILITY)
                }
            }

            button("Search") {
                prefWidth = 100.0
                gridpaneConstraints {
                    columnIndex = 3
                    marginTop = 15.0
                    marginLeftRight(50.0)
                }

                action {
                    close()
                    find<SearchFragment>().openModal(stageStyle = StageStyle.UTILITY)
                }
            }
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
    private lateinit var date : DatePicker
    private var name : javafx.scene.control.TextField by singleAssign()
    private var hour : javafx.scene.control.TextField by singleAssign()
    private var min : javafx.scene.control.TextField by singleAssign()
    private var sec : javafx.scene.control.TextField by singleAssign()

    override val root = form {
        prefWidth = 250.0
        prefHeight = 100.0

        fieldset {
            field("Date") {
                date = datepicker()
            }

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
                close()
                val exTime = "${hour.text}:${min.text}:${sec.text}"
                val aerobic = Aerobic(name.text, exTime)
                rec.add(date.value.format(DateTimeFormatter.ofPattern("yyyyMMdd")), aerobic)
                io.fileWrite(rec)
                UI().openWindow()
            }
        }
    }
}

class AnaerobicFragment: Fragment() {
    private lateinit var date : DatePicker
    private var name : javafx.scene.control.TextField by singleAssign()
    private var weight : javafx.scene.control.TextField by singleAssign()
    private var set : javafx.scene.control.TextField by singleAssign()
    private var rep : javafx.scene.control.TextField by singleAssign()

    override val root = form {
        prefWidth = 250.0
        prefHeight = 200.0

        fieldset {
            field("Date") {
                date = datepicker()
            }

            field("Exercise Name") {
                name = textfield()
            }

            field("Weight") {
                weight = textfield()
            }

            field("Set") {
                set = textfield()
            }

            field("Rep") {
                rep = textfield()
            }
        }

        button("commit") {
            setOnAction {
                close()
                val anAerobic = Anaerobic(name.text, weight.text.toInt(), set.text.toInt(), rep.text.toInt())
                rec.add(date.value.format(DateTimeFormatter.ofPattern("yyyyMMdd")), anAerobic)
                io.fileWrite(rec)
                UI().openWindow()
            }
        }
    }
}

class ModifyFragment: Fragment() {
    private lateinit var date : DatePicker
    private var name : javafx.scene.control.TextField by singleAssign()

    override val root = form {
        prefWidth = 200.0
        prefHeight = 100.0

        fieldset {
            field("Date") {
                date = datepicker()
            }
            field("Exercise Name") {
                name = textfield()
            }
        }

        button("Modify") {
            setOnAction {
                close()
                val list = rec.getRecord(date.value.format(DateTimeFormatter.ofPattern("yyyyMMdd")))

                if(list == null) {
                    find<NoDataFragment>().openModal(stageStyle = StageStyle.UTILITY)
                }

                else {
                    var toggle = false
                    for (i in list) {
                        if (i is Aerobic) {
                            tempDate = date.value.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                            tempName = name.text
                            rec.delete(tempDate, tempName)
                            find<ModifyAerobicFragment>().openModal(stageStyle = StageStyle.UTILITY)
                            toggle = true
                            break
                        }
                        else {
                            tempDate = date.value.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                            tempName = name.text
                            rec.delete(tempDate, tempName)
                            find<ModifyAnaerobicFragment>().openModal(stageStyle = StageStyle.UTILITY)
                            toggle = true
                            break
                        }
                    }
                    if (!toggle) find<NoDataFragment>().openModal(stageStyle = StageStyle.UTILITY)
                }
            }
        }
    }
}

class ModifyAerobicFragment: Fragment() {
    private var hour : javafx.scene.control.TextField by singleAssign()
    private var min : javafx.scene.control.TextField by singleAssign()
    private var sec : javafx.scene.control.TextField by singleAssign()

    override val root = form {
        prefWidth = 250.0
        prefHeight = 100.0

        fieldset {
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
                close()
                val exTime = "${hour.text}:${min.text}:${sec.text}"
                val aerobic = Aerobic(tempName, exTime)
                rec.add(tempDate, aerobic)
                io.fileWrite(rec)
                UI().openWindow()
            }
        }
    }
}

class ModifyAnaerobicFragment: Fragment() {
    private var weight : javafx.scene.control.TextField by singleAssign()
    private var set : javafx.scene.control.TextField by singleAssign()
    private var rep : javafx.scene.control.TextField by singleAssign()

    override val root = form {
        prefWidth = 250.0
        prefHeight = 200.0

        fieldset {
            field("Weight") {
                weight = textfield()
            }

            field("Set") {
                set = textfield()
            }

            field("Rep") {
                rep = textfield()
            }
        }

        button("commit") {
            setOnAction {
                close()
                val anAerobic = Anaerobic(tempName, weight.text.toInt(), set.text.toInt(), rep.text.toInt())
                rec.add(tempDate, anAerobic)
                io.fileWrite(rec)
                UI().openWindow()
            }
        }
    }
}

class DeleteFragment: Fragment() {
    private lateinit var date : DatePicker
    private var name : javafx.scene.control.TextField by singleAssign()

    override val root = form {
        prefWidth = 200.0
        prefHeight = 100.0

        fieldset {
            field("Date") {
                date = datepicker()
            }
            field("Exercise Name") {
                name = textfield()
            }
        }

        button("delete") {
            setOnAction {
                close()
                val list = rec.getRecord(date.value.format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                if (list == null) {
                    find<NoDataFragment>().openModal(stageStyle = StageStyle.UTILITY)
                }
                else {
                    var toggle = false
                    for (i in list) {
                        if (i.name == name.text) {
                            rec.delete(date.value.format(DateTimeFormatter.ofPattern("yyyyMMdd")), name.text)
                            io.fileWrite(rec)
                            toggle = true
                            break
                        }
                    }
                    if (!toggle) find<NoDataFragment>().openModal(stageStyle = StageStyle.UTILITY)
                    else UI().openWindow()
                }
            }
        }
    }
}

class SearchFragment: Fragment() {
    private lateinit var max : RadioButton
    private lateinit var avg : RadioButton
    private var name : javafx.scene.control.TextField by singleAssign()

    override val root = form {
        prefWidth = 250.0
        prefHeight = 200.0
        val toggleGroup = ToggleGroup()

        fieldset {
            vbox {
                max = radiobutton("Search for Max", toggleGroup)
                avg = radiobutton("Search for Avg", toggleGroup)
            }

            field("Exercise Name") {
                name = textfield()
            }
        }

        button("commit") {
            setOnAction {
                close()
                if (max.isSelected) {
                    val result = rec.getMax(name.text)
                    searchMax(result)
                }
                else if (avg.isSelected) {
                    tempAvg = rec.getAvg(name.text)
                    find<AvgFragment>().openModal(stageStyle = StageStyle.UTILITY)
                }
                else {
                    find<NoDataFragment>().openModal(stageStyle = StageStyle.UTILITY)
                }
            }
        }
    }

    private fun searchMax(exercise: Exercise) {
        if(exercise is Aerobic) {
            tempAerobic = exercise
            find<AerobicMaxFragment>().openModal(stageStyle = StageStyle.UTILITY)
        }
        else {
            tempAnaerobic = exercise as Anaerobic
            find<AnaerobicMaxFragment>().openModal(stageStyle = StageStyle.UTILITY)
        }
    }
}

class NoDataFragment: Fragment() {
    override val root = borderpane {
        center {
            style {
                fontSize = 15.px
            }
            label("Wrong Input!!") {
                borderpaneConstraints {
                    marginTop = 30.0
                }
            }
        }

        bottom {
            prefHeight = 30.0
            button("OK") {
                prefWidth = 50.0
                borderpaneConstraints {
                    marginTopBottom(30.0)
                    marginLeftRight(75.0)
                }

                setOnAction {
                    close()
                    UI().openWindow()
                }
            }
        }
    }
}

class AerobicMaxFragment: Fragment() {
    override val root = borderpane {
        center {
            style {
                fontSize = 15.px
            }
            label("Maximum Time is ${tempAerobic.getExTime()} !!") {
                borderpaneConstraints {
                    marginTop = 30.0
                }
            }
        }

        bottom {
            prefHeight = 30.0
            button("OK") {
                prefWidth = 50.0
                borderpaneConstraints {
                    marginTopBottom(30.0)
                    marginLeftRight(75.0)
                }

                setOnAction {
                    close()
                    UI().openWindow()
                }
            }
        }
    }
}

class AnaerobicMaxFragment: Fragment() {
    override val root = borderpane {
        center {
            style {
                fontSize = 15.px
            }
            label("Maximum Weight is ${tempAnaerobic.getExWeight()} !!") {
                borderpaneConstraints {
                    marginTop = 30.0
                }
            }
        }

        bottom {
            prefHeight = 30.0
            button("OK") {
                prefWidth = 50.0
                borderpaneConstraints {
                    marginTopBottom(30.0)
                    marginLeftRight(75.0)
                }

                setOnAction {
                    close()
                    UI().openWindow()
                }
            }
        }
    }
}

class AvgFragment: Fragment() {
    override val root = borderpane {
        center {
            style {
                fontSize = 15.px
            }
            label("Average Time or Weight : ${tempAvg.toString()}") {
                borderpaneConstraints {
                    marginTop = 30.0
                }
            }
        }

        bottom {
            prefHeight = 30.0
            button("OK") {
                borderpaneConstraints {
                    marginTopBottom(30.0)
                    marginLeftRight(75.0)
                }

                setOnAction {
                    close()
                    UI().openWindow()
                }
            }
        }
    }
}

class DailyExercise(val date : String, val exName: String)

class main_App : App(UI::class)
