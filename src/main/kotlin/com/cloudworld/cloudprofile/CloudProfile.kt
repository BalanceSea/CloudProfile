package com.cloudworld.cloudprofile

import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info
import taboolib.platform.util.bukkitPlugin

object CloudProfile : Plugin() {

    override fun onEnable() {
        info("云档案 —— 玩家个人档案系统")
        info("当前版本: ${bukkitPlugin.description.version}")
        info("作者: 山海")
        info("作者QQ: 3643203568")
        info("插件交流群: 342097496")
    }
}