package com.cloudworld.cloudprofile.listener

import com.cloudworld.cloudprofile.config.ConfigManager
import com.cloudworld.cloudprofile.gui.ProfileGUI
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEntityEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.isOffhand

object CloudProfileListener {

    @SubscribeEvent
    fun onPlayerShiftRightPlayer(event: PlayerInteractEntityEvent){
        if (ConfigManager.getConfig().snakeLook) {
            if (event.rightClicked is Player) {
                val player: Player = event.player
                val otherPlayer: Player = event.rightClicked as Player

                if (event.isOffhand() && player.isSneaking) {
                    event.isCancelled = true
                    ProfileGUI.openProfileMenu(player, otherPlayer)
                }
            }
        }
    }
}