package com.cloudworld.cloudprofile.config

import taboolib.common.platform.function.warning
import taboolib.library.xseries.XMaterial
import taboolib.module.chat.colored
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

/**
 * 槽位占位符枚举
 */
enum class IconSlotType{
    NORMAL,
    HELMET,
    CHESTPLATE,
    LEGGINGS,
    BOOTS,
    HAND,
    OFF_HAND;

    companion object {
        fun fromString(value: String):IconSlotType?{
            return IconSlotType.values().find {
                it.name.equals(value, ignoreCase = true)
            }
        }
    }
}
/**
 * GUI物品动作类
 */
data class IconActions(
    // 左键动作
    val leftActions: List<String>,
    // 右键动作
    val rightActions: List<String>,
)
/**
 * GUI物品类
 */
data class Icon(
    // 标识符
    val key: Char,
    // 物品名称
    val name: String,
    // 物品材质
    val mats: XMaterial,
    // CustomModelData数据
    val cmd: Int,
    // 物品描述
    val lore: List<String>,
    // 物品动作
    val actions: IconActions,
    // 图标类型
    val type: IconSlotType
)

/**
 * GUI配置类
 */
data class GuiConfig(
    val title: String,
    val layout: List<String>,
    val icons: List<Icon>
)
val defaultGuiConfig = GuiConfig(
    "&fCloudProfile | 玩家个人档案",
    listOf(""),
    icons = listOf()
)
object GuiConfigManager{
    @Config("gui.yml")
    lateinit var file: Configuration
        private set

    /**
     * 获取GUI配置类
     */
    fun getGuiConfig(): GuiConfig {
        return loadGuiConfig()
    }

    /**
     * 重载配置文件
     */
    fun reload(){
        file.reload()
    }

    /**
     * 加载GUI配置类
     */
    private fun loadGuiConfig(): GuiConfig{
        val title = file.getString("Title","&fCloudProfile | 玩家个人档案")!!
        val layout = file.getStringList("Layout")
        if (layout.isEmpty()){
            warning("GUI配置中的 Layout 为空,返回默认GUI配置")
            return defaultGuiConfig
        }
        val iconSection = file.getConfigurationSection("Icons") ?: error("获取图标配置子节点出错")

        val icons = ArrayList<Icon>()

        for (key in iconSection.getKeys(false)){
            val type = IconSlotType.fromString(file.getString("Icons.${key}.Type","NORMAL")!!)?: IconSlotType.NORMAL

            val name = file.getString("Icons.${key}.display.name"," ")!!

            val matsString = file.getString("Icons.${key}.display.mats","STONE")!!

            val mats = XMaterial.matchXMaterial(matsString).get()

            val cmd = file.getInt("Icons.${key}.display.cmd")

            val lore = file.getStringList("Icons.${key}.display.lore")

            val leftActions = file.getStringList("Icons.${key}.actions.left")

            val rightActions = file.getStringList("Icons.${key}.actions.right")

            val iconActions = IconActions(
                leftActions = leftActions,
                rightActions = rightActions,
            )
            icons.add(Icon(
                key = key[0],
                name = name,
                mats = mats,
                cmd = cmd,
                lore = lore.colored(),
                iconActions,
                type
            ))
            IconSlotType.fromString("1")
        }
        return GuiConfig(
            title.colored(),
            layout,
            icons,
        )
    }
}