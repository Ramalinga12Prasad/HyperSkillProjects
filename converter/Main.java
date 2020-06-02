package converter;

import java.util.Scanner;

public class Main {
    public static int fromBaseOne(String n) {
        int num = Integer.parseInt(n);
        int count = 0;
        while (num > 0) {
            count++;
            num/=10;
        }
        return count;
    }
    
    public static String toBaseOne(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append("1");            
        }
        return sb.toString();
    }
    
    public static int fromBase(String num, int radix) {
        if(radix == 1) {
            return fromBaseOne(num);
        }
        return Integer.parseInt(num, radix);
    }
    
    public static String toBase(int num, int radix) {
        if(radix < 1 || radix > 36) {
            throw new NumberFormatException();
        }
        if(radix == 1) 
        return toBaseOne(num);
        return Integer.toString(num, radix);
    }
    
    
    public static void conversion(String num, int fromRadix, int toRadix) {
        String[] value = num.split("\\.");
        try {
            int n = fromBase(value[0], fromRadix) ;
            System.out.print(toBase(n, toRadix));
            if(num.contains(".")) {
                System.out.println("." + convertFraction(value[1], fromRadix, toRadix));
            }
        } catch (NumberFormatException e) {
            System.out.println("some value is mismatched error");
        }
        
    }

    public static String convertFraction(String num, int fromRadix, int toRadix) {
        float sum = 0.0f;
        for (int i = 0; i < num.length(); i++) {
            int n  = fromBase(num.substring(i, i + 1), fromRadix);
            double power =Math.pow(fromRadix, i+1);
            sum += n / power;

        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sum *= toRadix;
            sb.append(toBase( (int)sum, toRadix ));
            sum = sum % 1;
        }
        return sb.toString();


    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try {
        int fromRadix = in.nextInt();
        String num = in.next();
        int toRadix = in.nextInt();
        conversion(num, fromRadix, toRadix);
        } catch (Exception e) {
            System.out.println("input error");
        }
        // convertFraction(num, fromRadix, toRadix);

    }
}
