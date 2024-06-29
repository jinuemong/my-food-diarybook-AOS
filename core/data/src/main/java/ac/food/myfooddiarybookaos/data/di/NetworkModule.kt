package ac.food.myfooddiarybookaos.data.di

import ac.food.myfooddiarybookaos.api.NetworkManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideNetworkManager(
        @ApplicationContext context: Context
    ) = NetworkManager(context)

}
