import java.util.concurrent.Future;

public class ShopApplication {

    public static void main(String[] args) {
        Shop shop = new Shop();

        //Start
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("nike");
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("호출이 리턴되는 시간: " + invocationTime + "msecs");

        doSomethingElse();
        try {
            double price = futurePrice.get(); //가격 정보가 있으면 Future에서 가격 정보를 읽고, 없으면 받을 때까지 블록한다.
            System.out.println("price: " + price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("가격이 리턴되는 시간: " + retrievalTime + "msecs");
    }

    private static void doSomethingElse() {
        System.out.println("제품의 가격을 계산하는 동안 다른 작업을 수행중...");
    }
}
