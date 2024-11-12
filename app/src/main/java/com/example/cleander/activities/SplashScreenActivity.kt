package com.example.cleander.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cleander.R
import com.example.cleander.model.Album
import com.example.cleander.ui.theme.CleanderTheme
import com.example.cleander.ui.theme.DetailSecondary
import com.example.cleander.ui.theme.Primary
import com.example.cleander.ui.theme.PrimaryVariant
import com.example.cleander.ui.theme.Secondary
import com.example.cleander.utils.DraggableCard
import com.example.cleander.utils.MultiStateAnimationCircleFilledCanvas
import com.example.cleander.utils.verticalGradientBackground
import com.example.cleander.viewmodel.DatingViewModel
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            CleanderTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    DatingHomeScreen()
                }
            }
        }
    }
}

@Composable
fun DatingHomeScreen() {
    val viewModel = DatingViewModel()
    val photos = viewModel.albumLiveData.observeAsState()

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val cardHeight = screenHeight - 200.dp

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(

            modifier = Modifier.verticalGradientBackground(
                listOf(
                    Primary,
                    Primary
                )
            )
        ) {
            val listEmpty = remember { mutableStateOf(false) }
            DatingLoader(modifier = Modifier)
            photos.value?.forEachIndexed { index, album ->
                DraggableCard(
                    item = album,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cardHeight)
                        .padding(
                            top = 16.dp + (index + 2).dp,
                            bottom = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    onSwiped = { _, swipedAlbum ->
                        if (photos.value?.isNotEmpty()?.or(false) == true) {
                            photos.value?.remove(swipedAlbum)
                            if (photos.value?.isEmpty()?.or(false) == true) {
                                listEmpty.value = true
                            }
                        }
                    }
                ) {
                    CardContent(album)
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = cardHeight)
                    .alpha(animateFloatAsState(if (listEmpty.value) 0f else 1f, label = "").value)
            ) {
                IconButton(
                    onClick = {
                        /* TODO Hook to swipe event */
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(PrimaryVariant)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = DetailSecondary,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                }
                IconButton(
                    onClick = {
                        /* TODO Hook to swipe event */
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(PrimaryVariant)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Secondary,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CardContent(album: Album) {
    Column {
        Image(
            painter = painterResource(album.imageId),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.weight(1f)
        )
        Row(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
            Text(
                text = album.date,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.Outlined.Place,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                tint = Secondary,
                contentDescription = null
            )
            Text(
                text = "${Random.nextInt(1, 15)} km",
                style = MaterialTheme.typography.bodyMedium,
                color = Secondary
            )
        }

    }
}

@Composable
fun DatingLoader(modifier: Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier
            .fillMaxSize()
            .clip(CircleShape)
    ) {
        MultiStateAnimationCircleFilledCanvas(Secondary, 400f)
        Image(
            painter = painterResource(id = R.mipmap.designer),
            modifier = modifier
                .size(60.dp)
                .clip(CircleShape),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }
}