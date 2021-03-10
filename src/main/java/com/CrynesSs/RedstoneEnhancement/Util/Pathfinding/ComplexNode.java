package com.CrynesSs.RedstoneEnhancement.Util.Pathfinding;

import java.util.HashMap;

public class ComplexNode {
    private final int x;
    private final int y;
    private int f_cost = 0;
    private int g_cost = 0;
    private int h_cost = 0;
    private final HashMap<Node, Integer> connections;

    public ComplexNode(int x, int y, HashMap<Node, Integer> connectionsAndCosts) {
        this.x = x;
        this.y = y;
        this.connections = connectionsAndCosts;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getF_cost() {
        return f_cost;
    }

    public int getG_cost() {
        return g_cost;
    }

    public int getH_cost() {
        return h_cost;
    }

    public void setF_cost(int f_cost) {
        this.f_cost = f_cost;
    }

    public void setG_cost(int g_cost) {
        this.g_cost = g_cost;
    }

    public void setH_cost(int h_cost) {
        this.h_cost = h_cost;
    }
}
