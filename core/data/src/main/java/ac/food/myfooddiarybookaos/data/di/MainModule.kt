package ac.food.myfooddiarybookaos.data.di

import ac.food.myfooddiarybookaos.api.KakaoApiManager
import ac.food.myfooddiarybookaos.api.NetworkManager
import ac.food.myfooddiarybookaos.data.dataCalendar.repository.CustomCalendarRepository
import ac.food.myfooddiarybookaos.data.dataCalendar.repository.TodayRepository
import ac.food.myfooddiarybookaos.data.dataDetail.DetailRepository
import ac.food.myfooddiarybookaos.data.dataHome.repository.HomePostRepository
import ac.food.myfooddiarybookaos.data.dataHome.repository.HomeRepository
import ac.food.myfooddiarybookaos.data.dataLogin.repository.LoginRepository
import ac.food.myfooddiarybookaos.data.dataMap.repository.MapSearchRepository
import ac.food.myfooddiarybookaos.data.dataMy.local.MyDatabase
import ac.food.myfooddiarybookaos.data.dataMy.repository.NoticeRepository
import ac.food.myfooddiarybookaos.data.dataSearch.repository.SearchRepository
import ac.food.myfooddiarybookaos.data.dataTimeLine.repository.TimeLineRepository
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MainModule {

    @ViewModelScoped
    @Provides
    fun bindTodayRepository() = TodayRepository()

    @ViewModelScoped
    @Provides
    fun provideLoginRepository(
        networkManager: NetworkManager,
        @ApplicationContext context: Context
    ) = LoginRepository(networkManager, context)

    @ViewModelScoped
    @Provides
    fun provideHomePostRepository(
        networkManager: NetworkManager,
        @ApplicationContext context: Context
    ) = HomePostRepository(networkManager, context)

    @ViewModelScoped
    @Provides
    fun provideHomeRepository(
        networkManager: NetworkManager,
        @ApplicationContext context: Context
    ) = HomeRepository(networkManager, context)

    @ViewModelScoped
    @Provides
    fun provideTimeLineRepository(
        networkManager: NetworkManager
    ) = TimeLineRepository(networkManager)


    @ViewModelScoped
    @Provides
    fun provideDetailRepository(
        networkManager: NetworkManager
    ) = DetailRepository(networkManager)

    @Provides
    @ViewModelScoped
    fun bindCustomCalendarRepository() = CustomCalendarRepository()


    @Provides
    @ViewModelScoped
    fun bindMapSearchRepository(
        kakaoApiManager: KakaoApiManager,
        @ApplicationContext context: Context
    ) = MapSearchRepository(kakaoApiManager, context)

    @Provides
    @ViewModelScoped
    fun bindSearchRepository(
        networkManager: NetworkManager,
        @ApplicationContext context: Context
    ) = SearchRepository(networkManager, context)

    @Provides
    @ViewModelScoped
    fun bindNoticeRepository(
        myDatabase: MyDatabase,
        networkManager: NetworkManager
    ) = NoticeRepository(myDatabase, networkManager)

}
