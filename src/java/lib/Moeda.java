/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import conexao.Call;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import validacao.OperacaoData;

/**
 *
 * @author projecto1
 */
public class Moeda {

    private double realValue;
    private int decimalCase;
    private int numPorCasas;
    private char sepInteiroDecimal;
    private char sepCasasCasas;

    /**
     * Inicializar a moeda com as caracteristicas default O separador entre as
     * casas inteira é: ponto(.) O separador entre a parate interira e decimal
     * é: virgula(,) A quantidade de casa decimal é: 2 (duas casas decimais) A
     * quantidade de numeros inteiros por casa é 3: (trez casas decimais)
     * Exemplo 1000000 = 1.000.000,00
     */
    public Moeda() {
        this.sepInteiroDecimal = ',';
        this.sepCasasCasas = '.';
        this.numPorCasas = 3;
        this.decimalCase = 2;
        this.realValue = 0.0;
    }

    /**
     * Inicializar a moeda com as caracteristicas default O separador entre as
     * casas inteira é: ponto(.) O separador entre a parate interira e decimal
     * é: virgula(,) A quantidade de casa decimal é: 2 (duas casas decimais) A
     * quantidade de numeros inteiros por casa é 3: (trez casas decimais)
     * Exemplo realValue é 1000000 = 1.000.000,00
     *
     * @param realValue
     */
    public Moeda(double realValue) {
        this();
        this.realValue = realValue;
    }

    /**
     *
     * @param realValue O valor original em double
     * @param decimalCase O total de casas decimais que deveraao aparecer
     * @param numPorCasas A quantidade de caractere em dasa casa inteiros
     * @param sepInteiroDecimal O caracter que ira separar a casa decimal
     * @param sepCasasCasas O caracter que ira separar cada casa
     */
    public Moeda(double realValue, int decimalCase, int numPorCasas, char sepInteiroDecimal, char sepCasasCasas) {
        this.realValue = realValue;
        this.decimalCase = decimalCase;
        this.numPorCasas = numPorCasas;
        this.sepInteiroDecimal = sepInteiroDecimal;
        this.sepCasasCasas = sepCasasCasas;
    }

    /**
     *
     * @param formatValues O valor formatado
     * @param decimalCase O total de casas decimais que deveraao aparecer
     * @param numPorCasas A quantidade de caractere em dasa casa inteiros
     * @param sepInteiroDecimal O caracter que ira separar a casa decimal
     * @param sepCasasCasas O caracter que ira separar cada casa
     */
    public Moeda(String formatValues, int decimalCase, int numPorCasas, char sepInteiroDecimal, char sepCasasCasas) {
        try {
            this.decimalCase = decimalCase;
            this.numPorCasas = numPorCasas;
            this.sepInteiroDecimal = sepInteiroDecimal;
            this.sepCasasCasas = sepCasasCasas;
            formatValues = formatValues.replace(sepCasasCasas + "", "");
            formatValues = formatValues.replace(sepInteiroDecimal, '.');
            this.realValue = Double.parseDouble(formatValues);
        } catch (Exception ex) {
            System.err.println("Invalid number format");
            throw new RuntimeException(ex);
        }

    }

    /**
     * Criar uma moeda a partir de um valor formatado por default
     *
     * @param formatValues -- O valor formatado
     */
    public Moeda(String formatValues) {
        this();
        try {
            formatValues = formatValues.replace(sepCasasCasas + "", "");
            formatValues = formatValues.replace(sepInteiroDecimal, '.');
            this.realValue = Double.parseDouble(formatValues);
        } catch (Exception ex) {
            System.err.println("Invalid number format");
            throw new RuntimeException(ex);
        }
    }

    public double getRealValue() {
        return realValue;
    }

    public void setRealValue(double realValue) {
        this.realValue = realValue;
    }

    public int getDecimalCase() {
        return decimalCase;
    }

    public void setDecimalCase(int decimalCase) {
        this.decimalCase = decimalCase;
    }

    public int getNumPorCasas() {
        return numPorCasas;
    }

    public void setNumPorCasas(int numPorCasas) {
        this.numPorCasas = numPorCasas;
    }

    public char getSepInteiroDecimal() {
        return sepInteiroDecimal;
    }

    public void setSepInteiroDecimal(char sepInteiroDecimal) {
        this.sepInteiroDecimal = sepInteiroDecimal;
    }

    public char getSplitChar() {
        return sepCasasCasas;
    }

    public void setSplitChar(char splitChar) {
        this.sepCasasCasas = splitChar;
    }

    @Override
    public String toString() {
        return this.format();
    }

