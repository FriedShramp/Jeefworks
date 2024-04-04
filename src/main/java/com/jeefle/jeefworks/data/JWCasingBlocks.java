package com.jeefle.jeefworks.data;

import com.gregtechceu.gtceu.api.block.RendererBlock;
import com.gregtechceu.gtceu.api.item.RendererBlockItem;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.client.renderer.block.TextureOverrideRenderer;
import com.jeefle.jeefworks.Jeefworks;
import com.jeefle.jeefworks.registry.JWCreativeTabs;
import com.lowdragmc.lowdraglib.Platform;
import com.lowdragmc.lowdraglib.client.renderer.IRenderer;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Map;
import java.util.function.BiFunction;

import static com.jeefle.jeefworks.Jeefworks.REGISTRATE;

public class JWCasingBlocks {

    static {
        REGISTRATE.creativeModeTab(() -> JWCreativeTabs.ITEM);
    }

    public static final BlockEntry<Block> BIOPLASTIC_CASING = createCasingBlock(
            "bioplastic_casing", "Biophylic Casing", RendererBlock::new,
            Jeefworks.id("block/casings/bioplastic_casing"), () -> Blocks.IRON_BLOCK);

    public static final BlockEntry<Block> VULCANIC_CASING = createCasingBlock(
            "vulcanic_casing", "Vulcanic Casing", RendererBlock::new,
            Jeefworks.id("block/casings/vulcanic_casing"), () -> Blocks.IRON_BLOCK);

    private static BlockEntry<Block> createCasingBlock(
            String name, String displayName,
            BiFunction<BlockBehaviour.Properties, IRenderer, ? extends RendererBlock> blockSupplier,
            ResourceLocation texture, NonNullSupplier<? extends Block> properties) {
        return REGISTRATE.block(name, p -> (Block) blockSupplier.apply(p,
                        Platform.isClient() ? new TextureOverrideRenderer(new ResourceLocation("block/cube_all"),
                                Map.of("all", texture)) : null))
                .initialProperties(properties)
                .lang(displayName)
                .blockstate(NonNullBiConsumer.noop())
                .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(RendererBlockItem::new)
                .model(NonNullBiConsumer.noop())
                .build()
                .register();
    }

    public static void init() {
    }
}
