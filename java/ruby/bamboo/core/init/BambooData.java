package ruby.bamboo.core.init;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.minecraft.item.ItemBlock;
import ruby.bamboo.api.Constants;

public @interface BambooData {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface BambooBlock {
        /**
         * 空白は自動的にクラス名を小文字化したものになる
         */

        String name() default Constants.STR_EMPTY;

        EnumMaterial material() default EnumMaterial.GROUND;

        Class<? extends ItemBlock> itemBlock() default ItemBlock.class;

        EnumCreateTab createiveTabs() default EnumCreateTab.NONE;

        // boolean disableItem() default false;

        // stateを無視する用
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface StateIgnore {
        };

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface BambooItem {
        /**
         * 空白は自動的にクラス名を小文字化したものになる
         */

        String name() default Constants.STR_EMPTY;

        EnumCreateTab createiveTabs() default EnumCreateTab.NONE;
    }

}
