package fan.yumetsuki.miyako

import fan.yumetsuki.miyako.feature.augustSummon
import fan.yumetsuki.miyako.feature.autoRepeat
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
            augustSummon()
            autoRepeat()
        }
    }

}