import java.time.LocalDate;
import java.util.*;

public class Main2 {
    public static void main(String[] args) {
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
                itemName.append((char) random.nextInt(26)).append("a");
            }
            double prevTime = System.nanoTime();
            storage.initializeItem(String.valueOf(itemName));
            time = System.nanoTime() - prevTime;
            System.out.println(time + "ns");
        }
        Object[] keys = storage.Stock.keySet().toArray();
        System.out.print("Enter amount of requests: ");
        num = input.nextInt();
        for (int i = 0; i < num; i++) {
            double prevTime = System.nanoTime();
            String item = keys[random.nextInt(keys.length)].toString();
            int amt = random.nextInt(5, 10);
            storage.addRequest(item, amt, "Kenneth", new Date());
            time = System.nanoTime() - prevTime;
            System.out.println(time + "ns");
        }

        LinkedList<String> requests = storage.getRequests();
        System.out.print("Enter amount of requests to approve (max " + num + "): ");
        int n = input.nextInt();
        if (n > num){
            num = random.nextInt(num);
        } else {
            num = n;
        }
        for(int i = 0; i < num; i++) {
            double prevTime = System.nanoTime();
            storage.approveRequest(requests.get(random.nextInt(requests.size())).split(" ")[0]);
            time = System.nanoTime() - prevTime;
            System.out.println(time + "ns");
        }
    }
}
