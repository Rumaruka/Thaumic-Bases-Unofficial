package com.rumaruka.tb.common.item;


import com.rumaruka.tb.init.TBBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import thaumcraft.common.items.consumables.ItemBucketDeath;

public class ItemPyrofluidBucket extends Item {
    public ItemPyrofluidBucket()
    {
        super();
        this.setHasSubtypes(false);
        this.setMaxStackSize(1);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
        boolean flag = false;
        RayTraceResult rayTraceResult = this.rayTrace(worldIn, player, flag);
        if (rayTraceResult == null) {
            return new ActionResult(EnumActionResult.SUCCESS, (Object)player.getHeldItem(hand));
        }
        ActionResult ret = ForgeEventFactory.onBucketUse((EntityPlayer)player, (World)worldIn, (ItemStack)player.getHeldItem(hand), (RayTraceResult)rayTraceResult);
        if (ret != null) {
            return ret;
        }
        if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = rayTraceResult.getBlockPos();
            if (!worldIn.isBlockModifiable(player, blockpos)) {
                return new ActionResult(EnumActionResult.SUCCESS, (Object)player.getHeldItem(hand));
            }
            BlockPos blockpos1 = blockpos.offset(rayTraceResult.sideHit);
            if (!player.canPlayerEdit(blockpos1, rayTraceResult.sideHit, player.getHeldItem(hand))) {
                return new ActionResult(EnumActionResult.SUCCESS, (Object)player.getHeldItem(hand));
            }
            if (this.tryPlaceContainedLiquid(worldIn, blockpos1) && !player.capabilities.isCreativeMode) {
                return new ActionResult(EnumActionResult.SUCCESS, (Object)new ItemStack(Items.BUCKET));
            }
        }
        return new ActionResult(EnumActionResult.SUCCESS, (Object)player.getHeldItem(hand));
    }

    public boolean tryPlaceContainedLiquid(World world, BlockPos pos) {
        boolean flag;
        Material material = world.getBlockState(pos).getMaterial();
        boolean bl = flag = !material.isSolid();
        if (!world.isAirBlock(pos) && !flag) {
            return false;
        }
        if (!world.isRemote && flag && !material.isLiquid()) {
            world.destroyBlock(pos, true);
        }
        world.setBlockState(pos, TBBlocks.pyrofluid.getDefaultState());
        return true;
    }


}
