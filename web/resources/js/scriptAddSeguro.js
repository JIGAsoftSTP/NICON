/* 
    Created on : Aug 16, 2015, 5:28:48 PM
    Author     : Junior
*/

$(document).ready(function() {
    $('#singu').attr("checked",true);
    $(".btContinue").attr('disabled',true);
    $(".AddSeguroContinuar").attr("disabled",true);
    $('.closeModalFrame').click(function (){
       var tipoCliente =  $("input:radio[name='addClienteformulario:tipoCli']").is(":disabled");
       if(tipoCliente === true)
       {
            $(".mp-addClient").fadeOut();
       }
       else
       {
            $(".mp-addClient").hide($(".mp-addClient").fadeOut(400));
            limparInfo();
       }
       
        
    }); 
    
    $( ".addClient" ).click(function() { 
         $('.buttons').css("display","");
         $('.clienteLimpar').attr("disabled", false);
         $('.formAddClient [type=text], select').attr('disabled', false);
        $("label").each(function ()
        {
            if ($(this).attr('for')==="addClienteformulario:tipoCli:0")
                $(this).show();                                                 
        });
        $("label").each(function ()
        {
            if ($(this).attr('for')==="addClienteformulario:tipoCli:1")
                $(this).show();                                                 
        });
       $('.formAddClient input').removeClass('onlyInfoSelected');
       $('.formAddClient input:text').val("");
       $('.formAddClient input:text').css("border","");
       $('.formAddClient select').removeClass('onlyInfoSelected');
       $('.formAddClient select').css("border","");
       $('.formAddClient select').val("");
       $('.formAddClient select').attr('disabled', false);
       $("input:radio[name='addClienteformulario:tipoCli']").attr('disabled',false);
       $("input:radio[name='addClienteformulario:numeroCliente']").val("");
       $("input:text[name='addClienteformulario:clienteDataNasc_input']").attr('disabled', false);
       $("input:text[name='addClienteformulario:clienteDataNasc_input']").val("");
       $(".mp-addClient").show($(".mp-addClient").fadeIn(400));
    });
   
    $('.cancel').click(function (){
     $(".mp-addClient").hide('fade'); 
         $('#formulario').each (function(e){
         this.reset();
            });
    }); 
     $(".nomeClienteSelecionado").click(function (e)
     {
        e.preventDefault();
       $(".tipoClienteS").html("");
        $('.formAddClient input').addClass('onlyInfoSelected');
        $('.formAddClient select').addClass('onlyInfoSelected');
          $("label").each(function ()
        {
            if ($(this).attr('for')==="addClienteformulario:tipoCli:0")
                $(this).show();                                                 
        });
        $("label").each(function ()
        {
            if ($(this).attr('for')==="addClienteformulario:tipoCli:1")
                $(this).show();                                                  
        });
        if($(".tipoCliente").html() !=="")
        {
            $("input:radio[name='addClienteformulario:tipoCli']").attr('disabled',true);
            if($(".tipoCliente").html() ==="Empresa")
            {
               $("input:radio[name='addClienteformulario:tipoCli']").each(function ()
              {
                 if($(this).val()==="Coletivo")
                 {
                      $(this).attr('checked', true);

                 }
                 $("label").each(function ()
                 {

                     if ($(this).attr('for')==="addClienteformulario:tipoCli:0")
                         $(this).hide();                                                  
                 });
              });
                $(".singular").hide($(".singular").fadeOut(00));
                $(".coletivo").show("slide");        
            }
            else
            {
                 $("input:radio[name='addClienteformulario:tipoCli']").each(function ()
             {
                 if($(this).val()==="Singular")
                 {
                      $(this).attr('checked', true);
                 }
                 $("label").each(function ()
                 {
                     if ($(this).attr('for')==="addClienteformulario:tipoCli:1")
                         $(this).hide();
                 });
             });
                $(".coletivo").hide($(".coletivo").fadeOut(00));
                $(".singular").show("slide");   
            }
                 
            $('.formAddClient input:text').attr('disabled', true);
            $('.formAddClient select').attr('disabled', true);
            $('.buttons').css("display","none");
            $("input:text[name='addClienteformulario:clienteDataNasc_input']").attr('disabled', true);
            $(".mp-addClient").show($(".mp-addClient").fadeIn(400));
        }
       
    });
});

function ClienteSelecionado(xhr, status, args)
{
    var d = args.selecionado;
    var nomeCli = args.nome;
    var tipoC = args.tipoC;
    var contratos = args.contrato;
    var email = args.emailCliente;
    var nif = args.nif;
    var telefone = args.telefone;
    var telemovel = args.telemovel;
  
    if(d==='sim')
    {
        
         $(".AddSeguroContinuar").attr("disabled",false);
         $(".nomeClienteSelecionado").html(nomeCli);
         
        $(".tipoCliente").html(tipoC);
        $(".contratoCliente").html(contratos);
        $(".btContinue").attr('disabled',false);
    }
    else
    {
         $(".AddSeguroContinuar").attr("disabled",true);
         $(".nomeClienteSelecionado").html("");
         $(".tipoCliente").html("");
          $(".contratoCliente").html("");
         $(".btContinue").attr('disabled',true);
    }
    
     if(tipoC !=="")
     {
        if(tipoC==="Pessoa")
        {
            $(".NifSingular").val(nif);
            if(telefone ==="NENHUM")
                 $(".telefoneS").val("");
            else  
              $(".telefoneS").val(telefone);
            if(telemovel ==="NENHUM")
               $(".telemovelS").val("");
            else
                $(".telemovelS").val(telemovel);
        }
        else
        {
            $(".nifColetivo").val(nif);
            if(telefone ==="NENHUM")
                 $(".telefoneColetivo").val("");
            else  
              $(".telefoneColetivo").val(telefone);
            if(telemovel ==="NENHUM")
               $(".telemovelColetivo").val("");
            else
                $(".telemovelColetivo").val(telemovel);
        }
    }

}

function Cliente(xhr, status, args)
{
    var d = args.selecionado;
    if(d==="sim")
    {
         $(".AddSeguroContinuar").attr("disabled",false);
    }
    else
    {
         $(".AddSeguroContinuar").attr("disabled",true);
    }
   
}



 
 //-------------INFORMAÇÕES DA PAGINA GESTÃO DE CLIENTES----------
 
 function Info(xhr, status, args)
 {
     var info = args.selecionado;
     var tipoCliente = args.tipoClienteSelecionado;
     var contrato = args.contratoCliente;
     var nome = args.nomeCliente;

     //se um cliente for selecionado na tabela
     if(info ==='true')
     {
         $(".nomeClienteSelecionado").html(nome);
         $(".tipoCliente").html(tipoCliente);
         $(".contratoCliente").html(contrato);
     }
   
 }
  function limparInfo()
 {
    $('.formAddClient input:text').val("");
    $('.formAddClient input:text').css("border","");
    $('.formAddClient select').css("border","");
    $('.formAddClient select').val("");
 }
 
function checkApolice(apoliceCarregado)
{
    if(apoliceCarregado === "N")
        $(".apolice").attr("disabled", false);
    else
        $(".apolice").attr("disabled", true);
}

function apoliceContrato(apolice)
{
    if(apolice === '')
    {
        $(".apolice").attr("disabled", false);
        $(".apolice").val("");
    }
    else
    {
        $(".apolice").attr("disabled", true);
        $(".apolice").val(apolice);
    }
}