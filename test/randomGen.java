
import java.util.Random;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jaideep
 */
public class randomGen {
    
    private static int genRandomNumber(int min, int max){
      
        Random rand = new Random();
        int otp = rand.nextInt(max-min) + min;
        return otp;
    }
    public static void main(String[] args) {
        
        System.out.println("randomGen.main() : " + genRandomNumber( 1000 ,9999));
    }
}
