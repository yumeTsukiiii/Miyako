package fan.yumetsuki.miyako.feature

import kotlinx.coroutines.delay
import net.mamoe.mirai.event.GroupMessageSubscribersBuilder
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.firstIsInstanceOrNull

/**
 * 防止因重复消息导致被判机器人？
 */
fun GroupMessageSubscribersBuilder.autoRepeat() {
    var lastRepeatTime = System.currentTimeMillis()

    always {
        // 仅仅是图片，忽视
        if (message.size == 1 && message.firstIsInstanceOrNull<Image>() != null) {
            return@always
        }
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastRepeatTime > 1000 * 60 * 10) {
            lastRepeatTime = currentTime
            // 10 分钟可以复读上一句话
            delay(2000)
            this.group.sendMessage(message + "（")
        }
    }
}