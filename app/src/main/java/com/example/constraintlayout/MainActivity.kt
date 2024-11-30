package com.example.constraintlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity(){
    private lateinit var tts: TtsManager
    private lateinit var edtConta: EditText
    private lateinit var texPrice: TextView
    private lateinit var inputPeople: EditText

    private var moneyToSplit: Double = 0.0
    private var peapleNumber: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        texPrice = findViewById<TextView>(R.id.textPrice)
        edtConta = findViewById<EditText>(R.id.edtConta).apply {
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    if(s.toString().isNotEmpty()) {
                        if (s.toString().toDouble() <= 0) {
                            // fazer alguma coisa para mostrar que não
                            return
                        }
                        moneyToSplit = s.toString().toDouble()
                        Log.d("PDM24", "v: $moneyToSplit")
                        updatePrice()
                    }

                }
            })
        }
        inputPeople = findViewById<EditText>(R.id.inputPeople).apply {
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    if(s.toString().isNotEmpty()) {
                        if (s.toString().toDouble() <= 0) {
                            // fazer alguma coisa para mostrar que não
                            return
                        }

                        peapleNumber = s.toString().toInt()
                        Log.d("PDM24", "v: $peapleNumber")
                        updatePrice()
                    }

                }
            })
        }

        tts = TtsManager(this)

    }

    private fun updatePrice() {
        var moneySplitted = moneyToSplit / peapleNumber
        texPrice.text = String.format("R$ %.2f", moneySplitted)
    }

    override fun onDestroy() {
        tts.destroy()
        super.onDestroy()
    }

// Fazer uma validação para não deixar colocar 0 Pessoas
    fun clickFalar(v: View){
        tts.clickFalar(v, peapleNumber, moneyToSplit)
    }

}

