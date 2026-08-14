<div align="center">

# ☁️ CloudProfile

**Minecraft 玩家个人档案查询插件** — 基于 TabooLib 6.3 开发，查看自己或其他玩家的装备与个人档案。

![TabooLib](https://img.shields.io/badge/TabooLib-6.3.0--afd75a7-green) ![Minecraft](https://img.shields.io/badge/Minecraft-1.12+-orange) ![Java](https://img.shields.io/badge/Java-8+-red) ![Kotlin](https://img.shields.io/badge/Kotlin-2.2.0-purple) ![License](https://img.shields.io/badge/License-MIT-blue)

</div>

---

## 📖 插件简介

**CloudProfile（云档案）** 是一款基于 [TabooLib](https://github.com/TabooLib/taboolib) 6.3 框架开发的 Minecraft 服务器插件，为玩家提供**个人档案查询**功能：

- 玩家可通过指令查看**自己**或**其他玩家**的档案界面；
- 界面会**实时展示目标玩家的装备**（头盔、胸甲、护腿、靴子、主手、副手）与玩家头颅；
- 界面内所有文本均支持 **PlaceholderAPI** 变量解析（如 `%player_name%`、`%xconomy_balance%`）；
- GUI 布局、图标、点击动作完全可通过配置文件定制，点击动作支持 **Kether 脚本**。

> 💡 插件对 PlaceholderAPI 为**软依赖**：不安装也能运行，但界面中的 `%...%` 变量将无法解析。

---

## ✨ 功能特性

| 特性 | 说明 |
|------|------|
| 📋 档案查询 | 通过指令查看自己 / 指定玩家的档案 |
| 🤝 快捷查看 | 潜行 + 副手右键玩家即可打开该玩家的档案（可在配置中开关） |
| 🎒 装备展示 | 实时展示目标玩家头盔、胸甲、护腿、靴子、主手、副手物品 |
| 🧩 变量支持 | GUI 标题、名称、Lore 均支持 PlaceholderAPI 变量解析 |
| 🎮 Kether 脚本 | 图标左键 / 右键动作由 Kether 脚本驱动，功能高度可扩展 |
| 🎨 全量配置 | 界面布局、图标、材质、动作均可在 `gui.yml` 中配置，无需改代码 |
| 🌐 多语言 | 语言文件独立存放，可随意修改所有提示文本与指令帮助 |
| 🔄 热重载 | 配置文件（含 GUI、语言文件）均可通过指令热重载 |

---

## 🧩 环境要求

| 依赖 | 版本要求 | 说明 |
|------|----------|------|
| **Java** | 8+ | 插件编译目标为 Java 8 |
| **服务端** | Bukkit / Spigot / Paper 1.12+ | 基于 1.20.4 编译，TabooLib 提供多版本兼容 |
| **PlaceholderAPI** | 任意近期版本 | 软依赖，解析 `%变量%` 时必需 |
| **TabooLib** | 无需安装 | 已内嵌于插件中 |

---

## 📥 安装

1. 从 [Releases](https://github.com/BalanceSea/CloudProfile/releases) 下载最新版 `CloudProfile-<版本>.jar`；
2. 将 JAR 文件放入服务器 `plugins` 目录；
3. 重启服务器，插件将自动生成配置文件；
4. （可选）安装 [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)，以启用变量解析。

---

## 🎮 指令与权限

### 指令

| 指令 | 说明 | 权限 |
|------|------|------|
| `/cp` 或 `/cloudprofile` | 查看指令帮助 | `cloudprofile.use` |
| `/cp profile` | 查看**自己**的档案 | `cloudprofile.use` |
| `/cp profile <玩家名>` | 查看**指定玩家**的档案（Tab 补全在线玩家） | `cloudprofile.use` |
| `/cp reload` | 重载主配置 / GUI 配置 / 语言文件 | `cloudprofile.admin` |

> 主指令别名：`cloudprofile`、`cp`、`profile`。

### 权限

| 权限节点 | 默认值 | 说明 |
|----------|--------|------|
| `cloudprofile.use` | ✅ 所有玩家 | 使用档案查询指令 |
| `cloudprofile.admin` | ❌ OP | 重载配置文件 |

---

## 📄 配置文件

### `config.yml` — 主配置

```yaml
settings:
  # 是否允许玩家蹲下（潜行）右键其他玩家查看其档案
  sneakLook: true
```

### `gui.yml` — 档案界面配置

界面内**所有地方**（标题、名称、Lore）都支持变量解析。

首次启动生成的默认 `gui.yml`（完整内容）：

```yaml
# 该界面内所有地方都支持变量解析
# 界面特殊TYPE: Helmet Chestplate Leggings Boots Hand OffHand
# 分别对应 头盔 胸甲 护腿 靴子 主手 副手
Title: "&fCloudProfile | 玩家 %player_name% 个人档案"

Layout:
  - "####i####"
  - "# 1     #"
  - "#526    #"
  - "# 3     #"
  - "# 4     #"
  - "#########"

Icons:
  '1':
    Type: Helmet
    display:
      # 回退材质 如果玩家没有对应槽位的物品 将使用该材质
      # 如果想显示空白则材质填写air
      name: "&f玩家头盔"
      mats: "stone"
      lore:
        - "&7玩家头盔"
    actions:
      left:
        - 'sound UI_BUTTON_CLICK by 1 1'
  '2':
    Type: Chestplate
    display:
      name: "&f玩家胸甲"
      mats: "stone"
      lore:
        - "&7玩家胸甲"
    actions:
      left:
        - 'sound UI_BUTTON_CLICK by 1 1'
  '3':
    Type: Leggings
    display:
      name: "&f玩家护腿"
      mats: "stone"
      lore:
        - "&7玩家护腿"
    actions:
      left:
        - 'sound UI_BUTTON_CLICK by 1 1'
  '4':
    Type: Boots
    display:
      name: "&f玩家靴子"
      mats: "stone"
      lore:
        - "&7玩家靴子"
    actions:
      left:
        - 'sound UI_BUTTON_CLICK by 1 1'
  '5':
    Type: Hand
    display:
      name: "&f玩家主手"
      mats: "stone"
      lore:
        - "&7玩家主手"
    actions:
      left:
        - 'sound UI_BUTTON_CLICK by 1 1'
  '6':
    Type: OffHand
    display:
      name: "&f玩家副手"
      mats: "stone"
      lore:
        - "&7玩家副手"
    actions:
      left:
        - 'sound UI_BUTTON_CLICK by 1 1'
  # 隔板
  '#':
    display:
      # 物品名称
      name: "&7隔板"
      # 物品材质
      mats: "Gray_Stained_Glass_Pane"
      # CustomModelData数据 默认为-1
      cmd: -1
      # 物品描述
      lore:
        - "&7我只是个隔板"
        - "&e爱上一只小猪?"
    # 这里使用的是Kether脚本
    actions:
      # 左键点击触发音效
      left:
        - 'sound UI_BUTTON_CLICK by 1 1'
      # 右键触发tell
      right:
        - 'tell "你是一只猪猪"'
        - 'sound entity.pig.ambient by 1 1'
  # 玩家信息
  'i':
    display:
      name: "&7玩家: &f%player_name%"
      mats: "player_head"
      lore:
        - "&7金币余额: &e%xconomy_balance%"
        - "&7点券余额: &e%playerpoints_points%"
```

**图标类型（`Type`）：**

| 类型 | 说明 |
|------|------|
| `Normal` | 普通图标，使用配置中的材质 |
| `Helmet` | 显示目标玩家**头盔**（空槽位时回退到配置材质） |
| `Chestplate` | 显示目标玩家**胸甲** |
| `Leggings` | 显示目标玩家**护腿** |
| `Boots` | 显示目标玩家**靴子** |
| `Hand` | 显示目标玩家**主手**物品 |
| `OffHand` | 显示目标玩家**副手**物品 |

**材质支持**：XMaterial 材质名（如 `stone`、`player_head`、`Gray_Stained_Glass_Pane`）；填写 `air` 可显示为空白。

### `lang/zh_CN.yml` — 语言文件

所有提示消息、指令帮助均由此文件控制，支持 `{参数}` 占位符替换（如 `{player_name}`）。

```yaml
# 前缀
prefix: "&7[&b玩家个人档案系统&7] &r"

# 档案界面打开提示
profile-other-open: "&a已为您打开玩家 &e{player_name} &a的个人档案"
profile-self-open: "&a已打开您的个人档案"
# 目标玩家不在线
player-off-online: '&c目标玩家 &e{0} &c不在线'
# 帮助信息
help-message:
  - '&b指令帮助'
  - ' &7- /cp profile [玩家名] &f- 查看自身/目标玩家档案'
  - ' &7- /cp reload &f- 重载配置文件'
# 重载提示
reload-message: '&a重载配置成功'
# 打开档案失败提示
profile-open-other-fail: '&c打开玩家 &e{0} &c的个人档案失败'
```

---

## 🏗️ 项目结构

```
CloudProfile
├─ build.gradle.kts          # Gradle 构建脚本（TabooLib + Kotlin）
├─ settings.gradle.kts
├─ gradle.properties         # group / version
├─ .github/.workflows/       # GitHub Actions：自动构建 + Release 发布
└─ src/main
   ├─ kotlin/com/cloudworld/cloudprofile
   │  ├─ CloudProfile.kt             # 插件主类
   │  ├─ command/
   │  │   └─ ProfileCommand.kt       # 指令（cloudprofile / cp / profile）
   │  ├─ config/
   │  │   ├─ MainConfig.kt           # 主配置 (config.yml)
   │  │   └─ GuiConfig.kt            # GUI 配置 (gui.yml) + 图标/动作数据模型
   │  ├─ gui/
   │  │   └─ ProfileGUI.kt           # 档案 GUI（装备展示、点击动作）
   │  ├─ listener/
   │  │   └─ CloudProfileListener.kt # 潜行右键查看玩家监听
   │  └─ utils/
   │      ├─ LanguageUtil.kt         # 语言/消息工具
   │      ├─ PlaceholderParse.kt     # 占位符解析
   │      └─ ProfileKetherExecuter.kt# Kether 脚本执行器
   └─ resources
      ├─ config.yml                  # 主配置
      ├─ gui.yml                     # 档案界面配置
      └─ lang/zh_CN.yml              # 简体中文语言文件
```

---

## 🔧 从源码构建

```bash
# 需要 JDK 8+
./gradlew clean build
# 产物位于 build/libs/CloudProfile-<version>.jar

# 构建 API 版本（供其他插件二次开发引用）
./gradlew taboolibBuildApi -PDeleteCode
```

> 项目已配置 GitHub Actions：推送到 `master` 分支会自动构建、上传构建产物并创建 GitHub Release。

---

## 📜 开源协议

本项目以 [MIT License](LICENSE) 协议开源 — 你可以自由使用、修改、分发（含商业用途），只需保留原版权声明与许可声明。

---

## 🙋 交流反馈

- 🐛 问题反馈：[GitHub Issues](https://github.com/BalanceSea/CloudProfile/issues)
- 👨‍💻 作者：山海（BalanceSea）
- 💬 作者 QQ：`3643203568`
- 👥 插件交流群：`342097496`

---

<div align="center">

**如果这个插件对你有帮助，请给个 ⭐ Star 支持一下！**

</div>
