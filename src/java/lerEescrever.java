


import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JOptionPane;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ILDO
 */
public class lerEescrever {

    Vector<Conta.conta> conta=new Vector<Conta.conta>();
    
    public static void readingSheet(String caminho_arquivo_xls) 
    {
        //objeto relativo ao arquivo excel
        Workbook workbook = null;
        try {
            //Carrega planilha
            WorkbookSettings config = new WorkbookSettings();
            config.setEncoding("Cp1252");//configura acentuação
            workbook = Workbook.getWorkbook(new File(caminho_arquivo_xls),config);//recupera arquivo desejado
            //recupera pagina/planilha/aba do arquivo
            Sheet sheet = workbook.getSheet(0);
            //recupera numero de linhas
            int linhas = sheet.getRows();
            //percorre todas as linhas da planilha
            for (int row = 0; row < linhas; row++) {
                //cria meu objeto que recebe as linhas
              
                //verifica se coluna 0 (A) e linha row não é vazia
                if(!sheet.getCell(1,row).getContents().isEmpty()){
                    //recupera informação da coluna A linha row.
                    JOptionPane.showMessageDialog(null,"Conta: "+sheet.getCell(1, row).getContents());
                }
                if(!sheet.getCell(2,row).getContents().isEmpty()){
                    //recupera informação da coluna B linha row.
                     JOptionPane.showMessageDialog(null,"Description: "+sheet.getCell(2, row).getContents());
                }
                //chama meu objeto que faz o serviço de salvar no banco de dados
            }

        } catch (IOException | BiffException e) {
//            print_erro(e);
        }
//            print_erro(e);
         catch (NumberFormatException e) {
//            print_erro(e);
        } catch(IndexOutOfBoundsException | HeadlessException e){
//            print_erro(e);
        } finally {
            //fechar
            if (workbook != null)
                workbook.close();
        }
}
    
    public String Reg_conta(){
        return "";
    }
    
    public static void main(String [] args)
        {
            readingSheet("C:/Users/ILDO/Documents/data-mining/Livro1.xlsx");
        }
}
