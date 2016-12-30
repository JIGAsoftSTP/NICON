/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.Scanner;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 *
 * @author Servidor
 */
public class DUFile 
{
//    private static int somaTotal;
//    /**
//     * Essa funcao serve para escrever em um fiecheiro os dados contidos no array list
//     * @param listData
//     * @param dirFile
//     * @param continueWriter
//     * @return 
//     */
//    public static boolean exportDataTxt(ArrayList<String> listData, String dirFile, boolean continueWriter)
//    {
//        try
//        {
//            try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File(dirFile), continueWriter)))
//            {
//                listData.stream().forEach((s) -> pw.println(s));
//                pw.close();
//                return true;
//            }catch(Exception ex1)
//            {
//                return false;
//            }
//        }catch(Exception ex) 
//        {
//            return false;
//        }
//    }
//
//    public static boolean exportDataPDF(ArrayList<String> listData, String dirFile)
//    {
//        try 
//        {
//            com.itextpdf.text.Font fTitle= new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 16, com.itextpdf.text.Font.BOLD);
//            com.itextpdf.text.Font fTableBody= new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 11, com.itextpdf.text.Font.NORMAL);
//            com.itextpdf.text.Font fTableHeader= new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 9, com.itextpdf.text.Font.BOLD);
//            String name = new File(dirFile).getName();
//            File f= new File(dirFile+".pdf");
//            
//            FileOutputStream fos = new FileOutputStream( f);
//            com.itextpdf.text.Document documento = new com.itextpdf.text.Document(com.itextpdf.text.PageSize.A4.rotate());
//            
//            com.itextpdf.text.pdf.PdfPTable pTableSpace = new com.itextpdf.text.pdf.PdfPTable(1);
//            com.itextpdf.text.pdf.PdfPCell cellSpace= new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Phrase(" ",fTableBody));
//            cellSpace.setBorder(0);
//            pTableSpace.addCell(cellSpace);
//            
//            com.itextpdf.text.pdf.PdfPTable pTableTitle = new com.itextpdf.text.pdf.PdfPTable(1);
//            pTableTitle.setWidthPercentage(100);
//            com.itextpdf.text.pdf.PdfPCell cellTitulo= new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Phrase(name,fTitle ));
//            cellTitulo.setBorder(0);
//            pTableTitle.addCell(cellTitulo);
//            
//            
//            ArrayList<String []> listDataCase = new ArrayList<>();
//            listData.stream().forEach((s) -> {
//                listDataCase.add(s.split(";"));
//            });
//            
//            float [] percentage = createPerncetage(listDataCase);
//            
//            com.itextpdf.text.pdf.PdfPTable pTablePrincial = new com.itextpdf.text.pdf.PdfPTable(percentage);
//            pTablePrincial.setWidthPercentage(100);
//            com.itextpdf.text.pdf.PdfPCell cellPrincipal;
//            boolean frist = true;
//            for (String s[]: listDataCase)
//            {
//                for(String ss: s)
//                {
//                    DUTable.Alignment dbUtilAligmn = DUTable.getAlignmentOrder(ss);
//                    
//                    int pdfAligmn = (dbUtilAligmn == DUTable.Alignment.CENTER)? pdfAligmn = com.itextpdf.text.Element.ALIGN_CENTER
//                            :(dbUtilAligmn == DUTable.Alignment.RIGHT)? com.itextpdf.text.Element.ALIGN_RIGHT
//                            :com.itextpdf.text.Element.ALIGN_LEFT;
//                            
//                    if(frist)
//                    {
//                        cellPrincipal = new com.itextpdf.text.pdf.PdfPCell( new com.itextpdf.text.Phrase(ss, fTableHeader));
//                        cellPrincipal.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//                        pTablePrincial.addCell(cellPrincipal);
//                    }
//                    else
//                    {
//                        cellPrincipal = new com.itextpdf.text.pdf.PdfPCell( new com.itextpdf.text.Phrase( ss, fTableBody));
//                        cellPrincipal.setHorizontalAlignment(pdfAligmn);
//                        pTablePrincial.addCell(cellPrincipal);
//                    }
//                 }
//                frist = false;
//                
//            }
//            com.itextpdf.text.pdf.PdfWriter.getInstance(documento, fos);
//            
//            com.itextpdf.text.pdf.PdfPTable pTableDate = new com.itextpdf.text.pdf.PdfPTable(1);
//            pTableDate.setWidthPercentage(100);
//            com.itextpdf.text.pdf.PdfPCell cellData = new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Phrase("10 Janeiro 2015",fTableBody));
//            cellData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
//            cellData.setBorder(0);
//            pTableDate.addCell(cellData);
//            
//            documento.setMargins(20f, 20f, 35f, 5f);
//            documento.open();
//            documento.add(pTableTitle);
//            documento.add(pTableSpace);
//            documento.add(pTableSpace);
//            documento.add(pTablePrincial);
//            documento.add(pTableSpace);
//            documento.add(pTableDate);
//            documento.close();
//            
//            return true;
//        } catch (FileNotFoundException | com.itextpdf.text.DocumentException ex) {
//            Logger.getLogger(DUFile.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }
//    
//    public static float[] createPerncetage (ArrayList<String []> lista)
//    {
//        if(lista == null || lista.isEmpty()) return  null;
//        float [] perncetages = new float[lista.get(0).length];
//        int [] lenthMax = new int[lista.size()];
//        DUFile.somaTotal = 0;
//        
//        lista.stream().forEach((linha) -> 
//        {
//            int soma =0;
//            for(int i =0; i<linha.length; i++)
//                if(lenthMax[i] < linha[i].length()) 
//                {
//                    soma = soma - lenthMax[i];
//                    lenthMax[i] = linha[i].length();
//                    DUFile.somaTotal = somaTotal + lenthMax[i];
//                }
//        });
//        for(int i = 0; i<perncetages.length; i++)
//            perncetages[i] = (float)((float)lenthMax[i] * 100) / DUFile.somaTotal;
//        DUFile.somaTotal = 0;
//        
//        return  perncetages;
//    }
//
//    /*
//     10, 5, 7, 11, 40, 90; 163
//     
//     163  --- 100%
//    10  --  pec [i]
//     pec [i] =  (10 * 100 ) /163
//    
//      
//    */
//
//    public static ArrayList<String> loadData(String configbin)
//    {
//        try
//        {
//            File file = new File(configbin);
//            Scanner sc = new Scanner(file);
//            ArrayList<String> listData = new ArrayList<>();
//            while (sc.hasNextLine())
//                listData.add(sc.nextLine());
//            sc.close();
//            return  listData;
//            
//        }catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        return null;
//    }
    
}
