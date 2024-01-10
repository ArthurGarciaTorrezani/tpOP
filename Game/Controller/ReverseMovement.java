package Controller;

import Model.Item;
public class ReverseMovement {
    public String mov(Item espada,String direction){
        if(espada != null){
            switch (direction) {
                case "norte" -> direction = "sul";
                case "sul" -> direction = "norte";
                case "leste" -> direction = "oeste";
                case "oeste" -> direction = "leste";
                case "nordeste" -> direction = "sudoeste";
                case "sudoeste" -> direction = "nordeste";
                case "noroeste" -> direction = "sudeste";
                case "sudeste" -> direction = "noroeste";
            }
        }
        return direction;
    }

}