    /**
     * Funcoa para cria a formatacao a parit do valor actual
     *
     * @return
     */
    public String format() {
        String format = "";

        //Forcar o valor a ser positivo
        double valueUsar = (this.realValue < 0) ? this.realValue * -1 : this.realValue;

        //Separas as partes inteiras das partes
        long intPart = (long) valueUsar;
        double doubepart = (valueUsar - intPart);
        String parte = (intPart + "");
        int count = 1;

        //Formatar a parte inteira
        for (int i = parte.length() - 1; i >= 0; i--) {
            format = parte.charAt(i) + format;
            if (count == this.numPorCasas && i > 0) {
                count = 1;
                format = this.sepCasasCasas + format;
            } else {
                count++;
            }
        }
        if (this.decimalCase > 0) {
            format = format + this.sepInteiroDecimal;
        }

        //Foramatar a parte decimal
        parte = doubepart + "";
        for (int i = 0; i < this.decimalCase; i++) {
            if (i + 2 < parte.length()) {
                format = format + parte.charAt(i + (2));
            } else {
                format = format + '0';
            }
        }

        //Se o valor original for negativo entao adicionar o -
        return (this.realValue < 0 ? "-" : "") + format;
    }

    /**
     * Utilizar a formatacao defaulte para o valor do parametro
     *
     * @param value O valor que sera formatado
     * @return O valor formatado
     */
    public static String format(double value) {
        DecimalFormat formato = new DecimalFormat("#.##");
        value = Double.valueOf((formato.format(value).replace(',', '.')));
        
        return NumberFormat.getInstance(Locale.FRENCH).format(value)+Moeda.addZerro(value);
    }
    public static double unFormat(String value) {
        try { return  NumberFormat.getInstance(Locale.FRENCH).parse(value).doubleValue(); } 
        catch (ParseException ex) { return -1;}
    }
    
    private static String addZerro(double  valor)
    {
        int de = (int)Math.round((valor - (int)valor) * 100);
        return ((de == 0) ? ",00": ((de >= 10 && (de%10) != 0) || de <= 9) ? "": "0");
    }
    
    /**
     * Criara a foarmatacao a parir do valor fornecido definido o numero o que
     * ira separa as casas entre os numeros inteiros e o que ira separar as
     * casas inteirars das casas decimais
     *
     * @param value O valor que sera formatdo
     * @param separadorCasas O separadores entre as casas
     * @param separadorInteiroDecimal O separador entre a casa inteira e a casa
     * decimal
     * @return O valor formatado nas condecoes fornecidas
     */
    public static String format(double value, char separadorCasas, char separadorInteiroDecimal) {
        return Moeda.format(value, 3, 2, separadorCasas, separadorInteiroDecimal);
    }

    /**
     * Criar a formatacao usando o valor dado definindo: A quantidade dos
     * numeros por casa A quantidade de casas decimais O caracter que separa
     * cada casa O caracetr que separa a parte inteira da parte decimal
     *
     * @param value O valor a ser formatado
     * @param numerosPorCasas A quantidade de numeros em cada casa
     * @param casasDecimais O umeros de casas decimais que sera apresentada na
     * formatacao
     * @param separadorCasas O caracter que ira separas as casas inteiras
     * @param separadorInteiroDecimal O caracter que ira separar as casas
     * inteirar da casa decimal
     * @return
     */
    public static String format(double value, int numerosPorCasas, int casasDecimais, char separadorCasas, char separadorInteiroDecimal) {
        Moeda m = new Moeda(value, casasDecimais, numerosPorCasas, separadorInteiroDecimal, separadorCasas);
        return m.format();
    }

    public static void main(String[] args) {
        System.out.println(Moeda.format(91.9999));
        JTextPane jtp = new JTextPane();
        Moeda.EscreverEstenso(999991, jtp, "ERR");
        System.out.println(jtp.getText());
    }
    public static String moeda;
    public static int milhao;

