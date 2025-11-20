package es.upm.etsisi.poo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class cashController {
    private List<Cash> cashes = new ArrayList<>();

    public boolean addCash(Cash cash){
        for(Cash c : cashes){
            if(c.getCashId().equals(cash.getCashId())){
                return false;
            }
        }
        cashes.add(cash);
        return true;
    }
    public boolean cashRemove(String cashId){
        for(Cash c : cashes){
            if(c.getCashId().equals(cashId)){
                cashes.remove(c);
                return true;
            }
        }
        return false;
    }
    public void list(){
        Collections.sort(cashes, (c1, c2) -> c1.getNombre().compareToIgnoreCase(c2.getNombre()));
        for(Cash c : cashes){
            System.out.println(c.toString());
        }
    }
    public Cash findCashById(String id) {
        for (Cash c : cashes) {
            if (c != null && c.getCashId().equals(id)) {
                return c;
            }
        }
        return null;
    }
}
