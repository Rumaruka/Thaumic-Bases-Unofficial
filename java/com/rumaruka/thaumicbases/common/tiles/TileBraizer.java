package com.rumaruka.thaumicbases.common.tiles;

import com.rumaruka.thaumicbases.common.block.BlockBraizer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
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
        return oldState.getBlock() != newSate.getBlock() && super.shouldRefresh(world, pos, oldState, newSate);
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
            List<EntityLivingBase> creatures = this.world.getEntitiesWithinAABB(EntityLivingBase.class, (new AxisAlignedBB(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p(), (this.field_174879_c.func_177958_n() + 1), (this.field_174879_c.func_177956_o() + 1), (this.field_174879_c.func_177952_p() + 1))).func_72321_a(12.0D, 6.0D, 12.0D));
            for (EntityLivingBase elb : creatures) {
                if (elb instanceof net.minecraft.entity.player.EntityPlayer) {
                    elb.addPotionEffect(new PotionEffect(MobEffects.field_76429_m, 2500, 0, true, false));
                    elb.addPotionEffect(new PotionEffect(MobEffects.field_76428_l, 2500, 0, true, false));
                }
                if (elb instanceof net.minecraft.entity.monster.EntityMob) {
                    if (elb.isEntityUndead()) {
                        elb.addPotionEffect(new PotionEffect(MobEffects.field_76432_h, 200, 2, true, false));
                        continue;
                    }
                    elb.addPotionEffect(new PotionEffect(MobEffects.field_76433_i, 200, 2, true, false));
                }
            }

        }
        }

    }
}
