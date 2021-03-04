/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.isActive

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    var isRunning by mutableStateOf(false)

    var hours by mutableStateOf(0)
    var minutes by mutableStateOf(1)
    var seconds by mutableStateOf(0)

    LaunchedEffect(key1 = Unit) {
        while (isActive) {
            withInfiniteAnimationFrameMillis {
                if (isRunning) {
                    Thread.sleep(1000)
                    when {
                        seconds > 0 -> {
                            seconds--
                        }
                        minutes > 0 -> {
                            minutes--
                            seconds = 59
                        }
                        hours > 0 -> {
                            hours--
                            minutes = 59
                            seconds = 60
                        }
                        else -> isRunning = false
                    }
                }
            }
        }
        isRunning = false
    }

    Surface(color = MaterialTheme.colors.background) {
        Scaffold(

            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.primaryVariant
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier,
                            text = "Timer"
                        )
                    }
                }
            },

            content = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        Text(
                            text = "$hours:$minutes:$seconds",
                            color = MaterialTheme.colors.primaryVariant,
                            style = MaterialTheme.typography.h1
                        )
                    }
                    Row(
                        modifier = Modifier.padding(top = 15.dp, bottom = 100.dp)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.width(100.dp),
                            value = "$hours",
                            label = { Text(text = "Hours") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            enabled = !isRunning,
                            onValueChange = {
                                try {
                                    hours = it.toInt()
                                } catch (exception: Exception) {
                                }
                            }
                        )
                        OutlinedTextField(
                            modifier = Modifier.width(100.dp),
                            value = "$minutes",
                            label = { Text(text = "Minutes") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            enabled = !isRunning,
                            onValueChange = {
                                try {
                                    minutes = it.toInt()
                                } catch (exception: Exception) {
                                }
                            }
                        )
                        OutlinedTextField(
                            modifier = Modifier.width(100.dp),
                            value = "$seconds",
                            label = { Text(text = "Seconds") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            enabled = !isRunning,
                            onValueChange = {
                                try {
                                    seconds = it.toInt()
                                } catch (exception: Exception) {
                                }
                            }
                        )
                    }
                }
            },

            bottomBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { isRunning = true }) {
                        Text(text = "Start")
                    }
                    Button(onClick = { isRunning = false }) {
                        Text(text = "Stop")
                    }
                }
            }

        )
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
