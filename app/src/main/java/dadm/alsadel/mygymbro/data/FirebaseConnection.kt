package dadm.alsadel.mygymbro.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseConnection @Inject constructor() {

    /**
     * Instancia de Firebase Authentication para hacer todas las llamadas respecto a la sesión del usuario
     */
    val auth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

    /**
     * Instancia de Realtime Database para guardar toda la información sobre el usuario, las sesiones de entrenamiento y los planes de entrenamiento
     */
    val db: FirebaseDatabase
        get() = FirebaseDatabase.getInstance()
}