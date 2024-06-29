package com.dnd_9th_3_android.gooding.data.di.remoteKey

import ac.food.myfooddiarybookaos.api.remoteKey.MyRemoteKeysEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRemote(list: List<MyRemoteKeysEntity>)

    @Query("SELECT * FROM my_remoteKey WHERE id = :id")
    fun getRemoteKeys(id: Int): MyRemoteKeysEntity

    @Query("DELETE FROM my_remoteKey")
    fun clearAll()
}
