package com.mintthursday.recipe.creation

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.mintthursday.R
import java.util.*

class SelectCategoryFragment : DialogFragment() {
    private lateinit var listCategory: List<String>
    private val chooseData: MutableSet<String> = HashSet()
    private var listener: NoticeDialogListener? = null

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, chooseData: Set<String>)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        listener = try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            if (parentFragment != null) {
                parentFragment as NoticeDialogListener
            } else {
                context as NoticeDialogListener
            }

//            val parentFragment = parentFragment as? NoticeDialogListener
//                    ?: context as NoticeDialogListener

        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(context.toString()
                    + " must implement NoticeDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        listCategory = resources.getStringArray(R.array.category).toList()
        if (arguments != null && requireArguments().getStringArrayList(ARG_CATEGORY_LIST) != null) {
            val chooseDataList: List<String>? = requireArguments().getStringArrayList(ARG_CATEGORY_LIST)
            chooseData.addAll(chooseDataList!!)
        }
        val builder = AlertDialog.Builder(activity)
        builder.setMultiChoiceItems(listCategory.toTypedArray(), getMultiChoiceItems(listCategory, chooseData)) { dialog, i, isChecked ->
            if (isChecked) {
                chooseData.add(listCategory[i])
            } else {
                chooseData.remove(listCategory[i])
            }
        }
        builder.setTitle(R.string.category_add)
                .setPositiveButton(R.string.add) { dialog, id -> listener?.onDialogPositiveClick(this@SelectCategoryFragment, chooseData) }
                .setNegativeButton(R.string.cancel) { dialog, id -> listener?.onDialogNegativeClick(this@SelectCategoryFragment) }
        // Create the AlertDialog object and return it
        return builder.create()
    }

    private fun getMultiChoiceItems(categories: List<String>, chooseCategories: Set<String>): BooleanArray {
        return categories.map { chooseCategories.contains(it) }.toBooleanArray()
    }

    companion object {
        private const val ARG_CATEGORY_LIST = "ARG_CATEGORY_LIST"

        @JvmStatic
        fun newInstance(data: Collection<String>?): SelectCategoryFragment {
            return SelectCategoryFragment().apply {
                if (data != null) {
                    arguments = bundleOf(ARG_CATEGORY_LIST to ArrayList(data))
                }
            }
        }
    }
}