package com.hust.ping.androidlover.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hust.ping.androidlover.R
import com.hust.ping.androidlover.ui.main.MainActivity
import com.hust.ping.androidlover.utils.hideInput
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private val registerViewModel by lazy {
        ViewModelProviders.of(this).get(RegisterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()
    }

    private fun initView() {
        register.setOnClickListener {
            registerViewModel.register(register_name.text.toString(), register_pwd.text.toString(), register_confirm_pwd.text.toString())
        }
        registerViewModel.registerLiveData.observe(this, Observer<Int>{
            when(it) {
                RegisterViewModel.REGISTER_ING -> {
                    register_progress_root.visibility = View.VISIBLE
                    register_progress_root.setOnClickListener{}
                    hideInput(this)
                }
                RegisterViewModel.NAME_NULL -> {
                    register_progress_root.visibility = View.GONE
                    register_name.error = getString(R.string.input_name)
                }
                RegisterViewModel.PWD_NULL -> {
                    register_progress_root.visibility = View.GONE
                    register_pwd.error = getString(R.string.input_pwd)
                }
                RegisterViewModel.CONFIRM_PWD_NULL -> {
                    register_progress_root.visibility = View.GONE
                    register_confirm_pwd.error = getString(R.string.input_confirm_pwd)
                }
                RegisterViewModel.PWD_CONFIRM_ERROR -> {
                    register_progress_root.visibility = View.GONE
                    register_confirm_pwd.error = getString(R.string.confirm_pwd_error)
                }
                RegisterViewModel.NAME_EXIST -> {
                    register_progress_root.visibility = View.GONE
                    register_name.error = getString(R.string.name_exist)
                }
                RegisterViewModel.REGISTER_SUCCESS -> {
                    register_progress_root.visibility = View.GONE
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                RegisterViewModel.REGISTER_ERROR -> { register_progress_root.visibility = View.GONE }
            }
        })
    }
}
