package work.alsace.mapmanager.service

import org.bukkit.World
import work.alsace.mapmanager.pojo.WorldGroup
import work.alsace.mapmanager.pojo.WorldNode
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * 地图管理代理，负责处理与LuckPerms权限插件的交互、管理世界及其权限组等功能。
 * @author CHuNan, Hanamizu
 */
interface IMapAgent {
    enum class MapGroup {
        ADMIN,
        BUILDER,
        VISITOR
    }
    /**
     * 创建一个新的世界，并将其注册到权限管理中。
     *
     * @param world 世界名称。
     * @param owner 世界拥有者的玩家名。
     * @param group 权限组名称。
     */
    fun newWorld(world: String?, owner: String?, group: String?)
    /**
     * 删除一个世界及其相关的权限信息。
     *
     * @param world 要删除的世界名称。
     * @return 如果成功删除，返回true；否则返回false。
     */
    fun deleteWorld(world: String?): Boolean
    /**
     * 为指定世界的指定权限组添加一个玩家。
     *
     * @param world 世界名称。
     * @param group 权限组（管理员、建筑师、访客之一）。
     * @param player 玩家名称。
     * @return 操作成功返回true，否则返回false。
     */
    fun addPlayer(world: String?, group: MapGroup?, player: String?): Boolean
    /**
     * 从指定世界的指定权限组中移除一个玩家。
     *
     * @param world 世界名称。
     * @param group 权限组索引（0为管理员，1为建筑师，2为访客）。
     * @param player 玩家名称。
     * @return 操作成功返回true，否则返回false。
     */
    fun removePlayer(world: String?, group: Int, player: String?): Boolean
    /**
     * 将指定世界设置为公开状态。
     *
     * @param world 世界名称。
     * @return 操作成功返回true，否则返回false。
     */
    fun publicizeWorld(world: String?): Boolean
    /**
     * 将指定世界设置为私有状态。
     *
     * @param world 世界名称。
     * @return 操作成功返回true，否则返回false。
     */
    fun privatizeWorld(world: String?): Boolean
    /**
     * 判断指定的世界是否为公共世界。
     *
     * @param world 要检查的世界名称。
     * @return 如果世界为公共世界，返回true，否则返回false。
     */
    fun isPublic(world: String?): Boolean
    /**
     * 获取指定名称的玩家集合，基于指定的权限组筛选。
     *
     * @param name 世界名称或权限组名称。
     * @param group 权限组枚举（管理员、建筑师、访客）。
     * @return 包含玩家名称的CompletableFuture实例。
     */
    fun getPlayers(name: String?, group: MapGroup?): CompletableFuture<MutableSet<String?>?>?
    /**
     * 获取玩家可以进入的所有世界。
     * @param name 玩家名。
     * @return 玩家可进入的所有世界。
     */
    fun getAccessWorlds(name: String?): List<String?>?
    /**
     * 设置全局物理/方块更新规则状态。
     *
     * @param physical 新的物理/方块更新规则状态。
     */
    fun setPhysical(physical: Boolean?)
    /**
     * 设置指定世界的物理/方块更新规则状态。
     *
     * @param world 世界名称。
     * @param physical 新的物理/方块更新规则状态。
     */
    fun setPhysical(world: String?, physical: Boolean)
    /**
     * 获取指定世界的物理/方块更新规则状态。
     *
     * @param world 世界名称。
     * @return 指定世界的物理/方块更新规则状态。
     */
    fun isPhysical(world: String?): Boolean
    /**
     * 设置全局爆炸破坏状态。
     *
     * @param exploded 新的爆炸破坏状态。
     */
    fun setExploded(exploded: Boolean?)
    /**
     * 设置指定世界的爆炸破坏状态。
     *
     * @param world 世界名称。
     * @param exploded 新的爆炸破坏状态。
     */
    fun setExploded(world: String?, exploded: Boolean)
    /**
     * 获取指定世界的爆炸破坏状态。
     *
     * @param world 世界名称。
     * @return 指定世界的爆炸破坏状态。
     */
    fun isExploded(world: String?): Boolean


    /**
     * 获取指定世界的权限组名称。
     *
     * @param world 世界名称。
     * @return 权限组名称，如果世界未指定权限组，则返回null。
     */
    fun getWorldGroupName(world: String?): String?
    /**
     * 获取权限组所包含的世界。
     *
     * @param group 权限组名称。
     * @return 世界列表，若权限组下无世界，则返回null。
     */
    fun getWorldListByGroup(group: String?): List<String?>?


    /**
     * 获取指定世界的管理员集合。
     *
     * @param world 指定的世界对象。
     * @return 该世界的管理员用户名集合。
     */
    fun getAdminSet(world: World?): MutableSet<String?>?
    /**
     * 获取指定世界的建筑师集合。
     *
     * @param world 指定的世界对象。
     * @return 该世界的建筑师用户名集合。
     */
    fun getBuilderSet(world: World?): MutableSet<String?>?
    /**
     * 获取指定世界的访客集合。
     *
     * @param world 指定的世界对象。
     * @return 该世界的访客用户名集合。
     */
    fun getVisitorSet(world: World?): MutableSet<String?>?
    /**
     * 获取当前所有MapManager所管理的世界的节点映射。
     *
     * @return 包含所有MapManager所管理的世界及其对应节点信息的映射表。世界名称作为键，对应的[WorldNode]作为值。
     */
    fun getNodeMap(): MutableMap<String?, WorldNode?>?
    /**
     * 获取当前所有权限组的映射。
     *
     * @return 包含所有权限组及其对应信息的映射表。权限组名称作为键，对应的[WorldGroup]作为值。
     */
    fun getGroupMap(): MutableMap<String?, WorldGroup?>?
    /**
     * 检查指定的世界是否被MapManager所管理。
     *
     * @param world 要检查的世界名称。
     * @return 如果指定的世界被MapManager所管理，返回true；否则返回false。
     */
    fun containsWorld(world: String?): Boolean
    /**
     * 获取当前所有MapManager所管理的世界的名称。
     *
     * @return 一个包含所有MapManager所管理的世界名称的集合。
     */
    fun getWorlds(): MutableSet<String?>
    /**
     * 获取玩家的UUID
     * @param player 玩家名称。
     * @return 返回玩家的UUID。
     */
    fun getUniqueID(player: String): UUID?

    /**
     * 保存MapManager信息。
     * @return 保存的结果。
     */
    fun save(): Boolean
}
