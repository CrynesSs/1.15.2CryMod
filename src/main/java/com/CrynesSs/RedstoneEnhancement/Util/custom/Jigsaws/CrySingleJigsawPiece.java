package com.CrynesSs.RedstoneEnhancement.Util.custom.Jigsaws;

import com.google.common.collect.ImmutableList;
import com.sun.javafx.geom.Vec2d;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class CrySingleJigsawPiece extends SingleJigsawPiece {
    public static final ImmutableList<Direction> horizontalDirections = ImmutableList.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
    protected final ResourceLocation location;
    protected final ImmutableList<StructureProcessor> processors;
    protected final boolean polarity;
    protected final List<Direction> connections;
    protected final List<Vec2d> connectionVec;

    public CrySingleJigsawPiece(String location, List<StructureProcessor> processors, JigsawPattern.PlacementBehaviour placementBehaviour, boolean polarity, List<Direction> connections) {
        super(location, processors, placementBehaviour);
        this.location = new ResourceLocation(location);
        this.processors = ImmutableList.copyOf(processors);
        this.polarity = polarity;
        this.connections = connections;
        this.connectionVec = connections.parallelStream().map((d) -> new Vec2d(d.getDirectionVec().getX(), d.getDirectionVec().getZ())).collect(Collectors.toList());
    }

    @Override
    public MutableBoundingBox getBoundingBox(@Nonnull TemplateManager templateManagerIn, @Nonnull BlockPos pos, @Nonnull Rotation rotationIn) {
        return super.getBoundingBox(templateManagerIn, pos, rotationIn);
    }
}
