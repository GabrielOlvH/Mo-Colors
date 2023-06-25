package me.steven.mocolors.items;

import me.steven.mocolors.blocks.ColoredBlock;
import me.steven.mocolors.gui.PainterScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PainterItem extends Item implements NamedScreenHandlerFactory {
    public PainterItem() {
        super(new Item.Settings().maxCount(1).maxDamage(128));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block instanceof ColoredBlock) {
            if (((ColoredBlock) block).setColor(context, world, blockPos, getColor(context.getStack()))) {
                context.getStack().damage(1, context.getPlayer(), (c) -> c.sendToolBreakStatus(context.getHand()));
                return ActionResult.success(world.isClient());
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            user.openHandledScreen(this);
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new PainterScreenHandler(syncId, inv);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("item.mocolors.painter");
    }

    public static int getColor(ItemStack stack) {
        return stack.getOrCreateNbt().getInt("Color");
    }
}
