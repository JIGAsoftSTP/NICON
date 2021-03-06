/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

/**
 *
 * @author Jaime Alcides
 */
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;



import javax.print.PrintService;

public class PrintPdf implements Serializable{

    private static double margin;
    private static double getWidth;
    private static double getHeight;
    private PrinterJob pjob = null;
    private int orientacao;
    private PrintService impressora = null;

    public PrintPdf(String directorio, String fixeiro, double margem, double width, double heigt,String nomeImpressora, int OrientacaoPapel) throws IOException, PrinterException {
        PrintPdf.margin = margem;
        PrintPdf.getWidth = width;
        PrintPdf.getHeight = heigt;
        orientacao=OrientacaoPapel;
        FileInputStream inputStream = new FileInputStream(directorio);
        
        byte[] pdfContent = new byte[inputStream.available()];
        inputStream.read( pdfContent, 0, inputStream.available());
        initialize(pdfContent, fixeiro, nomeImpressora);
    }

    public PrintPdf(byte[] content, String jobName,String nomeImpressora) throws IOException, PrinterException {
        initialize(content, jobName, nomeImpressora);
    }

    private void initialize(byte[] pdfContent, String jobName,String nomeImpressora) throws IOException, PrinterException {
        ByteBuffer bb = ByteBuffer.wrap(pdfContent);

        PDFFile pdfFile = new PDFFile(bb);
        PDFPrintPage pages = new PDFPrintPage(pdfFile);

        PrintService[] pservices = PrinterJob.lookupPrintServices();

        System.out.println(pservices.length);
        if (pservices.length > 0) {
            for (PrintService ps : pservices) {
                System.out.println("Impressora Encontrada: " + ps.getName());

                if (ps.getName().contains(nomeImpressora)) {
                    System.out.println("Impressora Selecionada: " + nomeImpressora);
                    impressora = ps;
                    break;
                }
            }
        }
        if (impressora != null) {
            pjob = PrinterJob.getPrinterJob();
            pjob.setPrintService(impressora);

            PageFormat pf = PrinterJob.getPrinterJob().defaultPage();

            pjob.setJobName(jobName);
            Book book = new Book();
            book.append(pages, pf, pdfFile.getNumPages());
            pjob.setPageable(book);

          
            Paper paper = new Paper();
            paper.setSize(getWidth, getHeight);
            paper.setImageableArea(margin, (int) margin / 4, getWidth - margin * 2, getHeight - margin * 2);
            //paper.setImageableArea(margin,margin, paper.getWidth()-margin*2,paper.getHeight()-margin*2);
            pf.setOrientation(orientacao);
            pf.setPaper(paper);
        }
    }

    public Boolean print() throws PrinterException {
        if (impressora != null) {
            System.out.println("IMPRIMINDO....");
            pjob.print();
            System.out.println("IMPRIMIU");
        }
        return null;
    }
}

class PDFPrintPage implements Printable {

    private final PDFFile file;

    PDFPrintPage(PDFFile file) {
        this.file = file;
    }

    @Override
    public int print(Graphics g, PageFormat format, int index) throws PrinterException {
        int pagenum = index + 1;
        if ((pagenum >= 1) && (pagenum <= file.getNumPages())) {
            Graphics2D g2 = (Graphics2D) g;

            PDFPage page = file.getPage(pagenum);

            Rectangle imageArea = new Rectangle((int) format.getImageableX(), (int) format.getImageableY(),
                    (int) format.getImageableWidth() , (int) format.getImageableHeight());
            g2.translate(0, 0);
            PDFRenderer pgs = new PDFRenderer(page, g2, imageArea, null, null);
            try {

                page.waitForFinish();

                pgs.run();
            } catch (InterruptedException ie) {
                System.out.println(ie.toString());
            }
            return PAGE_EXISTS;
        } else {
            return NO_SUCH_PAGE;
        }
    }
}
