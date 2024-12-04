package com.example.constraintlayout

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    // Gerenciador de Text-to-Speech (TTS)
    private lateinit var tts: TtsManager

    // Campos de entrada para valor da conta, o valor dividido
    // e a quantidade de pessoas a dividir a conta.
    private lateinit var edtConta: EditText
    private lateinit var texPrice: TextView
    private lateinit var inputPeople: EditText

    // Variáveis para tratar e persistir o valor da conta e o número de pessoas a dividir
    private var moneyToSplit: Double = 0.0
    private var peopleNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializando as variáveis dos campos para receber o valor da conta e ...
        texPrice = findViewById<TextView>(R.id.textPrice)
        edtConta = findViewById<EditText>(R.id.edtConta).apply {

            addTextChangedListener(object : TextWatcher {
                // Métodos de controle da formatação do texto, antes, depois e durante a edição
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Validações para evitar entradas inválidas

                    // Verifica o campo vazio
                    if (s.toString().isNotEmpty() && s != null) {

                        // Depois verifica a inserção de dados inválidos
                        if ((s.count { it == ',' } > 1) or (s.toString() == ",")) {
                            // Remove o último caractere inválido
                            val newNumber = s.substring(0, s.length - 1)

                            // Atualiza o valor da conta
                            edtConta.setText(newNumber)
                            // Define o cursor no final do texto
                            edtConta.setSelection(s.length - 1)
                        } else {
                            // Caso o valor atenda ao formato correto ele é convertido para Double
                            // p/ realizar as operações
                            moneyToSplit = s.toString().replace(',', '.').toDouble()
                        }
                        // Por fim depois de tratado o valor o metodo é chamado para atualizar o valor dividido
                        updatePrice()
                    } else {
                        // Enquanto não houver nenhum valor o app vai mostrar o valor de zero reais
                        texPrice.text = "R$ 0,00"

                        // Reseta o valor da conta, assim caso um valor seja digitado e apagado,
                        // deixando o campo vazio novamente, a divisão é resetada
                        moneyToSplit = 0.0
                    }
                }
            })
        }

        inputPeople = findViewById<EditText>(R.id.inputPeople).apply {
            // Verificações similares ao edtConta mas agora sem verificar valores flutuantes e sim inteiros
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().isNotEmpty()) {
                        // Converte o número de pessoas para Int para realizar as operações de divisão da conta
                        peopleNumber = s.toString().toInt()
                        //Log.d("PDM24", "v: $peopleNumber")

                        // Atualiza o preço dividido baseado no número de pessoas digitado no campo
                        updatePrice()
                    } else {
                        // Enquanto não houver nenhum valor o app vai mostrar o valor de zero reais
                        texPrice.text = "R$ 0,00" // Não seria melhor exibir o valor cheio no lugar

                        // Reseta a quantidade de pessoas, assim caso um valor seja digitado e apagado,
                        // deixando o campo vazio novamente, a divisão é resetada, similar ao edtConta
                        peopleNumber = 0 // Reseta o número de pessoas
                    }
                }
            })
        }

        // Inicializa o Text to Speech
        tts = TtsManager(this)
    }

    // Metodo de atualização do valor da conta dividido por pessoa
    @SuppressLint("DefaultLocale", "SetTextI18n")
    private fun updatePrice() {
        // Verifica se o valor da conta ou o número de pessoas são inválidos
        if ((moneyToSplit <= 0) or (peopleNumber <= 0)) {
            texPrice.text = "R$ 0,00"
            return
        }
        // Calcula o valor dividido e atualiza o texto no TextView
        texPrice.text = String.format("R$ %.2f", (moneyToSplit / peopleNumber))
    }


    override fun onDestroy() {
        // Finaliza os recursos do TTS antes de fechar o app
        tts.destroy()
        super.onDestroy()
    }

    // Metodo de fala do botão de Text to Speech
    fun clickFalar(v: View) {
        tts.clickFalar(v, peopleNumber, moneyToSplit)
    }

    // Metodo de compartilhamento do valor da conta
    fun clickShare(v: View) {
        val sendIntent: Intent = Intent().apply {
            // Define a ação de envio
            action = Intent.ACTION_SEND
            // Mensagem personalizada
            putExtra(Intent.EXTRA_TEXT, "Olá amigo,nossa conta deu R$ ${edtConta.text} e você tem que me passar ${texPrice.text}. Me manda no pix -> [Insira aqui seu pix]")
            // Define o tipo de conteúdo a ser compartilhado
            type = "text/plain"
        }
        // Cria o chooser para escolher o app de envio da mensagem com o valor da conta
        val shareIntent = Intent.createChooser(sendIntent, "Algo")
        // Inicializa a atividade de compartilhamento
        startActivity(shareIntent)
    }
}
