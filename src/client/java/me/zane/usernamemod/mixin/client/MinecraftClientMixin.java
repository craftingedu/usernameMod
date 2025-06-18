package me.zane.usernamemod.mixin.client;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import net.minecraft.client.session.Session.AccountType;
//import net.minecraft.client.util.Session.AccountType;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.Screen;
import me.zane.usernamemod.UsernameStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.UUID;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "getSession", at = @At("HEAD"), cancellable = true)
    private void onGetSession(CallbackInfoReturnable<Session> cir) {
        cir.setReturnValue(new Session(
                UsernameStorage.username,              // username
                UUID.randomUUID(),                      // random UUID
                "invalidtoken",                         // accessToken
                Optional.empty(),                       // xuid
                Optional.empty(),                       // clientId
                AccountType.LEGACY                      // accountType
        ));
        cir.cancel();
    }
}
