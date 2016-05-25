package ruby.bamboo.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLLog;
import ruby.bamboo.block.ICustomState;
import ruby.bamboo.block.decoration.DecorationClientFactory;
import ruby.bamboo.core.DataManager;
import ruby.bamboo.core.KeyBindFactory;
import ruby.bamboo.core.init.BambooData.BambooBlock.StateIgnore;
import ruby.bamboo.core.init.EntityRegister;
import ruby.bamboo.item.BambooBow;
import ruby.bamboo.item.itemblock.IEnumTex;
import ruby.bamboo.item.itemblock.ISubTexture;

/**
 * クライアントプロクシ
 *
 * @author Ruby
 *
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();
        this.registJson();
        new DecorationClientFactory().register();
        // えんてぃてぃれんだー
        new EntityRegister().renderRegist();
        KeyBindFactory.preInit();

    }

    @Override
    public void init() {
        super.init();
        KeyBindFactory.init();
        MinecraftForge.EVENT_BUS.register(DataManager.getItem(BambooBow.class));
    }

    /**
     * json登録の自動化
     */
    private void registJson() {
        List<ItemStack> isList = new ArrayList<ItemStack>();
        List<String> tmpNameList = new ArrayList<String>();
        for (String name : DataManager.getRegstedNameArray()) {
            Item item = Item.getByNameOrId(name);
            isList.clear();
            item.getSubItems(item, item.getCreativeTab(), isList);
            this.setIgnoreState(DataManager.getBlock(DataManager.getClass(name)));
            this.setCustomState(DataManager.getBlock(DataManager.getClass(name)));
            if (item instanceof ISubTexture) {

                for (IEnumTex tex : ((ISubTexture) item).getName()) {
                    String jsonName = tex.getJsonName();
                    //ModelBakery.addVariantName(item, jsonName);
                    ModelBakery.registerItemVariants(item, new ResourceLocation(jsonName));
                    ModelLoader.setCustomModelResourceLocation(item, tex.getId(), new ModelResourceLocation(jsonName, "inventory"));
                }
            } else {
                for (int i = 0; i < isList.size(); i++) {
                    ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(name, "inventory"));
                }
            }
        }
    }

    /**
     * カスタムstate設定
     *
     * @param block
     */
    private <T> void setCustomState(T obj) {
        if (obj instanceof ICustomState) {
            try {
                IStateMapper state = (IStateMapper) ((ICustomState) obj).getCustomState();
                ModelLoader.setCustomStateMapper((Block) obj, state);
            } catch (Exception e) {
                FMLLog.warning(obj.getClass().getName() + ": Custom State Error");
            }
        }
    }

    /**
     * stateをmodel参照時無視する
     *
     * @param <T>
     */
    private <T> void setIgnoreState(T obj) {
        Method method = this.getMethod(obj, StateIgnore.class);

        if (method != null) {
            try {
                IProperty[] prop = (IProperty[]) method.invoke(obj);
                if (prop != null) {
                    ModelLoader.setCustomStateMapper((Block) obj, (new StateMap.Builder()).ignore(prop).build());
                }
            } catch (Exception e) {
                FMLLog.warning(obj.getClass().getName() + "Ignore State Error");
            }
        }
    }

    /**
     * アノテーション付きメソッド探索
     *
     * @param obj
     * @param ano
     * @return
     */
    private <T> Method getMethod(T obj, Class<? extends Annotation> ano) {
        if (obj == null) {
            return null;
        }
        Method method = null;
        for (Method e : obj.getClass().getDeclaredMethods()) {
            if (e.getAnnotation(ano) != null) {
                method = e;
                break;
            }
        }
        return method;
    }
}
