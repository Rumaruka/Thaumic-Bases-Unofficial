package com.rumaruka.tb.common.item.foci;

import com.rumaruka.tb.init.TBThaumonomicon;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.common.items.casters.CasterManager;
import thaumcraft.common.items.casters.ItemCaster;
import thaumcraft.common.lib.events.ServerEvents;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXFocusPartImpact;
import thaumcraft.common.lib.utils.BlockUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class FocusExperience extends FocusEffect {




    @Override
    public boolean execute(RayTraceResult rayTraceResult, @Nullable Trajectory trajectory, float finalPower, int num) {
        PacketHandler.INSTANCE.sendToAllAround(new PacketFXFocusPartImpact(rayTraceResult.hitVec.x, rayTraceResult.hitVec.y, rayTraceResult.hitVec.z, new String[]{this.getKey()}), new NetworkRegistry.TargetPoint(this.getPackage().world.provider.getDimension(), rayTraceResult.hitVec.x, rayTraceResult.hitVec.y, rayTraceResult.hitVec.z, 64.0D));
        if (rayTraceResult.typeOfHit == Type.ENTITY && rayTraceResult.entityHit != null) {

            return false;
        } else {
            if (rayTraceResult.typeOfHit == Type.BLOCK) {
                BlockPos pos = rayTraceResult.getBlockPos();
                if (this.getPackage().getCaster() instanceof EntityPlayer && this.getPackage().world.getBlockState(pos).getBlockHardness(this.getPackage().world, pos) <= this.getDamageForDisplay(finalPower) / 25.0F) {
                    FocusEvent.breakBlockWithExp(this.getPackage().world, pos, this.getPackage().world.getBlockState(pos), (EntityPlayer)this.getPackage().getCaster(), 10,  num, 100F, (Runnable)null);
                    return true;
                }
            }

            return false;
        }
    }

    @Override
    public void renderParticleFX(World world, double v, double v1, double v2, double v3, double v4, double v5) {

    }

    @Override
    public int getComplexity() {
        return this.getSettingValue("power")*3;
    }

    @Override
    public Aspect getAspect() {
        return Aspect.ENERGY;
    }

    @Override
    public String getKey() {
        return "tb.exp";
    }


    @Override
    public String getResearch() {
        return "FIRSTSTEPS";
    }
}
