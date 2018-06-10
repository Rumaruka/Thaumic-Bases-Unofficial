package com.rumaruka.tb.common.tiles;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.Lightning;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import com.google.common.collect.Maps;
import com.rumaruka.tb.common.inventory.ContainerOverchanter;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.casters.IInteractWithCaster;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.events.EssentiaHandler;

import javax.annotation.Nullable;
import java.util.*;

public class TileOverchanter extends TileEntityLockable implements IInventory, IInteractWithCaster, ITickable {


    public ItemStack inventory = ItemStack.EMPTY;
    public int enchantingTime;
    public boolean xpAbsorbed;
    public boolean isEnchantingStarted;
    public int syncTimer;
    int ticksExisted;

    public Lightning renderedLightning;


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
            MiscUtils.syncTileEntity(tg, 0);
        }else
            --syncTimer;

        if(this.inventory.isEmpty())
        {
            isEnchantingStarted = false;
            xpAbsorbed = false;
            enchantingTime = 0;
            renderedLightning = null;
        }else
        {
            if(this.isEnchantingStarted)
            {
                if(ticksExisted % 20 == 0)
                {
                    renderedLightning = new Lightning(this.world.rand, new Coord3D(0,0,0), new Coord3D(MathUtils.randomDouble(this.world.rand)/50,MathUtils.randomDouble(this.world.rand)/50,MathUtils.randomDouble(this.world.rand)/50), 0.3F, 1,0,1);
                   // this.worldObj.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "thaumcraft:infuserstart", 1F, 1.0F);
                    this.world.playSound(pos.getX(),pos.getY(),pos.getZ(),SoundsTC.infuserstart,SoundCategory.BLOCKS,1f,1.0f,false);
                    if(EssentiaHandler.drainEssentia(this, Aspect.MAGIC, null, 8, false, 8))
                    {
                        ++enchantingTime;
                        if(enchantingTime >= 16 && !this.xpAbsorbed)
                        {
                            List<EntityPlayer> players = this.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX()+1, pos.getY()+1, pos.getZ()+1).expand(6, 3, 6));
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
                            int enchId = this.findEnchantment(inventory);
                            Enchantment ench = Enchantment.getEnchantmentByID(enchId);
                            NBTTagList nbttaglist = this.inventory.getEnchantmentTagList();
                            for(int i = 0; i < nbttaglist.tagCount(); ++i)
                            {
                                NBTTagCompound tag = nbttaglist.getCompoundTagAt(i);
                                if(tag != null && Enchantment.getEnchantmentByID(enchId)==ench)
                                {

                                    tag.setShort("lvl", (short) (Integer.valueOf(tag.getShort("lvl"))+5));
                                    NBTTagCompound stackTag = MiscUtils.getStackTag(inventory);
                                    if(!stackTag.hasKey("overchants"))
                                    {
                                        stackTag.setIntArray("overchants", new int[]{enchId});
                                    }else
                                    {
                                        int[] arrayInt = stackTag.getIntArray("overchants");
                                        int[] newArrayInt = new int[arrayInt.length+5];
                                        for(int j = 0; j < arrayInt.length; ++j)
                                        {
                                            newArrayInt[j] = arrayInt[j];
                                        }
                                        newArrayInt[newArrayInt.length+5]=enchId;

                                        stackTag.setIntArray("overchants", newArrayInt);
                                    }
                                    break;
                                }
                            }
                            isEnchantingStarted = false;
                            xpAbsorbed = false;
                            enchantingTime = 0;
                            renderedLightning = null;
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


    public boolean canStartEnchanting(){
        if(!this.isEnchantingStarted)
            if(!this.inventory.isEmpty()){
                if(this.inventory.getEnchantmentTagList()!=null&& this.inventory.getEnchantmentTagList().tagCount() > 0){
                    if(findEnchantment(inventory)!=-1){
                        return true;
                    }

                }

            }
            return false;
    }

    private int findEnchantment(ItemStack enchated) {
        NBTTagCompound stackTag = MiscUtils.getStackTag(inventory);
        Map<Enchantment, Integer> ench = EnchantmentHelper.getEnchantments(enchated);
        Set<Enchantment> keys = ench.keySet();
        Iterator<Enchantment>$i = keys.iterator();

        while ($i.hasNext()){
            Enchantment enchantment = $i.next();
            enchantment.getEnchantmentByID(stackTag.getShort("id"));
            int j = stackTag.getShort("lvl");

            if(!stackTag.hasKey("overchants")){
                return j;
            }
            int[]overchants = stackTag.getIntArray("overchants");
            if(MathUtils.arrayContains(overchants,j));
                continue;
        }
        return -1;
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
        if(canStartEnchanting()&&entityPlayer.isSneaking())
        {
            isEnchantingStarted = true;
            //entityPlayer.swingingHand(EnumHand.MAIN_HAND);
            entityPlayer.swingArm(EnumHand.MAIN_HAND);
            syncTimer = 0;
            //this.worldObj.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "thaumcraft:craftstart", 0.5F, 1.0F);
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
        return "tb.overchanter";
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation(getName());
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
        return "minecraft:overchanter";
    }

  }
