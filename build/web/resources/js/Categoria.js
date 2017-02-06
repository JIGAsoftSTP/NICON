$(document).ready(function (e)
{
    $(".categoriaGuardar").click(function (e)
    {
       e.preventDefault();
           
       var valido = true;
 
       $('.estruturaSalarioCampo').each(function ()
       {
          if($(this).val()==='') 
          {
              $(this).css("border","1px solid red");
              valido = false;
          }
       });
       if(valido === true)
       {
            if($(".categoriaAlmocoLivre").val() ==="")
            {
                $(".categoriaAlmocoLivre").focus();
                valido = false;
            }
       }
       if(valido === true)
           $('.modalProcess').show();
    });
       
    $('.categoriaSalario').keyup(function (e)
    {
        calculoValorTotal(); 
    });

    $("input").focus(function(e){
        $(this).css("border","");
    });
});

function clearFields()
{
    $('.modalProcess').hide();
    $('.mp-estrutura').fadeOut(400);
    $('.estruturaSalarioCampo').val("");
    $(".categoriaAlmocoLivre").val("");
    $('.categoriaValorTotal').html("");
}

function calculoValorTotal()
{
     var valorTotal =0;
    $('.categoriaSalario').each(function ()
    {
        valorTotal = valorTotal + Number($(this).val());
    });
    $('.categoriaValorTotal').html(valorTotal);
    $('.categoriaValorTotal').number(true,2);
}

function editarCategoria(nomeCategoria, numDias,nivel, salarioBase, subsidioAlojamento, subsidioTransporte, subsidioAlmoco, total, almocoLivreImposto )
{ 
    $(".default-JIGA h3").html("Editar Estrutura de Salário");
     $(".nomeCategoriaSalario").attr('disabled', false);
    $(".nivel").attr('disabled', false);
    $(".nomeCategoriaSalario").val(nomeCategoria);
    $(".nivel").val(nivel);
//    $(".nomeCategoriaSalario option").each(function (){if($(this).text() === nomeCategoria)$(this).attr('selected','selected');});
//    $(".nivel option").each(function (){if($(this).text() === nivel)$(this).attr('selected','selected');});
    $(".nomeCategoriaSalario").attr('disabled', true);
    $(".nivel").attr('disabled', true);
    $("input:text[name='formContabilidadeSalario:formCategoria:categoriaNumDia']").val(numDias);
    $("input:text[name='formContabilidadeSalario:formCategoria:categoriaSalarioBase']").val(salarioBase);
    $("input:text[name='formContabilidadeSalario:formCategoria:categSubAloj']").val(subsidioAlojamento);
    $("input:text[name='formContabilidadeSalario:formCategoria:categSubTransporte']").val(subsidioTransporte);
    $("input:text[name='formContabilidadeSalario:formCategoria:categSubAlmoco']").val(subsidioAlmoco);
    $(".categoriaAlmocoLivre").val(almocoLivreImposto);
    calculoValorTotal();
    $('.mp-estrutura').fadeIn(400);
}



