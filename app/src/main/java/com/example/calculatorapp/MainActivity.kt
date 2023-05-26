 package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ConditionVariable
import android.view.View
import android.widget.Button
import android.widget.TextView

 class MainActivity : AppCompatActivity() {

     var tvInput: TextView? = null
     var lastNumeric: Boolean = false
     var lastDot: Boolean = false
     var lastEqual: Boolean = false
     var lastMinus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        if(lastEqual){
            tvInput?.text = ""
            lastEqual = false
        }
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastMinus = false
    }

    fun onClear(view: View){
        tvInput?.text = ""
        lastDot = false
        lastNumeric = false
        lastMinus = false
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric = false; lastDot = true
        }
    }

     fun onMinus(view: View){
         tvInput?.text?.let {
             if(!isMinusAdded(it.toString()) && !lastMinus){
                 tvInput?.append((view as Button).text)
                 lastNumeric =false; lastDot = false; lastEqual = false; lastMinus = true
             }
         }
     }

     fun onOperator(view: View){
         tvInput?.text?.let {
             if(lastNumeric && !isOperatorAdded(it.toString()) && !isMinusAdded(it.toString())){
                 tvInput?.append((view as Button).text)
                 lastNumeric =false; lastDot = false; lastEqual = false; lastMinus = false
             }
         }

     }
    private fun isOperatorAdded(value: String): Boolean{
        return value.contains("%") || value.contains("X") || value.contains("+")
    }
     private fun isMinusAdded(value: String): Boolean{
         return if(value.startsWith("-") && !value.substring(1).contains("-")){
             false
         }
         else{
             value.contains("-")
         }
     }

     fun onEqual(view: View){
         if(lastNumeric){
             lastEqual = true;lastNumeric = true
             val tvValue = tvInput?.text.toString()
             var result: Double = 0.0
             val parts: List<String>
             if(tvValue.contains("-") && !tvValue.contains("+") && !tvValue.contains("%") && !tvValue.contains("X")){
                 if(tvValue.startsWith("-")){
                    parts = tvValue.substring(1).split("-")
                     result = parts[0].toDouble()*(-1) - parts[1].toDouble()
                 } else{
                     parts = tvValue.split("-")
                     result = parts[0].toDouble() - parts[1].toDouble()
                 }
             }
             else if(tvValue.contains("+")){
                 if(tvValue.startsWith("-")){
                     parts = tvValue.substring(1).split("+")
                     result = parts[0].toDouble()*(-1) + parts[1].toDouble()
                 } else{
                     parts = tvValue.split("+")
                     result = parts[0].toDouble() + parts[1].toDouble()
                 }
             }
             else if(tvValue.contains("X")){
                 if(tvValue.startsWith("-")){
                     parts = tvValue.substring(1).split("X")
                     result = (parts[0].toDouble()*(-1)) * parts[1].toDouble()
                 } else{
                     parts = tvValue.split("X")
                     result = parts[0].toDouble() * parts[1].toDouble()
                 }
             }
             else if(tvValue.contains("%")){
                 if(tvValue.startsWith("-")){
                     parts = tvValue.substring(1).split("%")
                     result = (parts[0].toDouble()*(-1)) / parts[1].toDouble()
                 } else{
                     parts = tvValue.split("%")
                     result = parts[0].toDouble() / parts[1].toDouble()
                 }
             }
             val resultAsString = result.toString()

             if(resultAsString.substring(resultAsString.length-2) == ".0"){
                 val integer = resultAsString.substring(0,resultAsString.length-2)
                 tvInput?.text = ""
                 tvInput?.append(integer)
             }
             else{
                 tvInput?.text = ""
                 tvInput?.append(resultAsString)
             }
         }
     }
}