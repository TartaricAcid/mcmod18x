package ruby.bamboo.gui;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class SlotCampfire extends Slot {
    EntityPlayer thePlayer;
    int field_75228_b;

    public SlotCampfire(EntityPlayer par1EntityPlayer, IInventory par2iInventory, int par3, int par4, int par5) {
        super(par2iInventory, par3, par4, par5);
        this.thePlayer = par1EntityPlayer;
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
        return false;
    }

    @Override
    public ItemStack decrStackSize(int par1) {
        if (this.getHasStack()) {
            this.field_75228_b += Math.min(par1, this.getStack().stackSize);
        }

        return super.decrStackSize(par1);
    }

    @Override
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
        this.onCrafting(par2ItemStack);
        super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
    }

    @Override
    protected void onCrafting(ItemStack par1ItemStack, int par2) {
        this.field_75228_b += par2;
        this.onCrafting(par1ItemStack);
    }

    @Override
    protected void onCrafting(ItemStack par1ItemStack) {
        par1ItemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75228_b);

        if (!this.thePlayer.worldObj.isRemote) {
            int i = this.field_75228_b;
            //経験値なぁ…うーん
            float f = FurnaceRecipes.instance().getSmeltingExperience(par1ItemStack);
            int j;

            if (f == 0.0F) {
                i = 0;
            } else if (f < 1.0F) {
                j = MathHelper.floor_float(i * f);

                if (j < MathHelper.ceiling_float_int(i * f) && (float) Math.random() < i * f - j) {
                    ++j;
                }

                i = j;
            }

            while (i > 0) {
                j = EntityXPOrb.getXPSplit(i);
                i -= j;
                this.thePlayer.worldObj.spawnEntityInWorld(new EntityXPOrb(this.thePlayer.worldObj, this.thePlayer.posX, this.thePlayer.posY + 0.5D, this.thePlayer.posZ + 0.5D, j));
            }
        }

        this.field_75228_b = 0;

        FMLCommonHandler.instance().firePlayerSmeltedEvent(thePlayer, par1ItemStack);

        if (par1ItemStack.getItem() == Items.IRON_INGOT) {
            this.thePlayer.addStat(AchievementList.ACQUIRE_IRON, 1);
        }

        if (par1ItemStack.getItem() == Items.COOKED_FISH) {
            this.thePlayer.addStat(AchievementList.COOK_FISH, 1);
        }
    }
}
