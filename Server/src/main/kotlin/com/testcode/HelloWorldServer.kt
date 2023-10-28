package com.testcode

import com.testcode.helloworld.GreeterGrpcKt
import com.testcode.helloworld.HelloReply
import com.testcode.helloworld.HelloRequest
import com.testcode.helloworld.helloReply
import io.grpc.Server
import io.grpc.ServerBuilder

class HelloWorldServer(private val port: Int) {
    val server: Server = ServerBuilder
        .forPort(port)
        .addService(GreeterService())
        .build()

    fun start() {
        server.start()
        println("Server started, listening on $port")
    }

    private fun stop() {
        server.shutdown()
    }
    fun blockUntilShutdown() {
        server.awaitTermination()
    }

    internal class GreeterService: GreeterGrpcKt.GreeterCoroutineImplBase() {
        override suspend fun sayHello(request: HelloRequest): HelloReply {
            return helloReply {
                message = "Hello ${request.name}"
            }
        }
    }
}

fun main() {
    val port = 50051
    val server = HelloWorldServer(port)
    server.start()
    server.blockUntilShutdown()
}