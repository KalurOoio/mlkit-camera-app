package com.mify.mlkitcameraapp.firstFragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mify.mlkitcameraapp.R
import com.mify.mlkitcameraapp.cameraActivity.CameraActivity

class FirstFragment: Fragment() {
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        {isGranted ->
            if (isGranted == true){
                startNewActivity()

            } else {


            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.first_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.Начать).setOnClickListener {
            val permission = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            )
            if (permission == PackageManager.PERMISSION_GRANTED){
                startNewActivity()
            }else {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }

        }


    }

    private fun startNewActivity() {
     val intent = Intent (requireContext(), CameraActivity::class.java)
        startActivity(intent)
    }

}



































