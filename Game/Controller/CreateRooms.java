package Controller;
import Model.Room;
import Model.Item;
import Model.Trunk;

import java.util.HashMap;

import javax.xml.stream.events.EndElement;

public class CreateRooms {
    Room currentRoom;
    public void createRooms(){

        Room shellsTown, orangeTown, islandsGecko,baratie,villageCocoyasi, logueTown,
                reverseMoutain, islandCactus, littleGarden, islandDrum, alabasta,
                jaya, skypiea, water7, enniesLobby, thrillerBark, sabaody,
                amazonLily, impelDown, marineFord,
                homensPeixe, punkRazard, dressrosa, zou,wholeCake,wano, fim;

        // create the rooms
        shellsTown = new Room("shellsTown",null);


        orangeTown = new Room("orangeTown","norte");
        islandsGecko = new Room("islandsGecko","nordeste");
        baratie = new Room("baratie","noroeste");
        villageCocoyasi = new Room("villageCocoyasi","norte");
        logueTown = new Room("logueTown",null);

        reverseMoutain = new Room("reverseMoutain","noroeste");
        islandCactus = new Room("islandCactus","oeste");
        littleGarden = new Room("littleGarden","sudoeste");
        islandDrum = new Room("islandDrum","oeste");
        alabasta = new Room("alabasta",null);

        jaya = new Room("jaya",null);
        skypiea = new Room("skypiea",null);

        water7 = new Room("water7",null);
        enniesLobby = new Room("enniesLobby",null);

        thrillerBark = new Room("thrillerBark",null);

        sabaody = new Room("sabaody","oeste");
        amazonLily = new Room("amazonLily",null);
        impelDown = new Room("impelDown",null);
        marineFord = new Room("marineFord",null);

        homensPeixe = new Room("homensPeixe",null);
        homensPeixe.setIsHomensPeixe();

        zou = new Room("zou",null);
        punkRazard = new Room("punkRazard",null);
        dressrosa = new Room("dressrosa",null);
        wholeCake = new Room("wholeCake",null);
        wano = new Room("wano",null);

        fim = new Room("FIM",null);
        fim.setIsLastRoom();

        shellsTown.setExit("norte",orangeTown);

        // first group
        orangeTown.setExit("nordeste",baratie);
        orangeTown.setExit("noroeste",islandsGecko);
        orangeTown.setExit("norte",villageCocoyasi);
        orangeTown.setExit("sul",shellsTown);

        islandsGecko.setExit("nordeste",villageCocoyasi);
        islandsGecko.setExit("leste",baratie);
        islandsGecko.setExit("sudeste",orangeTown);


        baratie.setExit("noroeste",villageCocoyasi);
        baratie.setExit("oeste",islandsGecko);
        baratie.setExit("sudoeste",orangeTown);

        villageCocoyasi.setExit("norte",logueTown); // mudar a coordenada para logueTown
        villageCocoyasi.setExit("sudeste",baratie);
        villageCocoyasi.setExit("sudoeste",islandsGecko);
        villageCocoyasi.setExit("sul",orangeTown);

        logueTown.setExit("norte",reverseMoutain); // mudar a coordenada para reverseMoutain
        logueTown.setExit("sul",villageCocoyasi);

        // second group
        reverseMoutain.setExit("nordeste",islandCactus);
        reverseMoutain.setExit("noroeste",islandDrum);
        reverseMoutain.setExit("norte",littleGarden);
        reverseMoutain.setExit("sul",logueTown);

        islandDrum.setExit("nordeste",littleGarden);
        islandDrum.setExit("leste",islandCactus);
        islandDrum.setExit("sudeste",reverseMoutain);
        islandDrum.setExit("oeste",alabasta); // mudar a coordenada para reverseMoutain

        islandCactus.setExit("noroeste",littleGarden);
        islandCactus.setExit("oeste",islandDrum);
        islandCactus.setExit("sudoeste",reverseMoutain);

        littleGarden.setExit("sul",reverseMoutain);
        littleGarden.setExit("sudeste",islandCactus);
        littleGarden.setExit("sudoeste",islandDrum);

        alabasta.setExit("leste",islandDrum);
        alabasta.setExit("norte",jaya);

        // third group
        jaya.setExit("leste",skypiea);
        jaya.setExit("sul",alabasta);

        skypiea.setExit("norte",water7);
        skypiea.setExit("oeste",jaya);

        // fourth group
        water7.setExit("leste",enniesLobby);
        water7.setExit("sul",skypiea);

        enniesLobby.setExit("nordeste",thrillerBark);
        enniesLobby.setExit("oeste",water7);

        // fifth group
        thrillerBark.setExit("noroeste",sabaody);
        thrillerBark.setExit("sudoeste",enniesLobby);

        // sixth group
        sabaody.setExit("sudoeste",amazonLily);
        sabaody.setExit("norte",impelDown);
        sabaody.setExit("oeste",marineFord);

        amazonLily.setExit("nordeste",sabaody);

        impelDown.setExit("sul",sabaody);

        marineFord.setExit("leste",sabaody);
        marineFord.setExit("norte",homensPeixe);

        // seventh group
        homensPeixe.setExit("leste",punkRazard);
        homensPeixe.setExit("norte",wholeCake);
        homensPeixe.setExit("oeste",dressrosa);

        punkRazard.setExit("norte",wano );
        punkRazard.setExit("oeste",homensPeixe);

        wholeCake.setExit("oeste",zou);
        wholeCake.setExit("sul",homensPeixe);

        dressrosa.setExit("nordeste",zou);
        dressrosa.setExit("leste",homensPeixe);

        wano.setExit("norte",fim);

        // create items and trunks
        shellsTown.createItem("batata", 1, 3, "batata",1);
        shellsTown.createItem("te da a direção correta", 1, 3, "bussola",0);

        orangeTown.createItem("carne", 3, 1, "carne",3);
        orangeTown.createItem("espada", 1, 1, "espada",0);

        islandsGecko.createItem("chave", 1, 1, "chave",0); // chave 1
        islandsGecko.createItem("batata", 1, 3, "batata",1);

        baratie.createItem("peixe", 1, 1, "peixe",1);
        baratie.createItem("agua", 2, 1, "agua",2);

        villageCocoyasi.createItemTrunk("poneglifo", 1, 1, "poneglifo",0); // bau 1
        villageCocoyasi.createItemTrunk("chave", 1, 1, "chave",0); // chave 2

        logueTown.createItemTrunk("poneglifo", 1, 1, "poneglifo",0); // bau 2
        logueTown.createItemTrunk("bussola", 1, 1, "bussola",0);
        logueTown.createItemTrunk("saque", 4, 3, "saque",4);

        islandCactus.createItemTrunk("espada", 1, 1, "espada",3); // bau 3
        islandCactus.createItem("agua", 2, 1, "agua",2);

        littleGarden.createItem("chave", 1, 1, "chave",0); // chave 3
        littleGarden.createItem("carne", 3, 1, "carne",3);

        alabasta.createItem("chave", 1, 2, "chave",0); // chave 4
        alabasta.createItemTrunk("poneglifo", 1, 2, "poneglifo",0); // bau 4
        alabasta.createItemTrunk("saque", 4, 3, "saque",4);
        alabasta.createItemTrunk("batata", 1, 1, "batata",1);


        skypiea.createItem("saque", 4, 3, "saque",4);
        skypiea.createItem("chave", 1, 3, "chave", 0); // chave 5

        enniesLobby.createItem("batata", 1, 3, "batata",1);

        sabaody.createItem("traje", 1, 1, "traje",0);
        sabaody.createItem("agua", 2, 1, "agua",2);

        marineFord.createItem("poneglifo", 1, 2, "poneglifo",0); // bau 5
        marineFord.createItem("espada lendaria", 1, 1, "espada lendaria",0);
        marineFord.createItemTrunk("saque", 4, 3, "saque",4);

        wano.createItem("chave one piece", 1, 1, "chave one piece",0);

        fim.createItemTrunk("ONE PIECE", 1, 1, "ONE PIECE",0);

        // create enemy
        logueTown.createEnemy();
        alabasta.createEnemy();
        skypiea.createEnemy();
        enniesLobby.createEnemy();
        marineFord.createEnemy();

        currentRoom = shellsTown;


    }

    public Room getInicialRoom(){
        return currentRoom;
    }




}
