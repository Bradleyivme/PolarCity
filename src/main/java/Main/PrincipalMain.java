
package Main;
import Inicio.Login;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class PrincipalMain {
    public static void main(String[] args) {
        Login is = new Login();
        is.setVisible(true);
        //consumoapi();
    }
    
//     public static void consumoapi(){
//        try{
//        URL url= new URL ("https://polarcity-app.herokuapp.com/usuarios");
//        HttpURLConnection conn =(HttpURLConnection)url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.connect();
//        int response = conn.getResponseCode();
//        if(response != 200){
//            JOptionPane.showMessageDialog(null,"Fallo en la conexion con BD");
//            throw new RuntimeException ("Hay un error"+ response);
//            
//        }else{
//            JOptionPane.showMessageDialog(null,"Conexion correcta con BD");
//            StringBuilder infor = new StringBuilder();
//            Scanner scan= new  Scanner(url.openStream());
//            while(scan.hasNext()){
//                infor.append(scan.nextLine());
//            
//            }
//            scan.close();
//            System.out.println(infor);
//            
//            
//            
//        }
//        
//        
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//     }
}
