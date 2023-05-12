package dadm.alsadel.mygymbro.ui.onboarding

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import dadm.alsadel.mygymbro.R

class ConfirmationRegisterDialog: DialogFragment() {

    interface ConfirmationDialogCallBack{
        fun confirmRegister()
    }

    private lateinit var callBack: ConfirmationDialogCallBack

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.confirmRegister)
            .setMessage(R.string.confirmRegisterMessage)
            .setMessage(R.string.confirmRegisterMessage2)
            .setPositiveButton(R.string.confirmRegisterButton){_,_ ->
                callBack.confirmRegister()
            }
            .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = parentFragment as ConfirmationDialogCallBack
    }
}