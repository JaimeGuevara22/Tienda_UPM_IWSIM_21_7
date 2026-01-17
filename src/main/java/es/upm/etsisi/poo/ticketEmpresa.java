package es.upm.etsisi.poo;

public class ticketEmpresa extends abstractTicket{
    private ticketPrinter printer;
    private Service[] services;
    private int contadorServicios;
    public ticketEmpresa(String ticketId, String cashId,ticketPrinter printer) {
        super(ticketId, cashId);
        this.printer = printer;
        this.services = new Service[100];
        this.contadorServicios = 0;
    }
    public boolean addService(Service service) {
        if(contadorServicios < 100){
            services[contadorServicios++] = service;
            return true;
        }else{
            return false;
        }
    }
    @Override
    public void printTicket() {
        printer.print(this);
    }
    @Override
    public boolean addItem(TicketItem nuevo) {
        if(contadorServicios == 0){
            return false;
        }
        Productos newProd = nuevo.getItem();
        if(!(newProd instanceof  Productos)){
            return false;
        }
        items[contador++] = nuevo;
        return true;
    }
    @Override
    public void close() {
        boolean hayServicios = contadorServicios > 0;
        boolean hayProductos = contador > 0;
        if (hayServicios && !hayProductos && contadorServicios > 0 && contador > 0) {
            throw new IllegalStateException("Ticket combinado inválido");
        }
        if(!hayServicios && hayProductos){
            throw new IllegalStateException("Ticket empresa no puede tener solo productos");
        }
        super.close();
    }
    public Service getService(int index) {
        if (index < 0 || index >= contadorServicios) {
            throw new IndexOutOfBoundsException();
        }
        return services[index];
    }
    public int getContadorServicios(){
        return contadorServicios;
    }
    @Override
    public int getItemsCount(){
        return contador;
    }
    public TicketItem getItem(int i) {
        return items[i];
    }
    @Override
    public boolean removeItem(int id) {
        return false;
    }
}
