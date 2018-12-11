import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop {

    private static Random random = new Random();

    /**
     * 제품명에 해당하는 가격을 비동기로 반환
     *
     * @param product the product name
     * @return double price
     */
    public Future<Double> getPriceAsync(String product) {
        //계산 결과를 포함할 CompletableFuture를 생성
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();

        new Thread(() -> {
            double price = calculatePrice(product); //다른 스레드에서 비동기적으로 계산을 수행
            futurePrice.complete(price);            //오랜 시간이 걸리는 계산이 완료되면 Future에 값을 설정
        }).start();

        return futurePrice; //계산 결과가 완료되길 기다리지 않고 Future를 반환한다.
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
