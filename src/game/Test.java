package game;

import java.util.Arrays;
import java.util.Comparator;

public abstract class Test {
    public static void main(String[] args) {
        String s = "a4, b1, c2, d0";
        String[] arr = s.split(",");
        java.util.List<String> list = Arrays.asList(arr);
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int i1 = Integer.parseInt(o1.trim().substring(1));
                int i2 = Integer.parseInt(o2.trim().substring(1));
                return i1 - i2;
            }
        });
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).trim());
        }
    }
}
