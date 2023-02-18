package creman.demonology.items;

import creman.demonology.utils.Materials;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ItemMadeOfMaterial extends BaseItem
{
    private final IItemTier material;

    public ItemMadeOfMaterial(Materials material)
    {
        super(new Item.Properties().defaultMaxDamage(material.getMaxUses()));
        this.material = material;
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return this.material.getRepairMaterial().test(repair);
    }

    public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }

    public IItemTier getTier() {
        return this.material;
    }
}
