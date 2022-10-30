/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BasicJava;

import java.awt.image.BufferedImage;

/**
 *
 * @author ASUS
 */
public class Basic {
   int x = 20;
   // Fungsi Declaration
   public static void fungsi(){
        System.out.println("This is Function");
    }
   
    public static void main(String[] args) {
        // Pemanggilan fungsi
        fungsi();
        BufferedImage bimg = null;
//                 int merahg = (rgb >> 16) & 0xff;;
        
        // Cara membuat object
        Basic Obj = new Basic();
        System.out.println(bimg);
    }
}
