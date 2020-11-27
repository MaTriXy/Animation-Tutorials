package com.smarttoolfactory.tutorial3_1transitions

import androidx.annotation.DrawableRes

/**
 * Holds the image resource references used by the grid and the pager fragments.
 */
object ImageData {

    @DrawableRes
    val IMAGE_DRAWABLES = intArrayOf(
        R.drawable.landscape1,
        R.drawable.landscape2,
        R.drawable.landscape3,
        R.drawable.landscape4,
        R.drawable.landscape5,
        R.drawable.landscape6,
        R.drawable.landscape7,
        R.drawable.landscape8,
        R.drawable.landscape9,
        R.drawable.landscape10
    )


    @DrawableRes
    val MAGAZINE_DRAWABLES = intArrayOf(
        R.drawable.mag1,
        R.drawable.mag2,
        R.drawable.mag3,
        R.drawable.mag4,
        R.drawable.mag5,
        R.drawable.mag6,
        R.drawable.mag7,
    )

    fun getDrawableRes(userId: Int): Int {
        return when {
            userId % 6 == 0 -> {
                R.drawable.avatar_1_raster
            }
            userId % 6 == 1 -> {
                R.drawable.avatar_2_raster
            }
            userId % 6 == 2 -> {
                R.drawable.avatar_3_raster
            }
            userId % 6 == 3 -> {
                R.drawable.avatar_4_raster
            }
            userId % 6 == 4 -> {
                R.drawable.avatar_5_raster
            }
            else -> {
                R.drawable.avatar_6_raster
            }
        }
    }
}