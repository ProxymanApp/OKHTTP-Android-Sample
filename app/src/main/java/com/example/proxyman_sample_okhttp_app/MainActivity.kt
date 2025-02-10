package com.example.proxyman_sample_okhttp_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.proxyman_sample_okhttp_app.ui.theme.Proxyman_Sample_OKHTTP_AppTheme
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import androidx.compose.ui.unit.dp
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class MainActivity : ComponentActivity() {
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Proxyman_Sample_OKHTTP_AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    var responseText by remember { mutableStateOf("No request made yet") }
    
    Column(modifier = modifier.padding(16.dp)) {
        Button(onClick = { 
            makeGetRequest { response ->
                responseText = response
            }
        }) {
            Text("Make GET Request")
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Button(onClick = { 
            makePostRequest { response ->
                responseText = response
            }
        }) {
            Text("Make POST Request")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(text = responseText)
    }
}

private fun makeGetRequest(onResponse: (String) -> Unit) {
    val request = Request.Builder()
        .url("https://httpbin.org/get")
        .build()

    OkHttpClient().newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            onResponse("Error: ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                onResponse(it.body?.string() ?: "No response body")
            }
        }
    })
}

private fun makePostRequest(onResponse: (String) -> Unit) {
    val jsonBody = JSONObject().apply {
        put("message", "Hello from Android!")
    }

    val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaType())

    val request = Request.Builder()
        .url("https://httpbin.org/post")
        .post(requestBody)
        .build()

    OkHttpClient().newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            onResponse("Error: ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                onResponse(it.body?.string() ?: "No response body")
            }
        }
    })
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Proxyman_Sample_OKHTTP_AppTheme {
        Greeting("Android")
    }
}