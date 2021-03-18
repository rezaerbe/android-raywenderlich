package com.erbe.markme.model.database

import androidx.room.*
import com.erbe.markme.model.Student

@Dao
interface UserDao {
    @Query("SELECT * FROM class ORDER BY name")
    fun loadAllStudents(): List<Student>

    @Query("SELECT * FROM class WHERE name = :name")
    fun findByName(name: String): Student

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student: Student)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudentList(students: List<Student>)

    @Delete
    fun deleteStudent(student: Student)
}

@Database(entities = [Student::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}