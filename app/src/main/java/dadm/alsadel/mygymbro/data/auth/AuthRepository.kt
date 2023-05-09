package dadm.alsadel.mygymbro.data.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    val currentUser : FirebaseUser?

    suspend fun login(email: String, password : String) : Task<AuthResult>

    suspend fun signup(name: String, email: String, password: String) : Result<FirebaseUser>

    fun logout()

}