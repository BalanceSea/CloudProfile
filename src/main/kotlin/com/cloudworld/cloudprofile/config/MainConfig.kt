package com.cloudworld.cloudprofile.config

import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

data class MainConfig(
   val snakeLook: Boolean
)

object ConfigManager{
    @Config("config.yml")
    lateinit var config : Configuration
        private set

    /**
     * 获取主配置类
     */
    fun getConfig(): MainConfig{
        return MainConfig(
            config.getBoolean("settings.sneakLook", false)
        )
    }
    /**
     * 重载主配置文件
     */
    fun reload(){
        config.reload()
    }


}