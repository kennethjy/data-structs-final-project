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
            System.out.println(time);
        }
        Object[] keys = storage.Stock.keySet().toArray();
        System.out.print("Enter amount of items: ");
        num = input.nextInt();
        for (int i = 0; i < num; i++) {
            double prevTime = System.currentTimeMillis();
            storage.addRequest(keys[random.nextInt(keys.length)].toString(), random.nextInt(5, 10), "Kenneth", new Date());
            time = System.currentTimeMillis() - prevTime;
            System.out.println(time + "ms");
        }
    }
}
