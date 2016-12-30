/* 
 *Created by Grinalda Soares
 */
 var tipoOperacao = "registrar";
$(document).ready(function(e)
{
 
    $(".Titulo").html("Adicionar Conta Banco");
    contaInicial();
   
    //modal Relatório
    $(".abrir").click(function (e)
    {
        e.preventDefault();
        $(".Conta").css('display','');
        tipoOperacao ="registrar";
        conta();
        $(".contaCampos").attr('disabled', false);
        if($(".Titulo").html() ==="Editar Conta Banco")
            $(".Titulo").html("Adicionar Conta Banco");
         else if($(".Titulo").html() ==="Editar Conta Pagamento")
         {
            $(".Titulo").html("Adicionar Conta Pagamento");
         }
          $(".Conta").trigger("click");
        $(".Modalframe").css('display','flex');
    });
    
    $(".pequisarConta").click(function (e)
    {
        e.preventDefault();
        enviarDadosPesquisa('Por valor'); 
    });
    
    /**
     * for do exel whit pesquisa
     */
    $(".excel").click(function (e)
    {
        e.preventDefault();
        enviarDadosPesquisa('ExportExel'); 
    });
    
    /**
     * for do pdf whit pesquisa
     */
    $(".pdf").click(function (e)
    {
        e.preventDefault();
        enviarDadosPesquisa('ExportPDF'); 
    });
    
    $(".valorReal").keyup(function(e)
    {
        e.preventDefault();
        if (!$.isNumeric($(this).val()))
            $(this).val("");
   });
    
    $(".contaPesquisa").click(function (e)
    {
        enviarDadosPesquisa('Por conta');
    });

    $(".CloseModalFrame").click(function (e)
     {
        e.preventDefault();
        $(".registrarConta input:text, select").val("");
        $(".registrarConta input:text, select").css("border","");
        $(".Modalframe").fadeOut();
    });
    
       $(".numeroAC").keyup(function(e){
        e.preventDefault();
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if(!$.isNumeric($(this).val()))
            $(this).val("");
   });
       $(".numeroInterio").keyup(function (e)
        {
            e.preventDefault();
            var expre = /[^0-9]/g;
            // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
            if ($(this).val().match(expre))
                $(this).val($(this).val().replace(expre, ''));
      });
   
    $(".Conta").click(function(e)
   {
        var tipoConta = $("input[name='GestConta:contaForm:tipoConta']:checked").val();
        $(".registrarConta input:text, select").val("");
        $(".registrarConta input:text, select").css("border","");
            $(".accountErrorInfo").css("display","none");
       if(tipoConta ==="banco")
       {
            $(".Titulo").html("Adicionar Conta Banco");
            $('.ContaBanco').fadeOut();
            $('.ContaPag').fadeIn();
       }
       else
       {
            $(".Titulo").html("Adicionar Conta Pagamento");
             $('.ContaPag').fadeOut();
            $('.ContaBanco').fadeIn();
       }
   });
   
       $(".regConta").click(function (e)
        {
            var tipoConta = $("input[name='GestConta:contaForm:tipoConta']:checked").val();
            if(tipoConta ==='banco')
                contaBanco();
            else
                contaPagamento();
        });
        
    $("input:text, select").focus(function (e)
    {
       $(this).css("border",""); 
        $(".accountErrorInfo").css("display","");
    });
    
    $(".moreData").click(function(e)
    {
       e.preventDefault();
       $(".mp-infoTable").fadeOut();
    });
    
//    $('.icon-cancel-circle').click(function (){
//        $('.anular').show();
//    });
});

function registradoConta()
{
     $(".Modalframe").fadeOut(800);
     $(".registrarConta input:text, select").val("");
     $(".registrarConta input:text, select").css("border","");
}

function limparCamposConta()
{
    $(".registrarConta input:text, select").val("");
    $(".registrarConta input:text, select").css("border","");
}

function contaBanco()
{
    if($(".conta").val()==='')
      $(".conta").css("border","1px solid red");
   if($(".tipoContaM").val()==='')
      $(".tipoContaM").css("border","1px solid red");
   if($(".bancoConta").val()==='')
       $(".bancoConta").css("border","1px solid red");
   if($(".numConta").val()==='')
       $(".numConta").css("border","1px solid red");
   if($(".moedaConta").val()==='')
       $(".moedaConta").css("border","1px solid red");
 } 
 
 function contaPagamento()
 {
     if($(".designacaoConta").val()==='')
         $(".designacaoConta").css("border","1px solid red");
     if($(".numContaP").val()==='')
         $(".numContaP").css("border","1px solid red");
 }
 
function contaInicial()
{
    if($(".Titulo").html()==="Adicionar Conta Banco")
    {
        $('.ContaBanco').fadeOut();
        $('.ContaPag').fadeIn();
    }
    else
    {
        $('.ContaBanco').fadeOut();
        $('.ContaPag').fadeIn();
    }
}

function accountShowProcess()
{
    $(".modalProcess").show();
}

function accountHideProcess()
{
    $(".modalProcess").hide();
}

function accountInfo()
{
    $(".mp-infoTable").fadeIn();
}

function enviarDadosPesquisa(tipoPesquisa)
{    
    dadosPesquisa([{name:'campoPesquisa',value:$(".campoPesquisaConta").val()},  
           {name:'valorPesquisa', value:$(".valorPesquisaConta").val()},
           {name:'conta',value:$("input[name='accountTableForm:tipoContaS']:checked").val()},
           {name:'tipoPesquisa', value:tipoPesquisa}]);
}
function conta()
{    
    accountOperation([{name:'operation',value:tipoOperacao}]);  
}


function ocultarOcultarColunas()
{
    $(".contaMostarOcultarColunas").css("display","none");
}

function validePagamento()
{
    var valido = true;
    $(".ContaPagF").find("input, textarea").each(function ()
    {
        if($(this).val() === "" )
        { $(this).css("border","1px solid red"); valido=false; }
        else
        { $(this).css("border","");  }
    });
    if(valido)
    {
        //$('.modalPagamentoS').fadeOut();
        $(".pReg").keyup();
    }
}

function atualizarContaBanco(conta, tipoMovimento, banco, numConta, moeda )
{
    tipoOperacao ="editar";
    $(".Conta").css('display','none');
    $(".contaBancoConta").attr('disabled', true);
    $(".moedaConta").attr('disabled', true);
    $(".Titulo").html("Editar Conta Banco");
    $('.ContaBanco').fadeOut();
    $('.ContaPag').fadeIn();
   $(".contaBancoConta").val(conta); 
   $(".tipoContaM").val(tipoMovimento); 
   $(".bancoConta").val(banco); 
   $(".numConta").val(numConta); 
   $(".moedaConta").val(moeda); 
}

function atualizarContaPagamento(numConta, designacao )
{
     tipoOperacao ="editar";
    $(".Conta").css('display','none');
    $(".Titulo").html("Editar Conta Pagamento");
    $('.ContaPag').fadeOut();
    $('.ContaBanco').fadeIn();
    $(".numContaP").attr('disabled', true);
    $(".numContaP").val(numConta);
    $(".designacaoConta").val(designacao);
        
}


