package com.soliel.rituacraft.common.items;

import com.soliel.rituacraft.Rituacraft;
import com.soliel.rituacraft.common.CommonProxy;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class RegistryItems {
    public static final Item.Properties STANDARD_PROPS_ITEM = new Item.Properties().maxStackSize(64).group(CommonProxy.ITEM_GROUP_RC);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Rituacraft.MODID);

    public static final RegistryObject<Item> RITUAL_CHALK    = ITEMS.register("ritual_chalk", ItemRitualChalk::new);
    public static final RegistryObject<Item> CHALK = ITEMS.register("chalk", () -> new ItemEssenceRequired(RITUAL_CHALK.get()));
}
