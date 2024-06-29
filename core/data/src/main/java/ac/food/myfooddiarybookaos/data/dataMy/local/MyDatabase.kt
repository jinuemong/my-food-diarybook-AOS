package ac.food.myfooddiarybookaos.data.dataMy.local

import ac.food.myfooddiarybookaos.api.myApi.NoticeEntity
import ac.food.myfooddiarybookaos.api.remoteKey.MyRemoteKeysEntity
import androidx.room.Database
import androidx.room.RoomDatabase
import com.dnd_9th_3_android.gooding.data.di.remoteKey.MyRemoteKeysDao

@Database(
    version = 2,
    entities = [NoticeEntity::class, MyRemoteKeysEntity::class],
    exportSchema = false,
//    autoMigrations = [
//        AutoMigration(
//            from = 1,
//            to = 2,
//            spec = MyDatabase.MyAutoMigration::class
//        )
//    ]
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun getNoticeDao(): MyDao

    abstract fun remoteKeyDao(): MyRemoteKeysDao

//    @RenameColumn(
//        tableName = "myDatabase",
//        fromColumnName = "remoteKeys",
//        toColumnName = "my_remoteKey"
//    )
//    class MyAutoMigration : AutoMigrationSpec

}
