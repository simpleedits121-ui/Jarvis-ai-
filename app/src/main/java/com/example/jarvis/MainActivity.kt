
package com.example.jarvis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jarvis.core.JarvisCore
import com.example.jarvis.core.Message

private val Background = Color(0xFF07111D)
private val Panel = Color(0xFF101F30)
private val Cyan = Color(0xFF63D8FF)
private val Muted = Color(0xFF8CA9BF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Cyan,
                    background = Background,
                    surface = Panel
                )
            ) {
                JarvisDesktop()
            }
        }
    }
}

@Composable
private fun JarvisDesktop() {
    var selected by remember { mutableStateOf("Home") }
    var menuOpen by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(10.dp)
    ) {
        // Compact desktop sidebar
        Column(
            modifier = Modifier
                .width(66.dp)
                .fillMaxHeight()
                .background(Panel, RoundedCornerShape(18.dp))
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("J", color = Cyan, fontSize = 30.sp,
                fontWeight = FontWeight.Bold)
            HorizontalDivider(color = Color(0xFF263B4D))

            listOf("Home", "Chat", "Models", "Tools", "Settings").forEach {
                nav ->
                NavigationTile(
                    label = nav,
                    selected = selected == nav,
                    onClick = { selected = nav }
                )
            }

            Spacer(Modifier.weight(1f))
            Text("v0.2", color = Muted, fontSize = 10.sp)
        }

        Spacer(Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            // Desktop title bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Panel, RoundedCornerShape(14.dp))
                    .padding(horizontal = 12.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text("JARVIS WORKSPACE",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold)
                    Text("LOCAL DESKTOP COMPANION",
                        color = Muted, fontSize = 9.sp)
                }
                Box {
                    TextButton(onClick = { menuOpen = true }) {
                        Text("•••", color = Cyan, fontSize = 18.sp)
                    }
                    DropdownMenu(
                        expanded = menuOpen,
                        onDismissRequest = { menuOpen = false }
                    ) {
                        listOf("Home", "Chat", "Models", "Tools", "Settings")
                            .forEach { page ->
                                DropdownMenuItem(
                                    text = { Text(page) },
                                    onClick = {
                                        selected = page
                                        menuOpen = false
                                    }
                                )
                            }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            when (selected) {
                "Chat" -> ChatPanel()
                "Models" -> InfoPanel(
                    "MODEL MANAGER",
                    "Local model installation and switching will be added here."
                )
                "Tools" -> InfoPanel(
                    "DEVICE TOOLS",
                    "Camera, app launching, calling and typing controls will be added here."
                )
                "Settings" -> InfoPanel(
                    "SETTINGS",
                    "Companion size, position, voice and appearance settings will be added here."
                )
                else -> HomePanel(onOpenChat = { selected = "Chat" })
            }
        }
    }
}

@Composable
private fun NavigationTile(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val symbol = when (label) {
        "Home" -> "⌂"
        "Chat" -> "◉"
        "Models" -> "▤"
        "Tools" -> "⚙"
        else -> "☰"
    }

    Surface(
        onClick = onClick,
        color = if (selected) Color(0xFF16445B) else Color.Transparent,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.size(54.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(symbol, color = Cyan, fontSize = 21.sp)
            Text(label, color = if (selected) Color.White else Muted,
                fontSize = 8.sp)
        }
    }
}

@Composable
private fun HomePanel(onOpenChat: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Companion stage: placeholder for future 3D character
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color(0xFF0B1A2A),
            shape = RoundedCornerShape(22.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color(0xFF123D55), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("J", color = Cyan, fontSize = 56.sp,
                        fontWeight = FontWeight.Light)
                }

                Spacer(Modifier.height(14.dp))
                Text("YOUR COMPANION",
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(5.dp))
                Text(
    "Anime companion · Local character engine",
    color = Muted,
    fontSize = 11.sp
)
                Spacer(Modifier.height(16.dp))

                Surface(
                    color = Color(0xFF102E36),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("●  SYSTEM ONLINE",
                        color = Cyan,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(
                            horizontal = 14.dp, vertical = 9.dp
                        )
                    )
                }
            }
        }

        Surface(
            color = Panel,
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(14.dp)) {
                Text("QUICK ACCESS",
                    color = Muted, fontSize = 10.sp,
                    fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(10.dp))
                Button(
                    onClick = onOpenChat,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Open JARVIS Chat")
                }
                Text(
                    "Local AI and device actions are not connected yet.",
                    color = Muted, fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
private fun InfoPanel(title: String, description: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Panel,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(title, color = Cyan, fontSize = 16.sp,
                fontWeight = FontWeight.Bold)
            Text(description, color = Color.White, fontSize = 13.sp)
        }
    }
}

@Composable
private fun ChatPanel() {
    val core = remember { JarvisCore() }
    val messages = remember {
        mutableStateListOf(
            Message(
                fromUser = false,
                text = "Hello. I'm JARVIS. My current response engine is a demo."
            )
        )
    }
    var draft by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("CONVERSATION", color = Cyan, fontSize = 12.sp,
            fontWeight = FontWeight.Bold)

        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages) { message ->
                Surface(
                    color = if (message.fromUser)
                        Color(0xFF164E68) else Panel,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(if (message.fromUser) "YOU" else "JARVIS",
                            color = Cyan, fontSize = 10.sp,
                            fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(4.dp))
                        Text(message.text, color = Color.White,
                            fontSize = 13.sp)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Panel, RoundedCornerShape(22.dp))
                .padding(start = 14.dp, end = 5.dp, top = 5.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = draft,
                onValueChange = { draft = it },
                modifier = Modifier.weight(1f).padding(vertical = 10.dp),
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = Color.White, fontSize = 14.sp
                ),
                decorationBox = { inner ->
                    Box {
                        if (draft.isEmpty()) {
                            Text("Talk to JARVIS...",
                                color = Muted, fontSize = 13.sp)
                        }
                        inner()
                    }
                }
            )

            IconButton(onClick = {
                val prompt = draft.trim()
                if (prompt.isNotEmpty()) {
                    messages.add(Message(fromUser = true, text = prompt))
                    messages.add(
                        Message(
                            fromUser = false,
                            text = core.respondToDemoMessage(prompt)
                        )
                    )
                    draft = ""
                }
            }) {
                Icon(Icons.Default.Send, contentDescription = "Send",
                    tint = Cyan)
            }
        }
    }
}
