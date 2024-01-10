package Model;

import java.util.HashMap;
import java.util.Set;

public class Trunk {
    private HashMap<String,Item> itemsOnTrunk = new HashMap<>();
    private boolean opened = false;

    public Trunk(){

    }

    private void setItem(String name, Item item) {
        itemsOnTrunk.put(name,item);
    }
    public void createItem(String description, int weight, int amount, String name, int healthRegenerate) {
        Item item = new Item(description, weight, amount,healthRegenerate);
        setItem(name, item);
    }

    public String getItems(){
        String information = "";
        Set<String> keys = itemsOnTrunk.keySet();
        for(String key: keys){
            information +=    key + "->" +itemsOnTrunk.get(key).getAmount() + " ";
        }
        return "Itens disponíveis no baú: " +information;
    }

    public Item getItem(String name){
        return itemsOnTrunk.get(name) != null? itemsOnTrunk.get(name): null;
    }
    public void perdeItem(String name){
        itemsOnTrunk.get(name).perderItem();
        if(itemsOnTrunk.get(name).getAmount() == 0){
            itemsOnTrunk.remove(name);
        }
    }
    public void ganhaItem(String name, Item item){
        if(itemsOnTrunk.get(name) != null){
            itemsOnTrunk.get(name).ganhaItem();
        }else{
            createItem(item.getDescription(), item.getWeight(),1,name, item.getHealthRegenerate());
            int a = itemsOnTrunk.get(name).getAmount();
            System.out.println(a);
        }
    }
    public boolean getOpened(){
        return opened;
    }
    public void openTrunk(){
        opened = true;
    }
    public int getSizeTrunk(){
        int size = 0;
        Set<String> keys = itemsOnTrunk.keySet();
        for(String key:keys){
            size++;
        }
        return size;
    }
}
