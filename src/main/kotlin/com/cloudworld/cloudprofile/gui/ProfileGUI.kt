package com.cloudworld.cloudprofile.gui

import com.cloudworld.cloudprofile.config.GuiConfigManager
import com.cloudworld.cloudprofile.config.Icon
import com.cloudworld.cloudprofile.config.IconSlotType
import com.cloudworld.cloudprofile.utils.LanguageUtil.sendParseLang
import com.cloudworld.cloudprofile.utils.MessageArg
import com.cloudworld.cloudprofile.utils.PlaceholderParse
import com.cloudworld.cloudprofile.utils.ProfileKetherExecuter
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Chest
import taboolib.platform.compat.replacePlaceholder
import taboolib.platform.util.buildItem
import taboolib.platform.util.isAir

object ProfileGUI {
    val PlayerMenuList = ArrayList<Player>()

    /**
     * 为玩家打开对应玩家的档案
     * @param player 当前玩家
     * @param targetPlayer 目标玩家
     */
    fun openProfileMenu(player: Player,targetPlayer:Player) {
        val guiConfig = GuiConfigManager.getGuiConfig()
        val title = PlaceholderParse.parsePlaceholder(guiConfig.title, targetPlayer)

        player.openMenu<Chest>(title) {
            map(*guiConfig.layout.toTypedArray())
            for (icon in guiConfig.icons) {
                val iconItem = buildIconItem(icon, targetPlayer)
                val iconActions = icon.actions
                set(icon.key, iconItem) {
                    val event = clickEvent()

                    if (event.click == ClickType.LEFT) {
                        ProfileKetherExecuter.execute(player, iconActions.leftActions)
                    } else if (event.click == ClickType.RIGHT) {
                        ProfileKetherExecuter.execute(player, iconActions.rightActions)
                    }
                }

                // 用于重载配置时刷新界面
                PlayerMenuList.add(player)

            }
            val name = MessageArg(
                "player_name",
                targetPlayer.name
            )
            player.sendParseLang("profile-other-open",name)

            /**
             * 关闭界面从刷新列表中移出玩家
             */
            onClose { PlayerMenuList.remove(player) }
        }
    }

    /**
     * 为玩家打开自己个人档案
     * @param player 当前玩家
     */
    fun openProfileMenu(player: Player) {
        val guiConfig = GuiConfigManager.getGuiConfig()
        val title = PlaceholderParse.parsePlaceholder(guiConfig.title, player)

        player.openMenu<Chest>(title) {
            map(*guiConfig.layout.toTypedArray())
            for (icon in guiConfig.icons) {
                val iconItem = buildIconItem(icon, player)
                val iconActions = icon.actions
                set(icon.key, iconItem) {
                    val event = clickEvent()

                    if (event.click == ClickType.LEFT) {
                        ProfileKetherExecuter.execute(player, iconActions.leftActions)
                    } else if (event.click == ClickType.RIGHT) {
                        ProfileKetherExecuter.execute(player, iconActions.rightActions)
                    }
                }

                // 用于重载配置时刷新界面
                PlayerMenuList.add(player)


            }
            player.sendParseLang("profile-self-open")
            /**
             * 关闭界面从刷新列表中移出玩家
             */
            onClose { PlayerMenuList.remove(player) }
        }
    }

    /**
     * 构建物品
     */
    fun buildIconItem(icon: Icon,player: Player): ItemStack{
        when(icon.type){
            IconSlotType.NORMAL -> {
                return buildNormalItem(icon,player)
            }
            IconSlotType.HELMET -> {
                return if (!player.inventory.helmet.isAir){
                    player.inventory.helmet!!
                }else{
                    buildNormalItem(icon,player)
                }
            }
            IconSlotType.CHESTPLATE -> {
                return if (!player.inventory.chestplate.isAir){
                    player.inventory.chestplate!!
                }else{
                    buildNormalItem(icon,player)
                }
            }
            IconSlotType.LEGGINGS -> {
                return if (!player.inventory.leggings.isAir){
                     player.inventory.leggings!!
                }else{
                    buildNormalItem(icon,player)
                }
            }
            IconSlotType.BOOTS -> {
                return if (!player.inventory.boots.isAir) {
                    player.inventory.boots!!
                }else{
                    buildNormalItem(icon,player)
                }
            }
            IconSlotType.HAND -> {
                return if (!player.inventory.itemInMainHand.isAir){
                    player.inventory.itemInMainHand
                }else{
                    buildNormalItem(icon,player)
                }
            }
            IconSlotType.OFF_HAND -> {
                return if (!player.inventory.itemInOffHand.isAir){
                    player.inventory.itemInOffHand
                }else{
                    buildNormalItem(icon,player)
                }
            }
        }
    }

    /**构建普通物品
     */
    fun buildNormalItem(icon: Icon,player: Player): ItemStack{
        val placeholderLore = icon.lore.map { text ->
            if (text.contains("%")){
                PlaceholderParse.parsePlaceholder(text,player)
            }else{
                text
            }
        }
        val iconItem = buildItem(icon.mats){
            name = icon.name.replacePlaceholder(player)
            lore += placeholderLore
            customModelData = icon.cmd
            skullOwner = player.name
            colored()
        }
        return iconItem
    }
}