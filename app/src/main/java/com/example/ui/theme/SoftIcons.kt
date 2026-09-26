package com.example.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * Custom modern iOS 18 SF-inspired soft vector icons.
 * Crafted with soft curvatures, rounded stroke caps, and balanced geometry without default chunky slop.
 */
object SoftIcons {

    // iOS 18 Home Icon (Soft rounded roof & welcoming door)
    val Home: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSHome",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.White),
                fillAlpha = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12f, 2.5f)
                curveTo(11.4f, 2.5f, 10.8f, 2.85f, 10.4f, 3.25f)
                lineTo(3.8f, 9.1f)
                curveTo(3.25f, 9.6f, 3f, 10.3f, 3f, 11.0f)
                verticalLineTo(19.2f)
                curveTo(3f, 20.75f, 4.25f, 22.0f, 5.8f, 22.0f)
                horizontalLineTo(9.2f)
                curveTo(9.65f, 22.0f, 10.0f, 21.65f, 10.0f, 21.2f)
                verticalLineTo(15.5f)
                curveTo(10.0f, 14.4f, 10.9f, 13.5f, 12.0f, 13.5f)
                curveTo(13.1f, 13.5f, 14.0f, 14.4f, 14.0f, 15.5f)
                verticalLineTo(21.2f)
                curveTo(14.0f, 21.65f, 14.35f, 22.0f, 14.8f, 22.0f)
                horizontalLineTo(18.2f)
                curveTo(19.75f, 22.0f, 21.0f, 20.75f, 21.0f, 19.2f)
                verticalLineTo(11.0f)
                curveTo(21.0f, 10.3f, 20.75f, 9.6f, 20.2f, 9.1f)
                lineTo(13.6f, 3.25f)
                curveTo(13.2f, 2.85f, 12.6f, 2.5f, 12f, 2.5f)
                close()
            }
        }.build()
    }

    // iOS 18 Explore / Compass Icon (Slender needle with smooth dial)
    val Explore: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSExplore",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.White),
                fillAlpha = 1.0f
            ) {
                moveTo(12f, 2f)
                curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
                curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
                curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
                curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
                close()
                moveTo(12f, 20.2f)
                curveTo(7.48f, 20.2f, 3.8f, 16.52f, 3.8f, 12f)
                curveTo(3.8f, 7.48f, 7.48f, 3.8f, 12f, 3.8f)
                curveTo(16.52f, 3.8f, 20.2f, 7.48f, 20.2f, 12f)
                curveTo(20.2f, 16.52f, 16.52f, 20.2f, 12f, 20.2f)
                close()
                moveTo(14.8f, 9.2f)
                lineTo(10.1f, 11.2f)
                lineTo(9.2f, 14.8f)
                lineTo(13.9f, 12.8f)
                close()
            }
        }.build()
    }

    // iOS 18 Search Icon (Fine circular lens + smooth rounded diagonal handle)
    val Search: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSSearch",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.White),
                strokeLineWidth = 2.2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(10.5f, 18f)
                curveTo(14.64f, 18f, 18f, 14.64f, 18f, 10.5f)
                curveTo(18f, 6.36f, 14.64f, 3f, 10.5f, 3f)
                curveTo(6.36f, 3f, 3f, 6.36f, 3f, 10.5f)
                curveTo(3f, 14.64f, 6.36f, 18f, 10.5f, 18f)
                close()
                moveTo(16.0f, 16.0f)
                lineTo(21.0f, 21.0f)
            }
        }.build()
    }

    // iOS 18 Library Icon (Multi-layered soft music sheets & audio pill)
    val Library: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSLibrary",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.White),
                fillAlpha = 1.0f
            ) {
                moveTo(4.5f, 5.0f)
                curveTo(4.5f, 3.9f, 5.4f, 3.0f, 6.5f, 3.0f)
                horizontalLineTo(17.5f)
                curveTo(18.6f, 3.0f, 19.5f, 3.9f, 19.5f, 5.0f)
                curveTo(19.5f, 5.6f, 18.6f, 6.0f, 17.5f, 6.0f)
                horizontalLineTo(6.5f)
                curveTo(5.4f, 6.0f, 4.5f, 5.6f, 4.5f, 5.0f)
                close()

                moveTo(3.0f, 9.0f)
                curveTo(3.0f, 7.9f, 3.9f, 7.0f, 5.0f, 7.0f)
                horizontalLineTo(19.0f)
                curveTo(20.1f, 7.0f, 21.0f, 7.9f, 21.0f, 9.0f)
                curveTo(21.0f, 9.6f, 20.1f, 10.0f, 19.0f, 10.0f)
                horizontalLineTo(5.0f)
                curveTo(3.9f, 10.0f, 3.0f, 9.6f, 3.0f, 9.0f)
                close()

                moveTo(2.0f, 14.0f)
                curveTo(2.0f, 12.35f, 3.35f, 11.0f, 5.0f, 11.0f)
                horizontalLineTo(19.0f)
                curveTo(20.65f, 11.0f, 22.0f, 12.35f, 22.0f, 14.0f)
                verticalLineTo(18.5f)
                curveTo(22.0f, 20.45f, 20.45f, 22.0f, 18.5f, 22.0f)
                horizontalLineTo(5.5f)
                curveTo(3.55f, 22.0f, 2.0f, 20.45f, 2.0f, 18.5f)
                verticalLineTo(14.0f)
                close()
                moveTo(14.5f, 13.5f)
                lineTo(10.5f, 15.8f)
                verticalLineTo(19.0f)
                lineTo(14.5f, 17.0f)
                close()
            }
        }.build()
    }

    // iOS 18 Settings Gear (Smooth rounded tooth profile)
    val Settings: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSSettings",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.White),
                strokeLineWidth = 2.0f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(12f, 15.5f)
                curveTo(13.93f, 15.5f, 15.5f, 13.93f, 15.5f, 12f)
                curveTo(15.5f, 10.07f, 13.93f, 8.5f, 12f, 8.5f)
                curveTo(10.07f, 8.5f, 8.5f, 10.07f, 8.5f, 12f)
                curveTo(8.5f, 13.93f, 10.07f, 15.5f, 12f, 15.5f)
                close()
                moveTo(19.4f, 13.0f)
                curveTo(19.45f, 12.68f, 19.5f, 12.34f, 19.5f, 12.0f)
                curveTo(19.5f, 11.66f, 19.45f, 11.32f, 19.4f, 11.0f)
                lineTo(21.3f, 9.5f)
                lineTo(19.7f, 6.7f)
                lineTo(17.4f, 7.6f)
                curveTo(16.9f, 7.2f, 16.3f, 6.9f, 15.7f, 6.7f)
                lineTo(15.3f, 4.3f)
                horizontalLineTo(12.2f)
                horizontalLineTo(8.7f)
                lineTo(8.3f, 6.7f)
                curveTo(7.7f, 6.9f, 7.1f, 7.2f, 6.6f, 7.6f)
                lineTo(4.3f, 6.7f)
                lineTo(2.7f, 9.5f)
                lineTo(4.6f, 11.0f)
                curveTo(4.55f, 11.32f, 4.5f, 11.66f, 4.5f, 12.0f)
                curveTo(4.5f, 12.34f, 4.55f, 12.68f, 4.6f, 13.0f)
                lineTo(2.7f, 14.5f)
                lineTo(4.3f, 17.3f)
                lineTo(6.6f, 16.4f)
                curveTo(7.1f, 16.8f, 7.7f, 17.1f, 8.3f, 17.3f)
                lineTo(8.7f, 19.7f)
                horizontalLineTo(15.3f)
                lineTo(15.7f, 17.3f)
                curveTo(16.3f, 17.1f, 16.9f, 16.8f, 17.4f, 16.4f)
                lineTo(19.7f, 17.3f)
                lineTo(21.3f, 14.5f)
                close()
            }
        }.build()
    }

    // Soft Close "X" Cross Icon (Circle capsule friendly with round stroke)
    val CloseSmall: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSCloseSmall",
            defaultWidth = 20.dp,
            defaultHeight = 20.dp,
            viewportWidth = 20f,
            viewportHeight = 20f
        ).apply {
            path(
                stroke = SolidColor(Color.White),
                strokeLineWidth = 2.2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(5.5f, 5.5f)
                lineTo(14.5f, 14.5f)
                moveTo(14.5f, 5.5f)
                lineTo(5.5f, 14.5f)
            }
        }.build()
    }

    // Soft Sparkles / AI Intelligence (Subtle 4-point star for Supermix)
    val SparkleAI: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSSparkleAI",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.White)
            ) {
                moveTo(12f, 2f)
                curveTo(12f, 7.5f, 16.5f, 12f, 22f, 12f)
                curveTo(16.5f, 12f, 12f, 16.5f, 12f, 22f)
                curveTo(12f, 16.5f, 7.5f, 12f, 2f, 12f)
                curveTo(7.5f, 12f, 12f, 7.5f, 12f, 2f)
                close()
            }
        }.build()
    }

    // iOS 18 Soft Play Icon (Curved vertices and rounded apex)
    val Play: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSPlay",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.White)
            ) {
                moveTo(7.5f, 4.3f)
                curveTo(6.7f, 3.8f, 5.8f, 4.3f, 5.8f, 5.3f)
                verticalLineTo(18.7f)
                curveTo(5.8f, 19.7f, 6.7f, 20.2f, 7.5f, 19.7f)
                lineTo(19.4f, 13.0f)
                curveTo(20.2f, 12.5f, 20.2f, 11.5f, 19.4f, 11.0f)
                close()
            }
        }.build()
    }

    // iOS 18 Soft Pause Icon (Two smooth rounded vertical pills)
    val Pause: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSPause",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.White)
            ) {
                moveTo(7.5f, 4.5f)
                curveTo(6.4f, 4.5f, 5.5f, 5.4f, 5.5f, 6.5f)
                verticalLineTo(17.5f)
                curveTo(5.5f, 18.6f, 6.4f, 19.5f, 7.5f, 19.5f)
                curveTo(8.6f, 19.5f, 9.5f, 18.6f, 9.5f, 17.5f)
                verticalLineTo(6.5f)
                curveTo(9.5f, 5.4f, 8.6f, 4.5f, 7.5f, 4.5f)
                close()

                moveTo(16.5f, 4.5f)
                curveTo(15.4f, 4.5f, 14.5f, 5.4f, 14.5f, 6.5f)
                verticalLineTo(17.5f)
                curveTo(14.5f, 18.6f, 15.4f, 19.5f, 16.5f, 19.5f)
                curveTo(17.6f, 19.5f, 18.5f, 18.6f, 18.5f, 17.5f)
                verticalLineTo(6.5f)
                curveTo(18.5f, 5.4f, 17.6f, 4.5f, 16.5f, 4.5f)
                close()
            }
        }.build()
    }

    // iOS 18 Soft Skip Next (Soft arrow + vertical pill)
    val SkipNext: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSSkipNext",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.White)
            ) {
                moveTo(6.0f, 5.2f)
                curveTo(5.2f, 4.7f, 4.2f, 5.2f, 4.2f, 6.2f)
                verticalLineTo(17.8f)
                curveTo(4.2f, 18.8f, 5.2f, 19.3f, 6.0f, 18.8f)
                lineTo(14.8f, 13.0f)
                curveTo(15.5f, 12.5f, 15.5f, 11.5f, 14.8f, 11.0f)
                close()

                moveTo(18.5f, 5.0f)
                curveTo(17.7f, 5.0f, 17.0f, 5.7f, 17.0f, 6.5f)
                verticalLineTo(17.5f)
                curveTo(17.0f, 18.3f, 17.7f, 19.0f, 18.5f, 19.0f)
                curveTo(19.3f, 19.0f, 20.0f, 18.3f, 20.0f, 17.5f)
                verticalLineTo(6.5f)
                curveTo(20.0f, 5.7f, 19.3f, 5.0f, 18.5f, 5.0f)
                close()
            }
        }.build()
    }

    // iOS 18 Soft Skip Previous
    val SkipPrevious: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSSkipPrevious",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.White)
            ) {
                moveTo(18.0f, 5.2f)
                curveTo(18.8f, 4.7f, 19.8f, 5.2f, 19.8f, 6.2f)
                verticalLineTo(17.8f)
                curveTo(19.8f, 18.8f, 18.8f, 19.3f, 18.0f, 18.8f)
                lineTo(9.2f, 13.0f)
                curveTo(8.5f, 12.5f, 8.5f, 11.5f, 9.2f, 11.0f)
                close()

                moveTo(5.5f, 5.0f)
                curveTo(4.7f, 5.0f, 4.0f, 5.7f, 4.0f, 6.5f)
                verticalLineTo(17.5f)
                curveTo(4.0f, 18.3f, 4.7f, 19.0f, 5.5f, 19.0f)
                curveTo(6.3f, 19.0f, 7.0f, 18.3f, 7.0f, 17.5f)
                verticalLineTo(6.5f)
                curveTo(7.0f, 5.7f, 6.3f, 5.0f, 5.5f, 5.0f)
                close()
            }
        }.build()
    }

    // iOS 18 Soft Heart Filled (Organic bezier curves)
    val HeartFilled: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSHeartFilled",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFF375F))
            ) {
                moveTo(12.0f, 21.35f)
                curveTo(11.6f, 21.35f, 11.2f, 21.2f, 10.9f, 20.95f)
                curveTo(5.2f, 16.3f, 2.0f, 13.1f, 2.0f, 8.8f)
                curveTo(2.0f, 5.3f, 4.7f, 2.5f, 8.2f, 2.5f)
                curveTo(10.1f, 2.5f, 11.9f, 3.4f, 13.0f, 4.9f)
                curveTo(14.1f, 3.4f, 15.9f, 2.5f, 17.8f, 2.5f)
                curveTo(21.3f, 2.5f, 24.0f, 5.3f, 24.0f, 8.8f)
                curveTo(24.0f, 13.1f, 20.8f, 16.3f, 15.1f, 20.95f)
                curveTo(14.8f, 21.2f, 14.4f, 21.35f, 14.0f, 21.35f)
                close()
            }
        }.build()
    }

    // iOS 18 Soft Heart Outlined
    val HeartOutlined: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSHeartOutlined",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.White),
                strokeLineWidth = 2.0f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(12.0f, 20.5f)
                curveTo(6.0f, 15.8f, 3.0f, 12.8f, 3.0f, 8.8f)
                curveTo(3.0f, 5.8f, 5.3f, 3.5f, 8.3f, 3.5f)
                curveTo(10.2f, 3.5f, 11.9f, 4.5f, 12.0f, 5.7f)
                curveTo(12.1f, 4.5f, 13.8f, 3.5f, 15.7f, 3.5f)
                curveTo(18.7f, 3.5f, 21.0f, 5.8f, 21.0f, 8.8f)
                curveTo(21.0f, 12.8f, 18.0f, 15.8f, 12.0f, 20.5f)
                close()
            }
        }.build()
    }

    // iOS 18 Soft Download (Arrow into rounded open tray)
    val Download: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSDownload",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.White),
                strokeLineWidth = 2.0f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(12.0f, 3.5f)
                lineTo(12.0f, 15.0f)
                moveTo(7.5f, 10.5f)
                lineTo(12.0f, 15.0f)
                lineTo(16.5f, 10.5f)
                moveTo(4.0f, 17.5f)
                verticalLineTo(19.0f)
                curveTo(4.0f, 20.1f, 4.9f, 21.0f, 6.0f, 21.0f)
                horizontalLineTo(18.0f)
                curveTo(19.1f, 21.0f, 20.0f, 20.1f, 20.0f, 19.0f)
                verticalLineTo(17.5f)
            }
        }.build()
    }

    // iOS 18 Soft Download Done (Circle with checkmark)
    val DownloadDone: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSDownloadDone",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color(0xFF63E6E2)),
                strokeLineWidth = 2.0f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(12.0f, 2.5f)
                curveTo(6.75f, 2.5f, 2.5f, 6.75f, 2.5f, 12.0f)
                curveTo(2.5f, 17.25f, 6.75f, 21.5f, 12.0f, 21.5f)
                curveTo(17.25f, 21.5f, 21.5f, 17.25f, 21.5f, 12.0f)
                curveTo(21.5f, 6.75f, 17.25f, 2.5f, 12.0f, 2.5f)
                close()
                moveTo(7.5f, 12.0f)
                lineTo(10.5f, 15.0f)
                lineTo(16.5f, 9.0f)
            }
        }.build()
    }

    // iOS 18 Soft Share Sheet Icon
    val Share: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSShare",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.White),
                strokeLineWidth = 2.0f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(12.0f, 14.0f)
                lineTo(12.0f, 3.0f)
                moveTo(8.0f, 7.0f)
                lineTo(12.0f, 3.0f)
                lineTo(16.0f, 7.0f)
                moveTo(4.5f, 11.5f)
                verticalLineTo(19.0f)
                curveTo(4.5f, 20.1f, 5.4f, 21.0f, 6.5f, 21.0f)
                horizontalLineTo(17.5f)
                curveTo(18.6f, 21.0f, 19.5f, 20.1f, 19.5f, 19.0f)
                verticalLineTo(11.5f)
            }
        }.build()
    }

    // iOS 18 Soft Lyrics / Mic
    val Lyrics: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSLyrics",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.White),
                strokeLineWidth = 2.0f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(12.0f, 3.0f)
                curveTo(10.0f, 3.0f, 8.5f, 4.5f, 8.5f, 6.5f)
                verticalLineTo(11.5f)
                curveTo(8.5f, 13.5f, 10.0f, 15.0f, 12.0f, 15.0f)
                curveTo(14.0f, 15.0f, 15.5f, 13.5f, 15.5f, 11.5f)
                verticalLineTo(6.5f)
                curveTo(15.5f, 4.5f, 14.0f, 3.0f, 12.0f, 3.0f)
                close()
                moveTo(5.5f, 10.5f)
                verticalLineTo(11.5f)
                curveTo(5.5f, 15.1f, 8.4f, 18.0f, 12.0f, 18.0f)
                curveTo(15.6f, 18.0f, 18.5f, 15.1f, 18.5f, 11.5f)
                verticalLineTo(10.5f)
                moveTo(12.0f, 18.0f)
                lineTo(12.0f, 21.5f)
                moveTo(8.5f, 21.5f)
                lineTo(15.5f, 21.5f)
            }
        }.build()
    }

    // iOS 18 Soft Chevron Down (Smooth curve for player sheet dismissal)
    val ChevronDown: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSChevronDown",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.White),
                strokeLineWidth = 2.4f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(6.0f, 9.5f)
                lineTo(12.0f, 15.5f)
                lineTo(18.0f, 9.5f)
            }
        }.build()
    }

    // iOS 18 Soft Spatial 3D Audio (Orb with wave rings)
    val Spatial3D: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSSpatial3D",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.White),
                strokeLineWidth = 2.0f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(12.0f, 12.0f)
                curveTo(12.0f, 13.1f, 11.1f, 14.0f, 10.0f, 14.0f)
                curveTo(8.9f, 14.0f, 8.0f, 13.1f, 8.0f, 12.0f)
                curveTo(8.0f, 10.9f, 8.9f, 10.0f, 10.0f, 10.0f)
                curveTo(11.1f, 10.0f, 12.0f, 10.9f, 12.0f, 12.0f)
                close()

                moveTo(4.5f, 7.5f)
                curveTo(2.5f, 9.8f, 2.5f, 14.2f, 4.5f, 16.5f)
                moveTo(19.5f, 7.5f)
                curveTo(21.5f, 9.8f, 21.5f, 14.2f, 19.5f, 16.5f)
                moveTo(16.5f, 9.5f)
                curveTo(17.8f, 10.8f, 17.8f, 13.2f, 16.5f, 14.5f)
            }
        }.build()
    }

    // iOS 18 Soft Shuffle
    val Shuffle: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSShuffle",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.White),
                strokeLineWidth = 2.0f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(4.0f, 17.0f)
                lineTo(7.5f, 17.0f)
                curveTo(9.5f, 17.0f, 11.0f, 15.5f, 12.5f, 13.5f)
                curveTo(14.0f, 11.5f, 15.5f, 7.0f, 17.5f, 7.0f)
                lineTo(20.0f, 7.0f)
                moveTo(17.0f, 4.0f)
                lineTo(20.0f, 7.0f)
                lineTo(17.0f, 10.0f)

                moveTo(4.0f, 7.0f)
                lineTo(7.5f, 7.0f)
                curveTo(9.5f, 7.0f, 11.0f, 8.5f, 12.5f, 10.5f)
                moveTo(14.5f, 13.5f)
                lineTo(17.5f, 17.0f)
                lineTo(20.0f, 17.0f)
                moveTo(17.0f, 14.0f)
                lineTo(20.0f, 17.0f)
                lineTo(17.0f, 20.0f)
            }
        }.build()
    }

    // iOS 18 Soft Repeat
    val Repeat: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSRepeat",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.White),
                strokeLineWidth = 2.0f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(17.0f, 3.0f)
                lineTo(20.0f, 6.0f)
                lineTo(17.0f, 9.0f)
                moveTo(4.0f, 11.0f)
                verticalLineTo(9.0f)
                curveTo(4.0f, 7.3f, 5.3f, 6.0f, 7.0f, 6.0f)
                horizontalLineTo(20.0f)

                moveTo(7.0f, 21.0f)
                lineTo(4.0f, 18.0f)
                lineTo(7.0f, 15.0f)
                moveTo(20.0f, 13.0f)
                verticalLineTo(15.0f)
                curveTo(20.0f, 16.7f, 18.7f, 18.0f, 17.0f, 18.0f)
                horizontalLineTo(4.0f)
            }
        }.build()
    }

    // iOS 18 Soft Repeat One
    val RepeatOne: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSRepeatOne",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.White),
                strokeLineWidth = 2.0f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(17.0f, 3.0f)
                lineTo(20.0f, 6.0f)
                lineTo(17.0f, 9.0f)
                moveTo(4.0f, 11.0f)
                verticalLineTo(9.0f)
                curveTo(4.0f, 7.3f, 5.3f, 6.0f, 7.0f, 6.0f)
                horizontalLineTo(20.0f)

                moveTo(7.0f, 21.0f)
                lineTo(4.0f, 18.0f)
                lineTo(7.0f, 15.0f)
                moveTo(20.0f, 13.0f)
                verticalLineTo(15.0f)
                curveTo(20.0f, 16.7f, 18.7f, 18.0f, 17.0f, 18.0f)
                horizontalLineTo(4.0f)

                moveTo(11.0f, 10.5f)
                lineTo(12.5f, 9.5f)
                verticalLineTo(14.5f)
            }
        }.build()
    }

    // iOS 18 Soft Music Note
    val MusicNote: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSMusicNote",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.White)
            ) {
                moveTo(12.0f, 3.0f)
                verticalLineTo(13.55f)
                curveTo(11.4f, 13.2f, 10.7f, 13.0f, 10.0f, 13.0f)
                curveTo(7.8f, 13.0f, 6.0f, 14.8f, 6.0f, 17.0f)
                curveTo(6.0f, 19.2f, 7.8f, 21.0f, 10.0f, 21.0f)
                curveTo(12.2f, 21.0f, 14.0f, 19.2f, 14.0f, 17.0f)
                verticalLineTo(7.0f)
                horizontalLineTo(18.0f)
                curveTo(18.6f, 7.0f, 19.0f, 6.6f, 19.0f, 6.0f)
                verticalLineTo(4.0f)
                curveTo(19.0f, 3.4f, 18.6f, 3.0f, 18.0f, 3.0f)
                horizontalLineTo(12.0f)
                close()
            }
        }.build()
    }

    // Soft More Options (3 Dots)
    val MoreVert: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSMoreVert",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.White)
            ) {
                moveTo(12.0f, 6.0f)
                curveTo(12.8f, 6.0f, 13.5f, 5.3f, 13.5f, 4.5f)
                curveTo(13.5f, 3.7f, 12.8f, 3.0f, 12.0f, 3.0f)
                curveTo(11.2f, 3.0f, 10.5f, 3.7f, 10.5f, 4.5f)
                curveTo(10.5f, 5.3f, 11.2f, 6.0f, 12.0f, 6.0f)
                close()

                moveTo(12.0f, 13.5f)
                curveTo(12.8f, 13.5f, 13.5f, 12.8f, 13.5f, 12.0f)
                curveTo(13.5f, 11.2f, 12.8f, 10.5f, 12.0f, 10.5f)
                curveTo(11.2f, 10.5f, 10.5f, 11.2f, 10.5f, 12.0f)
                curveTo(10.5f, 12.8f, 11.2f, 13.5f, 12.0f, 13.5f)
                close()

                moveTo(12.0f, 21.0f)
                curveTo(12.8f, 21.0f, 13.5f, 20.3f, 13.5f, 19.5f)
                curveTo(13.5f, 18.7f, 12.8f, 18.0f, 12.0f, 18.0f)
                curveTo(11.2f, 18.0f, 10.5f, 18.7f, 10.5f, 19.5f)
                curveTo(10.5f, 20.3f, 11.2f, 21.0f, 12.0f, 21.0f)
                close()
            }
        }.build()
    }

    // Soft Plus Add
    val Add: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSAdd",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.White),
                strokeLineWidth = 2.4f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(12.0f, 5.0f)
                lineTo(12.0f, 19.0f)
                moveTo(5.0f, 12.0f)
                lineTo(19.0f, 12.0f)
            }
        }.build()
    }

    // Soft Trending Up
    val Trending: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSTrending",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.White),
                strokeLineWidth = 2.2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(3.5f, 17.5f)
                lineTo(9.0f, 12.0f)
                lineTo(13.5f, 15.5f)
                lineTo(20.5f, 7.5f)
                moveTo(15.0f, 7.5f)
                lineTo(20.5f, 7.5f)
                lineTo(20.5f, 13.0f)
            }
        }.build()
    }

    // Soft Equalizer
    val Equalizer: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSEqualizer",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.White),
                strokeLineWidth = 2.2f,
                strokeLineCap = StrokeCap.Round
            ) {
                moveTo(6.0f, 18.0f)
                lineTo(6.0f, 10.0f)
                moveTo(12.0f, 20.0f)
                lineTo(12.0f, 5.0f)
                moveTo(18.0f, 17.0f)
                lineTo(18.0f, 12.0f)
            }
        }.build()
    }

    // Soft Folder
    val Folder: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSFolder",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.White)
            ) {
                moveTo(3.5f, 4.0f)
                curveTo(2.7f, 4.0f, 2.0f, 4.7f, 2.0f, 5.5f)
                verticalLineTo(18.5f)
                curveTo(2.0f, 19.3f, 2.7f, 20.0f, 3.5f, 20.0f)
                horizontalLineTo(20.5f)
                curveTo(21.3f, 20.0f, 22.0f, 19.3f, 22.0f, 18.5f)
                verticalLineTo(8.5f)
                curveTo(22.0f, 7.7f, 21.3f, 7.0f, 20.5f, 7.0f)
                horizontalLineTo(12.0f)
                lineTo(10.0f, 4.5f)
                curveTo(9.6f, 4.2f, 9.1f, 4.0f, 8.6f, 4.0f)
                close()
            }
        }.build()
    }

    // Soft Phone / Local Device
    val PhoneLocal: ImageVector by lazy {
        ImageVector.Builder(
            name = "IOSPhoneLocal",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.White),
                strokeLineWidth = 2.0f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(7.0f, 3.0f)
                horizontalLineTo(17.0f)
                curveTo(18.1f, 3.0f, 19.0f, 3.9f, 19.0f, 5.0f)
                verticalLineTo(19.0f)
                curveTo(19.0f, 20.1f, 18.1f, 21.0f, 17.0f, 21.0f)
                horizontalLineTo(7.0f)
                curveTo(5.9f, 21.0f, 5.0f, 20.1f, 5.0f, 19.0f)
                verticalLineTo(5.0f)
                curveTo(5.0f, 3.9f, 5.9f, 3.0f, 7.0f, 3.0f)
                close()
                moveTo(11.0f, 18.0f)
                horizontalLineTo(13.0f)
            }
        }.build()
    }
}
