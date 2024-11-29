package com.example.constraintlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity(){
    private lateinit var tts: TtsManager
    private lateinit var edtConta: EditText
    private lateinit var inputPeople: EditText

    private var moneyToSplit: Double = 0.0
    private var peapleNumber: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtConta = findViewById<EditText>(R.id.edtConta).apply {
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    Log.d("PDM24","Antes de mudar")
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    Log.d("PDM24","Mudando")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.d ("PDM24", "Depois de mudar")

                    val valor: Double

                    if(s.toString().isNotEmpty()) {
                        moneyToSplit = s.toString().toDouble()
                        Log.d("PDM24", "v: $moneyToSplit")
                        //    edtConta.setText("9")
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
                    Log.d("PDM24","People mudado")
                }
            })
        }

        tts = TtsManager(this)

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

