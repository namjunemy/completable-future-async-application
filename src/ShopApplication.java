import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

public class ShopApplication {

    private List<Shop> shops = Arrays.asList(
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
        return shops.stream()
            .map(shop -> String
                .format("%s price is %.2f", shop.getName(), shop.calculatePrice(product)))
            .collect(toList());
    }
}
