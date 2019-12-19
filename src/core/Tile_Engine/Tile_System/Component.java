package core.Tile_Engine.Tile_System;

//Components are an easy way to make versatile code that can be used easily with understandable structure
//Each specific tile made within a game using this engine can use the premade components and with a few line of codes achieve very complex functionality
//In the constructor the component adds itself unto the list that will be updated within the tile
public abstract class Component {
    public Tile t;
    public Component(Tile t)
    {
        this.t = t;
        this.t.addComponentList(this);
    }

    protected abstract void Update();
}
