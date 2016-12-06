package ruby.bamboo.api;

import net.minecraft.util.math.AxisAlignedBB;

/**
 * てきとーな定数
 */
public class Constants {

    public static final String MODID = "bamboomod";
    public static final String MC_VER = "@MC_VERSION@";
    public static final String BAMBOO_VER = "@VERSION@";
    public static final String RESOURCED_DOMAIN = "bamboomod:";
    public static final String STR_EMPTY = "";

    //すてーとたいぷ(良くわかってない)
    public static final String META = "meta";
    public static final String TYPE = "type";
    public static final String AGE = "age";
    public static final String FLG = "flg";

    //せぱれー
    /**
     * ドメイン変わることもある？
     */
    public static final String DMAIN_SEPARATE = ":";
    /**
     * ぱっけーじ用
     */
    public static final String PACKAGE_SEPARATE = ".";

    // ぱす
    /**
     * パッケージパス
     */
    public static final String BLOCK_PACKAGE = "ruby.bamboo.block";
    public static final String ITEM_PACKAGE = "ruby.bamboo.item";


    public static final AxisAlignedBB ZERO_AABB=new AxisAlignedBB(0,0,0,0,0,0);
}
