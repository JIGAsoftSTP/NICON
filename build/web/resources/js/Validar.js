/* 
 *Created by Helcio Guadalupe
 */
var model = "input:text[name='veiculoFormulario:veiculoModelo_input']";
var mac = "input:text[name='veiculoFormulario:veiculoMarca_input']";
var matricula = "input:text[name='veiculoFormulario:veiculoM_input']";
var campoApoliceDesativado = $("input:text[name='veiculoFormulario:veiculoApolice']").is(":disabled");
$(document).ready(function(e){
    
   $(".moreData").click(function(e)
    {
        e.preventDefault();
          $(".mp-infoTable").fadeOut();
    });
    $("input:text").click(function(e)
    {
      $(this).css("border","");  
    });
    $(".adicionarVeiculo").click(function (e)
    {
        e.preventDefault();

        var campoApolice = $("input:text[name='veiculoFormulario:veiculoApolice']").val();
        var cobertura = $("input:checked[name='veiculoFormulario:veiculoTipoC'").val();
        
        if(campoApoliceDesativado === false)
        {
            $("input:text[name='veiculoFormulario:veiculoApolice']").prop("required", true);
            if(campoApolice ==='')
                 $("input:text[name='veiculoFormulario:veiculoApolice']").css("border","1px solid red");
            else
                 $("input:text[name='veiculoFormulario:veiculoApolice']").css("border","");
        }
            
        if(cobertura ==="41") 
        {
            coberturaContraTerceiros();
            dadosVeiculo();
        }
        else
            if(cobertura ==="42")
            {
                coberturaTodosRiscos(); 
                dadosVeiculo();
            }
            else
            {
                coberturaCompreensivo();
                dadosVeiculo();
                
            }
    });
    
    $(".veiculoCapacidade").blur(function (e)
    {
       e.preventDefault();
       var capacidade = $(".veiculoCapacidade").val();
       if(capacidade ==='')
       {
           if(capacidade>250)
           {
               $(".veiculoCapacidade").val("");
           }
       }
    });
    


    $(".veiculoLimiteResp").attr('disabled',true);
     var limiteResp = $(".veiculoTC").html();
     if(limiteResp==="terceiros")
     {   
          $(".veiculoLimiteResp").val("Ilimitado");
     }
              
   $(".veiculoValorAtual").keyup(function (e)
   {
        var limiteResp = $(".veiculoTC").html();
        if(limiteResp ==="todos riscos")
        {
            $(".veiculoLimiteResp").val("");
            $(".veiculoValorP").val("");
        }
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

function coberturaCompreensivo()
{
    $(".veiculoValorP").attr('disabled',false);
    var numRegistro = $(".veiculoNumR").val();
    var certificado = $(".certificadoVeiculo").val();
    var modelo = $(model).val();
    var marca = $(mac).val();
    var chassi =  $(".veiculoChassi").val();
    var capacidade = $(".veiculoCapacidade").val();
    var limite = $(".veiculoLimiteResp").val();
    var anoF = $(".veiculoAnoF").val();
    var anoCompra = $(".veiculoAnoCompra").val();
    var valorCompra = $(".veiculoValorCompra").val();
    var valorAtual = $(".veiculoValorAtual").val();
    var valorP = $(".veiculoValorP").val();
    if(valorP ==='')
    {
        $(".veiculoValorP").css("border","1px solid red");
    }
    else
    {
         $(".veiculoValorP").css("border","");
    }
    if(certificado === "")
        $(".certificadoVeiculo").css("border","1px solid red");
    else
         $(".certificadoVeiculo").css("border","");
   if(numRegistro ==='')
    {
        $(".veiculoNumR").css("border","1px solid red");
    }
    else
    {
         $(".veiculoNumR").css("border","");
    }
    if($(matricula).val() ==='')
    {
         $(matricula).css("border","1px solid red");
    }
    else
    {
         $(matricula).css("border","");
    }
    if(modelo ==='')
    {
         $(model).css("border","1px solid red");
    }
    else
    {
         $(model).css("border","");
    }
    if(marca ==='')
    {
        $(mac).css("border","1px solid red");
    }
    else
    {
        $(mac).css("border","");
    }
    if(chassi ==='')
    {
        $(".veiculoChassi").css("border","1px solid red");
    }
    else
    {
        $(".veiculoChassi").css("border","");
    }
    if(capacidade ==='')
    {
        $(".veiculoCapacidade").css("border","1px solid red");
    }
    else
    {
        $(".veiculoCapacidade").css("border","");
    }
    if(limite ==='')
    {
        $(".veiculoLimiteResp").css("border","1px solid red");
    }
    else
    {
         $(".veiculoLimiteResp").css("border","");
    }
//    if(anoF ==='')
//    {
//        $(".veiculoAnoF").css("border","1px solid red");
//    }
//    else
//    {
//        $(".veiculoAnoF").css("border","");
//    }
//    if(anoCompra ==='')
//    {
//        $(".veiculoAnoCompra").css("border","1px solid red");
//    }
//    else
//    {
//        $(".veiculoAnoCompra").css("border","");
//    }
    if(valorCompra ==='')
    {
        $(".veiculoValorCompra").css("border","1px solid red");
    }
    else
    {
        $(".veiculoValorCompra").css("border","");
    }
    if(valorAtual ==='')
    {
        $(".veiculoValorAtual").css("border","1px solid red");
    }
    else
    {
        $(".veiculoValorAtual").css("border","");
    }
}

function limparCamposVeiculo()
{
    $(matricula).val("");
    $(model).val("");
    $(mac).val("");
    $(".veiculoMotor").val("");
    $(".veiculoChassi").val("");
    $(".veiculoCapacidade").val("");
    $(".veiculoAnoF").val("");
    $(".veiculoAnoCompra").val("");
    $(".veiculoValorCompra").val("");
    $(".veiculoValorAtual").val("");
}



function cobertura(xhr, status, args)
{
    var cobertura = args.cobertura;
    if(cobertura ==="contra terceiros")
    {
        limpar();
        $(".veiculoValorP").attr('disabled',false);
        $(".veiculoLimiteResp").val("Ilimitado");
        $(".veiculoLimiteResp").attr('disabled',true);
    }
    else
        if(cobertura ==="Todos os riscos")
        {
            limpar();
            $(".veiculoValorP").attr('disabled',true);
            $(".veiculoLimiteResp").attr('placeholder','Taxa');
            $(".veiculoLimiteResp").attr('disabled',false);
            $(".veiculoLimiteResp").val("");  
        }
        else
        {
            limpar();
              $(".veiculoValorP").attr('disabled',false);
             $(".veiculoLimiteResp").attr('placeholder','Limite de responsabilidade');
             $(".veiculoLimiteResp").attr('disabled',false);
             $(".veiculoLimiteResp").val("");  
        }
}

function adicionado(xhr, status, args)
{
    var i = 0;
    var numMatricula = args.matricula;
    var numMotor = args.motor;
    var numChassi = args.chassi;
    var registro = args.numeroR;
    var anoC = args.AnoCompra;
    if(registro ==="já existe")
    {
          $(".veiculoNumR").css("border","1px solid red");
          i = 1;
    }
    if(numMatricula==="já existe")
    {
        $(matricula).css("border","1px solid red");
        i =1;
    }
    if(numMotor ==="já existe")
    {
          $(".veiculoMotor").css("border","1px solid red");
          i = 1;
    }
    if(numChassi ==="já existe")
    {
          $(".veiculoChassi").css("border","1px solid red");
          i = 1;
    }
    if(anoC ==="inválido")
    {
         $(".veiculoAnoCompra").css("border","1px solid red");
         i = 1;
    }
}

function limpar()
{
    $(matricula).val("");
     $(model).val("");
     $(mac).val("");
     $(".veiculoMotor").val("");
     $(".veiculoChassi").val("");
     $(".veiculoCapacidade").val("");
     $(".veiculoLimiteResp").val("");
     $(".veiculoAnoF").val("");
     $(".veiculoAnoCompra").val("");
     $(".veiculoValorCompra").val("");
     $(".veiculoValorAtual").val("");
     $(".veiculoValorP").val("");
     
     
     $(":text" ,$("#veiculoFormulario")).each(function ()
     {
        $(this).css("border",""); 
     });
}

function verificarNumeroRegistro(xhr, status, args)
{
    var valor = args.numeroR;
    if(valor ==="já existe")
    {
        $(".veiculoNumR").css("border","1px solid red");
        $(".veiculoNumR").focus();
        $(".veiculoNumR").attr('title','Número de registro já existe');
    }
    else
    {
         $(".veiculoNumR").css("border","");
         $(".veiculoNumR").attr('title','');
    }
}

function verificarMatricula(xhr, status, args)
{
    var numMatricula = args.matricula;
    if(numMatricula ==="já existe")
    {
        $(matricula).css("border","1px solid red");
        $(matricula).attr('title','Número de matricula já existe');
    }
    else
    {
        $(matricula).css("border","");
        $(matricula).attr('title','');
    }
}

function verificarMotor(xhr, status, args)
{
    var numMotor = args.motor;
    if(numMotor==="já existe")
    {
        $(".veiculoMotor").css("border","1px solid red");
        $(".veiculoMotor").attr('title','Número de motor já existe');
    }
    else
    {
       $(".veiculoMotor").css("border","");
       $(".veiculoMotor").attr('title','');
    }
}

function verificarChassi(xhr, status, args)
{
    var numChassi = args.chassi;
    if(numChassi ==="já existe")
    {
        $(".veiculoChassi").css("border","1px solid red");
        $(".veiculoChassi").attr('title','Número de chassi já existe');
    }
    else
    {
        $(".veiculoChassi").css("border","");
        $(".veiculoChassi").attr('title','');
    }
}

function AnoCompraValido()
{
     $(".veiculoAnoCompra").css("border","");
     $(".veiculoAnoCompra").attr('title','');
}
function validarAnoCompraInvalido()
{
    $(".veiculoAnoCompra").val("");
}






function veiculoSeguinte()
{
      var valor1 = $(".veiculoAbrange").is(":disabled");
     var valor2 = $(".veiculoPassageiro").is(":disabled");
     var valor3= $(".veiculoDono").is(":disabled");
     var valor4 = $(".veiculoAlteracao").is(":disabled");
     var registro = $(".veiculoAdicionado").html();
      var info1 = $(".veiculoAbrange").val();
      var info2 = $(".veiculoPassageiro").val();
      var info3 = $(".veiculoDono").val();
      var info4 = $(".veiculoAlteracao").val();
      var i = 0;
     if(registro==="adicionado")
     {
         if(valor1 ===false)
         {
             if(info1==="")
             {
                  $(".veiculoAbrange").css("border","1px solid red");
                  i = 1;
             }
             else
                 $(".veiculoAbrange").css("border","");
         }
         if(valor2===false)
         {
             if(info2==="")
             {
                  $(".veiculoPassageiro").css("border","1px solid red");
                        i = 1;
             }
             else
                 $(".veiculoPassageiro").css("border","");      
         }
          if(valor3===false)
         {
             if(info3==="")
             {
                   $(".veiculoDono").css("border","1px solid red");
                         i = 1;
             }
             else
                 $(".veiculoDono").css("border","");
         }
         
          if(valor4===false)
         {
             if(info4==="")
             {
                    $(".veiculoAlteracao").css("border","1px solid red");
                     i = 1;
             }
             else
                $(".veiculoAlteracao").css("border","");
         }
         if(i ===0)
             window.location.href ="GestSeg_NovoSeguroApolice.xhtml";
     }
     else 
         moveTop();
}

function verificarNumeroRegistroRespPublica(xhr, status, args)
{
    var r = args.numeroR;
    if(r ==='já existe')
    {
        $(".RespPublicaNumRegistro").css("border","1px solid red");
    }
    else
    {
        $(".RespPublicaNumRegistro").css("border","");
    }    
}

function validarAnofabrico()
{
     $(".veiculoAnoF").val("");
}


function CapacidadeInValido()
{
    $(".veiculoCapacidade").val("");
}

function coberturaContraTerceiros()
{
    $(".veiculoValorP").attr('disabled',false);
    var numRegistro = $(".veiculoNumR").val();
    var certificado =  $(".certificadoVeiculo").val();
    var modelo = $(model).val();
    var marca = $(mac).val();
    var chassi =  $(".veiculoChassi").val();
    var capacidade = $(".veiculoCapacidade").val();
     var valorP = $(".veiculoValorP").val();
    
      if(certificado === "")
        $(".certificadoVeiculo").css("border","1px solid red");
    else
         $(".certificadoVeiculo").css("border","");
    if(valorP ==='')
        $(".veiculoValorP").css("border-color","red");
    else
         $(".veiculoValorP").css("border-color","");
    if(numRegistro ==='')
        $(".veiculoNumR").css("border","1px solid red");
    else
         $(".veiculoNumR").css("border","");
    if($(matricula).val() === '')
        $(matricula).css("border","1px solid red");
    else
        $(matricula).css("border","");
    if(chassi ==='')
        $(".veiculoChassi").css("border","1px solid red");
    else
        $(".veiculoChassi").css("border","");
    if(capacidade ==='')
        $(".veiculoCapacidade").css("border","1px solid red");
    else
        $(".veiculoCapacidade").css("border","");
    
}

function coberturaTodosRiscos()
{
    var certificado =  $(".certificadoVeiculo").val();
    var numRegistro = $(".veiculoNumR").val();
    var modelo = $(model).val();
    var marca = $(mac).val();
    var chassi =  $(".veiculoChassi").val();
    var capacidade = $(".veiculoCapacidade").val();
    var limite = $(".veiculoLimiteResp").val();
    var anoF = $(".veiculoAnoF").val();
    var anoCompra = $(".veiculoAnoCompra").val();
    var valorCompra = $(".veiculoValorCompra").val();
    var valorAtual = $(".veiculoValorAtual").val();
    
     if(certificado === "")
        $(".certificadoVeiculo").css("border","1px solid red");
    else
         $(".certificadoVeiculo").css("border","");
    if(numRegistro ==='')
    {
        $(".veiculoNumR").css("border","1px solid red");
    }
    else
    {
         $(".veiculoNumR").css("border","");
    }
    if($(matricula).val() ==='')
    {
         $(matricula).css("border","1px solid red");
    }
    else
    {
         $(matricula).css("border","");
    }
    if(modelo ==='')
    {
         $(model).css("border","1px solid red");
    }
    else
    {
         $(model).css("border","");
    }
    if(marca ==='')
    {
        $(mac).css("border","1px solid red");
    }
    else
    {
        $(mac).css("border","");
    }
    if(chassi ==='')
    {
        $(".veiculoChassi").css("border","1px solid red");
    }
    else
    {
        $(".veiculoChassi").css("border","");
    }
    if(capacidade ==='')
    {
        $(".veiculoCapacidade").css("border","1px solid red");
    }
    else
    {
        $(".veiculoCapacidade").css("border","");
    }
    if(limite ==='')
    {
        $(".veiculoLimiteResp").css("border","1px solid red");
    }
    else
    {
         $(".veiculoLimiteResp").css("border","");
    }
//    if(anoF ==='')
//    {
//        $(".veiculoAnoF").css("border","1px solid red");
//    }
//    else
//    {
//        $(".veiculoAnoF").css("border","");
//    }
//    if(anoCompra ==='')
//    {
//        $(".veiculoAnoCompra").css("border","1px solid red");
//    }
//    else
//    {
//        $(".veiculoAnoCompra").css("border","");
//    }
    if(valorCompra ==='')
    {
        $(".veiculoValorCompra").css("border","1px solid red");
    }
    else
    {
        $(".veiculoValorCompra").css("border","");
    }
    if(valorAtual ==='')
    {
        $(".veiculoValorAtual").css("border","1px solid red");
    }
    else
    {
        $(".veiculoValorAtual").css("border","");
    }
}

function valorPremioVeiculo(xhr, status,args)
{
    var valor = args.valorP;
    if(valor !=="nulo")
    {
        $(".veiculoValorP").val(valor);
    }
    else
    {
        limparCalculoPremioVeiculo();
    }
  
}

function limparCalculoPremioVeiculo()
{
    $(".veiculoValorAtual").val("");
    $(".veiculoValorP").val("");
    $(".veiculoLimiteResp").val("");
}

function dadosVeiculo()
{
    
    enviarDadosVeiculo( [{name:'apoliceEstadoCampo', value:(campoApoliceDesativado === true)? 'desativado' :'ativado'},
                    {name:'apolice', value: $("input:text[name='veiculoFormulario:veiculoApolice']").val()} ,
                        {name:'numero registro', value:$(".veiculoNumR").val()},
                        {name:'moeda', value:$(".veiculoMoeda").val()},
                        {name:'numeroAutomovel',value:$(matricula).val()},
                        {name:'marca', value:$(mac).val()},
                        {name:'modelo', value:$(model).val()},
                        {name:'numMotor',value:$(".veiculoMotor").val()},
                        {name:'chassi', value:$(".veiculoChassi").val()},
                        {name:'anoFabrico', value:$(".veiculoAnoF").val()},
                        {name:'anoCompra', value:$(".veiculoAnoCompra").val()},
                        {name:'capacidade', value:$(".veiculoCapacidade").val()},
                        {name:'valorCompra',value:$(".veiculoValorCompra").val()},
                        {name:'valorAtual', value:$(".veiculoValorAtual").val()},
                        {name:'limiteResp', value:$(".veiculoLimiteResp").val()},
                        {name:'certificado', value:$(".certificadoVeiculo").val()},
                        {name:'valorPremio', value:$(".veiculoValorP").val()}]);
}

function limparCampos(tipo)
{
    if(tipo==='terceiros')
    {
        $(".veiculoLimiteResp").val("Ilimitado");
        $(matricula).val("");
        $(model).val("");
        $(mac).val("");
        $(".veiculoMotor").val("");
        $(".veiculoChassi").val("");
        $(".veiculoCapacidade").val("");
        $(".veiculoAnoF").val("");
        $(".veiculoAnoCompra").val("");
        $(".veiculoValorCompra").val("");
        $(".veiculoValorAtual").val("");
        $(".veiculoValorP").val("");
    }
    else if(tipo==='TR')
    {
        $(matricula).val("");
        $(model).val("");
        $(mac).val("");
        $(".veiculoMotor").val("");
        $(".veiculoChassi").val("");
        $(".veiculoCapacidade").val("");
        $(".veiculoAnoF").val("");
        $(".veiculoAnoCompra").val("");
        $(".veiculoValorCompra").val("");
        $(".veiculoValorAtual").val("");
        $(".veiculoValorP").val("");
        $(".veiculoLimiteResp").val("");
    }
    else
    {
        $(matricula).val("");
        $(model).val("");
        $(mac).val("");
        $(".veiculoMotor").val("");
        $(".veiculoChassi").val("");
        $(".veiculoCapacidade").val("");
        $(".veiculoAnoF").val("");
        $(".veiculoAnoCompra").val("");
        $(".veiculoValorCompra").val("");
        $(".veiculoValorAtual").val("");
        $(".veiculoValorP").val("");
        $(".veiculoLimiteResp").val("");
}
}

function mostrarModalInfo()
{
    $(".mp-infoTable").fadeIn();
}

function apoliceBorda()
{
    $(".campoApoliceVeiculo").css("border","1px solid red");
}
// alterar as informações adicionadas na tabela
function editar(numRegistroAutomovel,chassi,valorCompra,marca,anoFabrico,valorAtualCompra,modelo,anoCompra,limiteResp,motor,capacidade,valorPremio )
{
    $(matricula).val(numRegistroAutomovel);
    $(".veiculoChassi").val(chassi);
    $(".veiculoValorCompra").val(valorCompra);
    $(mac).val(marca);
    $(".veiculoAnoF").val(anoFabrico);
    $(".veiculoValorAtual").val(valorAtualCompra);
    $(model).val(modelo);
    $(".veiculoAnoCompra").val(anoCompra);
    $(".veiculoLimiteResp").val(limiteResp);
    $(".veiculoMotor").val(motor);
    $(".veiculoCapacidade").val(capacidade);
    $(".veiculoValorP").val(valorPremio);
}

function carregarVeiculo(marca, modelo,numMotor,chassi,anoFabrico,anoCompra,capacidade,valorCompra,valorAtualCompra)
{
    $(".veiculoChassi").val(((chassi === 'null') ? '' : chassi));
    $(".veiculoValorCompra").val(((valorCompra === 'null') ? '' : valorCompra));
    $(mac).val(((marca === 'null') ? '' : marca));
    $(".veiculoAnoF").val(((anoFabrico === 'null') ? '' :anoFabrico));
    $(".veiculoValorAtual").val(((valorAtualCompra === 'null') ? '' : valorAtualCompra));
    $(model).val(((modelo === 'null') ? '' : modelo));
    $(".veiculoAnoCompra").val(((anoCompra === 'null') ? '' :anoCompra));
    $(".veiculoMotor").val(((numMotor === 'null') ? '' : numMotor));
    $(".veiculoCapacidade").val(((capacidade === 'null') ? '' :capacidade));
}