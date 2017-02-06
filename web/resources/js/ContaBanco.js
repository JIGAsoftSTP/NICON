/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var Values = function (){};
Values.prototype.name = undefined;
Values.prototype.value = undefined;

$(function () {
    $(".regcb-button").click(function (){
        regCB();
    });
});

function regCB(){
   if(testValueRCB()){
        newCBSend();
   }
}

function testValueRCB(){
    var bool = true;
    $(".regcb-div").find("input:text, select").each(function (){
        if($(this).val() === "") {
            $(this).css('border', '1px solid red');
            bool = false;
            return false;
        }
        else { $(this).css('border', ''); }
    });
    return bool;
}

function newCBSend(){
    var paramTipoConta = new Values();
    paramTipoConta.name = "tipoconta";
    paramTipoConta.value = $(".regcb-tipoconta").val();
    
    var paramNumConta = new Values();
    paramNumConta.name = "numconta";
    paramNumConta.value = $(".regcb-numconta").val();
    
    var paramCantaContablilistica = new Values();
    paramCantaContablilistica.name = "contacontabalistica";
    paramCantaContablilistica.value = $(".regcb-contacontabalistica").val();
    
    var paramBanco = new Values();
    paramBanco.name = "banco";
    paramBanco.value = $(".regcb-banco").val();
    
    var paramTipoMoeda = new Values();
    paramTipoMoeda.name = "tipomoeda";
    paramTipoMoeda.value = $(".regcb-tipomoeda").val();
    
    var listParam = [paramTipoConta, paramNumConta, paramCantaContablilistica, paramBanco, paramTipoMoeda];
    
    $('.modalProcess').show();
    
    regContaBanco(listParam);
}

function limparCBForm(){
    $(".regcb-div").find("input:text, select").val("");
}

$(".typeDoc").click(function (){
    var docType = new Values();
    docType.name = "docType";
    docType.value = $(this).attr("type");
    $('.modalProcess').show();
    printDocCB([docType]);
});

$(".bt-pesqContaBanco").click(function (){
    var pesqCantaBanco = new Values();
    pesqCantaBanco.name = "pesq";
    pesqCantaBanco.value = $(".vl-pesqContaBanco").val();
    $('.modalProcess').show();
    pesqCB([pesqCantaBanco]);
});

$(".vl-pesqContaBanco").keypress(function (e){
    if(e.keyCode === 13) $(".bt-pesqContaBanco").click();
});
