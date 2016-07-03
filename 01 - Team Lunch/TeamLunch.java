/**
 * 
 * Tuenti Challenge 6
 * Challenge 1 - Team Lunch
 * @author Enrique Carrillo (@ecarrillo88)
 *
 */

import java.util.Scanner;


public class TeamLunch {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int limit = scanner.nextInt();
        for (int i = 1; i <= limit; i++) {
            int diners = scanner.nextInt();
            int tables = numberOfTables(diners);
            System.out.printf("Case #%d: %d\n", i, tables);
        }
    }
    
    private static int numberOfTables(int diners) {
        if (diners == 0) return 0;
        if (diners <= 4) return 1;
        
        if ((diners % 2) == 0) {
            return (diners / 2) - 1;
        } else {
            return diners / 2;
        }
    }

}
