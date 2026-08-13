package com.test.gitee.hello_spigot.listener;

import com.test.gitee.hello_spigot.Hello_spigot;
import com.test.gitee.hello_spigot.recipe.MyRecipe;
import jdk.jfr.Enabled;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Beehive;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class MyListener implements Listener {
    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Location location = player.getLocation();
//        为了避免硬编码，这里的sound方法使用枚举的对象，给一个升级的音效,音量大小和音高设置为1
        player.getWorld().playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
//        玩家出现时的提示信息
        event.setJoinMessage("野生的" + ChatColor.GOLD + player.getName() + ChatColor.WHITE + "出现了");

//        把计分板对象放在玩家身上
        player.setScoreboard(Hello_spigot.instance().SCOREBOARD);
    }

    @EventHandler
//    监听蜂蜜等级和蜜蜂数量
    void fetHoneycombInfo(PlayerInteractEvent event){
//        获取玩家点击到的方块
        Block block = event.getClickedBlock();
        if(block == null) return;
//        判断点击的是否为蜂箱，因为每只手都可以对蜂箱进行触碰，所以这边添加一个判断，如果是副手则直接返回
        BlockState state = block.getState();
        if(!(state instanceof Beehive)) return;
        if(event.getHand() == EquipmentSlot.OFF_HAND) return;
//       手上有物品--直接返回
        if(event.hasItem()) return;
        Player player = event.getPlayer();
//        if(player.getInventory().getItemInMainHand().getType() == Material.AIR)  return;  --这样也可以判断用户手中是否有东西
//        蜜蜂数量
        int beeCount = ((Beehive) state).getEntityCount();
//         蜂蜜等级  把通用类的block.getBlockData()转换为(org.bukkit.block.data.type.Beehive)对象，然后再引用getHoneyLevel方法
        int honeyLevel = ((org.bukkit.block.data.type.Beehive) block.getBlockData()).getHoneyLevel();
//        通过聊天界面将蜜蜂数量和蜂蜜等级告诉MC
//        player.sendMessage("BeeCount:" + beeCount + ", HoneyLevel" + honeyLevel);
//        通过ACTION_BAR来发送复杂的消息 在里面传递一个消息组件new TextComponent()传递信息
//        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("BeeCount:" + beeCount + ", HoneyLevel" + honeyLevel));

//        同样是传递蜂蜜和蜂巢信息，接下来的方法是通过不同的组件进行传递，这样做的好处是可以定制其属性
        TextComponent BeeCountText = new TextComponent(String.valueOf(beeCount));
        BeeCountText.setColor(net.md_5.bungee.api.ChatColor.GOLD);
        TextComponent honeyLevelText = new TextComponent(String.valueOf(honeyLevel));
        honeyLevelText.setColor(net.md_5.bungee.api.ChatColor.GOLD);
        player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                new TextComponent("BeCount:"),
                BeeCountText,
                new TextComponent(", HoneyLevel"),
                honeyLevelText
        );

    }

}
