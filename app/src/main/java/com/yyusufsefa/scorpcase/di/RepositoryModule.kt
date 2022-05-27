package com.yyusufsefa.scorpcase.di

import com.yyusufsefa.scorpcase.data.local.DataSource
import com.yyusufsefa.scorpcase.data.repository.PersonRepository
import com.yyusufsefa.scorpcase.ui.adapter.PersonAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataSource(): DataSource = DataSource()


    @Provides
    @Singleton
    fun providerPersonRepository(source: DataSource): PersonRepository = PersonRepository(source)
}