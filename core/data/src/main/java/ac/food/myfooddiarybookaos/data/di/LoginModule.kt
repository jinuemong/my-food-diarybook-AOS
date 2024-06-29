package ac.food.myfooddiarybookaos.data.di

import ac.food.myfooddiarybookaos.api.NetworkManager
import ac.food.myfooddiarybookaos.data.dataLogin.repository.GoogleLoginRepository
import ac.food.myfooddiarybookaos.data.dataLogin.repository.KaKaoLoginRepository
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object LoginModule {

    @Provides
    @ViewModelScoped
    fun bindGoogle(
        @ApplicationContext context: Context,
        networkManager: NetworkManager
    ) = GoogleLoginRepository(context, networkManager)

    @Provides
    @ViewModelScoped
    fun bindKaKao(
        @ApplicationContext context: Context,
        networkManager: NetworkManager
    ) = KaKaoLoginRepository(context, networkManager)
}
