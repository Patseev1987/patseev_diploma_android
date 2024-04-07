package ru.bogdan.patseev_diploma.presenter

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import ru.bogdan.patseev_diploma.R
import ru.bogdan.patseev_diploma.databinding.FragmentCameraBinding
import java.util.concurrent.Executor

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private val launcher by lazy {
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.all { it }) {
                startCamera(binding, executor)
            } else {
                Toast.makeText(
                    this.requireContext(),
                    "Permissions is not granted",
                    Toast.LENGTH_SHORT
                ).show()
                this@CameraFragment.requireActivity().onBackPressed()
            }
        }
    }

    private var imageCapture: ImageCapture? = null

    private lateinit var executor: Executor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        executor = this.requireContext().mainExecutor
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        checkPermissions()
        return binding.root
    }


    private fun checkPermissions() {
        val isAlLGranted = REQUEST_PERMISSIONS.all { permission ->
            (ContextCompat.checkSelfPermission(this.requireContext(), permission)
                    == PackageManager.PERMISSION_GRANTED)
        }
        if (isAlLGranted) {
            Toast.makeText(
                this.requireContext(),
                "Permission is granted",
                Toast.LENGTH_SHORT
            ).show()
            startCamera(binding, executor)
        } else {
            launcher.launch(REQUEST_PERMISSIONS)
        }
    }


    private fun startCamera(binding: FragmentCameraBinding, executor: Executor) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this.requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.cameraViewFinder.surfaceProvider)
            imageCapture = ImageCapture.Builder().build()

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                this,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                imageCapture,
            )
        }, executor)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    fun scanning(): GmsBarcodeScanner {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()

        val scanner = GmsBarcodeScanning.getClient(this.requireContext(),options)

       return scanner
    }



    companion object {
        private val REQUEST_PERMISSIONS: Array<String> = buildList {
            add(Manifest.permission.CAMERA)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
    }

}