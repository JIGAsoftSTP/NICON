/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Export;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmedjorge
 */
public class MarcaDAgua {
    public static boolean isSimulation = false;
    public static boolean isCanceled = false;
    public static class SimulacaoVertical extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                PdfContentByte canvas = writer.getDirectContentUnder();
                Image image = Image.getInstance(ConfigDoc.Fontes.getDiretorio()+"/Simulacao.png");
                image.setAbsolutePosition(0, 0);
                image.scaleAbsolute(document.getPageSize());
                canvas.saveState();
                PdfGState state = new PdfGState();
                state.setFillOpacity(0.1f);
                canvas.setGState(state);
                canvas.addImage(image);
                canvas.restoreState();
            } catch (BadElementException | IOException ex) {
                Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static class AnulacaoVertical extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                PdfContentByte canvas = writer.getDirectContentUnder();
                Image image = Image.getInstance(ConfigDoc.Fontes.getDiretorio()+"/Anulado.png");
                image.setAbsolutePosition(0, 0);
                image.scaleAbsolute(document.getPageSize());
                canvas.saveState();
                PdfGState state = new PdfGState();
                state.setFillOpacity(0.1f);
                canvas.setGState(state);
                canvas.addImage(image);
                canvas.restoreState();
            } catch (BadElementException | IOException ex) {
                Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static class SimulacaoHorizontal extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                PdfContentByte canvas = writer.getDirectContentUnder();
                Image image = Image.getInstance(ConfigDoc.Fontes.getDiretorio()+"/Simulacao.png");
                image.setAbsolutePosition(0, 0);
                image.scaleAbsolute(document.getPageSize());
                canvas.saveState();
                PdfGState state = new PdfGState();
                state.setFillOpacity(0.1f);
                canvas.setGState(state);
                canvas.addImage(image);
                canvas.restoreState();
            } catch (BadElementException | IOException ex) {
                Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(ReciboPagamento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
