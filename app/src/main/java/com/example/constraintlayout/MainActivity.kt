package com.example.constraintlayout

import android.content.Intent
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

        if ((moneyToSplit <= 0) or (peapleNumber <= 0))  {
            texPrice.text = "R$ 0,00"
            return
        }

        texPrice.text = String.format("R$ %.2f", (moneyToSplit / peapleNumber))
    }

    override fun onDestroy() {
        tts.destroy()
        super.onDestroy()
    }

// Fazer uma validação para não deixar colocar 0 Pessoas
    fun clickFalar(v: View){
        tts.clickFalar(v, peapleNumber, moneyToSplit)
    }

    fun clickShare(v: View) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Olá amigo, você tem que me passar ${texPrice.text}. Me manda no pix -> [Insira seu pix]")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Algo")
        startActivity(shareIntent)

    }

}

