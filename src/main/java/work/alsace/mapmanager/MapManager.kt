package work.alsace.mapmanager

import net.luckperms.api.LuckPerms
import net.luckperms.api.model.group.Group
import net.luckperms.api.node.types.InheritanceNode
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.Logger
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.command.TabExecutor
import org.bukkit.plugin.java.JavaPlugin
import work.alsace.mapmanager.command.*
import work.alsace.mapmanager.function.DynamicWorld
import work.alsace.mapmanager.function.MapAgent
import work.alsace.mapmanager.listener.BlockListener
import work.alsace.mapmanager.listener.PlayerListener
import work.alsace.mapmanager.log.Log4JFilter
import java.io.File
import java.io.IOException
import java.util.*

class MapManager : JavaPlugin() {
    private var dynamicWorld: DynamicWorld? = null
    private var mapAgent: MapAgent? = null;
    private var luckPerms: LuckPerms? = null
    override fun onEnable() {
        server.consoleSender.sendMessage("[§6MapManager§7] §f启动中...")
        logger.info("正在加载配置...")
        try {
            loadConfig()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        logger.info("正在获取LuckPerms API...")
        this.dynamicWorld = DynamicWorld(this)
        this.mapAgent = MapAgent(this)

        (LogManager.getRootLogger() as Logger).addFilter(Log4JFilter())
        this.luckPerms = Bukkit.getServicesManager().getRegistration(LuckPerms::class.java)?.provider
        mapAgent?.setLuckPerms(luckPerms)
        initPermission()

        logger.info("正在注册指令和事件...")
        registerCommand("world", WorldCommand(this, null))
        registerCommand("import", ImportCommand(this))
        registerCommand("delete", DeleteCommand(this))
        registerCommand("mapadmin.md", MapAdminCommand(this))
        registerCommand("write", WriteCommand(this))
        registerCommand("worldtp", WorldTPCommand(this, dynamicWorld))
        registerCommand("create", CreateCommand(this))
        server.pluginManager.registerEvents(BlockListener(this), this)
        server.pluginManager.registerEvents(PlayerListener(dynamicWorld), this)
        server.consoleSender.sendMessage("[§6MapManager§7] §f加载成功！")
    }

    override fun onDisable() {
        mapAgent?.save()
        server.consoleSender.sendMessage("[§6MapManager§7] §f已卸载")
    }

    private fun registerCommand(cmd: String?, executor: CommandExecutor?) {
        cmd?.let { getCommand(it)?.setExecutor(executor) }
    }

    private fun registerCommand(cmd: String?, executor: TabExecutor?) {
        Objects.requireNonNull(cmd?.let { getCommand(it) })?.setExecutor(executor)
        Objects.requireNonNull(cmd?.let { getCommand(it) })?.tabCompleter = executor
    }

    fun getDynamicWorld(): DynamicWorld? {
        return dynamicWorld
    }

    fun getMapAgent(): MapAgent? {
        return mapAgent
    }

    fun getLuckPerms(): LuckPerms? {
        return luckPerms
    }

    @Throws(IOException::class)
    private fun loadConfig() {
        val configFile = File(dataFolder, "config.yml")
        if (!configFile.exists()) {
            saveResource("config.yml", false)
        }
        val worldsFile = File(dataFolder, "worlds.json")
        val groupsFile = File(dataFolder, "groups.json")
        if (!worldsFile.exists()) {
            if (!worldsFile.createNewFile()) {
                logger.warning("无法创建 worlds.json 文件")
            }
        }
        if (!groupsFile.exists()) {
            if (!groupsFile.createNewFile()) {
                logger.warning("无法创建 groups.json 文件")
            }
        }
        logger.info("运行所需文件创建完成")
    }

    private fun initPermission() {
        val manager = luckPerms?.groupManager
        manager?.loadGroup("apply")?.thenAcceptAsync { it ->
            if (it == null) {
                manager.createAndLoadGroup("apply").thenAcceptAsync { lp: Group? ->
                    val data = lp?.data()
                    data?.add(InheritanceNode.builder("default").build())
                    if (lp != null) {
                        manager.saveGroup(lp)
                        logger.info("权限组" + lp.name + "已创建并初始化完毕")
                    }
                }
            }
        }
        manager?.loadGroup("public")?.thenAcceptAsync { it ->
            if (it == null) {
                manager.createAndLoadGroup("public").thenAcceptAsync { lp: Group? ->
                    if (lp != null) {
                        manager.saveGroup(lp)
                        logger.info("权限组" + lp.name + "已创建并初始化完毕")
                    }
                }
            }
        }
        manager?.loadGroup("worldbase")?.thenAcceptAsync { it ->
            if (it == null) {
                manager.createAndLoadGroup("worldbase").thenAcceptAsync { lp: Group? ->
                    val data = lp?.data()
                    data?.add(InheritanceNode.builder("default").build())
                    if (lp != null) {
                        manager.saveGroup(lp)
                        logger.info("权限组" + lp.name + "已创建并初始化完毕")
                    }
                }
            }
        }
    }
}
