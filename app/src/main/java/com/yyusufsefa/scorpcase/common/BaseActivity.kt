package com.yyusufsefa.scorpcase.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> :
    AppCompatActivity() {

    internal lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getViewBinding()
        setContentView(binding.root)

        init()

    }

    open fun init() {}

    abstract fun getViewBinding(): VB

}