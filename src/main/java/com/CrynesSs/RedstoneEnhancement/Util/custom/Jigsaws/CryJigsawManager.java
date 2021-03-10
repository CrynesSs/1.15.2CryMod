package com.CrynesSs.RedstoneEnhancement.Util.custom.Jigsaws;

import com.CrynesSs.RedstoneEnhancement.RedstoneEnhancement;
import com.CrynesSs.RedstoneEnhancement.structures.City.CityStructure;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import net.minecraft.block.JigsawBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.jigsaw.EmptyJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.JigsawJunction;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.Structures;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class CryJigsawManager {
    public static final CryJigsawPatternRegistry REGISTRY = new CryJigsawPatternRegistry();


    public static void addPieces(ResourceLocation structureLocation, int maxDepth, CryJigsawManager.IPieceFactory pieceFactory, ChunkGenerator<?> chunkGeneratorIn, TemplateManager templateManagerIn, BlockPos posIn, List<StructurePiece> structurePieces, Random rand) {
        Structures.init();
        new CryJigsawManager.Assembler(structureLocation, maxDepth, pieceFactory, chunkGeneratorIn, templateManagerIn, posIn, structurePieces, rand);
    }

    static {
        REGISTRY.register(CryJigsawPattern.EMPTY);
    }

    public static BlockPos offsetBasedOnRot(Rotation rotation, AbstractVillagePiece piece) {
        System.out.println((piece.getBoundingBox().maxX - piece.getBoundingBox().minX) + "This is Verschiebung");
        switch (rotation) {
            case NONE:
                return new BlockPos(0, 0, 0);
            case CLOCKWISE_90:
                return new BlockPos(piece.getBoundingBox().maxX - piece.getBoundingBox().minX, 0, 0);
            case COUNTERCLOCKWISE_90:
                return new BlockPos(0, 0, piece.getBoundingBox().maxZ - piece.getBoundingBox().minZ);
            case CLOCKWISE_180:
                return new BlockPos(piece.getBoundingBox().maxX - piece.getBoundingBox().minX, 0, piece.getBoundingBox().maxZ - piece.getBoundingBox().minZ);
        }
        return new BlockPos(0, 0, 0);
    }

    public static BlockPos offsetBasedOnDirection(Direction d, boolean polarity) {
        BlockPos offset1 = new BlockPos(1, 0, 0);
        BlockPos offset2 = new BlockPos(-1, 0, 0);
        BlockPos offset3 = new BlockPos(0, 0, 1);
        BlockPos offset4 = new BlockPos(0, 0, -1);
        if (d == Direction.NORTH) {
            if (polarity) {
                return offset2;
            } else {
                return offset1;
            }
        } else if (d == Direction.SOUTH) {
            if (polarity) {
                return offset1;
            } else {
                return offset2;
            }
        } else if (d == Direction.EAST) {
            if (polarity) {
                return offset4;
            } else {
                return offset3;
            }
        } else if (d == Direction.WEST) {
            if (polarity) {
                return offset3;
            } else {
                return offset4;
            }

        }
        return new BlockPos(0, 0, 0);
    }


    static final class Assembler {
        private final int maxDepth;

        public IPieceFactory getPieceFactory() {
            return pieceFactory;
        }

        private final CryJigsawManager.IPieceFactory pieceFactory;
        private final ChunkGenerator<?> chunkGenerator;

        public TemplateManager getTemplateManager() {
            return templateManager;
        }

        private final TemplateManager templateManager;

        public List<StructurePiece> getStructurePieces() {
            return structurePieces;
        }

        private final List<StructurePiece> structurePieces;
        private final Random rand;

        public Deque<Entry> getAvailablePieces() {
            return availablePieces;
        }

        private final Deque<CryJigsawManager.Entry> availablePieces = Queues.newArrayDeque();
        private RoadConstructor constructor;
        private ChunkPos fromStructure;

        public ChunkGenerator<?> getChunkGenerator() {
            return chunkGenerator;
        }

        //TODO OFFSET BECAUSE OF + - ORIENTATION (DONE)
        //TODO Algorithm to get from point A->B->C->A'
        //TODO A* Algorithm with 3 Random Points in Radius connecting the Structures itself
        public Assembler(ResourceLocation resourceLocationIn, int maxDepth, CryJigsawManager.IPieceFactory factory, ChunkGenerator<?> generatorIn, TemplateManager templateManagerIn, BlockPos posIn, List<StructurePiece> p_i50691_7_, Random rand) {
            this.maxDepth = maxDepth;
            this.pieceFactory = factory;
            this.chunkGenerator = generatorIn;
            this.templateManager = templateManagerIn;
            this.structurePieces = p_i50691_7_;
            this.rand = rand;
            ChunkPos inChunk = new ChunkPos(posIn.getX() / 16, posIn.getZ() / 16);
            CityStructure.CHUNKLIST.forEach((k, v) -> {
                if (v.stream().anyMatch(chunkPos -> chunkPos.x == inChunk.x && chunkPos.z == inChunk.z)) {
                    this.constructor = CityStructure.ROADMAP.get(k);
                    this.fromStructure = k;
                }
            });
            if (constructor != null) {
                System.out.println("Found a valid Constructor for this City");
                System.out.println("with " + constructor.getFINALNODES().size() + "total Nodes");
            } else {
                return;
            }
            //*Sets a Random Rotation for the Base Plate (This is the Start of the Structure from where all other Pieces are attached to
            Rotation rotation = Rotation.randomRotation(rand);
            //*Finds the JigsawPattern of the Base Plate
            CryJigsawPattern jigsawpattern = CryJigsawManager.REGISTRY.get(resourceLocationIn);
            //*Finds a Random Piece in that Target Pool of Base Plates
            JigsawPiece jigsawpiece = jigsawpattern.getRandomPiece(rand);
            //*Creates the Piece with the Specified Rotation
            AbstractVillagePiece abstractvillagepiece = factory.create(templateManagerIn, jigsawpiece, posIn, jigsawpiece.getGroundLevelDelta(), rotation, jigsawpiece.getBoundingBox(templateManagerIn, posIn, rotation));
            //*Offset the Base Plate based on its rotation, so it is always in the Corner of the Chunk
            BlockPos offsetOnRotation = offsetBasedOnRot(rotation, abstractvillagepiece);
            //*Gets the Bounding Box (Size) of the Piece
            MutableBoundingBox mutableboundingbox = abstractvillagepiece.getBoundingBox();
            //*Finds the Midpoint of the X
            int i = (mutableboundingbox.maxX + mutableboundingbox.minX) / 2;
            //System.out.println(i + " This is I");
            //*Finds the Midpoint of the Z
            int j = (mutableboundingbox.maxZ + mutableboundingbox.minZ) / 2;
            System.out.println(j + " This is j");
            //*Gets the Height of the Map in the MIDDLE of the Jigsawpiece
            int k = generatorIn.getNoiseHeight(i, j, Heightmap.Type.WORLD_SURFACE_WG);
            //*Offsets the Piece, so it is above the ground
            abstractvillagepiece.offset(0, k - (mutableboundingbox.minY + abstractvillagepiece.getGroundLevelDelta()), 0);
            //*Offsets the Piece based on the Rotation(Not necessary but useful)
            abstractvillagepiece.offset(offsetOnRotation.getX(), 0, offsetOnRotation.getZ());
            //p_i50691_7_.add(abstractvillagepiece);
            RoadConstructor.placeParts(inChunk, this.fromStructure, constructor, this);
            if (maxDepth > 0) {
                //*while loop placing all the Pieces
                while (!this.availablePieces.isEmpty()) {
                    CryJigsawManager.Entry jigsawmanager$entry = this.availablePieces.removeFirst();
                    this.tryPlacingChildren(jigsawmanager$entry.villagePiece, jigsawmanager$entry.free, jigsawmanager$entry.boundsTop, jigsawmanager$entry.depth);
                }
                System.out.println(this.structurePieces.size() + " This is how many structurepieces there are");
            }
        }

        //TODO Street Manager to place Streets because this Algorithm is shit for that
        //TODO Canal Manager to place canals because this Algorithm is shit for that
        private void tryPlacingChildren(AbstractVillagePiece villagePieceIn, AtomicReference<VoxelShape> atomicVoxelShape, int boundsTop, int maxDepth) {
            JigsawPiece jigsawpiece = villagePieceIn.getJigsawPiece();
            //*Position of the First Placed Piece
            BlockPos blockpos = villagePieceIn.getPos();
            //*Rotation of the first placed Village Piece
            Rotation rotation = villagePieceIn.getRotation();
            //*PlacementBehaviour of the first VillagePiece
            JigsawPattern.PlacementBehaviour jigsawpattern$placementbehaviour = jigsawpiece.getPlacementBehaviour();
            //*Boolean flag if the PlacementBehaviour is Rigid
            boolean flag = jigsawpattern$placementbehaviour == JigsawPattern.PlacementBehaviour.RIGID;
            AtomicReference<VoxelShape> atomicreference = new AtomicReference<>();
            MutableBoundingBox mutableboundingbox = villagePieceIn.getBoundingBox();
            int i = mutableboundingbox.minY;

            label123:
            for (Template.BlockInfo template$blockinfo : jigsawpiece.getJigsawBlocks(this.templateManager, blockpos, rotation, this.rand)) {
                Direction direction = template$blockinfo.state.get(JigsawBlock.FACING);
                BlockPos blockpos1 = template$blockinfo.pos;
                BlockPos blockpos2 = blockpos1.offset(direction);
                int j = blockpos1.getY() - i;
                int k = -1;
                CryJigsawPattern jigsawpattern = CryJigsawManager.REGISTRY.get(new ResourceLocation(template$blockinfo.nbt.getString("target_pool")));
                CryJigsawPattern jigsawpattern1 = CryJigsawManager.REGISTRY.get(jigsawpattern.getFallback());
                if (jigsawpattern != CryJigsawPattern.INVALID && (jigsawpattern.getNumberOfPieces() != 0 || jigsawpattern == CryJigsawPattern.EMPTY)) {
                    boolean flag1 = mutableboundingbox.isVecInside(blockpos2);
                    AtomicReference<VoxelShape> atomicreference1;
                    int l;
                    if (flag1) {
                        atomicreference1 = atomicreference;
                        l = i;
                        if (atomicreference.get() == null) {
                            atomicreference.set(VoxelShapes.create(AxisAlignedBB.toImmutable(mutableboundingbox)));
                        }
                    } else {
                        atomicreference1 = atomicVoxelShape;
                        l = boundsTop;
                    }

                    List<JigsawPiece> list = Lists.newArrayList();
                    //*If the MaxDepth is not yet reached, it adds all the Pieces of the Target Pool
                    if (maxDepth != this.maxDepth) {
                        list.addAll(jigsawpattern.getShuffledPieces(this.rand));
                    }
                    //*Adds the Fallback Pieces
                    list.addAll(jigsawpattern1.getShuffledPieces(this.rand));

                    for (JigsawPiece jigsawpiece1 : list) {
                        if (jigsawpiece1 == EmptyJigsawPiece.INSTANCE) {
                            break;
                        }
                        boolean flag3 = false;
                        boolean flag4 = false;
                        if (jigsawpiece1 instanceof CrySingleJigsawPiece && villagePieceIn.getJigsawPiece() instanceof CrySingleJigsawPiece) {
                            //System.out.println(((CrySingleJigsawPiece) jigsawpiece1).polarity + " Polarity of JigsawPiece1");
                            //System.out.println(villagePieceIn.getPos() + "Position of JigsawPieceIn");
                            //System.out.println(((CrySingleJigsawPiece) jigsawpiece1).polarity + " Polarity of JigsawPieceIn");
                            //*Flag that controls the Polarity
                            flag3 = ((CrySingleJigsawPiece) jigsawpiece1).polarity == ((CrySingleJigsawPiece) villagePieceIn.getJigsawPiece()).polarity;
                            flag4 = ((CrySingleJigsawPiece) jigsawpiece1).polarity;

                        }
                        //*Iterates over all possible Rotations
                        for (Rotation rotation1 : Rotation.shuffledRotations(this.rand)) {
                            //*Gets the JigsawBlocks inside the Jigsawpiece
                            List<Template.BlockInfo> list1 = jigsawpiece1.getJigsawBlocks(this.templateManager, BlockPos.ZERO, rotation1, this.rand);
                            //*Creates a new Bounding Box depending on the Rotation of the JigsawPiece selected
                            MutableBoundingBox mutableboundingbox1 = jigsawpiece1.getBoundingBox(this.templateManager, BlockPos.ZERO, rotation1);
                            int i1;
                            if (mutableboundingbox1.getYSize() > 16) {
                                i1 = 0;
                            } else {
                                i1 = list1.stream().mapToInt((p_214880_2_) -> {
                                    if (!mutableboundingbox1.isVecInside(p_214880_2_.pos.offset(p_214880_2_.state.get(JigsawBlock.FACING)))) {
                                        return 0;
                                    } else {
                                        ResourceLocation resourcelocation = new ResourceLocation(p_214880_2_.nbt.getString("target_pool"));
                                        CryJigsawPattern jigsawpattern2 = CryJigsawManager.REGISTRY.get(resourcelocation);
                                        CryJigsawPattern jigsawpattern3 = CryJigsawManager.REGISTRY.get(jigsawpattern2.getFallback());
                                        return Math.max(jigsawpattern2.getMaxSize(this.templateManager), jigsawpattern3.getMaxSize(this.templateManager));
                                    }
                                }).max().orElse(0);
                            }
                            //*Iterates over the JigsawBlocks inside the JigsawPiece
                            for (Template.BlockInfo template$blockinfo1 : list1) {
                                //*Checks if the two JigsawBlocks have the same AttachmentType
                                if (JigsawBlock.func_220171_a(template$blockinfo, template$blockinfo1)) {
                                    BlockPos blockpos3 = template$blockinfo1.pos;
                                    BlockPos blockpos4 = new BlockPos(blockpos2.getX() - blockpos3.getX(), blockpos2.getY() - blockpos3.getY(), blockpos2.getZ() - blockpos3.getZ());
                                    //*Creates a new Bounding Box depending on the Rotation of the Piece
                                    MutableBoundingBox mutableboundingbox2 = jigsawpiece1.getBoundingBox(this.templateManager, blockpos4, rotation1);
                                    int j1 = mutableboundingbox2.minY;
                                    JigsawPattern.PlacementBehaviour jigsawpattern$placementbehaviour1 = jigsawpiece1.getPlacementBehaviour();
                                    //*Flag 2 = JigsawPatter.PlacementBehaviour.Rigid from the JigsawPiece selected
                                    boolean flag2 = jigsawpattern$placementbehaviour1 == JigsawPattern.PlacementBehaviour.RIGID;
                                    int k1 = blockpos3.getY();
                                    int l1 = j - k1 + template$blockinfo.state.get(JigsawBlock.FACING).getYOffset();
                                    int i2;
                                    if (flag && flag2) {
                                        i2 = i + l1;
                                    } else {
                                        if (k == -1) {
                                            k = this.chunkGenerator.getNoiseHeight(blockpos1.getX(), blockpos1.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
                                        }

                                        i2 = k - k1;
                                    }

                                    int j2 = i2 - j1;
                                    //*func_215127_b offests the Bounding Box in x,y,z Direction.
                                    //*Offests the Bounding Box in y-Direction
                                    MutableBoundingBox mutableboundingbox3 = mutableboundingbox2.func_215127_b(0, j2, 0);
                                    //*Offset BlockPos4 in y-Direction same amount as the BoundingBox
                                    BlockPos blockpos5 = blockpos4.add(0, j2, 0);

                                    if (i1 > 0) {
                                        int k2 = Math.max(i1 + 1, mutableboundingbox3.maxY - mutableboundingbox3.minY);
                                        mutableboundingbox3.maxY = mutableboundingbox3.minY + k2;
                                    }

                                    if (!VoxelShapes.compare(atomicreference1.get(), VoxelShapes.create(AxisAlignedBB.toImmutable(mutableboundingbox3).shrink(0.25D)), IBooleanFunction.ONLY_SECOND)) {
                                        atomicreference1.set(VoxelShapes.combine(atomicreference1.get(), VoxelShapes.create(AxisAlignedBB.toImmutable(mutableboundingbox3)), IBooleanFunction.ONLY_FIRST));
                                        int j3 = villagePieceIn.getGroundLevelDelta();
                                        int l2;
                                        if (flag2) {
                                            l2 = j3 - l1;
                                        } else {
                                            l2 = jigsawpiece1.getGroundLevelDelta();
                                        }
                                        //*Creates a new AbstractVillagePiece
                                        AbstractVillagePiece abstractvillagepiece = this.pieceFactory.create(this.templateManager, jigsawpiece1, blockpos5, l2, rotation1, mutableboundingbox3);
                                        int i3;
                                        if (flag) {
                                            i3 = i + j;
                                        } else if (flag2) {
                                            i3 = i2 + k1;
                                        } else {
                                            if (k == -1) {
                                                k = this.chunkGenerator.getNoiseHeight(blockpos1.getX(), blockpos1.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
                                            }
                                            i3 = k + l1 / 2;
                                        }
                                        if (flag3) {
                                            BlockPos offset = offsetBasedOnDirection(direction, flag4);
                                            abstractvillagepiece.offset(offset.getX(), offset.getY(), offset.getZ());
                                        }
                                        villagePieceIn.addJunction(new JigsawJunction(blockpos2.getX(), i3 - j + j3, blockpos2.getZ(), l1, jigsawpattern$placementbehaviour1));
                                        abstractvillagepiece.addJunction(new JigsawJunction(blockpos1.getX(), i3 - k1 + l2, blockpos1.getZ(), -l1, jigsawpattern$placementbehaviour));
                                        //System.out.println("VillagePieceInPos " + villagePieceIn.getPos() + "VillagePieceCreated " +abstractvillagepiece.getPos());
                                        //*Structurepieces inside the Structure(Those are the final ones)
                                        this.structurePieces.add(abstractvillagepiece);
                                        if (maxDepth + 1 <= this.maxDepth) {
                                            //*Adds the newly created piece so their Children can be placed
                                            this.availablePieces.addLast(new CryJigsawManager.Entry(abstractvillagepiece, atomicreference1, l, maxDepth + 1));
                                        }
                                        continue label123;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    RedstoneEnhancement.LOGGER.warn("Empty or none existent pool: {}", template$blockinfo.nbt.getString("target_pool"));
                }
            }

        }
    }

    static final class Entry {
        private final AbstractVillagePiece villagePiece;
        private final AtomicReference<VoxelShape> free;
        private final int boundsTop;
        private final int depth;

        Entry(AbstractVillagePiece villagePieceIn, AtomicReference<VoxelShape> free, int boundsTop, int depth) {
            this.villagePiece = villagePieceIn;
            this.free = free;
            this.boundsTop = boundsTop;
            this.depth = depth;
        }
    }

    public interface IPieceFactory {
        AbstractVillagePiece create(TemplateManager p_create_1_, JigsawPiece p_create_2_, BlockPos p_create_3_, int p_create_4_, Rotation p_create_5_, MutableBoundingBox p_create_6_);
    }
}

