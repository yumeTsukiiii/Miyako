package fan.yumetsuki.miyako.feature

import fan.yumetsuki.miyako.util.compositeSummon
import fan.yumetsuki.miyako.util.listFilesWithExt
import fan.yumetsuki.miyako.util.toInputStream
import net.mamoe.mirai.event.GroupMessageSubscribersBuilder
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import java.io.File

data class AugustFile(
    val bgs: List<File>,
    val characters: List<File>
)
fun GroupMessageSubscribersBuilder.augustSummon() {
    val augustFile = AugustFile(
        File("static/august/bg").listFilesWithExt("png"),
        File("static/august/character").listFilesWithExt("png")
    )
    (case("来点拔月") or case("来点八月")) {
        val image = compositeSummon(
            augustFile.bgs.random(), augustFile.characters.random()
        ).toInputStream().uploadAsImage(this.group)
        this.group.sendMessage(
            PlainText("拔月yyds！！\n") + image
        )
    }
}