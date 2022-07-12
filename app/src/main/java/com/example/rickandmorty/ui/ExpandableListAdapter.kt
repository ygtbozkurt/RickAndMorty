package com.example.rickandmorty.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.rickandmorty.R

class ExpandableListAdapter(
    var context: Context,
    var expandableListView: ExpandableListView,
    var header: String,
    var body: MutableList<String>
) : BaseExpandableListAdapter() {
    override fun getGroup(groupPosition: Int): String {
        return header
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var convertView = convertView
        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.item_expandable_list_group, null)
        }
        val title = convertView?.findViewById<TextView>(R.id.tv_title)
        val arrow = convertView?.findViewById<ImageView>(R.id.image_arrow)
        title?.text = getGroup(groupPosition)
        title?.setOnClickListener {
            if (expandableListView.isGroupExpanded(groupPosition)) {
                expandableListView.collapseGroup(groupPosition)
                arrow?.setImageResource(R.drawable.ic_arrow_down)
            } else {
                expandableListView.expandGroup(groupPosition)
                arrow?.setImageResource(R.drawable.ic_arrow_up)
            }

        }
        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return body.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): String {
        return body[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var convertView = convertView
        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.item_expandable_list_child, null)
        }
        val title = convertView?.findViewById<TextView>(R.id.tv_title)
        title?.text = getChild(groupPosition, childPosition)
        title?.setOnClickListener {
            Toast.makeText(context, getChild(groupPosition, childPosition), Toast.LENGTH_SHORT)
                .show()
        }
        return convertView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return 1
    }


}