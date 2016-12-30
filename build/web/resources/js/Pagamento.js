
var contaPagamento = "input:text[name='recebimentoForm:pagamentoContaPgto_input']";
var dataPagamento = "input:text[name='recebimentoForm:pagamentoData_input']";
var pagamentoSolicitado = "";
var idPayment = "";
$(document).ready(function (e)
{
        
    $(".tipoPagamento").val("2");
    
    $(".novoPagamento").click(function(e)
    {
         pagamentoSolicitado = "N";
    });
    $('.anularPagamentoBotao').click(function(e)
    {
       e.preventDefault();
       if($('.obsAnular').val() === "")
           $('.obsAnular').focus();
       else
           $('.modalProcess').show();
    });
       

    $(".hide-new-receivent").bind("click",receiveCloseFrame);
    
      $(".moreData").click(function (e)
    {
       e.preventDefault();
       $(".mp-infoTable").fadeOut();
    });
    
     $(".numeroAC").keyup(function(e)
    {
        e.preventDefault();
        if (!$.isNumeric($(this).val()))
            $(this).val("");
   });

  
   $(".pagamentoFechar").click(function (e)
   {
      e.preventDefault();
      $(".mp-messages").fadeOut();
   });
 
   $(".numeroInterio").keyup(function (e)
    {
        e.preventDefault();
                var expre = /[^0-9]/g;
                // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
                if ($(this).val().match(expre))
                        $(this).val($(this).val().replace(expre, ''));
    });
    $(".pagamentoForma").change(function (e)
    {
       e.preventDefault();
       if($(this).val()==="1")
       {
           $(".pagamentoNumero").attr("disabled", true);
           $(".pagamentoNumero").val("");
           $(".pagamentoNumero").css("border","");
       }
       else
            $(".pagamentoNumero").attr("disabled", false);
    });
    $(".prestacaoFormaPagamento").change(function (e)
    {
       e.preventDefault();
       if($(this).val()==='1')
       {
            $(".prestacaoNumDoc").attr("disabled", true);
            $(".prestacaoNumDoc").css("border","");          
            $(".prestacaoNumDoc").val("");          
       }
       else
           $(".prestacaoNumDoc").attr("disabled", false); 
    });
    
    $(".concluirRecebimento").click(function(e)
    {
        e.preventDefault();
        concluirAmortizacao(); 
    });
    
    $(".tipoPagamento").change(function(e)
    {
        if($(this).val()==="1")
        {
            $(".pagamentoForma").val(1);
            $(".pagamentoForma").attr("disabled", true);
            $(".pagamentoForma").css("border", "");
            $(".pagamentoNumero").attr("disabled", true);
            $(".pagamentoNumero").css("border", "");
            $(".pagamentoContaBanco").css("border", "");
            $(".pagamentoValor").attr("title","Valor total não pode exceder 1000000");
        }
        else
        {
            $(".pagamentoValor").attr("title","");
            $(".pagamentoForma").val("");
            $(".pagamentoForma").attr("disabled", false);
            $(".pagamentoNumero").attr("disabled", false);
            $(".pagamentoNumero").val("");
            $(".pagamentoContaBanco").val("");
            $(".pagamentoContaBanco").attr("disabled",false);
        }
    });
    
    $(".pagamentoDesc").focus(function (e)
    {
       $(this).css("border",""); 
    });
    $("input:text, select").focus(function(e)
    {
       $(this).css("border",""); 
    });
    $(".pagamentoContaBanco").focus(function(e)
    {
       $(this).css("border",""); 
    });
    
    $(".paymentAddTable").click(function (e)
    {
       e.preventDefault();
        if(pagamentoAdicionarTabela()=== true)
            pagamentoEnviarDados();
    });
    
    $(".finishPayment").click(function (e)
    {
        e.preventDefault();
        if(concluirPagamento() === true)
        {
            $(".modalProcess").show();
            dadosPagamentoRegistrar();
        }
    });
    
});


