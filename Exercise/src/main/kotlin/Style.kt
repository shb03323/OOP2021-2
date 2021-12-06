import javafx.scene.text.FontWeight
import tornadofx.*

class MyStyle: Stylesheet() {
    init {
        button {
            fontFamily = "Malgun Gothic"
            fontWeight = FontWeight.BOLD
            fontSize = 15.px
        }
        tableColumn {
            fontFamily = "Malgun Gothic"
            fontWeight = FontWeight.BOLD
            fontSize = 20.px
        }
        tableCell {
            fontFamily = "Malgun Gothic"
            fontWeight = FontWeight.LIGHT
            fontSize = 15.px
        }
        fieldset {
            fontFamily = "Malgun Gothic"
            fontWeight = FontWeight.BOLD
            fontSize = 15.px
        }
        textField {
            fontFamily = "Malgun Gothic"
            fontWeight = FontWeight.LIGHT
            fontSize = 15.px
        }
        label {
            fontFamily = "Malgun Gothic"
            fontWeight = FontWeight.BOLD
            fontSize = 15.px
        }
    }
}