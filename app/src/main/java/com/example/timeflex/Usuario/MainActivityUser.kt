package com.example.timeflex.Usuario

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.timeflex.R
import com.example.timeflex.Usuario.Bottom_Nav_Fragments_Usuario.FragmentApps
import com.example.timeflex.Usuario.Bottom_Nav_Fragments_Usuario.FragmentEstadisticas
import com.example.timeflex.Usuario.Bottom_Nav_Fragments_Usuario.FragmentExplorar
import com.example.timeflex.Usuario.Bottom_Nav_Fragments_Usuario.FragmentNotas
import com.example.timeflex.Usuario.Bottom_Nav_Fragments_Usuario.FragmentSocial
import com.example.timeflex.Usuario.Nav_Fragments_Usuario.FragmentConfiguracion
import com.example.timeflex.Usuario.Nav_Fragments_Usuario.FragmentInicio
import com.example.timeflex.Usuario.Nav_Fragments_Usuario.FragmentPerfil
import com.example.timeflex.databinding.ActivityMainUserBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivityUser : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    private lateinit var binding: ActivityMainUserBinding
    private var firebaseAuth : FirebaseAuth?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigationView.setNavigationItemSelectedListener(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        replaceFragment(FragmentInicio())
        binding.navigationView.setCheckedItem(R.id.op_inicio)
    }

    private fun cerrarSesion(){
        firebaseAuth!!.signOut()
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        finish()
        Toast.makeText(applicationContext,"Has cerrado la sesión", Toast.LENGTH_SHORT).show()
    }

    private fun comprobarSesion() {
        if(firebaseAuth!!.currentUser==null){
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            Toast.makeText(applicationContext, "Usuario no registrado", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(applicationContext, "Usuario en Linea", Toast.LENGTH_SHORT).show()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navFragment, fragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.op_inicio->{
                replaceFragment(FragmentInicio())
            }
            R.id.op_perfil->{
                replaceFragment(FragmentPerfil())
            }
            R.id.op_configuracion->{
                replaceFragment(FragmentConfiguracion())
            }
            R.id.op_cerrar_sesion->{
                cerrarSesion()
            }
            R.id.op_apps->{
                replaceFragment(FragmentApps())
            }
            R.id.op_estadisticas->{
                replaceFragment(FragmentEstadisticas())
            }
            R.id.op_notas->{
                replaceFragment(FragmentNotas())
            }
            R.id.op_explorar->{
                replaceFragment(FragmentExplorar())
            }
            R.id.op_social->{
                replaceFragment(FragmentSocial())
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}