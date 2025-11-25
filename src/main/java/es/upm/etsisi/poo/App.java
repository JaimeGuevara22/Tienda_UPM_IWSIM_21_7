package es.upm.etsisi.poo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class App {

    private static final String prompt="tUPM> ";

    private static ProductCatalog catalog = new ProductCatalog();

    private static Ticket ticket = new Ticket();

    private static cashController listCash = new cashController();

    private static ClientController listClient = new ClientController();

    public static void main(String[] args) {
        Scanner sc = null;
        boolean continuar = true;
        ticket = new Ticket();
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

            System.out.println("Welcome to the ticket module App.");
            System.out.println("Ticket module. Type 'help' to see commands.");


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
                        "  client add \"<nombre>\" <DNI> <email> <cashId>\n" +
                        "  client remove <DNI>\n" +
                        "  client list\n" +
                        "  cash add [<id>] \"<nombre>\"<email>\n" +
                        "  cash remove <id>\n" +
                        "  cash list\n" +
                        "  cash tickets <id>\n" +
                        "  ticket new [<id>] <cashId> <userId>\n" +
                        "  ticket add <ticketId><cashId> <prodId> <amount> [--p<txt> --p<txt>]\n" +
                        "  ticket remove <ticketId><cashId> <prodId>\n" +
                        "  ticket print <ticketId> <cashId>\n" +
                        "  ticket list\n" +
                        "  prod add <id> \"<name>\" <category> <price>\n" +
                        "  prod update <id> NAME|CATEGORY|PRICE <value>\n" +
                        "  prod addFood [<id>] \"<name>\" <price> <expiration:yyyy-MM-dd> <max_people>\n" +
                        "  prod addMeeting [<id>] \"<name>\" <price> <expiration:yyyy-MM-dd> <max_people>\n" +
                        "  prod list\n" +
                        "  prod remove <id>\n" +
                        "  help\n" +
                        "  echo \"<text>\"\n" +
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

                }case "addFood" -> {
                    int id;
                    try {
                        id = Integer.parseInt(parts[2]);

                        StringBuilder nameBuilder = new StringBuilder();
                        for (int i = 3; i < parts.length - 3; i++) {
                            nameBuilder.append(parts[i].replace("\"", ""));
                            nameBuilder.append(" ");
                        }
                        String name = nameBuilder.toString();
                        double price = Double.parseDouble(parts[parts.length - 3]);


                        //TODO falta hacer a partir de la fecha de expiración



                    } catch (NumberFormatException e) {
                        System.out.println("Fail: Product not added ");
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
                case "addMeeting" -> {

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
                case "add" -> {  // Da mal, no funciona
                    try {
                        String ticketIdParam = parts[3];   // ID del ticket
                        String cashId = parts[4];          // ID del cajero
                        int productId = Integer.parseInt(parts[5]); // ID del producto
                        int cantidad = Integer.parseInt(parts[6]);  // Cantidad


                        // Verifica que sea el mismo ticket
                        if (!ticket.getTicketId().equals(ticketIdParam)) {
                            System.out.println("Ticket add: Error - ticket ID mismatch");
                            break;
                        }


                        Productos product = catalog.getProducts()[productId - 1];
                        if (product == null) {
                            System.out.println("Ticket add: Error - product not found");
                            break;
                        }


                        TicketItem newItem = new TicketItem(product, cantidad);
                        if (!ticket.addItem(newItem)) {
                            System.out.println("Ticket add: Error - cannot add product");
                            break;
                        }


                        // Imprime el ticket completo con el ID correcto y descuentos
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
                    if (ticket.removeItem(prodId)) {
                        System.out.println("ticket remove: ok");
                    } else {
                        System.out.println("Error: the product wasn't found in the ticket.");
                    }
                }
                case "new" -> {
                    Ticket t = new Ticket();   // siempre creamos el ticket con ID autogenerado
                    String cashId;
                    String userId;

                    if (parts.length == 4) {
                        // ticket new <cashId> <userId>
                        cashId = parts[2];
                        userId = parts[3];
                    } else if (parts.length == 5) {
                        // ticket new <ticketId> <cashId> <userId>
                        String manualId = parts[2];
                        cashId = parts[3];
                        userId = parts[4];

                        // *** Sobrescribimos el ID generado automaticamente ***
                        try {
                            java.lang.reflect.Field f = Ticket.class.getDeclaredField("ticketId");
                            f.setAccessible(true);
                            f.set(t, manualId); // forzamos el ID manual
                        } catch (Exception e) {
                            System.out.println("ticket new: error - could not set manual ticketId");
                            break;
                        }
                    } else {
                        System.out.println("ticket new: error - wrong number of arguments");
                        break;
                    }

                    // Buscar la cash
                    Cash cash = listCash.findCashById(cashId);
                    if (cash != null) cash.addTicket(t);

                    // Imprimir ticket
                    System.out.println("Ticket : " + t.getTicketId());
                    System.out.println("  Total price: 0.0");
                    System.out.println("  Total discount: 0.0");
                    System.out.println("  Final Price: 0.0");
                    System.out.println("ticket new: ok");
                    System.out.println();
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
                    String cashId = parts[2];

                    Cash cash = listCash.findCashById(cashId);
                    if (cash == null) {
                        System.out.println("Error: cash not found");
                        System.out.println();
                        break;
                    }

                    System.out.println("Tickets:");

                    if (cash.getTickets().isEmpty()) {
                        // No hay tickets asociados
                        // (NO imprime nada más en la lista)
                    } else {
                        for (Ticket t : cash.getTickets()) {
                            if (t.getItems().isEmpty()) {
                                System.out.println("  " + t.getTicketId() + "->EMPTY");
                            } else {
                                System.out.println("  " + t.getTicketId() + "->" +
                                        String.format("%.2f", t.getTotalConDescuento()));
                            }
                        }
                    }

                    System.out.println("cash tickets: ok");
                    System.out.println();


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
