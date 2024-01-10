package Model;

import Model.Item;

import java.util.HashMap;
import java.util.Set;

public class Player {

    private int limitWeight = 20;
    private int totalWeight;
    private int health = 15;
    private int healthMax = 15;


    private HashMap<String, Item> itemsOfPlayer = new HashMap<>();

    public void takeItem(String name){
        itemsOfPlayer.get(name).ganhaItem();
        updateWeight(name,true);
    }

    public void dropItem(String name){
        itemsOfPlayer.get(name).perderItem();
        updateWeight(name,false);
        if(itemsOfPlayer.get(name).getAmount() == 0){
            itemsOfPlayer.remove(name);
        }
    }

    public String getItems(){
        Set<String> keys = itemsOfPlayer.keySet();
        String information = "";
        for(String key: keys){
            information += " "+key +":"+ itemsOfPlayer.get(key).getAmount();
        }
        return  "Items do Zoro: " + information + "\n"+ "Peso total: "+ totalWeight;
    }

    public Item getItem(String name){
        return itemsOfPlayer.get(name) != null ? itemsOfPlayer.get(name) : null;
    }

    private void setItem(String name, Item item) {
        itemsOfPlayer.put(name,item);
        updateWeight(name,true);
    }

    public void createItem(String description, int weight, int amount, String name, int healthRegenerate) {
        Item item = new Item(description, weight, amount,healthRegenerate);
        setItem(name, item);
    }

    private void updateWeight(String name, boolean add){
        if(add){
            if(totalWeight == limitWeight){
                System.out.println("Espaço insuficiente!");
            }else{
                totalWeight +=  itemsOfPlayer.get(name).getWeight();
            }

        }else{
            totalWeight -=  itemsOfPlayer.get(name).getWeight();
        }

    }

    public boolean regenerateHealth(int health){
        int ha = this.health;
        if((ha += health) > healthMax){
            System.out.println("Não é possivel passar a vida maxima de 15");
            return false;
        }else{
            this.health += health;
            return true;
        }

    }
    public void lossHealth(){
        health--;
    }
    public int getHealth(){
        return health;
    }
}
