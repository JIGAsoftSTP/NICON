/* 
    Created on : Aug 16, 2015, 5:28:48 PM
    Author     : Junior
*/

$(document).ready(function() {

  $(".mf-bt-ok").click(function ()
  {
     $(".clienteR").hide('fade'); 
     $(".mp-addClient").hide('fade');
     return false;
  });
    $('#singu').attr(":checked",true);          
    $( "input" ).on( "click", function() {       
       if ($(this).is(':checked')){
        if($(this).val() === 'Singular') {
             $("#coletivo").hide($("#coletivo").fadeOut(00));
             $("#singular").show($("#singular").fadeIn(00));             
         }
         else{
             $("#singular").hide($("#singular").fadeOut(00));
             $("#coletivo").show($("#coletivo").fadeIn(00));
         }
    }        
    });   
    $('.newCliendAdded').click(function ()
    {
        prosseguir();
        return false;
    });
    $(".clienteLimpar").click(function()
    {
        limparInfoCliente();
        return false;
    });
    $("input:text").click(function(e)
    {
       $(this).css("border",""); 
    });
    $(".clienteSONUMERO").keyup(function (e)
    {
        e.preventDefault();
        var expre = /[^0-9]/g;
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if ($(this).val().match(expre))
            $(this).val($(this).val().replace(expre, ''));
    });
    $(".clienteGuardar").click(function (e)
    {
       e.preventDefault();
       var tipoCliente = $(".clienteT").html();
       var tipoClienteCampo =  $("input:radio[name='addClienteformulario:tipoCli']").is(":disabled");
         if(tipoClienteCampo === true)
         {
            if($(".tipoCliente").html() !=="")
            {
                if($(".tipoCliente").html() ==="Empresa")
                     verificarCamposColetivo();
                else
                     VerificarCamposSingular();
            }
         }
         else
         {
             if(tipoCliente ==='Singular')
                VerificarCamposSingular();
           else
                verificarCamposColetivo();
             }
       
    });   
});
  function VerificarCamposSingular()
  {
     var apelido = $(".ApelidoCliente").val();
     var sexo = $(".sexoCliente").val();
     var morada = $(".moradaCliente").val();
     var nacionalidade = $(".nacionalidadeCliente").val();
     var profissao = $(".profissaoCliente").val();
     var telefone = $(".telefoneS").val();
     var localT = $(".localTCliente").val();
     var dataNasc = "input:text[name='addClienteformulario:clienteDataNasc_input']";
     var nome = $("input:text[name='addClienteformulario:clienteNomeS']").val();
     var documento = $(".clienteDoc").val();
     var numeroDoc = $(".clienteNumero").val();
     
     if($(".EstadoCliente").val() === "")
         $(".EstadoCliente").css("border", "1px solid red");
     else  $(".EstadoCliente").css("border", "");
     
     if(nacionalidade=== "")
         $(".nacionalidadeCliente").css("border","1px solid red");
     else $(".nacionalidadeCliente").css("border","");

     if($(".NifSingular").val() === "")
         $(".NifSingular").css("border", "1px solid red");
     else
        $(".NifSingular").css("border", "");
     if(numeroDoc ==="")
         $(".clienteNumero").css("border","1px solid red");
     else
         $(".clienteNumero").css("border","");
     if(documento==="")
         $(".clienteDoc").css("border","1px solid red");
     else
         $(".clienteDoc").css("border","");
     
      if(nome ==='')
          $("input:text[name='addClienteformulario:clienteNomeS']").css("border-color","red");
      else
          $("input:text[name='addClienteformulario:clienteNomeS']").css("border-color","");
      if(telefone ==="")
           $(".telefoneS").css("border","1px solid red");
       else
           $(".telefoneS").css("border","");
      if(apelido==="")
          $(".ApelidoCliente").css("border","1px solid red");
      else
          $(".ApelidoCliente").css("border","");
      if(sexo==="")
          $(".sexoCliente").css("border","1px solid red");
      else
          $(".sexoCliente").css("border","");
      if($(dataNasc).val() ==='')
          $(dataNasc).css("border","1px solid red");
      else
           $(dataNasc).css("border","");
  }
  
  function verificarCamposColetivo()
  {
      var nome = $(".nomeColetivo").val();
     var telefone = $(".telefoneColetivo").val();
      var localizacao = $(".localizacao").val();
      var pais = $(".paisCliente").val();
      if($(".nifColetivo").val() ==="")
          $(".nifColetivo").css("border","1px solid red");
      else
         $(".nifColetivo").css("border","");
      if(nome==="")
          $(".nomeColetivo").css("border-color","red");
      else
          $(".nomeColetivo").css("border-color","");
      if(localizacao==="")
          $(".localizacao").css("border-color","red");
      else
          $(".localizacao").css("border-color","");

     if(pais==="")
           $(".paisCliente").css("border-color","red");
     else
                $(".paisCliente").css("border-color","");
     if(telefone ==="")
        $(".telefoneColetivo").css("border-color","red");
    else
         $(".telefoneColetivo").css("border-color","");
  }
 

 
