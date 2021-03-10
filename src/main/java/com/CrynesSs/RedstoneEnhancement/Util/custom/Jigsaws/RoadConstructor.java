package com.CrynesSs.RedstoneEnhancement.Util.custom.Jigsaws;

import com.CrynesSs.RedstoneEnhancement.Util.Pathfinding.Node;
import com.CrynesSs.RedstoneEnhancement.Util.Pathfinding.StreetNode;
import com.CrynesSs.RedstoneEnhancement.structures.City.CityPieces;
import com.google.common.collect.ImmutableList;
import com.sun.javafx.geom.Vec2d;
import javafx.util.Pair;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class RoadConstructor {
    private final int x;
    private final int y;
    private final List<Node> included = new ArrayList<>();
    private final List<ChunkPos> satelliteChunks = new ArrayList<>();
    private final ChunkPos seedChunk;
    private final HashMap<streetType, List<CrySingleJigsawPiece>> pieces;

    public List<StreetNode> getFINALNODES() {
        return FINALNODES;
    }

    private List<StreetNode> FINALNODES = new ArrayList<>();

    public RoadConstructor(int x, int y, ChunkPos seedChunk, ResourceLocation streets) {
        this.x = x;
        this.y = y;
        this.seedChunk = seedChunk;
        HashMap<streetType, List<CrySingleJigsawPiece>> pieceHashMap = new HashMap<>();
        CityPieces.init();
        //System.out.println(CryJigsawManager.REGISTRY.getRegistry().toString());
        //System.out.println(CryJigsawManager.REGISTRY.get(streets).getSingleJigsawPieces().size() + "Amount of Pieces");
        CryJigsawManager.REGISTRY.get(streets).getSingleJigsawPieces()
                .forEach(k -> {
                    if (!pieceHashMap.containsKey(fromConnections(k.connections))) {
                        System.out.println("Putting Piece in");
                        pieceHashMap.put(fromConnections(k.connections), new ArrayList<>());
                    }
                    pieceHashMap.get(fromConnections(k.connections)).add(k);
                });
        pieces = pieceHashMap;
        this.calculatePath();

    }

    private streetType fromConnections(List<Direction> directions) {
        AtomicBoolean containsOpposites = new AtomicBoolean(false);
        directions.forEach(k -> {
            if (directions.contains(k.getOpposite())) {
                containsOpposites.set(true);
            }
        });
        if (containsOpposites.get()) {
            if (directions.size() == 3) {
                return streetType.T;
            } else if (directions.size() == 4) {
                return streetType.CROSS;
            } else {
                return streetType.STRAIGHT;
            }
        } else {
            return streetType.CORNER;
        }
    }

    public enum streetType {
        STRAIGHT,
        CORNER,
        T,
        CROSS
    }

    public void calculatePath() {
        //*Generate the Chunks for the Satellite Structures that are also City
        for (int i = 0; i < this.x; i++) {
            for (int k = 0; k < this.y; k++) {
                final int i1 = seedChunk.z - (this.x - 1) / 2 * 17 + k * 17;
                final int i2 = seedChunk.x - (this.x - 1) / 2 * 17 + i * 17;
                if (!(i2 == seedChunk.x) || !(i1 == seedChunk.z)) {
                    satelliteChunks.add(new ChunkPos(i2, i1));
                }
            }
        }
        Vec2d coords0 = new Vec2d(seedChunk.x - (((this.x - 1) / 2d) * 17) - 8, seedChunk.z - (((this.y - 1) / 2d) * 17) - 8);
        //System.out.println("coords0 " + coords0.toString());
        //System.out.println("Satellite Chunks");
        //satelliteChunks.forEach(System.out::println);
        included.add(new Node(seedChunk.x - (int) coords0.x, seedChunk.z - (int) coords0.y));
        satelliteChunks.forEach(k -> included.add(new Node(k.x - (int) coords0.x, k.z - (int) coords0.y)));
        //*Generate the Random Points inside the Sections of the Structure
        HashMap<Node, List<Node>> generated = new HashMap<>();
        included.forEach(k -> {
            List<Node> templist = new ArrayList<>();
            for (int i = 0, jx = 1, jz = 1; i < 4; i++) {
                templist.add(new Node(k.getX() + getValueBetween(2, 6) * jx, k.getY() + getValueBetween(2, 6) * jz));
                jz *= -1 * jx;
                jx *= -1;
            }
            generated.put(k, templist);
        });
        //*Generate the Paths from each CenterNode to its randomly Generated Nodes
        HashMap<Node, List<Vec2d>> pathMap = new HashMap<>();
        //*Generate the Paths from the randomly generated Nodes to EdgeMiddles
        HashMap<Node, List<Vec2d>> pathMap2 = new HashMap<>();
        //*Generate the Paths from the generated Nodes in one Section to the Next Section
        HashMap<Node, List<Vec2d>> pathMap3 = new HashMap<>();
        //*Magic
        generated.forEach(((node, nodeList) -> pathMap.put(node, nodeList.parallelStream().map((n) -> new Vec2d(node.getX() - n.getX(), node.getY() - n.getY())).collect(Collectors.toList()))));
        //*Magic2
        //*Gotcha. This calculates the Path from Generated Node to EdgeMiddle
        generated.forEach(((node, nodeList) -> {
            int jx = 1, jz = 1;
            for (Node n : nodeList) {
                Vec2d vec1 = new Vec2d(node.getX() + jx * 8 - n.getX(), node.getY() - n.getY());
                Vec2d vec2 = new Vec2d(node.getX() - n.getX(), node.getY() + jz * 8 - n.getY());
                pathMap2.put(n, ImmutableList.of(vec1, vec2));
                jz *= -1 * jx;
                jx *= -1;
            }
        }));
        List<Pair<Node, Node>> adjacentStructures = new ArrayList<>();
        //*Magic3
        //*jk Matching the Structures to each Neighbour. 12 Connections in total with no Dublication in a 3x3 Grid
        included.forEach(k -> included.forEach(j -> {
            if (k.getX() == j.getX() && (k.getY() - j.getY()) == 17) {
                adjacentStructures.add(new Pair<>(k, j));
            }
            if (k.getY() == j.getY() && (k.getX() - j.getX()) == 17) {
                adjacentStructures.add(new Pair<>(k, j));
            }
        }));
        //*BlackMagic 1
        //*Gotcha again. This creates the Paths from a Structure to another one
        adjacentStructures.forEach(p -> {
            if (p.getKey().getX() == p.getValue().getX()) {
                //*First is the above one
                Node first1 = generated.get(p.getKey()).get(0);
                Node first4 = generated.get(p.getKey()).get(3);
                //*Second is the below one
                Node second1 = generated.get(p.getValue()).get(0);
                Node second4 = generated.get(p.getValue()).get(3);
                Vec2d path1 = first1.makePath(second4);
                Vec2d path2 = first4.makePath(second1);
                if (!pathMap3.containsKey(first1)) {
                    ArrayList<Vec2d> templist1 = new ArrayList<>();
                    templist1.add(path1);
                    pathMap3.put(first1, templist1);
                }
                if (!pathMap3.containsKey(first4)) {
                    ArrayList<Vec2d> templist2 = new ArrayList<>();
                    templist2.add(path2);
                    pathMap3.put(first4, templist2);
                }
            } else if (p.getKey().getY() == p.getValue().getY()) {
                //*First is the right one
                Node first2 = generated.get(p.getKey()).get(1);
                Node first4 = generated.get(p.getKey()).get(3);
                //*Second is the left one
                Node second1 = generated.get(p.getValue()).get(0);
                Node second3 = generated.get(p.getValue()).get(2);
                Vec2d path1 = first2.makePath(second3);
                Vec2d path2 = first4.makePath(second1);
                if (!pathMap3.containsKey(first2)) {
                    ArrayList<Vec2d> templist1 = new ArrayList<>();
                    templist1.add(path1);
                    pathMap3.put(first2, templist1);
                }
                if (!pathMap3.containsKey(first4)) {
                    ArrayList<Vec2d> templist2 = new ArrayList<>();
                    templist2.add(path2);
                    pathMap3.put(first4, templist2);
                }
            }
        });

        pathMap2.forEach((k, v) -> {
            if (pathMap.containsKey(k)) {
                List<Vec2d> templist = new ArrayList<>();
                templist.addAll(pathMap.get(k));
                templist.addAll(v);
                pathMap.put(k, templist);
            } else {
                pathMap.put(k, v);
            }
        });
        pathMap3.forEach((k, v) -> {
            if (pathMap.containsKey(k)) {
                List<Vec2d> templist = new ArrayList<>();
                templist.addAll(pathMap.get(k));
                templist.addAll(v);
                pathMap.put(k, templist);
            } else {
                pathMap.put(k, v);
            }
        });
        evaluatePaths(pathMap);
    }

    //1-3
    private int getValueBetween(int lowerBound, int upperBound) {
        return (int) Math.floor(Math.random() * (upperBound - lowerBound + 1) + lowerBound);
    }

    //*REMOVES BOTH NODES
    private List<Node> removeDublicates(List<Node> nodeList) {
        return nodeList.stream().distinct().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private HashMap<Node, List<Node>> findAdjacentNodes(List<Node> nodeList) {
        HashMap<Node, List<Node>> adjacentTiles = new HashMap<>();
        nodeList.parallelStream().forEach(node -> adjacentTiles.put(node, nodeList.stream().filter(node1 -> Node.distanceBetweenNodes(node, node1) == 1).collect(Collectors.toList())));
        return adjacentTiles;
    }

    private List<StreetNode> convertIntoStreetNodes(HashMap<Node, List<Node>> adjacentList) {
        ArrayList<StreetNode> nodes = new ArrayList<>();
        adjacentList.forEach((k, v) -> {
            Vec2d vec2d = new Vec2d(0, 0);
            v.forEach(node -> vec2d.set(Math.abs(node.getX() - k.getX()) + vec2d.x, Math.abs(node.getY() - k.getY()) + vec2d.y));
            switch (v.size()) {
                case 1:
                    nodes.add(new StreetNode(k.getX(), k.getY(), streetType.STRAIGHT, vec2d.x > 0 ? Rotation.CLOCKWISE_90 : Rotation.NONE));
                    break;
                case 3:
                    nodes.add(new StreetNode(k.getX(), k.getY(), streetType.T, vec2d.x == 1 ? v.stream().filter(node -> node.getX() != k.getX()).mapToInt(node -> node.getX() - k.getX()).findFirst().getAsInt() > 0 ? Rotation.COUNTERCLOCKWISE_90 : Rotation.CLOCKWISE_90 : v.stream().filter(node -> node.getY() != k.getY()).mapToInt(node -> node.getY() - k.getY()).findFirst().getAsInt() > 0 ? Rotation.NONE : Rotation.CLOCKWISE_180));
                    break;
                case 4:
                    nodes.add(new StreetNode(k.getX(), k.getY(), streetType.CROSS, Rotation.NONE));
                    break;
                case 2: {
                    if (vec2d.x != vec2d.y) {
                        nodes.add(new StreetNode(k.getX(), k.getY(), streetType.STRAIGHT, vec2d.x > 0 ? Rotation.CLOCKWISE_90 : Rotation.NONE));
                    } else {
                        Vec2d vec2d1 = v.stream().map((node) -> new Vec2d(node.getX() - k.getX(), node.getY() - k.getY())).reduce((l, m) -> new Vec2d(l.x + m.x, l.y + m.y)).get();
                        nodes.add(vec2d1.x == vec2d1.y ? new StreetNode(k.getX(), k.getY(), streetType.CORNER, vec2d1.x > 0 ? Rotation.NONE : Rotation.CLOCKWISE_180) : new StreetNode(k.getX(), k.getY(), streetType.CORNER, vec2d1.x > 0 && vec2d1.y < 0 ? Rotation.COUNTERCLOCKWISE_90 : Rotation.CLOCKWISE_90));
                    }
                    break;
                }
            }
        });
        return nodes;
    }

    private void evaluatePaths(HashMap<Node, List<Vec2d>> pathMap) {
        List<Node> completeList = new ArrayList<>();
        pathMap.entrySet().parallelStream().forEach(nodeListEntry -> {
            completeList.add(nodeListEntry.getKey());
            nodeListEntry.getValue().forEach(v -> {
                int curX = nodeListEntry.getKey().getX(), curY = nodeListEntry.getKey().getY();
                for (int ix = (int) v.x, iy = (int) v.y; (ix != 0 || iy != 0); ) {
                    if (ix == 0) {
                        int normY = iy / Math.abs(iy);
                        iy -= normY;
                        curY += normY;
                        completeList.add(new Node(curX, curY));
                        continue;
                    }
                    if (iy == 0) {
                        int normX = ix / Math.abs(ix);
                        ix -= normX;
                        curX += normX;
                        completeList.add(new Node(curX, curY));
                        continue;
                    }
                    if (Math.random() < 0.5f) {
                        int normY = iy / Math.abs(iy);
                        iy -= normY;
                        curY += normY;
                        completeList.add(new Node(curX, curY));
                    } else {
                        int normX = ix / Math.abs(ix);
                        ix -= normX;
                        curX += normX;
                        completeList.add(new Node(curX, curY));
                    }
                }
            });
        });
        List<Node> noDublicateList = removeDublicates(completeList);
        HashMap<Node, List<Node>> adjacentNodeMap = findAdjacentNodes(noDublicateList);
        System.out.println(adjacentNodeMap.toString());
        this.FINALNODES = convertIntoStreetNodes(adjacentNodeMap).stream().peek(streetNode -> streetNode.selectPiece(this.pieces)).collect(Collectors.toList());
    }

    public static void placeParts(ChunkPos pos, ChunkPos fromChunk, RoadConstructor constructor, CryJigsawManager.Assembler assembler) {
        System.out.println((pos.x - fromChunk.x) + " x-Direction " + (pos.z - fromChunk.z) + " z-Direction");
        // 17-0 = 8/25
        //Chunk in my coordinate System
        Node node = new Node(25 + (pos.x - fromChunk.x), 25 + (pos.z - fromChunk.z));
        BlockPos fromChunkPos = new BlockPos(fromChunk.x << 4, 0, fromChunk.z << 4);
        List<StreetNode> toBePlaced = constructor.FINALNODES.stream().filter(streetNode -> Math.abs(streetNode.getX() - node.getX()) + Math.abs(streetNode.getY() - node.getY()) <= 8).collect(Collectors.toList());
        System.out.println(toBePlaced.size() + " amount of nodes to be placed " + toBePlaced.toString());
        toBePlaced.forEach(k -> System.out.println(k.toString()));
        List<AbstractVillagePiece> piecelist = toBePlaced
                .stream()
                .map(placeNode -> assembler.getPieceFactory()
                        .create(assembler.getTemplateManager(),
                                placeNode.getPiece(), fromChunkPos.add((placeNode.getX() - 25) << 4, 0, (placeNode.getY() - 25) << 4),
                                placeNode.getPiece().getGroundLevelDelta(),
                                placeNode.rotation,
                                placeNode.getPiece().getBoundingBox(assembler.getTemplateManager(), fromChunkPos.add((placeNode.getX() - 25) << 4, 0, (placeNode.getY() - 25) << 4), placeNode.rotation)))
                .collect(Collectors.toList());
        piecelist.forEach(k -> {
            BlockPos offsetOnRotation = CryJigsawManager.offsetBasedOnRot(k.getRotation(), k);
            k.offset(offsetOnRotation.getX(), 0, offsetOnRotation.getZ());
            MutableBoundingBox mutableboundingbox = k.getBoundingBox();
            //*Finds the Midpoint of the X
            int i = (mutableboundingbox.maxX + mutableboundingbox.minX) / 2;
            //System.out.println(i + " This is I");
            //*Finds the Midpoint of the Z
            int j = (mutableboundingbox.maxZ + mutableboundingbox.minZ) / 2;
            //System.out.println(j + " This is j");
            //*Gets the Height of the Map in the MIDDLE of the Jigsawpiece
            int kk = assembler.getChunkGenerator().getNoiseHeight(i, j, Heightmap.Type.WORLD_SURFACE_WG);
            //*Offsets the Piece, so it is above the ground
            k.offset(0, kk - (mutableboundingbox.minY + k.getGroundLevelDelta()), 0);
            System.out.println(k.getPos() + " Pos of k ");
            assembler.getStructurePieces().add(k);
        });
        constructor.FINALNODES.removeIf(toBePlaced::contains);
    }

}
