package com.ellies.composeapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.ellies.composeapplication.ui.theme.ComposeApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val taskDao = TaskDatabase.getInstance(applicationContext).taskDao
        val vmf = MyViewModelFactory(taskDao)
        setContent {
            ComposeApplicationTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainNavigation(vmf)
                }
            }
        }
    }
}

/*@Preview
@Composable
fun PreviewTaskEditor(){
    TaskEditor()
}*/
