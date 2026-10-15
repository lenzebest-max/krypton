package com.krypton.client.ui;

import com.krypton.client.module.Module;
import com.krypton.client.module.ModuleRegistry;
import com.krypton.client.feature.AmethystScanner;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import java.util.*;

/** Compact charcoal/purple module menu. The design is original and merely inspired by common client GUIs. */
public final class ClickGuiScreen extends Screen {
    private String category = "Combat";
    private String query = "";
    private int scroll = 0;
    private TextFieldWidget search;
    private final List<Hit> hits = new ArrayList<>();
    private record Hit(int x, int y, int w, int h, Module module) {}
    public ClickGuiScreen() { super(Text.literal("Krypton Client")); }
    @Override protected void init() {
        int w = Math.min(270, width - 32);
        search = new TextFieldWidget(textRenderer, width / 2 - w / 2 + 12, 45, w - 24, 18, Text.literal("Search modules"));
        search.setPlaceholder(Text.literal("Search modules..."));
        search.setChangedListener(s -> { query = s; scroll = 0; });
        addDrawableChild(search);
    }
    @Override public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        int panelW = Math.min(620, width - 28), panelH = Math.min(390, height - 28);
        int left = (width - panelW) / 2, top = (height - panelH) / 2;
        ctx.fill(left, top, left + panelW, top + panelH, 0xF018171D);
        ctx.fill(left, top, left + panelW, top + 3, 0xFFB68CFF);
        ctx.fill(left, top + 3, left + 138, top + panelH, 0xFF211E29);
        ctx.drawTextWithShadow(textRenderer, "KRYPTON", left + 14, top + 13, 0xFFE4D4FF);
        ctx.drawTextWithShadow(textRenderer, "CLIENT", left + 80, top + 13, 0xFFB68CFF);
        ctx.drawText(textRenderer, ModuleRegistry.all().size() + " modules", left + panelW - 86, top + 14, 0xFF9C95A8, false);
        int cy = top + 38;
        for (String c : ModuleRegistry.categories()) {
            boolean selected = c.equals(category);
            if (selected) ctx.fill(left + 8, cy - 4, left + 130, cy + 15, 0xFF4B3B61);
            ctx.drawText(textRenderer, c, left + 16, cy, selected ? 0xFFEADFFF : 0xFFAAA3B5, false);
            cy += 23;
        }
        ctx.drawText(textRenderer, "RIGHT SHIFT  •  CLOSE", left + 13, top + panelH - 19, 0xFF777080, false);
        int contentX = left + 150, contentRight = left + panelW - 12;
        ctx.drawTextWithShadow(textRenderer, query.isBlank() ? category : "Search results", contentX, top + 13, 0xFFF1ECF8);
        int gridY = top + 73;
        int cardW = Math.max(120, (contentRight - contentX - 10) / 2);
        List<Module> modules = ModuleRegistry.all().stream()
            .filter(m -> query.isBlank() ? m.category().equals(category) : (m.name().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT)) || m.category().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))))
            .toList();
        int rows = Math.max(1, (panelH - 88) / 34);
        int start = Math.min(scroll, Math.max(0, modules.size() - rows * 2));
        hits.clear();
        for (int i = start; i < Math.min(modules.size(), start + rows * 2); i++) {
            Module m = modules.get(i);
            int col = (i - start) % 2, row = (i - start) / 2;
            int x = contentX + col * (cardW + 8), y = gridY + row * 34;
            boolean enabled = m.enabled();
            ctx.fill(x, y, x + cardW, y + 28, enabled ? 0xFF3B2E4B : 0xFF28252F);
            ctx.fill(x, y, x + 3, y + 28, enabled ? 0xFFBE9AFF : 0xFF514B5B);
            ctx.drawText(textRenderer, m.name(), x + 9, y + 5, enabled ? 0xFFF2E8FF : 0xFFD1CBD9, false);
            ctx.drawText(textRenderer, enabled ? "ON" : "OFF", x + cardW - 28, y + 5, enabled ? 0xFFC9A8FF : 0xFF81798C, false);
            hits.add(new Hit(x, y, cardW, 28, m));
        }
        if (modules.isEmpty()) ctx.drawText(textRenderer, "No matching modules", contentX, gridY, 0xFFAAA3B5, false);
        ctx.drawText(textRenderer, "Amethyst observations: " + AmethystScanner.findings().size(), contentX, top + panelH - 18, 0xFFBCA7DD, false);
        super.render(ctx, mouseX, mouseY, delta);
    }
    @Override public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int panelW = Math.min(620, width - 28), panelH = Math.min(390, height - 28);
            int left = (width - panelW) / 2, top = (height - panelH) / 2;
            int cy = top + 38;
            for (String c : ModuleRegistry.categories()) {
                if (mouseX >= left + 8 && mouseX <= left + 132 && mouseY >= cy - 4 && mouseY <= cy + 17) { category = c; scroll = 0; query = ""; if (search != null) search.setText(""); return true; }
                cy += 23;
            }
            for (Hit hit : hits) if (mouseX >= hit.x && mouseX <= hit.x + hit.w && mouseY >= hit.y && mouseY <= hit.y + hit.h) { hit.module.toggle(); return true; }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    @Override public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) { scroll = Math.max(0, scroll - (int)Math.signum(verticalAmount) * 2); return true; }
    @Override public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT || keyCode == GLFW.GLFW_KEY_ESCAPE) { close(); return true; }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    @Override public boolean shouldPause() { return false; }
}
