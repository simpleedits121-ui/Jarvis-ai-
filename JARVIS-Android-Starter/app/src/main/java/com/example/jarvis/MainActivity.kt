package com.example.jarvis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jarvis.core.JarvisCore
import com.example.jarvis.core.Message

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme(
                primary = Color(0xFF63D8FF),
                background = Color(0xFF07111D),
                surface = Color(0xFF101F30)
            )) {
                JarvisScreen()
            }
        }
    }
}

@Composable
private fun JarvisScreen() {
    val core = remember { JarvisCore() }
    val messages = remember {
        mutableStateListOf(
            Message(
                fromUser = false,
                text = "Hello. I'm JARVIS v0.1. My protected core is online. " +
                    "This starter currently uses a demo response engine."
            )
        )
    }
    var draft by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF07111D)) {
        Column(
            modifier = Modifier.fillMaxSize().padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(48.dp).background(Color(0xFF123D55), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("J", color = Color(0xFF63D8FF), fontSize = 25.sp, fontWeight = FontWeight.Bold)
                }
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Text("JARVIS", fontSize = 23.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("LOCAL-FIRST AI COMPANION · v0.1", fontSize = 10.sp, color = Color(0xFF8CA9BF))
                }
            }

            Surface(
                color = Color(0xFF10263A),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("CORE STATUS", color = Color(0xFF63D8FF), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    Text("Protected core  •  Demo engine  •  Offline UI", color = Color.White, fontSize = 13.sp)
                    Text("Voice, avatar, local model and device actions are not connected yet.",
                        color = Color(0xFF9CB3C5), fontSize = 11.sp)
                }
            }

            Text("CONVERSATION", color = Color(0xFF8CA9BF), fontSize = 11.sp, fontWeight = FontWeight.Bold)

            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(messages) { message ->
                    MessageBubble(message)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().background(Color(0xFF102033), RoundedCornerShape(24.dp))
                    .padding(start = 16.dp, end = 6.dp, top = 6.dp, bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = draft,
                    onValueChange = { draft = it },
                    modifier = Modifier.weight(1f).padding(vertical = 10.dp),
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 14.sp),
                    decorationBox = { inner ->
                        Box {
                            if (draft.isEmpty()) Text("Talk to JARVIS...", color = Color(0xFF7790A5), fontSize = 14.sp)
                            inner()
                        }
                    }
                )
                Button(
                    onClick = {
                        val prompt = draft.trim()
                        if (prompt.isNotEmpty()) {
                            messages.add(Message(fromUser = true, text = prompt))
                            messages.add(Message(fromUser = false, text = core.respondToDemoMessage(prompt)))
                            draft = ""
                        }
                    },
                    shape = CircleShape,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Send message")
                }
            }
        }
    }
}

@Composable
private fun MessageBubble(message: Message) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.fromUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            color = if (message.fromUser) Color(0xFF164E68) else Color(0xFF142437),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(0.88f)
        ) {
            Column(Modifier.padding(13.dp)) {
                Text(
                    if (message.fromUser) "YOU" else "JARVIS",
                    color = if (message.fromUser) Color(0xFF9CE6FF) else Color(0xFF63D8FF),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(message.text, color = Color.White, fontSize = 14.sp)
            }
        }
    }
}
