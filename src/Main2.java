import java.time.LocalDate;
import java.util.*;

public class Main2 {
    public static void main(String[] args) {
        benchmarkTM();
    }
    public static void benchmarkLHM(){
        StorageLHM storage = new StorageLHM();
        Scanner input = new Scanner(System.in);
        System.out.print("Enter amount of items: ");
        int num = input.nextInt();
        double time = 0;
        Random random = new Random();
        StringBuilder itemName;
        while (storage.Stock.size() < num) {
            itemName = new StringBuilder();
            while (itemName.length() < random.nextInt(4, 10)) {
                itemName.append((char) random.nextInt(26) + 'a');
            }
            double prevTime = System.nanoTime();
            storage.initializeItem(String.valueOf(itemName));
            time = System.nanoTime() - prevTime;
        }
        System.out.println(time + "ns");
        Object[] keys = storage.Stock.keySet().toArray();
        for (int i = 0; i < num; i++) {
            double prevTime = System.nanoTime();
            String item = keys[random.nextInt(keys.length)].toString();
            int amt = random.nextInt(5, 10);
            storage.addRequest(item, amt, "Kenneth", new Date());
            time = System.nanoTime() - prevTime;
        }
        System.out.println(time + "ns");

        LinkedList<String> requests = storage.getRequests();
        double prevTime = System.nanoTime();
        storage.approveRequest(requests.get(random.nextInt(requests.size())).split(" ")[0]);
        time = System.nanoTime() - prevTime;
        System.out.println(time + "ns");
        for(int i = 1; i < num - 1; i++) {
            storage.approveRequest(requests.get(random.nextInt(requests.size())).split(" ")[0]);
        }
    }
    public static void benchmarkTM(){
        StorageTM storage = new StorageTM();
        Scanner input = new Scanner(System.in);
        System.out.print("Enter amount of items: ");
        int num = input.nextInt();
        double time = 0;
        Random random = new Random();
        StringBuilder itemName;
        while (storage.Stock.size() < num) {
            itemName = new StringBuilder();
            while (itemName.length() < random.nextInt(4, 10)) {
                itemName.append((char) random.nextInt(26)).append("a");
            }
            double prevTime = System.nanoTime();
            storage.initializeItem(String.valueOf(itemName));
            time = System.nanoTime() - prevTime;
        }
        System.out.println(time + "ns");
        Object[] keys = storage.Stock.keySet().toArray();
        for (int i = 0; i < num; i++) {
            double prevTime = System.nanoTime();
            String item = keys[random.nextInt(keys.length)].toString();
            int amt = random.nextInt(5, 10);
            storage.addRequest(item, amt, "Kenneth", new Date());
            time = System.nanoTime() - prevTime;
        }
        System.out.println(time + "ns");

        LinkedList<String> requests = storage.getRequests();
        double prevTime = System.nanoTime();
        storage.approveRequest(requests.get(random.nextInt(requests.size())).split(" ")[0]);
        time = System.nanoTime() - prevTime;
        System.out.println(time + "ns");
        for(int i = 1; i < num - 1; i++) {
            storage.approveRequest(requests.get(random.nextInt(requests.size())).split(" ")[0]);
        }
    }
    public static void benchmarkLL(){
        StorageLL storage = new StorageLL();
        Scanner input = new Scanner(System.in);
        System.out.print("Enter amount of items: ");
        int num = input.nextInt();
        double time = 0;
        Random random = new Random();
        StringBuilder itemName;
        while (storage.Stock.size() < num) {
            itemName = new StringBuilder();
            while (itemName.length() < random.nextInt(4, 10)) {
                itemName.append((char) random.nextInt(26)).append("a");
            }
            double prevTime = System.nanoTime();
            storage.initializeItem(String.valueOf(itemName));
            time = System.nanoTime() - prevTime;
        }
        System.out.println(time + "ns");
        LinkedList<String> keys = new LinkedList<>();
        for (Item item: storage.Stock){
            keys.add(item.name);
        }
        for (int i = 0; i < num; i++) {
            double prevTime = System.nanoTime();
            String item = keys.get(random.nextInt(keys.size()));
            int amt = random.nextInt(5, 10);
            storage.addRequest(item, amt, "Kenneth", new Date());
            time = System.nanoTime() - prevTime;
        }
        System.out.println(time + "ns");

        LinkedList<String> requests = storage.getRequests();
        double prevTime = System.nanoTime();
        storage.approveRequest(requests.get(random.nextInt(requests.size())).split(" ")[0]);
        time = System.nanoTime() - prevTime;
        System.out.println(time + "ns");
        for(int i = 1; i < num - 1; i++) {
            storage.approveRequest(requests.get(random.nextInt(requests.size())).split(" ")[0]);
        }
    }
}
