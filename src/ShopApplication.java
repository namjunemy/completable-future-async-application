public class ShopApplication {

    public static void main(String[] args) {
        Shop shop = new Shop();

        //Start
        long start = System.nanoTime();

        double price = shop.getPrice("nike");

        long invocationTime = ((System.nanoTime() - start) / 1_000_000);

        System.out.println("수행시간: " + invocationTime);
        System.out.println("price: " + price);
    }
}
