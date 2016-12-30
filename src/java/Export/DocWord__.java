/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

//import modelo.Funcionario;

import java.io.Serializable;

//import bean.ViagemBean;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.Serializable;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.faces.context.FacesContext;
//import javax.servlet.http.HttpServletResponse;
//import modelo.Viagem;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.poi.xwpf.usermodel.XWPFRun;
//import org.apache.poi.xwpf.usermodel.XWPFTable;
//import org.apache.poi.xwpf.usermodel.XWPFTableCell;
//import org.apache.poi.xwpf.usermodel.XWPFTableRow;
//import sessao.SessionUtil;
/**
 *
 * @author AhmedJorge
 */
public class DocWord__ implements Serializable{
    
//    public void ViagemDoc(ViagemBean vb,String numCriente)
//    {
//        XWPFDocument document= new XWPFDocument();
//        int total = vb.getInfoPessoaSegurada().size();
//        
//        for (int i = 0; i < total; i++) 
//        {
//            XWPFTable table = document.createTable();
//            table.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE, 0, 0, "FFFFFF");
//            table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 0, 0, "FFFFFF");
//            table.setCellMargins(0, 0, 0, 0);
//           
//            XWPFTableRow run0= table.getRow(0);
//            XWPFTableCell cellNome=run0.getCell(0);
//            cellNome.setText("NOME DO SEGURADO: "+vb.getInfoPessoaSegurada().get(i).getNomePessoaSegurada());
//
//            XWPFTableRow run1= table.createRow();
//            XWPFTableCell cellEndereco=run1.getCell(0);
//            cellEndereco.setText("Endereço: "+vb.getInfoPessoaSegurada().get(i).getEndereco());
//
//            XWPFTableRow run2= table.createRow();
//            XWPFTableCell cellDataNac=run2.getCell(0);
//            cellDataNac.setText("Data e Local de Nascimento: "+vb.getInfoPessoaSegurada().get(i).getLocalNascimento());
//
//            XWPFTableCell cellSexoTel=run2.createCell();
//            cellSexoTel.setText("Sexo: "+vb.getInfoPessoaSegurada().get(i).getSexo()+" Tel. "+vb.getInfoPessoaSegurada().get(i).getTelefone());
//
//            XWPFTableRow run3= table.createRow();
//            XWPFTableCell cellApolice=run3.getCell(0);
//            cellApolice.setText("Apólice nº: "+vb.getViagem().getNumApolice());
//
//            XWPFTableCell cellNumCliente=run3.createCell();
//            cellNumCliente.setText("Cliente nº: "+numCriente);
//
//            XWPFTableRow run4= table.createRow();
//            XWPFTableCell cellDuracao=run4.getCell(0);
//            
//            cellDuracao.setText("Durante: "+vb.getClausula().getContrato().getDias()+" Dias"); 
//            
//            XWPFTableCell cellPriodo=run4.createCell();
//            cellPriodo.setText("Período: De "+vb.getViagem().getDatatInicioFormatada()+" a "+vb.getInfoPessoaSegurada().get(i).getDataFimFormatada());
//
//            XWPFTableRow run5= table.createRow();
//            XWPFTableCell cellDataEm=run5.getCell(0);
//            SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-YYY");
//            cellDataEm.setText("Data de Emissão: "+sdf.format(new Date()));
//
//            XWPFTableCell Distino=run5.createCell();
//            Distino.setText("Destino: "+vb.getViagem().getPaisDestino());
//
//            XWPFTableRow run6= table.createRow();
//            XWPFTableCell celMeioIdentifi=run6.getCell(0);
//            celMeioIdentifi.setText("Meios de Identificação: "+vb.getInfoPessoaSegurada().get(i).getTipoDoc() +" "+vb.getInfoPessoaSegurada().get(i).getNumDoc());
//
//            XWPFTableCell EnitidoEm =run6.createCell();
//            EnitidoEm.setText("Emitido em "+vb.getInfoPessoaSegurada().get(i).getLocalEmissao()+" em "+vb.getInfoPessoaSegurada().get(i).getDataEmissaoFormatada());
//                        
//            if(i+1!=total)
//            {
//                XWPFParagraph run7= document.createParagraph();
//                XWPFRun celVazio=run7.createRun();
//                celVazio.setText(" ");
//            }
//        }
//        
//        try
//        {
//            SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy HH.mm.ss");
//            String data= sdf.format(new Date());
//            try (FileOutputStream outputDoc = new FileOutputStream(((Funcionario)SessionUtil.obterValor("utilizador")).getNomeAcesso()+"- Viagem Doc "+data+".docx")) {
//                document.write(outputDoc);
//            }
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//    public static void main(String[] args) {
//       DocWord docWord= new DocWord();
//       ArrayList<Viagem> vs= new ArrayList<>();
//       Viagem v= new Viagem();
//       vs.add(v);
//       
//       v= new Viagem();
//       vs.add(v);
//       
//       docWord.ViagemDoc(new ViagemBean(), "fgfggfgf dfg");
//            File f=new File("BD.txt");
//            System.out.println(f.getAbsolutePath());
//        System.err.println("file:\\\\\\");
//    }
//    public void save(String nameFile)
//    {
//        try {
//            FacesContext facesContext = FacesContext.getCurrentInstance();
//            
//            // Get HTTP response
//            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
//            
//            // Set response headers
//            response.reset();   // Reset the response in the first place
//            response.setHeader("Content-Type", "application/docx");  // Set only the content type
//            
//            // Open response output stream
//            OutputStream responseOutputStream = response.getOutputStream();
//            
//            // Read PDF contents
//            //        URL url = new File(PDF_URL).toURL();
//            File f= new File(nameFile);
//            System.err.println(f.getAbsolutePath());
//            URL url = new URL(f.getAbsolutePath());
//            InputStream pdfInputStream = url.openStream();
//
//            // Read PDF contents and write them to the output
//            byte[] bytesBuffer = new byte[2048];
//            int bytesRead;
//            while ((bytesRead = pdfInputStream.read(bytesBuffer)) > 0) {
//                responseOutputStream.write(bytesBuffer, 0, bytesRead);
//            }
//
//            // Make sure that everything is out
//            responseOutputStream.flush();
//
//            // Close both streams
//            pdfInputStream.close();
//            responseOutputStream.close();
//
//            // JSF doc:
//            // Signal the JavaServer Faces implementation that the HTTP response for this request has already been generated
//            // (such as an HTTP redirect), and that the request processing lifecycle should be terminated
//            // as soon as the current phase is completed.
//            facesContext.responseComplete();
//                    } catch (MalformedURLException ex) {
//                        Logger.getLogger(DocWord.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (IOException ex) {
//                        Logger.getLogger(DocWord.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//   }       
}
