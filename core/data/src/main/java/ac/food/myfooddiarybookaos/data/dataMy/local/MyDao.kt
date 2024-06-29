package ac.food.myfooddiarybookaos.data.dataMy.local

import ac.food.myfooddiarybookaos.api.myApi.NoticeEntity
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<NoticeEntity>)

    @Query("SELECT * FROM myNotices")
    fun getItemPager(): PagingSource<Int, NoticeEntity>

    @Query("DELETE FROM myNotices")
    fun clearAll()
}
