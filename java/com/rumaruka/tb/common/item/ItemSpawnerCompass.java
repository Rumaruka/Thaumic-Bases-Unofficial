package com.rumaruka.tb.common.item;

import DummyCore.Utils.Coord3D;
import com.rumaruka.tb.utils.TBUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import thaumcraft.client.fx.FXDispatcher;

import java.util.Hashtable;

public class ItemSpawnerCompass extends Item {

    Hashtable<Entity, Object> beams = new Hashtable<Entity, Object>();
    public ItemSpawnerCompass(){
        isFull3D();
        setMaxStackSize(1);
        setMaxDamage(0);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World w, EntityPlayer player, EnumHand handIn) {
        ItemStack itemstack = player.getHeldItem(handIn);
        if(w != null && w.isRemote && itemstack.isEmpty()   && !(player instanceof FakePlayer))
        {
            if(beams.containsKey(player))
                beams.remove(player);
        }
        if(w != null && w.isRemote && !(player instanceof FakePlayer) && itemstack.isEmpty() )
        {
            EntityPlayer p = (EntityPlayer) player;
            Coord3D closest = TBUtils.getClosestLoadedClientSpawner(p);
            if(closest != null)
            {
                Vec3d vec = player.getLookVec();

                Vec3d createdVec = new Vec3d(closest.x+0.5D, closest.y+0.5D , closest.z+0.5D);
                Vec3d posTo0 = new Vec3d(createdVec.x - p.posX, createdVec.y - (p.posY+p.getEyeHeight()), createdVec.z - p.posZ);
                double xCoord = (posTo0.x < 0 ? Math.max(posTo0.x, -10) : Math.min(posTo0.x, 10))/20;
                double yCoord = (posTo0.y < 0 ? Math.max(posTo0.y, -10) : Math.min(posTo0.y, 10))/20;
                double zCoord = (posTo0.z < 0 ? Math.max(posTo0.z, -10) : Math.min(posTo0.z, 10))/20;
                posTo0 = new Vec3d(xCoord,yCoord,zCoord);
                createdVec = new Vec3d(p.posX+vec.x+posTo0.x, p.posY+p.getEyeHeight()+vec.y+posTo0.y, p.posZ+vec.z+posTo0.z);

                Object beam = FXDispatcher.INSTANCE.beamBore(p.posX+vec.x,p.posY+p.getEyeHeight()+vec.y,p.posZ+vec.z, createdVec.x, createdVec.y, createdVec.z, 2, 0x55555, false, 1.08F, null, 1);
                beams.put(p, beam);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }



    @Override
    public void onUpdate(ItemStack stk, World w, Entity player, int slotNum, boolean isCurrentItem)
    {
        if(w != null && w.isRemote && !isCurrentItem && player instanceof EntityPlayer && !(player instanceof FakePlayer))
        {
            if(beams.containsKey(player))
                beams.remove(player);
        }
        if(w != null && w.isRemote && player instanceof EntityPlayer && !(player instanceof FakePlayer) && isCurrentItem)
        {
            EntityPlayer p = (EntityPlayer) player;
            Coord3D closest = TBUtils.getClosestLoadedClientSpawner(p);
            if(closest != null)
            {
                Vec3d vec = player.getLookVec();

                Vec3d createdVec = new Vec3d(closest.x+0.5D, closest.y+0.5D , closest.z+0.5D);
                Vec3d posTo0 = new Vec3d(createdVec.x - p.posX, createdVec.y - (p.posY+p.getEyeHeight()), createdVec.z - p.posZ);
                double xCoord = (posTo0.x < 0 ? Math.max(posTo0.x, -10) : Math.min(posTo0.x, 10))/20;
                double yCoord = (posTo0.y < 0 ? Math.max(posTo0.y, -10) : Math.min(posTo0.y, 10))/20;
                double zCoord = (posTo0.z < 0 ? Math.max(posTo0.z, -10) : Math.min(posTo0.z, 10))/20;
                posTo0 = new Vec3d(xCoord,yCoord,zCoord);
                createdVec = new Vec3d(p.posX+vec.x+posTo0.x, p.posY+p.getEyeHeight()+vec.y+posTo0.y, p.posZ+vec.z+posTo0.z);

                Object beam = FXDispatcher.INSTANCE.beamBore(p.posX+vec.x,p.posY+p.getEyeHeight()+vec.y,p.posZ+vec.z, createdVec.x, createdVec.y, createdVec.z, 2, 0x55555, false, 1.08F, null, 1);
                beams.put(p, beam);
            }
        }
    }
}
