package Conta;


import conexao.Call;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import sessao.SessionUtil;

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
Vector<Conta.conta> Newconta=new Vector<Conta.conta>();

   public void readingSheet(String caminho_arquivo_xls) 
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
                int cont=0;
                for (int row = 0; row < linhas; row++) {
                    //cria meu objeto que recebe as linhas
                    String linha=null;
                    String coluna=null;
                    //verifica se coluna 0 (A) e linha row não é vazia
                    if(!sheet.getCell(0,row).getContents().isEmpty()){
                        //recupera informação da coluna A linha row.
                        linha=sheet.getCell(0, row).getContents();
                    }
                    if(!sheet.getCell(1,row).getContents().isEmpty()){
                        //recupera informação da coluna B linha row.
                        coluna=sheet.getCell(1, row).getContents();
                    }
                    //chama meu objeto que faz o serviço de salvar no banco de dados
                    if(validar(linha)==false){
                        //valida o numero da conta se tem uma conta Raíz
                        //se napo tiver conta raíz, então nao lhe é permitido registrar a nova conta
                        Newconta.addElement(new conta(row, linha, coluna, getNivel(linha), getSuperId(linha,0), getClasse(linha), String.valueOf(getSuperId(linha,1))));
                    }else{
//                        mostra a msg de confirmação de cotinuar com registros ou não
                        if(JOptionPane.showConfirmDialog(null, "A conta "+linha+"Não cumpre os requisitos\n Continuar Com o Registro?","Erro No carregamento dos Dados",0)>0)
                            {
                                break;
                            }
                                }
            }

        } catch (BiffException | IndexOutOfBoundsException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (IOException ex) {
        Logger.getLogger(lerEescrever.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
            //fechar
            if (workbook != null)
                workbook.close();
            System.out.println("Conta"+"\t"+
                                   "Descrição"+"\t"+
                                   "Classe"+"\t"+
                                   "Nivel"+"\t"+
                                   "Super Conta");
            for (int i=0;i<Newconta.size()-1;i++)
            {
   
                System.out.println(Newconta.elementAt(i).getContaId()+"\t"+
                                   Newconta.elementAt(i).getContaDescricao()+"\t"+
                                   "«"+Newconta.elementAt(i).getClasse()+"»"+"\t"+
                                   Newconta.elementAt(i).getNivel()+"\t"+
                                   "("+Newconta.elementAt(i).getSuperConta()+")"
                        );
            }
        }
}
    /**
     * Ler do ficheiro excel com dados das contas e registrar na base dde dados
     * @param caminho_ficheiro
     * @return 
     */
    public String Load_Reg_conta(String caminho_ficheiro)
        {
            readingSheet(caminho_ficheiro);
           for(int a=0;a<Newconta.size()-1;a++)
            {
                regAccount(Newconta.elementAt(a).getContaId(),Newconta.elementAt(a).getContaDescricao(),Newconta.elementAt(a).getSuperId(), (String) SessionUtil.getUserlogado().getId());
            }
            return "";
        }
    
    public String getRegNewSuper(int ID,String conta,String Desc,String Super, int Classe, String SuperNma )
        {
            Newconta.addElement(new conta(ID, conta, "Default", 0, Super, Classe, conta));
            return "";
        }
    
    /**
     * retorna o nível da conta a ser criada
     * @param Conta
     * @return 
     */
    public int getNivel(String Conta) 
        {
        return Conta.length()-1;
    }
    
    /**
     *retor a classe da conta a ser criada
     * @param Conta
     * @return 
     */
    public int getClasse(String Conta) 
        {
        return Integer.parseInt(String.valueOf(Conta.charAt(0)));
    }
    
    /**
     * retorna o id da conta raíz
     * @param Conta
     * @param p
     * @return 
     */
    public String getSuperId(String Conta,int p)
        {
            for (int i=0; i<Newconta.size();i++)
            {
                if(Newconta.elementAt(i).getContaId().length()<Conta.length()&&Newconta.elementAt(i).getContaId().equals(Conta.substring(0, Conta.length()-1)))
                    {
                        if(p==0)
                            return String.valueOf(Newconta.elementAt(i).getId());
                        else
                            return Newconta.elementAt(i).getContaId();
                    }            
            }
            return null;
        }
    /**
     * registra na base de dados as contas contabilisticas
     * @param conta nº da conta
     * @param Descricao a descrição da conta
     * @param IdSuper o id da conta raíz
     * @param user o funcionário responsável
     * @return retorna 
     */
    public Object regAccount(String conta, String Descricao,String IdSuper,String user)
        {
        String sql="FUNC_REG_ACCOUNT";
         return Call.callSampleFunction(sql, 1, conta,Descricao,IdSuper,user);
    }
    
    boolean validar(String Conta)
    {
        return getSuperId(Conta, 0)==null&& Conta.length()>1;
    }
}
