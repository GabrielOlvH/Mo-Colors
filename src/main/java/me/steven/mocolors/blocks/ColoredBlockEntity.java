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
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class ColoredBlockEntity extends BlockEntity implements RenderAttachmentBlockEntity {

    private int color = -1;

    public ColoredBlockEntity(BlockPos pos, BlockState blockState) {
        super(MoColors.COLORED_BLOCK_ENTITY_TYPE, pos, blockState);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int rgb) {
        this.color = rgb;
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

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        this.color = tag.getInt("c");

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT && world != null) {
            MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, null, null, 8);
        }
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        tag.putInt("c", color);
    }

    public void sync() {
        Preconditions.checkNotNull(world); // Maintain distinct failure case from below
        if (!(world instanceof ServerWorld))
            throw new IllegalStateException("Cannot call sync() on the logical client! Did you check world.isClient first?");

        ((ServerWorld) world).getChunkManager().markForUpdate(getPos());
    }

    @Override
    public @Nullable Object getRenderAttachmentData() {
        return color;
    }
}
