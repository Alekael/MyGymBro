package dadm.alsadel.mygymbro.data
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class TrainingPlan(val username:String?, val plan:HashMap<String,List<Exercise>>?){}
