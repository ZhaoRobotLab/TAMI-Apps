package com.muse.museapp.activities.readInputFile
import android.os.Build
import android.os.Environment.getExternalStorageDirectory
import android.util.Log
import androidx.annotation.RequiresApi
import android.content.res.Resources as resources
import java.io.InputStream


class readInputFileSession {
    private val mStoragePath = getExternalStorageDirectory().absolutePath
    var lineList = mutableListOf<String>()
    @RequiresApi(Build.VERSION_CODES.O)
    fun readFile(){

        Log.i("asd", "Calling readFile()")
        //val path = Paths.get("").toAbsolutePath().toString()
        //val path1 = Paths.get(System.getProperty("user.dir"))
        //Log.i("asd", "path: $path")
        //val newpath1 = path1 + "tmp"
        Log.i("asd", "path: $mStoragePath")
        val absolutePath = "C:\\Users\\georg\\StudioProjects\\tami_app\\app\\src\\main\\java\\com\\tammy\\tammyapp\\readInputFile"
        val newpath = mStoragePath + "/tmp"
        val packageName = "com.tami.tamiapp.activities"

        val taskId = resources.getSystem().getIdentifier( packageName + ":raw/", null, null)
        Log.i("asd task id:", "$taskId")
        val inputStream: InputStream = resources.getSystem().openRawResource(taskId)
        inputStream.bufferedReader().useLines {
                lines -> lines.forEach { lineList.add(it)}
        }

        //Assume folder is sorted by topics

        /*
        File(mStoragePath).walkTopDown().forEach {
            Log.i("asd", "inside for each loop")
            Log.i("asd","$it")

        }

        val dir = File( object {}.javaClass.getResource(mStoragePath).file )
        dir.walk().forEach { f ->
            if (f.isFile) {
                //println("file ${f.name}")
                Log.i("asd", f.name)
            } else {
                Log.i("asd dir", f.name)
            }
        }

        if (Files.isDirectory(path1)){
            //List all items in the directory. Note that we are using Java 8 streaming API to group the entries by
            //directory and files
            val fileDirMap = Files.list(path1).collect(Collectors.partitioningBy( {it -> Files.isDirectory(it)}))

            Log.i("asd", "directories")
            //Print out all of the directories
            fileDirMap[true]?.forEach { it -> println(it.fileName) }

        } else {
            //println("Enter a directory")
        }
        */

    }
}