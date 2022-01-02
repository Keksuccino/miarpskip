package de.keksuccino.miarpskip.mixin;

import de.keksuccino.miarpskip.MIARPSkip;
import de.keksuccino.miarpskip.PackPromptSkipHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.packet.s2c.play.ResourcePackSendS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {

    @Inject(at = @At("HEAD"), method = "onResourcePackSend")
    private void onOnResourcePackSend(ResourcePackSendS2CPacket packet, CallbackInfo info) {

        ServerInfo s = MinecraftClient.getInstance().getCurrentServerEntry();

        if (PackPromptSkipHandler.skipForIp(s.address)) {
            s.setResourcePackPolicy(ServerInfo.ResourcePackPolicy.DISABLED);
            MIARPSkip.LOGGER.info("#### Resource pack prompt skipped for MineInAbyss server: " + s.address);
        }

    }

}
