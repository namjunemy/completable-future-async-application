import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ShopApplication {

    private final Executor executor =
        // 상점 수만큼의 스레드를 갖는 풀을 생성한다. 범위는 0~100
        Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);  //프로그램 종료를 방해하지 않는 데몬 스레드를 사용한다.
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
        System.out.println(shopApplication.findPrices("myphoneX"));
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);

        System.out.println("수행 시간: " + invocationTime + "msecs");

    }

    public List<String> findPrices(String product) {
        List<CompletableFuture<String>> priceFutures =
            shops.stream()
                .map(shop -> CompletableFuture
                    .supplyAsync(() -> shop.getName() + "price is " + shop.calculatePrice(product),
                        executor))
                .collect(toList());

        return priceFutures.stream()
            .map(CompletableFuture::join)
            .collect(toList());
    }
}
