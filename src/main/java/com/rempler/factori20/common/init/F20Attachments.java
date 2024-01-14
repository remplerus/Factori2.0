package com.rempler.factori20.common.init;

import com.rempler.factori20.api.chunk.ChunkResourceData;
import com.rempler.factori20.utils.F20Constants;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class F20Attachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, F20Constants.MODID);
    //TODO: figure out how attachments work Sadge
    public static final Supplier<AttachmentType<ChunkResourceData>> CHUNK_RESOURCES = ATTACHMENTS.register("chunk_resource_data",
            () -> AttachmentType.builder(ChunkResourceData::new).build());
    public static final Supplier<AttachmentType<ItemStackHandler>> ITEM_HANDLER = ATTACHMENTS.register("item_handler",
            () -> AttachmentType.serializable(() -> new ItemStackHandler(1)).build());
}
