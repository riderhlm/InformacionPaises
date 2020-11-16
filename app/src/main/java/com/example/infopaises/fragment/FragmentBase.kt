package com.example.infopaises.fragment

import android.view.View
import androidx.fragment.app.Fragment



open class FragmentBase: Fragment(){

    protected fun fragmentVisible(): Boolean{
        return activity != null && isAdded
    }

    protected fun showHideView(view: View, vararg viewsToHide: View){
        view.visibility = View.VISIBLE
        for(v in viewsToHide){
            v.visibility = View.GONE
        }
    }

}