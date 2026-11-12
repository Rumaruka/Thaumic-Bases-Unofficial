package com.rumaruka.thaumicbases.common.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemTBAnvilBlock extends ItemMultiTexture
{
    public ItemTBAnvilBlock(Block block)
    {
        super(block, block, new String[] {"intact", "slightly_damaged", "very_damaged"});

        this.addPropertyOverride(new ResourceLocation("thaumic_anvil"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if(stack.getItemDamage() == 0)
                    return 0F;
                if (stack.getItemDamage() == 1)
                    return 1.0F;
                if (stack.getItemDamage() == 2)
                    return 2.0F;
                return 0.0F;
            }
        });

        this.addPropertyOverride(new ResourceLocation("void_anvil"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if(stack.getItemDamage() == 0)
                    return 0F;
                if (stack.getItemDamage() == 1)
                    return 1.0F;
                if (stack.getItemDamage() == 2)
                    return 2.0F;
                return 0.0F;
            }
        });
    }

    public int getMetadata(int damage)
    {
        return damage << 2;
    }
}