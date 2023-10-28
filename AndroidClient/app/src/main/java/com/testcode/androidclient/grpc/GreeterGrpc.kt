package com.testcode.androidclient.grpc

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import com.testcode.helloworld.GreeterGrpcKt
import com.testcode.helloworld.helloRequest
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import java.io.Closeable

class GreeterGrpc(uri: Uri): Closeable {
    val responseState = mutableStateOf("")
    private val channel = let {
        println("Connecting to ${uri.host}:${uri.port}")
        val builder = ManagedChannelBuilder.forAddress(uri.host, uri.port)
        if(uri.scheme == "https") {
            builder.useTransportSecurity()
        }else {
            builder.usePlaintext()
        }
        builder.executor(Dispatchers.IO.asExecutor()).build()
    }
    private val greeter = GreeterGrpcKt.GreeterCoroutineStub(channel)
    suspend fun sayHello(name: String) {
        try {
            val request = helloRequest { this.name = name }
            val response = greeter.sayHello(request)
            responseState.value = response.message
        }catch (e: Exception) {
            responseState.value = e.message ?: "unknown Error"
            e.printStackTrace()
        }
    }

    override fun close() {
        channel.shutdown()
    }
}