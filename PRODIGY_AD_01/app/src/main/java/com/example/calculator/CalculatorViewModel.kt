package com.example.calculator

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel  : ViewModel(){

    private val _equationText = mutableStateOf("")
    val equationText = _equationText

    private val _resultText = mutableStateOf("0")
    val resultText = _resultText

    fun onButtonClick(btn : String){
        _equationText.value?.let {
            if(btn=="AC"){
                _equationText.value = ""
                _resultText.value = "0"
                return
            }

            if(btn=="C"){
                if(it.isNotEmpty()){
                    _equationText.value = it.substring(0,it.length-1)
                }
                return
            }

            if(btn == "="){
                _equationText.value = _resultText.value
                return
            }

            _equationText.value = it+btn

            //Calculate Result
            try {
                _resultText.value =  calculateResult(_equationText.value)
            }catch (_ : Exception){}
        }
    }

    private fun calculateResult(equation : String) : String{
        val context : Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable : Scriptable = context.initStandardObjects()
        var finalResult = context.evaluateString(scriptable,equation,"Javascript",1,null).toString()
        if(finalResult.endsWith(".0")){
            finalResult = finalResult.replace(".0","")
        }
        return finalResult
    }
}
