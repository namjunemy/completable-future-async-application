import java.util.Random;

public class Shop {

    private static Random random = new Random();

    private String name;

    public Shop(String name) {
        this.name = name;
    }

    /**
     * 제품명에 해당하는 가격 반환
     *
     * @param product the product name
     * @return double price
     */
    public String getPrice(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    /**
     * 실제로 상점 DB등 외부 서비스에 접근해서 가격 정보를 얻는 로직 대신 randomDelay()
     *
     * @param product the product name
     * @return random double price
     */
    public double calculatePrice(String product) {
        randomDelay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public static void randomDelay() {
        int delay = 500 + random.nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }
}
