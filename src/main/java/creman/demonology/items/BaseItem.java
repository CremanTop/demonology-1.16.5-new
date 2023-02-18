package creman.demonology.items;

import creman.demonology.utils.ItemGroups;
import net.minecraft.item.Item;

public class BaseItem extends Item
{
    public BaseItem(Item.Properties builder)
    {
        super(builder.group(ItemGroups.ITEM_GROUP));
    }
}
