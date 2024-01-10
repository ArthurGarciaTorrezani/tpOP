package Model;

import java.util.HashMap;
import java.util.Set;

/**
 * Class Model.Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * A "Model.Room" represents one location in the scenery of the game. It is
 * connected to other rooms via exits. The exits are labelled north,
 * east, south, west. For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room {
    private String description;
    private HashMap<String, Room> exits = new HashMap<>(); // esquerda chave direita objeto
    private HashMap<String,Item> itemsOnRoom = new HashMap<>();
    private Trunk trunk;
    private String correctDirection;
    private boolean enemy;
    private boolean isLastRoom;
    private boolean isHomensPeixe;
    //private String bestDirection;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     *
     * @param description The room's description.
     */
    public Room(String description, String correctDirection) {
        this.description = description;
        this.correctDirection = correctDirection;
        isLastRoom = false;
        isHomensPeixe = false;
    }
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }
    public void setIsLastRoom(){
        isLastRoom = true;
    }
    public void setIsHomensPeixe(){
        isHomensPeixe = true;
    }
    private void setItem(String name, Item item) {
        itemsOnRoom.put(name,item);
    }

    public void createItem(String description, int weight, int amount, String name, int healthRegenerate) {
        Item item = new Item(description, weight, amount, healthRegenerate);
        setItem(name, item);
    }

    public void createItemTrunk(String description, int weight, int amount, String name, int healthRegenerate) {
        if(!existTrunk()){
            trunk = new Trunk();
        }
        trunk.createItem(description,weight,amount,name, healthRegenerate);
    }
    public void createEnemy(){
        enemy = true;
    }
    public boolean getEnemy(){
        return enemy;
    }
    /**
     * @return The description of the room.
     */
    public String getDescription() {
        return description;
    }
    public Room getExit(String direction) {
        return exits.get(direction) != null ? exits.get(direction) : null;
    }
    private String getExitString() {
        Set<String> keys = exits.keySet();
        String information = String.join(" ", keys);
        return information;
    }
    public String getLongDescription() {
        return "Você está na " + description + "\n" + "Saídas existentes: " + getExitString() + "\n" + getItems() + "\n" + (existTrunk() ? "Há um baú com tesouros nesta sala!":"");
    }
    public String getItems(){
        String information = "";
        Set<String> keys = itemsOnRoom.keySet();
        if(keys.isEmpty()){
            return "";
        }
        else{
            for(String key: keys){
                information +=  key + "->" + itemsOnRoom.get(key).getAmount() + " ";
            }
            return "Itens disponíveis na sala: " + information;
        }
    }
    public void perdeItem(String name){
        itemsOnRoom.get(name).perderItem();
        if(itemsOnRoom.get(name).getAmount() == 0){
            itemsOnRoom.remove(name);
        }
    }
    public void ganhaItem(String name, Item item){
        if(itemsOnRoom.get(name) != null){
            itemsOnRoom.get(name).ganhaItem();
        }else{
            createItem(item.getDescription(), item.getWeight(),1,name,item.getHealthRegenerate());
            int a = itemsOnRoom.get(name).getAmount();
            System.out.println(a);
        }
    }
    public Item getItem(String name){
        return itemsOnRoom.get(name) != null ? itemsOnRoom.get(name) : null;
    }
    public String getItemsTrunk(){
        return trunk.getItems();
    }
    public Item getItemTrunk(String name){
        return trunk.getItem(name);
    }
    public void perdeItemTrunk(String name){
        trunk.perdeItem(name);
    }
    public void ganhaItemTrunk(String name, Item item){
        trunk.ganhaItem(name, item);
    }
    public boolean getTrunkOpened(){
        return trunk.getOpened();
    }
    public void openTrunk(){
        trunk.openTrunk();
    }
    public boolean existTrunk(){
        return trunk == null ? false:true;
    }


    public String getCorrectDirection(){
        return correctDirection;
    }
    public boolean getIsLastRoom(){
        return isLastRoom;
    }
    public boolean getIsHomensPeixe(){
        return isHomensPeixe;
    }

}
