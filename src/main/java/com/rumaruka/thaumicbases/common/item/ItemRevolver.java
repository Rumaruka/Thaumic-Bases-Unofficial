package com.rumaruka.thaumicbases.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.rumaruka.thaumicbases.api.RevolverUpgrade;
import com.rumaruka.thaumicbases.api.dummycore_remove.utils.DummyPacketHandler;
import com.rumaruka.thaumicbases.common.entity.EntityRevolverBullet;
import com.rumaruka.thaumicbases.core.TBCore;
import com.rumaruka.thaumicbases.init.TBGuiHandler;
import com.rumaruka.thaumicbases.init.TBItems;
import com.rumaruka.thaumicbases.utils.Pair;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.IWarpingGear;
import thaumcraft.common.blocks.essentia.BlockJarItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemRevolver extends Item implements IWarpingGear
{
	public static void addUpgrade(ItemStack stk, RevolverUpgrade upgrade, int level)
	{
		if(stk.hasTagCompound() && stk.getTagCompound().hasKey("tb.upgrades"))
		{
			NBTTagList upgrades = stk.getTagCompound().getTagList("tb.upgrades",10);
			for(int i = 0; i < upgrades.tagCount(); ++i)
			{
				NBTTagCompound tag = upgrades.getCompoundTagAt(i);
				int id = tag.getInteger("id");
				int llevel = tag.getInteger("level");
				if(upgrade.id == id)
				{
					llevel = level;
					tag.setInteger("level", llevel);
					return;
				}
			}
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("id", upgrade.id);
			tag.setInteger("level", level);
			upgrades.appendTag(tag);
			
		}else
		{
			if(!stk.hasTagCompound())
				stk.setTagCompound(new NBTTagCompound());
			
			if(!stk.getTagCompound().hasKey("tb.upgrades"))
				stk.getTagCompound().setTag("tb.upgrades", new NBTTagList());
			
			addUpgrade(stk,upgrade,level);
		}
	}
	
	public static int getUpgradeLevel(ItemStack stk, RevolverUpgrade upgrade)
	{
		if(stk.hasTagCompound() && stk.getTagCompound().hasKey("tb.upgrades"))
		{
			NBTTagList upgrades = stk.getTagCompound().getTagList("tb.upgrades",10);
			for(int i = 0; i < upgrades.tagCount(); ++i)
			{
				NBTTagCompound tag = upgrades.getCompoundTagAt(i);
				int id = tag.getInteger("id");
				int level = tag.getInteger("level");
				if(id < RevolverUpgrade.allUpgrades.length && RevolverUpgrade.allUpgrades[id] != null && RevolverUpgrade.allUpgrades[id].equals(upgrade))
					return level;
			}
		}
		
		return 0;
	}
	
	public static ArrayList<Pair<RevolverUpgrade,Integer>> getAllUpgradesFor(ItemStack stk)
	{
		ArrayList<Pair<RevolverUpgrade,Integer>> retLst = new ArrayList<>();
		
		if(stk.hasTagCompound() && stk.getTagCompound().hasKey("tb.upgrades"))
		{
			NBTTagList upgrades = stk.getTagCompound().getTagList("tb.upgrades",10);
			for(int i = 0; i < upgrades.tagCount(); ++i)
			{
				NBTTagCompound tag = upgrades.getCompoundTagAt(i);
				int id = tag.getInteger("id");
				int level = tag.getInteger("level");
				if(id < RevolverUpgrade.allUpgrades.length && RevolverUpgrade.allUpgrades[id] != null)
					retLst.add(new Pair<>(RevolverUpgrade.allUpgrades[id], level));
			}
		}
		
		return retLst;
	}
	
    public boolean hitEntity(ItemStack stk, EntityLivingBase attacker, EntityLivingBase attacked)
    {
    	stk.damageItem(2, attacked);
        return true;
    }

	public ActionResult<ItemStack> onItemRightClick(World w, EntityPlayer user, EnumHand handIn)
	{
		ItemStack stk = user.getHeldItem(handIn);

		if(w.isRemote)
			return new ActionResult<ItemStack>(EnumActionResult.PASS, user.getHeldItem(handIn));

		if(!stk.hasTagCompound())
			stk.setTagCompound(new NBTTagCompound());

		if(stk.getTagCompound().getInteger("shots") > 0)
		{
			EntityRevolverBullet b = new EntityRevolverBullet(w,user);
			if(!w.isRemote) {
				b.shoot(user, user.rotationPitch, user.rotationYaw, 0.0F, 3.0F, 1.0F);
				w.spawnEntity(b);
			}
			
			stk.damageItem(1, user);
			if(stk.isEmpty() || stk.getItemDamage() > stk.getMaxDamage())
				return null;
			
			DummyPacketHandler.playSoundOnServer(w, "thaumicbases:revolver.shot", user.posX, user.posY, user.posZ, 3, 1F);

			stk.getTagCompound().setDouble("barrelRotation", stk.getTagCompound().getDouble("barrelRotation")+45);

			stk.getTagCompound().setInteger("shots", stk.getTagCompound().getInteger("shots")-1);
		}
		else
		{
			if (stk.hasTagCompound() && stk.getTagCompound().hasKey("jar"))
			{
				ItemStack jar = new ItemStack(stk.getTagCompound().getCompoundTag("jar"));

				if(jar.isEmpty())
				{
					DummyPacketHandler.playSoundOnServer(w, "thaumicbases:revolver.click", user.posX, user.posY, user.posZ, 3, 2F);
					stk.getTagCompound().setDouble("barrelRotation", stk.getTagCompound().getDouble("barrelRotation")+45);

					return super.onItemRightClick(w, user, handIn);
				}

				ArrayList<Pair<RevolverUpgrade,Integer>> upgrades = getAllUpgradesFor(stk);
				boolean hasPrimal = false;
				for(Pair<RevolverUpgrade,Integer> p : upgrades)
				{
					if (p.getFirst() == RevolverUpgrade.primal) {
						hasPrimal = true;
						break;
					}

					if(hasPrimal)
						break;
				}

				int addedShots = hasPrimal ? 1 : 2;
				for(Pair<RevolverUpgrade,Integer> p : upgrades)
				{
					addedShots = p.getFirst().modifyShots(user, stk, p.getSecond(), addedShots, hasPrimal);
				}

				boolean addShots = false;

				if(!(jar.getItem() instanceof BlockJarItem))
					return super.onItemRightClick(w, user, handIn);

				AspectList aspects = ((BlockJarItem)jar.getItem()).getAspects(jar);
				if (aspects != null && aspects.size() > 0)
				{
					for (Aspect tag : aspects.getAspectsSortedByName())
					{
						if(tag == Aspect.AVERSION)
						{
							int required = addedShots < 0 ? 2 : 1;
							if(aspects.getAmount(tag) >= required)
							{
								aspects.reduce(tag, required);
								addShots = true;
								break;
							}
						}
					}
				}

				if(addShots)
				{
					stk.getTagCompound().setInteger("shots", addedShots < 0 ? 1 : addedShots);
					DummyPacketHandler.playSoundOnServer(w, "thaumicbases:revolver.reload", user.posX, user.posY, user.posZ, 3, 2F);
				}

				if(aspects != null && aspects.visSize() > 0)
				{
					((BlockJarItem)jar.getItem()).setAspects(jar, aspects);
					NBTTagCompound tag = new NBTTagCompound();
					jar.writeToNBT(tag);
					stk.setTagInfo("jar", tag);
				}else
				{
					ItemStack emptyJar = new ItemStack(BlocksTC.jarNormal,1,0);
					NBTTagCompound tag = new NBTTagCompound();
					emptyJar.writeToNBT(tag);
					stk.setTagInfo("jar", tag);
				}
			}else
			{
				DummyPacketHandler.playSoundOnServer(w, "thaumicbases:revolver.click", user.posX, user.posY, user.posZ, 3, 2F);
			}
		}
		return super.onItemRightClick(w, user, handIn);
	}

    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
    	if(!entityLiving.world.isRemote)
    	{
	    	if(entityLiving instanceof EntityPlayer)
	    	{
	    		if(((EntityPlayer) entityLiving).openContainer instanceof ContainerPlayer && entityLiving.isSneaking())
	    		{
					((EntityPlayer) entityLiving).openGui(TBCore.instance, TBGuiHandler.REVOLVER, entityLiving.world, MathHelper.floor(entityLiving.posX), MathHelper.floor(entityLiving.posY), MathHelper.floor(entityLiving.posZ));
	    		}
	    	}
    	}
        return false;
    }

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stk, @Nullable World worldIn, List<String> lst, ITooltipFlag flagIn)
	{
		super.addInformation(stk, worldIn, lst, flagIn);
		iLabel:if (stk.hasTagCompound() && stk.getTagCompound().hasKey("jar"))
		{
			ItemStack jar = new ItemStack(stk.getTagCompound().getCompoundTag("jar"));

			try
			{
				if(!(jar.getItem() instanceof BlockJarItem))
					break iLabel;

				AspectList aspects = ((BlockJarItem)jar.getItem()).getAspects(jar);

				if (aspects != null && aspects.size() > 0)
					for (Aspect tag : aspects.getAspects())
						lst.add(tag.getName() + " x " + aspects.getAmount(tag));
			}
			catch (Exception e)
			{
				//...
			}
		}
		if(stk.hasTagCompound() && stk.getTagCompound().hasKey("tb.upgrades"))
		{
			ArrayList<Pair<RevolverUpgrade,Integer>> upgrades = getAllUpgradesFor(stk);
			for(Pair<RevolverUpgrade,Integer> pr : upgrades)
			{
				lst.add(pr.getFirst().getName()+" "+ I18n.translateToLocal("enchantment.level."+pr.getSecond()));
			}
		}
	}

    public EnumRarity getRarity(ItemStack stk)
    {
        return EnumRarity.RARE;
    }

	@Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap attribs = HashMultimap.create();
		if(stack.getItem()== TBItems.revolver&&slot==EntityEquipmentSlot.MAINHAND)
			attribs.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"), "ATTACK DAMAGE", 5.0F+(getUpgradeLevel(stack, RevolverUpgrade.heavy)*2), 0));
		return attribs;
	}

	public void onUpdate(ItemStack stk, World w, Entity entity, int slot, boolean held)
	{
		super.onUpdate(stk, w, entity, slot, held);

		if(!stk.hasTagCompound())
			stk.setTagCompound(new NBTTagCompound());

		int u = getUpgradeLevel(stk, RevolverUpgrade.uvoid);
		if(u > 0)
			if ((stk.isItemDamaged()) && (entity != null) && (entity.ticksExisted % 100/u == 0) && ((entity instanceof EntityLivingBase)))
				stk.damageItem(-1, (EntityLivingBase)entity);
		
		if(stk.getTagCompound() != null)
		{
			double rotation = stk.getTagCompound().getDouble("barrelRotation");
			double renderedRotation = stk.getTagCompound().getDouble("renderedRotation");
			
			if(rotation >= 3600000)
				rotation -= 3600000;
			
			if(renderedRotation >= 3600000)
				renderedRotation -= 3600000;

			if(renderedRotation < rotation)
				renderedRotation += 10;
			
			renderedRotation = Math.min(rotation, renderedRotation);
			
			stk.getTagCompound().setDouble("barrelRotation", rotation);
			stk.getTagCompound().setDouble("renderedRotation", renderedRotation);
			
		}
	}
    
	@Override
	public int getWarp(ItemStack stk, EntityPlayer player)
	{
		return getUpgradeLevel(stk, RevolverUpgrade.uvoid);
	}

}