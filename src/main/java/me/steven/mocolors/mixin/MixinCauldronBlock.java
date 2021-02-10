package me.steven.mocolors.mixin;

import me.steven.mocolors.blocks.ColoredBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CauldronBlock.class)
public class MixinCauldronBlock {
    @Inject(method = "onUse", at = @At("RETURN"), cancellable = true)
    private void mocolors_clean(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {

        if (cir.getReturnValue() != ActionResult.SUCCESS && !world.isClient()) {
            int level = state.get(CauldronBlock.LEVEL);
            if (level <= 0) return;
            ItemStack itemStack = player.getStackInHand(hand);
            Item item = itemStack.getItem();
            if (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof ColoredBlock) {
                Item convert = ((ColoredBlock) ((BlockItem) item).getBlock()).getCleanItem();
                if (convert != null) {
                    world.setBlockState(pos, state.with(CauldronBlock.LEVEL, level - 1));
                    player.setStackInHand(hand, new ItemStack(convert, itemStack.getCount()));
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }
        }
    }
}
