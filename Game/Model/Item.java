package Model;

import javax.print.DocFlavor.STRING;

public class Item {

    private String description;
    private int weight;
    private int amount;
    private int healthRegenerate;
    public Item(String description, int weight, int amount, int healthRegenerate){
        this.description = description;
        this.weight = weight;
        this.amount = amount;
        this.healthRegenerate = healthRegenerate;
    }
    public void perderItem(){
        amount--;
    }
    public void ganhaItem(){
        amount++;
    }
    public int getAmount(){
        return amount;
    }
    public String getDescription(){
        return description;
    }
    public int getWeight(){
        return weight;
    }
    public int getHealthRegenerate(){return healthRegenerate;}
    public String getInfoItem(){
        String info = "INFORMAÇÕES SOBRE O ITEM:" +
                      "\n Descrição: " + description +
                      "\n Peso: " + weight +
                      "\n Quantidade: " + amount +
                      "\n Vida que regenera: " + healthRegenerate;
        return info;
    }

}
