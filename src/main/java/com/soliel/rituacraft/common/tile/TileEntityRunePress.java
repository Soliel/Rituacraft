package com.soliel.rituacraft.common.tile;

import com.soliel.rituacraft.common.crafting.RegistryRecipes;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityRunePress extends TileEntity implements ITickableTileEntity {
    private final ItemStackHandler craftingInventory = createItemHandler();
    private final ItemStackHandler resultInventory = createItemHandler();

    private final LazyOptional<IItemHandler> inputCap = LazyOptional.of(() -> craftingInventory);
    private final LazyOptional<IItemHandler> outputCap = LazyOptional.of(() -> resultInventory);

    private final int requiredTicks = 100;
    private int passedTicks = 0;
    private int chargedTicks = 0;

    private IRecipe<IInventory> workingRecipe;

    public TileEntityRunePress() {
        super(RegistryTileEntity.RUNE_PRESS.get());
    }

    @Override
    public void tick() {
        if(workingRecipe != null) {
            doCraftingTick();
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.DOWN) {
                return outputCap.cast();
            } else {
                return inputCap.cast();
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("input", craftingInventory.serializeNBT());
        compound.put("output", resultInventory.serializeNBT());
        compound.putInt("passed_ticks", this.passedTicks);

        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        craftingInventory.deserializeNBT(nbt.getCompound("input"));
        resultInventory.deserializeNBT(nbt.getCompound("output"));
        this.passedTicks = nbt.getInt("passed_ticks");

        this.processItemChanges();

        super.read(state, nbt);
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        read(state, tag);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT tag = new CompoundNBT();
        return write(tag);
    }

    public boolean addCharge() {
        if(this.chargedTicks < 3) {
            this.chargedTicks += 5;
            return true;
        }

        return false;
    }

    public float getProgress() {
        return (float) passedTicks / (float) requiredTicks;
    }

    public ItemStack insertItem(ItemStack itemStack, boolean simulate) {
        return craftingInventory.insertItem(0, itemStack, simulate);

    }

    public ItemStack grabMaterialItem() {
        return craftingInventory.extractItem(0, 1, false);
    }

    public ItemStack grabResultItem() {
        return resultInventory.extractItem(0, 1, false);
    }

    public ItemStack getCurrentInputItem() {
        return craftingInventory.getStackInSlot(0).copy();
    }

    public ItemStack getCurrentOutputItem() {
        return resultInventory.getStackInSlot(0).copy();
    }

    public boolean hasResultItem() {
        return this.resultInventory.getStackInSlot(0) != ItemStack.EMPTY;
    }

    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();

                processItemChanges();

                super.onContentsChanged(slot);
            }
        };
    }

    private IRecipe<IInventory> getCraftingRecipe(ItemStack stack) {
        IInventory tempInventory = new Inventory(stack);
        if(world != null) {
            return world.getRecipeManager().getRecipe(RegistryRecipes.RUNE_PRESS_RECIPE, tempInventory, world).orElse(null);
        }

        return null;
    }

    private boolean canCraft(IRecipe<IInventory> recipe) {
        return resultInventory.insertItem(0, recipe.getRecipeOutput(), true) == ItemStack.EMPTY;
    }

    private void processItemChanges() {
        workingRecipe = getCraftingRecipe(craftingInventory.getStackInSlot(0));
        if(workingRecipe == null || !canCraft(workingRecipe)) {
            resetProgress();
        }
    }

    private void resetProgress() {
        passedTicks = 0;
        chargedTicks = 0;
    }

    private void doCraftingTick() {
        if(workingRecipe == null) return;

        if(this.chargedTicks > 0) {
            if(passedTicks < requiredTicks) {
                passedTicks++;
            } else {
                completeCrafting();
                passedTicks = 0;
                return;
            }

            chargedTicks--;
        }
    }

    private void completeCrafting() {
        ItemStack result = workingRecipe.getCraftingResult(null);
        ItemStack leftover = resultInventory.insertItem(0, result, false);
        if(leftover == result) return;

        craftingInventory.extractItem(0, 1, false);
    }

}
