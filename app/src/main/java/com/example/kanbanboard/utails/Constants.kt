package com.example.kanbanboard.utails

object Constants {
    object DB {
        const val TABLE_NAME = "TASK"
        const val ID = "id"
        const val TASKNAME = "taskName"
        const val TASKDESCRIPTION = "taskDescription"
        const val TASKCOLOR = "taskColor"
        const val TASKSTATUS = "taskStatus"
    }

    object TaskStatus {
        const val BACKLOG = "Backlog"
        const val PROGRESS = "Progress"
        const val DONE = "Done"
    }
}