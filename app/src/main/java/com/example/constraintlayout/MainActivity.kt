package com.example.constraintlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import java.util.*

class MainActivity : AppCompatActivity() , TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech
    private lateinit var edtConta: EditText
    private lateinit var inputPeople: EditText
    private var ttsSucess: Boolean = false;

    private var moneyToSplit: Double = 0.0
    private var peapleNumber: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtConta = findViewById<EditText>(R.id.edtConta)
        inputPeople = findViewById<EditText>(R.id.inputPeople)



        edtConta.addTextChangedListener(object : TextWatcher {
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

        inputPeople.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("PDM24","People mudado")
            }
        })


        // Initialize TTS engine
        tts = TextToSpeech(this, this)

    }



// Fazer uma validação para não deixar colocar 0 Pessoas
    fun clickFalar(v: View){
        Log.d ("teste", this.inputPeople.text.toString())
        if (tts.isSpeaking) {
            tts.stop()
        }
        if(ttsSucess) {

            tts.speak("Você tem $moneyToSplit Reais a ser pago por $peapleNumber Pessoas, que dá ${moneyToSplit/peapleNumber} reais para cada um.", TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }
    override fun onDestroy() {
            // Release TTS engine resources
            tts.stop()
            tts.shutdown()
            super.onDestroy()
        }

    override fun onInit(status: Int) {
            if (status == TextToSpeech.SUCCESS) {
                // TTS engine is initialized successfully
                tts.language = Locale.getDefault()
                ttsSucess=true
                Log.d("PDM23","Sucesso na Inicialização")
            } else {
                // TTS engine failed to initialize
                Log.e("PDM23", "Failed to initialize TTS engine.")
                ttsSucess=false
            }
        }


}

