package com.CrynesSs.RedstoneEnhancement.Util.Pathfinding;

import com.CrynesSs.RedstoneEnhancement.Util.custom.Jigsaws.CrySingleJigsawPiece;
import com.CrynesSs.RedstoneEnhancement.Util.custom.Jigsaws.RoadConstructor;
import net.minecraft.util.Rotation;

import java.util.HashMap;
import java.util.List;

public class StreetNode extends Node {
    public final RoadConstructor.streetType type;
    public Rotation rotation;

    public CrySingleJigsawPiece getPiece() {
        return piece;
    }

    public void setPiece(CrySingleJigsawPiece piece) {
        this.piece = piece;
    }

    private CrySingleJigsawPiece piece;

    public StreetNode(int x, int y, RoadConstructor.streetType type, Rotation rot) {
        super(x, y);
        this.type = type;
        this.rotation = rot;
    }

    public void selectPiece(HashMap<RoadConstructor.streetType, List<CrySingleJigsawPiece>> singleJigsawPieceHashMap) {
        List<CrySingleJigsawPiece> pieces = singleJigsawPieceHashMap.get(this.type);
        if (pieces == null) {
            return;
        }
        if (pieces.size() == 0) {
            return;
        }
        CrySingleJigsawPiece piece = pieces.get((int) Math.floor(Math.random() * pieces.size()));
        this.setPiece(piece);
    }

    @Override
    public String toString() {
        return "StreetNode{" +
                "type=" + type +
                ", rotation=" + rotation +
                ", x=" + getX() +
                ", y=" + getY() +
                '}';
    }

}
