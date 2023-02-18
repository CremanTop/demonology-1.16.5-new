package creman.demonology.items;

import creman.demonology.utils.Materials;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.MixinEnvironment;

import javax.annotation.Nullable;
import java.util.List;

public class VoodooDoll extends ItemMadeOfMaterial
{
    public VoodooDoll()
    {
        super(Materials.CLOTH_MATERIAL);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        PlayerEntity target = null;
        if (!worldIn.isRemote)
        {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString("name", playerIn.getName().getString());
            nbt.putBoolean("isInverted", false);
            itemstack.setTag(nbt);

            if (itemstack.hasTag())
            {
                if (!itemstack.getTag().getString("name").isEmpty())
                {
                    List<? extends PlayerEntity> players = worldIn.getPlayers();
                    for(PlayerEntity player : players)
                    {
                        if(player.getName().getString().equals(itemstack.getTag().getString("name")))
                        {
                            target = player;
                        }
                    }
                    if (target == null)
                    {
                        playerIn.sendMessage(new TranslationTextComponent("demonology.message.voodoo.not_found_player", itemstack.getTag().getString("name")), null);
                        return ActionResult.resultSuccess(itemstack);
                    }
                }
            }

            if (target != null)
            {
                //itemstack.setDamage(itemstack.getDamage() + 1);
                //itemstack.attemptDamageItem(1, worldIn.rand, (ServerPlayerEntity) playerIn);
                itemstack.damageItem(1, playerIn, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
                if(!itemstack.getTag().getBoolean("isInverted"))
                {
                    if (itemstack.getDamage() > 0)
                    {
                        target.attackEntityFrom(DamageSource.MAGIC, 4.0F);
                    }
                    else
                    {
                        target.attackEntityFrom(DamageSource.MAGIC, Float.MAX_VALUE);
                    }
                }
                else
                {
                    if (itemstack.getDamage() > 0)
                    {
                        target.setHealth(target.getHealth() + 6.0F);
                    }
                    else
                    {
                        target.setHealth(target.getMaxHealth());
                    }
                }
                //worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ, Sounds.PIPE, SoundCategory.NEUTRAL, 0.5F, 1.0F);
            }
        }
        return ActionResult.resultSuccess(itemstack);
    }

    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!stack.hasTag()) return;
        CompoundNBT nbt = stack.getTag();
        if(nbt.getString("name").isEmpty())return;
        tooltip.add(ITextComponent.getTextComponentOrEmpty(TextFormatting.GRAY + I18n.format("demonology.information.voodoo", nbt.getString("name"))));
    }

    //@SideOnly(MixinEnvironment.Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return super.hasEffect(stack) || stack.getTag().getBoolean("isInverted");
        //return false;
    }
}
