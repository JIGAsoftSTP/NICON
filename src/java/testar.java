
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import lib.Moeda;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ildo
 */
public class testar {
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//              Date d = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        String ano = dateFormat.format(d);
//        String anoAtual = ano.substring(6, 10);
//        System.out.println(anoAtual);

        //A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3 false 8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92
        //A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3 false 8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92
//        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
//        byte messageDigest[] = algorithm.digest("123".getBytes("UTF-8"));
//
//        StringBuilder hexString = new StringBuilder();
//        for (byte b : messageDigest) {
//          hexString.append(String.format("%02X", 0xFF & b));
//        }
//        String senha = hexString.toString();
//
//        MessageDigest algorithm1 = MessageDigest.getInstance("SHA-256");
//        byte messageDigest1[] = algorithm1.digest("123456".getBytes("UTF-8"));
//
//        StringBuilder hexString1 = new StringBuilder();
//        for (byte b : messageDigest1) {
//          hexString1.append(String.format("%02X", 0xFF & b));
//        }
//        String senha1 = hexString1.toString();
//        
//         
//        //System.err.println("7EE0DEEE0B55AC51E0B9640AF8BC49167B82B635B6E9E7038DE0ED042BDB96E5");
//        System.err.println(senha+" "+senha.equals(senha1)+" "+senha1);

//        System.err.println(ConfigDoc.Fontes.FONTB);
//        File f = new File(ConfigDoc.Fontes.FONTB);
//        System.err.println(f.getAbsolutePath());
        
//        double numero= 100.0890;
//        
//        DecimalFormat formato = new DecimalFormat("#.##"); 
//        
//        numero = Double.valueOf((formato.format(numero).replace(',', '.')));
//        System.err.println(numero);
//        
//        System.err.println("12-12-2015".substring(3, "12-12-2015".length()));
//        String[] codCliente = "2016TIN0385/0385".split("/");
//        System.err.println(codCliente.length+""+Arrays.toString(codCliente));
//        System.out.println(((codCliente.length == 2) ? "TIN "+codCliente[1] : "TIN "));
        
//        new ContratoDao().carregarUmContracto(381);
//        System.err.println(NumberFormat.getInstance(new Locale("fr")).format(333333331)+testar.addZerro(31));
        
//        ArrayList<Object[]> namesl = new ArrayList<>();
//        Object[] names;
//        names =new Object[]{"ferreira","fffhfh88fhfh","ffhfhfh999hf","ahmed"};
//        namesl.add(names);
//        names = new Object[]{"afonso","fffhfh88fhfh","ffhfhfh999hf","jorge"};
//        namesl.add(names);
//        names = new Object[]{"jorge","fffhfh88fhfh","ffhfhfh999hf","afonso"};
//        namesl.add(names);
//        names = new Object[]{"ahmed","fffhfh88fhfh","ffhfhfh999hf","ferreira"};
//        namesl.add(names);
//        
//        System.err.println("Antes");
//        for (int i = 0; i < namesl.size(); i++) {
//            Object[] get = namesl.get(i);
//            System.err.println(Arrays.toString(get));
//        }
//        System.err.println("------------------------------");
//        System.err.println("------------------------------");
//        System.err.println("------------------------------");
//        System.err.println("Depois");
//        removeItem = new int[]{};
//        namesl = removeParamm(namesl);
//        for (int i = 0; i < namesl.size(); i++) {
//            Object[] get = namesl.get(i);
//            System.err.println(Arrays.toString(get));
//        }
        
        System.err.println(Moeda.format(455.4));
    }
    
//    private static String addZerro(double  valor)
//    {
//        int de = (int)Math.round((valor - (int)valor) * 100);
//        System.out.println(de);
////        System.err.println(de+" "+(de%10)+"\n\n\n");
//        return ((de == 0) ? ",00": (de >= 10 && (de%10) != 0 || de < 9) ? "": "0");
//    }
//    
    public static int[] removeItem;
    public static ArrayList<Object[]>removeParamm(ArrayList<Object[]> al)
    {
        boolean b;
        Object[] newList;
        ArrayList<Object[]> newArrayList = new ArrayList<>();
        for (Object[] list : al) {
            int h = 0;
            newList = new Object[al.get(0).length-removeItem.length]; 
            for (int ii = 0; ii < list.length; ii++) {
                b =true;
                for (int j = 0; j < removeItem.length; j++) {
                   if( ii == removeItem[j] )
                   { b = false; break;}
                }
                if(b) 
                { newList[h]=list[ii]; h++; }
            }
            newArrayList.add(newList);
        }
        return newArrayList;
    }
}
