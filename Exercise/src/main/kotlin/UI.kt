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

class UI : View("Exercise Recorder") {
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
            button("Add") {
                prefWidth = 100.0
                gridpaneConstraints {
                    columnIndex = 0
                    marginTopBottom(15.0)
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
                    marginTopBottom(15.0)
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
                    marginTopBottom(15.0)
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
                    marginTopBottom(15.0)
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

        gridpane {
            button("Aerobic") {
                prefWidth = 100.0
                gridpaneConstraints {
                    rowIndex = 0
                    marginTopBottom(10.0)
                    marginLeftRight(30.0)
                }
                action {
                    close()
                    find<AerobicFragment>().openModal(stageStyle = StageStyle.UTILITY)
                }
            }
            button("Anaerobic") {
                prefWidth = 100.0
                gridpaneConstraints {
                    rowIndex = 1
                    marginTopBottom(10.0)
                    marginLeftRight(30.0)
                }
                action {
                    close()
                    find<AnaerobicFragment>().openModal(stageStyle = StageStyle.UTILITY)
                }
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
        prefWidth = 300.0

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

        gridpane {
            button("commit") {
                prefWidth = 100.0
                gridpaneConstraints {
                    marginLeftRight(100.0)
                    marginBottom = 10.0
                }
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
}

class AnaerobicFragment: Fragment() {
    private lateinit var date : DatePicker
    private var name : javafx.scene.control.TextField by singleAssign()
    private var weight : javafx.scene.control.TextField by singleAssign()
    private var set : javafx.scene.control.TextField by singleAssign()
    private var rep : javafx.scene.control.TextField by singleAssign()

    override val root = form {
        prefWidth = 300.0
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

        gridpane {
            button("commit") {
                prefWidth = 100.0
                gridpaneConstraints {
                    marginLeftRight(100.0)
                    marginBottom = 10.0
                }
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
}

class ModifyFragment: Fragment() {
    private lateinit var date : DatePicker
    private var name : javafx.scene.control.TextField by singleAssign()

    override val root = form {
        prefWidth = 300.0
        prefHeight = 100.0

        fieldset {
            field("Date") {
                date = datepicker()
            }
            field("Exercise Name") {
                name = textfield()
            }
        }

        gridpane {
            button("Modify") {
                prefWidth = 100.0
                gridpaneConstraints {
                    marginLeftRight(100.0)
                    marginBottom = 10.0
                }
                setOnAction {
                    close()
                    val list = rec.getRecord(date.value.format(DateTimeFormatter.ofPattern("yyyyMMdd")))

                    if(list == null) {
                        find<NoDataFragment>().openModal(stageStyle = StageStyle.UTILITY)
                    }

                    else {
                        var toggle = false
                        for (i in list) {
                            if (i.name == name.text) {
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
                        }
                        if (!toggle) find<NoDataFragment>().openModal(stageStyle = StageStyle.UTILITY)
                    }
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

        gridpane {
            button("commit") {
                prefWidth = 100.0
                gridpaneConstraints {
                    marginLeftRight(75.0)
                }
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

        gridpane {
            button("commit") {
                prefWidth = 100.0
                gridpaneConstraints {
                    marginLeftRight(75.0)
                }
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
}

class DeleteFragment: Fragment() {
    private lateinit var date : DatePicker
    private var name : javafx.scene.control.TextField by singleAssign()

    override val root = form {
        prefWidth = 300.0
        prefHeight = 100.0

        fieldset {
            field("Date") {
                date = datepicker()
            }
            field("Exercise Name") {
                name = textfield()
            }
        }

        gridpane {
            button("delete") {
                prefWidth = 100.0
                gridpaneConstraints {
                    marginLeftRight(100.0)
                    marginBottom = 10.0
                }
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
}

class SearchFragment: Fragment() {
    private lateinit var max : RadioButton
    private lateinit var avg : RadioButton
    private lateinit var exInfo : RadioButton
    private var name : javafx.scene.control.TextField by singleAssign()

    override val root = form {
        prefWidth = 300.0
        prefHeight = 150.0
        val toggleGroup = ToggleGroup()

        fieldset {
            vbox {
                max = radiobutton("Search for Max", toggleGroup) {
                    vboxConstraints {
                        marginTop = 10.0
                    }
                }
                avg = radiobutton("Search for Avg", toggleGroup) {
                    vboxConstraints {
                        marginTopBottom(10.0)
                    }
                }
                exInfo = radiobutton("Search for Exercise Information", toggleGroup) {
                    vboxConstraints {
                        marginBottom = 10.0
                    }
                }
            }

            field("Exercise Name") {
                name = textfield()
            }
        }

        gridpane {
            button("commit") {
                prefWidth = 100.0
                gridpaneConstraints {
                    marginLeftRight(100.0)
                }
                setOnAction {
                    close()
                    if(getList(name.text).isEmpty()) find<NoDataFragment>().openModal(stageStyle = StageStyle.UTILITY)
                    else {
                        if (max.isSelected) {
                            val result = rec.getMax(name.text)
                            searchMax(result)
                        }
                        else if (avg.isSelected) {
                            tempAvg = rec.getAvg(name.text)
                            if (rec.isAerobic(name.text)) AvgAerobicFragment().openWindow()
                            else AvgAnaerobicFragment().openWindow()
                        }
                        else if (exInfo.isSelected) {
                            tempName = name.text
                            if (rec.isAerobic(tempName)) SearchAerobicFragment().openWindow()
                            else SearchAnaerobicFragment().openWindow()
                        }
                        else {
                            find<NoDataFragment>().openModal(stageStyle = StageStyle.UTILITY)
                        }
                    }
                }
            }
        }
    }

    private fun getList(name : String) : MutableList<DailyExercise> {
        var exList : MutableList<DailyExercise> = mutableListOf()
        for ((key, value) in rec.getTotal()) {
            for (i in value) {
                if (i.name == name) {
                    exList.add(DailyExercise(key, i.name))
                }
            }
        }
        return exList
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
            prefWidth = 300.0
            label("Maximum Time is ${tempAerobic.getExTime()} !!") {
                borderpaneConstraints {
                    marginLeftRight(20.0)
                    marginTop = 30.0
                }
            }
        }

        bottom {
            gridpane {
                button("OK") {
                    prefWidth = 50.0
                    gridpaneConstraints {
                        marginLeftRight(125.0)
                        marginTopBottom(20.0)
                    }
                    setOnAction {
                        close()
                        UI().openWindow()
                    }
                }
            }
        }
    }
}

class AnaerobicMaxFragment: Fragment() {
    override val root = borderpane {
        center {
            prefWidth = 300.0
            label("Maximum Weight is ${tempAnaerobic.getExWeight()}kg !!") {
                borderpaneConstraints {
                    marginLeftRight(20.0)
                    marginTop = 30.0
                }
            }
        }

        bottom {
            gridpane {
                button("OK") {
                    prefWidth = 50.0
                    gridpaneConstraints {
                        marginLeftRight(125.0)
                        marginTopBottom(20.0)
                    }
                    setOnAction {
                        close()
                        UI().openWindow()
                    }
                }
            }
        }
    }
}

class AvgAerobicFragment: Fragment() {
    override val root = borderpane {
        center {
            prefWidth = 200.0
            var time = tempAvg.toInt()
            val hour = time / 3600
            time %= 3600
            val min = time / 60
            time %= 60
            val sec = time
            label("Average Time : $hour:$min:$sec") {
                borderpaneConstraints {
                    marginTop = 30.0
                }
            }
        }

        bottom {
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

class AvgAnaerobicFragment: Fragment() {
    override val root = borderpane {
        center {
            prefWidth = 200.0
            label("Average Weight : $tempAvg") {
                borderpaneConstraints {
                    marginTop = 30.0
                }
            }
        }

        bottom {
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

class SearchAerobicFragment: Fragment() {
    override val root = borderpane {
        var list = getList().asObservable()
        center = tableview(list) {
            readonlyColumn("Date", DailyAerobic::date) {
                prefWidth = 100.0
            }
            readonlyColumn("Exercise", DailyAerobic::exName) {
                prefWidth = 150.0
            }
            readonlyColumn("Time", DailyAerobic::exTime) {
                prefWidth = 100.0
            }
        }

        bottom = gridpane {
            button("OK") {
                prefWidth = 50.0
                gridpaneConstraints {
                    marginTopBottom(10.0)
                    marginLeftRight(150.0)
                }
                setOnAction {
                    close()
                    UI().openWindow()
                }
            }
        }
    }

    private fun getList() : MutableList<DailyAerobic> {
        var exList : MutableList<DailyAerobic> = mutableListOf()
        for ((key, value) in rec.getTotal()) {
            for (i in value) {
                if (i.name == tempName) {
                    exList.add(DailyAerobic(key, i.name, (i as Aerobic).getExTime()))
                }
            }
        }
        return exList
    }
}

class SearchAnaerobicFragment: Fragment() {
    override val root = borderpane {
        var list = getList().asObservable()
        center = tableview(list) {
            readonlyColumn("Date", DailyAnaerobic::date) {
                prefWidth = 100.0
            }
            readonlyColumn("Exercise", DailyAnaerobic::exName) {
                prefWidth = 150.0
            }
            readonlyColumn("Weight", DailyAnaerobic::exWeight) {
                prefWidth = 100.0
            }
            readonlyColumn("Set", DailyAnaerobic::exSet) {
                prefWidth = 100.0
            }
            readonlyColumn("Rep", DailyAnaerobic::exRep) {
                prefWidth = 100.0
            }
        }

        bottom = gridpane {
            button("OK") {
                prefWidth = 50.0
                gridpaneConstraints {
                    marginTopBottom(10.0)
                    marginLeftRight(250.0)
                }
                setOnAction {
                    close()
                    UI().openWindow()
                }
            }
        }
    }

    private fun getList() : MutableList<DailyAnaerobic> {
        var exList : MutableList<DailyAnaerobic> = mutableListOf()
        for ((key, value) in rec.getTotal()) {
            for (i in value) {
                if (i.name == tempName) {
                    exList.add(DailyAnaerobic(key, i.name, (i as Anaerobic).getExWeight(), i.getExSet(), i.getExRep()))
                }
            }
        }
        return exList
    }
}

class DailyExercise(val date : String, val exName : String)
class DailyAerobic(val date : String, val exName : String, val exTime : String)
class DailyAnaerobic(val date : String, val exName : String, val exWeight : Int, val exSet : Int, val exRep : Int)

class MainApp : App(UI::class, MyStyle::class)
