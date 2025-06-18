package me.zane.usernamemod.mixin.client;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import net.minecraft.client.session.Session.AccountType;
//import net.minecraft.client.util.Session.AccountType;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.Screen;
import me.zane.usernamemod.UsernameStorage;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.SaveLoader;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.UUID;

//@Mixin(MinecraftClient.class)
//public class MinecraftClientMixin {
//
//    @Inject(method = "getSession", at = @At("HEAD"), cancellable = true)
//    private void onGetSession(CallbackInfoReturnable<Session> cir) {
//        cir.setReturnValue(new Session(
//                UsernameStorage.username,              // username
//                UUID.randomUUID(),                      // random UUID
//                "invalidtoken",                         // accessToken
//                Optional.empty(),                       // xuid
//                Optional.empty(),                       // clientId
//                AccountType.LEGACY                      // accountType
//        ));
//        cir.cancel();
//    }
//}

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow
    private Session session;

    @Inject(method = "startIntegratedServer", at = @At("HEAD"))
    private void resetSessionBeforeWorldStart(LevelStorage.Session session, ResourcePackManager dataPackManager, SaveLoader saveLoader, boolean newWorld, CallbackInfo ci) {
        this.session = new Session(
                UsernameStorage.username,
                UUID.randomUUID(),
                "invalidtoken",
                Optional.empty(),
                Optional.empty(),
                AccountType.LEGACY
        );
        System.out.println("Username session set: " + UsernameStorage.username);
    }
}