    public static void EscreverEstenso(double valor, JTextPane d, String moeda)//escrever por extenso
    {
        int a = ((int) valor);
        //double centim=((double)(valor-a)*100);
        Moeda.moeda = moeda;
        d.setText("");//esvazia a caixa de texto
        int cent, dez, unid, mil;
        boolean rt = true;
        while (rt == true)//grante que não seja introduzido letras
        {
            try {
                rt = false;
            } catch (Exception e) {//mostra a mensagem caso nao for introduzido numeros inteiros
                d.setText("Só é permitido introduzir numeros inteiros \n Valor introduzido Inválido");
                rt = true;
                break;
            }
        }
        milhao = ((int) valor / 1000000);//separa o algarismo dos milhoes
        mil = ((int) valor % 1000000) / 1000;//separa o algarismo dos milhares
        cent = ((int) valor % 1000);//separa o algarismo das centanas
        dez = ((int) valor % 100) / 100;//separa o algarismo das dezanas
        unid = ((int) valor % 100);//separa o algarismo das unidades
        int centimos = (int) Math.round((valor - a) * 100);
        if (milhao > 1)//escreve por estenso o algarismo refente aos milhões, somemte se esse algarismo for maior que 1
        {
            fr(milhao, d);//função que permite

            d.setText(d.getText() + "Milhões ");
            milhao = 0;
        } else if (milhao == 1) {
            d.setText(d.getText() + "Um Milhão ");
            milhao = 0;
        }//escreve por estenso o algarismo refente aos milhões, somemte se esse algarismo for igual a 1
        if (mil > 1 & mil < 1000)//se o numero tiver trêis algarismos então escreve-o
        {
            fr(mil, d);//escreve por estenso o algarismo refente aos milhares, somemte se esse algarismo for maior que 1 e igual a 999
            d.setText(d.getText() + "Mil e ");
            milhao = 0;
        } else if (mil == 1)//escreve por estenso o algarismo refente aos milhares, somemte se esse algarismo for igual a 1
        {
            d.setText(d.getText() + "Mil ");
            milhao = 0;
        }
        if (cent > 0)//escreve as centenas se for maior que zero
        {
            fr(cent, d);
            milhao = 0;
        }
        if (dez > 0)//escreve as dezenas se for maior que zero
        {
            fp(dez, unid, d);
            milhao = 0;
        }
        d.setText(d.getText() + " " + Moeda.moeda);
        if (centimos > 0)//escreve as dezenas se for maior que zero
        {
            Moeda.moeda = "";
            d.setText(d.getText() + " e ");
            fr(centimos, d);
            milhao = 0;
            d.setText(d.getText() + "Centimos");
        }

    }

    public static void fr(int valor, JTextPane d)// função que permite escrever por estenso um numero em centenas
    {
        int cent, dez, unid;

        cent = (valor / 100);//separa o algarismo das centanas
        dez = (valor % 100) / 10;//separa o algarismo das dezanas
        unid = (valor % 10);//separa o algarismo das unidades
        if (cent > 0 && moeda.equals("Dobras") && milhao == 0)//se o numero tiver trêis algarismos então escreve-o
        {
            fem(cent, d, dez, unid);
        } else {
            switch (cent) {
                case 1:
                    if (cent == 1 & dez == 0 & unid == 0) {
                        d.setText(d.getText() + "Cem");
                    } else {
                        d.setText(d.getText() + "Cento");
                    }
                    break;
                case 2:
                    d.setText(d.getText() + "Duzentos");
                    break;
                case 3:
                    d.setText(d.getText() + "Trezentos");
                    break;
                case 4:
                    d.setText(d.getText() + "Quatrocentos");
                    break;
                case 5:
                    d.setText(d.getText() + "Quinhentos");
                    break;
                case 6:
                    d.setText(d.getText() + "Seiscentos");
                    break;
                case 7:
                    d.setText(d.getText() + "Setecentos");
                    break;
                case 8:
                    d.setText(d.getText() + "Oitocentos");
                    break;
                case 9:
                    d.setText(d.getText() + "Novecentos");
                    break;
            }
            if (dez > 0 && cent > 0) {
                d.setText(d.getText() + " e ");
            }
        }

        if (dez > 0)//escreve o algarismo da dezena
        {
            fp(dez, unid, d);
        }
        if (dez == 0 & cent == 0 & unid == 0)//se o numero digitado for zero
        {
            d.setText("Zero");
        }
        //JOptionPane.showMessageDialog(null, moeda);
        if (moeda.equals("Dobras") & dez != 1 & cent <= 9 & milhao == 0) {
            unid(unid, d);
        } else if (dez != 1 & cent <= 9) {
            switch (unid) {
                case 1:
                    d.setText(d.getText() + "Um");
                    break;
                case 2:
                    d.setText(d.getText() + "Dois");
                    break;
                case 3:
                    d.setText(d.getText() + "Três");
                    break;
                case 4:
                    d.setText(d.getText() + "Quatro");
                    break;
                case 5:
                    d.setText(d.getText() + "Cinco");
                    break;
                case 6:
                    d.setText(d.getText() + "Seis");
                    break;
                case 7:
                    d.setText(d.getText() + "Sete");
                    break;
                case 8:
                    d.setText(d.getText() + "Oito");
                    break;
                case 9:
                    d.setText(d.getText() + "Nove");
                    break;
            }
        }
        d.setText(d.getText() + " ");
    }

