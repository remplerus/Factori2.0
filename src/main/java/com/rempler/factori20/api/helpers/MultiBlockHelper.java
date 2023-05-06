package com.rempler.factori20.api.helpers;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.level.BlockEvent.EntityMultiPlaceEvent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MultiBlockHelper {
    public static final List<Pair<BlockPos, BlockState>> NO_SNAPSHOTS = ImmutableList.of();

    public static List<Pair<BlockPos, BlockState>> getMultiBlockStates(ResourceKey<Level> key, LevelAccessor world, BlockPos placePos, BlockState againstState, Entity entity, TagKey<Block> blockTag, int cubeXZ, int cubeY, List<Pair<BlockPos, BlockState>> pairs) {
        return getCubeAround(placePos, cubeXZ, cubeY)
                .filter(pos -> canMultiBlockFormAt(world, pos, placePos, blockTag, cubeXZ, cubeY))
                .map(pos -> getStatesForPlacementIfPermitted(key, world, againstState, entity, pairs))
                .filter(list -> !list.isEmpty())
                .findFirst()
                .orElse(ImmutableList.of());
    }

    public static boolean canMultiBlockFormAt(LevelAccessor world, BlockPos corePos, BlockPos placePos, TagKey<Block> blockTag, int cubeXZ, int cubeY) {
        return getCubeAround(corePos, cubeXZ, cubeY).allMatch(pos -> pos.equals(placePos) || world.getBlockState(pos).is(blockTag));
    }

    public static boolean canMultiBlockPlaceAt(LevelAccessor world, BlockPos corePos, BlockPlaceContext useContext, int cubeXZ, int cubeY) {
        // the two-blockpos constructor for AABB is [inclusive, exclusive)
        // so we have to add 2 to the second arg
        boolean noEntitiesInArea = world.getEntitiesOfClass(LivingEntity.class, new AABB(corePos.offset(-1,-1,-1), corePos.offset(2,2,2))).isEmpty();
        return noEntitiesInArea && getCubeAround(corePos, cubeXZ, cubeY)
                .allMatch(pos -> world.getBlockState(pos).canBeReplaced(useContext));
    }

    public static Stream<BlockPos> getCubeAround(BlockPos pos, int cubeXZ, int cubeY) {
        int x = cubeXZ % 2 != 0 ? (int) (cubeXZ/2 - 0.5) : cubeXZ/2;
        int y = cubeY % 2 != 0 ? (int) (cubeY/2 - 0.5) : cubeY/2;
        return BlockPos.betweenClosedStream(pos.offset(-x, -y, -x), pos.offset(x,y,x));
    }

    public static List<Pair<BlockPos, BlockState>> getStatesForPlacementIfPermitted(ResourceKey<Level> key, LevelAccessor world, BlockState againstState, Entity placer, List<Pair<BlockPos, BlockState>> pairs) {
        return doesPlayerHavePermissionToMakeMultiBlock(key, world, pairs, againstState, placer)
                ? pairs
                : NO_SNAPSHOTS;
    }

    public static boolean doesPlayerHavePermissionToMakeMultiBlock(ResourceKey<Level> key, LevelAccessor world, List<Pair<BlockPos, BlockState>> pairs, BlockState placedAgainst, Entity entity) {
        List<BlockSnapshot> snapshots = pairs.stream()
                .map(pair -> BlockSnapshot.create(key, world, pair.getFirst()))
                .collect(Collectors.toList());
        EntityMultiPlaceEvent event = new EntityMultiPlaceEvent(snapshots, placedAgainst, entity);
        MinecraftForge.EVENT_BUS.post(event);
        return !event.isCanceled();
    }
}