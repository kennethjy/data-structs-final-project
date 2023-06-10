import java.time.LocalDate;
import java.util.*;

public class Main {
    Storage storage = new Storage();
    public void main(String[] args) {

        //
    }
}

class Storage{
    LinkedHashMap<String, Request> onHold = new LinkedHashMap<>();
    LinkedHashMap<String, Request> archive = new LinkedHashMap<>();
    LinkedHashMap<String, Item> Stock = new LinkedHashMap<>();

    public void addRequest(String itemName, int amount, String requester, Date date){
        for (String id: Stock.keySet()){
            if (Stock.get(id).name.equals(itemName)){
                Request request = new Request(id, true, amount, requester, date);
                Calendar tempCalendar = new GregorianCalendar();
                String ID = requester + tempCalendar.get(Calendar.DATE);
                onHold.put(ID, request);
                if (amount < 0){
                    rejectRequest(ID);
                }
                return;
            }
        }
    }

    public boolean removeRequest(String itemName, int amount, String requester, Date date){
        for (String id: Stock.keySet()){
            if (Stock.get(id).name.equals(itemName)){
                if (Stock.get(id).checkCurrentStock(amount)) {
                    Request request = new Request(id, false, amount, requester, date);
                    Calendar tempCalendar = new GregorianCalendar();
                    String ID = requester + tempCalendar.get(Calendar.DATE);
                    onHold.put(ID, request);
                    if (amount < 0){
                        rejectRequest(ID);
                        return false;
                    }
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public LinkedList getRequests(){
        LinkedList<String> requests = new LinkedList<>();
        for (String id: onHold.keySet()){
            requests.add(id + " " + onHold.get(id).toString());
        }
        return requests;
    }

    public boolean approveRequest(String ID){
        if (!onHold.containsKey(ID)){
            return false;
        }
        onHold.get(ID).setApproval(Stock);
        archive.put(ID, onHold.remove(ID));
        return true;
    }
    public void rejectRequest(String ID){
        if (onHold.containsKey(ID)) {
            onHold.get(ID).setApproval(Stock);
            archive.put(ID, onHold.remove(ID));
        }
    }

    public boolean initializeItem(String name){
        for (String id: Stock.keySet()){
            if (Stock.get(id).name.equals(name)) {
                return false;
            }
        }
        Stock.put((Stock.values().size()) + "" + name.substring(0, 3), new Item(name));
        return true;
    }
}

class Request{
    String itemID;
    boolean isPutIn;
    int amount;
    String requester;
    Date date;
    private boolean approval = false;

    Request(String itemID, boolean isPutIn, int amount, String requester, Date date){
        this.itemID = itemID;
        this.isPutIn = isPutIn;
        this.amount = amount;
        this.requester = requester;
        this.date = date;
    }

    public void setApproval(LinkedHashMap<String, Item> Stock){
        Item item = Stock.get(itemID);
        if (item == null){
            return;
        }
        if (isPutIn) {
            item.add(amount);
        } else {
            item.remove(amount);
        }
        approval = true;
    }

    public boolean getApproval(){
        return approval;
    }

    public String toString(){
        return ((isPutIn) ? "add" : "take") + " " + itemID + " " + amount + "x by " + requester +
                " at " + date.toString() + " " + ((approval) ? "approved" : "not approved");
    }
}

class Item{
    String name;
    int currentStock = 0;

    Item(String name){
        this.name = name;
    }
    public void add(int amount){
        currentStock += amount;
    }
    public void remove(int amount){
        currentStock -= amount;
    }

    public boolean checkCurrentStock(int amount){
        return amount <= currentStock;
    }
}