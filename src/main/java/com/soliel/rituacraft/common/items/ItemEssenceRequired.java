package com.soliel.rituacraft.common.items;

import com.soliel.rituacraft.common.CommonProxy;
import com.soliel.rituacraft.common.capabilities.EssenceChunk;
import com.soliel.rituacraft.common.capabilities.RegisterCapabilities;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.chunk.Chunk;

public class ItemEssenceRequired extends Item {
    private Item transformedItem;

    public ItemEssenceRequired(Item transformedItem) {
        super(new Item.Properties().maxStackSize(64).group(CommonProxy.ITEM_GROUP_RC));
        this.transformedItem = transformedItem;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        int chunkX =  ((int)Math.floor(entity.getPosX())) >> 4;
        int chunkZ =  ((int)Math.floor(entity.getPosZ())) >> 4;

        Chunk chunk = entity.world.getChunk(chunkX, chunkZ);
        EssenceChunk eChunk = chunk.getCapability(RegisterCapabilities.CAPABILITY_ESSENCE_CHUNK).orElse(null);

        if(eChunk.getCurrentGenerationAmount() >= 360) {
            ItemStack newItem = new ItemStack(transformedItem);
            ItemEntity newEntity = new ItemEntity(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), newItem);
            entity.world.addEntity(newEntity);
            entity.remove();
        }

        return false;
    }
}
