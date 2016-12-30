$(document).ready(function (e)
{
    soNumeros();
    $("input:text").click(function(e)
    {
      $(this).css("border","");  
    });
    $(".IniciarDesativado").attr('disabled',true);
    $(".botaoProximo").attr('disabled',true);
    $(".maritimoSeguinte").click(function(e)
    {
       e.preventDefault();
        verificarInfomacoesNavio();
    });
     $(".numeroAC").keyup(function(e){
        e.preventDefault();
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if(!$.isNumeric($(this).val()))
            $(this).val("");
   });
    $(".valorPrimeiraC").keyup(function (e)
    {
        e.preventDefault();
       $(".taxaPrimeiraC").val(""); 
         $(".p1").val(""); 
    });
    
      $(".valorSegundaC").keyup(function (e)
      {
        e.preventDefault();
        $(".taxaSegundaC").val(""); 
          $(".p2").val(""); 
     });
     $(".valorTerceiraC").keyup(function (e)
      {
        e.preventDefault();
       $(".taxaTerceiraC").val(""); 
         $(".p3").val(""); 
    });
      $(".valorQuartaC").keyup(function (e)
      {
        e.preventDefault();
       $(".taxaQuartaC").val(""); 
         $(".p4").val(""); 
    });
    
       $(".numeroAC").keyup(function(e){
        e.preventDefault();
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if(!$.isNumeric($(this).val()))
            $(this).val("");
   });

});

function verficar()
{
    if($(".MaritimoCobertura1").is(":enabled"))
    {
        verificarCamposPrimeiraCobertura();
    }
    if($(".MaritimoCobertura4").is(":enabled"))
    {
        verificarCamposSegundaCobertura();
    }
    if($(".MaritimoCobertura7").is(":enabled"))
    {
        verificarCamposTerceiraCobertura();
    }
    if($(".MaritimoCobertura10").is(":enabled"))
    {
        verificarCamposQuartaCobertura();
    }
}


function verificarCamposPrimeiraCobertura()
{
   var campoCobertura1 = $(".MaritimoCobertura1").val();
   var campoCobertura2 = $(".MaritimoCobertura2").val();

   
   if(campoCobertura1 ==='')
   {
       $(".MaritimoCobertura1").css("border","1px solid red");
   }
   else
   {
      $(".MaritimoCobertura1").css("border",""); 
   }
    if(campoCobertura2 ==='')
   {
       $(".MaritimoCobertura2").css("border","1px solid red");
   }
   else
   {
      $(".MaritimoCobertura2").css("border",""); 
   }
  
 
}

function verificarCamposSegundaCobertura()
{
   var campoCobertura4 = $(".MaritimoCobertura4").val();
   var campoCobertura5 = $(".MaritimoCobertura5").val();
   var campoCobertura6 = $(".MaritimoCobertura6").val();
   
     if(campoCobertura4 ==='')
   {
       $(".MaritimoCobertura4").css("border","1px solid red");
   }
   else
   {
      $(".MaritimoCobertura4").css("border",""); 
   }
    if(campoCobertura5 ==='')
   {
       $(".MaritimoCobertura5").css("border","1px solid red");
   }
   else
   {
      $(".MaritimoCobertura5").css("border",""); 
   }
   
 
}

function verificarCamposTerceiraCobertura()
{
   var campoCobertura7 = $(".MaritimoCobertura7").val();
   var campoCobertura8 = $(".MaritimoCobertura8").val();
 
   
   if(campoCobertura7 ==='')
   {
       $(".MaritimoCobertura7").css("border","1px solid red");
   }
   else
   {
      $(".MaritimoCobertura7").css("border",""); 
   }
    if(campoCobertura8 ==='')
   {
       $(".MaritimoCobertura8").css("border","1px solid red");
   }
   else
   {
      $(".MaritimoCobertura8").css("border",""); 
   }
   
}

function verificarCamposQuartaCobertura()
{
   var campoCobertura10 = $(".MaritimoCobertura10").val();
   var campoCobertura11 = $(".MaritimoCobertura11").val();
   var campoCobertura12 = $(".MaritimoCobertura12").val();
   
   if(campoCobertura10 ==='')
   {
       $(".MaritimoCobertura10").css("border","1px solid red");
   }
   else
   {
      $(".MaritimoCobertura10").css("border",""); 
   }
    if(campoCobertura11 ==='')
   {
       $(".MaritimoCobertura11").css("border","1px solid red");
   }
   else
   {
      $(".MaritimoCobertura11").css("border",""); 
   }
    if(campoCobertura12 ==='')
   {
       $(".MaritimoCobertura12").css("border","1px solid red");
   }
   else
   {
      $(".MaritimoCobertura12").css("border",""); 
   }
}


