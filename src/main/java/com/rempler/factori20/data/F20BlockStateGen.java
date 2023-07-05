package com.rempler.factori20.data;

import com.rempler.factori20.common.init.F20Blocks;
import com.rempler.factori20.utils.F20Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class F20BlockStateGen extends BlockStateProvider {
    public F20BlockStateGen(PackOutput gen, ExistingFileHelper exFileHelper) {
        super(gen, F20Constants.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleItemBlock(F20Blocks.ELECTRIC_DRILL_BLOCK.get(), models().cubeAll("electric_drill_block", new ResourceLocation("factori20", "block/drill_component")));
        simpleItemBlock(F20Blocks.BURNER_DRILL_BLOCK.get(), models().cubeAll("burner_drill_block", new ResourceLocation("factori20", "block/burner_drill_component")));
        simpleItemBlock(F20Blocks.ELECTRIC_RESEARCH_BLOCK.get(), models().cubeAll("electric_research_block", new ResourceLocation("factori20", "block/drill_component")));
        simpleItemBlock(F20Blocks.BURNER_RESEARCH_BLOCK.get(), models().cubeAll("burner_research_block", new ResourceLocation("factori20", "block/burner_drill_component")));
    }

    private void simpleItemBlock(Block block, ModelFile modelFile) {
        simpleBlock(block, modelFile);
        simpleBlockItem(block, modelFile);
    }
}
