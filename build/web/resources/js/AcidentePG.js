/* 
 *Created by Helcio Guadalupe
 */
    var valido = 0;
   var dataNasc = "input:text[name='acidenteF:AcidenteDataNasc_input']";
$(document).ready(function(e){
   
    estadoInicial();
    $(".acidenteAdicionar").click(function(e)
   {
        e.preventDefault();
        validarCampos();
        if(valido===1)
            enviarDadosAcidente('preenchdio');
        else
            enviarDadosAcidente('vazio');
   });
   
    $(".moreData").click(function (e)
    {
       $(".mp-infoTable").fadeOut();
    });
    
    $("input:text").click(function (e)
    {
        e.preventDefault();
        $(this).css("border",""); 
    });
    $(".numeroAC").keyup(function(e){
        e.preventDefault();
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if(!$.isNumeric($(this).val()))
            $(this).val("");
   });
   
 $(".acidenteTaxa100").keyup(function(e){
        if($(this).val()>100||$(this).val()<0)
        {
            $(this).val("");
        }
   });
});

function estadoInicial()
{
     var estadoCampoAcidentes = $(".acidenteUltimosAnos").is(":disabled");  
    var estadoCampoDefeito = $(".acidenteDefeitosFCampo").is(":disabled");  
    $("input:radio[name='acidenteF:anos']").each(function ()
        {
            if(estadoCampoAcidentes === true)
            {
                if($(this).val()==="N")
                {
                     $(this).attr('checked', true);
                }  
            }
            else
            {
                if($(this).val()==="Y")
                {
                     $(this).attr('checked', true);
                }  
            }
                 
        });
    $("input:radio[name='acidenteF:r']").each(function ()
        {
            if(estadoCampoDefeito === true)
            {
                if($(this).val()==="N")
                {
                     $(this).attr('checked', true);
                }  
            }
            else
            {
                if($(this).val()==="Y")
                {
                     $(this).attr('checked', true);
                }  
            }
                 
        });
    $("input:radio[name='acidenteF:radio1']").each(function ()
        {
            if($(this).val()==="2")
            {
                 $(this).attr('checked', true);
            }             
        });
    $("input:radio[name='acidenteF:acidenteLis1']").each(function ()
        {
            if($(this).val()==="N")
            {
                 $(this).attr('checked', true);
            }             
        });
}
function validarCampos()
{

    var numeroR = $(".acidenteNumeroRegistro").val();
    var nome = $(".acidenteNome").val();
    var categoria = $(".acidenteCategoria").val();
    var prof = $(".acidenteProfissao").val();
    var valorMorte = $(".morte").val();
    var taxaMorte = $("input:text[name='acidenteF:AcidenteTaxaMorte']").val();
    var incapTemp = $("input:text[name='acidenteF:AcidenteIncapTemp']").val();
    var taxaIncapTemp = $("input:text[name='acidenteF:AcidenteTaxaIncapTemp']").val();
    var despesaMedica =$("input:text[name='acidenteF:AcidenteDespesa']").val();
    var taxaDespesa = $("input:text[name='acidenteF:AcidenteTaxaDespesa']").val();
    var imcapPermanente = $("input:text[name='acidenteF:AcidenteIncapPermanente']").val();
    var taxaIncapPermanente = $("input:text[name='acidenteF:AcidenteTaxaIncapPermanente']").val();
    var custoRep = $("input:text[name='acidenteF:AcidenteCustoRep']").val();
    var taxaCustoRep =$("input:text[name='acidenteF:AcidenteTaxaCustoRep']").val();
    var campoDefeito = $(".acidenteDFMV").val();
    var campoAcidente = $(".acidenteOU3AV").val();
    var campoAcidenteAtivo = $(".acidenteUltimosAnos").is(":disabled");
    var campoAci = $(".acidenteOU3AV").val();
    var campoDefeitoAtivo = $(".acidenteDefeitosFCampo").is(":disabled");
    var radioAcidente = $("input:radio[name='acidenteF:anos']").val();
    var numeroApolice = $(".acidenteNumeroApolice").val();
    
    if(numeroApolice ==="")
        $(".acidenteNumeroApolice").css("border","1px solid red");
    else
    {
        $(".acidenteNumeroApolice").css("border","");
        valido = 1;
    }   
    
    if(numeroR==='')
    {
        $(".acidenteNumeroRegistro").css("border","1px solid red");
    }
    else
    {
        valido = 1;
        $(".acidenteNumeroRegistro").css("border","");
    }
      if(nome==='')
    {
        $(".acidenteNome").css("border","1px solid red");
    }
    else
    {
        valido = 1;
        $(".acidenteNome").css("border","");
    }
    if(categoria==='')
    {
        $(".acidenteCategoria").css("border","1px solid red");
    }
    else
    {
        valido = 1;
        $(".acidenteCategoria").css("border","");
    }
    if(prof==='')
    {
        $(".acidenteProfissao").css("border","1px solid red");
    }
    else
    {
        valido = 1;
        $(".acidenteProfissao").css("border","");
    }
    if($("input:text[name='acidenteF:AcidenteDataNasc_input']").val()==='')
    {       
        $("input:text[name='acidenteF:AcidenteDataNasc_input']").css("border","1px solid red");
    }
    else
    {
        valido = 1;
        $("input:text[name='acidenteF:AcidenteDataNasc_input']").css("border","");
    }
    if(valorMorte==='')
    {
        $(".morte").css("border","1px solid red");
    }
    else
    {
        valido = 1;
         $(".morte").css("border","");
    }
    if(taxaMorte==='')
    {
      $("input:text[name='acidenteF:AcidenteTaxaMorte']").css("border","1px solid red");
    }
    else
    {
        valido = 1;
      $("input:text[name='acidenteF:AcidenteTaxaMorte']").css("border","");
    }
    if(incapTemp==='')
    {
        $("input:text[name='acidenteF:AcidenteIncapTemp']").css("border","1px solid red");
    }
    else
    {
        valido = 1;
        $("input:text[name='acidenteF:AcidenteIncapTemp']").css("border","");
    }
    if(taxaIncapTemp==='')
    {
        $("input:text[name='acidenteF:AcidenteTaxaIncapTemp']").css("border","1px solid red");
    }
    else
    {
        valido = 1;
        $("input:text[name='acidenteF:AcidenteTaxaIncapTemp']").css("border","");
    }
    if(despesaMedica==='')
    {
        $("input:text[name='acidenteF:AcidenteDespesa']").css("border","1px solid red");
    }
    else
    {
        valido =1;
        $("input:text[name='acidenteF:AcidenteDespesa']").css("border","");
    }
    if(taxaDespesa==='')
    {
        $("input:text[name='acidenteF:AcidenteTaxaDespesa']").css("border","1px solid red");
    }
    else
    {
        valido = 1;
        $("input:text[name='acidenteF:AcidenteTaxaDespesa']").css("border","");
    }
    if(imcapPermanente==='')
    {
        $("input:text[name='acidenteF:AcidenteIncapPermanente']").css("border","1px solid red");
    }
    else
    {
        valido = 1;
        $("input:text[name='acidenteF:AcidenteIncapPermanente']").css("border","");
    }
    if(taxaIncapPermanente==='')
    {
        $("input:text[name='acidenteF:AcidenteTaxaIncapPermanente']").css("border","1px solid red");
    }
    else
    {
        valido = 1;
        $("input:text[name='acidenteF:AcidenteTaxaIncapPermanente']").css("border","");
    }
    if(custoRep==='')
    {
        $("input:text[name='acidenteF:AcidenteCustoRep']").css("border","1px solid red");
    }
    else
    {
        valido =1;
        $("input:text[name='acidenteF:AcidenteCustoRep']").css("border","");
    }
    if(taxaCustoRep==='')
    {
        $("input:text[name='acidenteF:AcidenteTaxaCustoRep']").css("border","1px solid red");
    }
    else
    {
        valido =1;
        $("input:text[name='acidenteF:AcidenteTaxaCustoRep']").css("border","");
    }
    
    if(campoAcidenteAtivo === false)
    {
    
        if(campoAci==="")
        {
            valido=0;
             $(".acidenteOU3AV").css("border","1px solid red");
        }
           
        else
        {
            valido = 1;
             $(".acidenteOU3AV").css("border","");
        }
    }
    if(campoDefeitoAtivo === false)
    {
        if(campoDefeito ==="")
        {
             valido=0;
              $(".acidenteDFMV").css("border","1px solid red");
        }
        else
        {
              $(".acidenteDFMV").css("border","");
              valido = 1;
        }
    }
    

 }
         
   

