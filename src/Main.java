import com.sun.source.tree.Tree;

import java.time.LocalDate;
import java.util.*;

public class Main {
    StorageLHM storage = new StorageLHM();
    public void main(String[] args) {

        //
    }

    public void requestItemEnter(){
        Scanner in = new Scanner(System.in);
        String[] keys = storage.Stock.keySet().toArray(new String[0]);
        int i = 1;
        for (String key: keys){
            System.out.println(i + " " + storage.Stock.get(key));
            i++;
        }
        String input;
        System.out.println("Enter the index of the item to enter");
        input = in.next();
        int keyNum;
        try {
            System.out.println("Input entered is invalid");
            keyNum = Integer.parseInt(input);
        } catch (Exception e) {
            return;
        }
        System.out.println("Enter the number of items to enter");
        input = in.next();
        int amt;
        try {
            amt = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("Input entered is invalid");
            return;
        }
        System.out.println("Enter your name");
        String name = in.next();
        storage.addRequest(keys[keyNum], amt, name, new Date());
        System.out.println("Request Successfully added");
    }
    public void requestItemTake(){
        Scanner in = new Scanner(System.in);
        String[] keys = storage.Stock.keySet().toArray(new String[0]);
        int i = 1;
        for (String key: keys){
            System.out.println(i + " " + storage.Stock.get(key));
            i++;
        }
        String input;
        System.out.println("Enter the index of the item to retrieve");
        input = in.next();
        int keyNum;
        try {
            keyNum = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("Input entered is invalid");
            return;
        }
        System.out.println("Enter the number of items to retrieve");
        input = in.next();
        int amt;
        try {
            amt = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("Input entered is invalid");
            return;
        }
        System.out.println("Enter your name");
        String name = in.next();
        if(storage.removeRequest(keys[keyNum], amt, name, new Date())) {
            System.out.println("Request Successfully added");
        } else {
            System.out.println("Not enough items in stock");
        }
    }
    public void reviewRequest(){
        LinkedList<String> requests = storage.getRequests();
        for (int i = 0; i <= requests.toArray().length; i++){
            System.out.println((i+1) + " " + requests.get(i));
        }
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the index of the request to approve/reject");
        String input = in.next();
        int keyNum;
        try {
            keyNum = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("Input entered is invalid");
            return;
        }
        System.out.println("Would you like to [A]pprove or [R]eject this request?");
        input = in.next().charAt(0) + "";
        String id = requests.get(keyNum).split(" ")[0];
        if (input.equalsIgnoreCase("A")){
            storage.approveRequest(id);
        } else {
            storage.rejectRequest(id);
        }
    }
}

class StorageLHM{
    LinkedHashMap<String, Request> onHold = new LinkedHashMap<>();
    LinkedHashMap<String, Request> archive = new LinkedHashMap<>();
    LinkedHashMap<String, Item> Stock = new LinkedHashMap<>();

    public void addRequest(String itemName, int amount, String requester, Date date){
        if (Stock.containsKey(itemName)){
            Calendar tempCalendar = new GregorianCalendar();
            String ID = requester + tempCalendar.get(Calendar.DATE);
            String tempID = ID;
            int i = 1;
            while (onHold.containsKey(ID)){
                ID = tempID + "" + i;
                i++;
            }
            Request request = new Request(ID, itemName, true, amount, requester, date);
            onHold.put(ID, request);
            if (amount < 0){
                rejectRequest(ID);
            }
        }
    }

