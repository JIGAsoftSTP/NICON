
/* global Integer */

$(document).ready(function(e){
   
    $(".apoliceNumero").keyup(function(e)
    {
        e.preventDefault();
        if(!$.isNumeric($(this).val()))
            $(this).val("");
   });
  $(".apoliceConcluir").click(function(e)
   {
        e.preventDefault();
        var taxa = $(".apoliceTaxa").val();
        var seguro = $(".seguroSelecionado").html();
       
        verificarCampos();
       
   });
   $(".okError").click(function (e)
   {
       e.preventDefault();
       $(".apoliceMensagem").fadeOut();
   });
  $(".numeroAC").keyup(function(e){
        e.preventDefault();
        var expre = /[^0-9]/g;
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if ($(this).val().match(expre))
            $(this).val($(this).val().replace(expre,''));
   });
   });
    $(".apoliceNumero").keyup(function(e)
    {
        e.preventDefault();
        if(!$.isNumeric($(this).val()))
            $(this).val("");
   });


function verificarCampos()
{
    var i = 0;
    var dataInicial = "input:text[name='contrato:ApoliceDataContrato_input']";
    var dataFinal = "input:text[name='contrato:ApoliceDataF_input']";
    var dataContrato = "input:text[name='contrato:ApolicePeridoContrato_input']";
        
        if($(dataInicial).val()==='')
        {
            $(dataInicial).css("border","1px solid red");
            i =1;
        }
        else
             $(dataInicial).css("border","");
        if($(dataFinal).val()==='')
        {
            i =1;
            $(dataFinal).css("border","1px solid red");
        } 
        else
             $(dataFinal).css("border","");
        if($(dataContrato).val()==='')
        {
            i =1;
            $(dataContrato).css("border","1px solid red");
        }
        else
        {
             $(dataContrato).css("border","");
        }
        
         return i;
}
   
  function dataFinalInvalido()
  {
      if($(".seguroSelecionado").html()!=='TIN')
        {
            $(".apolicePeriodoFim").attr('title','Data final tem que ser superior a data inicial');
            $(".apolicePeriodoFim").css("border","1px solid red");
        }
      
  }
   function dataFinalValido()
  {
       if($(".seguroSelecionado").html()!=='TIN')
        {
            $(".apolicePeriodoFim").attr('title','');
            $(".apolicePeriodoFim").css("border","");
        }
  }
  function dataContratoValido()
  {
       if($(".seguroSelecionado").html()!=='TIN')
        {
            $(".apoliceInicio").attr('title','');
           $(".apoliceInicio").css("border",""); 
       }
  }
  
    function dataContratoInvalido()
  {
       if($(".seguroSelecionado").html()!=='TIN')
        {
            $(".apoliceInicio").attr('title','Data de contrato tem que ser menor que a data inicial');
           $(".apoliceInicio").css("border","1px solid red"); 
       }
  }
  
function mostrarP1()
{
    if(verificarCampos() ===0)
    {
        $(".processamento").show();
         $("#ModPag").attr("disabled","disabled");
    }
}

function verificarTaxa()
{
    var taxa = $(".apoliceTaxa").val();
    if(taxa==='')
        $(".apoliceTaxa").css("border","1px solid red");
    else
         $(".apoliceTaxa").css("border","");
}

function modalInfoError()
{
    $(".apoliceMensagem").fadeIn();
}
function dataContratoFoco()
{
    var dataContrato = "input:text[name='contrato:ApolicePeridoContrato_input']";
    $(dataContrato).focus();
}
function dataRenovacaoFoco()
{
     var dataRenovacao = "input:text[name='contrato:dataRenovacao_input']";
    $(dataRenovacao).focus();
}
function dataFimFoco()
{
     var dataFinal = "input:text[name='contrato:ApoliceDataF_input']";
    $(dataFinal).focus();
}
