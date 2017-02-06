var realNumCheque = "";
var valid;
var dataLancamento = "input:text[name='movimentacao:dataLancamento_input']";
var dataDocumento = "input:text[name='movimentacao:dataEDocumento_input']";
var launchAccount = "input:text[name='movimentacao:launchAccount_input']";

$(document).ready(function (e){
   
    $(".closeModalFrame").click(function()
    {
      $(".modalCreditInfo").fadeOut();

    });
 
    $(".btAddtoTable").click(function(e)
    {
        e.preventDefault();
        fieldsNewLaunch();
    });
    
     $(".list").on('click','article',function () {
         $('.inConta').val($(this).closest("article").find("p:eq(0)").text());
         $('.accountDesc').val($(this).closest("article").find("p:eq(1)").text());
    });
    $(".launchFieldSearch").keyup(function (e) {
         $(".launchSearchList").trigger("click"); 
    });

    $("input:text, select").focus(function()
    {
       $(this).css("border", ""); 
    });
    $(".newCheck").click(function ()
    {
        checkFields("registered");
    });
    
    $(".checkSearchIcon").click(function ()
    {
       $(".checkSearch").trigger("keyup"); 
    });
    $(".checkRegButton").click(function(e)
    {
       e.preventDefault();
        checkFields("register");
        if(valid === true)
            $(this).attr("disabled", true);
    });
  $('.menuMovm section a').click( function(){
      $('.menuMovm section a').removeClass('active');
      $(this).addClass('active');
  });
  
  $('.MN-Cheque').click(function (){
      $('.DV-lancamento').removeClass('ativo');
      $('.DV-cheque').addClass('ativo');
  });
   $('.MN-Lancamento').click(function (){
      $('.DV-lancamento').addClass('ativo');
      $('.DV-cheque').removeClass('ativo');
  });
  
    $('.filtroMov label').click( function(){
      $('.filtroMov label').removeClass('active').find('i').remove();
      $(this).addClass('active').prepend('<i class="icon-checkmark"></i>');
  });
  
  $('#btcheq').click(function(){
      $('#cheq').addClass('aparecer');
  });
  $('#btlanc').click(function(){
      $('#lanc').addClass('aparecer');
  });
   $('#iconLanc, .btCancel').click(function(){
      $('#lanc').removeClass('aparecer');
        return false;
  });
  $('#IconCheq').click(function(){
      $('#cheq').removeClass('aparecer');
  });
  
  
   $('.inConta').focus(function (){
      $('.drop').addClass('in');
   });
   $('.inConta').focusout(function (){
      $('.drop').removeClass('in');
   });
   
   
    $('.seeTable').click(function (){
        $('.areaTableConta').toggleClass('yes');
    });
   
    $(".1").click(function ()
    {
        testeVasio($(".descMo"));
        testeVasio($(".valorMo"));
        testeVasio($(".paraMo"));
        testeVasio($(".deMo"));
    });
    $(".2").click(function ()
    {
        testeVasio($(".contaDC"));
        testeVasio($(".valorDC"));
        testeVasio($(".numDDC"));
        testeVasioRadio($(".debit label"),$("input[name='movimentacao:tipDC']"));
    });
    $(".3").click(function ()
    {
        testeVasio($(".totalCh"));
        testeVasio($(".fimCh"));
        testeVasio($(".incioCh"));
        testeVasio($(".bancoCh"));
    });
    

    $('.formatNumber').keyup(function ()
    {
        formatted($(this));

    });
       $('.double').keypress(function (e) {

        //$( ".integer" ).trigger("keypress ");
          console.log("entrou 2");
        if ((e.which !== 44 || $(this).val().indexOf(',') !== -1) &&
                ((e.which < 48 || e.which > 57) &&
                        (e.which !== 0 && e.which !== 8))) {
            e.preDefault();
        }

        var text = $(this).val();

        if
                ((text.indexOf(',') !== -1) &&
                        (text.substring(text.indexOf(',')).length > 2) &&
                        (e.which !== 0 && e.which !== 8) &&
                        ($(this)[0].selectionStart >= text.length - 2)) {
            e.preventDefault();
        }
    });
    
    $(".CalTotalCheque").keyup(function ()
    {
        console.log("entrou");
        if ($(".checkEnd").val().length === $(".checkStart").val().length)
        {
            if (Number($(".checkEnd").val()) > Number($(".checkStart").val()))
            {
                var t = Number($(".checkEnd").val()) - Number($(".checkStart").val());
//                var to = $(".fimCh").val().length - (t + "").length;
                realNumCheque = t+1;
//                for (i = 0; i < to; i++)
//                {realNumCheque = "0" + realNumCheque;}
                soCor($(".checkStart"), "");
                soCor($(".checkEnd"), "");
            } else
            {
                realNumCheque = "";
                soCor($(".checkStart"), "#D3813C");
                soCor($(".checkEnd"), "#D3813C");
            }
        } else
        {
            realNumCheque = "";
            soCor($(".checkStart"), "#D3813C");
            soCor($(".checkEnd"), "#D3813C");
        }
        $(".totalCh").val(realNumCheque);
    });
});
var listFiltered = [];
 var list;
