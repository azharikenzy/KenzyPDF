package com.example.kenzypdf

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ربط أزرار الواجهة الرئيسية بالأنشطة المختلفة
        // ملاحظة: تأكد من تطابق تعريف الأزرار في ملف activity_main.xml لديك

        // 1. قارئ الـ PDF
        // val btnOpenReader = findViewById<Button>(R.id.btnOpenReader)
        // btnOpenReader.setOnClickListener {
        //     startActivity(Intent(this, PdfReaderActivity::class.java))
        // }

        // 2. استخراج النصوص OCR
        // val btnOpenOcr = findViewById<Button>(R.id.btnOpenOcr)
        // btnOpenOcr.setOnClickListener {
        //     startActivity(Intent(this, OcrActivity::class.java))
        // }

        // 3. دمج الملفات
        // val btnOpenMerger = findViewById<Button>(R.id.btnOpenMerger)
        // btnOpenMerger.setOnClickListener {
        //     startActivity(Intent(this, PdfMergerActivity::class.java))
        // }

        // 4. ضغط الملفات
        // val btnOpenCompressor = findViewById<Button>(R.id.btnOpenCompressor)
        // btnOpenCompressor.setOnClickListener {
        //     startActivity(Intent(this, PdfCompressorActivity::class.java))
        // }

        // 5. تحويل النص إلى PDF
        // val btnOpenTextToPdf = findViewById<Button>(R.id.btnOpenTextToPdf)
        // btnOpenTextToPdf.setOnClickListener {
        //     startActivity(Intent(this, TextToPdfActivity::class.java))
        // }
    }
}
