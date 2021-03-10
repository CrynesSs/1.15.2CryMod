package com.CrynesSs.RedstoneEnhancement.Util.custom.Jigsaws;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CryJigsawPattern {
    public static final CryJigsawPattern EMPTY = new CryJigsawPattern(new ResourceLocation("empty"), new ResourceLocation("empty"), ImmutableList.of(), JigsawPattern.PlacementBehaviour.RIGID);
    public static final CryJigsawPattern INVALID = new CryJigsawPattern(new ResourceLocation("invalid"), new ResourceLocation("invalid"), ImmutableList.of(), JigsawPattern.PlacementBehaviour.RIGID);
    private final ResourceLocation name;
    private final ImmutableList<Pair<CrySingleJigsawPiece, Integer>> rawTemplates;


    private final List<CrySingleJigsawPiece> jigsawPieces;

    public List<CrySingleJigsawPiece> getSingleJigsawPieces() {
        return singleJigsawPieces;
    }

    private final List<CrySingleJigsawPiece> singleJigsawPieces = new ArrayList<>();
    private final ResourceLocation fallback;
    private final JigsawPattern.PlacementBehaviour placementBehaviour;
    private int maxSize = Integer.MIN_VALUE;

    public CryJigsawPattern(ResourceLocation nameIn, ResourceLocation fallbackIn, List<Pair<CrySingleJigsawPiece, Integer>> rawTemplatesIn, JigsawPattern.PlacementBehaviour placementBehaviourIn) {
        this.name = nameIn;
        this.rawTemplates = ImmutableList.copyOf(rawTemplatesIn);
        this.jigsawPieces = Lists.newArrayList();

        for (Pair<CrySingleJigsawPiece, Integer> pair : this.rawTemplates) {
            for (int integer = 0; integer < pair.getSecond(); integer = integer + 1) {
                this.jigsawPieces.add((CrySingleJigsawPiece) pair.getFirst().setPlacementBehaviour(placementBehaviourIn));
                if (integer == 0) {
                    this.singleJigsawPieces.add((CrySingleJigsawPiece) pair.getFirst().setPlacementBehaviour(placementBehaviourIn));
                }
            }
        }

        this.fallback = fallbackIn;
        this.placementBehaviour = placementBehaviourIn;
    }

    public int getMaxSize(TemplateManager templateManagerIn) {
        if (this.maxSize == Integer.MIN_VALUE) {
            this.maxSize = this.jigsawPieces.stream().mapToInt((p_214942_1_) -> p_214942_1_.getBoundingBox(templateManagerIn, BlockPos.ZERO, Rotation.NONE).getYSize()).max().orElse(0);
        }

        return this.maxSize;
    }

    public ResourceLocation getFallback() {
        return this.fallback;
    }

    public JigsawPiece getRandomPiece(Random rand) {
        return this.jigsawPieces.get(rand.nextInt(this.jigsawPieces.size()));
    }

    public List<JigsawPiece> getShuffledPieces(Random rand) {
        return ImmutableList.copyOf(ObjectArrays.shuffle(this.jigsawPieces.toArray(new JigsawPiece[0]), rand));
    }

    public List<CrySingleJigsawPiece> getJigsawPieces() {
        return jigsawPieces;
    }

    public ResourceLocation getName() {
        return this.name;
    }

    public int getNumberOfPieces() {
        return this.jigsawPieces.size();
    }
}
