package com.ellies.composeapplication

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update

/*
data class Task(var name: String, var complete: Boolean)

class TaskDao {
    private var _tasks: MutableLiveData<List<Task>> = MutableLiveData(
        mutableListOf(Task(name = "Buy Milk", complete = true))
    )

    fun insert(task: Task){
        val newTasks = mutableListOf<Task>()
        newTasks.add(task)
        newTasks.addAll(_tasks.value!!)
        _tasks.postValue(newTasks)
    }

    fun getAll() = _tasks
}
*/

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "complete")
    var complete: Boolean = false,
)

@androidx.room.Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM task WHERE id = :key")
    fun get(key: Long): LiveData<Task>

    @Query("SELECT * FROM task ORDER BY id DESC")
    fun getAll(): LiveData<List<Task>>
}

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        "tasks_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}