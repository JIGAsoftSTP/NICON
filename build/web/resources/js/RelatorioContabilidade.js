$(document).ready(function (e)
{
    typeReport("Pagamentos");
    $('.firstSectionDescriptionField').html('Valor:');
    $('.sectionHide').css('display', 'none');
    $('.menu-lateral li').click(function (){
        var actual = $(this).html();
        $('.titleSelected').html(actual);
        typeReport(actual);
        console.log($(".field").val());
        $('.field').trigger('change');
      
    });
    
    $('.searchReportField').keypress(function(e)
    {
       if(e.keyCode===13) // se o evento for enter
            $('.field').trigger('change');
    });
    
    $('.icon-search').click(function (e)
    {
        e.preventDefault();
        $('.field').trigger('change');
    });
});


function typeReport(parametro)
{
    if(parametro==="Taxas")
    {
        $(".contabilidadeTaxas").css("display","");
        $(".mi-values").css("display","none");
    }
    else if(parametro === 'Pagamentos' || parametro === 'Recebimentos')
    {
        $('.sectionHide').css('display', 'none');
        $('.balancete').css('display', 'none');
        $(".mi-values").css("display","");
        $(".contabilidadeTaxas").css("display","none");
        $('.firstSectionDescriptionField').html('Valor:');
    }
    else if(parametro ==='Contas')
    {
        $('.sectionHide').css('display', 'none');
        $('.balancete').css('display', 'none');
        $(".mi-values").css("display","");
        $(".contabilidadeTaxas").css("display","none");
        $('.firstSectionDescriptionField').html('');
    }
    else if(parametro === 'Mapa de Produção')
    {
        $('.firstSectionFifthDescriptionField').html('');
        $('.sectionHide').css('display', 'none');
        $('.balancete').css('display', '');
        $('.firstField').html('');
        $(".mi-values").css("display","");
        $(".contabilidadeTaxas").css("display","none");
        $('.firstSectionDescriptionField').html('STD:');
        $('.secondSectionDescriptionField').html('USD:');
        $('.thirdSectionDescriptionField').html('EUR:');
    }
    else
    {
        $('.sectionHide').css('display', '');
        $('.balancete').css('display', '');
        $(".mi-values").css("display","");
        $(".contabilidadeTaxas").css("display","none");
        $('.firstSectionDescriptionField').html('TOTAL DAS CONTAS DO BALANÇO:');
        $('.secondSectionDescriptionField').html('TOTAL DAS CONTAS DE RESULTADO:');
        $('.thirdSectionDescriptionField').html('TOTAL DO BALANCETE:');
        $('.firstField').html('');
    }
    relatorioContabilidade([{name: 'relatorio', value: parametro}]); 
}

function balanceteTotaisBalanco(debito, credito, devedor, credor)
{
    $('.firstSectionSecondDescriptionField').html('DÉBITO: '+debito);
    $('.firstSectionThirdDescriptionField').html('CRÉDITO: '+credito);
    $('.firstSectionFourthDescriptionField').html('DEVEDOR: '+devedor);
    $('.firstSectionFifthDescriptionField').html('CREDOR: '+credor);
}
function balanceteTotaisResultado(debito, credito, devedor, credor)
{
    $('.secondSectionSecondDescriptionField').html('DÉBITO: '+debito);
    $('.secondSectionThirdDescriptionField').html('CRÉDITO: '+credito);
    $('.secondSectionFourthDescriptionField').html('DEVEDOR: '+devedor);
    $('.secondSectionFifthDescriptionField').html('CREDOR: '+credor);
}
function balanceteTotaisBalancete(debito, credito, devedor, credor)
{
    $('.thirdSectionSecondDescriptionField').html('DÉBITO: '+debito);
    $('.thirdSectionThirdDescriptionField').html('CRÉDITO: '+credito);
    $('.thirdSectionFourthDescriptionField').html('DEVEDOR: '+devedor);
    $('.thirdSectionFifthDescriptionField').html('CREDOR: '+credor);
}

function mapaProducaoTotais(std, usd, eur, tipo, premio)
{
         console.log('tipo '+tipo);
         switch (tipo)
         {
             case '1':
                console.log('entrou total seguro automovel');
                $('.firstSectionDescriptionField').html('Total Seguro Automóvel: ');
                $('.firstSectionSecondDescriptionField').html('STD: '+std);
                $('.firstSectionThirdDescriptionField').html('USD: '+usd);
                $('.firstSectionFourthDescriptionField').html('EUR: '+eur);
            break;
        case '2':
            console.log('entrou total de total');
            $('.firstSectionDescriptionField').html('Total: ');
            $('.firstSectionSecondDescriptionField').html('STD: '+std);
            $('.firstSectionThirdDescriptionField').html('USD: '+usd);
            $('.firstSectionFourthDescriptionField').html('EUR: '+eur);
            break;
        case '3':
            $('.firstSectionDescriptionField').html('Prémio: ');
            $('.firstSectionSecondDescriptionField').html(premio);
            $('.firstSectionThirdDescriptionField').html('');
            $('.firstSectionFourthDescriptionField').html('');
            break;
        default :
            console.log('entrou total seguro automovel');
            $('.firstSectionDescriptionField').html('Total Seguro Automóvel: ');
            $('.firstSectionSecondDescriptionField').html('STD: '+std);
            $('.firstSectionThirdDescriptionField').html('USD: '+usd);
            $('.firstSectionFourthDescriptionField').html('EUR: '+eur);
        break;
    }

}

