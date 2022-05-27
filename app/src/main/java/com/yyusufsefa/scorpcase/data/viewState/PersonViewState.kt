package com.yyusufsefa.scorpcase.data.viewState

import com.yyusufsefa.scorpcase.data.local.Person

class PersonViewState(private val person: Person) {
    fun getPerson() = "${person.fullName} (${person.id})"
}