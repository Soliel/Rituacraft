package com.soliel.rituacraft.common.rituals.tiles;

import com.soliel.rituacraft.common.rituals.RegistryRituals;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TileFurnusRitual extends TileEntity implements ITickableTileEntity {
    private final ItemStackHandler furnusInventory = createItemHandler();
    private final LazyOptional<IItemHandler> inventoryOptional = LazyOptional.of(() -> furnusInventory);

    private IRecipe<IInventory> activeRecipe;
    private IInventory workingInventory;
    private int requiredTicks = 100;
    private int passedTicks;

    public TileFurnusRitual() {
        super(RegistryRituals.FURNUS_RITUAL_TILE.get());
    }

    @Override
    public void tick() {
        List<ItemEntity> itemList = this.findItemEntitiesAroundBlockPos(this.pos, this.world);

        if(!itemList.isEmpty()) {
            insertAndRemoveItemEntities(itemList.get(0));
        };

        if(activeRecipe != null) {
            doCraftingTick();
            return;
        }

        IInventory tempInventory = new Inventory(furnusInventory.getStackInSlot(0));
        IRecipe<IInventory> iRecipe = this.world.getRecipeManager().getRecipe(IRecipeType.SMELTING, tempInventory, this.world).orElse(null);

        if(iRecipe != null) {
            //Simulate insertion into output slot to verify that output slot can accept an item of this type.
            ItemStack resultItem = iRecipe.getCraftingResult(tempInventory);
            ItemStack stack = furnusInventory.insertItem(1, resultItem, true);
            if(stack != resultItem) {
                this.startCrafting(iRecipe, tempInventory);
            }
        }

    }

    public ItemStack extractFromInputSlot(int amount) {
        return furnusInventory.extractItem(0, amount, false);
    }

    public ItemStack getInputSlot() {
        return furnusInventory.getStackInSlot(0);
    }

    public ItemStack extractFromOutputSlot(int amount) {
        return furnusInventory.extractItem(1, amount, false);
    }

    public ItemStack getOutputSlot() {
        return furnusInventory.getStackInSlot(1);
    }

    public double getProgressPercent() {
        return (double) passedTicks / (double) requiredTicks;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.inventoryOptional.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT tag = new CompoundNBT();
        return write(tag);
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        read(state, tag);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        furnusInventory.deserializeNBT(nbt.getCompound("inv"));
        this.passedTicks = nbt.getInt("passed_ticks");

        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inv", furnusInventory.serializeNBT());
        compound.putInt("passed_ticks", this.passedTicks);
        return super.write(compound);
    }

    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(2) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
                super.onContentsChanged(slot);
            }
        };
    }

    private List<ItemEntity> findItemEntitiesAroundBlockPos(BlockPos pos, World worldIn) {
        final AxisAlignedBB checkRegion = new AxisAlignedBB(pos.add(-1, -1, -1), pos.add(1, 1, 1));
        return worldIn.getEntitiesWithinAABB(ItemEntity.class, checkRegion);
    }

    private void insertAndRemoveItemEntities(ItemEntity item) {
        ItemStack remainder = furnusInventory.insertItem(0, item.getItem(), false);
        if (remainder != item.getItem()) {
            if(!remainder.isEmpty()) {
                ItemEntity newEntity = new ItemEntity(this.world, item.getPosX(), item.getPosY(), item.getPosZ(), remainder);
                this.world.addEntity(newEntity);
            }
            item.remove();
        }
    }

    private void doCraftingTick() {
        this.passedTicks++;
        if(this.furnusInventory.getStackInSlot(0) == ItemStack.EMPTY) {
            cancelCrafting();
        }
        if(passedTicks == requiredTicks) {
            finishCrafting();
        }
    }

    private void startCrafting(IRecipe<IInventory> recipe, IInventory tempInventory) {
        this.activeRecipe = recipe;
        this.workingInventory = tempInventory;
        this.passedTicks = 0;
        this.markDirty();
    }

    private void cancelCrafting() {
        this.activeRecipe = null;
        this.passedTicks = 0;
        this.workingInventory = null;
    }

    private void finishCrafting() {
        ItemStack resultItem = activeRecipe.getCraftingResult(this.workingInventory);
        furnusInventory.insertItem(1, resultItem, false);
        furnusInventory.extractItem(0, 1 , false);

        if(furnusInventory.getStackInSlot(0) == ItemStack.EMPTY) {
            this.activeRecipe = null;
            this.passedTicks = 0;
            this.workingInventory = null;
        } else {
            startCrafting(activeRecipe, this.workingInventory);
        }

        this.markDirty();
    }
}
