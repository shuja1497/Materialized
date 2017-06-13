package shuja1497.bitsandpizzasmaterial;

/**
 * Created by shujareshi on 13/6/17.
 */

public class Pizza  {

    private String name;
    private int imageResourceId;

    public static final Pizza[] pizzas = {

            new Pizza("Diavolo", R.drawable.diavolo),
            new Pizza("Funghi", R.drawable.funghi)
    };

    public Pizza(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }
    public int getImageResourceId() {
        return imageResourceId;
    }
}
