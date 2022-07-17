package rndm_access.assorteddiscoveries.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import rndm_access.assorteddiscoveries.common.block_screen.ADWoodcutterScreenHandler;
import rndm_access.assorteddiscoveries.common.item.crafting.ADWoodcuttingRecipe;

import java.util.List;
import java.util.Objects;

public class ADWoodcutterScreen extends HandledScreen<ADWoodcutterScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/stonecutter.png");
    private float scrollAmount;
    private boolean mouseClicked;
    private int scrollOffset;
    private boolean canCraft;

    public ADWoodcutterScreen(ADWoodcutterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        handler.setContentsChangedListener(this::onInventoryChange);
        --this.titleY;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
        super.render(matrices, mouseX, mouseY, partialTicks);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        this.renderBackground(matrices);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = this.x;
        int j = this.y;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        int k = (int) (41.0F * this.scrollAmount);
        this.drawTexture(matrices, i + 119, j + 15 + k, 176 + (this.shouldScroll() ? 0 : 12), 0, 12, 15);
        int l = this.x + 52;
        int i1 = this.y + 14;
        int j1 = this.scrollOffset + 12;
        this.renderRecipeBackground(matrices, mouseX, mouseY, l, i1, j1);
        this.renderRecipeIcons(l, i1, j1);
    }

    @Override
    protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
        super.drawMouseoverTooltip(matrices, x, y);
        if (this.canCraft) {
            int i = this.x + 52;
            int j = this.y + 14;
            int k = this.scrollOffset + 12;
            List<ADWoodcuttingRecipe> list = this.handler.getAvailableRecipes();

            for (int l = this.scrollOffset; l < k && l < this.handler.getAvailableRecipeCount(); ++l) {
                int i1 = l - this.scrollOffset;
                int j1 = i + i1 % 4 * 16;
                int k1 = j + i1 / 4 * 18 + 2;
                if (x >= j1 && x < j1 + 16 && y >= k1 && y < k1 + 18) {
                    this.renderTooltip(matrices, list.get(l).getOutput(), x, y);
                }
            }
        }
    }

    private void renderRecipeBackground(MatrixStack matrices, int mouseX, int mouseY, int x, int y, int scrollOffset) {
        for (int i = this.scrollOffset; i < scrollOffset && i < this.handler.getAvailableRecipeCount(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int i1 = y + l * 18 + 2;
            int j1 = this.backgroundHeight;
            if (i == this.handler.getSelectedRecipe()) {
                j1 += 18;
            } else if (mouseX >= k && mouseY >= i1 && mouseX < k + 16 && mouseY < i1 + 18) {
                j1 += 36;
            }
            this.drawTexture(matrices, k, i1 - 1, 0, j1, 16, 18);
        }
    }

    private void renderRecipeIcons(int x, int y, int scrollOffset) {
        List<ADWoodcuttingRecipe> list = this.handler.getAvailableRecipes();

        for (int i = this.scrollOffset; i < scrollOffset && i < this.handler.getAvailableRecipeCount(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int i1 = y + l * 18 + 2;

            Objects.requireNonNull(this.client).getItemRenderer().renderInGuiWithOverrides(list.get(i).getOutput(), k, i1);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.mouseClicked = false;
        if (this.canCraft) {
            int i = this.x + 52;
            int j = this.y + 14;
            int k = this.scrollOffset + 12;

            for (int l = this.scrollOffset; l < k; ++l) {
                double i1 = l - this.scrollOffset;
                double d0 = mouseX - (i + i1 % 4 * 16);
                double d1 = mouseY - (j + i1 / 4 * 18);
                if (d0 >= 0.0D && d1 >= 0.0D && d0 < 16.0D && d1 < 18.0D) {
                    if (this.handler.onButtonClick(Objects.requireNonNull(this.client).player, l)) {
                        MinecraftClient.getInstance().getSoundManager()
                                .play(PositionedSoundInstance.master(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                        Objects.requireNonNull(this.client.interactionManager).clickButton((this.handler).syncId, l);
                        return true;
                    }
                }
            }

            i = this.x + 119;
            j = this.y + 9;
            if (mouseX >= i && mouseX < i + 12 && mouseY >= j && mouseY < j + 54) {
                this.mouseClicked = true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.mouseClicked && this.shouldScroll()) {
            int i = this.y + 14;
            int j = i + 54;
            this.scrollAmount = ((float) mouseY - i - 7.5F) / (j - i - 15.0F);
            this.scrollAmount = MathHelper.clamp(this.scrollAmount, 0.0F, 1.0F);
            this.scrollOffset = (int) (this.scrollAmount * this.getMaxScroll() + 0.5D) * 4;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (this.shouldScroll()) {
            int i = this.getMaxScroll();
            this.scrollAmount = (float) (this.scrollAmount - amount / i);
            this.scrollAmount = MathHelper.clamp(this.scrollAmount, 0.0F, 1.0F);
            this.scrollOffset = (int) (this.scrollAmount * i + 0.5D) * 4;
        }
        return true;
    }

    private boolean shouldScroll() {
        return this.canCraft && this.handler.getAvailableRecipeCount() > 12;
    }

    protected int getMaxScroll() {
        return (this.handler.getAvailableRecipeCount() + 4 - 1) / 4 - 3;
    }

    private void onInventoryChange() {
        this.canCraft = this.handler.canCraft();
        if (!this.canCraft) {
            this.scrollAmount = 0.0F;
            this.scrollOffset = 0;
        }
    }
}
