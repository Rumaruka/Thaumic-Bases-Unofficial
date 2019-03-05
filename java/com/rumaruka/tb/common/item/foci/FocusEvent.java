package com.rumaruka.tb.common.item.foci;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import thaumcraft.common.lib.events.ServerEvents;

import java.util.concurrent.LinkedBlockingQueue;

import static thaumcraft.common.lib.events.ServerEvents.addRunnableServer;
import static thaumcraft.common.lib.events.ServerEvents.breakList;

public class FocusEvent {
    @SuppressWarnings("unchecked")
    public static void breakBlockWithExp(final World world,final BlockPos pos, final IBlockState source, final EntityPlayer player,  final int fortune,  int delay, final float vis, final Runnable run){

        Block blockEater = source.getBlock();
        int xp = blockEater.getExpDrop(source,world,pos,fortune);
        blockEater.onBlockDestroyedByPlayer(player.world, pos,source);
        world.setBlockToAir(pos);
        player.world.playSound(pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, blockEater.getSoundType().getBreakSound(), SoundCategory.BLOCKS, 1, 1, false);
        for(int i = 0; i < 100; ++i)
            player.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, pos.getX()+player.world.rand.nextDouble(), pos.getY()+player.world.rand.nextDouble(), pos.getZ()+player.world.rand.nextDouble(), 0, 0, 0);

        xp *= 2;

        EntityXPOrb orb = new EntityXPOrb(world, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, xp);
        if(!world.isRemote)
            world.spawnEntity(orb);
        }
    }

