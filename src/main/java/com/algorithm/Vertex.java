package com.algorithm;

class Vertex {

    private Integer value;

    public Vertex(Integer n) {
       this.value = n;
    }

    public Integer getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if( obj == null) return false;
        Vertex other = (Vertex) obj;
        return other.getValue().equals(this.value);
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
