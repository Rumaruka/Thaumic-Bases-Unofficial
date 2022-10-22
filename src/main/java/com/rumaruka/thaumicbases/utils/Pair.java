package com.rumaruka.thaumicbases.utils;


public class Pair<T1, T2> {
    public T1 obj1;

    public T2 obj2;

    public T1 getFirst() {
        return this.obj1;
    }

    public T2 getSecond() {
        return this.obj2;
    }

    public Pair(T1 o1, T2 o2) {
        this.obj1 = o1;
        this.obj2 = o2;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        return this.obj1.toString() + "," + this.obj2.toString();
    }

    public boolean equals(Object obj) {
        return (obj != null && obj.toString().equals(toString()));
    }
}