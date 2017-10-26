/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import modelo.ComoBox;
import modelo.ProcessThree;
import org.primefaces.behavior.ajax.AjaxBehavior;
import org.primefaces.behavior.ajax.AjaxBehaviorListenerImpl;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author Servidor
 */
public class DataTableControl implements Serializable
{
    private final ArrayList<String[]> listaValue;
    private final ArrayList<String[]> listaShow;
    //private final ArrayList<ComoBox> colummnsName;
    private final ArrayList<Integer> listInclud;
    //private ArrayList<ComoBox> colummnShow;
    private ArrayList<TableColumn> columns;
    private BiFunction<HashMap<String, String>,Integer, Boolean> filter;
    private ProcessThree<Integer, HashMap, ArrayList> onProcessThree;
    private final String nameClasse_atribute;
    
    private String [] selectdLine;
    //private double[] percentageColumun;
    //private int lenthColum[];
    
    
    private DataTable dataTable;
    private FacesContext fc;
    private Application application;
    private ExpressionFactory ef;
    private final String idDataTable;
    private ELContext elc;
    private int accountLength = -1;
    
    /**
     * Essa funcao serve para proucurar por uma tabela em um formulario
     * @param currentFaces
     * @param idComponent
     * @return 
     */
    public static DataTable findDataTable (FacesContext currentFaces, String idComponent)
    {
        try
        {
            return (DataTable) findComponet(currentFaces, idComponent);
        }catch (Exception ex)
        {
            return null;
        }
    }
    
    
    /**
     * Essa funcao serve para proucurar por uma componente em um formulario
     * @param currentFaces
     * @param idComponent
     * @return 
     */
    public static UIComponent findComponet(FacesContext currentFaces, String idComponent)
    {
        return UIComponent.getCurrentComponent(currentFaces).findComponent(idComponent);
    }
    
   
    /**
     * 
     * @param idDataTable o id da tabela na pagina
     * @param beanName_dtControlAtribute o nome do atributo da DataTableContro no beans
     */
    public DataTableControl (String idDataTable, String beanName_dtControlAtribute)
    {
        
        
        this.nameClasse_atribute = beanName_dtControlAtribute;
        this.idDataTable = idDataTable;
        
        this.listaValue = new ArrayList<>();
        this.listaShow = new ArrayList<>();
        this.listInclud = new ArrayList<>();
        this.columns = new ArrayList<>();
    }

    public int getAccountLength() {
        return accountLength;
    }

    public void setAccountLength(int accountLength) {
        this.accountLength = accountLength;
    }


    /**
     * Atualizar o faces na data control
     * @param pageFace 
     */
    public void updFaces(FacesContext pageFace)
    {
        this.fc = pageFace;
        this.application = this.fc.getApplication();
        this.ef = this.application.getExpressionFactory();
        this.elc = this.fc.getELContext();
        this.dataTable = findDataTable(pageFace, this.idDataTable);
    }
    
    
    
    /**
     * Inicializar o controladorDeResultSet
     * @param rs O resultSet Lista de inclusao 
     * @param apresentarColunas Mode de escolher colunas para a tabela
     * @param includExcudName
     */
    public void refreshModel(ResultSet rs, ShowMode apresentarColunas, String ... includExcudName)
    {
        if(this.dataTable != null)
        {
            this.prepareModel(rs, apresentarColunas, includExcudName);
        }
    }
    
