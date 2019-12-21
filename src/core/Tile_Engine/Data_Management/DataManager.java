package core.Tile_Engine.Data_Management;

import core.Tile_Engine.Tile_System.Tile;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.ArrayList;

//Saves information about a list of objects into a JSON file and then loads that JSON file into a list which the information can be taken back from
public class DataManager {

    PApplet parent;
    private String loadGameFile;
    private String dataFolder = "src/core/DataBreak/Level_Data/";
    public JSONObject gameData;

    public DataManager(PApplet p, String fileName){
        this.parent = p;
        loadGameFile = fileName;
    }

    public void load(){
        this.gameData = parent.loadJSONObject(dataFolder + loadGameFile);
    }

    public void save(ArrayList<Tile> jsonList, String nameOfList){
        JSONArray newList = new JSONArray();
        for(Serializable serialJson : jsonList){
            newList.append(serialJson.serializeToJSON());
        }
        this.gameData.setJSONArray(nameOfList, newList);
        parent.saveJSONObject(this.gameData, dataFolder + loadGameFile);
    }

    public JSONArray get_json_array(String arrayName){
        if(this.gameData.hasKey(arrayName)){
            return this.gameData.getJSONArray(arrayName);
        }
        return null;
    }
}
