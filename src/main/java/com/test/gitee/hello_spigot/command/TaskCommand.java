package com.test.gitee.hello_spigot.command;

import com.test.gitee.hello_spigot.Hello_spigot;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

//设置命令的调度器以及自动补全
public class TaskCommand implements CommandExecutor, TabCompleter {

    private final BukkitScheduler scheduler = Bukkit.getScheduler();
//    给命令设置多个参数，根据不同的参数来选择同步/异步执行
    private final List<String> subCommand = List.of("sync", "async", "delay", "period");

    private BukkitTask periodTask = null;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                sender.sendMessage(Thread.currentThread().getName() + "停止2秒");
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                } finally {
//                    sender.sendMessage(Thread.currentThread().getName() + "恢复运行");
//                }
//            }
//            在这个对象中使用runTask，使主线程同步执行这个任务
//        }.runTask(Hello_spigot.instance());
////            在这个对象中使用runTaskAsynchronously，使主线程异步执行这个任务
//        }.runTaskAsynchronously(Hello_spigot.instance());

//        下面是同样功能的不同写法：

        if (strings.length == 0) {
            sender.sendMessage("请输入参数: sync 或 async");
            return false;
        }

        switch (strings[0]) {
            case "sync":
                scheduler.runTask(Hello_spigot.instance(), ()->{
                    sender.sendMessage(Thread.currentThread().getName() + "停止2秒");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        sender.sendMessage(Thread.currentThread().getName() + "恢复运行");
                    }
                });
                break;
            case "async":
                scheduler.runTaskAsynchronously(Hello_spigot.instance(), ()->{
                    sender.sendMessage(Thread.currentThread().getName() + "停止2秒");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        sender.sendMessage(Thread.currentThread().getName() + "恢复运行");
                    }
                });
                break;

//          实现定时任务的执行
            case "delay":
                scheduler.runTaskLaterAsynchronously(Hello_spigot.instance(), ()->{
                    sender.sendMessage(Thread.currentThread().getName() + "停止2秒");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        sender.sendMessage(Thread.currentThread().getName() + "恢复运行");
                    }
                }, 20);
                break;

//                实现周期性任务
            case "period":
//                任务不存在：创建该任务
                if(periodTask == null){
//                    runTaskTimer方法中第三四个参数分别是：延迟时间和间隔时间
                    periodTask = scheduler.runTaskTimer(Hello_spigot.instance(), () -> sender.sendMessage("攻击你！"), 0, 20);
                } else {
//                    任务已经存在：取消它
                    periodTask.cancel();
                    periodTask = null;
                }
                break;
            default:
                return false;
        }
        return true;
    }

    //实现自动补全功能
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
//判断参数数量，因为这个命令只接受一个参数的情况
        if(args.length != 1) return null;
//        如果第一个参数为空，则认定该命令为空或者是个死命令
        if(args[0].isEmpty()){
            return subCommand;
        } else {
//            返回前缀匹配的字符串
            return subCommand.stream().filter(s -> s.startsWith(args[0])).toList();
        }
    }
}
