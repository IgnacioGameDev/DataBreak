package core.Tile_Engine.Data_Management;

import processing.data.JSONObject;

//Allows an object to implement serialization so it can store its information onto JSON files
public interface Serializable {
    public JSONObject serializeToJSON();
    public void loadJSONObject(JSONObject jsonObject);
}
