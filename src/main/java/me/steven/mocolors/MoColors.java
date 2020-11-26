package me.steven.mocolors;

import me.steven.mocolors.blocks.*;
import me.steven.mocolors.blocks.models.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MoColors implements ModInitializer {

	public static final String MOD_ID = "mocolors";

	public static final Block COLORED_GLASS = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "colored_glass"), new ColoredGlassBlock());
	public static final Item COLORED_GLASS_ITEM = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "colored_glass"), new BlockItem(COLORED_GLASS, new Item.Settings()));

	public static final Block COLORED_SLIME = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "colored_slime"), new ColoredSlimeBlock());
	public static final Item COLORED_SLIME_ITEM = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "colored_slime"), new BlockItem(COLORED_SLIME, new Item.Settings()));

	public static final Block COLORED_GLASS_PANE = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "colored_glass_pane"), new ColoredGlassPaneBlock());
	public static final Item COLORED_GLASS_PANE_ITEM = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "colored_glass_pane"), new BlockItem(COLORED_GLASS_PANE, new Item.Settings()));

	public static final Block COLORED_WOOL = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "colored_wool"), new ColoredWoolBlock());
	public static final Item COLORED_WOOL_ITEM = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "colored_wool"), new BlockItem(COLORED_WOOL, new Item.Settings()));

	public static final BlockEntityType<ColoredBlockEntity> COLORED_BLOCK_ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "colored_block"), BlockEntityType.Builder.create(ColoredBlockEntity::new, COLORED_GLASS, COLORED_SLIME, COLORED_GLASS_PANE).build(null));

	@Override
	public void onInitialize() {
		ColoredBakedModel glassBakedModel = new ColoredGlassBakedModel();
		ColoredBakedModel slimeBakedModel = new ColoredSlimeBakedModel();
		ColoredBakedModel glassPaneBakedModel = new ColoredGlassPaneModel();
		ColoredBakedModel woolBakedModel = new ColoredWoolBakedModel();
		ModelLoadingRegistry.INSTANCE.registerVariantProvider((res) -> (modelId, ctx) -> {
			if (modelId.getNamespace().equals("mocolors") && modelId.getPath().equals("colored_glass"))
				return glassBakedModel;
			else if (modelId.getNamespace().equals("mocolors") && modelId.getPath().equals("colored_slime"))
				return slimeBakedModel;
			else if (modelId.getNamespace().equals("mocolors") && modelId.getPath().equals("colored_glass_pane"))
				return glassPaneBakedModel;
			else if (modelId.getNamespace().equals("mocolors") && modelId.getPath().equals("colored_wool"))
				return woolBakedModel;
			return null;
		});
	}
}
