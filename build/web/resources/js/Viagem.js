  var adress = "input:text[name='ViagemformularioTable:Vadress_input']";
  var numDoc = "input:text[name='ViagemformularioTable:numeroDoc_input']";
  var dataFim = "input:text[name='Viagemformulario:VdataF_input']";
  var dataInicio = "input:text[name='Viagemformulario:VdataI_input']";
  var cidadeDestino = "input:text[name='Viagemformulario:viagemCidadeDestino_input']";
  var zonaDestino = "input:text[name='Viagemformulario:viagemZonaDestino_input']";
  var dataNasc = "input:text[name='ViagemformularioTable:VdataNasc_input']";
  var dataEmissao ="input:text[name='ViagemformularioTable:dataEmissaoViagem_input']";
  var objetivoViagem ="input:text[name='Viagemformulario:Vobj_input']";
  
$(document).ready(function (e)
{
    var limite = 15;
    var tamanho = $(numDoc).val().length;
    $(".moreData").click(function(e)
    {
        e.preventDefault();
          $(".mp-infoTable").fadeOut();
    });
    $(".viagemAdicionar").click(function (e)
    {
        e.preventDefault();
        validarCamposAdicionar();
    });
    $("input:text").click(function(e)
    {
       $(this).css("border",""); 
    });
   $("select").focus(function(e)
   {
        $(this).css("border",""); 
   });
   

    $(".viagemConcluir").click(function (e)
    {
        e.preventDefault();
        viagemAvancar();
    });

    // acção do botão cancelar

});

function validarCamposAdicionar()
{
    var limite = 15;
    var valido = true;
    var tamanho = $(numDoc).val().length;
      
    if($(".viagemApolice").val() === "")
    {
        $(".viagemApolice").css("border","1px solid red");
        $(".viagemApolice").focus();
        moveTop();
        valido = false;
    }
    else  
        $(".viagemApolice").css("border","");
    
    if($(objetivoViagem).val() ==="")
    {
        valido = false;
        $(objetivoViagem).css("border", "1px solid red");
    }
    else
        $(objetivoViagem).css("border", "");
      
    if($(cidadeDestino).val() === "")
    {
        $(cidadeDestino).css("border", "1px solid red");
        valido = false;
    }
    else
         $(cidadeDestino).css("border", "");
     
    if($(zonaDestino).val() === "")
    {
        $(zonaDestino).css("border", "1px solid red");
        valido = false;
    }
    else
         $(zonaDestino).css("border", "");
     
    if($(".paisDestinoViagem").val() ==="")
    {
        $(".paisDestinoViagem").css("border", "1px solid red");
        valido = false;
    }
    else
        $(".paisDestinoViagem").css("border", "");
    
    if($(".viagemNacionalidade").val() === "")
    {
        $(".viagemNacionalidade").css("border", "1px solid red");
        valido = false;
    }
    else
         $(".viagemNacionalidade").css("border", "");
     
    if($(dataInicio).val() === "")
    {
        $(dataInicio).css("border", "1px solid red");
        valido = false;
    }
    else
        $(dataInicio).css("border", ""); 
    
    if($(dataFim).val() === "")
    {
        $(dataFim).css("border", "1px solid red");
        valido = false;
    }
    else
        $(dataFim).css("border", ""); 
    
    if($(".viagemNome").val() === "")
    {
        $(".viagemNome").css("border", "1px solid red");
        valido = false;
    }
    else
        $(".viagemNome").css("border", "");
    
    if($(adress).val() === "")
    {
        $(adress).css("border", "1px solid red");
        valido = false;
    }
    else
        $(adress).css("border", ""); 
    
    if($(".viagemTipoDoc").val() === "")
    {
        $(".viagemTipoDoc").css("border", "1px solid red");
           valido = false;
    }
    else
        $(".viagemTipoDoc").css("border", "");
      
    if ($(numDoc).val() === "" || limite < tamanho)
    {
        $(numDoc).css("border", "1px solid red");
         valido = false;
    }
    else
        $(numDoc).css("border", "");
    
    if ($(dataEmissao).val() === "")
    {
        $(dataEmissao).css("border", "1px solid red");
          valido = false;
    }
    else
        $(dataEmissao).css("border", "");
    
    if($(".viagemLocalEmissao").val() === "")
    {
        $(".viagemLocalEmissao").css("border", "1px solid red");
           valido = false;
    }
    else
        $(".viagemLocalEmissao").css("border", "");
  
    if($(dataNasc).val() === "")
    {
        $(dataNasc).css("border", "1px solid red");
        valido = false;
    }
    else
        $(dataNasc).css("border", "");
    
    if($(".viagemLocalNasc").val() === "")
    {
        $(".viagemLocalNasc").css("border", "1px solid red");
         valido = false;
    }
    else
        $(".viagemLocalNasc").css("border", "");
    
    if($(".viagemSexo").val() === "")
    {
        $(".viagemSexo").css("border", "1px solid red");
        valido = false;
    }
    else
        $(".viagemSexo").css("border", "");
    if ( $(".viagemTelefone").val() === "")
    {
        $(".viagemTelefone").css("border", "1px solid red");
         valido = false;
    }
    else
        $(".viagemTelefone").css("border", "");
      
    if(valido === true)
    {
        $(".modalProcess").show();
        viagemDadosAdicionar();
    }
}


