package com.erbe.findmylaunch.db

import androidx.room.*

@Entity(tableName = "launches")
data class Launch(
    @ColumnInfo(name = "name")
    @PrimaryKey
    val name: String,
    @ColumnInfo(name = "details")
    val details: String
)

@Entity(tableName = "launches_fts")
@Fts4(contentEntity = Launch::class)
data class LaunchFTS(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "details")
    val details: String
)

data class LaunchWithMatchInfo(
    @Embedded
    val launch: Launch,
    @ColumnInfo(name = "matchInfo")
    val matchInfo: ByteArray
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LaunchWithMatchInfo

        if (launch != other.launch) return false
        if (!matchInfo.contentEquals(other.matchInfo)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = launch.hashCode()
        result = 31 * result + matchInfo.contentHashCode()
        return result
    }
}