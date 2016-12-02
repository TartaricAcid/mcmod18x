package ruby.bamboo.api.crafting.grind;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IGrindInputItem {
    public Item getItem();

    public int getStackSize();

    public int getItemDamage();

    public int[] getOreIDs();

    public ItemStack getItemStack();

    @Override
    public int hashCode();

    @Override
    public boolean equals(Object obj);
}
