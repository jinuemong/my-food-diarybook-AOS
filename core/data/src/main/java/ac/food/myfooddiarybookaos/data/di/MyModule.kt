package ac.food.myfooddiarybookaos.data.di

import ac.food.myfooddiarybookaos.api.NetworkManager
import ac.food.myfooddiarybookaos.data.dataMy.repository.MyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MyModule {

    @Singleton
    @Provides
    fun provideMyRepository(
        networkManager: NetworkManager
    ) = MyRepository(networkManager)

}
