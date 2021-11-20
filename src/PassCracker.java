import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;

public class PassCracker {

    private static String Hashify2(String s) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(s.getBytes(StandardCharsets.UTF_8));
        byte[] encoded = Base64.getEncoder().encode(hash);
        return(new String(encoded));
    }

    private static String returnPasswordAndSaltStringHash(String s){
        String[] arr = s.split(":", 3);
        return arr[2];

    }
    private static String returnSaltStringHash(String s){
        String [] arr = s.split(":", 3);
        return arr[1];
    }

    private static String returnUserName(String s){
        String [] arr = s.split(":", 3);
        return arr[0];
    }

    public static void main(String [] args) throws NoSuchAlgorithmException, IOException {

        Scanner scannerInputs = new Scanner(new File("src/inputs"), StandardCharsets.UTF_8);
        int matches=0;
        int passwords = 0;

        while (scannerInputs.hasNextLine()){
         String inputString = scannerInputs.nextLine();
         passwords++;
         //Scanner scannerCommons = new Scanner(new File("src/10mill.txt"));
            BufferedReader reader = new BufferedReader(new FileReader("src/realhuman_phill.txt"));
            String line;

         //while (scannerCommons.hasNextLine()){
            while ((line = reader.readLine()) != null){
              //String commonString = scannerCommons.nextLine();
                String commonString = line;
              String inputPasswordAndSalt = returnPasswordAndSaltStringHash(inputString);

              String salt = returnSaltStringHash(inputString);

              String combine = salt+commonString;
             String hashedCommonandSalt = Hashify2(combine);


              if(inputPasswordAndSalt.equals(hashedCommonandSalt)){
                  System.out.println("Match: " + returnUserName(inputString) + "::" + commonString);
                  matches++;
              }
          }
        }

        System.out.println("Number of password cracked: "+ matches + " out of " + passwords);
    }
}
