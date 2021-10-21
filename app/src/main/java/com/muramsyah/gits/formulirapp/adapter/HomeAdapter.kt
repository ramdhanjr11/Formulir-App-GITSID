package com.muramsyah.gits.formulirapp.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muramsyah.gits.formulirapp.databinding.ItemLayoutFormulirBinding
import com.muramsyah.gits.formulirapp.domain.model.Formulir

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var formulirs = ArrayList<Formulir>()

    var onItemClicked: ((Formulir) -> Unit)? = null

    fun setData(data: List<Formulir>) {
        formulirs.clear()
        formulirs.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemLayoutFormulirBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(formulirs[position])
    }

    override fun getItemCount(): Int = formulirs.size

    @SuppressLint("UseCompatLoadingForDrawables")
    inner class ViewHolder(private val binding: ItemLayoutFormulirBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Formulir) {

            Glide.with(itemView.context)
                .load("http://192.168.1.7/todoAPI/gambar/${data.image}")
                .into(binding.imgUser)
            binding.tvName.text = data.nama
            binding.tvEmail.text = data.email

            itemView.setOnClickListener { onItemClicked?.invoke(data) }
        }
    }
}