package dadm.alsadel.mygymbro.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Users(val email: String?, val password: String?, val nickname: String?, val age: Double?, val weight: Double?,
                val height: Double?, val gender: String?, val level: String?, val interest: String?, val days: String?, val time: Double?) {
// Null default values create a no-argument default constructor, which is needed
// for deserialization from a DataSnapshot.
}
