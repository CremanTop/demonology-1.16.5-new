package creman.demonology.items;

import creman.demonology.utils.Materials;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class VoodooDoll extends ItemMadeOfMaterial
{
    public VoodooDoll()
    {
        super(Materials.CLOTH_MATERIAL);
    }

    @Override
    @MethodsReturnNonnullByDefault
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
    {
        ItemStack heldStack = player.getHeldItem(hand);
        if (world.isRemote)
        {
            return ActionResult.resultPass(heldStack);
        }
        PlayerEntity target;

        CompoundNBT nbt = heldStack.getTag();
        nbt.putString("name", player.getName().getString());
        nbt.putBoolean("isInverted", false);
        assert world.getServer() != null;
        target = world.getServer().getPlayerList().getPlayerByUsername(nbt.getString("name"));

        heldStack.damageItem(1, player, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        if(nbt.getBoolean("isInverted"))
        {
            target.setHealth(heldStack.getDamage() > 0 ? target.getHealth() + 6.0F : target.getMaxHealth());
        }
        else
        {
            target.attackEntityFrom(DamageSource.MAGIC, heldStack.getDamage() > 0 ? 4.0F : Float.MAX_VALUE);
        }
        //worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ, Sounds.PIPE, SoundCategory.NEUTRAL, 0.5F, 1.0F);
        return ActionResult.resultSuccess(heldStack);
    }

    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        stack.damageItem(1, attacker, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        if (!stack.hasTag())
        {
            return;
        }
        CompoundNBT nbt = stack.getTag();
        if(nbt.getString("name").isEmpty())
        {
            return;
        }
        tooltip.add(ITextComponent.getTextComponentOrEmpty(TextFormatting.GRAY + I18n.format("demonology.information.voodoo", nbt.getString("name"))));
    }

    //@SideOnly(MixinEnvironment.Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return super.hasEffect(stack) || stack.getTag().getBoolean("isInverted");
        //return false;
    }
}
