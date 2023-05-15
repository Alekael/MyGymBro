package dadm.alsadel.mygymbro.data.auth

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.SignInMethodQueryResult
import dadm.alsadel.mygymbro.data.FirebaseConnection
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.security.auth.login.LoginException

class AuthRepositoryImpl @Inject constructor(private val firebaseConnection: FirebaseConnection) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseConnection.auth.currentUser

    /**
     * Método para loguearse en Firebase con un email y contraseña
     */
    override suspend fun login(email: String, password: String) : Task<AuthResult> {

       return firebaseConnection.auth.signInWithEmailAndPassword(email,password)
    }

    /**
     * Registra un usuario que recibe por parámetro el email y contraseña
     */
    override suspend fun signup(email: String, password: String): Task<AuthResult> {

        return firebaseConnection.auth.createUserWithEmailAndPassword(email, password)
    }

    /**
     * Verifica si el email que llega por parámetro existe en el registro de usuarios de Firebase Authentication
     */
    override suspend fun verifyUser(email: String) : Task<SignInMethodQueryResult> {

        return firebaseConnection.auth.fetchSignInMethodsForEmail(email)
    }

    /**
     * Verifica si el email está verificado o no en Firebase Authentication
     * Si no estuviese verificado, la aplicación no dejaría loguear al usuario
     */
    override suspend fun verifyEmail(): Task<Void> {
        return firebaseConnection.auth.currentUser?.sendEmailVerification() ?: Tasks.forCanceled()
    }

    /**
     * Cerraría la sesión del usuario, dejando la propiedad currentUser de Firebase Authentication en null
     */
    override fun logout() {
        firebaseConnection.auth.signOut()
    }
}