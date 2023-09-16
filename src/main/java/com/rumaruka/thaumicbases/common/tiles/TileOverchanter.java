package com.rumaruka.thaumicbases.common.tiles;

import com.rumaruka.thaumicbases.api.dummycore_remove.utils.AllUtils;
import com.rumaruka.thaumicbases.api.dummycore_remove.utils.MathUtils;
import com.rumaruka.thaumicbases.common.inventory.ContainerOverchanter;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.casters.IInteractWithCaster;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.events.EssentiaHandler;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TileOverchanter extends TileEntityLockable implements IInventory, IInteractWithCaster, ITickable {


    public ItemStack inventory = ItemStack.EMPTY;
    private int enchantingTime;
    private boolean xpAbsorbed;
    private boolean isEnchantingStarted;
    private int syncTimer;
    private int ticksExisted;


    @Override
    public int getSizeInventory() {
        return 1;
    }
    @Override
    public void update() {

        ++ticksExisted;
        if(syncTimer <= 0)
        {
            syncTimer = 100;
            NBTTagCompound tg = new NBTTagCompound();
            tg.setInteger("0", enchantingTime);
            tg.setBoolean("1", xpAbsorbed);
            tg.setBoolean("2", isEnchantingStarted);
            tg.setInteger("x", this.pos.getX());
            tg.setInteger("y", this.pos.getY());
            tg.setInteger("z", this.pos.getZ());
        }else
            --syncTimer;

        if(this.inventory.isEmpty())
        {
            isEnchantingStarted = false;
            xpAbsorbed = false;
            enchantingTime = 0;
        }else
        {
            if(this.isEnchantingStarted)
            {
                if(ticksExisted % 20 == 0)
                {
                    this.world.playSound(pos.getX(),pos.getY(),pos.getZ(),SoundsTC.infuserstart,SoundCategory.BLOCKS,1f,1.0f,false);
                    if(EssentiaHandler.drainEssentia(this, Aspect.MAGIC, null, 8, false, 8))
                    {
                        ++enchantingTime;
                        if(enchantingTime >= 16 && !this.xpAbsorbed)
                        {
                            List<EntityPlayer> players = this.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX()+1, pos.getY()+1, pos.getZ()+1).expand(6, 3, 6).expand(-6, -3, -6));

                            if(!players.isEmpty())
                            {


                                for(int i = 0; i < players.size(); ++i)
                                {
                                    EntityPlayer p = players.get(i);



                                    if(p.experienceLevel >= 30)
                                    {
                                        p.attackEntityFrom(DamageSource.MAGIC, 8);
                                        this.world.playSound(pos.getX(),pos.getY(),pos.getZ(),SoundsTC.zap,SoundCategory.BLOCKS,1f,1.0f,false);
                                        p.experienceLevel -= 30;
                                        xpAbsorbed = true;
                                        break;
                                    }
                                }
                            }
                        }

                        if(xpAbsorbed && enchantingTime >= 32)
                        {
                            NBTTagList nbttaglist = this.inventory.getEnchantmentTagList();

                            for(int i = 0; i < nbttaglist.tagCount(); ++i)
                            {
                                int enchId = this.findEnchantment(inventory);
                                Enchantment ench = Enchantment.getEnchantmentByID(enchId);
                                NBTTagCompound tag = nbttaglist.getCompoundTagAt(i);

                                if (tag != null && Enchantment.getEnchantmentByID(tag.getShort("id")) == ench) {
                                    tag.setShort("lvl", (short) (tag.getShort("lvl") + 1));
                                    NBTTagCompound stackTag = AllUtils.getStackTag(this.inventory);
                                    if (!stackTag.hasKey("overchants")) {
                                        stackTag.setIntArray("overchants", new int[]{enchId});
                                    } else {
                                        int[] arrayInt = stackTag.getIntArray("overchants");
                                        int[] newArrayInt = new int[arrayInt.length + 1];
                                        System.arraycopy(arrayInt, 0, newArrayInt, 0, arrayInt.length);
                                        newArrayInt[newArrayInt.length - 1] = enchId;
                                        stackTag.setIntArray("overchants", newArrayInt);
                                    }
                                    break;
                                } // End of modification

                            }
                            isEnchantingStarted = false;
                            xpAbsorbed = false;
                            enchantingTime = 0;
                            this.world.playSound(pos.getX(),pos.getY(),pos.getZ(),SoundsTC.wand,SoundCategory.BLOCKS,1f,1.0f,false);
                        }

                    }else
                    {
                        --enchantingTime;
                    }
                }
            }
        }
    }


    private boolean canStartEnchanting(){
        if(!this.isEnchantingStarted)
            if(!this.inventory.isEmpty()){
                if(this.inventory.getEnchantmentTagList().tagCount() > 0){
                    return findEnchantment(inventory) != -1;

                }

            }
        return false;
    }

    private  int findEnchantment(ItemStack enchated) {
        NBTTagCompound stackTag = AllUtils.getStackTag(this.inventory);
        Map<Enchantment, Integer> ench = EnchantmentHelper.getEnchantments(enchated);
        Set<Enchantment> keys = ench.keySet();
        Iterator<Enchantment>$i = keys.iterator();

        while ($i.hasNext()) {
            int i = Enchantment.getEnchantmentID($i.next());
            if (!stackTag.hasKey("overchants")) {
                return i;
            }
            int[] overchants = stackTag.getIntArray("overchants");
            if (MathUtils.arrayContains(overchants, i))
                continue;
            return i;
        }
        return -1; // End of modification

    }

    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        enchantingTime = pkt.getNbtCompound().getInteger("0");
        xpAbsorbed = pkt.getNbtCompound().getBoolean("1");
        isEnchantingStarted = pkt.getNbtCompound().getBoolean("2");
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (!this.inventory.isEmpty())
        {
            ItemStack itemstack;

            if (this.inventory.getMaxStackSize() <= count)
            {
                itemstack = this.inventory;
                this.inventory = ItemStack.EMPTY;
                this.markDirty();
                return itemstack;
            }
            itemstack = this.inventory.splitStack(count);

            if (this.inventory.getMaxStackSize() == 0)
            {
                this.inventory = ItemStack.EMPTY;
            }

            this.markDirty();
            return itemstack;
        }
        return ItemStack.EMPTY;
    }


    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack old = inventory.copy();
        this.inventory = ItemStack.EMPTY;
        return old;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory = stack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return player.dimension == this.world.provider.getDimension() && !this.world.isAirBlock(pos);
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return stack.hasTagCompound() && stack.getEnchantmentTagList() != null;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        enchantingTime = compound.getInteger("enchTime");
        xpAbsorbed = compound.getBoolean("xpAbsorbed");
        isEnchantingStarted = compound.getBoolean("enchStarted");
        inventory = new ItemStack(compound.getCompoundTag("itm"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("enchTime", enchantingTime);
        compound.setBoolean("xpAbsorbed",xpAbsorbed);
        compound.setBoolean("enchStarted", isEnchantingStarted);

        compound.setTag("itm", inventory.serializeNBT());
        return compound;
    }

    @Override
    public boolean onCasterRightClick(World world, ItemStack itemStack, EntityPlayer entityPlayer, BlockPos blockPos, EnumFacing enumFacing, EnumHand enumHand) {
        if(canStartEnchanting() && entityPlayer.isSneaking())
        {
            isEnchantingStarted = true;
            entityPlayer.swingArm(EnumHand.MAIN_HAND);
            syncTimer = 0;
            this.world.playSound(pos.getX(), pos.getY(), pos.getZ(),SoundsTC.craftstart,SoundCategory.BLOCKS,0.5f,1.0f,false);
            return true;
        }
        return false;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        inventory= ItemStack.EMPTY;

    }



    @Override
    public String getName() {
        return "thaumicbases.overchanter";
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }


    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerOverchanter(playerInventory, this);
    }
    public String getGuiID()
    {
        return "thaumicbases:overchanter";
    }

}