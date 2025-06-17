package me.zane.usernamemod.client;

import me.zane.usernamemod.UsernameStorage;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;

public class UsernamemodClient implements ClientModInitializer {

    public static boolean usernameScreenShown = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!usernameScreenShown && client.currentScreen instanceof TitleScreen) {
                client.setScreen(new UsernameEntryScreen(client.currentScreen));
                usernameScreenShown = true;
            }
        });
    }
}
