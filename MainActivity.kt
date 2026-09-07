package com.example.kenzypdf

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val PICK_IMAGES_REQUEST = 100
    private val STORAGE_PERMISSION_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ربط زر "صور إلى PDF" في الواجهة بالأمر البرمجي
        val cardImagesToPdf = findViewById<CardView>(R.id.cardImagesToPdf)
        cardImagesToPdf.setOnClickListener {
            if (checkPermission()) {
                openGalleryForImages()
            } else {
                requestPermission()
            }
        }
    }

    // التحقق من صلاحيات التخزين
    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    // طلب صلاحيات التخزين إذا لم تكن مفعلة
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_CODE
        )
    }

    // فتح معرض الصور لاختيار صور متعددة بجودة عالية
    private fun openGalleryForImages() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        startActivityForResult(Intent.createChooser(intent, "اختر الصور لتحويلها إلى Kenzy PDF"), PICK_IMAGES_REQUEST)
    }

    // استقبال الصور المختارة وتحويلها فوراً إلى ملف PDF بجودة HD فائقة
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGES_REQUEST && resultCode == RESULT_OK) {
            val pdfDocument = PdfDocument()
            val imageUris = mutableListOf<Uri>()

            if (data?.clipData != null) {
                val count = data.clipData!!.itemCount
                for (i in 0 until count) {
                    imageUris.add(data.clipData!!.getItemAt(i).uri)
                }
            } else if (data?.data != null) {
                imageUris.add(data.data!!)
            }

            if (imageUris.isNotEmpty()) {
                try {
                    for ((index, uri) in imageUris.withIndex()) {
                        val inputStream = contentResolver.openInputStream(uri)
                        val bitmap = BitmapFactory.decodeStream(inputStream)

                        // صفحة جديدة في ملف الـ PDF بأبعاد الصورة الأصلية للحفاظ على جودة HD
                        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, index + 1).create()
                        val page = pdfDocument.startPage(pageInfo)

                        // رسم الصورة بدقة فائقة دون أي ضغط يقلل الوضوح
                        page.canvas.drawBitmap(bitmap, 0f, 0f, null)
                        pdfDocument.finishPage(page)
                        inputStream?.close()
                    }

                    // مسار حفظ ملف الـ PDF الناتج في الهاتف
                    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    val file = File(downloadsDir, "KenzyPDF_${System.currentTimeMillis()}.pdf")
                    
                    val fos = FileOutputStream(file)
                    pdfDocument.writeTo(fos)
                    pdfDocument.close()
                    fos.close()

                    Toast.makeText(this, "تم إنشاء ملف PDF بنجاح في التنزيلات (Downloads)!", Toast.LENGTH_LONG).show()

                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "حدث خطأ أثناء التحويل: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
