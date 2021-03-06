package Traitement_des_produits_frais;
// Importing required classes

import Traitement_Des_articles.Item;
import Traitement_des_factures.Payable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.floor;
//freshitems that are still fresh
public class FreshItem extends Item  {
    private String bestBeforeDate;//expirationdate
    //constructor with arguments
    public FreshItem(String name, long price, int weight ,String bestBeforeDate ) {
        super(name, price, weight);
        SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-DD");
        try
        {
            Date javaDate = sdfrmt.parse(bestBeforeDate);
            this.bestBeforeDate=bestBeforeDate;
        }
        /* Date format is invalid */
        catch (ParseException e)
        {
            System.out.println(bestBeforeDate+" is Invalid Date format");
        }
    }
    //override the tax thing
    @Override
    public long taxRatePerTenThousand() {
       if(super.getWeight()<1000)
           //weight <1kg means => 10%
           return 1000;
       else
           //created a algorithm based on the question
           // we take off 0.1 each time
           //1<weight<2 grammes means => 10% - 0.1%
           //2<weight<3 grammes means => 10% - 0.2%
           //3<weight<4 grammes means => 10% - 0.3%
           //4<weight<5 grammes means => 10% - 0.4%
           //etc
       // 1500 => 10-0.1=9.9*100=990
           return (long) ((10-(0.1*(floor(super.getWeight()/1000))))*100);
    }
//getter of the date
    public String getBestBeforeDate() { return bestBeforeDate; }
    //displaying fresh product stuff
    public String toString() {
        //we use super since the attribute is private and we are inheriting from the the base class to the daughter class
            System.out.println("BBD :"+bestBeforeDate+" "+super.toString());
        return "";
    }
    //testing main function
    public static void main(String[] args){
        Item item1 = new Item("corn flakes", 500, 1000);
        System.out.println(item1);     // affiche: corn flakes: 5.00MAD
        FreshItem fresh = new FreshItem("Salmon", 1450, 800, "2012-04-11");
        System.out.println(fresh);     // affiche: BBD:2012-04-11 Salmon: 14.50MAD
    }
}
