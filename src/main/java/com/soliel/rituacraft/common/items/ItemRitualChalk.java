package com.soliel.rituacraft.common.items;

import com.soliel.rituacraft.common.CommonProxy;
import com.soliel.rituacraft.common.misc.BlockUtils;
import com.soliel.rituacraft.common.rituals.RegistryRituals;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;

public class ItemRitualChalk extends Item {
    public ItemRitualChalk() {
        super(new Properties().maxStackSize(1).maxDamage(200).group(CommonProxy.ITEM_GROUP_RC).rarity(Rarity.EPIC));
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        BlockItemUseContext blockContext = new BlockItemUseContext(context);
        BlockUtils.placeBlock(blockContext, RegistryRituals.FURNUS_RITUAL_BLOCK.get());
        return ActionResultType.SUCCESS;
    }
}
