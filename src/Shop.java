import java.util.Random;

public class Shop {

    private static Random random = new Random();

    /**
     * 제품명에 해당하는 가격을 반환
     *
     * @param product the product name
     * @return double price
     */
    public double getPrice(String product) {
        return calculatePrice(product);
    }

    /**
     * 실제로 상점 DB등 외부 서비스에 접근해서 가격 정보를 얻는 로직 대신 delay()
     *
     * @param product the product name
     * @return random double price
     */
    private double calculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public static void delay() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
