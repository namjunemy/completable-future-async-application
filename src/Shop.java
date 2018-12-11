import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop {

    private static Random random = new Random();

    private String name;

    public Shop(String name) {
        this.name = name;
    }

    /**
     * 제품명에 해당하는 가격을 비동기로 반환
     *
     * @param product the product name
     * @return double price
     */
    public Future<Double> getPriceAsync(String product) {
        /**
         * 반환하는 CompletableFuture는 직접 만들고 completeExceptionally() 처리한 CompletableFuture와 같다.
         * 즉, 둘 다 같은 방법으로 에러를 관리한다.
         */
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    /**
     * 실제로 상점 DB등 외부 서비스에 접근해서 가격 정보를 얻는 로직 대신 delay()
     *
     * @param product the product name
     * @return random double price
     */
    public double calculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public String getName() {
        return name;
    }
}
