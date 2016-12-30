$(document).ready(function(e){
 
    $(".rouboContinuar").click(function (e)
    {
       e.preventDefault();
        if(ContinuarRoubo() === 1)
             enviarDados();
    });
    
    $(".moreData").click(function(e)
    {   
       e.preventDefault();
       $(".mp-infoTable").fadeOut();
       
    });
    $(".rouboAdicionar").click(function (e)
    {
        e.preventDefault();
        if(RouboVerificarCamposTabela() ===1)
             dadosTabela();
         
           
    });
    
    $("input:text").click(function(e)
    {
       $(this).css("border",""); 
    });
     $(".rouboNumero").keyup(function(e){
        e.preventDefault();
        var expre = /[^0-9]/g;
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if ($(this).val().match(expre))
            $(this).val($(this).val().replace(expre,''));
   }); 
    $(".text").keyup(function(e){
        e.preventDefault();
        var expre = /[^0-9]/g;
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if ($(this).val().match(expre))
            $(this).val($(this).val().replace(expre,''));
   });
    $(".valorRoubo").keyup(function(e){
        e.preventDefault();
        var expre = /[^0-9]/g;
        var i =0;
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if ($(this).val().match(expre))
         {
            $(this).val($(this).val().replace(expre,''));
          
         }
   });

});


function moverTopo()
{
    $('html, body').animate({ scrollTop: 0 }, 'slow');
}
function ContinuarRoubo()
{
    var preenchido = 1;
    var tempoO = $(".TempoOcupacao").val();
    var dataIns = $(".DataInspecao").val();
    var enderecoE = $(".enderecoEdificio").val();
    var cofreValor = $(".cofreValor").val();
    var marcaCofre = $(".cofreMarca").val();
    var tipoEdificioCampoAtivo = $(".rouboTipoEdificioEspecificar").is(":disabled");
    var campoAlarmeAtivo = $(".rouboAlarme").is(":disabled");
    var campoPerdaSofridaAtivo = $(".rouboPerda").is(":disabled");
    var rouboMedidaTomdaAtivo = $(".rouboMedidaT").is(":disabled");
    var valor1 = $(".rouboTipoEdificioEspecificar").val();
    var valor2 =  $(".rouboAlarme").val();
    var valor3 = $(".rouboPerda").val();
    var valor4 = $(".rouboMedidaT").val();
    var dataAquisicao = "input:text[name='forma:dataAqRoubo_input']";
    var numeroRegistro = $(".rouboNumeroRegistro").val();
    var cabecalho = $(".seguroRouboCabecalho").is(":visible");
    var apolice = $(".rouboApolice").val();
    
    if(cabecalho === true)
    {
        if(apolice==="")
        {
            preenchido =0;
            $(".rouboApolice").css("border","1px solid red");
            $('html, body').animate({ scrollTop: 0 }, 'slow');
        }
        if(numeroRegistro ==="")
       {
           preenchido =0;
           $(".rouboNumeroRegistro").css("border","1px solid red");
           $('html, body').animate({ scrollTop: 0 }, 'slow');
       }
       else
           $(".rouboNumeroRegistro").css("border","");
    }
   
    if($(dataAquisicao).val() ==='')
    {
        $(dataAquisicao).css("border","1px solid red");
        preenchido = 0;
    }
    else
        $(dataAquisicao).css("border","");
    if(enderecoE ==="")
    {
        preenchido = 0;
        $(".enderecoEdificio").css("border","1px solid red");
    }
    else
        $(".enderecoEdificio").css("border","");
    if(tempoO ==="")
    {
        preenchido = 0;
        $(".TempoOcupacao").css("border","1px solid red");
    }
    else
        $(".TempoOcupacao").css("border","");
    if(dataIns ==="")
    {
        preenchido = 0;
        $(".DataInspecao").css("border","1px solid red");
    }
    else
        $(".DataInspecao").css("border","");
    if(cofreValor ==="")
    {
        preenchido = 0;
        $(".cofreValor").css("border","1px solid red");
    }
    else
        $(".cofreValor").css("border","");
    if(marcaCofre ==="")
    {
        preenchido = 0;
        $(".cofreMarca").css("border","1px solid red");
    }
    else
        $(".cofreMarca").css("border",""); 
    if(tipoEdificioCampoAtivo === false)
    {
        if(valor1 ==="")
        {
            preenchido = 0;
            $(".rouboTipoEdificioEspecificar").css("border","1px solid red");
        }
        else
             $(".rouboTipoEdificioEspecificar").css("border","");
    }

    if(campoAlarmeAtivo ===false)
    {

        if(valor2 ==="")
        {
            preenchido = 0;
            $(".rouboAlarme").css("border","1px solid red");
        }
        else
            $(".rouboAlarme").css("border","");
    }
 
    if(campoPerdaSofridaAtivo ===false)
    {
  
        if(valor3 ==="")
        {
            preenchido = 0;
            $(".rouboPerda").css("border","1px solid red");
        }
        else
            $(".rouboPerda").css("border","");
    }
    if(rouboMedidaTomdaAtivo ===false)
    {

        if(valor4 ==="")
        {
            preenchido = 0;
            $(".rouboMedidaT").css("border","1px solid red");
        }
        else
            $(".rouboMedidaT").css("border","");
    }
    return preenchido;
}

