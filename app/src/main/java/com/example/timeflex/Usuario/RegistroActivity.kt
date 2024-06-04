package com.example.timeflex.Usuario

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.timeflex.Constantes
import com.example.timeflex.MainActivity
import com.example.timeflex.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistroActivity : AppCompatActivity() {

    private lateinit var  binding : ActivityRegistroBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.btnRegistro.setOnClickListener {
            validarInformacion()
        }
    }

    private var nombre = ""
    private var correo = ""
    private var contrasena = ""
    private var ccontrasena = ""
    private fun validarInformacion() {
        nombre = binding.etNombre.text.toString().trim()
        correo = binding.etCorreo.text.toString().trim()
        contrasena = binding.etcontrasena.text.toString().trim()
        ccontrasena = binding.etCcontrasena.text.toString().trim()

        if (nombre.isEmpty()){
            binding.etNombre.error = "Ingresa un Nombre"
            binding.etNombre.requestFocus()
        }else if (correo.isEmpty()){
            binding.etCorreo.error = "Ingresa un correo"
            binding.etCorreo.requestFocus()
        }else if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            binding.etCorreo.error = "Correo no valido"
            binding.etCorreo.requestFocus()
        }else if(contrasena.isEmpty()){
            binding.etcontrasena.error = "Ingresa una contraseña"
            binding.etcontrasena.requestFocus()
        }else if (contrasena.length < 6){
            binding.etcontrasena.error = "Necesita 6 o más caracteres"
            binding.etcontrasena.requestFocus()
        }else if (ccontrasena.isEmpty()){
            binding.etCcontrasena.error = "Confirme la contraseña"
            binding.etCcontrasena.requestFocus()
        }else if (contrasena!=ccontrasena){
            binding.etCcontrasena.error = "No coincicen las contraseñas"
            binding.etCcontrasena.requestFocus()
        }else {
            registrar()
        }

    }

    private fun registrar() {
        progressDialog.setMessage("Creando Cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(correo, contrasena)
            .addOnSuccessListener {
                insertarInfoBD()
            }
            .addOnFailureListener {e->
                Toast.makeText(this, "Fallo el Registro debido a ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }

    private fun insertarInfoBD(){
        progressDialog.setMessage("Guardando Información...")

        val uidBD = firebaseAuth.uid
        val nombreBD = nombre
        val correoBD = correo
        val contrasenaBD = contrasena
        val tiempoBD = Constantes().obtenerTiempoD()

        val datosUsuario = HashMap<String, Any>()

        datosUsuario["uid"] = "$uidBD"
        datosUsuario["nombre"] = "$nombreBD"
        datosUsuario["correo"] = "$correoBD"
        datosUsuario["contrasena"] ="$contrasenaBD"
        datosUsuario["tiempo_Registro"] = tiempoBD

        val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
        reference.child(uidBD!!)
            .setValue(datosUsuario)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivityUser::class.java))
                finish()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(this, "No se registro en la BD debido a ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}