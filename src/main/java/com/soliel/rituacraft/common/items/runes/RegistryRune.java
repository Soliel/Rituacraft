package com.soliel.rituacraft.common.items.runes;

import com.soliel.rituacraft.common.items.ItemEssenceRequired;
import com.soliel.rituacraft.common.items.RegistryItems;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;

public class RegistryRune {
    enum Rune {
        attraction,
        energy,
        fire,
        structure,
        transport,
        assimilation
    }

    public static final Map<Rune, RegistryObject<Item>> runeItems = new HashMap<>();

    public static Item getRune(Rune rune) {
        return runeItems.get(rune).get();
    }

    public static void registerRunes(DeferredRegister<Item> itemDeferredRegister) {
        for (Rune rune: Rune.values()) {
            RegistryObject<Item> runeItem = itemDeferredRegister.register("rune_" + rune.name(), () -> new Item(RegistryItems.STANDARD_PROPS_ITEM));
            itemDeferredRegister.register("rune_" + rune.name() + "_depowered", () -> new ItemEssenceRequired(runeItem.get()));

            runeItems.put(rune, runeItem);
        }
    }
}
