package me.zane.usernamemod.mixin.client;

import me.zane.usernamemod.UsernameStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.session.Session;
import net.minecraft.client.session.Session.AccountType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.UUID;

@Mixin(MultiplayerScreen.class)
public class ConnectScreenMixin {

    @Inject(method = "connect", at = @At("HEAD"))
    private void onConnect(CallbackInfo ci) {
        Session newSession = new Session(
                UsernameStorage.username,
                UUID.randomUUID(),
                "invalidtoken",
                Optional.empty(),
                Optional.empty(),
                AccountType.LEGACY
        );

        ((MinecraftClientAccessor) MinecraftClient.getInstance()).setSession(newSession);
    }
}
