package com.krypton.client;

import com.krypton.client.feature.AmethystScanner;
import com.krypton.client.module.ModuleRegistry;
import com.krypton.client.ui.ClickGuiScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public final class KryptonClient implements ClientModInitializer {
    private boolean shiftWasDown;
    @Override public void onInitializeClient() {
        ModuleRegistry.init();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client == null) return;
            long window = client.getWindow().getHandle();
            boolean down = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;
            if (down && !shiftWasDown && client.currentScreen == null) client.setScreen(new ClickGuiScreen());
            shiftWasDown = down;
            if (ModuleRegistry.find("Amethyst Finder").enabled()) AmethystScanner.tick(client);
        });
    }
}
