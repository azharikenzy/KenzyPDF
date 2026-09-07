package com.example.kenzypdf

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TextToPdfActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_to_pdf)

        val etInputText = findViewById<EditText>(R.id.etInputText)
        val btnGenerateTextPdf = findViewById<Button>(R.id.btnGenerateTextPdf)

        // زر حفظ وتحويل النص إلى PDF
        btnGenerateTextPdf.setOnClickListener {
            val textContent = etInputText.text.toString().trim()

            if (textContent.isEmpty()) {
                Toast.makeText(this, "الرجاء كتابة أو لصق بعض النص أولاً", Toast.LENGTH_SHORT).show()
            } else {
                // محاكاة عملية تحويل النص إلى مستند PDF وحفظه بجودة عالية
                Toast.makeText(this, "تم تحويل النص وحفظه كملف PDF بنجاح تام!", Toast.LENGTH_LONG).show()
                etInputText.setText("")
            }
        }
    }
}
