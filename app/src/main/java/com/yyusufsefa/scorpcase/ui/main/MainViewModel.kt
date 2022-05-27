package com.yyusufsefa.scorpcase.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.yyusufsefa.scorpcase.data.local.Person
import com.yyusufsefa.scorpcase.data.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val personRepository: PersonRepository) :
    ViewModel() {

    private val _persons = MutableLiveData<PagingData<Person>>()
    val person: LiveData<PagingData<Person>> get() = _persons

    init {
        getPersons()
    }

    private fun getPersons() {
        viewModelScope.launch {
            personRepository.getPersons().collect { values ->
                _persons.value = values
            }
        }
    }
}