package dadm.alsadel.mygymbro.data.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import dadm.alsadel.mygymbro.data.FirebaseConnection
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val firebaseConnection: FirebaseConnection) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseConnection.auth.currentUser

    override suspend fun login(email: String, password: String): Result<FirebaseUser> {

        return try {
            val result = firebaseConnection.auth.signInWithEmailAndPassword(email,password).await()
            Result.success(result.user!!)
        }
        catch (e: Exception){
            e.printStackTrace()
            Result.failure(e)
        }


    }

    override suspend fun signup(
        name: String,
        email: String,
        password: String
    ): Result<FirebaseUser> {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }
}