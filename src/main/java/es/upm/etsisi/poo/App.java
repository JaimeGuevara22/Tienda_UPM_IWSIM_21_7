package es.upm.etsisi.poo;

import java.util.Scanner;

public class App {

    private static final String prompt="tUPM> ";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean continuar = true;
        Ticket ticket = new Ticket();
        ProductCatalog catalog = new ProductCatalog();

        System.out.println("Welcome to the ticket module App.");
        System.out.println("Ticket module. Type 'help' to see commands.");


        while(continuar){
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if(input.isEmpty()){continue;}

            if(input.equalsIgnoreCase("help")){
                help();
            }

            else if(input.startsWith("echo")){
                System.out.println(input);
            }

            else if(input.equalsIgnoreCase("exit")){
                System.out.println("Closing application.");
                System.out.println("Goodbye!");
                continuar = false;
            }

            else if(input.startsWith("prod")){
                prodCommand(input, catalog);
            }

            else if(input.startsWith("ticket")){
                ticketCommand(input, ticket, catalog);
            }

        }
        sc.close();
    }
    private static void help() {
    }
    private static void prodCommand(String input, ProductCatalog catalog) {
    }
    private static void ticketCommand(String input, Ticket ticket, ProductCatalog catalog) {
    }
}
