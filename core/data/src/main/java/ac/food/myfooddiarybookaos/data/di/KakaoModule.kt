package ac.food.myfooddiarybookaos.data.di

import ac.food.myfooddiarybookaos.api.KakaoApiManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object KakaoModule {

    @Singleton
    @Provides
    fun provideKakaoManager(
        @ApplicationContext context: Context
    ) = KakaoApiManager(context)
}
