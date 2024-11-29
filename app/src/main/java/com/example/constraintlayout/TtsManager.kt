package com.example.constraintlayout

import android.os.Bundle
import android.speech.tts.TextToSpeech

import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class TtsManager(context: AppCompatActivity) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech = TextToSpeech(context, this)
    private var ttsSucess: Boolean = false;


//    override fun onDestroy() {
//        // Release TTS engine resources
//        tts.stop()
//        tts.shutdown()
//        super.onDestroy()
//    }

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


    fun clickFalar(v: View, peapleNumber: Int, moneyToSplit: Double){
        if (tts.isSpeaking) {
            tts.stop()
        }
        if(ttsSucess ) {
            tts.speak("Você tem $moneyToSplit Reais a ser pago por $peapleNumber Pessoas, que dá ${moneyToSplit/peapleNumber} reais para cada um.", TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }


}