function registradoAmortizacao()
{
    $(".prestacaoLimpar").val("");
    $(".prestacaoNumDoc").attr("disabled", false);
}

function recebimentoMaisInfo()
{
    $(".mp-infoTable").fadeIn();
}

function recebimentoMostrarProcess()
{
    $(".modalProcess").show();
}

function recebimentoOcultarProcess()
{
    $(".modalProcess").hide();
}

function mostrarFrameRecebimento()
{
    $(".newReceivent").css("right","0");
    $(".newReceivent").css("transition","all .5s ease");
}

function concluirAmortizacao()
{
    var numDocEstado = $(".prestacaoNumDoc").is(":disabled");
    if(numDocEstado === false)
    {
        if($(".prestacaoNumDoc").val()==="")
            $(".prestacaoNumDoc").css("border","1px solid red");
    }
    if($(".prestacaoFormaPagamento").val()==="")
        $(".prestacaoFormaPagamento").css("border","1px solid red");

    if($(".prestacaoConta").val()==="")
        $(".prestacaoConta").css("border","1px solid red");
}

function receiveCloseFrame()
{
    $(".newReceivent").css("right"," -50");
    $(".newReceivent").css("transition","all .5s ease");
}

function pagamentoAdicionarTabela()
{
    var valido = true;
    var contaP = $(contaPagamento).val();
    if($(".tipoPagamento").val()==="2")
    {
        if($(".pagamentoNumDoc").val()==='')
        {
            valido = false;
            $(".pagamentoNumDoc").css("border","1px solid red");
        }
        if($(".pagamentoBeneficiario").val()==="")
        {
             valido = false;
            $(".pagamentoBeneficiario").css("border","1px solid red");
        }
        if($(".pagamentoDesc").val()==="")
        {
             valido = false;
            $(".pagamentoDesc").css("border","1px solid red");
        }
        if($(".pagamentoValor").val()==="")
        {
             valido = false;
            $(".pagamentoValor").css("border","1px solid red");
        }
        if(contaP ==="")
        {
             valido = false;
            $(contaPagamento).focus();
        }
   
    }
    else
    {
        if($(".pagamentoNumDoc").val()==='')
        {
            valido = false;
            $(".pagamentoNumDoc").css("border","1px solid red");
        }
        if($(".pagamentoDesc").val()==="")
        {
             valido = false;
            $(".pagamentoDesc").css("border","1px solid red");
        }
        if($(".pagamentoValor").val()==="")
        {
             valido = false;
            $(".pagamentoValor").css("border","1px solid red");
        }
        if(contaP ==="")
        {
             valido = false;
            $(contaPagamento).focus();
        }
    }
    return valido;
    
}

function pagamentoEnviarDados()
{
 
    pagamentoInfoTable([{name:'numDoc', value:$(".pagamentoNumDoc").val()},
                       {name:'beneficiário',value: $(".pagamentoBeneficiario").val()} ,
                       {name:'tipoPagamento',value: $(".tipoPagamento").val()} ,
                        {name:'contaPagamento', value:$(contaPagamento).val()},
                        {name:'pagamentoValor', value:$(".pagamentoValor").val()},
                        {name:'pagamentoDesc', value:$(".pagamentoDesc").val()}]);
}

function dadosPagamentoRegistrar()
{
       console.log("entrou pagamento");
    regPagamento([{name:'tipoPagamento',value: $(".tipoPagamento").val()} ,
                    {name:'contaBanco',value: $(".pagamentoContaBanco").val()} ,
                    {name:'formaPagamento',value: $(".pagamentoForma").val()} ,
                     {name:'dataPagamento', value:$(dataPagamento).val()},
                     {name:'numero', value:$(".pagamentoNumero").val()},
                     {name:'ID REQUISICAO PAGAMENTO', value:idPayment},
                     {name:'pagamentoSolicitado', value:pagamentoSolicitado}]);
}

