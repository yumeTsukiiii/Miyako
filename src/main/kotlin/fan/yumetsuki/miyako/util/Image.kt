package fan.yumetsuki.miyako.util

import java.awt.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import javax.imageio.ImageIO

fun compositeSummon(background: String, character: String): BufferedImage = compositeSummon(File(background), File(character))

fun compositeSummon(background: File, character: File): BufferedImage {
    val backgroundImage = ImageIO.read(
        background
    )
    val characterImage = ImageIO.read(
        character
    )

    // 先缩放一次角色图的宽度，保证不超过背景图的宽度
    val widthScaledCharacter = if (characterImage.width > backgroundImage.width) {
        (backgroundImage.width.toDouble() / characterImage.width).let { ratio ->
            characterImage.getScaledInstance(
                (characterImage.width * ratio).toInt(),
                (characterImage.height * ratio).toInt(),
                Image.SCALE_SMOOTH
            )
        }
    } else {
        characterImage
    }

    // 再缩放一次角色图高度，尽可能让角色图在背景图上半身显示的比较好
    val scaledCharacter = if (widthScaledCharacter.getHeight(null) > backgroundImage.height * 0.95) {
        (backgroundImage.height * 0.95 / widthScaledCharacter.getHeight(null)).let { ratio ->
            widthScaledCharacter.getScaledInstance(
                (widthScaledCharacter.getWidth(null) * ratio).toInt(),
                (widthScaledCharacter.getHeight(null) * ratio).toInt(),
                Image.SCALE_SMOOTH
            )
        }
    } else {
        widthScaledCharacter
    }

    backgroundImage.graphics.drawImage(
        scaledCharacter,
        // 背景图和角色图，x 中线对齐
        (backgroundImage.width - scaledCharacter.getWidth(null)) / 2,
        // 角色图相对背景图稍微往下偏移
        backgroundImage.height - scaledCharacter.getHeight(null),
        null
    )

    val scaledResult = if (backgroundImage.width > 512) {
        val ratio = 512.0 / backgroundImage.width
        val width = (backgroundImage.width * ratio).toInt()
        val height = (backgroundImage.height * ratio).toInt()
        BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB).apply {
            graphics.drawImage(
                backgroundImage.getScaledInstance(width, height, Image.SCALE_SMOOTH),
                0, 0, null
            )
        }
    } else {
        backgroundImage
    }
    return scaledResult
}

fun BufferedImage.toInputStream(): InputStream {
    return ByteArrayOutputStream().apply {
        ImageIO.write(this@toInputStream, "png", this)
    }.toByteArray().inputStream()
}