package com.erbe.markme.repository

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.google.gson.Gson
import com.erbe.markme.feature.FeatureContract
import com.erbe.markme.model.Student
import com.erbe.markme.model.database.AppDatabase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import timber.log.Timber

private const val MSG_DATA_SAVED_TO_DB = "Data saved to DB"
private const val MSG_DATA_SAVED_TO_PREFS = "Data saved to prefs"

object AppRepository : FeatureContract.Model<Student>, KoinComponent {

    private val sharedPreferences: SharedPreferences by inject()
    private val database: AppDatabase by inject()

    override fun add2Db(data: List<Student>, callback: (String) -> Unit) {
        doAsync {
            database.userDao().insertStudentList(data)
            uiThread {
                callback(MSG_DATA_SAVED_TO_DB)
            }
        }
    }

    @SuppressLint("ApplySharedPref")
    override fun add2Prefs(data: List<Student>, callback: (String) -> Unit) {
        doAsync {
            data.forEach {
                with(sharedPreferences.edit()) {
                    val jsonString = Gson().toJson(it)
                    putString(it.name, jsonString).commit()
                }
            }
            uiThread {
                callback(MSG_DATA_SAVED_TO_PREFS)
            }
        }
    }

    override fun fetchFromDb(data: List<Student>, callback: (List<Student>) -> Unit) {
        doAsync {
            val list = database.userDao().loadAllStudents()
            uiThread {
                callback(list)
            }
        }
    }

    override fun fetchFromPrefs(data: List<Student>): List<Student> {
        data.forEach {
            val item: Student? = Gson().fromJson(sharedPreferences.getString(it.name, ""), Student::class.java)
            item?.let { persItem ->
                it.attendance = persItem.attendance
                it.grade = persItem.grade
            }
        }

        return data
    }
}