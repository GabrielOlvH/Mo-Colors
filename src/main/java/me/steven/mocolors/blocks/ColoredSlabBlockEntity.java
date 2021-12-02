package me.steven.mocolors.blocks;

import com.google.common.base.Preconditions;
import me.steven.mocolors.MoColors;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachmentBlockEntity;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class ColoredSlabBlockEntity extends BlockEntity implements RenderAttachmentBlockEntity {

    private int topColor = -1;
    private int bottomColor = -1;

    public ColoredSlabBlockEntity(BlockPos pos, BlockState blockState) {
        super(MoColors.COLORED_SLAB_BLOCK_ENTITY_TYPE, pos, blockState);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public final NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = super.toInitialChunkDataNbt();
        writeNbt(nbt);
        return nbt;
    }

    public int getTopColor() {
        return topColor;
    }

    public void setTopColor(int topColor) {
        this.topColor = topColor;
    }

    public int getBottomColor() {
        return bottomColor;
    }

    public void setBottomColor(int bottomColor) {
        this.bottomColor = bottomColor;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        this.topColor = tag.getInt("top");
        this.bottomColor = tag.getInt("bottom");

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT && world != null) {
            MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, null, null, 8);
        }
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        tag.putInt("top", topColor);
        tag.putInt("bottom", bottomColor);
    }

    @Override
    public @Nullable Object getRenderAttachmentData() {
        return new int[] {topColor, bottomColor};
    }

    public void sync() {
        Preconditions.checkNotNull(world); // Maintain distinct failure case from below
        if (!(world instanceof ServerWorld))
            throw new IllegalStateException("Cannot call sync() on the logical client! Did you check world.isClient first?");

        ((ServerWorld) world).getChunkManager().markForUpdate(getPos());
    }
}
