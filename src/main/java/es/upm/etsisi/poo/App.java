package es.upm.etsisi.poo;

import javax.xml.catalog.Catalog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Scanner;

public class App {

    private static final String prompt="tUPM> ";

    private static ProductCatalog catalog = new ProductCatalog();
    private static Ticket ticket;

    private static cashController listCash = new cashController();

    private static ClientController listClient = new ClientController();

    public static void main(String[] args) {
        String ticketId = null;
        Scanner sc = null;
        boolean continuar = true;
        ticket = new Ticket(ticketId);
        listCash = new cashController();

        try {
            if (args.length > 0) {
                String nombreFichero = args[0];
                InputStream is = App.class.getResourceAsStream("/" + nombreFichero);
                if (is != null) {
                    sc = new Scanner(is);
                } else {
                    sc = new Scanner(new File(nombreFichero));
                }
            } else {

                System.out.println("Welcome to the ticket module App.");
                System.out.println("Ticket module. Type 'help' to see commands.");
                sc = new Scanner(System.in);
            }


            while (continuar) {
                System.out.print(prompt);
                String input = sc.nextLine().trim();
                String[] parts = input.trim().split(" ");
                String caso = parts[0];
                if (input.isEmpty()) {
                    continue;
                }

                if (caso.toLowerCase().equals("help")) {
                    help();
                } else if (caso.toLowerCase().equals("echo")) {
                    String texto = input.substring(input.indexOf(" ") + 1); // quita la palabra "echo"
                    System.out.println(texto);
                    System.out.println();
                } else if (caso.toLowerCase().equals("exit")) {
                    System.out.println("Closing application.");
                    System.out.println("Goodbye!");
                    continuar = false;
                } else if (caso.toLowerCase().equals("prod")) {
                    prodCommand(input);
                } else if (caso.toLowerCase().equals("ticket")) {
                    ticketCommand(input);
                } else if (caso.toLowerCase().equals("cash")) {
                    cashCommand(input);
                } else if (caso.toLowerCase().equals("client")) {
                    clientCommand(input);
                }


            }
            sc.close();
        }catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sc != null) sc.close();
        }
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
    private static void prodCommand(String input) {
        String[] parts = input.trim().split(" ");
        try {
            String subcommand = parts[1];

            switch (subcommand.toLowerCase()) {
                case "add" -> {
                    int id;
                    try {
                        id = Integer.parseInt(parts[2]);

                        StringBuilder nameBuilder = new StringBuilder();
                        for (int i = 3; i < parts.length - 2; i++) {
                            nameBuilder.append(parts[i].replace("\"", ""));
                            nameBuilder.append(" ");
                        }
                        String name = nameBuilder.toString();
                        String category = parts[parts.length - 2];
                        double price = Double.parseDouble(parts[parts.length - 1]);

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
                        System.out.println("Fail: Product not added ");
                    }
                    System.out.println();

                }case "addfood" -> {
                    String id;
                    try {
                        id = (parts[2]);

                        StringBuilder nameBuilder = new StringBuilder();
                        for (int i = 3; i < parts.length - 3; i++) {
                            nameBuilder.append(parts[i].replace("\"", ""));
                            nameBuilder.append(" ");
                        }
                        String name = nameBuilder.toString();
                        double price = Double.parseDouble(parts[parts.length - 3]);
                        LocalDate date = LocalDate.parse(parts[parts.length - 2]);
                        //TODO comprobar que pasa si metemos un long
                        int max_people = Integer.parseInt(parts[parts.length - 1]);
                        Food food = new Food(date, max_people, price, id, name);
                        if (catalog.addProduct(food)) {
                            System.out.println(food);
                            System.out.println("prod addFood: ok");
                        } else {
                            System.out.println("Error processing ->prod addFood ->Error adding product");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Fail: the id is not valid"+"Fail: " + e.getMessage() + "\n");

                        System.out.println();

                    }
                }case "list" -> {
                    System.out.println("Catalog:");
                    catalog.listProducts();
                    System.out.println("prod list: ok");
                    System.out.println();
                }
                case "update" -> {
                    try {
                        int id = Integer.parseInt(parts[2]);
                        String field = parts[3].toUpperCase();
                        String newValue;

                        // Si el NAME tiene espacios o comillas
                        if (field.equals("NAME")) {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 4; i < parts.length; i++) {
                                if (i > 4) sb.append(" ");
                                sb.append(parts[i].replace("\"", ""));
                            }
                            newValue = sb.toString();
                        } else {
                            newValue = parts[4];
                        }

                        // 1️⃣ Obtener el producto antes de actualizar
                        Productos product = catalog.getProductById(id);

                        if (product == null) {
                            System.out.println("Fail: product not found\n");
                            break;
                        }

                        // 2️⃣ Actualizar el campo
                        boolean ok = catalog.updateField(id, field, newValue);

                        if (ok) {
                            // 3️⃣ Imprimir el producto actualizado
                            System.out.println(product.toString());
                            System.out.println("prod update: ok\n");
                        } else {
                            System.out.println("Fail: invalid value or field\n");
                        }

                    } catch (Exception e) {
                        System.out.println("Fail: invalid parameters\n");
                    }
                }

                case "remove" -> {
                    int id = Integer.parseInt(parts[2]);
                    Object removed = catalog.getProductById(id);
                    if (catalog.remove(id)) {
                        System.out.println(removed.toString());
                        System.out.println("prod remove: ok");
                    } else {
                        System.out.println("Fail: product not removed");
                    }
                    System.out.println();
                }
                case "addmeeting" -> {
                    String id;
                    try {
                        id = (parts[2]);

                        StringBuilder nameBuilder = new StringBuilder();
                        for (int i = 3; i < parts.length - 3; i++) {
                            nameBuilder.append(parts[i].replace("\"", ""));
                            nameBuilder.append(" ");
                        }
                        String name = nameBuilder.toString();
                        double price = Double.parseDouble(parts[parts.length - 3]);
                        LocalDate date = LocalDate.parse(parts[parts.length - 2]);
                        int max_people = Integer.parseInt(parts[parts.length - 1]);
                        Meetings meetings = new Meetings(date, max_people, price, id, name);
                        if (catalog.addProduct(meetings)) {
                            System.out.println(meetings);
                            System.out.println("prod addMeeting: ok");
                        } else {
                            System.out.println("Error processing ->prod addMeeting ->Error adding product");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Fail: the id is not valid");

                    }
                }
                default -> {
                    System.out.println("Unknown prod command.");
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println();
        }
    }
    private static void ticketCommand(String input) {


        String[] parts = input.trim().split(" ");
        try {
            String subcommand = parts[1];


            switch (subcommand.toLowerCase()) {
                case "add" -> {
                    try {
                        String ticketId = parts[2];
                        String cashId = parts[3];
                        int productId = Integer.parseInt(parts[4]);
                        int cantidad = Integer.parseInt(parts[5]);


                        if (!ticket.getTicketId().equals(ticketId)) {
                            System.out.println("Ticket add: Error - ticket ID mismatch");
                            break;
                        }


                        Productos product = catalog.getProductById(productId);
                        if (product == null) {
                            System.out.println("Ticket add: Error - product not found");
                            break;
                        }


                        TicketItem newItem = new TicketItem(product, cantidad);
                        if (!ticket.addItem(newItem)) {
                            System.out.println("Ticket add: Error - cannot add product");
                            break;
                        }


                        System.out.println("Ticket : " + ticket.getTicketId());
                        ticket.printTicket();


                        System.out.println("ticket add: ok");
                        System.out.println();
                    } catch (Exception e) {
                        System.out.println("Ticket add: Error - invalid parameters");
                    }
                }


                case "remove" -> {
                    int prodId = Integer.parseInt(parts[2]);
                    Object removed = catalog.getProductById(prodId);
                    if (ticket.removeItem(prodId)) {
                        System.out.println(removed.toString());
                        System.out.println("ticket remove: ok");
                    } else {
                        System.out.println("Error: the product wasn't found in the ticket.");
                    }
                }
                case "new" -> {
                    String id = null;
                    String cashId;
                    String clientId;
                    if (parts.length == 3) {
                        System.out.println("ticket new: error de formato");
                        return;
                    }
                    else if (parts.length == 4) {
                        cashId = parts[2];
                        clientId = parts[3];
                    }
                    else if (parts.length == 5) {
                        id = parts[2];
                        cashId = parts[3];
                        clientId = parts[4];
                    }
                    else {
                        System.out.println("ticket new: error de formato");
                        return;
                    }
                    Cash cash = listCash.findCashById(cashId);
                    if(cash == null){
                        System.out.println("ticket new: cajero no encontrado");
                        return;
                    }
                    Client client = listClient.findClientByDNI(clientId);
                    if(client == null){
                        System.out.println("ticket new: cliente no encontrado");
                        return;
                    }
                    Ticket t = new Ticket(id);
                    client.addTicket(t);
                    cash.addTicket(t);

                    ticket = t;

                    System.out.println("Ticket : " + t.getTicketId());
                    System.out.println("  Total price: 0.0");
                    System.out.println("  Total discount: 0.0");
                    System.out.println("  Final Price: 0.0");
                    System.out.println("ticket new: ok");

                }
                case "print" -> {
                    ticket.printTicket();
                    System.out.println("ticket print: ok");
                    System.out.println();
                }
                case "list" -> {


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

    private static void cashCommand(String input) {
        String[] parts = input.trim().split(" ");
        try {
            String subcommand = parts[1];
            switch (subcommand.toLowerCase()) {
                case "add" -> {
                    String cashId = null;
                    String name = null;
                    String email = null;
                    if (parts.length == 4) {
                        name = parts[2].replace("\"", "");
                        email = parts[3];

                    } else if (parts.length == 5) {
                        cashId = parts[2];
                        name = parts[3].replace("\"", "");
                        email = parts[4];
                    }

                    Cash newCash = new Cash(name,email, cashId);
                    if(listCash.addCash(newCash)) {
                        System.out.println(newCash);
                        System.out.println("cash add: ok");
                        System.out.println();
                    }
                }
                case "remove" -> {
                    String cashId = parts[2];
                    if(listCash.cashRemove(cashId)) {
                        System.out.println("cash remove: ok");
                    } else {
                        System.out.println("Error: the cash wasn't found in the list.");
                    }
                    System.out.println();

                }
                case "list" -> {
                    listCash.list();
                    System.out.println("cash list: ok");
                    System.out.println();

                }
                case "tickets" -> {


                }

            }


        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println();
        }

    }
    private static void clientCommand(String input) {
        String[] parts = input.trim().split(" ");
        try {
            String subcommand = parts[1];
            switch (subcommand.toLowerCase()) {
                case "add" -> {
                    String name = parts[2].replace("\"", "");
                    String dni = parts[3];
                    String email = parts[4];
                    String cashId = parts[5];

                    Client newClient = new Client(name, dni, email, cashId);
                    if(listClient.addClient(newClient)) {
                        System.out.println(newClient);
                        System.out.println("client add: ok");
                        System.out.println();
                    }

                }
                case "remove" -> {
                    String name = parts[2];
                    if(listClient.removeClient(name)) {
                        System.out.println("client remove: ok");
                    } else {
                        System.out.println("Error: the client wasn't found in the list.");
                    }
                    System.out.println();
                }
                case "list" -> {
                    listClient.list();
                    System.out.println("client list: ok");
                    System.out.println();

                }


            }


        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println();
        }

    }

}
