package es.upm.etsisi.poo;

import java.util.Scanner;

public class App {

    private static final String prompt="tUPM> ";

    private static Ticket ticket = new Ticket();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean continuar = true;
        ticket = new Ticket();
        ProductCatalog catalog = new ProductCatalog();

        System.out.println("Welcome to the ticket module App.");
        System.out.println("Ticket module. Type 'help' to see commands.");


        while(continuar){
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            String inputToLower = input.toLowerCase();
            if(input.isEmpty()){continue;}

            if(input.equalsIgnoreCase("help")){
                help();
            }

            else if(inputToLower.startsWith("echo")){
                System.out.println(input);
                System.out.println();
            }

            else if(inputToLower.equalsIgnoreCase("exit")){
                System.out.println("Closing application.");
                System.out.println("Goodbye!");
                continuar = false;
            }

            else if(inputToLower.startsWith("prod")){
                prodCommand(input, catalog);
            }

            else if(inputToLower.startsWith("ticket")){
                ticketCommand(input, catalog);
            }

        }
        sc.close();
    }
    private static void help() {
        System.out.println(
                "Commands:\n" +
                        "  prod add <id> \"<name>\" <category> <price>\n" +
                        "  prod list\n" +
                        "  prod update <id> NAME|CATEGORY|PRICE <value>\n" +
                        "  prod remove <id>\n" +
                        "  ticket new\n" +
                        "  ticket add <prodId> <quantity>\n" +
                        "  ticket remove <prodId>\n" +
                        "  ticket print\n" +
                        "  echo \"<texto>\"\n" +
                        "  help\n" +
                        "  exit\n\n" +
                        "Categories: MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS\n" +
                        "Discounts if there are ≥2 units in the category: MERCH 0%, STATIONERY 5%, CLOTHES 7%, BOOK 10%, ELECTRONICS 3%."
        );
        System.out.println();
    }
    private static void prodCommand(String input, ProductCatalog catalog) {
    String[] parts = input.trim().split(" ");
    try {

        String subcommand = parts[1];


        switch (subcommand.toLowerCase()) {
            case "add" -> {
                int id;
                try {
                    id = Integer.parseInt(parts[2]);

                    StringBuilder nameBuilder = new StringBuilder();
                    int i = 3;
                    while (!parts[i].endsWith("\"")) {
                        nameBuilder.append(parts[i].replace("\"", "")).append(" ");
                        i++;
                    }
                    nameBuilder.append(parts[i].replace("\"", ""));
                    String name = nameBuilder.toString();
                    String category = parts[i + 1];
                    double price = Double.parseDouble(parts[i + 2]);

                    try {
                        Productos product = new Productos(id, name, price, Category.valueOf(category.toUpperCase()));
                        if (catalog.addProduct(product)) {
                            System.out.println(product);
                            System.out.println("prod add: ok");
                        } else {
                            System.out.println("Fail: product not added");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Fail: Product not added");
                    }
                    } catch (NumberFormatException e) {
                        System.out.println("El id debe ser un dato tipo int. ");
                    }
                    System.out.println();

            }
            case "list" -> {
                System.out.println("Catalog:");
                catalog.listProducts();
                System.out.println("prod list: ok");
                System.out.println();
            }
            case "update" -> {
                int id = Integer.parseInt(parts[2]);
                String category = parts[3].toUpperCase();
                String newValue;
                if (category.equals("NAME")) { //en caso de que el nombre tenga más de una palabra
                    StringBuilder sb = new StringBuilder();
                    for (int j = 4; j < parts.length; j++) {
                        if (j > 4) {
                            sb.append(" ");
                        }
                        sb.append(parts[j]);
                    }
                    newValue = sb.toString();
                } else {
                    newValue = parts[4];
                }

                Productos[] products = catalog.getProducts();
                boolean updated = false;

                for (Productos product : products) {
                    if (product != null && product.getId() == id) {
                        try {
                            switch (category) {
                                case "NAME" -> {
                                    StringBuilder nameBuilder = new StringBuilder();
                                    int i = 4;
                                    while (!parts[i].endsWith("\"")) {
                                        nameBuilder.append(parts[i].replace("\"", "")).append(" ");
                                        i++;
                                    }
                                    nameBuilder.append(parts[i].replace("\"", ""));
                                    newValue = nameBuilder.toString();
                                    product.setNombre(newValue);
                                }
                                case "CATEGORY" -> product.setCategoria(Category.valueOf(newValue.toUpperCase()));
                                case "PRICE" -> product.setPrecio(Double.parseDouble(newValue));
                                default -> {
                                    System.out.println("Error: unknown field");
                                    return;
                                }
                            }
                            System.out.println(product);
                            System.out.println("prod update: ok");
                            System.out.println();
                            updated = true;
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Fail: invalid value for update (" + e.getMessage() + ")");
                            System.out.println();
                            updated = true;
                            break;
                        }
                    }
                }

                if (!updated) {
                    System.out.println("Fail: product not found");
                    System.out.println();
                }
            }
            case "remove" -> {
                int id = Integer.parseInt(parts[2]);
                if (catalog.removeProduct(id)) {
                    System.out.println("prod remove: ok");
                } else {
                    System.out.println("Fail: product not removed");
                }
                System.out.println();
            }
            default -> {
                System.out.println("Unknown prod command.");
            }
        }
    }catch (ArrayIndexOutOfBoundsException e){
        System.out.println();
    }
    }
    private static void ticketCommand(String input, ProductCatalog catalog) {

        String[] parts = input.trim().split(" ");
        try {
            String subcommand = parts[1];

            switch (subcommand.toLowerCase()) {
                case "add" -> {
                    if (parts.length != 4) {
                        System.out.println("Ticket add: Error");
                        break;
                    }
                    int productId = Integer.parseInt(parts[2]);
                    int quantity = Integer.parseInt(parts[3]);

                    Productos product = catalog.getProducts()[productId - 1];

                    if (product == null) {
                        System.out.println("Ticket add: Error -Product with ID " + (productId) + " not found.");
                        break;
                    }


                    TicketItem newItem = new TicketItem(product, quantity);


                    if (ticket.addItem(newItem)) {
                        ticket.printTicket();
                        System.out.println("ticket add: ok");
                        System.out.println();
                    } else {

                        System.out.println("Ticket add: Error -Product cant be added");
                    }
                }
                case "remove" -> {

                    int prodId = Integer.parseInt(parts[2]);
                    if (ticket.removeItem(prodId)) {
                        System.out.println("ticket remove: ok");
                    } else {
                        System.out.println("Error: the product wasn't foud in the ticket.");
                    }
                }
                case "new" -> {
                    ticket = new Ticket();
                    System.out.println("ticket new: ok");
                    System.out.println();
                }
                case "print" -> {
                    ticket.printTicket();
                    System.out.println("ticket print: ok");
                    System.out.println();
                }
                default -> {
                    System.out.println("Unknown ticket command.");
                    System.out.println();
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println();
        }
    }

}
