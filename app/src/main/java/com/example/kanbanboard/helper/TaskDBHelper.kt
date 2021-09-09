package com.example.kanbanboard.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.kanbanboard.utails.Constants
import android.content.ContentValues
import android.database.Cursor
import com.example.kanbanboard.model.Task

class TaskDBHelper(context: Context) : SQLiteOpenHelper(context, DBNAME, null, DBVERSION) {
    override fun onCreate(database: SQLiteDatabase?) {
        val sql = "CREATE TABLE ${Constants.DB.TABLE_NAME} (" +
                "${Constants.DB.ID} INTEGER PRIMARY KEY," +
                "${Constants.DB.TASKNAME} TEXT," +
                "${Constants.DB.TASKDESCRIPTION} TEXT," +
                "${Constants.DB.TASKSTATUS} TEXT," +
                "${Constants.DB.TASKCOLOR} TEXT )"
        database?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${Constants.DB.TABLE_NAME}")
        onCreate(db)
    }


    fun listTask(status: String): MutableList<Task> {
        val sql = "select * from ${Constants.DB.TABLE_NAME} where ${Constants.DB.TASKSTATUS} = ?"
        val db = this.readableDatabase
        val storeTask = mutableListOf<Task>()
        val cursor: Cursor = db.rawQuery(sql, arrayOf(status))
        while (cursor.moveToNext()) {
            val taskId = cursor.getString(0).toInt()
            val taskName = cursor.getString(1)
            val taskDescription = cursor.getString(2)
            val taskStatus = cursor.getString(3)
            val taskColor = cursor.getString(4).toInt()
            storeTask.add(Task(
                taskId = taskId,
                taskName = taskName,
                taskDescription = taskDescription,
                taskStatus = taskStatus,
                color = taskColor
            ))
        }
        cursor.close()
        return storeTask
    }

    fun addTask(task: Task) {
        val newEntry = ContentValues().apply {
            put(Constants.DB.TASKNAME, task.taskName)
            put(Constants.DB.TASKDESCRIPTION, task.taskDescription)
            put(Constants.DB.TASKSTATUS, task.taskStatus)
            put(Constants.DB.TASKCOLOR, task.color)
        }
        val db = this.writableDatabase
        db.insert(Constants.DB.TABLE_NAME, null, newEntry)
    }

    fun updateTask(task: Task) {
        val values = ContentValues()
        values.put(Constants.DB.TASKSTATUS, task.taskStatus)
        val db = this.writableDatabase
        db.update(
            Constants.DB.TABLE_NAME,
            values,
            Constants.DB.ID + "	= ?",
            arrayOf(java.lang.String.valueOf(task.taskId))
        )
    }

    fun updateColorTask(task: Task) {
        val values = ContentValues()
        values.put(Constants.DB.TASKCOLOR, task.color)
        val db = this.writableDatabase
        db.update(
            Constants.DB.TABLE_NAME,
            values,
            Constants.DB.ID + "	= ?",
            arrayOf(java.lang.String.valueOf(task.taskId))
        )
    }

    fun deleteTask(id: Int?) {
        val db = this.writableDatabase
        db.delete(Constants.DB.TABLE_NAME, "${Constants.DB.ID} = ?", arrayOf(id.toString()))
    }

    companion object {
        private const val DBNAME = "TaskDataBase"
        private const val DBVERSION = 1
    }
}