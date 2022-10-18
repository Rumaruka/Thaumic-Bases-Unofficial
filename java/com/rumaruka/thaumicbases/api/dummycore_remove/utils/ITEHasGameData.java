package com.rumaruka.thaumicbases.api.dummycore_remove.utils;

public interface ITEHasGameData {

    public abstract String getData();

    /**
     * This is recieved on the client. Use the given array to restore the values you've written
     * @param data
     */
    public abstract void setData(DummyData[] data);

    /**
     *
     * @return the Coord3D object representing the position of your tileEntity
     */
    public abstract Coord3D getPosition();
}
