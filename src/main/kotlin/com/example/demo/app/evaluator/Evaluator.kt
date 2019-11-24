package com.example.demo.app.evaluator

class Evaluator {
    public fun evaluate(expression: String): Double? {
        for (operator in Operators.values()) {
            var position = expression.reversed().lastIndexOf(operator.sign)
            if (position > 0 && position < expression.length) {
                val partialExpressions = expression.split(position)
                val left = partialExpressions[0]
                val right = partialExpressions[1]

                val value0 = evaluate(left)
                val value1 = evaluate(right)

                val res = when (operator) {
                    Operators.PLUS -> value0?.plus(value1!!)
                    Operators.MINUS -> value0?.minus(value1!!)
                    Operators.DIVISION -> {
                        if (value1 == 0.0)
                            throw ArithmeticException("Divide By Zero")
                        value0?.div(value1!!)
                    }
                    Operators.MULTIPLY -> value0?.times(value1!!)
                }
                return res
            }
        }
        return expression.toDoubleOrNull()
    }

    private fun String.lastIndexOf(char: Char): Int {
        val i = this.indexOf(char)
        return this.length -i -1
    }

    private fun String.split(position: Int) = listOf(this.substring(0, position), this.substring(position + 1, this.length))
}