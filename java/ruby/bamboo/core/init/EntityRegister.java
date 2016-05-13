package ruby.bamboo.core.init;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruby.bamboo.core.BambooCore;
import ruby.bamboo.entity.SakuraPetal;
import ruby.bamboo.entity.SlideDoor;
import ruby.bamboo.entity.Wind;
import ruby.bamboo.render.entity.RenderPetal;
import ruby.bamboo.render.entity.RenderSlideDoor;
import ruby.bamboo.render.entity.RenderWind;

public class EntityRegister {
    private int windEID = 0;
    private int slideDoorEID = 1;

    public void entityRegist() {
        registerEntity(Wind.class, "wind", windEID);
        registerEntity(SlideDoor.class, "slideDoor", slideDoorEID);
    }

    @SideOnly(Side.CLIENT)
    public void renderRegist() {
        this.registRender(Wind.class, RenderWind.class);
        this.registRender(SlideDoor.class, RenderSlideDoor.class);
        this.registRender(SakuraPetal.class, RenderPetal.class);
    }

    private void registerEntity(Class entityClass, String entityName, int id) {
        this.registerEntity(entityClass, entityName, id, 80, 3, true);
    }

    /**
     *
     * @param entityClass
     * @param entityName
     * @param id
     * @param trackingRange 追跡範囲？
     * @param updateFrequency 更新間隔tick？
     * @param sendsVelocityUpdates クライアントへのmotion値送信？
     */
    void registerEntity(Class entityClass, String entityName, int id, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {

        EntityRegistry.registerModEntity(entityClass, entityName, id, BambooCore.instance, trackingRange, updateFrequency, sendsVelocityUpdates);

    }

    private void registRender(Class<? extends Entity> cls, Class<? extends Render> render) {
        RenderingRegistry.registerEntityRenderingHandler(cls, manager -> {
            try {
                return render.getConstructor(manager.getClass()).newInstance(manager);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });

    }

}
