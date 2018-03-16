import java.util.Scanner;

public class MathFunction {
    public double start(long x,long y) {
        double result;
        result = (1+Math.sqrt(Math.sin(x+y)))/(2+Math.abs((2*x)/(1+Math.sqrt(x)*Math.sqrt(y))))+x;
        return result;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        long x,y;
        try {
            System.out.println("Input x\n");
            x = scanner.nextInt();
            System.out.println("Input y\n");
            y = scanner.nextInt();
        }catch (Exception e) {
            System.out.println("Incorrect data");
            return;
        }
        System.out.println(new MathFunction().start(x,y));
    }
}
