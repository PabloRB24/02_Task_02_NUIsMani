package com.example.a02task02nuis

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

    private val REQ_CODE_SPEECH_INPUT = 100
    private lateinit var tvResult: TextView

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSpeak: ImageButton = findViewById(R.id.btnSpeak)
        tvResult = findViewById(R.id.textResult)

        btnSpeak.setOnClickListener {
            promptSpeechInput()
        }
    }

    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla algo...")

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (e: ActivityNotFoundException) {
            tvResult.text = "Error: Reconocimiento de voz no disponible"
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val result: ArrayList<String>? =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                var spokenText = result?.get(0) ?: ""
                tvResult.text = "Texto hablado: $spokenText"

                spokenText = spokenText.toLowerCase().toString()
                when {
                    spokenText == "google" ->{
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
                        startActivity(intent)
                    }
                    spokenText == "calendario" ->{
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://calendar.google.com/calendar/u/0/r?hl=es&pli=1"))
                        startActivity(intent)
                    }
                    spokenText == "calculadora" ->{
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=calculadora&rlz=1C1CHBF_esES1083ES1083&oq=calculadora&gs_lcrp=EgZjaHJvbWUqDggAEEUYJxg7GIAEGIoFMg4IABBFGCcYOxiABBiKBTISCAEQABgUGIMBGIcCGLEDGIAEMhEIAhAAGAoYQxixAxiABBiKBTISCAMQABhDGIMBGLEDGIAEGIoFMhIIBBAAGEMYgwEYsQMYgAQYigUyEggFEAAYQxiDARixAxiABBiKBTINCAYQABiDARixAxiABDINCAcQABiDARixAxiABDINCAgQABiDARixAxiABDINCAkQABiDARixAxiABNIBCDE4MDdqMGo3qAIAsAIA&sourceid=chrome&ie=UTF-8"))
                        startActivity(intent)
                    }
                    spokenText == "maps" ->{
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps"))
                        startActivity(intent)
                    }
                    spokenText == "gmail" ->{
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com/mail/u/0/#inbox"))
                        startActivity(intent)
                    }
                }
            }
        }
    }
}

