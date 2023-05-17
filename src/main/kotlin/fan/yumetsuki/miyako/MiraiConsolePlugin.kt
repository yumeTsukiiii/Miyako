package fan.yumetsuki.miyako

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.event.subscribeGroupMessages

object MiyakoPlugin : KotlinPlugin(
    JvmPluginDescription(
        id = "fan.yumetsuki.miyako",
        version = "1.0.0"
    )
) {

    override fun onEnable() {
        super.onEnable()
        logger.info("fan.yumetsuki.miyako bot 已启动")
        globalEventChannel().subscribeGroupMessages {
            this.sentBy(2812003614) {
                logger.info("收到了来自开发者的消息: $it")
                this.group.sendMessage("你发送了 $it")
            }
        }
    }

}