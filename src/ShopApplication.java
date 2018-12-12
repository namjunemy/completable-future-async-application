import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Stream;

public class ShopApplication {

    private final Executor executor =
        Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });

    private static List<Shop> shops = Arrays.asList(
        new Shop("apple"),
        new Shop("samsung"),
        new Shop("nokia"),
        new Shop("blackberry")
    );

    public static void main(String[] args) {
        ShopApplication shopApplication = new ShopApplication();

        long start = System.nanoTime();

        CompletableFuture[] futures = shopApplication.findPricesStream("myPhone")
            .map(f -> f.thenAccept(
                s -> System.out.println(s + " (done in " +
                    ((System.nanoTime() - start) / 1_000_000) + " mecs)")))
            .toArray(size -> new CompletableFuture[size]);

        CompletableFuture.allOf(futures).join();

        System.out.println("All shops have now responded in " +
            ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }

    public Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
            //각 상점에서 할인전 가격을 비동기적으로 얻는다. return Stream<CompletableFuture<String>>
            .map(shop -> CompletableFuture.supplyAsync(
                () -> shop.getPrice(product), executor))

            // 상점에서 반환한 문자열을 Quote 객체로 변환한다. return Stream<CompletableFuture<Quote>>
            .map(future -> future.thenApply(Quote::parse))

            //결과 Future를 다른 비동기 작업과 조합해서 할인 코드를 적용한다. return Stream<CompletableFuture<String>>
            .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(
                () -> Discount.applyDiscount(quote), executor)));
    }
}
