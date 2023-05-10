package dadm.alsadel.mygymbro.data.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.SignInMethodQueryResult

interface AuthRepository {

    val currentUser : FirebaseUser?

    suspend fun login(email: String, password : String) : Task<AuthResult>

    suspend fun signup(email: String, password: String) : Task<AuthResult>

    suspend fun verifyUser(email : String) : Task<SignInMethodQueryResult>

    fun logout()

}