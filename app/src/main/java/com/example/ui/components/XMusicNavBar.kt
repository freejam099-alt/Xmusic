package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AppleMusicBorder
import com.example.ui.theme.AppleMusicRed
import com.example.ui.theme.AppleMusicTextSecondary
import com.example.ui.theme.SoftIcons

data class NavItem(
    val id: String,
    val title: String,
    val icon: ImageVector
)

@Composable
fun XMusicNavBar(
    currentTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavItem("home", "Dengarkan", SoftIcons.Home),
        NavItem("explore", "Jelajahi", SoftIcons.Explore),
        NavItem("search", "Cari", SoftIcons.Search),
        NavItem("library", "Koleksi", SoftIcons.Library),
        NavItem("settings", "Pengaturan", SoftIcons.Settings)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        // Apple Music Frosted Floating Tab Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(32.dp),
                    spotColor = Color(0x80000000),
                    ambientColor = Color(0x60000000)
                )
                .clip(RoundedCornerShape(32.dp))
                .background(Color(0xEE1C1C1E))
                .border(
                    width = 0.8.dp,
                    color = AppleMusicBorder,
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            items.forEach { item ->
                val isSelected = currentTab == item.id

                val tintColor by animateColorAsState(
                    targetValue = if (isSelected) AppleMusicRed else AppleMusicTextSecondary,
                    animationSpec = spring(dampingRatio = 0.8f, stiffness = 400f),
                    label = "tab_tint"
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onTabSelected(item.id)
                        }
                        .padding(vertical = 4.dp)
                        .testTag("nav_item_${item.id}"),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = tintColor,
                        modifier = Modifier.size(22.dp)
                    )

                    Text(
                        text = item.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color = tintColor,
                        modifier = Modifier.padding(top = 2.dp),
                        maxLines = 1
                    )
                }
            }
        }
    }
}