function verificarInfomacoesNavio()
{
    var apolice = $(".apoliceMaritimo").val();
    var registro = $(".numeroRegistroMaritimo").val();
    var bandeira = $(".maritimoBandeira").val();
    var uso = $(".maritimoUso").val();
    var classe = $(".maritimoClasse").val();
    var potencia = $(".maritimoPotencia").val();
    var tipoCombustivel = $(".maritimocombustivel").val();
    var peso = $(".maritimoPeso").val();
    var numMotor = $(".maritimoNumMotor").val();
    var marcaMotor = $(".maritimoMarcaMotor").val();
    var numTripulante = $(".maritimoTripulante").val();
    var areaOperacao = $(".maritimoAreaOperacao").val();
    var espe = $(".maritimoEspecificacao").val();
    var nomeNavio = $(".maritimoNomeNavio").val();
    var modelo = $(".maritimoMarcaModelo").val();
    var chassi = $(".maritimoChassi").val();
    var idade = $(".maritimoIdade").val();
    var tipoNavio = $(".maritimoTipoNavio").val();
    var tipoConstrucao = $(".maritimoConstrucao").val();
    var condicao = $(".maritimoCondicao").val();
    
    if(apolice==="")
        $(".apoliceMaritimo").css("border","1px solid red");
    else
        $(".apoliceMaritimo").css("border","");
 
    if(bandeira ==='')
    {
         $(".maritimoBandeira").css("border","1px solid red");
    }
    else
    {
         $(".maritimoBandeira").css("border","");
    }
    if(uso ==='')
    {
         $(".maritimoUso").css("border","1px solid red");
    }
    else
    {
         $(".maritimoUso").css("border","");
    }
    if(classe ==='')
    {
         $(".maritimoClasse").css("border","1px solid red");
    }
    else
    {
         $(".maritimoClasse").css("border","");
    }
    if(potencia ==='')
    {
         $(".maritimoPotencia").css("border","1px solid red");
    }
    else
    {
         $(".maritimoPotencia").css("border","");
    }
    if(tipoCombustivel ==='')
    {
         $(".maritimocombustivel").css("border","1px solid red");
    }
    else
    {
         $(".maritimocombustivel").css("border","");
    }
   if(peso ==='')
    {
         $(".maritimoPeso").css("border","1px solid red");
    }
    else
    {
         $(".maritimoPeso").css("border","");
    }
    if(numMotor ==='')
    {
         $(".maritimoNumMotor").css("border","1px solid red");
    }
    else
    {
         $(".maritimoNumMotor").css("border","");
    }     
    if(marcaMotor ==='')
    {
         $(".maritimoMarcaMotor").css("border","1px solid red");
    }
    else
    {
         $(".maritimoMarcaMotor").css("border","");
    }     
    if(numTripulante ==='')
    {
         $(".maritimoTripulante").css("border","1px solid red");
    }
    else
    {
         $(".maritimoTripulante").css("border","");
    }    
    if(areaOperacao ==='')
    {
         $(".maritimoAreaOperacao").css("border","1px solid red");
    }
    else
    {
         $(".maritimoAreaOperacao").css("border","");
    }        
    if($(".maritimoEspecificacao").is(":enabled"))
    {
         if(espe ==='')
        {
             $(".maritimoEspecificacao").css("border","1px solid red");
        }
        else
        {
             $(".maritimoEspecificacao").css("border","");
        }   
    }
    if(nomeNavio ==='')
    {
         $(".maritimoNomeNavio").css("border","1px solid red");
    }
    else
    {
         $(".maritimoNomeNavio").css("border","");
    }      
    if(modelo ==='')
    {
         $(".maritimoMarcaModelo").css("border","1px solid red");
    }
    else
    {
         $(".maritimoMarcaModelo").css("border","");
    }  
    if(chassi ==='')
    {
         $(".maritimoChassi").css("border","1px solid red");
    }
    else
    {
         $(".maritimoChassi").css("border","");
    }  
    if(idade ==='')
    {
         $(".maritimoIdade").css("border","1px solid red");
    }
    else
    {
         $(".maritimoIdade").css("border","");
    }  
    if(tipoNavio ==='')
    {
         $(".maritimoTipoNavio").css("border","1px solid red");
    }
    else
    {
         $(".maritimoTipoNavio").css("border","");
    } 
    if(tipoConstrucao ==='')
    {
         $(".maritimoConstrucao").css("border","1px solid red");
    }
    else
    {
         $(".maritimoConstrucao").css("border","");
    } 
    if(condicao ==='')
    {
         $(".maritimoCondicao").css("border","1px solid red");
    }
    else
    {
         $(".maritimoCondicao").css("border","");
    } 
   if(registro ==='')
    {
         $('html, body').animate({ scrollTop: 0 }, 'slow');
         $(".numeroRegistroMaritimo").css("border","1px solid red");
    }
    else
    {
         $(".numeroRegistroMaritimo").css("border","");
        
    } 

  
}

