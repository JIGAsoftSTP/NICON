var dataInicio = "input:text[name='resseguroForm:resseguroDataI_input']";
var dataFim = "input:text[name='resseguroForm:resseguroDataF_input']";

$(document).ready(function (e)
{
    $('.btSaveRess').css('display', 'none');
   
        $(".btPreviousRess").click(function()
        {             
           $(this).addClass('hideRess');
           $(".btNextRess").removeClass('hideRess');
           $(".btSaveRess").addClass('hideRess');
          setTimeout(function(){
                $("#div1").removeClass('hideRess');
                $("#div2").addClass('hideRess'); 
              }, 500);  
          $('.resseguroProximo').css('display','');
       });
        
    $('.premioGrosso').keyup(function ()
    {
         $('.valorDeducao').trigger('keyup');
    });
    $('.resseguroAdicionar').click(function (e)
    {
        e.preventDefault();
        if($('.resseguroEmpresa').val() === '')
            $('.resseguroEmpresa').css('border', '1px solid red');
    });
    $('.premioGrosso').blur(function ()
    {
        if($(this).val() !== '')
        {
            if(Number($(this).val()) === 0)
            {
                $(this).attr('title', 'Valor deve ser superior a zero(0)!');
                $(this).css('border','1px solid red');
            }
            else
                $(this).attr('title', '');
        }
    });
    $('.resseguroPercentagem').blur(function ()
    {
        if($(this).val() !== '')
        {
            if(Number($(this).val()) === 0)
            {
                $(this).attr('title', 'Valor deve ser superior a zero(0)!');
                $(this).css('border','1px solid red');
            }
            else
                $(this).attr('title', '');
        }
    });
    $('.valorDeducao').blur(function ()
    {
        if($(this).val() !== '')
        {
            if(Number($(this).val()) === 0)
            {
                $(this).attr('title', 'Valor deve ser superior a zero(0)!');
                $(this).css('border','1px solid red');
            }
            else
                $(this).attr('title', '');
        }
    });
    $('input, select').focus(function ()
    {
        $(this).css('border', '');
    });
    $('.descricaoResseguro').focus(function ()
    {
        $(this).css('border', '');
    });
    $('.resseguroProximo').click(function (e)
    {
        e.preventDefault();
        resseguroFirstPart();  

    });
});



function resseguroNext()
{
    $(".btPreviousRess").removeClass('hideRess');
   $(".btSaveRess").removeClass('hideRess');
    $(this).addClass('hideRess');

  setTimeout(function(){
      $("#div1").addClass('hideRess');
      $("#div2").removeClass('hideRess');
  }, 500);  
}
function resseguroFirstPart()
{
    var valido = true;
    if($(dataInicio).val() === '')
    {
        $(dataInicio).css('border','1px solid red');
        valido = false;
    }
    if($(".resseguroTotal").val() === "")
        valido = false;
    if($(dataFim).val() === '')
    {
        $(dataFim).css('border','1px solid red');
        valido = false;
    }  
    
    $('.resseguroCampo').each(function ()
    {
       if($(this).val() === '')
       {
           $(this).css('border', '1px solid red');
            valido = false;
       }
    });
    
    if(valido === true)
        sendDataRes();
}


function sendDataRes()
{
    var dataI = "input:text[name='resseguroForm:resseguroDataI_input']";
    var dataF = "input:text[name='resseguroForm:resseguroDataF_input']";
    RegResseguro([{name:'tipo',value:$(".resseguroTipoFA").val()},  
               {name:'data inicio',value:$(dataI).val()},
               {name:'data fim',value:$(dataF).val()},
               {name:'limite de responsabilidade', value:$(".resseguroLimiteResp").val()},
               {name:'apolice', value:$(".resseguroApolice").val()},
               {name:'cobertura', value:$(".resseguroTipoCobertura").val()},
               {name:'descrição', value:$(".descricaoResseguro").val()},
               {name:'premio', value:$(".resseguroPremioGrosso").val()},
               {name:'cliente', value:$(".resseguroCliente").val()},
               {name:'tipo seguro', value:$(".resseguroTipoSeguro").val()},
               {name:'moeda', value:$(".resseguroMoeda").val()},
               {name:'nota debito', value:$(".resseguroNotaDebito").val()}]);
 
}
function dataFimFoco()
{
    $(dataFim).focus();
}

function resseguroRegistrado()
{
    $(dataInicio).val('');
    $(dataFim).val('');      
    $('.resseguroCampo').val('');
    $('.resseguroCampo').val('');
    $('.resseguroTotal').val('');
    $('.resseguroParte2').val('');
}


function resseguroExcel (){
    resseguroExportDOC([{name:'tipo', value: "2"}]);
    $('.modalProcess').show();
}

function resseguroPdf(){
    resseguroExportDOC([{name:'tipo', value : "1"}]);
    $('.modalProcess').show();
}
