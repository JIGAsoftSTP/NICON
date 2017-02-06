$(document).ready(function(e)
{
        $(".localTCredito").attr('disabled',true);
        $(".localidadeCredito").attr('disabled',true);
      $(".TransferenciaValor").keyup(function(e){
        e.preventDefault();
        var expre = /[^0-9]/g;
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if ($(this).val().match(expre))
            $(this).val($(this).val().replace(expre,''));
   });
       $(".numero2").keyup(function(e){
        e.preventDefault();
        var expre = /[^0-9]/g;
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if ($(this).val().match(expre))
            $(this).val($(this).val().replace(expre,''));
   });
   $(".movimentacaoValidar").click(function (e)
   {
       e.preventDefault();
 
        verificarCampos(); 
   });
   
    $( "input" ).on( "click", function() {       
       if ($(this).is(':checked')){
        if($(this).val() === 'SP') {
             $(".localTCredito").attr('disabled',true);
            $(".localidadeCredito").attr('disabled',true);   
         }
         else{
             $(".localTCredito").attr('disabled', false);
            $(".localidadeCredito").attr('disabled',false); 
         }
       }
   });
   $(".regSequenciaCheque").click(function (e)
   {
      e.preventDefault();
        regSequenciaCheque();
   });
           
});
  
 
 function verificarCampos()
 {
     var bancoD = $(".bancoD").val();
     var bancoC = $(".bancoC").val();
     var valorT = $(".TransferenciaValor").val();
     var descricao = $(".TransfDescricao").val();
     if(bancoD ==="")
     {
         $(".bancoD").css("border-color","red");
     }
     else
     {
         $(".bancoD").css("border-color","");
     }
      if(bancoC ==="")
     {
         $(".bancoC").css("border-color","red");
     }
     else
     {
         $(".bancoC").css("border-color","");
     }
      if(valorT ==="")
     {
         $(".TransferenciaValor").css("border-color","red");
     }
     else
     {
         $(".TransferenciaValor").css("border-color","");
     }
      if(descricao ==="")
     {
         $(".TransfDescricao").css("border-color","red");
     }
     else
     {
         $(".TransfDescricao").css("border-color","");
     }
 }
 
function Validar()
{
    var numeroDoc = $(".numeroDocumento").val();
    var valor = $(".valor").val();
    var banco = $(".bancoDebitoC").val();
    
    if(numeroDoc ==="")
    {
        $(".numeroDocumento").css("bordr-color","red");
    }
    else
    {
         $(".numeroDocumento").css("bordr-color","");
    }
    
    if(valor ==="")
    {
        $(".valor").css("bordr-color","red");
    }
    else
    {
         $(".valor").css("bordr-color","");
    }
    
    if(banco ==="")
    {
        $(".bancoDebitoC").css("bordr-color","red");
    }
    else
    {
         $(".bancoDebitoC").css("bordr-color","");
    }
}

function limparTransf()
{
    $(".bancoD").val("");
    $(".bancoC").val("");
    $(".TransferenciaValor").val("");
    $(".TransfDescricao").val("");
}

function limparDebitoCredito()
{
    $(".numeroDocumento").val("");
    $(".valor").val("");
    $(".bancoDebitoC").val("");
}

function regSequenciaCheque()
{
    testeVasio($(".seqTotal"));
    testeVasio($(".seqFin"));
    testeVasio($(".seqInicio"));
    testeVasio($(".seqAgencia"));
    testeVasio($(".seqBanco"));
}
function limparCamposSequencia()
{
    $(".seqTotal").val("");
    $(".seqFin").val("");
    $(".seqInicio").val("");
    $(".seqAgencia").val("");
    $(".seqBanco").val("");
}
function testeVasio(variavel)
{
    var teste=true;
    //alert(variavel.val());
    if(variavel.val()===""||variavel.val()==="xxx0xxx")
    {
        variavel.css("border","1px solid red");
        variavel.focus();
        teste=false;
    }
    else
    {
        variavel.css("border","");
    }
    
    return teste;
}
