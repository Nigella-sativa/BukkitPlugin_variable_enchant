package com.test.gitee.hello_spigot.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class OpenCommand implements CommandExecutor {

    //通过同样颜色分队伍，一样颜色队伍中的玩家可以打开队内的背包
    public  final Map<String, Inventory> inventoryMap = new HashMap<>();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
//        命令只能由玩家运行
        if(!(sender instanceof Player player)) return false;
//        通过玩家计分板获取到玩家所在的队伍
        Team team = player.getScoreboard().getEntryTeam(player.getName());
        if(team == null){
            player.sendMessage("玩家不属于任何队伍");
            return true;
        }

        ChatColor color = team.getColor();
        if(color == ChatColor.RESET){
            player.sendMessage("队伍没有颜色");
            return true;
        }

//        将物品栏存在内存中
//        默认情况下是没有物品栏的，因此需要创建
//        createInventory中第一个参数可以是实体/方块/null  2.物品栏的类型，可以指定类型或者数字,这里所指定的是木头物品栏
        Inventory inventory = inventoryMap.computeIfAbsent(color.name(), chatColor -> Bukkit.createInventory(null, InventoryType.HOPPER));
//        玩家打开物品栏
        player.openInventory(inventory);
        return false;
    }
}
