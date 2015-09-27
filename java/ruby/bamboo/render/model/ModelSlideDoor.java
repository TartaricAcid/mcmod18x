package ruby.bamboo.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSlideDoor extends ModelBase {
    public ModelRenderer box;

    public ModelSlideDoor() {
        textureWidth = 64;
        textureHeight = 64;
    }

    public ModelSlideDoor(boolean b) {
        this();
        box = new ModelRenderer(this, 0, 0);
        box.mirror = b;
        box.addBox(0F, 0F, 0F, 16, 32, 2);
        box.setRotationPoint(8F, 16F, -1F);
        box.rotateAngleZ = (float) Math.PI;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        box.render(f5);
    }
}