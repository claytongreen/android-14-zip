package com.green.ziptest

import android.graphics.Color
import android.os.Bundle
import android.text.format.Formatter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.StringBuilder
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class MainActivity : AppCompatActivity() {
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text = findViewById(R.id.text)

        findViewById<Button>(R.id.open_test).setOnClickListener { openZip("windows.zip") }
        findViewById<Button>(R.id.open_yii).setOnClickListener { openZip("yii.zip") }
    }

    private fun openZip(file: String) {
        val color = obtainStyledAttributes(intArrayOf(com.google.android.material.R.attr.colorOnSurface)).getColor(0, Color.MAGENTA)
        text.setTextColor(color)
        text.text = "Loading..."

        assets.open(file).use { inputStream ->
            val sb = StringBuilder()
            sb.appendLine("*** $file\n")

            try {
                val zip = ZipInputStream(inputStream)
                while (true) {
                    val entry: ZipEntry = zip.nextEntry ?: break

                    val content = Formatter.formatFileSize(this, entry.size)

                    sb.appendLine("${entry.name}: $content")
                }

                val color = obtainStyledAttributes(intArrayOf(android.R.attr.colorPrimary)).getColor(0, Color.MAGENTA)
                text.setTextColor(color)
            } catch (e: Exception) {
                val color = obtainStyledAttributes(intArrayOf(android.R.attr.colorError)).getColor(0, Color.MAGENTA)
                text.setTextColor(color)

                sb.append(e.message)
            }

            text.text = sb.toString()
        }

    }
}

