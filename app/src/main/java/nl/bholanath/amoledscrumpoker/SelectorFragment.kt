package nl.bholanath.amoledscrumpoker

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.Button

abstract class SelectorFragment : Fragment(), View.OnClickListener
{
    var mCallback: OnSelectorActivityInteractionListener? = null

    interface OnSelectorActivityInteractionListener
    {
        fun onSelectionMade(message: CharSequence)
    }

    companion object
    {
        const val CHOSEN_VALUE = "nl.bholanath.amoledscrumpoker.message"
    }

    override fun onAttach(context: Context)
    {
        super.onAttach(context)

        if (context !is Activity)
        {
            return
        }

        try
        {
            mCallback = context as OnSelectorActivityInteractionListener
        }
        catch (e: ClassCastException)
        {
            throw ClassCastException(context.toString() + " must implement OnSelectorActivityInteractionListener.")
        }
    }

    override fun onDetach()
    {
        super.onDetach()
    }

    override fun onClick(view: View)
    {
        if (view !is Button)
        {
            return
        }

        mCallback!!.onSelectionMade(view.text)
    }

    protected fun setOnClickListeners(container: ConstraintLayout)
    {
        val buttons = container.touchables

        for (button in buttons)
        {
            button.setOnClickListener(this)
        }
    }
}
