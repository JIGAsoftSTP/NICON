
package enumeracao;

public enum TipoPesquisa {
    TODOS (1),
    DOCUMENTO(2),
    LANCAMENTO(3);
    
    private int type = 1; 
            
    private TipoPesquisa(int type)
    {
        this.type = type;
    }

    public int getType() {
        return type;
    }

  
    
}
