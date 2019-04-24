package com.example.tareaexternalstorage

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    lateinit var txtInput:EditText
    lateinit var txtOutput:EditText
    lateinit var btnSave:Button
    lateinit var btnRead:Button
    var file:File
    val externalFolderPath = Environment.getExternalStorageDirectory().absoluteFile
    val fileName = "savedToExternal.txt"
    init {
        file = File(externalFolderPath,fileName)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtInput = findViewById(R.id.txt_input)
        txtOutput = findViewById(R.id.txt_output)
        btnSave = findViewById(R.id.btn_save)
        btnRead = findViewById(R.id.btn_read)


        btnSave.setOnClickListener{
            try{
                val writeExternalStoragePermission = ContextCompat.checkSelfPermission(this@MainActivity,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if(writeExternalStoragePermission!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1234)
                    Toast.makeText(this,"Try to save again",Toast.LENGTH_SHORT).show()
                }else{
                    val outputStreamWriter = OutputStreamWriter(FileOutputStream(file)).apply {
                        write(txtInput.text.toString())
                        flush()
                        close()
                    }
                    Toast.makeText (this, "Data written correctly", Toast.LENGTH_SHORT) .show ()
                }




            }catch (e:IOException){
                Log.e("CUSTOM",e.toString())
            }
        }

        btnRead.setOnClickListener {
            try{
                val fileInputStream = FileInputStream(file)
                val document = InputStreamReader(fileInputStream)
                val bufferedReader = BufferedReader(document)
                var line = bufferedReader.readLine()

                val allContent = StringBuilder()
                while (line!=null){
                    allContent.append(line)
                    line = bufferedReader.readLine()
                }
                bufferedReader.close()
                document.close()
                txtOutput.setText(allContent)

            }
            catch (e:IOException){
                Log.e("CUSTOM",e.toString())

            }
        }
    }
}
