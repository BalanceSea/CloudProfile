package com.cloudworld.cloudprofile.utils

import org.bukkit.entity.Player
import taboolib.platform.compat.replacePlaceholder

/**
 * 变量解析工具
 */
object PlaceholderParse {
    fun parsePlaceholder(message: String,player: Player): String{
        return message.replacePlaceholder(player)
    }
}