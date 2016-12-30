$(document).ready(function (e)
{  
    $(".InsendioTaxa").keyup(function (e)
    {
        e.preventDefault(); 
        if($(this).val()>100)
        {
            $(this).val("");
        }
    });
    
    desavilitarAviaao();
    
    $(".aviaoId").click(function (e)
        {   
            desavilitarAviaao();
        });
        
     desavilitaColisao();
     
     $(".clisaoId").click(function (e)
        {   
             desavilitaColisao();
        });
        
     desavilitaExplosao();
     $(".explosaoId").click(function (e)
        {   
            desavilitaExplosao();
        });
        $("input").focus(function (e)
        {
           e.preventDefault();
           $(this).css("border","");
        });
        
     desavilitaTempestadeID();
     $(".tempestadeID").click(function (e)
        {   
            desavilitaTempestadeID();
        });
        
        
      desavilitaTerramotoId();
     $(".terramotoId").click(function (e)
        {   
            desavilitaTerramotoId();
        });
        
     desavilitaafundamentoID();
     $(".afundamentoID").click(function (e)
        {   
            desavilitaafundamentoID();
        });
        
      desavilitaRiscoPolitico();
     $(".riscoPoliticoID").click(function (e)
        {   
            desavilitaRiscoPolitico();
        });
        
     desavilitaDanoMalicioso();
     $(".danoMaliciosoId").click(function (e)
        {   
            desavilitaDanoMalicioso();
        });
        
       desavilitaTumulto();
     $(".tumultoId").click(function (e)
        {   
            desavilitaTumulto();
        });
        
       desavilitaIncendioF();
     $(".incendioFId").click(function (e)
        {   
            desavilitaIncendioF();
        });
        
      desavilitaExplosaoEx();
     $(".explosaoExId").click(function (e)
        {   
            desavilitaExplosaoEx();
        });
     
     desavilitaRoptura();
     $(".ropturaId").click(function (e)
        {   
            desavilitaRoptura();
        });
        
    $(".IncendioSeguinte").click(function (e)
    {
        e.preventDefault();
        IncendioValidar();
    });
    
     $(".numeroAC").keyup(function(e)
    {
        e.preventDefault();
        if (!$.isNumeric($(this).val()))
            $(this).val("");
   });
   
      $(".numeroReal").keyup(function(e){
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
    
});

$("input:text").click(function(e)
{
   $(this).css("border",""); 
});

function verificarCamposvazios()
{
   var numeros = $(".Incendionumeros").val();
   var condicao = $(".incendioCondicao").val();
   var combateincendio = $(".Incendiocombateincencio").val();
   var aguadisponivel = $(".Incendiofonteaguadisponivel").val();
   var ano =$ (".Incendioano").val();
   var numerosandadres =$(".Incendioandares").val();
   var distanciabombeiro = $(".Incendiodistanciabombeiro").val();
   var enderenco = $(".Incendioendereco").val();

   
   if(numeros ==='')
   {
       $(".Incendionumeros").css("border-color","red");
       $(".Incendionumeros").focus();
   }
   else
   {
      $(".Incendionumeros").css("border-color",""); 
   }
      
   if(combateincendio ==='')
   {
       $(".Incendiocombateincencio").css("border-color","red");
       $(".Incendiocombateincencio").focus();
   }
   else
   {
      $(".Incendiocombateincencio").css("border-color",""); 
   }
   
   
    if(aguadisponivel ==='')
   {
       $(".Incendiofonteaguadisponivel").css("border-color","red");
       $(".Incendiofonteaguadisponivel").focus();
   }
   else
   {
      $(".Incendiofonteaguadisponivel").css("border-color",""); 
   }
   
   
   if(ano ==='')
   {
       $(".Incendioano").css("border-color","red");
       $(".Incendioano").focus();
   }
   else
   {
      $(".Incendioano").css("border-color",""); 
   }
   
   
    if(numerosandadres ==='')
   {
       $(".Incendioandares").css("border-color","red");
       $(".Incendioandares").focus();
   }
   else
   {
      $(".IncendioIncendioandaresl").css("border-color",""); 
   }
   
   if(distanciabombeiro ==='')
   {
       $(".Incendiodistanciabombeiro").css("border-color","red");
       $(".Incendiodistanciabombeiro").focus();
   }
   else
   {
      $(".Incendiodistanciabombeiro").css("border-color",""); 
   }
   
   if(enderenco ==='')
   {
       $(".Incendioendereco").css("border-color","red");
       $(".Incendioendereco").focus();
   }
   else
   {
      $(".Incendioendereco").css("border-color",""); 
   }
   
}


    
function IncendioValidar()
{
     var i = 0;
    //informações que estão no topo do formulário
    var numeroR = $(".incendioNumeroRegistro").val();
    var condicao = $(".incendioCondicao").val();
    var fonte = $(".incendioFonte").val();
    var ano = $(".incendioAno").val();
    var andar = $(".incendioAndar").val();
    var distancia = $(".incendioDistancia").val();
    var equipamento = $(".incendioEquipamento").val();
    var endereco = $(".incendioEndereco").val();
    var apolice = $(".incendiosNumeroApolice").val();
    if($(".seguroIncendioCabecalho").is(":visible"))
    {
        if(apolice ==="")
        {
            $(".incendiosNumeroApolice").css("border","1px solid red");
            i = 1;
            $(".incendiosNumeroApolice").focus();
        }
        else
            $(".incendiosNumeroApolice").css("border","");
    }

    if(fonte ==='')
    {
        $(".incendioFonte").css("border","1px solid red");
        $(".incendioFonte").focus();
           i = 1;
    }
    else
    {
        $(".incendioFonte").css("border","");
    }
    if(ano ==='')
    {
        $(".incendioAno").css("border","1px solid red");
        $(".incendioAno").focus();
           i = 1;
    }
    else
    {
        $(".incendioAno").css("border","");
    }
    if(andar ==='')
    {
        $(".incendioAndar").css("border","1px solid red");
        $(".incendioAndar").focus();
           i = 1;
    }
    else
    {
        $(".incendioAndar").css("border","");
    }
    if(distancia ==='')
    {
        $(".incendioDistancia").css("border","1px solid red");
        $(".incendioDistancia").focus();
           i = 1;
    }
    else
    {
        $(".incendioDistancia").css("border","");
    }
    if(equipamento ==='')
    {
        $(".incendioEquipamento").css("border","1px solid red");
        $(".incendioEquipamento").focus();
           i = 1;
    }
    else
    {
        $(".incendioEquipamento").css("border","");
    }
    if(endereco ==='')
    {
        $(".incendioEndereco").css("border","1px solid red");
        $(".incendioEndereco").focus();
           i = 1;
    }
    else
    {
        $(".incendioEndereco").css("border","");
    }
    
    var aviaoDesativado = $(".incendioAviaoValor").is(":disabled");
    
    var aviaoValor = $(".incendioAviaoValor").val();
    var aviaoTaxa = $(".incendioAviaoTaxa").val();
    var aviaoPremio =$(".incendioAviaoPremio").val();
    var colisaoDesativado =  $(".colisaoValor").is(":disabled");
    
    var colisaoValor = $(".colisaoValor").val();
    var colisaoTaxa = $(".colisaoTaxa").val();
    var colisaoPremio = $(".colisaoPremio").val();
    var explosaoDesativado = $(".explosaoValor").is(":disabled");
    
    var explosaoValor = $(".explosaoValor").val();
    var explosaoTaxa = $(".explosaoTaxa").val();
    var explosaoPremio = $(".explosaoPremio").val();
   
    var terramotoDesativado = $(".terramotoValor").is(":disabled");
    var terramotoValor = $(".terramotoValor").val();
    var terramotoTaxa = $(".terramotoTaxa").val();
    var terramotoPremio = $(".terramotoPremio").val();
    var tempestadeDesativado = $(".tempestadeValor").is(":disabled");
    var tempestadeValor = $(".tempestadeValor").val();
    var tempestadeTaxa = $(".tempestadeTaxa").val();
    var tempestadePremio = $(".tempestadePremio").val();
    
    var afundamentoDesativado = $(".afundamentoValor").is(":disabled");
    var afundamentoValor = $(".afundamentoValor").val();
    var afundamentoTaxa = $(".afundamentoTaxa").val();
    var afundamentoPremio = $(".afundamentoPremio").val();
    
    var riscoPoliticoDesativado = $(".riscoPoliticoValor").is(":disabled");
    var riscoPoliticoValor = $(".riscoPoliticoValor").val();
    var riscoPoliticoTaxa = $(".riscoPoliticoTaxa").val();
    var riscoPoliticoPremio = $(".riscoPoliticoPremio").val();
    
    var danoMaliciosoDesativado = $(".danoMaliciosoValor").is(":disabled");
    var danoMaliciosoValor = $(".danoMaliciosoValor").val();
    var danoMaliciosoTaxa = $(".danoMaliciosoTaxa").val();
    var danoMaliciosoPremio = $(".danoMaliciosoPremio").val();
    
    var tumultoDesativado = $(".tumultoValor").is(":disabled");
    var tumultoValor = $(".tumultoValor").val();
    var tumultoTaxa = $(".tumultoTaxa").val();
    var tumultoPremio = $(".tumultoPremio").val();
    
    var incendioFdesativado = $(".incendioFvalor").is(":disabled");
    var incendioFvalor = $(".incendioFvalor").val();
    var incendioFtaxa = $(".incendioFtaxa").val();
    var incendioFpremio = $(".incendioFpremio").val();
    
    var explosaoExDesativado = $(".explosaoExValor").is(":disabled");
    var explosaoExValor = $(".explosaoExValor").val();
    var explosaoExTaxa = $(".explosaoExTaxa").val();
    var explosaoExPremio = $(".explosaoExPremio").val();
    
    var roturaDesativado = $(".roturaValor").is(":disabled");
    var roturaValor = $(".roturaValor").val();
    var roturaTaxa = $(".roturaTaxa").val();
    var roturaPremio = $(".roturaPremio").val();
    
    var outrosInterrese = $(".incendioInteresses").is(":disabled");
    var interrese = $(".incendioInteresses").val();
    var fumar = $(".fumar").is(":disabled");
    var fumar2 = $(".fumar").val();
    var perda = $(".perda").is(":disabled");
    var perda2 = $(".perda").val();
    var processoF = $(".processoF").is(":disabled");
    var processo = $(".processoF").val();
    var uso = $(".uso").val();
    if(outrosInterrese===false)
    {
        if(interrese ==='')
        {
            $(".incendioInteresses").css("border","1px solid red");
            $(".incendioEndereco").focus();
            i = 1;
        }
        else
        {
            $(".incendioInteresses").css("border","");
        }      
    }
    if(fumar === false)
    {
        if(fumar2==='')
        {
            $(".fumar").css("border","1px solid red");
            $(".fumar").focus();
            i = 1;
        }
        else
        {
            $(".fumar").css("border","");
        }
    }
    if(perda === false)
    {
        if(perda2 ==='')
        {
            $(".perda").css("border","1px solid red");
            $(".perda").focus();
            i = 1;
        }
        else
        {
            $(".perda").css("border","");
        }
    }
    if(processoF ===false)
    {
        if(processo ==='')
        {
            $(".processoF").css("border","1px solid red");
            $(".processoF").focus();
            i = 1;
        }
        else
        {
            $(".processoF").css("border","");
        }
    }
    if(uso ==='')
    {
        $(".uso").css("border","1px solid red");
        $(".uso").focus();
        i = 1;
    }
    else
    {
        $(".uso").css("border","");
    }
    if(aviaoDesativado ===false)
    {
        if(aviaoValor ==='')
        {
            $(".incendioAviaoValor").css("border","1px solid red");
            $(".incendioAviaoValor").focus();
            i = 1;
        }
        else
        {
            $(".incendioAviaoValor").css("border","");
        }
        if(aviaoTaxa ==='')
        {
         
            $(".incendioAviaoTaxa").css("border","1px solid red");
            $(".incendioAviaoTaxa").focus();
            i = 1;
        }
        else
        {
            $(".incendioAviaoTaxa").css("border","");
        }
        if(aviaoPremio ==='')
        {
            $(".incendioAviaoPremio").css("border","1px solid red");
            $(".incendioAviaoPremio").focus();
            i = 1;
        }
        else
        {
            $(".incendioAviaoPremio").css("border","");
        }
    }
    else
    {
        $(".incendioAviaoValor").css("border","");
        $(".incendioAviaoTaxa").css("border","");
        $(".incendioAviaoPremio").css("border","");
    }
    
    if(colisaoDesativado ===false)
    {
        if(colisaoValor ==='')
        {
            $(".colisaoValor").css("border","1px solid red");
            $(".colisaoValor").focus();
            i = 1;
        }
        else
        {
            $(".colisaoValor").css("border","");
        }
        if(colisaoTaxa ==='')
        {
            $(".colisaoTaxa").css("border","1px solid red");
            $(".colisaoTaxa").focus();
            i =1;
        }
        else
        {
            $(".colisaoTaxa").css("border","");
        }
        if(colisaoPremio ==='')
        {
            $(".colisaoPremio").css("border","1px solid red");
            $(".colisaoPremio").focus();
            i = 1;
        }
        else
        {
            $(".colisaoPremio").css("border","");
        }
    }
    else
    {
        $(".colisaoValor").css("border","");
        $(".colisaoTaxa").css("border","");
        $(".colisaoPremio").css("border","");
    }
    if(explosaoDesativado ===false)
    {
        if(explosaoValor ==='')
        {
            $(".explosaoValor").css("border","1px solid red");
            $(".explosaoValor").focus();
            i =1;
        }
        else
        {
            $(".explosaoValor").css("border","");
        }
        if(explosaoTaxa ==='')
        {
            $(".explosaoTaxa").css("border","1px solid red");
            $(".explosaoTaxa").focus();
            i =1;
        }
        else
        {
            $(".explosaoTaxa").css("border","");
        }
        if(explosaoPremio ==='')
        {
            $(".explosaoPremio").css("border","1px solid red");
            $(".explosaoPremio").focus();
            i =1;
        }
        else
        {
            $(".explosaoPremio").css("border","");
        }
       
    }
    else
    {
        $(".explosaoValor").css("border","");
        $(".explosaoTaxa").css("border","");
        $(".explosaoPremio").css("border","");

    }
    if(terramotoDesativado ===false)
    {
        if(terramotoValor ==='')
        {
            $(".terramotoValor").css("border","1px solid red");
            $(".terramotoValor").focus();
            i = 1;
        }
        else
        {
            $(".terramotoValor").css("border","");
        }
        if(terramotoTaxa ==='')
        {
            $(".terramotoTaxa").css("border","1px solid red");
            $(".terramotoTaxa").focus();
            i = 1;
        }
        else
        {
            $(".terramotoTaxa").css("border","");
        }
        if(terramotoPremio ==='')
        {
            $(".terramotoPremio").css("border","1px solid red");
            $(".terramotoPremio").focus();
            i =1;
        }
        else
        {
            $(".terramotoPremio").css("border","");
        }
    }
    else
    {
        $(".terramotoValor").css("border","");
        $(".terramotoTaxa").css("border","");
        $(".terramotoPremio").css("border","");
    }
    if(tempestadeDesativado ===false)
    {
        if(tempestadeValor==='')
        {
            $(".tempestadeValor").css("border","1px solid red");
            $(".tempestadeValor").focus();
            i = 1;
        }
        else
        {
            $(".tempestadeValor").css("border","");
        }
        if(tempestadeTaxa==='')
        {
            $(".tempestadeTaxa").css("border","1px solid red");
            $(".tempestadeTaxa").focus();
            i = 1;
        }
        else
        {
            $(".tempestadeTaxa").css("border","");
        }
        if(tempestadePremio==='')
        {
            $(".tempestadePremio").css("border","1px solid red");
            $(".tempestadePremio").focus();
            i = 1;
        }
        else
        {
            $(".tempestadePremio").css("border","");
        }
    }
    else
    {
        $(".tempestadeValor").css("border","");
        $(".tempestadeTaxa").css("border","");
        $(".tempestadePremio").css("border","");
    }
    if(afundamentoDesativado ===false)
    {
        if(afundamentoValor ==='')
        {
            $(".afundamentoValor").css("border","1px solid red");
            $(".afundamentoValor").focus();
            i =1;
        }
        else
        {
            $(".afundamentoValor").css("border","");
        }
        if(afundamentoTaxa==='')
        {
            $(".afundamentoTaxa").css("border","1px solid red");
            $(".afundamentoTaxa").focus();
            i = 1;
        }
        else
        {
            $(".afundamentoTaxa").css("border","");
        }
        if(afundamentoPremio==='')
        {
            $(".afundamentoPremio").css("border","1px solid red");
            $(".afundamentoPremio").focus();
            i =1;
        }
        else
        {
            $(".afundamentoPremio").css("border","");
        }
    }
    else
    {
        $(".afundamentoValor").css("border","");
        $(".afundamentoTaxa").css("border","");
        $(".afundamentoPremio").css("border","");
      
    }
    if(riscoPoliticoDesativado ===false)
    {
        if(riscoPoliticoValor ==='')
        {
            $(".riscoPoliticoValor").css("border","1px solid red");
            $(".riscoPoliticoValor").focus();
            i = 1;
        }
        else
        {
            $(".riscoPoliticoValor").css("border","");
        }
        if(riscoPoliticoTaxa==='')
        {
            $(".riscoPoliticoTaxa").css("border","1px solid red");
            $(".riscoPoliticoTaxa").focus();
            i =1;
        }
        else
        {
            $(".riscoPoliticoTaxa").css("border","");
        }
        if(riscoPoliticoPremio==='')
        {
            $(".riscoPoliticoPremio").css("border","1px solid red");
            $(".riscoPoliticoPremio").focus();
            i = 1;
        }
        else
        {
            $(".riscoPoliticoPremio").css("border","");
        }
    }
    else
    {
        $(".riscoPoliticoValor").css("border","");
        $(".riscoPoliticoTaxa").css("border","");
        $(".riscoPoliticoPremio").css("border","");
    }
    if(danoMaliciosoDesativado ===false)
    {
        if(danoMaliciosoValor ==='')
        {
            $(".danoMaliciosoValor").css("border","1px solid red");
            $(".danoMaliciosoValor").focus();
            i =1;
        }
        else
        {
            $(".danoMaliciosoValor").css("border","");
        }
        if(danoMaliciosoTaxa ==='')
        {
            $(".danoMaliciosoTaxa").css("border","1px solid red");
            $(".danoMaliciosoTaxa").focus();
            i =1;
        }
        else
        {
            $(".danoMaliciosoTaxa").css("border","");
        }
        if(danoMaliciosoPremio ==='')
        {
            $(".danoMaliciosoPremio").css("border","1px solid red");
            $(".danoMaliciosoTaxa").focus();
            i = 1;
        }
        else
        {
            $(".danoMaliciosoPremio").css("border","");
        }
    }
    else
    {
        $(".danoMaliciosoValor").css("border","");
        $(".danoMaliciosoTaxa").css("border","");
        $(".danoMaliciosoPremio").css("border","");
    }
    if(tumultoDesativado ===false)
    {
        if(tumultoValor ==='')
        {
            $(".tumultoValor").css("border","1px solid red");
            $(".tumultoValor").focus();
            i =1;
        }
        else
        {
            $(".tumultoValor").css("border","");
        }
        if(tumultoTaxa ==='')
        {
            $(".tumultoTaxa").css("border","1px solid red");
            $(".tumultoTaxa").focus();
            i =1;
        }
        else
        {
            $(".tumultoTaxa").css("border","");
        }
        if(tumultoPremio ==='')
        {
            $(".tumultoPremio").css("border","1px solid red");
            $(".tumultoPremio").focus();
            i =1;
        }
        else
        {
            $(".tumultoPremio").css("border","");
        }     
    }
    else
    {
        $(".tumultoValor").css("border","");
        $(".tumultoTaxa").css("border","");
        $(".tumultoPremio").css("border","");
    }
    if(incendioFdesativado ===false)
    {
        if(incendioFvalor ==='')
        {
            $(".incendioFvalor").css("border","1px solid red");
            $(".incendioFvalor").focus();
            i = 1;
        }
        else
        {
            $(".incendioFvalor").css("border","");
        }
        if(incendioFtaxa ==='')
        {
            $(".incendioFtaxa").css("border","1px solid red");
            $(".incendioFtaxa").focus();
            i = 1;
        }
        else
        {
            $(".incendioFtaxa").css("border","");
        }
        if(incendioFpremio==='')
        {
            $(".incendioFpremio").css("border","1px solid red");
            $(".incendioFpremio").focus();
            i =1;
        }
        else
        {
            $(".incendioFpremio").css("border","");
        }
    }
    else
    {
        $(".incendioFvalor").css("border","");
        $(".incendioFtaxa").css("border","");
        $(".incendioFpremio").css("border","");
    }
    if(explosaoExDesativado ===false)
    {
        if(explosaoExValor==='')
        {
            $(".explosaoExValor").css("border","1px solid red");
            $(".explosaoExValor").focus();
            i = 1;
        }
        else
        {
            $(".explosaoExValor").css("border","");
        }
        if(explosaoExTaxa==='')
        {
            $(".explosaoExTaxa").css("border","1px solid red");
            $(".explosaoExTaxa").focus();
            i = 1;
        }
        else
        {
             $(".explosaoExTaxa").css("border","");
        }
        if(explosaoExPremio==='')
        {
            $(".explosaoExPremio").css("border","1px solid red");
            $(".explosaoExPremio").focus();
            i = 1;
        }
        else
        {
            $(".explosaoExPremio").css("border","");
            
        }
    }
    else
    {
        $(".explosaoExValor").css("border","");
        $(".explosaoExTaxa").css("border","");
        $(".explosaoExPremio").css("border","");
    }
    if(roturaDesativado===false)
    {
        if(roturaValor ==='')
        {
            $(".roturaValor").css("border","1px solid red");
            $(".roturaValor").focus();
            i = 1;
        }
        else
        {
            $(".roturaValor").css("border","");
        }
        if(roturaTaxa ==='')
        {
            $(".roturaTaxa").css("border","1px solid red");
            $(".roturaTaxa").focus();
            i = 1;
        }
        else
        {
            $(".roturaTaxa").css("border","");
        }
        if(roturaPremio ==='')
        {
            $(".roturaPremio").css("border","1px solid red");
            $(".roturaPremio").focus();
            i = 1;
        }
        else
        {
            $(".roturaPremio").css("border","");
        }
    }
    else
    {
        $(".roturaValor").css("border","");
        $(".roturaTaxa").css("border","");
        $(".roturaPremio").css("border","");
    }
    return (i ===0) ? "preenchido" : "vazio";
   
}

function desavilitarAviaao()
{
    var checke=$(".aviaoId").is(":checked");
    
    if(checke===false)
    {
        $(".incendioAviaoValor").attr('disabled',true);
        $(".incendioAviaoTaxa").attr('disabled',true);
        $(".incendioAviaoTaxa").val("");
        $(".incendioAviaoValor").val("");
        $(".incendioAviaoPremio").val("");
    }
    else
    {
        $(".incendioAviaoValor").attr('disabled',false);
        $(".incendioAviaoTaxa").attr('disabled',false);
    }
}

function desavilitaColisao()
{
    var checke=$(".clisaoId").is(":checked");
    
    if(checke===false)
    {
        $(".colisaoValor").attr('disabled',true);
        $(".colisaoTaxa").attr('disabled',true);
        $(".colisaoTaxa").val("");
        $(".colisaoValor").val("");
        $(".colisaoPremio").val("");
    }
    else
    {
        $(".colisaoValor").attr('disabled',false);
        $(".colisaoTaxa").attr('disabled',false);
    }
}

function desavilitaExplosao()
{
    var checke=$(".explosaoId").is(":checked");
    
    if(checke===false)
    {
        $(".explosaoValor").attr('disabled',true);
        $(".explosaoTaxa").attr('disabled',true);
        $(".explosaoTaxa").val("");
        $(".explosaoValor").val("");
        $(".explosaoPremio").val("");
    }
    else
    {
        $(".explosaoValor").attr('disabled',false);
        $(".explosaoTaxa").attr('disabled',false);
    }
}


function desavilitaTerramotoId()
{
    var checke=$(".terramotoId").is(":checked");
    
    if(checke===false)
    {
        $(".terramotoValor").attr('disabled',true);
        $(".terramotoTaxa").attr('disabled',true);
        $(".terramotoTaxa").val("");
        $(".terramotoValor").val("");
        $(".terramotoPremio").val("");
    }
    else
    {
        $(".terramotoValor").attr('disabled',false);
        $(".terramotoTaxa").attr('disabled',false);
    }
}

function desavilitaTempestadeID()
{
    var checke=$(".tempestadeID").is(":checked");
    
    if(checke===false)
    {
        $(".tempestadeValor").attr('disabled',true);
        $(".tempestadeTaxa").attr('disabled',true);
        $(".tempestadeTaxa").val("");
        $(".tempestadeValor").val("");
        $(".tempestadePremio").val("");
    }
    else
    {
        $(".tempestadeValor").attr('disabled',false);
        $(".tempestadeTaxa").attr('disabled',false);
    }
}

function desavilitaafundamentoID()
{
    var checke=$(".afundamentoID").is(":checked");
    
    if(checke===false)
    {
        $(".afundamentoValor").attr('disabled',true);
        $(".afundamentoTaxa").attr('disabled',true);
        $(".afundamentoValor").val("");
        $(".afundamentoTaxa").val("");
        $(".afundamentoPremio").val("");
    }
    else
    {
        $(".afundamentoValor").attr('disabled',false);
        $(".afundamentoTaxa").attr('disabled',false);
    }
}

function desavilitaRiscoPolitico()
{
    var checke=$(".riscoPoliticoID").is(":checked");
    
    if(checke===false)
    {
        $(".riscoPoliticoValor").attr('disabled',true);
        $(".riscoPoliticoTaxa").attr('disabled',true);
        $(".riscoPoliticoValor").val("");
        $(".riscoPoliticoTaxa").val("");
        $(".riscoPoliticoPremio").val("");
    }
    else
    {
        $(".riscoPoliticoValor").attr('disabled',false);
        $(".riscoPoliticoTaxa").attr('disabled',false);
    }
}

function desavilitaDanoMalicioso()
{
    var checke=$(".danoMaliciosoId").is(":checked");
    
    if(checke===false)
    {
        $(".danoMaliciosoValor").attr('disabled',true);
        $(".danoMaliciosoTaxa").attr('disabled',true);
        $(".danoMaliciosoValor").val("");
        $(".danoMaliciosoTaxa").val("");
        $(".danoMaliciosoPremio").val("");
    }
    else
    {
        $(".danoMaliciosoValor").attr('disabled',false);
        $(".danoMaliciosoTaxa").attr('disabled',false);
    }
}

function desavilitaTumulto()
{
    var checke=$(".tumultoId").is(":checked");
    
    if(checke===false)
    {
        $(".tumultoValor").attr('disabled',true);
        $(".tumultoTaxa").attr('disabled',true);
        $(".tumultoValor").val("");
        $(".tumultoTaxa").val("");
        $(".tumultoPremio").val("");
    }
    else
    {
        $(".tumultoValor").attr('disabled',false);
        $(".tumultoTaxa").attr('disabled',false);
    }
}

function desavilitaIncendioF()
{
    var checke=$(".incendioFId").is(":checked");
    
    if(checke===false)
    {
        $(".incendioFvalor").attr('disabled',true);
        $(".incendioFtaxa").attr('disabled',true);
        $(".incendioFvalor").val("");
        $(".incendioFtaxa").val("");
        $(".incendioFpremio").val("");
    }
    else
    {
        $(".incendioFvalor").attr('disabled',false);
        $(".incendioFtaxa").attr('disabled',false);
    }
}

function desavilitaExplosaoEx()
{
    var checke=$(".explosaoExId").is(":checked");
    
    if(checke===false)
    {
        $(".explosaoExValor").attr('disabled',true);
        $(".explosaoExTaxa").attr('disabled',true);
        $(".explosaoExValor").val("");
        $(".explosaoExTaxa").val("");
        $(".explosaoExPremio").val("");
    }
    else
    {
        $(".explosaoExValor").attr('disabled',false);
        $(".explosaoExTaxa").attr('disabled',false);
    }
}

function desavilitaRoptura()
{
    var checke=$(".ropturaId").is(":checked");
    
    if(checke===false)
    {
        $(".roturaValor").attr('disabled',true);
        $(".roturaTaxa").attr('disabled',true);
        $(".roturaValor").val("");
        $(".roturaTaxa").val("");
        $(".roturaPremio").val("");
    }
    else
    {
        $(".roturaValor").attr('disabled',false);
        $(".roturaTaxa").attr('disabled',false);
    }
}

function IncendioAvancar()
{
    if(IncendioValidar() ==='preenchido')
        window.location.assign("GestSeg_NovoSeguroApolice.xhtml");
}

function IncendioNumeroRegistro(xhr, status, args)
{
    var relulte=args.registro;
    if(relulte ==='já existe')
    {
       $('html, body').animate({ scrollTop: 0 }, 'slow');
        $(".incendioNumeroRegistro").css("border","1px solid red");
        $(".incendioNumeroRegistro").focus();
    }
    if(relulte ==='já existe')
    {
        $(".incendioNumeroRegistro").css("border","");
    }
}

function riscos()
{
    $("input:checkbox[name='multi:dinheiroComb']").attr('checked', false);
    $("input:checkbox[name='multi:rouboComb']").attr('checked', false);
    $("input:checkbox[name='multi:incendioComb']").attr('checked', false);
}

function focoApoliceIncendio()
{
    $(".incendiosNumeroApolice").css("border", "1px solid red");
}

function topoIncendio()
{
    $('html, body').animate({ scrollTop: 0 }, 'slow');
}