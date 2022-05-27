package com.yyusufsefa.scorpcase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yyusufsefa.scorpcase.data.local.Person
import com.yyusufsefa.scorpcase.data.viewState.PersonViewState
import com.example.scorpcase.databinding.ItemPersonBinding
import javax.inject.Inject

class PersonAdapter @Inject constructor() :
    PagingDataAdapter<Person, PersonAdapter.PersonViewHolder>(diffCallBack) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PersonViewHolder =
        PersonViewHolder(
            ItemPersonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class PersonViewHolder(private val itemPersonBinding: ItemPersonBinding) :
        RecyclerView.ViewHolder(itemPersonBinding.root) {

        fun bind(person: Person) {
            itemPersonBinding.txtPerson.text = PersonViewState(person).getPerson()
        }
    }

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean =
                oldItem == newItem
        }
    }
}