package com.cloudworld.cloudprofile.utils

import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.ProxyCommandSender
import taboolib.module.chat.colored
import taboolib.module.lang.Language
import taboolib.module.lang.asLangText
import taboolib.module.lang.asLangTextList
import taboolib.module.lang.registerLanguage
import taboolib.platform.util.asLangText

data class MessageArg(
    val key: String,
    val value: String
)

object LanguageUtil {

    @Awake(LifeCycle.ENABLE)
    fun init() {
        Language.default = "zh_CN"

        registerLanguage("zh_CN")
    }

    /**
     * 发送带前缀的消息
     */
    fun ProxyCommandSender.sendParseLang(node: String, vararg args: MessageArg) {
        val prefix = asLangText("prefix")
        var message = asLangText(node)
        message = parseText(message, *args)
        sendMessage("$prefix$message")
    }
    fun ProxyCommandSender.sendParseLang(node: String, isList: Boolean, vararg args: MessageArg) {
        val prefix = asLangText("prefix")
        var texts = asLangTextList(node)
        texts = parseText(texts, *args)
        if (isList) {
            for (text in texts) {
                sendMessage("$prefix$text")
            }
        }
    }
    fun Player.sendParseLang(node: String, vararg args: MessageArg) {
        val prefix = asLangText("prefix")
        var message = asLangText(node)
        message = parseText(message, *args)
        sendMessage("$prefix$message")
    }

    /**
     * 发送帮助信息
     */
    fun ProxyCommandSender.sendHelp(){
        sendParseLang("help-message",true)
    }
    /**
     * 解析自定义占位符
     */
    fun parseText(text: String, vararg args: MessageArg): String {
        var result = text

        for (arg in args) {
            result = result.replace(
                oldValue = "{${arg.key}}",
                newValue = arg.value
            )
        }

        return result.colored()
    }

    fun parseText(texts: List<String>, vararg args: MessageArg): List<String> {
        return texts.map { text ->
            args.fold(text) { result, arg ->
                result.replace("{${arg.key}}", arg.value)
            }
        }
    }
}