package View;

import Controller.Command;
import Controller.CreateRooms;
import Model.Item;
import Model.Parser;
import Model.Player;
import Model.Room;
import Controller.ReverseMovement;

import java.util.HashMap;
import java.util.Stack;

/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game. Users
 * can walk around some scenery. That's all. It should really be extended
 * to make it more interesting!
 *
 * To play this game, create an instance of this class and call the "play"
 * method.
 *
 * This main class creates and initialises all the others: it creates all
 * rooms, creates the parser and starts the game. It also evaluates and
 * executes the commands that the parser returns.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game {
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> oldRooms = new Stack<>();
    private Player player = new Player();
    private ReverseMovement reverse = new ReverseMovement();
    private CreateRooms criarSalas = new CreateRooms();

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        criarSalas.createRooms();
        currentRoom = criarSalas.getInicialRoom();
    }

    /**
     * Initizalize.Main play routine. Loops until end of play.
     */
    public void play() {
        printWelcome();
        boolean finished = false;
        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.

        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Obrigado por jogar! Esperamos que tenha se divertido!");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Bem vindos ao mundo de One Piece!");
        System.out.println("Um universo repleto de aventuras, mistérios e piratas destemidos! Em um vasto oceano dividido em quatro mares, seu objetivo é explorar todas as ilhas com muita determinação, atrás de concluir todos os desafios e o principal objetivo: o grande tesouro de ONE PIECE!");
        System.out.println("Embarque nesta emocionante jornada, explore ilhas exóticas! O desafio está lançado, e a busca pelo One Piece está prestes a começar. Aventure-se neste mundo vasto e repleto de possibilidades em sua busca pelo tesouro supremo!");
        System.out.println("Digite 'ajuda' se você precisar de ajuda.");
        System.out.println("Digite 'descricao' se deseja saber mais do personagem.");
        System.out.println();
        printLocationInfo();

    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("Este comando é inválido.");
            return false;
        }

        String commandWord = command.getCommandWord();
        String secondWord = command.getSecondWord();

        if (commandWord.equals("ajuda")) {
            printHelp();
        } else if (commandWord.equals("avancar")) {
            goRoom(command);

        } else if (commandWord.equals("sair")) {
            wantToQuit = quit(command);

        }else if(commandWord.equals("olhar")){
            printLocationInfo();

        }else if(commandWord.equals("voltar")){
            goRoom(command);

        }else if(commandWord.equals("pegar")){
            takeItem(command);

        }else if(commandWord.equals("largar")){
            dropItem(command);

        }else if(commandWord.equals("inventario")){
            healthPlayer();
            String items = player.getItems();
            System.out.println(items);

        }else if(commandWord.equals("abrir")){
            if(currentRoom.existTrunk()){ // verifica se existe bau
                if(currentRoom.getTrunkOpened()){ // verifica se o bau esta aberto
                    System.out.println(currentRoom.getItemsTrunk());

                }else{
                    if(getPlayerItem("chave") != null){ // verifica se possui o item para abrir o bau
                        System.out.println(currentRoom.getItemsTrunk());
                        currentRoom.openTrunk();
                        player.dropItem("chave");

                    }else{
                        System.out.println("Para abrir este baú é necessário ter uma chave!");

                    }
                }
            }else{
                System.out.println("Não existe um baú nessa sala para ser aberto.");

            }
        }else if(commandWord.equals("pegarIB")){
            if(currentRoom.existTrunk()){
                if(currentRoom.getTrunkOpened()){
                    takeItemTrunk(command);

                }else{
                    System.out.println("O baú está fechado! Abra-o primeiro para realizar a ação.");

                }
            }else{
                System.out.println("Não existe um baú nessa sala.");

            }
        }else if(commandWord.equals("largarIB")){
            if(currentRoom.existTrunk()){
                if(currentRoom.getTrunkOpened()){
                    dropItemTrunk(command);

                }else{
                    System.out.println("O baú está fechado! Abra-o primeiro para realizar a ação.");

                }
            }else{
                System.out.println("Não existe um baú nessa sala.");

            }
        }
        else if (commandWord.equals("descricao")){
            printDescrição();

        } else if(commandWord.equals("bussola")){
            if(getPlayerItem("bussola") != null){
                String cDirection = currentRoom.getCorrectDirection();
                System.out.println(cDirection);
                player.dropItem("bussola");

            }else{
                System.out.println("Você não possui o item bussola! Obtenha-o e tente novamente.");

            }

        }else if(commandWord.equals("comer")){
            if(secondWord != null){
                Item item = getPlayerItem(secondWord);
                if( item != null){
                    int regenerate = item.getHealthRegenerate();
                    if(regenerate <= 0){
                        System.out.println("Não é possivel regenerar a sua vida usando este item.");

                    }else{
                        if(player.regenerateHealth(regenerate)){
                            player.dropItem(secondWord);
                            System.out.println("Vida regenerada com sucesso! \n Vida atual: " + player.getHealth());
                        }
                    }
                }else{
                    System.out.println("Comer o que?");
                }
            }else{
                System.out.println("Zoro não tem essa comida.");
            }


        }else if(commandWord.equals("infoItem")){
            if(getPlayerItem(secondWord)  != null){
                Item item = getPlayerItem(secondWord);
                System.out.println(item.getInfoItem());
            }else{
                System.out.println("Zoro não tem esse item");
            }
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp() {
        System.out.println("Você está perdido. Você está sozinho. Você está vagando.");
        System.out.println();
        System.out.println("Seus possíveis comandos são:");
        parser.showCommands();
    }

    private void printDescrição(){
        System.out.println("Você é Roronoa Zoro, o caçador de piratas. Sua ambição é tornar o maior espadachim do mundo, e para isso você terá de conseguir o tão sonhado One Piece. ");
        System.out.println("Seus cabelos são verdes, seu estilo de luta é o Santoryu (conhecido como o estilo das três espadas), você é alto e ama saquê.");
        System.out.println();
        System.out.println("E um pequeno detalhe... você é deslexo, então cuidado com as direções, principalmente se portar uma espada!");
    }

    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) {

        if(command.getCommandWord().equals("voltar")){
            if(oldRooms.isEmpty()){
                System.out.println("Não há mais salas para voltar!");
                return;
            }else{
                if(player.getHealth() < 5){
                    System.out.println("Sem saude para avancar, coma primeiro");
                    return;
                }else{
                    currentRoom = oldRooms.pop();
                    player.lossHealth();
                    healthPlayer();
                    printLocationInfo();
                    return;
                }

            }
        }else if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Ir para onde?");
            return;
        }

        String direction = command.getSecondWord();
        Item item = getPlayerItem("espada");
        String newDirection = reverse.mov(item,direction);

        // Try to leave current room.

        Room nextRoom = currentRoom.getExit(newDirection);

        if(player.getHealth() < 4){
            System.out.println("Sem saude para avancar, coma primeiro.");
        }else{
            if (nextRoom == null) {
                System.out.println("Não há uma saida!");
            }else if(nextRoom.getEnemy()){
                if(item != null){
                    if (nextRoom.getIsHomensPeixe()){
                        Item poneglifo = getPlayerItem("poneglifo");
                        if(poneglifo != null){
                            if(poneglifo.getAmount() > 5){
                                oldRooms.push(currentRoom);
                                currentRoom = nextRoom;
                                oldRooms.clear();
                                player.lossHealth();
                                healthPlayer();
                                printLocationInfo();
                            }else{
                                System.out.println("Você não possui poneglifos suficiente!");
                            }
                        }else{
                            System.out.println("Você não possui o poneglifo.");
                        }

                    }else if(nextRoom.getIsLastRoom()){
                        System.out.println("VOCE GANHOU");
                        System.exit(0);
                    } else{
                        oldRooms.push(currentRoom);
                        currentRoom = nextRoom;
                        player.lossHealth();
                        healthPlayer();
                        printLocationInfo();
                    }
                }else{
                    System.out.println("É necessário uma espada para poder enfregar o inimigo e seguir para a próxima ilha!");
                }
            }else{
                if (nextRoom.getIsHomensPeixe()){
                    Item poneglifo = getPlayerItem("poneglifo");
                    if(poneglifo != null){
                        if(poneglifo.getAmount() > 4){
                            oldRooms.push(currentRoom);
                            currentRoom = nextRoom;
                            oldRooms.clear();
                            player.lossHealth();
                            healthPlayer();
                            printLocationInfo();
                        }else{
                            System.out.println("Você não possui poneglifos suficiente!");
                        }
                    }else{
                        System.out.println("Você não possui o poneglifo.");
                    }

                }else if(nextRoom.getIsLastRoom()){
                    System.out.println("VOCE GANHOU");
                    System.exit(0);
                } else{
                    oldRooms.push(currentRoom);
                    currentRoom = nextRoom;
                    player.lossHealth();
                    healthPlayer();
                    printLocationInfo();
                }
            }
        }


    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     *
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Sair do que?");
            return false;
        } else {
            return true; // signal that we want to quit
        }
    }

    private void printLocationInfo() {
        System.out.println(currentRoom.getLongDescription());
    }

    private void takeItem(Command command){
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Pegar qual item?");
            return;
        }

        String secondWord = command.getSecondWord();
        Item itemRoom = currentRoom.getItem(secondWord);

        if (itemRoom == null) {
            System.out.println("Não existe items na sala!");
            return;
        }

        currentRoom.perdeItem(secondWord);

        if(getPlayerItem(secondWord) != null){
            player.takeItem(secondWord);
        }else{
            player.createItem(itemRoom.getDescription(), itemRoom.getWeight(), 1, secondWord, itemRoom.getHealthRegenerate());
        }

        String itemsOfPlayer = player.getItems();
        String itemsOfRoom = currentRoom.getItems();
        System.out.println(itemsOfPlayer + "\n" + itemsOfRoom);

    }

    private void takeItemTrunk(Command command){
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Pegar qual item?");
            return;
        }

        String secondWord = command.getSecondWord();
        Item itemTrunk = currentRoom.getItemTrunk(secondWord);

        if (itemTrunk == null) {
            System.out.println("O item não existe no baú!");
            return;
        }

        currentRoom.perdeItemTrunk(secondWord);

        if(getPlayerItem(secondWord) != null){
            player.takeItem(secondWord);
        }else{
            player.createItem(itemTrunk.getDescription(), itemTrunk.getWeight(), 1, secondWord, itemTrunk.getHealthRegenerate());
        }

        String itemsOfPlayer = player.getItems();
        String itemsOfTrunk = currentRoom.getItemsTrunk();
        System.out.println(itemsOfPlayer + "\n" + itemsOfTrunk);

    }

    private void dropItem(Command command){
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Pegar qual item?");
            return;
        }

        String secondWord = command.getSecondWord();
        Item itemPlayer = getPlayerItem(secondWord);

        if(itemPlayer != null){
            currentRoom.ganhaItem(secondWord, itemPlayer);
            player.dropItem(secondWord);
        }else{
            System.out.println("Zoro não possui este item.");
        }

        String itemsOfPlayer = player.getItems();
        String itemsOfRoom = currentRoom.getItems();
        System.out.println(itemsOfPlayer + "\n" + itemsOfRoom);

    }

    private void dropItemTrunk(Command command){
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Largar qual item?");
            return;
        }

        String secondWord = command.getSecondWord();
        Item itemPlayer = getPlayerItem(secondWord);

        if(itemPlayer != null){
            currentRoom.ganhaItemTrunk(secondWord, itemPlayer);
            player.dropItem(secondWord);
        }else{
            System.out.println("Zoro não possui este item.");
        }

        String itemsOfPlayer = player.getItems();
        String itemTrunk = currentRoom.getItemsTrunk();
        System.out.println(itemsOfPlayer + "\n" + itemTrunk);

    }

    private void healthPlayer(){
        System.out.println("Vida do Zoro: " + player.getHealth());
    }
    private Item getPlayerItem(String item){
        if(player.getItem(item) != null){
            return player.getItem(item);
        }else{
            return null;
        }
    }
}
