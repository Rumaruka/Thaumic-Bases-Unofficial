package com.rumaruka.thaumicbases.common.item;

import com.rumaruka.thaumicbases.client.creativetabs.TBCreativeTabs;
import com.rumaruka.thaumicbases.common.block.TBBlock;
import com.rumaruka.thaumicbases.init.TBBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class ItemKnozeSeeds extends Item implements IPlantable {


    public Block crops;

    public ItemKnozeSeeds(Block crops){

        this.crops = crops;
        this.setCreativeTab(TBCreativeTabs.TB_CREATIVEtabs);

    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if(facing != EnumFacing.UP){
            return EnumActionResult.FAIL;
        }
        else if (!player.canPlayerEdit(pos.offset(facing),facing,stack)){
            return EnumActionResult.FAIL;
        }
        else if((worldIn.getBlockState(pos).getBlock()== TBBlocks.crystalblockfire || worldIn.getBlockState(pos).getBlock()== TBBlocks.crystalblockwater || worldIn.getBlockState(pos).getBlock()== TBBlocks.crystalblockair || worldIn.getBlockState(pos).getBlock()== TBBlocks.crystalblockearth|| worldIn.getBlockState(pos).getBlock()== TBBlocks.crystalblockorder| worldIn.getBlockState(pos).getBlock()== TBBlocks.crystalblockentropy || worldIn.getBlockState(pos).getBlock()== TBBlocks.crystalblocktainted || worldIn.getBlockState(pos).getBlock()== TBBlocks.crystalblockmixed)&&worldIn.isAirBlock(pos.up())){
            worldIn.setBlockState(pos.up(),this.crops.getDefaultState());
            stack.shrink(1);
            return EnumActionResult.SUCCESS;
        }



        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {

        return this.crops == Blocks.NETHER_WART ? EnumPlantType.Nether:EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {

        return this.crops.getDefaultState();
    }
}
