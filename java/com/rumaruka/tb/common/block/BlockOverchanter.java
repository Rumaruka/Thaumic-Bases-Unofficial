package com.rumaruka.tb.common.block;

import DummyCore.Client.IModelRegisterer;
import com.rumaruka.tb.common.tiles.TileOverchanter;
import com.rumaruka.tb.core.TBCore;
import com.rumaruka.tb.init.TBGuiHandler;
import com.rumaruka.tb.utils.TBGui;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nullable;

public class BlockOverchanter extends BlockContainer implements IModelRegisterer,ITileEntityProvider {
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

    private static boolean keepInventory;


    public BlockOverchanter() {
        super(Material.ROCK, MapColor.PURPLE);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }


    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileOverchanter();
    }

  /*  @Override
        public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {

        if (w.isRemote)
        {
            return true;
        }
        else
        {
            TileEntity tileentity = w.getTileEntity(pos);

            if (tileentity instanceof TileOverchanter)
            {
                // playerIn.displayGUIChest((TileEntityCoalGrinder)tileentity);
                p.openGui(TBCore.instance, TBGuiHandler.OVERCHANTER, w, pos.getX(), pos.getY(), pos.getZ());
                System.out.print(TBGuiHandler.OVERCHANTER);
            }

            return true;
        }




    }*/

    @Override
    public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (w.isRemote)
        {
            return true;
        }
        else
        {
            TileEntity tileentity = w.getTileEntity(pos);

            if (tileentity instanceof TileOverchanter )
            {
                // playerIn.displayGUIChest((TileEntityCoalGrinder)tileentity);
                p.openGui(TBCore.instance, TBGuiHandler.OVERCHANTER, w, pos.getX(), pos.getY(), pos.getZ());
            }

            return true;
        }
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!keepInventory)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileOverchanter)
            {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileOverchanter)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }


    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),0,new ModelResourceLocation("thaumicbases:overchanter","inventory"));
    }
}