function validarDataFim(xhr, status, args)
{
    var dataF = "input:text[name='Viagemformulario:VdataF_input']";
    var r = args.info;
    if (r === "Data final inválida")
    {
        $(dataF).css("border", "1px solid red");
        $(dataF).focus();
    }
    else
    {
        $(dataF).css("border", "");
    }
}

function viagemDadosAdicionar()
{
    var multi =  $(".viagemMulti").prop('checked') === true ? "multi" : "normal";
    viagemDados([{name:'nomePessoa',value:$(".viagemNome").val()},
                {name:'address', value:$(adress).val()},
                {name:'documento',value:$(".viagemTipoDoc").val()},
                {name:'numDoc', value:$(numDoc).val()},
                {name:'dataEmissao', value:$(dataEmissao).val()},
                {name:'localEmissao', value:$(".viagemLocalEmissao").val()},
                {name:'dataNasc', value:$(dataNasc).val()},
                {name:'localNasc', value:$(".viagemLocalNasc").val()},
                {name:'sexo', value:$(".viagemSexo").val()},
                {name:'telefone', value:$(".viagemTelefone").val()},
                {name:'data de inicio', value:$(dataInicio).val()},
                {name:'data de fim', value:$(dataFim).val()},
                {name:'objetivo da viagem', value:$(objetivoViagem).val()},
                {name:'pais de destino', value:$(".paisDestinoViagem").val()},
                {name:'apolice segurado', value:$(".viagemApolice").val()},
                {name:'cidade de destino', value:$(cidadeDestino).val()},
                {name:'zona de destino', value:$(zonaDestino).val()},
                {name:'nacionalidade', value:$(".viagemNacionalidade").val()},
                {name:'multiViagem', value:multi},
                {name:'outrasInf', value:$(".viagemOutrasInf").val()}]);
            
}

function viagemAvancar()
{
    if($(".viagemApolice").val() ==="")
    {
        $(".viagemApolice").css("border", "1px solid red");
        $(".viagemApolice").focus();
    }
    if($(".viagemNumeroRegistro").val() ==="")
    {
        $(".viagemNumeroRegistro").css("border", "1px solid red");
        moveTop();
    }
    if($(".viagemNumeroRegistro").val() !== "" &&  $(".viagemApolice").val() !=="")
    {
        viagemProximo([{name:'apolice',value:$(".viagemApolice").val()},
            {name:'numeroR', value:$(".viagemNumeroRegistro").val()}]);
    }
   
}  

function editar()
{
     $(".viagemTelefone").val($(".contactoPessoaSegurada").html());
}

function mostrarModalInfo()
{
    $(".mp-infoTable").fadeIn();
}

function modalProcessing()
{
    $(".modalProcess").show();
}
function modalProcessingHide()
{
    $(".modalProcess").hide();
}

function carregarViagem(telefone, sexo, endereco, localEmissao, localNascimento, dataEmissaoSegurado, dataNascSegurado, tipoDoc, nomePessoaSegurada, nacionalidade)
{
    var dataEmissao = "input:text[name='ViagemformularioTable:dataEmissaoViagem_input']";
    var dataNasc = "input:text[name='ViagemformularioTable:VdataNasc_input']";
    
    if(telefone !== 'null' && telefone !=='')
        $(".viagemTelefone").val(telefone);
    if(nacionalidade !=='null' && nacionalidade !=='')
        $(".viagemNacionalidade").val(nacionalidade);
    if(sexo !=='null' && sexo !=='')
        $(".viagemSexo").val(sexo);
    if(endereco !=='null' && endereco!=='')
        $(adress).val(endereco);
    if(localEmissao !=='null' && localEmissao !=='')
         $(".viagemLocalEmissao").val(localEmissao);
     if(localNascimento !== 'null' && localNascimento !=='')
        $(".viagemLocalNasc").val(localNascimento);
    if(dataEmissaoSegurado !=='null' && dataEmissaoSegurado !=='')
         $(dataEmissao).val(dataEmissaoSegurado);
     if(dataNascSegurado !=='null' && dataNascSegurado !=='')
         $(dataNasc).val(dataNascSegurado);
     if(tipoDoc !=='null' && tipoDoc!=='')
        $(".viagemTipoDoc").val(tipoDoc);
    if(nomePessoaSegurada !=='null' && nomePessoaSegurada !=='')
      $(".viagemNome").val(nomePessoaSegurada);
}

function viagemDadosAdicionados()
{
    $(objetivoViagem).val("");
    $(dataInicio).val("");
    $(dataFim).val("");
    $(".viagemCampo").val("");
    $(".viagemNumDias").html("");
    $(cidadeDestino).val("");
    $(zonaDestino).val("");
    $(adress).val("");
    $(numDoc).val("");
    $(dataEmissao).val("");
    $(dataNasc).val("");
}

function multiViagem(isMultiViagem)
{
    if(isMultiViagem ==='multi')
    {
         $(dataFim).val(null);
        $(dataFim).attr('disabled', true);
    }
    else
         $(dataFim).attr('disabled', false);
}

function desativarDataFIm()
{
     $(dataFim).attr('disabled', true);
}

function apoliceSeguradoVerificar()
{
    moveTop();
    $(".viagemApolice").focus();
}