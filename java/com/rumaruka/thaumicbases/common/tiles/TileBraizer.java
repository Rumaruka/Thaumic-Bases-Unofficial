package com.rumaruka.thaumicbases.common.tiles;

import com.rumaruka.thaumicbases.common.block.BlockBraizer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.lib.events.EssentiaHandler;

import java.util.List;

public class TileBraizer extends TileEntity implements ITickable {

    public int burnTime;
    public int uptime;

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return  oldState.getBlock()==newSate.getBlock() ? false: super.shouldRefresh(world, pos, oldState, newSate);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        burnTime = compound.getInteger("burnTime");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        return compound;
    }


    @Override
    public void update() {
        uptime++;
        if (burnTime > 0) {
            burnTime--;
        } else {
            if (EssentiaHandler.drainEssentia(this, Aspect.FIRE, null, 6, false, 6)) {
                burnTime = 1600;
                this.world.setBlockState(getPos(), world.getBlockState(getPos()).withProperty(BlockBraizer.STATE, 1));
            }
        }
        if(!this.world.isRemote){
            if(burnTime<=0){
                int metadata = getWorld().getBlockState(getPos()).getValue(BlockBraizer.STATE);
                if(metadata==1){
                    this.world.setBlockState(getPos(), world.getBlockState(getPos()).withProperty(BlockBraizer.STATE, 0));

                }
                return;

            }
        }
        if(!this.world.isRemote)
        {
            List<EntityLivingBase> creatures = this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX()+1, pos.getY()+1, pos.getZ()+1).expand(12, 6, 12));

            for(EntityLivingBase elb : creatures)
            {


                if(elb instanceof EntityPlayer) {
                    elb.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 2_500, 0, true, false));
                    elb.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 2_500, 0, true, false));
                }


                /**
                 * AeXiaohu modified
                 * 修复火盆周围生物获得不匹配的effect
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