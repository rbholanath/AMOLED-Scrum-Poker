package nl.bholanath.amoledscrumpoker

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_selector.*

class SelectorFragment : Fragment(), View.OnClickListener
{
    var mCallback: OnSelectorActivityInteractionListener? = null

    // Container Activity must implement this interface
    interface OnSelectorActivityInteractionListener
    {
        fun onSelectionMade(message: CharSequence)
    }

    companion object
    {
        const val CHOSEN_VALUE = "nl.bholanath.amoledscrumpoker.message"
        val REGULAR_NUMBERS = arrayOf("0", "½", "1", "2", "3", "5", "8", "13", "20", "40", "100", "∞")
        val T_SHIRT = arrayOf("XXS", "XS", "S", "M", "L", "XL", "XXL", "∞", "?")
    }

    private var _lastRowEnabled = true

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
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

    fun switchMode(shouldLastRowBeEnabled: Boolean, strings: Array<String>)
    {
        if (_lastRowEnabled == shouldLastRowBeEnabled)
        {
            return
        }

        toggleLastRow()

        for ((index, value) in strings.withIndex())
        {
            switchButton(index, value)
        }
    }

    private fun toggleLastRow()
    {
        _lastRowEnabled = !_lastRowEnabled

        val visibility = if (_lastRowEnabled) View.VISIBLE else View.INVISIBLE

        button9.visibility = visibility
        button10.visibility = visibility
        button11.visibility = visibility
    }

    private fun switchButton(index: Int, string: String)
    {
        val buttonID = "button$index"
        val resourceId = resources.getIdentifier(buttonID, "id", "nl.bholanath.amoledscrumpoker")
        val button = getView()!!.findViewById(resourceId) as Button
        button.text = string
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
