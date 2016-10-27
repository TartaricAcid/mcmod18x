package ruby.bamboo.item.arrow;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ruby.bamboo.core.init.BambooData.BambooItem;
import ruby.bamboo.core.init.EnumCreateTab;
import ruby.bamboo.entity.arrow.BaseArrow;
import ruby.bamboo.entity.arrow.EntityLightArrow;
import ruby.bamboo.item.BambooBow;
import ruby.bamboo.util.ItemStackHelper;

@BambooItem(createiveTabs = EnumCreateTab.TAB_BAMBOO)
public class LightArrow extends ArrowBase {

    @Override
    public void execute(World world, ItemStack bow, ItemStack arrow, float power, int chargeFrame, EntityPlayer player) {

        EntityLightArrow entityArrow = new EntityLightArrow(world, player, power * 2.0f);
        entityArrow.setDamage(1);

        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, bow);

        if (j > 0) {
            entityArrow.setDamage(entityArrow.getDamage() + j * 0.5D + 0.5D);
        }

        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, bow);

        if (k > 0) {
            entityArrow.setKnockbackStrength(k);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, bow) > 0) {
            entityArrow.setFire(100);
        }

        if (!world.isRemote) {
            world.spawnEntityInWorld(entityArrow);
        }
        if (!isNoResources(player)) {
            ItemStackHelper.decrStackSize(player.inventory, arrow, 1);
        } else {
            entityArrow.setNoPick();
        }

    }

    @Override
    public ModelResourceLocation getBowModel(int useRemaining) {
        String jsonName = BambooBow.ICON_PULL_NAMES[useRemaining >= 18 ? 3 : useRemaining > 13 ? 2 : 1];
        return new ModelResourceLocation(jsonName, "inventory");
    }

    @Override
    public Class<? extends BaseArrow> getArrowClass() {
        return EntityLightArrow.class;
    }

}