function testeVasio(variavel)
{
    var teste=true;
    //alert(variavel.val());
    if(variavel.val()==="")
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
function testeVasioRadio(variavel,valor)
{
    var teste=false;
    valor.each(function() 
    {
       if ($(this).is(':checked'))
       {teste=true;}
    });
    if(!teste)
    {
        variavel.css("color","red");
        variavel.css("font-weight","lighter");
        teste=false;
    }
    else
    {
        variavel.css("font-weight","bold");
        variavel.css("color","");
    }
    
    return teste;
}
function limparAllMov(val)
{
    if(val==="1")
    {
        limparCampo($(".descMo"));
        limparCampo($(".valorMo"));
        limparCampo($(".paraMo"));
        limparCampo($(".deMo"));
    }
    if(val==="2")
    {
        limparCampo($(".contaDC"));
        limparCampo($(".valorDC"));
        limparCampo($(".numDDC"));
//        testeVasioRadio($("table[id='movimentacao:tipDC']"),$("input[name='movimentacao:tipDC']"));
    }
    if(val==="3")
    {
        limparCampo($(".totalCh"));
        limparCampo($(".fimCh"));
        limparCampo($(".incioCh"));
        limparCampo($(".bancoCh"));
    }
}
function soCor(variavel,cor)
{
    if(cor!=="")
        variavel.css("border","1px solid "+cor);
    else
        variavel.css("border","");
  
}
function limparCampo(variavel)
{
    variavel.val("");
}

function formatted(nStr)
{
    var num = nStr.val().replace(/(\s)/g, '');
    nStr.val(num.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1 "));

}

function checkFields(op)
{
    valid = true;
    if(op ==='register')
    {
        $(".regCheque [type=text], select").each(function ()
        {
           if($(this).val() === "")
           {
               $(this).css("border","1px solid red");
               valid= false;
           }
        });
    }
    else if(op ==="registered")
    {
        $(".regCheque [type=text], select").each(function ()
        {
            $(this).css("border","");
            $(this).val("");    
        });
        $(".btSaveMov").attr("disabled", false);
    }
    else $(".btSaveMov").attr("disabled", false);

}

function fieldsNewLaunch()
{
    if($(dataLancamento).val() === "")
        $(dataLancamento).css("border", "1px solid red");
    else
        $(dataLancamento).css("border", "");
    if($(".typeLaunch").val()==="")
        $(".typeLaunch").css("border","1px solid red");
    else
        $(".typeLaunch").css("border","");
    if($(".numDocLaunch").val() === "")
        $(".numDocLaunch").css("border","1px solid red");
    else
        $(".numDocLaunch").css("border","");
    if($(dataDocumento).val() === "")
        $(dataDocumento).css("border", "1px solid red");
    else
        $(dataDocumento).css("border", "");
    if($(launchAccount).val() ==="")
        $(launchAccount).css("border", "1px solid red");
    else
         $(launchAccount).css("border", "");
    if($(".movimentLaunchDesc").val() === "")
        $(".movimentLaunchDesc").css("border","1px solid red");
    else
        $(".movimentLaunchDesc").css("border",""); 
    if($(".launchCoin").is(":disabled") === false)
    {
        if($(".launchCoin").val() === "")
            $(".launchCoin").css("border","1px solid red");
        else
            $(".launchCoin").css("border","");   
    }
    if($(".launchValue").val() === "")
        $(".launchValue").css("border","1px solid red");
    else
        $(".launchValue").css("border",""); 
    
    
}

function creditAdded(op)
{
    if(op === 'add')
    {
          $(dataDocumento).val("");
        $(".launchAddTable").css("border", ""); 
        $(".launchAddTable").val("");
         $(launchAccount).val("");
    }
    else
    {
        $(dataLancamento).val("");
        $(dataDocumento).val("");
        $(".launchAddTable").css("border", ""); 
        $(".launchAddTable").val("");
        $(".launchCoin").val("");
        $(".typeLaunch").val("");
        $(launchAccount).val("");
    }
  
}

function moedaAtivarDesativar(state)
{
    if(state === 'ativar')
        $('.launchCoin').attr('disabled', false);
    else $('.launchCoin').attr('disabled', true);
}


