package com.rumaruka.thaumicbases.common.tiles;

import com.rumaruka.thaumicbases.common.block.BlockCampfire;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.potions.PotionFluxTaint;
import java.util.*;


public class TileCampfire extends TileEntity implements ITickable {


    public int burnTime;
    public int logTime;
    public int uptime;
    public boolean tainted;
    public int syncTimer;

    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        burnTime = tag.getInteger("burnTime");
        logTime = tag.getInteger("logTime");
        tainted = tag.getBoolean("tainted");
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("burnTime", burnTime);
        compound.setInteger("logTime", logTime);
        compound.setBoolean("tainted", tainted);

        return compound;
    }

    public boolean addLog(ItemStack log)
    {
        if(log == null)
            return false;

        if(logTime > 0)
            return false;

        if(log.getItem() == Item.getItemFromBlock(BlocksTC.logGreatwood)||log.getItem() == Item.getItemFromBlock(BlocksTC.logSilverwood))
        {
            if(log.getItemDamage()%3==0)
            {
                logTime = 1600*4;
                this.world.setBlockState(getPos(), world.getBlockState(getPos()).withProperty(BlockCampfire.STATE,1));
                syncTimer = 1;
                return true;
            }
        }

        if(log.getItem() == Item.getItemFromBlock(BlocksTC.taintLog))
        {
            logTime = 1600*8;
            tainted = true;
            this.world.setBlockState(getPos(), world.getBlockState(getPos()).withProperty(BlockCampfire.STATE,1));
            syncTimer = 1;
            return true;
        }

        if(Block.getBlockFromItem(log.getItem()) instanceof BlockLog)
        {
            logTime = 1600;
            this.world.setBlockState(getPos(), world.getBlockState(getPos()).withProperty(BlockCampfire.STATE,1));
            syncTimer = 1;
            return true;
        }

        if(OreDictionary.getOreIDs(log).length > 0)
        {
            int[] ints = OreDictionary.getOreIDs(log);
            boolean isLog = false;
            for(int i = 0; i < ints.length; ++i)
            {
                String str = OreDictionary.getOreName(ints[i]);
                if(str.equals("logWood"))
                {
                    isLog = true;
                    break;
                }
            }
            if(isLog)
            {
                logTime = 1600;
                this.world.setBlockState(getPos(), world.getBlockState(getPos()).withProperty(BlockCampfire.STATE,1));
                syncTimer = 1;
                return true;
            }
        }

        return false;
    }

    public boolean addFuel(ItemStack item)
    {
        if(item == null)
            return false;

        if(logTime <= 0)
            return false;

        if(burnTime > 0)
            return false;

        int burnTime = TileEntityFurnace.getItemBurnTime(item);

        if(burnTime <= 0)
            return false;

        this.burnTime = burnTime;
        this.world.setBlockState(getPos(), world.getBlockState(getPos()).withProperty(BlockCampfire.STATE,2));

        syncTimer = 1;

        return true;
    }

    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        boolean b = pkt.getNbtCompound().getBoolean("2");
        if(tainted != b)
        {
            this.world.markBlockRangeForRenderUpdate(getPos().down().west().north(), getPos().up().east().south());
            tainted = b;
        }
    }

    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return oldState.getBlock() == newSate.getBlock() ? false : super.shouldRefresh(world, pos, oldState, newSate);
    }

    @SuppressWarnings({ "rawtypes" })
    public void update()
    {
        if(syncTimer <= 0)
        {
            syncTimer = 100;
            NBTTagCompound tg = new NBTTagCompound();
            tg.setBoolean("2", tainted);
            tg.setInteger("x", this.pos.getX());
            tg.setInteger("y", this.pos.getY());
            tg.setInteger("z", this.pos.getZ());

        }else
            --syncTimer;

        ++uptime;

        if(burnTime > 0)
        {
            --burnTime;

            if(logTime > 0)
                --logTime;
        }

        if(!this.world.isRemote)
        {
            if(burnTime <= 0)
            {

                int metadata = getWorld().getBlockState(getPos()).getValue(BlockCampfire.STATE);
                if(metadata==2)
                    this.world.setBlockState(getPos(), world.getBlockState(getPos()).withProperty(BlockCampfire.STATE,1));

                return;
            }
            if(logTime <= 0)
            {
                int metadata = getWorld().getBlockState(getPos()).getValue(BlockCampfire.STATE);
                if(metadata > 0)
                    this.world.setBlockState(getPos(), world.getBlockState(getPos()).withProperty(BlockCampfire.STATE,0));
                tainted = false;
                return;
            }
        }


        if(!this.world.isRemote)
        {
            List<EntityLivingBase> creatures = this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX()+1, pos.getY()+1, pos.getZ()+1).expand(12, 6, 12));

            for(EntityLivingBase elb : creatures)
            {
                if(this.tainted && this.world.rand.nextDouble() < 0.003D ) {
                    elb.addPotionEffect(new PotionEffect(PotionFluxTaint.instance, 200, 0, true, false));
                    elb.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 0, true, false));
                    elb.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 0, true, false));
                }

                if(elb instanceof EntityPlayer && !this.tainted) {
                    elb.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 1_500, 0, true, false));
                    elb.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 1_500, 0, true, false));
                }

                /**
                 * AeXiaohu modified
                 * 修复营火周围生物获得不匹配的effect
                 */
//                if(elb instanceof EntityCreature && !(elb instanceof EntityPlayer)){
//                    elb.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE,200,2,true,false));
//                }
                if(elb instanceof EntityMob && !(elb instanceof EntityPlayer)){
                    if(elb.isEntityUndead()){
                        elb.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH,200,2,true,false));
                    }else{
                        elb.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE,200,2,true,false));
                    }
                } // End of modification

            }
        }
    }
}