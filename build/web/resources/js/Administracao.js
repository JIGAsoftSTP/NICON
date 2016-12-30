var quantidade =0;
var nomeArtigo;
var artigoCategoria = "input:text[name='articleForm:articleCategory_input']";
$(document).ready(function (e)
{
    $('.addArtigo').click(function (e)
    {
       e.preventDefault();
        $(".modalFrame12 h3").html("Adicionar Novo Artigo");
       $(".articleNA").val("");
       $(artigoCategoria).val("");
       $(".articleNA").attr("disabled", false);
       $(".articleChangeNameDisabled").val("");
       $(".articleChangeNameDisabled").attr("disabled", false);
       $('.pageModal').fadeIn();
    });
    $('.addArticle').click(function (e)
    {
        e.preventDefault();
        
        if($(".artigoQuantidade").is(':disabled'))
        {
             if($(".artigoNome").val() === '')
               $(".artigoNome").css('border','1px solid red');
            if($(artigoCategoria).val() === '')
                  $(artigoCategoria).css('border','1px solid red');
        }
        else
        {
             $('.formArtigo [type=text], select, [type=textarea]').each(function ()
            {
               if($(this).val()==='')
                   $(this).css('border','1px solid red');
            });
            if($(".artigoObs").val()==='')
                $(".artigoObs").css("border","1px solid red");
        }
        sendDataArticle();
    });
    $(".artigoObs").focus(function(e)
    {
       $(this).css("border",""); 
    });
    $("input, select").focus(function (e)
    {
        $(this).css("border", "");
    });
  
});

function articleChangeQuantity(parametro,quantidadeStock)
{
    if(quantidadeStock !=='null')
    {
        $(".articleChangeNameDisabled").attr("disabled", false);
        $(".modalFrame12 h3").html(parametro);
        $(".articleNA").attr("disabled", true);
        $('.pageModal').fadeIn();  
        $('.artigoQuantidade').val(quantidadeStock);
        quantidade = quantidadeStock;
    }
    else
    {
        nomeArtigo = $(".artigoNome").val();
        $(".modalFrame12 h3").html(parametro);
        $(".articleChangeNameDisabled").attr("disabled", true);
        $('.pageModal').fadeIn();  
    }
}
function artigoRegistrado()
{
    $('.modalProcess').hide();
    $(".articleNA").val("");
    $(".articleChangeNameDisabled").val("");
     $('.pageModal').fadeOut("slow");
}

function nomeArtigoExiste()
{
    $(".artigoNome").focus();
}

function changeArticleNameCategory()
{
    var valido = true;
    if($(".artigoNome").val() === '')
        valido = false;
    if($(artigoCategoria).val() === '')
        valido = false;
    return valido;
}
function sendDataArticle()
{
    var valido = true;
      if($(".artigoQuantidade").is(':disabled'))
      {
          if(changeArticleNameCategory() === true)
          {
              $('.modalProcess').show();
              article([{name:'nome',value:$(".artigoNome").val()},  
             {name:'categoria',value:$(artigoCategoria).val()},
             {name:'quantidade', value:$(".artigoQuantidade").val()},
             {name:'funcionario', value:$(".artigoFuncionario").val()},
             {name:'fornecedor', value:$(".artigoFornecedor").val()},
             {name:'typeOperation', value:"alterar nome do artigo"},
             {name:'stockQuantidade', value:quantidade},
             {name:'adicionarRemover', value:$(".modalFrame12 h3").html()},          
             {name:'nomeArtigoAtualizar', value:nomeArtigo},          
             {name:'obs', value:$(".artigoObs").val()}]);
          }
      }
      else
      {
            if($(artigoCategoria).val() === '')
                valido = false;
           if($(".artigoObs").val()==='')
               valido = false;
           $('.formArtigo [type=text], select').each(function ()
           {
              if($(this).val()==='')
                  valido = false;
           });
            var nomeD = $(".artigoNome").is(":disabled");
           if(valido === true)
           {    
                $('.modalProcess').show();
                article([{name:'nome',value:$(".artigoNome").val()},  
                {name:'categoria',value:$(artigoCategoria).val()},
                {name:'quantidade', value:$(".artigoQuantidade").val()},
                {name:'funcionario', value:$(".artigoFuncionario").val()},
                {name:'fornecedor', value:$(".artigoFornecedor").val()},
                {name:'typeOperation', value:(nomeD===true)? "alterar quantidade" : "novo"},
                {name:'stockQuantidade', value:quantidade},
                {name:'adicionarRemover', value:$(".modalFrame12 h3").html()},          
                {name:'obs', value:$(".artigoObs").val()}]);
           }   
      }
}

function artigoQuantidadeBorda()
{
    $(".artigoQuantidade").focus();
}

function quantidadeArtigoAtual(quantidade)
{
       $(".artigoQuantidade").val(quantidade);
}