function CloseFrame()
{
        limparInfoCliente();
     ("#modalPage").hide($("#modalPage").fadeOut(800)); 

     
 }
 function limparInfoCliente()
 {
    $('.formAddClient input:text').val("");
    $('.formAddClient input:text').css("border","");
    $('.formAddClient select').css("border","");
    $('.formAddClient select').val("");
 } 
 function verificarNif(xhr, status, args)
 {
     var nif = args.nif;
     if(nif ==="nif singular já existe")
     {
         $(".NifSingular").css("border","1px solid red");
     }
     else if(nif ==='nif coletivo já existe')
     {
          $(".NifSingular").css("border","");
         $(".nifColetivo").css("border","1px solid red");
     }
     else
     {
          $(".NifSingular").css("border","");
         $(".nifColetivo").css("border","");
     }
         
 }
 
 function verificarAlvara(xhr, status, args)
 {
     var numAlvara = args.alvara;
     if(numAlvara==="alvará já existe")
         $(".alvaraCliente").css("border","1px solid red");
     else
         $(".alvaraCliente").css("border","");
 }
 
 function verificarEmail(xhr, status, args)
 {
     var email = args.emailS;
      if(email ==="email singular já existe")
     {
         $(".clienteEmailS").css("border","1px solid red");
     }
     else if(email ==='email coletivo já existe')
     {
            $(".clienteEmailS").css("border","");
          $(".emailCCli").css("border","1px solid red");
     }
     else
     {
          $(".clienteEmailS").css("border","");
          $(".emailCCli").css("border","");
     }
         
 }
 
 function ValidarEmail(xhr, status, args)
 {
     var email = args.email;
     if(email ==="email singular inválido")
     {
         $(".clienteEmailS").css("border","1px solid red");
     }
     else if(email ==='email coletivo inválido')
     {
            $(".clienteEmailS").css("border","");
          $(".emailCCli").css("border","1px solid red");
     }
     else
          $(".emailCCli").css("border","");
 }

 function fechar()
 {
       var tipoCliente =  $("input:radio[name='addClienteformulario:tipoCli']").is(":disabled");
       if(tipoCliente ===true)
            $(".mp-addClient").hide($(".mp-addClient").fadeOut(800));  
       else
       {
          $(".mp-addClient").hide($(".mp-addClient").fadeOut(800));  
          limparInfoCliente();
       }
   
      
 }
 
 function prosseguir()
{
    $( '#divIncludeStep1').add( '#divIncludeStep2' ).toggle('fade');
}

function InfoCliente()
{
    
}

function bordaVermelhaNIF1()
{
    $(".NifSingular").css("border", "1px solid red");
}
function bordaVermelhaNIF2()
{
    $(".nifColetivo").css("border", "1px solid red");
}

function bordaVermelhaEmail1()
{
    $(".clienteEmailS").css("border", "1px solid red");
}
function bordaVermelhaEmail2()
{
     $(".emailCCli").css("border", "1px solid red");
}
function bordaVermelhaNIF()
{
}
function emailInvalido()
{
    alert("Email inválido");
    $(".emailCCli").focus();
    $(".clienteEmailS").focus();
       $(".emailCCli").focus();
    $(".clienteEmailS").focus();
}
function nifExiste()
{
    alert("NIF já existe");
    $(".NifSingular").focus();
    $(".nifColetivo").focus();
    
}
function emailExiste()
{
    alert("Email já existe");
    $(".emailCCli").focus();
    $(".clienteEmailS").focus();
    
}


function clienteRegistrado()
{
    limparInfoCliente();
    apoliceContrato("");
    $(".clienteR").show($(".clienteR").slideDown(400));
}

function mostrarProcesso()
{
    $(".modalProcess").show();
}
function fecharProcesso()
{
     $(".modalProcess").hide();
}