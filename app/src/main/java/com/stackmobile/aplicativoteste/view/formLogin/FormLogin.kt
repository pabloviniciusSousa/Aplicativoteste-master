package com.stackmobile.aplicativoteste.view.formLogin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.stackmobile.aplicativoteste.R
import com.stackmobile.aplicativoteste.databinding.ActivityFormCadastroBinding
import com.stackmobile.aplicativoteste.databinding.ActivityFormLoginBinding
import com.stackmobile.aplicativoteste.view.formCadastro.FormCadastro
import com.stackmobile.aplicativoteste.view.telaPrincipal.TelaPrincipal

class FormLogin : AppCompatActivity() {


    private lateinit var binding: ActivityFormLoginBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogar.setOnClickListener{ view ->

            val email = binding.editEmailLogin.text.toString()
            val senha = binding.editSenhaLogin.text.toString()

            if (email.isEmpty() || senha.isEmpty()){

                val snackbar = Snackbar.make(view, "Preencha os dados no campo!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else{
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { autenticacao ->
                    if(autenticacao.isSuccessful){
                        navegarTelaPrincipal()
                    }
                }.addOnFailureListener {
                    val snackbar = Snackbar.make(view, "Erro ao fazer login do usario", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()

                }


            }
        }

        binding.txtTelaCadastro.setOnClickListener{
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }
    }

    private fun navegarTelaPrincipal(){
        val intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()

        val usuarioAtual = FirebaseAuth.getInstance().currentUser

        if(usuarioAtual != null){
            navegarTelaPrincipal()
        }
    }
}