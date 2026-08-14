package com.cloudworld.cloudprofile.utils

import org.bukkit.entity.Player
import taboolib.module.chat.colored
import taboolib.module.kether.KetherShell
import taboolib.module.kether.ScriptOptions

object ProfileKetherExecuter {

    /**
     * 执行Kether脚本
     */
    fun execute(player: Player,ketherList: List<String>){
        val ketherList = ketherList.joinToString("\n").colored()
        KetherShell.eval(
            source = ketherList,
            options = ScriptOptions.new {
                sender(player)
                sandbox()
            }
        ).join()
    }
}