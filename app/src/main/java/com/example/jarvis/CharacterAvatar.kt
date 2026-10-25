
package com.example.jarvis

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CharacterAvatar(
    state: CharacterState,
    modifier: Modifier = Modifier
) {
    val animation = rememberInfiniteTransition(label = "character")
    val breath by animation.animateFloat(
        initialValue = 1f,
        targetValue = 1.018f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathing"
    )

    val skin = Color(0xFFFFDCCB)
    val hair = Color(0xFF593D70)
    val eye = Color(0xFF6848A0)
    val outfit = Color(0xFF485A91)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Canvas(
            modifier = Modifier
                .size(width = 220.dp, height = 250.dp)
                .scale(breath)
        ) {
            val w = size.width
            val h = size.height
            val cx = w / 2f
            val headY = h * 0.39f
            val headW = w * 0.49f
            val headH = h * 0.35f

            // Hair behind the head
            drawOval(
                color = hair,
                topLeft = androidx.compose.ui.geometry.Offset(
                    cx - headW * 0.59f, headY - headH * 0.47f
                ),
                size = androidx.compose.ui.geometry.Size(
                    headW * 1.18f, headH * 1.65f
                )
            )

            // Neck
            drawRect(
                color = skin,
                topLeft = androidx.compose.ui.geometry.Offset(
                    cx - w * 0.065f, headY + headH * 0.65f
                ),
                size = androidx.compose.ui.geometry.Size(
                    w * 0.13f, h * 0.12f
                )
            )

            // Shoulders and outfit
            val shoulders = Path().apply {
                moveTo(cx - w * 0.37f, h * 0.94f)
                quadraticTo(cx - w * 0.34f, h * 0.69f,
                    cx, h * 0.69f)
                quadraticTo(cx + w * 0.34f, h * 0.69f,
                    cx + w * 0.37f, h * 0.94f)
                close()
            }
            drawPath(shoulders, outfit)

            // Face
            drawOval(
                color = skin,
                topLeft = androidx.compose.ui.geometry.Offset(
                    cx - headW / 2f, headY - headH / 2f
                ),
                size = androidx.compose.ui.geometry.Size(headW, headH)
            )

            // Front fringe
            val fringe = Path().apply {
                moveTo(cx - headW * 0.52f, headY - headH * 0.13f)
                lineTo(cx - headW * 0.39f, headY - headH * 0.78f)
                lineTo(cx - headW * 0.03f, headY - headH * 0.49f)
                lineTo(cx + headW * 0.22f, headY - headH * 0.82f)
                lineTo(cx + headW * 0.53f, headY - headH * 0.17f)
                lineTo(cx + headW * 0.23f, headY - headH * 0.32f)
                lineTo(cx - headW * 0.04f, headY - headH * 0.19f)
                close()
            }
            drawPath(fringe, hair)

            val eyeY = headY + headH * 0.02f
            val eyeGap = headW * 0.24f
            val eyeRadius = headW * 0.065f
            val eyeOffset = if (state.mood == CharacterMood.THINKING) {
                -headW * 0.025f
            } else {
                0f
            }

            // Eyes
            for (side in listOf(-1f, 1f)) {
                val x = cx + side * eyeGap + eyeOffset
                if (state.mood == CharacterMood.SLEEPING) {
                    drawLine(
                        eye, androidx.compose.ui.geometry.Offset(
                            x - eyeRadius, eyeY
                        ),
                        androidx.compose.ui.geometry.Offset(
                            x + eyeRadius, eyeY
                        ),
                        strokeWidth = 3.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                } else {
                    drawOval(
                        color = Color.White,
                        topLeft = androidx.compose.ui.geometry.Offset(
                            x - eyeRadius, eyeY - eyeRadius * 1.3f
                        ),
                        size = androidx.compose.ui.geometry.Size(
                            eyeRadius * 2f, eyeRadius * 2.6f
                        )
                    )
                    drawCircle(
                        color = eye,
                        radius = eyeRadius * 0.72f,
                        center = androidx.compose.ui.geometry.Offset(
                            x + eyeOffset * 0.2f, eyeY
                        )
                    )
                    drawCircle(
                        color = Color.White,
                        radius = eyeRadius * 0.23f,
                        center = androidx.compose.ui.geometry.Offset(
                            x - eyeRadius * 0.2f,
                            eyeY - eyeRadius * 0.25f
                        )
                    )
                }
            }

            // Eyebrows
            val browY = eyeY - eyeRadius * 2f
            for (side in listOf(-1f, 1f)) {
                drawLine(
                    color = hair,
                    start = androidx.compose.ui.geometry.Offset(
                        cx + side * eyeGap - eyeRadius, browY
                    ),
                    end = androidx.compose.ui.geometry.Offset(
                        cx + side * eyeGap + eyeRadius, browY
                    ),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            // Blush
            if (state.mood == CharacterMood.HAPPY ||
                state.mood == CharacterMood.PLAYFUL
            ) {
                for (side in listOf(-1f, 1f)) {
                    drawCircle(
                        color = Color(0x55F06C8D),
                        radius = headW * 0.075f,
                        center = androidx.compose.ui.geometry.Offset(
                            cx + side * headW * 0.32f,
                            headY + headH * 0.27f
                        )
                    )
                }
            }

            // Mouth: expression changes with mood
            val mouthX = cx
            val mouthY = headY + headH * 0.31f

            when (state.mood) {
                CharacterMood.HAPPY, CharacterMood.PLAYFUL -> {
                    drawArc(
                        color = Color(0xFF9B4D57),
                        startAngle = 15f,
                        sweepAngle = 150f,
                        useCenter = false,
                        topLeft = androidx.compose.ui.geometry.Offset(
                            mouthX - headW * 0.13f,
                            mouthY - headH * 0.07f
                        ),
                        size = androidx.compose.ui.geometry.Size(
                            headW * 0.26f, headH * 0.19f
                        ),
                        style = Stroke(width = 3.dp.toPx())
                    )
                }

                CharacterMood.SURPRISED -> {
                    drawOval(
                        color = Color(0xFF9B4D57),
                        topLeft = androidx.compose.ui.geometry.Offset(
                            mouthX - headW * 0.045f,
                            mouthY - headH * 0.035f
                        ),
                        size = androidx.compose.ui.geometry.Size(
                            headW * 0.09f, headH * 0.13f
                        )
                    )
                }

                CharacterMood.SAD, CharacterMood.CONCERNED -> {
                    drawArc(
                        color = Color(0xFF9B4D57),
                        startAngle = 195f,
                        sweepAngle = 150f,
                        useCenter = false,
                        topLeft = androidx.compose.ui.geometry.Offset(
                            mouthX - headW * 0.11f,
                            mouthY - headH * 0.015f
                        ),
                        size = androidx.compose.ui.geometry.Size(
                            headW * 0.22f, headH * 0.12f
                        ),
                        style = Stroke(width = 3.dp.toPx())
                    )
                }

                else -> {
                    drawLine(
                        color = Color(0xFF9B4D57),
                        start = androidx.compose.ui.geometry.Offset(
                            mouthX - headW * 0.08f, mouthY
                        ),
                        end = androidx.compose.ui.geometry.Offset(
                            mouthX + headW * 0.08f, mouthY
                        ),
                        strokeWidth = 2.5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
        }

        Text(
            text = when (state.mood) {
                CharacterMood.IDLE -> "Waiting for you..."
                CharacterMood.LISTENING -> "I'm listening..."
                CharacterMood.THINKING -> "Let me think..."
                CharacterMood.HAPPY -> "Happy"
                CharacterMood.PLAYFUL -> "Oh, really?"
                CharacterMood.SAD -> "A little sad"
                CharacterMood.CONCERNED -> "Are you okay?"
                CharacterMood.SURPRISED -> "What?!"
                CharacterMood.SLEEPING -> "Zzz..."
            },
            style = MaterialTheme.typography.titleMedium
        )

        if (state.text.isNotBlank()) {
            Text(
                text = state.text,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun CharacterInteractionDemo(
    modifier: Modifier = Modifier
) {
    var state by remember { mutableStateOf(CharacterState()) }

    val engine = remember {
        CharacterEngine { newState -> state = newState }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                1.dp,
                MaterialTheme.colorScheme.outlineVariant,
                RoundedCornerShape(24.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CharacterAvatar(state = state)

        Text(
            text = "JARVIS Character Engine",
            style = MaterialTheme.typography.titleLarge
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { engine.onListening() }) {
                Text("Listen")
            }
            Button(onClick = { engine.onAiThinking() }) {
                Text("Think")
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                engine.onAiResponse(
                    CharacterReply(
                        text = "Oh, that's actually interesting!",
                        emotion = "playful",
                        gesture = "head_tilt"
                    )
                )
            }) {
                Text("Playful")
            }

            Button(onClick = {
                engine.onAiResponse(
                    CharacterReply(
                        text = "I'm here. What's wrong?",
                        emotion = "concerned"
                    )
                )
            }) {
                Text("Concerned")
            }
        }

        Button(
            onClick = { engine.onBecameIdle() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Return to idle")
        }
    }
}

