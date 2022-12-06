package com.kodex.rednavigation.utils

import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat

class Constants {
    object Screens{
        const val SPLASH_SCREEN = "splash_screen"
        const val MAIN_SCREEN = "main_screen"
        const val FOOD_SCREEN = "food_screen"
        const val MUSIC_SCREEN = "music_screen"
        const val DETAIL_SCREEN = "detail_screen"
        const val MOVIES_SCREEN = "movies_screen"
    }
}

@Composable
fun HtmlText(html: String, modifier : Modifier = Modifier){
    AndroidView(
        modifier = Modifier,
        factory = { context -> TextView(context) },
        update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) }
    )

}
