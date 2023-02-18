package creman.demonology.utils;

import creman.demonology.items.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;

import static creman.demonology.Demonology.MOD_ID;

public class ItemGroups
{
    public static ItemGroup createGroup(String id, RegistryObject<Item> icon)
    {
        return new ItemGroup(id) {
            @Override
            public ItemStack createIcon()
            {
                return new ItemStack(icon.get());
            }
        };
    }
    public static final ItemGroup ITEM_GROUP = createGroup(MOD_ID + "_tab", Items.VOODOO_DOLL);
}
