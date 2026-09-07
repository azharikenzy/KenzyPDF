package com.example.kenzypdf

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader

class PdfReaderActivity : AppCompatActivity() {

    private val PICK_PDF_FILE = 200
    private lateinit var tvPdfContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_reader)

        val btnSelectPdf = findViewById<Button>(R.id.btnSelectPdf)
        tvPdfContent = findViewById<TextView>(R.id.tvPdfContent)

        // النقر على الزر لفتح مستعرض الملفات لاختيار ملف PDF
        btnSelectPdf.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/pdf"
            }
            startActivityForResult(intent, PICK_PDF_FILE)
        }
    }

    // استقبال الملف المختار وعرض تفاصيله أو محتواه النصي
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PDF_FILE && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                readPdfContent(uri)
            }
        }
    }

    private fun readPdfContent(uri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String? = reader.readLine()

            while (line != null) {
                stringBuilder.append(line).append("\n")
                line = reader.readLine()
            }
            inputStream?.close()

            // عرض النص المستخرج من الملف في شاشة القارئ
            val content = stringBuilder.toString()
            if (content.isNotBlank()) {
                tvPdfContent.text = content
            } else {
                tvPdfContent.text = "تم فتح ملف Kenzy PDF بنجاح! (ملف مصور أو محمي)."
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "خطأ في قراءة الملف: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
