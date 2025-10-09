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
                System.out.println();
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
    String subcommand = parts[1];

        switch (subcommand) {
            case "add" -> { //Revisar lo de unir las partes entre comillas

                    int id = Integer.parseInt(parts[2]);

                    // Unir todas las partes del nombre que están entre comillas
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

                    Productos product = new Productos(id, name, price, Category.valueOf(category));
                    if (catalog.addProduct(product)) {
                        System.out.println(product);
                        System.out.println("prod add: ok");
                    } else {
                        System.out.println("Fail: product not added");
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
                String newValue = parts[4];

                Productos[] products = catalog.getProducts();
                boolean updated = false;

                for (Productos product : products) {
                    if (product != null && product.getId() == id) {
                        switch (category) {
                            case "NAME" -> product.setNombre(newValue);
                            case "CATEGORY" -> product.setCategoria(Category.valueOf(newValue));
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
                    }
                }

                if (!updated) {
                    System.out.println("Fail: product not found");
                    System.out.println();
                }
            }
            case "remove" -> {
            }
            default -> {
                System.out.println("Unknown prod command.");
            }
        }

    }
    private static void ticketCommand(String input, Ticket ticket, ProductCatalog catalog) {
    }
}