    public static void fp(int dez, int unid, JTextPane d)// função que permite escrever por estenso um numero em dezenas
    {
        if (dez > 1 & (unid == 0 | unid > 0))//se a dezena for apartir de 20, escreve dezena e depois passa a escrever unidade
        {
            switch (dez) {
                case 2:
                    d.setText(d.getText() + "Vinte");
                    break;
                case 3:
                    d.setText(d.getText() + "Trinta");
                    break;
                case 4:
                    d.setText(d.getText() + "Quarenta");
                    break;
                case 5:
                    d.setText(d.getText() + "Cinquenta");
                    break;
                case 6:
                    d.setText(d.getText() + "Sessenta");
                    break;
                case 7:
                    d.setText(d.getText() + "Setenta");
                    break;
                case 8:
                    d.setText(d.getText() + "Oitenta");
                    break;
                case 9:
                    d.setText(d.getText() + "Noventa");
                    break;
            }
            if (unid > 0) {
                d.setText(d.getText() + " e ");
            }
        } else if (dez == 1 & unid >= 0)//se a dezena for entre 10 e 20 escreva a mensagem abaixo
        {
            switch (unid) {
                case 0:
                    d.setText(d.getText() + "Dez");
                    break;
                case 1:
                    d.setText(d.getText() + "onze");
                    break;
                case 2:
                    d.setText(d.getText() + "Doze");
                    break;
                case 3:
                    d.setText(d.getText() + "Treze");
                    break;
                case 4:
                    d.setText(d.getText() + "Catorze");
                    break;
                case 5:
                    d.setText(d.getText() + "Quinze");
                    break;
                case 6:
                    d.setText(d.getText() + "Dezasseis");
                    break;
                case 7:
                    d.setText(d.getText() + "Dezassete");
                    break;
                case 8:
                    d.setText(d.getText() + "Dezoito");
                    break;
                case 9:
                    d.setText(d.getText() + "Dezanove");
                    break;
            }
        }
    }

    public static void unid(int unid, JTextPane d) {
        switch (unid) {
            case 1:
                d.setText(d.getText() + "Uma");
                break;
            case 2:
                d.setText(d.getText() + "Duas");
                break;
            case 3:
                d.setText(d.getText() + "Três");
                break;
            case 4:
                d.setText(d.getText() + "Quatro");
                break;
            case 5:
                d.setText(d.getText() + "Cinco");
                break;
            case 6:
                d.setText(d.getText() + "Seis");
                break;
            case 7:
                d.setText(d.getText() + "Sete");
                break;
            case 8:
                d.setText(d.getText() + "Oito");
                break;
            case 9:
                d.setText(d.getText() + "Nove");
                break;
        }
    }

    public static void fem(int cent, JTextPane d, int dez, int unid) {
        switch (cent) {
            case 1:
                if (cent == 1 & dez == 0 & unid == 0) {
                    d.setText(d.getText() + "Cem");
                } else {
                    d.setText(d.getText() + "Cento");
                }
                break;
            case 2:
                d.setText(d.getText() + "Duzentas");
                break;
            case 3:
                d.setText(d.getText() + "Trezentas");
                break;
            case 4:
                d.setText(d.getText() + "Quatrocentas");
                break;
            case 5:
                d.setText(d.getText() + "Quinhentas");
                break;
            case 6:
                d.setText(d.getText() + "Seiscentas");
                break;
            case 7:
                d.setText(d.getText() + "Setecentas");
                break;
            case 8:
                d.setText(d.getText() + "Oitocentas");
                break;
            case 9:
                d.setText(d.getText() + "Novecentas");
                break;
        }
    }

    public static int getIdMoedaBySigla(String nomeMoeda) {
        int idMoe = 0;
        try (ResultSet rss = Call.selectFrom("VER_MOEDA where SIGLA = ? ", "*", nomeMoeda)) {
            while (rss.next()) {  idMoe = rss.getInt("ID"); }
            return idMoe;
        } catch (SQLException ex) {
            Logger.getLogger(Moeda.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public static float getTaxaMoeda(int idBase, int idMoeda, Date dia) {
           float taxa = 1;
           try (ResultSet rss = Call.callTableFunction("PACK_CONTA.getTaxaMoney", "*",  OperacaoData.toSQLDate(dia), idBase, idMoeda)) {
            while (rss.next()) { taxa = rss.getFloat("TX_VENDA"); }
            return taxa;
        } catch (SQLException ex) {
            Logger.getLogger(Moeda.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
    }
    private static double taxa;
    public static double valorCompra(String nomeMoeda, Object date) {
        taxa = 0;
        ResultSet rs = Call.callTableFunction("PACK_CONTA.getTaxaDay", "*", date, nomeMoeda);
        Consumer<HashMap<String, Object>> act = (map) -> { taxa = Float.valueOf(toString(map.get("TX_VENDA"))); };
        Call.forEchaResultSet(act, rs);
        
        return taxa;
    }
    
    public static String toString(Object o) { return ((o == null) ? "" : o.toString()); }
    
    public static double arrendodamento(String s) {
        double d=Double.valueOf(s.replace(',', '.'));
        DecimalFormat formato = new DecimalFormat("#.##");
        d = Double.valueOf((formato.format(d).replace(',', '.')));
        return d;
    }
}
