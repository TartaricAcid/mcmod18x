package ruby.bamboo.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import ruby.bamboo.api.Constants;
import ruby.bamboo.tileentity.TileCampfire;


public class GuiCampfire extends GuiContainer {
    private static final ResourceLocation RESORCE = new ResourceLocation(Constants.RESOURCED_DOMAIN + "textures/guis/campfire.png");
    private TileCampfire tile;

    public GuiCampfire(InventoryPlayer inventoryPlayer, TileCampfire tileEntity) {
        super(new ContainerCampfire(inventoryPlayer, tileEntity));
        this.tile = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(RESORCE);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;
        this.drawTexturedModalRect(k + 10, l + 17, 176, 17, 12, 32 - (int) (32 * (tile.fuelRatio / 100F)));//12.50
        this.drawTexturedModalRect(k + 90, l + 35, 176, 0, 23 - (int) (23 * (tile.cookRatio / 100F)), 16);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        if (k + 10 < mouseX && mouseX < k + 22) {
            if (l + 8 < mouseY && mouseY < l + 58) {
                FontRenderer font = null;
                if (font == null)
                    font = fontRendererObj;
                this.drawHoveringText(Arrays.asList(String.valueOf(tile.fuelRatio + "%")), mouseX - k - 2, mouseY - l + 6 + 3, font);
            }
        }
    }
}
