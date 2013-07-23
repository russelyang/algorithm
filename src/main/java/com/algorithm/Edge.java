package com.algorithm;

class Edge {

    private Vertex _v1;
    private Vertex _v2;

    public Edge(Vertex v1, Vertex v2) {
        this._v1 = v1;
        this._v2 = v2;
    }

    public Vertex getV1() { return _v1;}
    public Vertex getV2() { return _v2;}

    @Override
    public int hashCode() {
        return _v1.getValue().hashCode() + _v2.getValue().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        Edge other = (Edge) o;
        return other._v1.getValue().equals(this._v1.getValue()) && other._v2.getValue().equals(this._v2.getValue());
    }

    @Override
    public String toString() {
        return "(" + this._v1.getValue() + "," + this._v2.getValue() + ")";
    }
}