    /**
     * Recaregar os dados na tabela
     */
    public void reloadModel()
    {
        if(dataTable != null)
        {
            //
            if(this.filter != null || onProcessThree != null)
            {
                filter();
                this.dataTable.setValue(this.listaShow);
            }
            else
            {
                this.dataTable.setValue(this.listaValue);
            }
            
            this.dataTable.setVar("linha");
            this.dataTable.setSelectionMode("single");
            
            
            ValueExpression rowKeyExp = ef.createValueExpression(elc, "#{linha}", Object.class);
            this.dataTable.setValueExpression("rowKey", rowKeyExp);
            
            ValueExpression selectionExp = ef.createValueExpression(elc, "#{"+this.nameClasse_atribute+".selectdLine}", Object.class);
            this.dataTable.setValueExpression("selection", selectionExp);
            
            this.dataTable.getChildren().clear();
                  
            for (TableColumn coll: this.columns)
            {
                if(!coll.show) 
                    continue;
                {
                    Column showColumnTable = (Column) this.application.createComponent(Column.COMPONENT_TYPE);
                    UIOutput indexColumnTitle = (UIOutput) this.application.createComponent(UIOutput.COMPONENT_TYPE);

                    //Obtendo on nome da coluna
                    indexColumnTitle.setValue(coll.showName);
                    showColumnTable.getFacets().put("header", indexColumnTitle);

                    //Estilizar a coluna para determinar a largura que a mesma devera ocupar em relacao as outras
                    if(coll.width != null)
                        showColumnTable.setStyle("width:"+coll.width+"%");
                    else showColumnTable.setStyle("width:"+coll.precentage+"%");
                    showColumnTable.setStyle("text-align:"+coll.aling.name().toLowerCase());
                    
                    UIComponent valuesColumn = null;
                    HtmlOutputLabel label = null;
                    HtmlOutputText text = null;
                    if(coll.type == TypeColumn.TEXT)
                    {
                        int realIndex = coll.index;
                        ValueExpression indexValueExp = ef.createValueExpression(elc, "#{linha["+realIndex+"]}", Object.class);
                        text = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE );
                        text.setValueExpression("value", indexValueExp);
                        //showColumnTable.setValueExpression("sortBy", shortByValueExp);
                        //ValueExpression shortByValueExp = ef.createValueExpression(elc, "#{linha["+realIndex+"]}", Object.class);
                        
                        valuesColumn = text;
                    }
                    else
                    {
                        indexColumnTitle.setValue("");
                        label = (HtmlOutputLabel) application.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
                        label.setStyleClass(coll.cssClass);
                        label.setTitle(coll.showName);
                        valuesColumn = label;
                    }
                    
                    //Quando tiver o eveto ajax na coluna para ser disparado
                    if(coll.ajax)
                    {
                        AjaxBehavior ajax = (AjaxBehavior) application.createBehavior(AjaxBehavior.BEHAVIOR_ID);
                        MethodExpression me = ef.createMethodExpression(fc.getELContext(), coll.listiner, Object.class, new Class<?>[]{Object.class});
                        if(coll.process != null && coll.process.length()>0) ajax.setProcess(coll.process);
                        if(coll.process != null && coll.process.length()>0) ajax.setUpdate(coll.update);
                        ajax.addAjaxBehaviorListener(new AjaxBehaviorListenerImpl(me, me));
                        if(coll.onComplet != null) ajax.setOncomplete(coll.onComplet);
                        if(coll.onStart != null) ajax.setOnstart(coll.onStart);                        
                        
                        if(label != null) label.addClientBehavior(coll.event, ajax);
                        if(text != null) text.addClientBehavior(coll.event, ajax);
                    }
                    showColumnTable.getChildren().add(valuesColumn );
                    this.dataTable.getChildren().add( showColumnTable );
                }
                System.out.println(coll);
            }
        }
    }
    
    
    /**
     * Essa funcao serve para apartir de um resultSet colocar os dados em uma tabela
     * Essa
     * @param rs
     * @param modoApresentacao
     * @param includExcudName
     */
    public final  void prepareModel(ResultSet rs, ShowMode modoApresentacao, String ... includExcudName)
    {
        modoApresentacao = (modoApresentacao == null)? ShowMode.SHOWALL : modoApresentacao;
        if (rs != null)
        {
            try 
            {
                //Limpar todas os antigos dados armazenado o objecto
                int totalColumn = rs.getMetaData().getColumnCount();
                ArrayList<String> inOutColuns = new ArrayList<>(Arrays.asList(includExcudName));
                TableColumn column;
                this.columns.clear();
                this.listaValue.clear();
                this.listInclud.clear();
                
                //Carregar todos os nomes e validar qual deve ser apresentado na tabela
                    //Obter a string com o maior nome
                for (int i =0; i<totalColumn; i++)
                {
                    String name = rs.getMetaData().getColumnName(i+1);
                    this.columns.add(column = new TableColumn(i, name));
                    
                    //Verificar se o nome esta na lista de inclusao ou exclusao
                    boolean exist = inOutColuns.contains(name);              
                    if ((modoApresentacao == ShowMode.SHOWALL) 
                            || (modoApresentacao == ShowMode.SHOW && exist)
                            || (modoApresentacao == ShowMode.HIDE && !exist))
                        column.show = true;
                }
                
                //Carregar totos os dados do resultSet para array
                while(rs.next())
                {
                    String aux[]=new String[this.columns.size()];
                    for (int i =0; i<this.columns.size(); i++)
                    {
                        aux[i] = "";
                        try
                        {
                           System.out.println("dados "+rs.getString("CONTA"));
                            aux[i] = rs.getString(i+1);
                            if(aux[i] == null) aux[i] = "";
                            if(aux[i].length()> this.columns.get(i).maxLength)
                                this.columns.get(i).maxLength = aux[i].length();
                            
                        }catch (Exception ex) {}
                    }
                    if(accountLength != -1){
                        if(rs.getString("CONTA").length() == accountLength)
                            this.listaValue.add(aux);
                    }
                    else
                         this.listaValue.add(aux);
                }
                rs.close();
                this.updPercentageColumn();
                this.reloadModel();
                
                //Calculara as percentagem que cada coluna ira ocupar na tabela
            } catch (Exception ex) 
            {
                Logger.getLogger(DataTableControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Escolher uma coluna para mostrar ou esconder
     * @param show
     * @param name 
     */
    public void selectShow (ShowMode show, String name)
    {
        for(TableColumn colun: this.columns)
        {
            if(colun.dbName.equals(name) && show == ShowMode.SHOW )
            {
                colun.show = true;
                break;
            }
            else if(colun.dbName.equals(name) && show == ShowMode.HIDE)
            {
                colun.show = false;
                break;
            }
            else if(show == ShowMode.SHOWALL)
                colun.show = true;
        }
        this.updPercentageColumn();
    }
    
    
    /**
     * Obter o valor em uma dada coluna e linha
     * @param row A linha requisitada
     * @param colummn a coluna requisitada
     * @return o Resultadao do valor
     */
    public String getValueAt(int row, String colummn)
    {
        return this.getRowMap(row).get(colummn); 
    }

    public DataTable getDataTable ()
    {
        return this.dataTable;
    }
    
    public void removeAll()
    {
        this.listaValue.clear();
        this.columns.clear();
        this.listInclud.clear();
    }
//    /**
//     * Obter a lista das colunas que estão sendo projectado
//     * @return 
//     */
//    public ArrayList<ComoBox> getColummnShow()
//    {
//        ArrayList<ComoBox> list = new ArrayList<>();
//        for(ColumnDataTable column: this.columns)
//        {
//            if(column.show)
//                list.add(new ComoBox(column.dataBaseName, column.showName));
//        }
//        return list;
//    }
//
//    public void setColummnShow(ArrayList<ComoBox> colummnShow)
//    {
//        this.colummnShow = colummnShow;
//    }
    
    
    /**
     * Obter os nomes da colunas
     * @return 
     */
//    public ArrayList<ComoBox> getColummnsName() {
//        return colummnsName;
//    }
    public ArrayList<TableColumn> getColummnsName() {
        return columns;
    }


    /**
     * Lista dos dados da tabela
     * @return 
     */
    public ArrayList<String[]> getListaValue() {
        return listaValue;
    }

    /**
     * Obter o total da linha
     * @return 
     */
    public int countLines ()
    {
        return this.listaValue.size();
    }

    /**
     * Obter o total da coluna
     * @return 
     */
    public int countColumn() {
        return this.columns.size();
    }
    
    /**
     * Obter o total das colunas que esão sendo projectada
     * @return 
     */
    public int countColumnShow()
    {
        int count =0;
        for (TableColumn c: columns)
            if(c.show) count ++;
        return count;
    }

   
    /**
     * Obter o mapa da linha selecionada
     * @return HasMap da linha selecionada
     */
    public HashMap<String, String> getSelededRowMap()
    {
        return this.rowMap(this.selectdLine);
    }
    
    /**
     * Obter o mapa de uma linha
     * @param row a linha que se pretende obter o mapa
     * @return 
     */
    public HashMap<String, String> getRowMap(int row)
    {
        if(row >=0 && row <this.listaValue.size())
            return this.rowMap(this.listaValue.get(row));
        return null;
    }
    
    
    
    /**
     * Criar um mapa de uma linha
     * @param rowInfo
     * @return 
     */
    private HashMap<String, String> rowMap(String [] rowInfo)
    {
        if (rowInfo== null) return  null;
        HashMap<String, String> map = new HashMap<>();
        for (TableColumn coll: this.columns)
        {
            if(coll.type == TypeColumn.TEXT)
                map.put(coll.dbName, rowInfo[coll.index]);
        }
        return map;
    }
    
    
    
    /**
     * Renomiar os nomes da colunas
     * @param realName O nome real da coluna
     * @param newName  O novo nome para a coluna
     */
    public void renameColumn (String realName, String newName)
    {
        for (TableColumn coll: columns)
        {
            if(coll.dbName.equals(realName))
            {
                coll.setShowName(newName);
                break;
            }
        }
        this.updPercentageColumn();
        this.reloadModel();
    }
    
    /**
     * Definir a largura da coluna
     * @param columnName O nome real da coluna
     * @param width O valor da coluna
     */
    public void setWidthColumn (String columnName, float width)
    {
        
        for (TableColumn coll: columns)
        {
            if(coll.dbName.equals(columnName))
                coll.setWidth(width);
        }
    }
  
    
    /**
     * colocara a linha selecionada
     * @param selectdLine 
     */
    public void setSelectdLine(String [] selectdLine) 
    {
        this.selectdLine = selectdLine;
    }
    
    /**
     * Obter o array da linha selecionada
     * @return 
     */
    public String [] getSelectdLine ()
    {
        return this.selectdLine;
    }

    /**
     * Esse procedimento serve para cantabilizar a percentagem que cada coluna ira copuar 
     * na tabela.
     * O calculo depende do do tamanho do maximo valor enm cada coluna incluido os nomes
     */
    private void updPercentageColumn() 
    {
        int sumLength = 0;
        for (TableColumn coll: this.columns)
            sumLength = sumLength + coll.maxLength;

        for(TableColumn coll: this.columns)
            coll.setPercentagem((double)((double) ((double) coll.maxLength*(double)100.0)/(double)sumLength));
    }
    
    /**
     * Devolve os dados do cabecalho
     * @param consumerHeader Cossumer da accao que sera executada com os dados
     */
    public void onReciveHeader (Consumer<ArrayList<ComoBox>> consumerHeader)
    {
        ArrayList<ComoBox> listColum = new ArrayList<>();
        for(TableColumn coll: columns)
        {
            if(coll.show && coll.type == TypeColumn.TEXT)
            {
                ComoBox newCombo = new ComoBox();
                newCombo.setValue(coll.showName);             
                 if(coll.width != null)
                    newCombo.putValue("width", coll.width);
                 else newCombo.putValue("width", coll.precentage);
                 listColum.add(newCombo);
            }
            
        }
        consumerHeader.accept(listColum);
    }
    
    public void onReciveDatas(Consumer<String []> consumerDadas)
    {
       //this.dataTable.setValue(model);
        String row [];
        int countShow  = 0;
        for(TableColumn coll: this.columns)
            if(coll.show) countShow ++;
        
        for(String[] rowList: listaValue)
        {
            row = new String[countShow];
            int i = 0;
            for(TableColumn coll: this.columns)
            {
                if(coll.show)
                {
                    row[i++] = rowList[coll.index];
                }
            }
            consumerDadas.accept(row);
        }
    }

    
//    /**
//     * Atualizar o tamanho maximo de cada coluna
//     * @param indexColumn 
//     */
//    private void updLength(int indexColumn) 
//    {
//        int maxLength = this.colummnsName.get(indexColumn).getValue().length();
//        for (String [] s: this.listaValue)
//        {
//            if(s != null && s[indexColumn] != null && maxLength < s[indexColumn].length())
//                maxLength = s[indexColumn].length();
//        }
//        this.lenthColum[indexColumn] = maxLength;
//    }
    /**
     * Esta função server para filtar o data control
     * @param filter 
     */
    public void setFilter(BiFunction<HashMap<String, String>, Integer, Boolean> filter )
    {
        boolean igaual = (this.filter == filter);
        this.filter = filter;
        if(!igaual)
            reloadModel();
    }
    
    public void setOnProcess(ProcessThree<Integer, HashMap, ArrayList> onProcess)
    {
        this.onProcessThree = onProcess;
    }

    private void filter() {
        listaShow.clear();
        
        for (int i =0; i < this.listaValue.size() ; i++) 
        {
            HashMap<String, String> map = this.getRowMap(i);
            
            boolean aceite = filter == null
                    ||(filter != null && filter.apply(map, i));
            if(aceite)
            { 
                if(onProcessThree != null)
                    onProcessThree.onProcess(i, map, listaShow);
                listaShow.add(listaValue.get(i));
            }
        }
    }
    
    public void alingColumn(String collName, Alignment align)
    {
        for(TableColumn coll: this.columns)
           coll.setAlignm(align);
    }
    
    
    public TableColumn addColumnLabel(String columnName, String title, String className)
    {
        TableColumn column = new TableColumn(null, columnName);
        column.type = TypeColumn.LABEL;
        column.show = true;
        column.showName = title;
        column.cssClass = className;
        this.columns.add(column);
        return column;
    }
    
    public boolean addColumnAjax(String columnName, String event, String process, String update)
    {
        TableColumn coll = this.findColumn(columnName);
        if(coll == null) return false;
        coll.ajax = true;
        coll.event = event;
        coll.process = process;
        coll.update = update;
        return true;
    }
    
    /**
     * Adicionar o evento on listiner numa dada coluna
     * @param columnName
     * @param listiner
     * @return 
     */
    public boolean addListiner(String columnName, String listiner)
    {
        TableColumn coll = this.findColumn(columnName);
        if(coll == null || !coll.ajax) return false;
        coll.listiner = listiner;
        return true;
    }
    
    /**
     * Adicionar o evento on complit em uma dada coluna
     * @param columnName
     * @param onComplit
     * @return 
     */
    public boolean addOnComplet(String columnName, String onComplit)
    {
        TableColumn coll = this.findColumn(columnName);
        if(coll == null || !coll.ajax) return false;
        coll.onComplet = onComplit;
        return true;
    }
    
    /**
     * Adicioanr o evento onStart em uma dada coluna
     * @param columnName
     * @param onStart
     * @return 
     */
    public boolean addOnStart(String columnName, String onStart)
    {
        TableColumn coll = this.findColumn(columnName);
        if(coll == null || !coll.ajax) return false;
        coll.onStart = onStart;
        return true;
    }
    
    /**
     * 
     * @param columnName
     * @param title
     * @param className
     * @param action 
     */
    @SuppressWarnings("UnusedAssignment")
    public void addActionColumn (String columnName, String title, String className, Consumer<HashMap<String, String>> action)
    {
        TableColumn coll = this.findColumn(columnName);
        if(coll == null) 
            coll = this.addColumnLabel(columnName, title, className);
        this.addColumnAjax(columnName, "click", null, null);
        this.addListiner(columnName, "#{"+this.nameClasse_atribute+".onClickListiner('"+columnName+"', linha)}");
        coll = this.findColumn(columnName);
        coll.clickAction = action;
    }

    public void onClickListiner(String columnName, String [] linha) 
    {
        TableColumn coll = this.findColumn(columnName);
        if(coll.clickAction != null)
            coll.clickAction.accept(this.rowMap(linha));
        
    }
    
    public void dataTableControlAjax()
    {
        System.out.println("bean.DataTableControl.dataTableControlAjax()");
    }

    public TableColumn findColumn(String columnName)
    {
        for(TableColumn coll: this.columns)
            if(coll.dbName.equals(columnName))
                return coll;
        return null;
    }
    
    public static class TableColumn
    {
        private String dbName;
        private String showName;
        private boolean show;
        private Integer index;
        private double precentage;
        private int maxLength;
        private Float width;
        private Alignment aling;
        private TypeColumn type;
        private String cssClass;
        private String event;
        private String process;
        private String listiner;
        private String update;
        private boolean ajax;
        private String onComplet;
        private String onStart;
        private Consumer<HashMap<String, String>> clickAction;

        public TableColumn(Integer index, String dataBaseName) 
        {
            this.index = index;
            this.dbName = dataBaseName;
            this.showName = dataBaseName;
            if(dataBaseName == null)
                this.dbName = "";
            this.maxLength = this.dbName.length();
            this.aling = Alignment.LEFT;
            type = TypeColumn.TEXT;
        }

        private void setShowName(String newName) 
        {
            this.showName = newName;
            if(newName.length()>this.maxLength)
                this.maxLength = newName.length();
        }
        
        private void setWidth(Float newWidth)
        {
            this.width = newWidth;
        }

        private void setPercentagem(Double perecentage)
        {
            this.precentage = perecentage;
        }

        @Override
        public String toString()
        {
            return "ColumnDataTable{" + "dataBaseName=" + dbName + ", showName=" + showName + ", show=" + show + ", index=" + index + ", precentage=" + precentage + ", maxLength=" + maxLength + ", width=" + width + '}';
        }
 
        private void setAlignm(Alignment align) 
        {
            this.aling = align;
        }
   }

    /**
     * Modo de apresentacao na tabela
     */
    public static enum ShowMode
    {
        
        /**
         * Mostar um conjundo de colunas especificadas
         */
        SHOW,
        
        /**
         * Esconder um conjuntos de colunas especificada
         */
        HIDE,
        
        /**
         * Mostrar todos as colunas de result set
         */
        SHOWALL
    }
    
    public static enum Alignment
    {
        CENTER,
        RIGHT,
        LEFT
    }
    
    public static enum TypeColumn
    {
        LABEL,
        TEXT
    }
}