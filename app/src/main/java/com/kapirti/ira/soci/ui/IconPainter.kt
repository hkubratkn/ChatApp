package com.kapirti.ira.soci.ui

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.IconCompat
import com.google.accompanist.drawablepainter.rememberDrawablePainter

/**
 * Creates and remembers a [Painter] from a bitmap icon specified by the [contentUri].
 */
@Composable
fun rememberIconPainter(contentUri: Uri): Painter {
    val icon = IconCompat.createWithAdaptiveBitmapContentUri(contentUri)
    val context = LocalContext.current
    return rememberDrawablePainter(drawable = icon.loadDrawable(context))
}
