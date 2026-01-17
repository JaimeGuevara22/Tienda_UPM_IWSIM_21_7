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

public class App {

    private static final String prompt="tUPM> ";

    private static ProductCatalog catalog = new ProductCatalog();
    private static abstractTicket ticket;

    private static cashController listCash = new cashController();

    private static ClientController listClient = new ClientController();
    private static List<abstractTicket> listTicket = new ArrayList<abstractTicket>();

    public static void main(String[] args) {
        String ticketId = null;
        String cashId = null;
        Scanner sc = null;
        boolean continuar = true;
        ticket = new Ticket(ticketId, cashId);
        listCash = new cashController();

        // Inicialización (lectura de ficheros, etc.)
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
                if (input.isEmpty()) {
                    continue;
                }

                try {
                    if (input.equalsIgnoreCase("help")) {
                        help();
                    } else if (input.startsWith("echo")) {
                        echo(input);
                    } else if (input.equalsIgnoreCase("exit")) {
                        System.out.println("Closing application.");
                        continuar = false;
                    } else if (input.startsWith("prod")) {
                        prodCommand(input);
                    } else if (input.startsWith("ticket")) {
                        ticketCommand(input);
                    } else if (input.startsWith("cash")) {
                        cashCommand(input);
                    } else if (input.startsWith("client")) {
                        clientCommand(input);
                    } else {
                        System.out.println("Unknown command. Type 'help' for a list of commands.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Input error: " + e.getMessage());
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Argument error: missing or insufficient arguments.");
                } catch (java.time.format.DateTimeParseException e) {
                    System.out.println("Date format error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Unexpected error (" + e.getClass().getSimpleName() + "): " + e.getMessage());
                    e.printStackTrace(System.out);
                }
            }

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
                        "  ticket add <ticketId> <cashId> <prodId> <amount> [--p<txt> --p<txt>]\n" +
                        "  ticket remove <ticketId> <cashId> <prodId>\n" +
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
    private static void echo(String input){
        String text = input.substring(5).trim();
        if (text.startsWith("\"") && text.endsWith("\"")) {
            text = text.substring(1, text.length() - 1);
        }
        System.out.println(text);
    }
    private static void prodCommand(String input) {
        String[] parts = input.trim().split(" ");
        try {
            String subcommand = parts[1];

            switch (subcommand.toLowerCase()) {
                case "add" -> {
                    boolean servicio = true;
                    Productos product = null;
                    Service service = null;
                    try {
                        LocalDate fechaServicio = null;
                        // Id siempre es la segunda palabra
                        try {
                                fechaServicio = LocalDate.parse(parts[2]);
                            } catch (Exception e) {
                                servicio = false;
                            }
                            if (!servicio) {
                            int id = Integer.parseInt(parts[2]);
                            int primeraComilla = input.indexOf('"');
                            int ultimaComilla = input.lastIndexOf('"');
                            if (primeraComilla == -1 || ultimaComilla == -1 || ultimaComilla == primeraComilla) {
                                System.out.println("Fail: invalid name format");
                                break;
                            }
                            String name = input.substring(primeraComilla + 1, ultimaComilla);

                            String afterName = input.substring(ultimaComilla + 1).trim();
                            String[] tail = afterName.split(" ");
                            
                            if (tail.length < 2) {
                                System.out.println("Fail: invalid parameters");
                                break;
                            }

                            String categoria = tail[0];
                            double price = Double.parseDouble(tail[1]);
                            if (tail.length == 3) {
                                int maxPersonal = Integer.parseInt(tail[2]);
                                product = new ProductosPersonalizables(id, name, price, Category.valueOf(categoria), maxPersonal);
                            } else {
                                product = new Product(id, name, price, Category.valueOf(categoria));
                            }
                            } else {
                                ServiceType tipoServicio;
                                switch (parts[3].toUpperCase()) {
                                    case "TRANSPORT":
                                        tipoServicio = ServiceType.TRANSPORT;
                                        break;
                                    case "SHOW":
                                        tipoServicio = ServiceType.SHOW;
                                        break;
                                    case "INSURANCE":
                                        tipoServicio = ServiceType.INSURANCE;
                                        break;
                                    default:
                                        throw new Exception("Servicio no existente");
                                }
                                service = new Service(fechaServicio, tipoServicio);
                            }
                            if (!servicio) {
                                if (catalog.addProduct(product)) {
                                    System.out.println(product.toString());
                                    System.out.println("prod add: ok\n");
                                } else {
                                    System.out.println("Fail: product not added\n");
                                }
                            } else {
                                if (servicio) {
                                    System.out.println("Servicio");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Fail: Product not added\n");
                    }
                }
                case "addfood" -> {
                    int id;
                    try {
                        id = Integer.parseInt(parts[2]);
                        int primerasComillas = input.indexOf('"');
                        int ultimasComillas = input.lastIndexOf('"');
                        if(primerasComillas == -1 || ultimasComillas == primerasComillas){
                            System.out.println("Nombre no válido");
                            return;
                        }
                        String name = input.substring(primerasComillas + 1, ultimasComillas);//intervalo del nombre
                        String [] resto = input.substring(ultimasComillas + 1).trim().split(" ");
                        if(resto.length != 3){
                            System.out.println("Parámetros no válidos");
                            return;
                        }
                        double price = Double.parseDouble(resto[0]);
                        LocalDate date = LocalDate.parse(resto[1]);
                        int max_people = Integer.parseInt(resto[2]);
                        Productos food = new Food(date, max_people, price, id, name);
                        if (catalog.addProduct(food)) {
                            System.out.println(food);
                            System.out.println("prod addFood: ok");
                        } else {
                            System.out.println("Error product not added");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error processing ->prod addFood ->Error adding product");

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
                    Productos removed = catalog.getProductById(id);
                    if (catalog.remove(id)) {
                        System.out.println(removed.toString());
                        System.out.println("prod remove: ok");
                    } else {
                        System.out.println("Fail: product not removed");
                    }
                    System.out.println();
                }
                case "addmeeting" -> {
                    int id;
                    try {
                        id = Integer.parseInt(parts[2]);
                        int primerasComillas = input.indexOf('"');
                        int ultimasComillas = input.lastIndexOf('"');
                        if(primerasComillas == -1 || ultimasComillas == primerasComillas){
                            System.out.println("Nombre no válido");
                            return;
                        }
                        String name = input.substring(primerasComillas + 1, ultimasComillas);//intervalo del nombre
                        String [] resto = input.substring(ultimasComillas + 1).trim().split(" ");
                        if(resto.length != 3){
                            System.out.println("Parámetros no válidos");
                            return;
                        }
                        double price = Double.parseDouble(resto[0]);
                        LocalDate date = LocalDate.parse(resto[1]);
                        int max_people = Integer.parseInt(resto[2]);
                        Productos meetings = new Meetings(date, max_people, price, id, name);
                        if (catalog.addProduct(meetings)) {
                            System.out.println(meetings);
                            System.out.println("prod addMeeting: ok");
                        } else {
                            System.out.println("Error product not added");
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
        }catch (NumberFormatException e){
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
                        Productos item = catalog.getProductById(Integer.parseInt(itemId));
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
                        if (item instanceof Meetings m) {
                            double precioUnitario = m.getPrecio();
                                double totalPrice = precioUnitario * cantidad;
                                System.out.println("{Class:Meeting, id: " + m.getId() + ", name: " + m.getNombre() + ", price: " + totalPrice +
                                        ", date of event: " + m.getMeetingsExpirationDate() +
                                        ", max people allowed: " + m.getNumParticipants() +
                                        ", actual people in event: " + cantidad + "}");
                        }else if(item instanceof Food f) {
                            double precioUnitario = f.getPrecio();
                            double totalPrice = precioUnitario * cantidad;
                            System.out.println("{Class:Meeting, id: " + f.getId() + ", name: " + f.getNombre() + ", price: " + totalPrice +
                                    ", date of event: " + f.getFoodExpirationDate() +
                                    ", max people allowed: " + f.getNumParticipants() +
                                    ", actual people in event: " + cantidad + "}");
                        }else{
                                ticket.printTicket();
                        }
                        System.out.println("ticket add: ok\n");

                    } catch (Exception e) {
                        System.out.println("Ticket add: Error - invalid parameters");
                    }
                }

                case "remove" -> {
                    int prodId = Integer.parseInt(parts[parts.length - 1]);
                    Productos removed = catalog.getProductById(prodId);
                    if (ticket.removeItem(prodId)) {
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
                    char opcion = 'p';
                    if (parts[parts.length - 1].startsWith("-")) {
                        opcion = parts[parts.length - 1].charAt(1);
                    }
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
                    abstractTicket t;
                    if (client.esEmpresa()) {
                        ticketPrinter printer = null;

                        switch (opcion) {
                            case 's' -> printer = new printerSoloServicios();
                            case 'c' -> printer = new printerCombinado();
                            default -> {
                                System.out.println("Empresa debe usar -s o -c");
                                printer = new printerCombinado(); // Default printer
                            }
                        }

                        t = new ticketEmpresa(id, cashId, printer);
                    } else {
                        t = new Ticket(id, cashId);
                    }
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
                    return;
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
                    List<abstractTicket> copia = new ArrayList<>(listTicket);
                    copia.sort(Comparator.comparing(abstractTicket::getTicketId));
                    for(abstractTicket t : copia){
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
                    return;
                }
                case "remove" -> {
                    String cashId = parts[2];
                    if(listCash.cashRemove(cashId)) {
                        System.out.println("cash remove: ok");
                    } else {
                        System.out.println("Error: the cash wasn't found in the list.");
                    }
                    System.out.println();
                    return;
                }
                case "list" -> {
                    System.out.println("Cash: ");
                    listCash.list();
                    System.out.println("cash list: ok");
                    System.out.println();
                    return;
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
                    List<abstractTicket> ticketsCash = cash.getTickets();
                    ticketsCash.sort(Comparator.comparing(abstractTicket::getTicketId)); //para ordenar por id
                    System.out.println("Tickets:");
                    for(abstractTicket t : ticketsCash){
                        System.out.println(t.getTicketId()+"->"+t.getState());
                    }
                    System.out.println("cash tickets: ok");
                    return;
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
                        return;
                }
                case "remove" -> {
                    String dni = parts[2];
                    if(listClient.removeClient(dni)) {
                        System.out.println("client remove: ok");
                    } else {
                        System.out.println("Error: the client wasn't found in the list.");
                    }
                    System.out.println();
                    return;
                }
                case "list" -> {
                    listClient.list();
                    System.out.println("client list: ok");
                    System.out.println();
                    return;
                }


            }


        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println();
        }

    }

}
