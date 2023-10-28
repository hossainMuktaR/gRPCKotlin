package com.testcode.androidclient.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.testcode.androidclient.grpc.GreeterGrpc
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeter(
    greeterGrpc: GreeterGrpc
) {
    val scope = rememberCoroutineScope()
    val nameState = remember {
        mutableStateOf(TextFieldValue())
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Type Your Name", modifier = Modifier.padding(top = 10.dp))
        OutlinedTextField(nameState.value, { nameState.value = it })

        Button(
            onClick = {
                scope.launch {
                    greeterGrpc.sayHello(nameState.value.text)
                }
            },
            Modifier.padding(10.dp)
        ) {
            Text(text = "Send Request")
        }
        if(greeterGrpc.responseState.value.isNotEmpty()) {
            Text(text = "Response From gRPC Server:", modifier = Modifier.padding(top = 10.dp))
            Text(greeterGrpc.responseState.value)
        }
    }
}