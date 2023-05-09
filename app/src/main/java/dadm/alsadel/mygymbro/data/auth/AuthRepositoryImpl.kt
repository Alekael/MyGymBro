package dadm.alsadel.mygymbro.data.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import dadm.alsadel.mygymbro.data.FirebaseConnection
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.security.auth.login.LoginException

class AuthRepositoryImpl @Inject constructor(private val firebaseConnection: FirebaseConnection) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseConnection.auth.currentUser

    override suspend fun login(email: String, password: String) : Task<AuthResult> {

       return firebaseConnection.auth.signInWithEmailAndPassword(email,password)
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