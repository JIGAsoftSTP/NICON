
$(document).ready(function (e)
{
    action(false);
    $('.firstSectionDescriptionField').html('Total Prémio:');
    $(".sectionHide").css('display', 'none');
 
    
    $('.menu-lateral li').click(function (){
        var actual = $(this).html();
        var paginaAtual = window.location.href;
        var contem ="GestSeg_Relatorios.xhtml";
        $('.titleSelected').html(actual);
        action(actual === 'Crescente' || actual === 'Produção');
         if(paginaAtual.indexOf(contem) !==-1)
         {
            relatorioSelected(actual);
            $('.seguro').trigger('change');
         } 
         else
         {
             relatorioRecursosHumanos([{name: 'relatorio', value:$(this).html()}]);
             $(".icon-search").trigger('click');
         }
         
        contratoRelatorioTotal(actual);
       
    });
   
});
function relatorioSelected(param){
    dados([{name: 'relatorio', value: param}]);
}
function action( isCrescente ){
    if(isCrescente){
        $(".relatorioFiltrar").attr('disabled', false);

    } else 
    {
        $(".relatorioFiltrar").attr('disabled', true);
        $(".relatorioFiltrar").val(1);
    }
    
}

function totalRegistro(table)
{
      var rowCount = $(table +" td").closest("tr").length; // número de linhas na tabela
      console.log("total de registro "+rowCount);
}

function relatorioContratos()
{
     getRowCount(".tableRel");
}

function contratoRelatorioTotal(typeReport)
{
    var actual = typeReport;
    
     if(actual === 'Clientes' || actual === 'Seguros')
        {
            $('.mi-values').css('display', '');
            $('.firstSectionDescriptionField').html(((actual ==='Clientes')? 'Total Prémio:' : 'Prémio:'));
            $('.sectionHide').css('display', 'none');
        }
        else if(actual === 'Produção')
        {
            $('.mi-values').css('display', '');
            $('.sectionHide').css('display', '');
            $('.firstSectionDescriptionField').html('Prémio:');
            $('.secondSectionDescriptionField').html('Imposto Consumo:');
            $('.thirdSectionDescriptionField').html('Imposto Selo:');
            $('.fourthSectionDescriptionField').html('FGA:');
            $('.fifthSectionDescriptionField').html('Valor Total:');
             
        }
        else if(actual === 'Crescente')
        {
            $('.mi-values').css('display', 'none');
            $('.sectionHide').css('display', '');
            $('.lastSection').css('display', 'none');
            $('.firstSectionDescriptionField').html('Novos Aderentes:');
            $('.secondSectionDescriptionField').html('Todos Aderentes:');
            $('.thirdSectionDescriptionField').html('Novos Contratos:');
            $('.fourthSectionDescriptionField').html('Todos Contratos:');
        }
        else if(actual === 'Mapa de Provisão')
        {
            $('.mi-values').css('display', '');
            $('.sectionHide').css('display', '');
            $('.lastSection').css('display','none');
            $('.firstSectionDescriptionField').html('Prémio Liquido:');
            $('.secondSectionDescriptionField').html('Provisão 10%:');
            $('.thirdSectionDescriptionField').html('Provisão 30%:');
            $('.fourthSectionDescriptionField').html('Total:');
        }
       

        
        
}