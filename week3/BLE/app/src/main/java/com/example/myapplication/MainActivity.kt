package com.example.myapplication

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var bluetoothLeScanner: BluetoothLeScanner

    // 권한 요청 코드
    private val REQUEST_PERMISSIONS = 2

    // ActivityResultLauncher 선언
    private lateinit var enableBluetoothLauncher: ActivityResultLauncher<Intent>

    // BLE 스캔 시간 (밀리초)
    private val SCAN_PERIOD: Long = 30000 // 30 seconds

    // CountDownTimer 객체
    private var scanTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // BluetoothAdapter 초기화
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner

        // ActivityResultLauncher 초기화
        enableBluetoothLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    checkPermissionsAndStartScan()
                } else {
                    Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_SHORT).show()
                }
            }

        // 블루투스 활성화 확인 및 요청
        if (!bluetoothAdapter.isEnabled) {
            showEnableBluetoothDialog()
        } else {
            checkPermissionsAndStartScan()
        }

        // 버튼 클릭 리스너 설정
        binding.btnStartScan.setOnClickListener {
            if (checkPermissions()) {
                startBleScan()
            } else {
                requestPermissions()
            }
        }

        binding.btnStopScan.setOnClickListener {
            stopBleScan()
        }

        // Clear 버튼 클릭 리스너 설정
        binding.btnClear.setOnClickListener {
            binding.layoutDevices.removeAllViews()
        }
    }

    // 블루투스 활성화 다이얼로그 표시
    private fun showEnableBluetoothDialog() {
        AlertDialog.Builder(this)
            .setTitle("Enable Bluetooth")
            .setMessage("이 앱이 제대로 작동하려면 Bluetooth를 활성화해야 합니다.\n지금 블루투스를 활성화하시겠습니까?")
            .setPositiveButton("Yes") { _, _ ->
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                enableBluetoothLauncher.launch(enableBtIntent)
            }
            .setNegativeButton("No") { _, _ ->
                Toast.makeText(
                    this,
                    "Bluetooth is disabled. App may not work correctly.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setCancelable(false)
            .show()
    }

    // 권한 확인 및 요청
    private fun checkPermissionsAndStartScan() {
        if (checkPermissions()) {
            startBleScan()
        } else {
            requestPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        val permissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions.add(Manifest.permission.BLUETOOTH)
            permissions.add(Manifest.permission.BLUETOOTH_ADMIN)
            permissions.add(Manifest.permission.BLUETOOTH_SCAN)
            permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
        }

        return permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        val permissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions.add(Manifest.permission.BLUETOOTH)
            permissions.add(Manifest.permission.BLUETOOTH_ADMIN)
            permissions.add(Manifest.permission.BLUETOOTH_SCAN)
            permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
        }

        ActivityCompat.requestPermissions(this, permissions.toTypedArray(), REQUEST_PERMISSIONS)
    }

    // BLE 스캔 시작
    private fun startBleScan() {
        try {
            if (checkPermissions()) {
                bluetoothLeScanner.startScan(leScanCallback)
                binding.tvStatus.text = "Scanning..."
                startScanTimer()
            } else {
                Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SecurityException) {
            Toast.makeText(this, "Permission denied for BLE scan", Toast.LENGTH_SHORT).show()
        }
    }

    // BLE 스캔 중지
    private fun stopBleScan() {
        try {
            if (checkPermissions()) {
                bluetoothLeScanner.stopScan(leScanCallback)
                binding.tvStatus.text = "Scan stopped"
                stopScanTimer()
            } else {
                Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SecurityException) {
            Toast.makeText(this, "Permission denied for stopping BLE scan", Toast.LENGTH_SHORT)
                .show()
        }
    }

    // BLE 스캔 타이머 시작
    private fun startScanTimer() {
        scanTimer?.cancel() // 기존 타이머 취소
        scanTimer = object : CountDownTimer(SCAN_PERIOD, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // 타이머 갱신
                val secondsRemaining = millisUntilFinished / 1000
                binding.tvStatus.text = "Scanning... Time remaining: $secondsRemaining seconds"
            }

            override fun onFinish() {
                // 타이머 종료 시 스캔 중지
                stopBleScan()
            }
        }.start()
    }

    // BLE 스캔 타이머 종료
    private fun stopScanTimer() {
        scanTimer?.cancel()
        scanTimer = null
    }

    // BLE 스캔 콜백
    private val leScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            try {
                result?.device?.let { device ->
                    // 디바이스 정보를 로그에 출력
                    val deviceInfo = "Found device: ${device.name}, ${device.address}"
                    Log.d("TAG", deviceInfo)
                    binding.layoutDevices.addView(TextView(this@MainActivity).apply {
                        text = deviceInfo
                    })
                }
            } catch (e: SecurityException) {
                Toast.makeText(
                    this@MainActivity,
                    "Permission denied for stopping BLE scan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            println("Scan failed with error: $errorCode")
        }
    }

    // 권한 요청 결과 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                startBleScan()
            } else {
                Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 액티비티 종료 시 타이머 정리
    override fun onDestroy() {
        super.onDestroy()
        stopScanTimer()
    }
}