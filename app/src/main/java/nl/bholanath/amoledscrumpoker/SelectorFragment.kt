package nl.bholanath.amoledscrumpoker

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_selector.*

open class SelectorFragment : Fragment(), View.OnClickListener
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

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_selector, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        setOnClickListeners()
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

    private fun setOnClickListeners()
    {
        val buttons = fragment_selector.touchables

        for (button in buttons)
        {
            button.setOnClickListener(this)
        }
    }
}
