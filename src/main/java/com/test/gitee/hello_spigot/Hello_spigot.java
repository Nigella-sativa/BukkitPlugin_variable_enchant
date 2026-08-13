package com.test.gitee.hello_spigot;

import com.test.gitee.hello_spigot.command.OpenCommand;
import com.test.gitee.hello_spigot.command.TaskCommand;
import com.test.gitee.hello_spigot.command.VillagerCommand;
import com.test.gitee.hello_spigot.listener.MyListener;
import com.test.gitee.hello_spigot.recipe.MyRecipe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Logger;

public final class Hello_spigot extends JavaPlugin {

    //plugin比较经常访问，因此把它实例放在容易访问的位置
    private static Hello_spigot instance;
    public final Logger logger = getLogger();

//    计分板对象
    public  final Scoreboard SCOREBOARD = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        logger.info("hello plugin");
//    System.out.println("Hello spigot");

//        注册监听事件
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new MyListener(), this);


//        注册熔炉方法
        MyRecipe.getFurnaceRecipe((Plugin) this).forEach(Bukkit::addRecipe);

//        注册配方
        MyRecipe.getShapedRecipe((Plugin) this).forEach(Bukkit::addRecipe);

//        注册生成村民
        this.getCommand("villager").setExecutor(new VillagerCommand());
        this.getCommand("open").setExecutor(new OpenCommand());
        this.getCommand("task").setExecutor(new TaskCommand());

//        1.名称是health 2.准则是生命值  3.名称不会显示，因此直接留空 4.渲染类型为心形
        Objective objective1 = SCOREBOARD.registerNewObjective("health", Criteria.HEALTH, "", RenderType.HEARTS);
//        设置显示位置为玩家列表
        objective1.setDisplaySlot(DisplaySlot.PLAYER_LIST);

//        位于玩家下方的计分板：记录盔甲值,渲染类型为数字,渲染位置在玩家名称的下方
        Objective objective2 = SCOREBOARD.registerNewObjective("armor", Criteria.ARMOR, "", RenderType.INTEGER);
        objective2.setDisplaySlot(DisplaySlot.BELOW_NAME);

//        侧边栏计分板
        Objective objective3 = SCOREBOARD.registerNewObjective("rule", Criteria.DUMMY, "规则列表");
        objective3.setDisplaySlot(DisplaySlot.SIDEBAR);
//        自定义要显示的内容
        objective3.getScore(ChatColor.GOLD + "hhhhh").setScore(0);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @NotNull
    public static Hello_spigot instance(){
        return instance;
    }
}
