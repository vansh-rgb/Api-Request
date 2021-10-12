package com.strink.apirequest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.strink.apirequest.R
import com.strink.apirequest.model.Detail

class DetailAdapter(private var details: ArrayList<Detail>, private val context: Context) :
    RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailAdapter.DetailViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return DetailViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DetailAdapter.DetailViewHolder, position: Int) {
        val resObject = details[position]
        holder.detailId.text = resObject.id.toString()
        holder.detailName.text = resObject.name
        holder.detailEmail.text = resObject.email
        holder.detailGender.text = resObject.gender
        holder.detailStatus.text = resObject.status
    }

    override fun getItemCount(): Int {
        return details.size
    }

    class DetailViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val detailId: TextView = view.findViewById(R.id.idVariable)
        val detailName: TextView = view.findViewById(R.id.nameTxtView)
        val detailEmail: TextView = view.findViewById(R.id.emailVariable)
        val detailGender: TextView = view.findViewById(R.id.genderVariable)
        val detailStatus: TextView = view.findViewById(R.id.statusVariable)
    }
}