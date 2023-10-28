package com.testcode.androidclient

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.testcode.androidclient.grpc.GreeterGrpc
import com.testcode.androidclient.presentation.Greeter
import com.testcode.androidclient.ui.theme.AndroidClientTheme

class MainActivity : ComponentActivity() {
    private val uri by lazy { Uri.parse("http://192.168.0.101:50051/") }
    private val greeterService by lazy { GreeterGrpc(uri) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidClientTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeter(greeterService)
                }
            }
        }
    }
}

