package de.mybukit.mycommands.mixins;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.mybukit.mycommands.helper.Location;
import de.mybukit.mycommands.helper.Teleport;

@Mixin(LivingEntity.class)
public class MixinDeathBack {

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void onDeath(DamageSource damageSource_1, CallbackInfo ci) {
        if(((LivingEntity) (Object) this) instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) (Object) this;
    		if(Teleport.playerBackMap.containsKey(playerEntity)){
    			Teleport.playerBackMap.remove(playerEntity);
    		}
    		Teleport.playerBackMap.put((ServerPlayerEntity) playerEntity, new Location(playerEntity));
        }
    }
}