    public boolean removeRequest(String itemName, int amount, String requester, Date date){
        if (Stock.containsKey(itemName)){
            if (Stock.get(itemName).checkCurrentStock(amount)) {
                Calendar tempCalendar = new GregorianCalendar();
                String ID = requester + tempCalendar.get(Calendar.DATE);
                String tempID = ID;
                int i = 1;
                while (onHold.containsKey(ID)){
                    ID = tempID + "" + i;
                }
                Request request = new Request(ID, itemName, false, amount, requester, date);
                onHold.put(ID, request);
                if (amount < 0){
                    rejectRequest(ID);
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public LinkedList<String> getRequests(){
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
            archive.put(ID, onHold.remove(ID));
        }
    }

    public boolean initializeItem(String name){
        if (Stock.containsKey(name)){
            return false;
        }
        Stock.put(name, new Item(name));
        return true;
    }
}

class StorageLL{
    LinkedList<Request> onHold = new LinkedList<>();
    LinkedList<Request> archive = new LinkedList<>();
    LinkedList<Item> Stock = new LinkedList<>();

    public void addRequest(String itemName, int amount, String requester, Date date){
        Item item = containsStockType(itemName);
        if (item != null){
            Calendar tempCalendar = new GregorianCalendar();
            String ID = requester + tempCalendar.get(Calendar.DATE);
            String tempID = ID;
            int i = 1;
            while (containsRequest(ID)){
                ID = tempID + "" + i;
                i++;
            }
            Request request = new Request(ID, itemName, true, amount, requester, date);
            onHold.add(request);
            if (amount < 0){
                rejectRequest(ID);
            }
        }
    }

    public boolean removeRequest(String itemName, int amount, String requester, Date date){
        Item item = containsStockType(itemName);
        if (item != null){
            if (item.checkCurrentStock(amount)) {
                Calendar tempCalendar = new GregorianCalendar();
                String ID = requester + tempCalendar.get(Calendar.DATE);
                String tempID = ID;
                int i = 1;
                while (containsRequest(ID)){
                    ID = tempID + "" + i;
                    i++;
                }
                Request request = new Request(ID, itemName, false, amount, requester, date);
                onHold.add(request);
                if (amount < 0){
                    rejectRequest(ID);
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public boolean containsRequest(String id){
        for (Request request: onHold){
            if(Objects.equals(request.requestID, id)){
                return true;
            }
        }
        return false;
    }

    public Item containsStockType(String name){
        for (Item item: Stock){
            if(Objects.equals(item.name, name)){
                return item;
            }
        }
        return null;
    }

    public LinkedList<String> getRequests(){
        LinkedList<String> requests = new LinkedList<>();
        for (Request request: onHold){
            String id = request.requestID;
            requests.add(id + " " + request);
        }
        return requests;
    }

    public boolean approveRequest(String ID){
        for (Request request: onHold) {
            if (request.requestID.equals(ID)) {
                request.setApproval(Stock);
                onHold.remove(request);
                archive.add(request);
                return true;
            }
        }
        return false;
    }
    public void rejectRequest(String ID){
        for (Request request: onHold) {
            if (request.requestID.equals(ID)) {
                onHold.remove(request);
                archive.add(request);
            }
        }
    }

    public boolean initializeItem(String name){
        if (containsStockType(name) != null){
            return false;
        }
        Stock.add(new Item(name));
        return true;
    }
}

class StorageTM{
    TreeMap<String, Request> onHold = new TreeMap<>();
    TreeMap<String, Request> archive = new TreeMap<>();
    TreeMap<String, Item> Stock = new TreeMap<>();

    public void addRequest(String itemName, int amount, String requester, Date date){
        if (Stock.containsKey(itemName)){
            Calendar tempCalendar = new GregorianCalendar();
            String ID = requester + tempCalendar.get(Calendar.DATE);
            String tempID = ID;
            int i = 1;
            while (onHold.containsKey(ID)){
                ID = tempID + "" + i;
                i++;
            }
            Request request = new Request(ID, itemName, true, amount, requester, date);
            onHold.put(ID, request);
            if (amount < 0){
                rejectRequest(ID);
            }
        }
    }

    public boolean removeRequest(String itemName, int amount, String requester, Date date){
        if (Stock.containsKey(itemName)){
            if (Stock.get(itemName).checkCurrentStock(amount)) {
                Calendar tempCalendar = new GregorianCalendar();
                String ID = requester + tempCalendar.get(Calendar.DATE);
                String tempID = ID;
                int i = 1;
                while (onHold.containsKey(ID)){
                    ID = tempID + "" + i;
                }
                Request request = new Request(ID, itemName, false, amount, requester, date);
                onHold.put(ID, request);
                if (amount < 0){
                    rejectRequest(ID);
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public LinkedList<String> getRequests(){
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
            archive.put(ID, onHold.remove(ID));
        }
    }

    public boolean initializeItem(String name){
        if (Stock.containsKey(name)){
            return false;
        }
        Stock.put(name, new Item(name));
        return true;
    }
}
class Request{
    String requestID;
    String itemID;
    boolean isPutIn;
    int amount;
    String requester;
    Date date;
    private boolean approval = false;

    Request(String requestID, String itemID, boolean isPutIn, int amount, String requester, Date date){
        this.itemID = itemID;
        this.isPutIn = isPutIn;
        this.amount = amount;
        this.requester = requester;
        this.date = date;
        this.requestID = requestID;
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
    public void setApproval(TreeMap<String, Item> Stock){
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
    public void setApproval(LinkedList<Item> Stock){
        for (Item item: Stock){
            if (Objects.equals(item.name, itemID)){
                if (isPutIn) {
                    item.add(amount);
                } else {
                    item.remove(amount);
                }
                approval = true;
            }
        }
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