package es.upm.etsisi.poo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import static java.util.Collections.sort;

public class App {

    private static final String prompt="tUPM> ";

    private static ProductCatalog catalog = new ProductCatalog();
    private static Ticket ticket;

    private static cashController listCash = new cashController();

    private static ClientController listClient = new ClientController();
    private static List<Ticket> listTicket = new ArrayList<Ticket>();

    public static void main(String[] args) {
        String ticketId = null;
        String cashId = null;
        Scanner sc = null;
        boolean continuar = true;
        ticket = new Ticket(ticketId, cashId);
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
                        "  client add \"<nombre>\" <DNI> <email> <cashID> \n"+
                        "  client remove <DNI> \n"+
                        "  client list \n"+
                        "  cash add [<id>] \"<nombre>\" <email> \n"+
                        "  cash remove <id> \n"+
                        "  cash list \n"+
                        "  cash tickets <id> \n"+
                        "  ticket new [<id>] <cashID> <userID> \n" +
                        "  ticket add <ticketId><cashId> <prodId> <amount> [--p<txt> --p<txt>]\n" +
                        "  ticket remove <ticketId><cashId> <prodId>\n" +
                        "  ticket print <ticketId> <cashId>\n" +
                        "  ticket list \n"+
                        "  prod add <id> \"<name>\" <category> <price>\n" +
                        "  prod update <id> NAME|CATEGORY|PRICE <value>\n" +
                        "  prod addFood [<id>] \"<name>\" <price> <expiration:yyyy-MM-dd> <max_people>\n"+
                        "  prod addMeeting [<id>] \"<name>\" <price> <expiration:yyyy-MM-dd> <max_people>\n"+
                        "  prod list\n" +
                        "  prod remove <id>\n" +
                        "  help\n" +
                        "  echo \"<texto>\"\n" +
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
                    try {
                        // Id siempre es la segunda palabra
                        int id = Integer.parseInt(parts[2]);

                        int firstQuote = input.indexOf('"');
                        int lastQuote = input.lastIndexOf('"');
                        if (firstQuote == -1 || lastQuote == -1 || lastQuote == firstQuote) {
                            System.out.println("Fail: invalid name format");
                            break;
                        }
                        String name = input.substring(firstQuote + 1, lastQuote);

                        String afterName = input.substring(lastQuote + 1).trim();
                        String[] tail = afterName.split(" ");

                        if (tail.length < 2) {
                            System.out.println("Fail: invalid parameters");
                            break;
                        }

                        String categoryTxt = tail[0];
                        double price = Double.parseDouble(tail[1]);

                        Productos product;

                        if (tail.length == 3) {
                            int maxPersonal = Integer.parseInt(tail[2]);
                            product = new ProductosPersonalizables(id, name, price, Category.valueOf(categoryTxt), maxPersonal);
                        } else {
                            product = new Productos(id, name, price, Category.valueOf(categoryTxt));
                        }

                        if (catalog.addProduct(product)) {
                            System.out.println(product.toString());
                            System.out.println("prod add: ok\n");
                        } else {
                            System.out.println("Fail: product not added\n");
                        }

                    } catch (Exception e) {
                        System.out.println("Fail: Product not added\n");
                    }
                }
                case "addfood" -> {
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
                        Food food = new Food(date, max_people, price, id, name);
                        if (catalog.addProduct(food)) {
                            System.out.println(food);
                            System.out.println("prod addFood: ok");
                        } else {
                            System.out.println("Error processing ->prod addFood ->Error adding product");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error processing ->prod addMeeting ->Error adding product");

                        System.out.println();
                    }catch (DateTimeParseException e) {
                        System.out.println("Error processing ->prod addFood ->Error adding product");
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

                        Productos product = catalog.getProductById(id);

                        if (product == null) {
                            System.out.println("Fail: product not found\n");
                            break;
                        }

                        boolean ok = catalog.updateField(id, field, newValue);

                        if (ok) {
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
                        System.out.println("Error processing ->prod addMeeting ->Error adding product");


                    }catch(DateTimeParseException e){
                        System.out.println("Error processing ->prod addMeeting ->Error adding product");
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

                        if (ticket.getState() == TicketState.CLOSED) {
                            System.out.println("Ticket already closed");
                            break;
                        }

                        String ticketId = parts[2];
                        String cashId = parts[3];
                        String itemId = parts[4];
                        int cantidad = Integer.parseInt(parts[5]);

                        if (!ticket.getTicketId().equals(ticketId)) {
                            System.out.println("Ticket add: Error - ticket ID mismatch");
                            break;
                        }

                        // Buscar item en catálogo
                        Object item = catalog.getProductById(Integer.parseInt(itemId));
                        if (item == null) {
                            System.out.println("Ticket add: Error - item not found");
                            break;
                        }

                        if (ticket.getItemsCount() + cantidad > 100) {
                            System.out.println("Ticket add: Error - cannot add, exceeds maximum 100 items per ticket");
                            break;
                        }

                        ArrayList<String> nuevosTextos = new ArrayList<>();

                        for (int i = 6; i < parts.length; i++) {
                            String param = parts[i];
                            if (param.startsWith("--p")) {
                                String txt = param.substring(3);  // elimina "--p"
                                nuevosTextos.add(txt);
                            }
                        }

                        if (item instanceof ProductosPersonalizables orig) {

                            for (String t : nuevosTextos) {
                                orig.addTexto(t);
                            }

                            for (int u = 0; u < cantidad; u++) {

                                ProductosPersonalizables copia = new ProductosPersonalizables(orig.getId(), orig.getNombre(),
                                        orig.getPrecioBase(), orig.getCategoria(), orig.getMaxTextos());

                                for (String t : orig.getTextos()) copia.addTexto(t);

                                TicketItem newItem = new TicketItem(copia, 1);
                                if (!ticket.addItem(newItem)) {
                                    System.out.println("Ticket add: Error - cannot add item");
                                    break;
                                }
                            }

                        } else {
                            TicketItem newItem = new TicketItem(item, cantidad);
                            if (!ticket.addItem(newItem)) {
                                System.out.println("Ticket add: Error - cannot add item");
                            }
                        }

                        ticket.setState(TicketState.OPEN);
                        System.out.println("Ticket: " + ticket.getTicketId());
                        ticket.printTicket();
                        System.out.println("ticket add: ok\n");

                    } catch (Exception e) {
                        System.out.println("Ticket add: Error - invalid parameters");
                    }
                }

                case "remove" -> {
                    int prodId = Integer.parseInt(parts[parts.length - 1]);
                    Object removed = catalog.getProductById(prodId);
                    if (ticket.removeItem(String.valueOf(prodId))) {
                        System.out.println("Ticket: "+ticket.getTicketId());
                        ticket.printTicket();
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
                        System.out.println("Format error");
                        return;
                    }
                    Cash cash = listCash.findCashById(cashId);
                    if(cash == null){
                        System.out.println("Cash not found");
                        return;
                    }
                    Client client = listClient.findClientByDNI(clientId);
                    if(client == null){
                        System.out.println("Client not found");
                        return;
                    }
                    Ticket t = new Ticket(id, cashId);
                    client.addTicket(t);
                    cash.addTicket(t);

                    ticket = t;
                    listTicket.add(t);

                    System.out.println("Ticket : " + t.getTicketId());
                    System.out.println("  Total price: 0.0");
                    System.out.println("  Total discount: 0.0");
                    System.out.println("  Final Price: 0.0");
                    System.out.println("ticket new: ok");
                    System.out.println();

                }
                case "print" -> {
                    ticket.close();
                    ticket.printTicket();
                    System.out.println("ticket print: ok");
                    System.out.println();
                }
                case "list" -> {
                    if(listTicket.isEmpty()){
                        System.out.println("There is no tickets in the ticketList.");
                        return;
                    }
                    List<Ticket> copia = new ArrayList<>(listTicket);
                    copia.sort(Comparator.comparing(Ticket::getTicketId));
                    for(Ticket t : copia){
                        System.out.println(t.getTicketId()+ " - "+t.getState());
                    }
                    System.out.println("ticket list: ok");
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
                    try {
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

                        Cash newCash = new Cash(name, email, cashId);
                        if (listCash.addCash(newCash)) {
                            System.out.println(newCash);

                            System.out.println("cash add: ok");
                            System.out.println();
                        }
                    }catch (IllegalArgumentException e){
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
                    System.out.println("Cash: ");
                    listCash.list();
                    System.out.println("cash list: ok");
                    System.out.println();

                }
                case "tickets" -> {
                    if(parts.length != 3) {
                        System.out.println("Format error.");
                        return;
                    }
                    String id = parts[2];
                    Cash cash = listCash.findCashById(id);
                    if(cash == null){
                        System.out.println("Error: cash not found.");
                        return;
                    }
                    List<Ticket> ticketsCash = cash.getTickets();
                    ticketsCash.sort(Comparator.comparing(Ticket::getTicketId)); //para ordenar por id
                    System.out.println("Tickets:");
                    for(Ticket t : ticketsCash){
                        System.out.println(t.getTicketId()+"->"+t.getState());
                    }
                    System.out.println("cash tickets: ok");
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
                    if(parts.length != 6) {
                        System.out.println("Format error.");
                        return;
                    }
                        String name = parts[2].replace("\"", "");
                        String dni = parts[3];
                        String email = parts[4];
                        String cashId = parts[5];

                        Client newClient = new Client(name, dni, email, cashId);
                        if (listClient.addClient(newClient)) {
                            System.out.println(newClient);
                            System.out.println("client add: ok");
                            System.out.println();
                        }

                }
                case "remove" -> {
                    String dni = parts[2];
                    if(listClient.removeClient(dni)) {
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
