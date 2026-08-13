package com.test.gitee.hello_spigot.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import java.util.List;


//
////        创建一个熔炉配方  NamespaceKey是改配方的唯一标识，第二个参数是我所期望得到的木炭物品堆 第三个参数是原材料，然后是经验值以及烧制的时间
//        FurnaceRecipe recipe1 = new FurnaceRecipe(
//                new NamespacedKey((Plugin) this, "crimson_stem_to_charcoal"),
//                new ItemStack(Material.CHARCOAL),
//                Material.CRIMSON_STEM,
//                0.15f,
//                200
//        );
////        注册配方
//        Bukkit.addRecipe(recipe1);
//
//        FurnaceRecipe recipe2 = new FurnaceRecipe(
//                new NamespacedKey((Plugin) this, "warped_stem_to_charcoal"),
//                new ItemStack(Material.CHARCOAL),
//                Material.WARPED_STEM,
//                0.15f,
//                200
//        );
////        注册配方
//        Bukkit.addRecipe(recipe1);

public class MyRecipe {
//    添加熔炉配方
    public static List<FurnaceRecipe> getFurnaceRecipe(Plugin plugin) {
        return List.of(
                new FurnaceRecipe(
                        new NamespacedKey(plugin, "crimson_stem_to_charcoal"),
                        new ItemStack(Material.CHARCOAL),
                        Material.CRIMSON_STEM,
                        0.15f,
                        200
                ),
                new FurnaceRecipe(
                        new NamespacedKey(plugin, "warped_stem_to_charcoal"),
                        new ItemStack(Material.CHARCOAL),
                        Material.WARPED_STEM,
                        0.15f,
                        200
                )
        );
    }

    /**
     * 获取有序合成配方列表
     *
     * @param plugin 插件实例，用来创建NamespaKey
     * @return 有序配方列表
     *
     * */
    public static List<ShapedRecipe> getShapedRecipe(Plugin plugin){
        var result = new ItemStack(Material.ELYTRA);
//        给合成后的物品添加附魔效果
        result.addEnchantment(Enchantment.UNBREAKING, 1);
        var shapeRecipe = new ShapedRecipe(
            new NamespacedKey(plugin, "craft_elytra"),
                result
        );
//        指定合成的原材料,摆放在2*2 3*3的工作台中
//        指定形状，几行就几个参数，每行用一个字符表示，没有物品也需要用空格表示，然后再指定对应的物品
        shapeRecipe
                .shape("A A", "BEB", "CDC")
                .setIngredient('A', Material.STRING)
                .setIngredient('B', Material.FEATHER)
                .setIngredient('C', Material.SHULKER_SHELL)
                .setIngredient('D', Material.DIAMOND)
                .setIngredient('E', Material.NETHERITE_INGOT);
        return List.of(
                shapeRecipe
        );
    }


    /**
     * 获取交易配方
     * @param plugin 插件实例，用来创建村民交易配方，村民交易界面每一行都是一个配方
     * @return 交易配方的列表
     */
    public static List<MerchantRecipe> getMerchantRecipe(Plugin plugin){
//                村民交易配方的两个参数：买入了什么、最大交易次数
        MerchantRecipe recipe1 = new MerchantRecipe(new ItemStack(Material.COAL, 12), 6);
//        通过传入列表的方式，传入我需要给的金额:钻石或者金币
        recipe1.setIngredients(List.of(new ItemStack(Material.DIAMOND), new ItemStack(Material.GOLD_INGOT)));

//两个绿宝石换一个陶瓦
        MerchantRecipe recipe2 = new MerchantRecipe(new ItemStack(Material.TERRACOTTA, 1), 12);
        recipe2.setIngredients(List.of(new ItemStack(Material.EMERALD, 2)));
        return  List.of(
                recipe1,
                recipe2
        );
    }

}
