package ruby.bamboo.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruby.bamboo.api.BambooBlocks;
import ruby.bamboo.api.Constants;
import ruby.bamboo.core.init.BambooData.BambooBlock;
import ruby.bamboo.core.init.BambooData.BambooBlock.StateIgnore;
import ruby.bamboo.core.init.EnumCreateTab;
import ruby.bamboo.core.init.EnumMaterial;
import ruby.bamboo.item.itemblock.ItemBambooShoot;

/**
 * たけのこ
 *
 * @author Ruby
 *
 */
@BambooBlock(itemBlock = ItemBambooShoot.class, createiveTabs = EnumCreateTab.TAB_BAMBOO, material = EnumMaterial.PLANTS)
public class BambooShoot extends BlockBush implements IGrowable {

    public static final PropertyInteger META = PropertyInteger.create(Constants.META, 0, 1);
    public static final AxisAlignedBB BLOCK_AABB = new AxisAlignedBB(0.3F, 0.0F, 0.3F, 0.7F, 0.5F, 0.7F);

    public BambooShoot(Material material) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(META, 0));
        this.setTickRandomly(true);
        this.setHardness(0.05F);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BLOCK_AABB;
    }

    /**
     * 中心軸からずらすアレ
     */
    @Override
    @SideOnly(Side.CLIENT)
    public Block.EnumOffsetType getOffsetType() {
        return Block.EnumOffsetType.XYZ;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(META, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(META);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, META);
    }

    public void tryBambooGrowth(World world, BlockPos pos, IBlockState state, float prob) {
        if (!world.isRemote) {
            if (world.rand.nextFloat() < prob && canChildGrow(world, pos, state)) {
                world.setBlockState(pos, BambooBlocks.BAMBOO.getDefaultState());
            }
        }
    }

    public boolean canChildGrow(World world, BlockPos pos, IBlockState state) {
        // 頭上が空気以外だったら成長させない置物処理
        boolean flg = world.isAirBlock(pos.up());

        if (flg) {
            Chunk chunk = world.getChunkFromBlockCoords(pos);
            // 成長の条件は人工の光7以上
            flg = chunk.getLightFor(EnumSkyBlock.BLOCK, pos) > 7;
        }
        return flg;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this);
    }

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState state) {
        return EnumPushReaction.DESTROY;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn) {
        if (!canBlockStay(world, pos, state)) {
            world.setBlockToAir(pos);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        Block ground = worldIn.getBlockState(pos.down()).getBlock();
        return ground == Blocks.GRASS || ground == Blocks.DIRT || ground == Blocks.FARMLAND;
    }

    @Override
    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!this.canBlockStay(worldIn, pos, state)) {
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        tryBambooGrowth(world, pos, state, world.isRaining() ? 0.25F : 0.125F);
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        this.tryBambooGrowth(world, pos, state, 0.75F);
    }

    @StateIgnore
    public IProperty[] getIgnoreState() {
        return new IProperty[] { META };
    }
}
