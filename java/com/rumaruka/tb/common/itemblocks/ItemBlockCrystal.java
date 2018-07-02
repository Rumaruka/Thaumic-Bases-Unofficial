package com.rumaruka.tb.common.itemblocks;

import com.rumaruka.tb.init.TBBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class ItemBlockCrystal extends ItemBlock {



    public ItemBlockCrystal(Block block) {
        super(block);

    }
/*
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if(stack.getItem()== ItemBlockSpike.getItemFromBlock(TBBlocks.crystalblockair)){
            tooltip.add(TextFormatting.YELLOW+I18n.format("tb.tooltip.aer") );
        }
        if(stack.getItem()== ItemBlockSpike.getItemFromBlock(TBBlocks.crystalblockfire)){
            tooltip.add(TextFormatting.RED+I18n.format("tb.tooltip.fire") );
        }
        if(stack.getItem()== ItemBlockSpike.getItemFromBlock(TBBlocks.crystalblockwater)){
            tooltip.add(TextFormatting.BLUE+I18n.format("tb.tooltip.water") );
        }
        if(stack.getItem()== ItemBlockSpike.getItemFromBlock(TBBlocks.crystalblockearth)){
            tooltip.add(TextFormatting.GREEN+I18n.format("tb.tooltip.earth") );
        }
        if(stack.getItem()== ItemBlockSpike.getItemFromBlock(TBBlocks.crystalblockorder)){
            tooltip.add(TextFormatting.WHITE+I18n.format("tb.tooltip.order") );
        }
        if(stack.getItem()== ItemBlockSpike.getItemFromBlock(TBBlocks.crystalblockentropy)){
            tooltip.add(TextFormatting.GRAY+I18n.format("tb.tooltip.entropy") );
        }
        if(stack.getItem()== ItemBlockSpike.getItemFromBlock(TBBlocks.crystalblockmixed)){
            tooltip.add(TextFormatting.LIGHT_PURPLE+I18n.format("tb.tooltip.mix") );
        }
        if(stack.getItem()== ItemBlockSpike.getItemFromBlock(TBBlocks.crystalblocktainted)){
            tooltip.add(TextFormatting.DARK_PURPLE+I18n.format("tb.tooltip.tainted") );
        }
    }*/
}
