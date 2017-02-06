/* 
 *Created by Grinalda Soares
 */

$(function() {
       
     $(".icon-search").click(function (e) {
         $(".labelTrigger").trigger("click");
    });
    $(".contaNum").attr("disabled", true);
    $(".addAccount").click(function () {
        regAccount();
    });
    $(".regOpA").click(function()
    {
        if($(".accountTypeOp").val() !== "" && $(".accountOperationValue").val() !== "")
            $('.modalProcess').show();
    });
   
    $(".contaNum").focus(function (){
       if($(".contaRaiz").val() !== "-1" && numConta === undefined)      
            numConta = $(this).val(); 
    });
    //Fechar Modal Relatório
    $(".iconRight").click(function (e)
    {
      $('.areaModal').addClass('hide');
    });
    
    $(".addAccount").click(function () {
        regAccount(); 
    });
    
    $(".contaNum").keyup(function (e){
        newLength = numConta.length+1;
         $(this).attr("maxlength", newLength);
        if(e.which === 8)
            $(".contaNum").val(numConta);
       
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

    $(".closeSpanTable").click(function () {
        $(".areaTableOperações").addClass("bottonn");
    });

    $(".abrir").click(function () {
       $(".areaModal").removeClass("hide");
    });
    
     $(".conta").click(function () {
       $(".RegConta").removeClass("hide");
    });
    $(".MultipleSelectInput").click(function() {
       if($(".operationInsurance").attr("id") !== undefined && $(".accountTypeOp").val() !== "")
           $(".MultipleSelectDisplay").removeClass("out");
       
    });
    
    $(".selectBoxes").focusout(function () {
        $(".MultipleSelectDisplay").addClass("out");
     
    });
    
    $(".openOperacao").click(function () {
       $(".areaTableOperações").removeClass("bottonn");
//       $(".areaTableOperações").fadeIn(300);
       
   });
 });

    var numConta = undefined
    , newLength = 0;

function desdobrarConta(){
    $('.contaDesig').val('');
    $('h3').html('Desdobramento da Conta');
    $(".contaRaiz").attr("disabled", true);
}


function regAccount(){
    if($(".contaRaiz").val() ==="-1")
        $(".contaRaiz").focus();
    else{
        if(newLength !== $(".contaNum").val().length)
            $(".contaNum").focus();
        else{
            if($(".contaDesig").val() === "")
                $(".contaDesig").focus();
        }
    }
}  

function activeNumberAccount()
{
    $(".contaNum").attr("disabled", false);
    numConta = undefined;
}

function accountRegistered(){
     $('h3').html('Novo Registo da Conta');
    $(".contaRaiz").attr("disabled", false);
    $(".accountField").val("");
    $(".accountFieldLabel").html("");
    $(".contaRaiz").val("-1");
    numConta = undefined;
    newLength = 0;
}

function updateAccount(op){
    if(op === 1){
        $('.RegConta h3').html('Atualizar Conta');
            $(".contaNum").attr("disabled", true);
        $(".contaRaiz").attr("disabled", true);
         $('.RegConta').removeClass('hide');
    }
    else
        accountRegistered();
}

function operationRegistry()
{
    $(".operacao h3").html("Operações");
    $(".operacao").removeClass("hide");
}

function accountOperationRegistered()
{
    $(".accountTypeOp").val("");
    $(".accountOperationValue").val("");
    $(".operacao").addClass("hide");
    
}
