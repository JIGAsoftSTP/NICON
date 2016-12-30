package Export;

import dao.EmpresaDao;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import lib.Moeda;

/**
 *
 * @author AhmedJorge
 */
public class ConfigDoc implements Serializable {

    public static class Fontes implements Serializable {
//        public final static String FONTB = localFont()+"segoeuib.ttf";
//        public final static String FONT = localFont()+"segoeui.ttf";
//        public final static String FONTI = localFont()+"segoeuii.ttf";
//        public final static String FONTBI = localFont()+"segoeuiz.ttf";

        public final static String FONTB = localFont() + "verdanab.ttf";
        public final static String FONT = localFont() + "verdana.ttf";
        public final static String FONTI = localFont() + "verdanai.ttf";
        public final static String FONTBI = localFont() + "verdanaz.ttf";

        public static String localFont() {
            if (getDiretorio() != null) {
                return getDiretorio() + "/Fonts/";
            } else {
                return "Documentos/Fonts/";
            }
        }

        public static String getDiretorio() {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ServletContext scontext;
            String arquivo = null;
            if (facesContext != null) {
                scontext = (ServletContext) facesContext.getExternalContext().getContext();
                arquivo = scontext.getRealPath("/Documentos/");
            }
            return arquivo;
        }
    }

    public static class Empresa implements Serializable {

        public static String NOME = getDados("NOME").toUpperCase();
        public static String ENDERECO = getDados("RESIDENCIA").toUpperCase();
        public static String CAIXAPOSTAL = "Caixa Postal ".toUpperCase() + getDados("CP").toUpperCase();
        public static String REPUBLICA = "REPUBLICA DEMOCRATICA DE S. TOMÉ E PRINCIPE";
        public static String TELEFAX = "TEL/FAX: " + getDados("TELEFONE").toUpperCase();
        public static String SOCIEDADE = "SOCIEDADE ANONIMA DE SEGUROS";
        public static String APOLICE = "APOLICE: ";
        public static String CAPITALSOCIAL = "COM UM CAPITAL DE USD " + getDados("CAPITALSOCIAL");
        public static String EMAIL = "Email: ".toUpperCase() + getDados("EMAIL");

        public static String getDados(String param) {
            try {
                ResultSet rs;
                if (EmpresaDao.getDadoEmpresa() != null) {
                    rs = EmpresaDao.getDadoEmpresa();
                    return rs.getString(param);
                } else {
                    return " ";
                }
            } catch (SQLException ex) {
                return " ";
            }
        }
    }

    public static String getPestação(int i) {
        if (i < 10) {
            return getUnidade(i);
        } else {
            return getDecenas(i);
        }
    }

    public static String getUnidade(int i) {
        switch (i) {
            case 1:
                return "Primeira ";
            case 2:
                return "Segunda ";
            case 3:
                return "Terceira ";
            case 4:
                return "Quarta ";
            case 5:
                return "Quinta ";
            case 6:
                return "Sexta ";
            case 7:
                return "Setima ";
            case 8:
                return "Oitava ";
            default:
                return "Nona ";
        }
    }

    public static String getDecenas(int i) {
        switch (i) {
            case 10:
                return "Decima ";
            case 11:
                return "Decima Primeira ";
            case 12:
                return "Decima Segunda ";
            case 13:
                return "Decima Terceira ";
            case 14:
                return "Decima Quarta ";
            case 15:
                return "Decima Quinta ";
            case 16:
                return "Decima Sexta ";
            case 17:
                return "Decima Setima ";
            case 18:
                return "Decima Oitava ";
            default:
                return "Decima Nona ";
        }
    }

    public static void main(String[] args) {

    }

    public static String toMoeda(Object o, String moeda) {
        if (o != null && !o.toString().isEmpty()) {
            return Moeda.format(Double.valueOf(o.toString().replace(",", ".").replace(" ", "0"))) + " " + moeda;
        } else {
            return " ";
        }
    }

    public static String toFormat(Object o, String patter1, String patter2) {
        SimpleDateFormat sdf = new SimpleDateFormat(patter1);
        SimpleDateFormat sdf2 = new SimpleDateFormat(patter2);
        try {
            return sdf.format(sdf2.parse((o == null) ? " " : o.toString()));
        } catch (ParseException ex) {
            return " ";
        }
    }

    public static void AlterDadosEmpresa() {
        Empresa.NOME = Empresa.getDados("NOME").toUpperCase();
        Empresa.ENDERECO = Empresa.getDados("RESIDENCIA").toUpperCase();
        Empresa.CAIXAPOSTAL = "Caixa Postal ".toUpperCase() + Empresa.getDados("CP").toUpperCase();
        Empresa.REPUBLICA = "REPUBLICA DEMOCRATICA DE S. TOMÉ E PRINCIPE";
        Empresa.TELEFAX = "TEL/FAX: " + Empresa.getDados("TELEFONE").toUpperCase();
        Empresa.SOCIEDADE = "SOCIEDADE ANONIMA DE SEGUROS";
        Empresa.APOLICE = "APOLICE: ";
        Empresa.CAPITALSOCIAL = "COM UM CAPITAL DE USD " + Empresa.getDados("CAPITALSOCIAL");
        Empresa.EMAIL = "Email: " + Empresa.getDados("EMAIL");
    }
}
