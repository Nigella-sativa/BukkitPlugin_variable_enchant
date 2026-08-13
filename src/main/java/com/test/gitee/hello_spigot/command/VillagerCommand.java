package com.test.gitee.hello_spigot.command;

import com.test.gitee.hello_spigot.Hello_spigot;
import com.test.gitee.hello_spigot.recipe.MyRecipe;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.jetbrains.annotations.NotNull;

public class VillagerCommand implements CommandExecutor {
    
    @Override
//    四个参数：1.命令发送者：玩家/控制台 2.当前命令对象 3.主命令的字符串 4.参数列表
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
//        发送者不是玩家--直接返回
        if(!(sender instanceof Player player)) return false;

//        在玩家附近生成一个村民
        World world = player.getWorld();
        Location location = player.getLocation();
        Villager villager = (Villager) world.spawnEntity(location, EntityType.VILLAGER);
//        设置村民属性
        villager.setCustomName("奸商");
        villager.setCustomNameVisible(true);
        villager.setAI(false);
        villager.setRecipes(MyRecipe.getMerchantRecipe(Hello_spigot.instance()));
        return true;
    }
}
