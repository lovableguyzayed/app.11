package com.retro.calculator.ui.screens.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.retro.calculator.ui.components.RobotCharacter
import com.retro.calculator.ui.components.RobotAnimationType
import com.retro.calculator.ui.theme.*

@Composable
fun CalculatorScreen() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(BgCard, BgDark, BgDark),
                    radius = 1000f
                )
            )
    ) {
        // Left Panel - Robot and Chat
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Text(
                text = "RETRO CALCULATOR",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                ),
                color = Accent,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "QUANTITY × RATE = TOTAL",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                ),
                color = Primary,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Robot Character
            RobotCharacter(
                modifier = Modifier.size(160.dp),
                isAnimating = true,
                animationType = RobotAnimationType.IDLE
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Chat Bubble
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(horizontal = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Primary
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Welcome! Let's calculate some unit prices together. Start by entering a quantity.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 11.sp,
                            lineHeight = 14.sp
                        ),
                        color = androidx.compose.ui.graphics.Color.White,
                        textAlign = TextAlign.Center,
                        maxLines = 4,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
        
        // Right Panel - Calculator Interface
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(BgCard, BgDark)
                    )
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Calculator Interface",
                style = MaterialTheme.typography.headlineLarge,
                color = Primary,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Coming Soon!",
                style = MaterialTheme.typography.bodyLarge,
                color = Accent,
                textAlign = TextAlign.Center
            )
        }
    }
}