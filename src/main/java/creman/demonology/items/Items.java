package creman.demonology.items;

import creman.demonology.Demonology;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Items
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Demonology.MOD_ID);

    public static final RegistryObject<Item> VOODOO_DOLL = ITEMS.register("voodoo_doll", VoodooDoll::new);
}
