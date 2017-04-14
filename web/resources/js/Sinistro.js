/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var validoS = true;
var dadosContratoSegurado = "Dados do Contrato";
$(document).ready(function (){
   
    validarHipoteca();
    validarTestemunha();
    $(".painelGroupSinistro").hide();
    $(".FirstSin").show();
    
    $(".menuSin nav").click(function ()
    {
        if($(this).html() ==="Dados do Contrato")
        {
            dadosContratoSegurado = $(this).html();
            $(".painelGroupSinistro").hide();
            $(".FirstSin").show();
        }
        else if($(this).html() ==="Dados do Segurado")
        {
            $(".FirstSin").hide();
            dadosContratoSegurado = $(this).html();
        }
         dadosSinistro([{name:'typeInfo',value:$(this).html()}]);   
    });
  
    $('.OpenModalSin').click(function (){
        $('.modalFrameSin').css('display','flex');
    });
    $('.CloseModalSin').click(function (){
        $('.modalFrameSin').fadeToggle();
    });
    
    $(".btSaveSinistro").click(validarTudo);
    
//    $("input:text[id='table:sPesquisa']").keyup(pesquisar);
    
    $(".sHora").mask("99:99");
    
    
    $('.menuSin').find('.nav1').click(function(){
        $('.menuSin').find('nav').removeClass('activo');
        $(this).addClass('activo');
        $('.areaDivs').find('div').addClass('inactive');
        $('.areaDivs').find('.FirstSin').removeClass('inactive');
    });
    
    $('.menuSin').find('.nav2').click(function(){
        $('.menuSin').find('nav').removeClass('activo');
        $(this).addClass('activo');   
        $('.areaDivs').find('div').addClass('inactive');
        $('.areaDivs').find('.SecondSin').removeClass('inactive');
    });
    
    $('.menuSin').find('.nav3').click(function(){
        $('.menuSin').find('nav').removeClass('activo');
        $(this).addClass('activo');   
        $('.areaDivs').find('div').addClass('inactive');
        $('.areaDivs').find('.terceiroSin').removeClass('inactive');
    });
});

function validarHipoteca()
{
//    alert($("input:radio[name='sinistro:sSetHipotese']").val());
    if($("input:radio[name='sinistro:sSetHipotese']:checked").val() === "false")
    {
        $(".areaNomeInteresado").find("input:text").attr("disabled","disabled");
        $(".btAddSinistro").attr("disabled","disabled");
    }
    else
    {
        $(".areaNomeInteresado").find("input:text").removeAttr("disabled");
        $(".btAddSinistro").removeAttr("disabled");
    }
}

function validarTestemunha()
{
    if($("input:radio[name='sinistro:sSetTestemunha']:checked").val() === "false")
    {
        $(".areaNome").find("input:text").attr("disabled","disabled");
    }
    else
    {
        $(".areaNome").find("input:text").removeAttr("disabled");
    }     
}

function validarTudo()
{
    validoS = true;
    $(".validatorSinistro").each(function (e)
    {
        $(this).find("input:text , textArea").each(function (e)
        {
            if($(this).val()==="" && $(this).attr("readonly") !== "readonly" && $(this).attr("disabled") !== "disabled" 
            || ((($(this).attr("id")==="sinistro:sApolice" || $(this).attr("id")==="sinistro:sSeguro")) && $(this).val()===""))
            {
                if($(this).attr("id")!=="sinistro:sHEndereco" && $(this).attr("id")!=="sinistro:sHNomeIntere")
                {
                    $(this).css("border","red solid 1px");
                    
                    if($(this).attr("id")==="sinistro:sApolice")
                    { $(".mesagemLabel").click(); }
                    validoS = false;
                } 
            }
            else
            {  $(this).css("border",""); }
        });
    });
    
    if($("tbody[id='sinistro:tableHipoteca_data']").find("tr").attr("data-ri")!=="0"
    && $("input:radio[name='sinistro:sSetHipotese']:checked").val() === "true")
    { 
        $(".mesagemLabel").dblclick();
        validoS = false;
    }
    
    valideHoraSinistro();
    
    if(validoS)
        $(".mesagemLabel").keyup();
    
//        data-ri
}

function testeHipoteca()
{
    $(".areaNomeInteresado").find("input:text").each(function ()
    {
        if( $(this).val()==="" )
        { $(this).css("border","red solid 1px"); }
    });
}

function limparDadosSinistro()
{
    $(".validatorSinistro").find("input:text , textArea").val("");
}

function valideHoraSinistro()
{
    if($(".sHora").val() !== null)
    {
        var h = $(".sHora").val().split(":");
        h = $.makeArray(h);
        if(( !$.isNumeric(h[0]) || !$.isNumeric(h[1])) || (Number(h[0])>23 || Number(h[1])>59))
        { 
            $(".sHora").css("border","red solid 1px");
            $(".sHora").attr("title","Hora Invalida!");
            validoS = false;
        }
        else
        { $(".sHora").removeAttr("title"); }
    }
}

function mostarInfoSinistro()
{
     $(".menuSin nav").trigger("click");
    $(".sinistroMaisInfo").fadeIn();

}

function sinistroDadosContrato(apolice, dataInicio, dataFim,cliente, valorSegurado,premioBruto,valorExcesso,estadoP, estadoContrato, moeda)
{
    $(".moreInfoSinistroApolice").html(apolice);
    $(".moreInfoSinistroDataInicio").html(dataInicio);
    $(".moreInfoSinistroDataFim").html(dataFim);
    $(".moreInfoSinistroCliente").html(cliente);
    $(".moreInfoSinistroValorSegurado").html(valorSegurado);
    $(".moreInfoSinistroPremioBruto").html(premioBruto);
    $(".moreInfoSinistroValorExcesso").html(valorExcesso);
    $(".moreInfoSinistroEstadoPagamento").html(estadoP);
    $(".moreInfoSinistroEstadoContrato").html(estadoContrato);
    $(".moreInfoSinistroMoeda").html(moeda);
}

function valideDesativarSinistro()
{
    if($(".justText").val() === "")
    { $(".justText").css("border","red solid 1px"); }
    else
    {
        $(".justText").css("border","");
        $(".btCancelJust").keyup();
    }
}

function sinistroRelatorio(seguro, testemunha)
{
    if(testemunha === 'true')
        $(".sinistroTestemunha").show();
    else
        $(".sinistroTestemunha").hide();
    if(seguro === "Seguro de Automóvel")
        $(".sinistroVeiculo").show();
    else  
        $(".sinistroVeiculo").hide();
}

function  validePagamento(){
    var entrou = false;
   $(".ContaPagF").find("input:text , textArea").each(function (e){
        if( $(this).val()==="" )
        { 
            $(this).css("border","red solid 1px");
            entrou = true;
        }
    });
    if(!entrou){
        $('.processamento').show();
        $(".pReg").keyup();
    }
}
