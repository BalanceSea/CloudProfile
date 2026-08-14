package com.cloudworld.cloudprofile.command

import com.cloudworld.cloudprofile.config.ConfigManager
import com.cloudworld.cloudprofile.config.GuiConfigManager
import com.cloudworld.cloudprofile.gui.ProfileGUI
import com.cloudworld.cloudprofile.utils.LanguageUtil.sendHelp
import com.cloudworld.cloudprofile.utils.LanguageUtil.sendParseLang
import com.cloudworld.cloudprofile.utils.MessageArg
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.module.lang.Language

@CommandHeader(
    name = "cloudprofile",
    aliases = ["cp","profile"],
    description = "玩家档案系统主指令",
    permission = "cloudprofile.use",
    permissionDefault = PermissionDefault.TRUE
)
/**
 * 插件指令部分
 * /cp profile <target> - 查看对应玩家档案
 * /cp profile - 查看自己的档案
 * /cp reload - 重载配置文件
 */
object ProfileCommand {


    @CommandBody(permission = "cloudprofile.use", permissionDefault = PermissionDefault.TRUE)
    val main = mainCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            sender.sendHelp()
        }
    }
    /**
     * 打开对应玩家档案
     */
    @CommandBody(permission = "cloudprofile.use", permissionDefault = PermissionDefault.TRUE)
    val profile = subCommand {
        /**
         * 查看对应玩家档案
         */
        dynamic("玩家名") { 
            suggestion<Player> { _, _ ->
                Bukkit.getOnlinePlayers().map { it.name }
            }
            execute<Player> { sender, context, _ ->
                val targetPlayerName = context["玩家名"]
                val targetPlayer = Bukkit.getPlayer(targetPlayerName) ?: return@execute
                if (targetPlayer.isOnline) {
                    ProfileGUI.openProfileMenu(sender,targetPlayer)
                    return@execute
                }else{
                    val targetName = MessageArg(
                        "player_name",
                        targetPlayer.name
                    )
                    sender.sendParseLang("player-off-online",targetName)
                    return@execute
                }
            }
        }

        /**
         * 查看自己的档案
         */
        execute<Player> {sender, _, _ ->
            ProfileGUI.openProfileMenu(sender)
            return@execute
        }
    }
    /**
     * 重载配置文件(Config.yml,gui.yml.lang/zh_CN.yml)
     */
    @CommandBody(permission = "cloudprofile.admin")
    val reload = subCommand {
        execute<ProxyCommandSender>{ sender, _, _ ->
            try {
                ConfigManager.reload()
            }catch (e : Exception) {
                error("重载主配置失败: ${e.message}")
            }
            try {
                GuiConfigManager.reload()
            }catch (e : Exception) {
                error("重载GUI配置失败: ${e.message}")
            }
            try {
                Language.reload()
            }catch (e : Exception) {
                error("重载语言文件失败: ${e.message}")
            }

            sender.sendParseLang("reload-message")
        }
    }
}