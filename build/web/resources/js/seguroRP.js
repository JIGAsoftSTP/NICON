$(document).ready(function(e){
    respInitialState();
    $('.more-info [type=radio]').each( function (){
        var Label = $(this).next().text();
        if(Label.contains('Não') || Label.contains('Bom'))
            $(this).attr('checked' , true);
    });
     
     
     $('.ant-check [type=checkbox]').click(function ()
     {
           var textField = $(this).closest('nav').find('[type=text]');
             if($(this).is(':checked'))
                 textField.attr('disabled', false);
             else
             {
                  textField.attr('disabled', true);
                  textField.val("");
             }    
     });
     
     $('[type=radio]').click(function ()
     {
        var campo = $(this).closest('section').find('[type=text]');
        
     });
    $('.addItemTable').click(function(e)
    {
       e.preventDefault();
       $('.respInputtable [type=text]').each(function()
       {
           if($(this).val() ==='') 
               $(this).css('border','1px solid #dc3847');
       });
    });
    
    $('input').focus(function()
    {
       $(this).css('border',''); 
    });
    
    $('.respNext').click(function (e)
    {
       e.preventDefault();
        respCheckFields();
        respSendData();
    });
    
});

function respCheckFields() // function that verify if the required fields are empty
{
    $('.K-seguros [type=text]').each(function ()
    {
         if($(this).val()==='')
         {
              $(this).css('border','1px solid #dc3847');
              moveTop();
         }
    });
    if($('.respSubEmpreiteiros').val()==='')
         $('.respSubEmpreiteiros').css('border','1px solid #dc3847');
    if($('.respDiretor').val()==='')
        $('.respDiretor').css('border','1px solid #dc3847');
}


function respInitialState()
{
     $('.ant-check [type=checkbox]').each(function ()
     {
        var textField = $(this).closest('nav').find('[type=text]');
          if($(this).is(':checked'))
              textField.attr('disabled', false);
          else
              textField.attr('disabled', true);        
     });
 
}

function respSendData()
{
      var estadoE = $("input[name='responsabilidadeForm:respStateBuilding']:checked").val();
     respDados([{name:'numeroRegistro',value:$(".RespPublicaNumRegistro").val()},  
               {name:'apolice',value:$(".apoliceRespPublica").val()},
               {name:'moeda', value:$(".respMoeda").val()},
               {name:'incendioCoberturaCheckBox', value:(($(".respIncendioCob [type=checkbox]").is(':checked'))? 'true': "0")},
               {name:'incendioCobValor', value:$(".incendioCobValor").val()},
               {name:'respIntoxicacaoCob', value:(($('.respIntoxicacaoCob [type=checkbox]').is(':checked'))? 'true' : "0")},
               {name:'intoxicacaoCobValor', value:$(".intoxicacaoCobValor").val()},
               {name:'respOutroCob', value:(($('.respOutroCob [type=checkbox]').is(':checked'))? 'true':0)},
               {name:'outroCobDetalhes', value:$(".outroCobDetalhes").val()},
               {name:'respAcidenteValor', value:$(".respAcidenteValor").val()},
               {name:'respAcidenteTaxa', value:$(".respAcidenteTaxa").val()},
               {name:'respPeriodoValor', value:$(".respPeriodoValor").val()},
               {name:'respPeriodoTaxa', value:$(".respPeriodoTaxa").val()},
               {name:'respSubEmpreiteiros', value:$(".respSubEmpreiteiros").val()},
               {name:'respDiretor', value:$(".respDiretor").val()},
               {name:'respEstadoEdificio', value:estadoE},
               {name:'outroCobValor', value:$(".outroCobValor").val()}]);

}

function limiteIndenmizacaoResp()
{
    if($('.respAcidenteValor').val()==='')
        $('.respAcidenteValor').css('border','1px solid #dc3847');
    if($('.respAcidenteTaxa').val()==='')
        $('.respAcidenteTaxa').css('border','1px solid #dc3847');
    if($('.respPeriodoValor').val() ==='')
        $('.respPeriodoValor').css('border','1px solid #dc3847');
    if($('.respPeriodoTaxa').val() ==='')
        $('.respPeriodoTaxa').css('border','1px solid #dc3847');
}

function limparIndBorda()
{
    $('.limit [type=text]').css('border','');
}

function empregadoCampo()
{
    $('.rspEmpregadoCampo').focus();
}

function limparCamposTabela()
{
    $('.respLimpar').val("");
}

function respNumeroRegistroInvalido()
{
    $('.RespPublicaNumRegistro').css('border','1px solid #dc3847');
}

function outrasInfoDown()
{
    var valido = true;
    $('.otherInfoResp').each(function ()
    {
        if($(this).is(':disabled')===false)
        {
            if($(this).val()==='')
            {
                valido = false;
                $(this).css('border','1px solid #dc3847');
            }
        }
        else
            $(this).val("");
    });
    console.log("valido "+valido);
    if(valido === true)
         window.location.href ="GestSeg_NovoSeguroApolice.xhtml";
}