function numeroR(borda)
{
    if(borda==='red')
    {
        $('html, body').animate({ scrollTop: 0 }, 'slow');
        $(".acidenteNumeroRegistro").css("border","1px solid red");
    }
    else
         $(".acidenteNumeroRegistro").css("border",""); 
}

// function that sends the fields value with jquery to managedbean
function enviarDadosAcidente(campos)
{

      dadosAcidente([{name:'campos',value:campos},
                    {name:'apolice',value:$(".acidenteNumeroApolice").val()},
                   {name:'numeroRegistro',value:$(".acidenteNumeroRegistro").val()},
                   {name:'moeda',value:$(".acidenteMoeda").val()},
                    {name:'acidente3anos',value:$(".acidenteU3anos").val()},
                    {name:'defeitosFisicos', value:$(".acidenteDefeitosF").val()},
                    {name:'maquinasUtilizadas', value:$(".maquinasUtilizadas").val()},
                    {name:'defeitosCampo', value:$(".acidenteDefeitosFCampo").val()},
                    {name:'acidente3AnosCampo',value:$(".acidenteUltimosAnos").val()},
                    {name:'nome', value:$(".acidenteNome").val()},
                    {name:'profissão', value:$(".acidenteProfissao").val()},
                    {name:'categoria', value:$(".acidenteCategoria").val()},
                    {name:'dataNasc', value:$(dataNasc).val()},
                    {name:'valorMorte', value:$(".valorMorte").val()},
                    {name:'taxaMorte', value:$(".taxaMorte").val()},
                    {name:'incapacidadeTemp', value:$(".acidenteIncapTemp").val()},
                    {name:'taxaIncapacidadeTemp', value:$(".acidenteTaxaIncapTemp").val()},
                    {name:'incapacidadePerm', value:$(".incapacidadePermanente").val()},
                    {name:'taxaIncapPermanente', value:$(".taxaIncapPermanente").val()},
                    {name:'despesaMedica', value:$(".acidenteDespesaM").val()},
                    {name:'taxaDespesaMedica', value:$(".acidenteTaxaDespesaM").val()},
                    {name:'custoRep', value:$(".acidenteCustoRep").val()},
                    {name:'taxaCustoRep', value:$(".acidenteTaxaCustoRep").val()}]);
}

function acidenteLimparCampos()
{
    $(".numeroAC").val("");
    $(".limparCampo").val("");
    $(dataNasc).val("");
}

function limparValoresPremio()
{
    $(".limparValores").html("");
}

function mostrarPorocessamentoA()
{
    $(".modalProcess").show();
}

function fecharPorocessamentoA()
{
     $(".modalProcess").hide();
}

function moreInfoAPG()
{
    $(".mp-infoTable").fadeIn();
}
function focoNumeroApoliceAcidente()
{
     $('html, body').animate({ scrollTop: 0 }, 'slow');
    $(".acidenteNumeroApolice").css("border","1px solid red");
    
}