function soNumeros()
{
 
     $(".MaritimoCobertura6").keyup(function(e){
        e.preventDefault();
        var expre = /[^0-9]/g;
        var i =0;
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if ($(this).val().match(expre))
         {
            $(this).val($(this).val().replace(expre,''));
          
         }
   });
       $(".MaritimoCobertura7").keyup(function(e){
        e.preventDefault();
        var expre = /[^0-9]/g;
        var i =0;
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if ($(this).val().match(expre))
         {
            $(this).val($(this).val().replace(expre,''));
          
         }
   });
       $(".maritimoIdade").keyup(function(e){
        e.preventDefault();
        var expre = /[^0-9]/g;
        var i =0;
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if ($(this).val().match(expre))
         {
            $(this).val($(this).val().replace(expre,''));
          
         }
   });
    $(".maritimoTripulante").keyup(function(e){
        e.preventDefault();
        var expre = /[^0-9]/g;
        var i =0;
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if ($(this).val().match(expre))
         {
            $(this).val($(this).val().replace(expre,''));
          
         }
   });
 
    $(".MaritimoCobertura9").keyup(function(e){
        e.preventDefault();
        var expre = /[^0-9]/g;
        var i =0;
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if ($(this).val().match(expre))
         {
            $(this).val($(this).val().replace(expre,''));
          
         }
   });
}

{
    $(":text", $("#maritimoFormulario")).each(function()
    {
        $(this).val("");
    });
}

function PrimeiraCobertura()
{
    var info = $(".maritimoSemGuerra").html();
    if(info === 'true')
    {
        $(".MaritimoCobertura1").attr('disabled', false);        

          $(".MaritimoCobertura2").attr('disabled',true);       
    }
    else
    {
        $(".MaritimoCobertura1").attr('disabled', true);
         $(".MaritimoCobertura1").val("");
          $(".p1").val(""); 
    }
}

function SegundaCobertura()
{
    var info = $(".maritimoGuerra").html();
    if(info ==='true')
    {
        $(".MaritimoCobertura2").attr('disabled',false);
        $(".MaritimoCobertura1").attr('disabled', true);  
    }
    else
    {
         $(".MaritimoCobertura2").attr('disabled',true);
         $(".MaritimoCobertura2").val("");
          $(".p2").val(""); 
    }
    return info;
}

function TerceiraCobertura()
{
    var info = $(".maritimoSpassageiro").html();
    if(info ==='true')
    {
        $(".MaritimoCobertura3").attr('disabled',false);
        $(".MaritimoCobertura4").attr('disabled',true);
    }
    else
    {
         $(".MaritimoCobertura3").val("");
         $(".MaritimoCobertura3").attr('disabled',true);
          $(".p3").val(""); 
    }
}

function QuartaCobertura()
{
    var info = $(".maritimoPassageiro").html();
    if(info ==='true')
    {
        $(".MaritimoCobertura4").attr('disabled',false);
        $(".MaritimoCobertura3").attr('disabled',true);
    }
    else
    {
        $(".MaritimoCobertura4").val("");
         $(".MaritimoCobertura4").attr('disabled',true);
          $(".p4").val(""); 
    }
}

function numeroRegistro(xhr, status, args)
{
    var info = args.numeroR;
    if(info ==="já existe")
    {
         $('html, body').animate({ scrollTop: 0 }, 'fast');
        $(".maritimoRegistro").css("border","1px solid red");
        $(".maritimoRegistro").attr('title','Número de registro  já existe');
    }
    else
    {
        $(".maritimoRegistro").css("border","");
        $(".maritimoRegistro").attr('title','');
    }
}

function SelecionarCobertura()
{
    $(".infoM").css('display','');
}
function CoberturaSelecionada()
{
    $(".infoM").css('display','none');
}

function avancar()
{
    var certo = 1;
    var i1 = $(".p1").val();
 
    alert($(".tamanho3").val());
    if(certo === 1)
    {
        window.location.href ="GestSeg_NovoSeguroApolice.xhtml";
        CoberturaSelecionada();
    }
    else
    {
        $(".infoM").html("Selecione pelo menos uma cobertura informando o valor e a taxa");
        SelecionarCobertura();
    }
}

function existe()
{
    $(".maritimoRegistro").css("border","1px solid red");
     $(".maritimoRegistro").focus();
    $(".maritimoRegistro").attr('title','Número de registro já existe');
}

function apoliceMaritimo()
{
     $('html, body').animate({ scrollTop: 0 }, 'slow');
    $(".apoliceMaritimo").focus();
}