function limparAdicionar()
{
    $(contaPagamento).val("");
    $(".pagamentoLimparTabela").val("");
}

function pagamentoMoreInfo(numDoc,beneficiario, contaPagamento, valor, descricao)
{

    $(".numeroDocPagamento").val(numDoc);
    $(".benefagamento").val(beneficiario);
    $(".contaPayment").val(contaPagamento);
    $(".valorPagamento").val(valor);
    $(".descPagamento").val(descricao);
}

function concluirPagamento()
{
    var valido = true;
    if($(".pagamentoNumero").is(":disabled")===false)
    {
        if($(".pagamentoNumero").val()==="")
        {
            $(".pagamentoNumero").css("border","1px solid red");
            valido = false;
        }
    }
    if($(".pagamentoContaBanco").is(":disabled")===false)
    {
        if($(".pagamentoContaBanco").val()==="")
        {
            $(".pagamentoContaBanco").css("border","1px solid red");
            valido = false;
        }
    }
    if($(".pagamentoForma").is(":disabled")===false)
    {
        if($(".pagamentoForma").val()==="")
        {
            $(".pagamentoForma").css("border","1px solid red");
            valido = false;
        }
    }
    if($(dataPagamento).val() ==="")
    {
        $(dataPagamento).focus();
        valido = false;
    }
    return valido;
    
}

function pagamentoValorSuperior()
{
    $(".pagamentoValor").focus();
}

function nextPaymentCod(codigo)
{
    $(".pagamentoNumero").val(codigo);
}

function pagamentoEfetuado()
{
    $(dataPagamento).val("");
    $(contaPagamento).val("");
    $(".pagamentoLimpar").val("");
    $(".pagamentoLimpar").attr("disabled", false);
    $(".pagamentoLimpar").css("border","");
    $(".mp-messages").show($(".mp-messages").slideDown(400));
    
}

function contaPagamentoInvalido()
{
    $(contaPagamento).focus();
}

function contaBancoFoco()
{
    $(".pagamentoContaBanco").focus();
}


function pagamentoNumeroDocumentoFoco()
{
    $(".pagamentoNumDoc").focus();
}


function loadCheque(numero)
{
    $(".pagamentoNumero").val(numero);
}

function tituloConta(descricao)
{
    $(".prestacaoConta").attr('title',descricao);
}

function ativarDesativarCampoCheque(estado)
{
    if(estado ==="ativar")
    {
        $('.pagamentoNumero').attr("disabled", false);
        $('.finishPayment').attr("disabled", false);
    }
    else
    {
        $('.finishPayment').attr("disabled", true);
        $('.pagamentoNumero').attr("disabled", true); 
    }
    
}
function ativarDesativarBotaoPagamento(estado)
{
    if(estado ==="ativar")
        $('.finishPayment').attr("disabled", false);
    else
        $('.finishPayment').attr("disabled", true);
}



function solicitarPagamento(beneficiario, valor, dataPagamento, idPagamento)
{
    pagamentoSolicitado ="Y";
    idPayment = idPagamento;
    var datePayment= "input:text[name='recebimentoForm:pagamentoData_input']";
    $(".pagamentoBeneficiario").val(((beneficiario ==="null") ? "" : beneficiario));
    $(".pagamentoValor").val(valor);
    $(datePayment).val(dataPagamento);
    
    var paginaAtual = window.location.href;
    var contem ="Pgtos_Rcbtos.xhtml";
     console.log("efetuar pagamento inicio");
    if(paginaAtual.indexOf(contem) !==-1)
    {
        var selected = $('.newPaymentCheckBox').is(":checked");
        if(selected === false)
        {
            $(".newPaymentCheckBox").attr("checked", true);
            $(".hide-new-payment").trigger("click");
        }
    } 
     console.log("efetuar pagamento abrir");
}

function contaPagamentoValor(valor)
{
    $(contaPagamento).val(valor);
}
