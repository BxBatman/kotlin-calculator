package com.example.demo.app

import com.example.demo.app.evaluator.Evaluator
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.Stage
import tornadofx.*

class CalculatorApp : App() {
    override val primaryView = Calculator::class

    override fun start(stage: Stage) {
        importStylesheet("/style.css")
        stage.isResizable = false;
        super.start(stage)
    }
}

class Calculator : View() {
    override val root: VBox by fxml("/Calculator.fxml")
    @FXML lateinit var display: Label
    @FXML lateinit var displayExpression: Label
    @FXML lateinit var oneButton: Button
    @FXML lateinit var twoButton: Button
    @FXML lateinit var threeButton: Button
    @FXML lateinit var fourButton: Button
    @FXML lateinit var fiveButton: Button
    @FXML lateinit var sixButton: Button
    @FXML lateinit var sevenButton: Button
    @FXML lateinit var eightButton: Button
    @FXML lateinit var nineButton: Button
    @FXML lateinit var zeroButton: Button
    @FXML lateinit var mul: Button
    @FXML lateinit var div: Button
    @FXML lateinit var sub: Button
    @FXML lateinit var add: Button
    @FXML lateinit var equal: Button
    @FXML lateinit var dot: Button
    @FXML lateinit var clear: Button

    var isDot: Boolean = false


    init {
        title = "CalculatorApp"
        oneButton.setOnMouseClicked { appendOnExpression("1") }
        twoButton.setOnMouseClicked { appendOnExpression("2") }
        threeButton.setOnMouseClicked { appendOnExpression("3") }
        fourButton.setOnMouseClicked { appendOnExpression("4") }
        fiveButton.setOnMouseClicked { appendOnExpression("5") }
        sixButton.setOnMouseClicked { appendOnExpression("6") }
        sevenButton.setOnMouseClicked { appendOnExpression("7") }
        eightButton.setOnMouseClicked { appendOnExpression("8") }
        nineButton.setOnMouseClicked { appendOnExpression("9") }
        zeroButton.setOnMouseClicked { appendOnExpression("0") }
        add.setOnMouseClicked { appendOnExpression("+"); isDot=false }
        div.setOnMouseClicked { appendOnExpression("/"); isDot=false }
        sub.setOnMouseClicked { appendOnExpression("-"); isDot=false }
        mul.setOnMouseClicked { appendOnExpression("*"); isDot=false }
        clear.setOnMouseClicked { displayExpression.text = ""; display.text = ""; isDot=false; disableOperatorButtons() }
        dot.setOnMouseClicked {
            if (isDot ==false) {
            appendOnExpression(".")
                isDot = true
            }
        }

        disableOperatorButtons()

        equal.setOnMouseClicked {
            if (displayExpression.text.equals("")) {
                displayExpression.text ="0"
            }

            if(!(displayExpression.text.last().toString() in arrayOf("+","-","*","/","."))) {
                val evaluator = Evaluator()
                val result = evaluator.evaluate(displayExpression.text)
                display.text = result.toString()
            }
        }
    }

    fun disableOperatorButtons() {
        add.setDisable(true)
        div.setDisable(true)
        mul.setDisable(true)
        sub.setDisable(true)
        dot.setDisable(true)
    }


    fun enableOperatorButtons() {
        add.setDisable(false)
        div.setDisable(false)
        mul.setDisable(false)
        sub.setDisable(false)
        dot.setDisable(false)
    }

    fun appendOnExpression(string: String) {
        if(checkIfOperatorOrDotLast(string)) {
            return
        }
        checkIfOperator(string)
        display.text = ""
        displayExpression.text += string
        enableOperatorButtons()
    }


    fun checkIfOperator(string: String) {
        if ( !display.text.equals("") && (string.equals("+") || string.equals("-") || string.equals("/") || string.equals("*")) ) {
            displayExpression.text = display.text
        }

    }



    fun checkIfOperatorOrDotLast(string: String): Boolean {
        if (string.equals("+") || string.equals("-") || string.equals("/") || string.equals("*") || string.equals(".")) {
            if(displayExpression.text.last().toString() in arrayOf("+","-","*","/",".")) {
                return true
            }
        }
        return false
    }


}

