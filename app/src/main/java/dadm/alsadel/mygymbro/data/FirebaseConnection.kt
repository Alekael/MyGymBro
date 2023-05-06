package dadm.alsadel.mygymbro.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseConnection @Inject constructor() {

    val auth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

    val db: FirebaseDatabase
        get() = FirebaseDatabase.getInstance()
}