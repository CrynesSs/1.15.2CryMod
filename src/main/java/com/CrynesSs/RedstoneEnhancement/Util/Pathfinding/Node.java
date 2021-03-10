package com.CrynesSs.RedstoneEnhancement.Util.Pathfinding;

import com.sun.javafx.geom.Vec2d;

import java.util.Objects;

public class Node {

    private final int x;
    private final int y;
    private int f_cost = 0;
    private int g_cost = 0;
    private int h_cost = 0;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void calculateCosts(Node BeginNode, Node EndNode) {
        this.g_cost = distanceBetweenNodes(BeginNode, this);
        this.h_cost = distanceBetweenNodes(EndNode, this);
        this.f_cost = this.g_cost + this.h_cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x &&
                y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static int distanceBetweenNodes(Node node1, Node node2) {
        return Math.abs(node1.getX() - node2.getX()) + Math.abs(node1.getY() - node2.getY());
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", f_cost=" + f_cost +
                ", g_cost=" + g_cost +
                ", h_cost=" + h_cost +
                '}';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vec2d makePath(Node sub) {
        return new Vec2d(this.getX() - sub.x, this.y - sub.y);
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
