package com.example.timeflex.Usuario

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.timeflex.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnLogin.setOnClickListener {
            validarInfo()
        }

        binding.tvRegistrar.setOnClickListener {
            startActivity(Intent(applicationContext, RegistroActivity::class.java))
        }
    }


    private var correo = ""
    private var  contrasena = ""
    private fun validarInfo() {
        correo = binding.etCorreo.text.toString().trim()
        contrasena = binding.etcontrasena.text.toString().trim()

        if (correo.isEmpty()){
            binding.etCorreo.error = "Ingresa un correo"
            binding.etCorreo.requestFocus()
        }else if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            binding.etCorreo.error = "Correo no valido"
            binding.etCorreo.requestFocus()
        }else if(contrasena.isEmpty()){
            binding.etcontrasena.error = "Ingresa una contraseña"
            binding.etcontrasena.requestFocus()
        }else{
            login()
        }
    }

    private fun login() {
        progressDialog.setMessage("Ingresando...")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(correo, contrasena)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivityUser::class.java))
                finishAffinity()
                Toast.makeText(
                    this,
                    "Bienvenido(a)",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se ha podido iniciar sesión debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}