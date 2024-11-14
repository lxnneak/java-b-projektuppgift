package se.linnea.game;

import se.linnea.game.model.Burglar;
import se.linnea.game.model.Resident;

import java.util.Scanner;

public class Game {
    Scanner scanner = new Scanner(System.in);
    private boolean running = true;
    private boolean metBurglar = false;
    private boolean fryingPanFound = false;


    private final String LIVING_ROOM = "living room";
    private final String KITCHEN = "kitchen";
    private final String BEDROOM = "bedroom";
    private final String ENTRYWAY = "entryway";
    private final String OFFICE = "office";
    private String currentLocation = LIVING_ROOM;

    Resident resident = new Resident("Resident", 12, 3);
    Burglar burglar = new Burglar("Burglar", 12, 4);

    Game() {
    }

    void startGame() {
        welcome();
        askNextAction();
    }

    private void welcome() {
        System.out.println("Welcome to the game! " +
                "\nYou wake up on the living room couch in the middle of the night. " +
                "\nYou hear a noise from somewhere in the apartment. \nWhat do you want to do?");
        printAlternatives();
    }

    private void printAlternatives() {
        if (!metBurglar || !burglar.isConscious()) {
            System.out.println("Your alternatives are: \n'office', 'entryway', 'kitchen', 'bedroom', 'living room' or 'quit'");
        } else {
            System.out.println("Your alternatives are: \n'fight', 'office', 'entryway', 'kitchen', 'bedroom', 'living room' or 'quit'");
        }
    }

    private void askNextAction() {
        while (running && resident.isConscious()) {
            String input = getUserInput();
            running = processInput(input);
            if (!resident.isConscious()) {
                System.out.println("Game over");
            }
        }
    }

    private String getUserInput() {
        return scanner.nextLine().toLowerCase();
    }

    private boolean processInput(String input) {
        switch (input) {
            case "living room" -> enterLivingRoom();
            case "kitchen" -> enterKitchen();
            case "bedroom" -> enterBedroom();
            case "entryway" -> enterEntryway();
            case "office" -> {
                enterOffice();
                // running ändras till false när tjuven är knockad och currentLocation = office
                if (!burglar.isConscious() && currentLocation.equals(OFFICE)) {
                    scanner.close();
                    return false;
                }
            }
            case "fight" -> {
                if (metBurglar && burglar.isConscious()) {
                    resident.fightOneRound(resident, burglar);
                    if (resident.isConscious() && burglar.isConscious()) {
                        System.out.println("Enter 'fight' to keep fighting or leave the room");
                    } else if (!burglar.isConscious()) {
                        System.out.println("What should you do now?");
                    }
                } else {
                    System.out.println("Invalid input");
                    printAlternatives();
                }
            }
            case "quit" -> {
                System.out.println("Closing game. Thanks for playing!");
                scanner.close();
                return false;
            }
            default -> {
                System.out.println("Invalid input");
                printAlternatives();
            }
        }
        return true;
    }

    private void enterLivingRoom() {
        if (!currentLocation.equals(LIVING_ROOM)) {
            currentLocation = LIVING_ROOM;
            if (!metBurglar) {
                System.out.println("You enter the living room. \nWhat do you want to do?");
            } else if (burglar.isConscious()) {
                burglarFollows();
            } else {
                System.out.println("You enter the living room. \nWhat now?");
            }
        } else {
            invalidDirection();
        }
    }

    private void enterEntryway() {
        if (currentLocation.equals(LIVING_ROOM)) {
            currentLocation = ENTRYWAY;
            if (!metBurglar) {
                System.out.println("You enter the entryway. \nYou encounter a burglar." +
                        "\nEnter 'fight' to fight the burglar or leave the room");
                metBurglar = true;
            } else if (burglar.isConscious()) {
                burglarFollows();
            } else {
                System.out.println("You enter the entryway. \nWhat now?");
            }
        } else {
            invalidDirection();
        }
    }

    private void enterKitchen() {
        if (currentLocation.equals(LIVING_ROOM)) {
            currentLocation = KITCHEN;
            if (!fryingPanFound) {
                System.out.println("You enter the kitchen. You find a frying pan and pick it up. It might be useful.");
                fryingPanFound = true;
                resident.addDamage(3);
                if (!metBurglar) {
                    System.out.println("What's next?");
                } else {
                    System.out.println("The burglar follows you into the kitchen. \nEnter 'fight' or leave the room");
                }
            } else if (!metBurglar) {
                System.out.println("You enter the kitchen. \nWhat's next?");
            } else if (burglar.isConscious()) {
                burglarFollows();
            } else {
                System.out.println("You enter the kitchen. \nWhat now?");
            }

        } else {
            invalidDirection();
        }
    }

    private void enterOffice() {
        if (currentLocation.equals(LIVING_ROOM)) {
            currentLocation = OFFICE;
            if (!metBurglar) {
                System.out.println("You enter the office. You see a desk with a desktop and a phone. \nWhat's next?");
            } else if (burglar.isConscious()) {
                burglarFollows();
            } else if (!burglar.isConscious()) {
                System.out.println("You enter the office. You pick up the phone and call the police." +
                        "\nCongratulations, you won!");
            }
        } else {
            invalidDirection();
        }
    }

    private void enterBedroom() {
        if (currentLocation.equals(LIVING_ROOM)) {
            currentLocation = BEDROOM;
            if (!metBurglar) {
                System.out.println("You enter the bedroom. You look at the bed but you're not sleepy anymore. \nWhat's next?");
            } else if (burglar.isConscious()) {
                burglarFollows();
            } else {
                System.out.println("You enter the bedroom. \nWhat now?");
            }

        } else {
            invalidDirection();
        }
    }


    private void invalidDirection() {
        System.out.println("You can't go that way right now.");
        printAlternatives();
    }

    private void burglarFollows() {
        System.out.println("The burglar follows you into the " + currentLocation);
        burglar.executePunch(burglar, resident);
        if (resident.isConscious() && burglar.isConscious()) {
            System.out.println("Enter 'fight' or leave the room");
        }
    }

}