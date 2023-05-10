package dadm.alsadel.mygymbro.di

import com.google.firebase.database.FirebaseDatabase
import dadm.alsadel.mygymbro.data.database.UserRepository
import dadm.alsadel.mygymbro.data.database.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UserProviderModule {

    fun provideFirebaseDatabase() : FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun providesUserRepository(impl: UserRepositoryImpl) : UserRepository = impl
}