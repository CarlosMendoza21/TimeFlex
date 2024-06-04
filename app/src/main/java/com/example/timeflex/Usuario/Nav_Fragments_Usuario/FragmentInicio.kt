package com.example.timeflex.Usuario.Nav_Fragments_Usuario

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.timeflex.R
import com.example.timeflex.Usuario.Bottom_Nav_Fragments_Usuario.FragmentApps
import com.example.timeflex.Usuario.Bottom_Nav_Fragments_Usuario.FragmentEstadisticas
import com.example.timeflex.Usuario.Bottom_Nav_Fragments_Usuario.FragmentExplorar
import com.example.timeflex.Usuario.Bottom_Nav_Fragments_Usuario.FragmentNotas
import com.example.timeflex.Usuario.Bottom_Nav_Fragments_Usuario.FragmentSocial
import com.example.timeflex.databinding.FragmentInicioBinding

class FragmentInicio : Fragment() {

    private lateinit var binding: FragmentInicioBinding
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentInicioBinding.inflate(inflater, container, false)

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
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
            true
        }

        replaceFragment(FragmentApps())
        binding.bottomNavigation.selectedItemId = R.id.op_apps

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.bottomFragment, fragment)
            .commit()
    }
}