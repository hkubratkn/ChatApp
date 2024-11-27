/*
 * Copyright (C) 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kapirti.pomodorotechnique_timemanagementmethod.common.composable

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

@Composable
private fun arrowIcon(ltrIcon: ImageVector, rtlIcon: ImageVector): ImageVector =
    if (LocalLayoutDirection.current == LayoutDirection.Ltr) ltrIcon else rtlIcon

@Composable
fun arrowBackIcon() = arrowIcon(
    ltrIcon = Icons.AutoMirrored.Outlined.ArrowBack, rtlIcon = Icons.AutoMirrored.Outlined.ArrowForward
)

object PomodoroIcons {
    val ArrowBack = Icons.AutoMirrored.Rounded.ArrowBack
    val Camera = Icons.Default.CameraAlt
    //    val gg = Icons.Default.Came
    val Search = Icons.Default.Search
    var MoreVert = Icons.Default.MoreVert
    val Menu = Icons.Default.Menu
    val Message = Icons.AutoMirrored.Filled.Message
    var Call = Icons.Filled.Call
    var VideoCall = Icons.Filled.VideoCall
    var Mic = Icons.Filled.Mic
    val VideoLibrary = Icons.Default.VideoLibrary
    val Close = Icons.Default.Close
    val Timeline = Icons.Default.Timeline
    val Add = Icons.Default.Add
    val Home = Icons.Filled.Home
    val Operator = Icons.Default.AccountBox
    val ConstructionMachinery = Icons.Default.DirectionsCar
    val HeavyEquipment = Icons.Default.Agriculture
    val Flag = Icons.Default.Flag
    val Bookmarks = Icons.Default.Bookmarks
    val Bookmark = Icons.Filled.Bookmark
    val BookmarkBorder = Icons.Filled.BookmarkBorder
    val Favorite = Icons.Default.Favorite
    val Fire = Icons.Filled.LocalFireDepartment
    val Language = Icons.Default.Language
    val Hotel = Icons.Default.Hotel
    val Restaurant = Icons.Default.Restaurant
    val Account = Icons.Default.AccountCircle
    val Settings = Icons.Default.Settings
    val Report = Icons.Default.Report
    // val follow = Icons.AutoMirrored.Filled.FollowTheSigns

    val Email = Icons.Default.Email
    val Visibility = Icons.Default.Visibility
    val VisibilityOff = Icons.Default.VisibilityOff
    val Lock = Icons.Default.Lock
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}
