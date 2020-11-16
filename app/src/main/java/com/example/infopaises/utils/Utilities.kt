package com.example.infopaises.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.infopaises.R

class Utilities {

    companion object{
        fun replaceFragment(manager: FragmentManager, nextFragment: Fragment, viewId: Int, effect: Boolean, backStack: Boolean){
            val transaction = manager.beginTransaction()
            if(effect){
                transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out, R.anim.right_in, R.anim.right_out)
            }
            transaction.replace(viewId, nextFragment)
            if(backStack)
                transaction.addToBackStack(null)
            transaction.commit()
        }

        /*fun showSnackBar(view: View?, text: String){
            if(view != null)
                Snackbar.make(view, text, Snackbar.LENGTH_LONG).show()
        }*/

        fun showToast(context: Context, text: String){
            Toast.makeText(context,text, Toast.LENGTH_LONG).show()
        }
    }
}