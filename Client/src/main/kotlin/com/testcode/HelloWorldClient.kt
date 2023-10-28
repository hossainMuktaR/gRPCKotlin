package com.testcode

import com.testcode.helloworld.GreeterGrpcKt
import com.testcode.helloworld.Hello
import com.testcode.helloworld.helloRequest
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.io.Closeable
import java.util.concurrent.TimeUnit

class HelloWorldClient (private val channel: ManagedChannel): Closeable {
    private val stub: GreeterGrpcKt.GreeterCoroutineStub = GreeterGrpcKt.GreeterCoroutineStub(channel)

    suspend fun greet(name: String) {
        val request = helloRequest { this.name = name }
        val response = stub.sayHello(request)
        println("Received: ${response.message}")
    }

    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}
suspend fun main(args: Array<String>) {
    val port = 50051
    val channel = ManagedChannelBuilder.forAddress("localhost", port)
        .usePlaintext()
        .build()
    val client = HelloWorldClient(channel)
    val user = args.singleOrNull() ?: "Your Name"
    client.greet(user)
}