function adicionarTabelaRoubo()
{
    $(".rouboQuantidade").val("");
    $(".rouboQuantidade").css("border","");
    $(".rouboValor").val("");
    $(".rouboValor").css("border","");
    $(".rouboModelo").val("");
    $(".rouboModelo").css("border","");
    $(".rouboDescricao").val(""); 
    $(".rouboDescricao").css("border","");
}

function RouboVerificarCamposTabela()
{
    var preenchido =1;
    var quantidade = $(".rouboQuantidade").val();
    var valor = $(".rouboValor").val();
    var modelo = $(".rouboModelo").val();
    var descricao = $(".rouboDescricao").val();
  
    if(quantidade ==="")
    {
        preenchido = 0;
        $(".rouboQuantidade").css("border","1px solid red");
    }
    else
        $(".rouboQuantidade").css("border","");
    if(valor ==="")
    {
           preenchido = 0;
        $(".rouboValor").css("border","1px solid red");
    }
    else
            $(".rouboValor").css("border","");
    if(modelo ==="")
    {
           preenchido = 0;
        $(".rouboModelo").css("border","1px solid red");
    }
    else
        $(".rouboModelo").css("border","");
    if(descricao ==="")
    {
           preenchido = 0;
        $(".rouboDescricao").css("border","1px solid red");
    }
    else
    {
        $(".rouboDescricao").css("border","");
    }
    return preenchido;
}

function verificarNumeroRegistroRoubo(xhr, status, args)
{
    var valor = args.numeroRegistro;
    if(valor ==="já existe")
    {
        $(".rouboNumeroRegistro").css("border","1px solid red");
        $(".rouboNumeroRegistro").focus();
    }
    else
    {
        $(".rouboNumeroRegistro").css("border","none");
    }
    
}

function handleComplete(xhr, status, args)
{
    var info = args.continuar;
    if(info !=="true")
    {
         $(".rouboNumeroRegistro").css("border","1px solid red");
    }
    else
    {
        $(".rouboNumeroRegistro").css("border","none");
    }
}

function redirecionar()
{
    window.location.href ="GestSeg_NovoSeguroApolice.xhtml";
}

function enviarDados()
{
    var dataAquisicao = "input:text[name='forma:dataAqRoubo_input']";
    rouboInfo([{name:'numeroRegistro',value:$(".rouboNumeroRegistro").val()},  
               {name:'apolice',value:$(".rouboApolice").val()},
               {name:'moeda', value:$(".moedaRoubo").val()},
               {name:'especificarTipoEdificio', value:$(".rouboTipoEdificioEspecificar").val()},
               {name:'tempoOcupacao', value:$(".TempoOcupacao").val()},
               {name:'dataInspecao', value:$(".DataInspecao").val()},
               {name:'endereço', value:$(".enderecoEdificio").val()},
               {name:'zelador', value:$(".zelador").val()},
               {name:'mercadorias', value:$(".mercadorias").val()},
               {name:'bensGuardados', value:$(".bens").val()},
               {name:'valorCofre', value:$(".cofreValor").val()},
               {name:'marca', value:$(".cofreMarca").val()},
               {name:'dataAquisicao', value:$(dataAquisicao).val()},
               {name:'descricaoPerda', value:$(".rouboPerda").val()},
               {name:'medidasTomadasAssalto', value:$(".rouboMedidaT").val()}]);
}
function riscos()
{
    $("input:checkbox[name='multi:dinheiroComb']").attr('checked', false);
    $("input:checkbox[name='multi:rouboComb']").attr('checked', false);
    $("input:checkbox[name='multi:incendioComb']").attr('checked', false);
}

function dadosTabela()
{

    addTable([{name:'quantidade',value:$(".rouboQuantidade").val()},  
               {name:'valor',value:$(".rouboValor").val()},
               {name:'modelo', value:$(".rouboModelo").val()},
               {name:'descrição', value:$(".rouboDescricao").val()}]);
}
function mostrarProcessamentoRoubo()
{
    $(".modalProcess").show();
}
function fecharProcessamentoRoubo()
{
     $(".modalProcess").hide();
}

function apoliceRoubo()
{
    $(".rouboApolice").css("border","1px solid red");
}

function modalInfoRoubo()
{
    $(".mp-infoTable").fadeIn();
}