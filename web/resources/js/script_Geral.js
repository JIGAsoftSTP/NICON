
$(document).ready(function() {
      
      $(document).keypress(function (e)
      {
         if(e.keyCode === 27)
             $(".modalPage").fadeOut();     
      });
      
      $('.formatValue').keyup(function ()
      {
          formatted($(this));
      });
      
       $('.double').keypress(function (e) {

        //$( ".integer" ).trigger("keypress ");
        if ((e.which !== 44 || $(this).val().indexOf(',') !== -1) &&
                ((e.which < 48 || e.which > 57) &&
                        (e.which !== 0 && e.which !== 8))) {
            e.preventDefault();
        }

        var text = $(this).val();

        if
                ((text.indexOf(',') !== -1) &&
                        (text.substring(text.indexOf(',')).length > 2) &&
                        (e.which !== 0 && e.which !== 8) &&
                        ($(this)[0].selectionStart >= text.length - 2)) {
            e.preventDefault();
        }
    });
    $(".percentagem").keyup(function ()
      {
          if($(this).val() !=="")
          {
              if(Number($(this).val())>100)
                  $(this).val("");
          }
      });
      $(".parceV").keyup(function (e)
        {
            if( Number($(this).val()) < 0 || Number($(this).val())>100 )
            { $(this).val(""); }
        });

     $(".mf-bt-ko").click(function ()
     {
         $(".editActual").hide('fade');
         $(".senhaAtualFuncionario").val("");
         $(".senhaAtualFuncionario").css("border","");
        $(".novaSenhaFuncionario").val("");
        $(".novaSenhaFuncionario").css("border","");
         $(".confirmarSenhaFuncionario").val("");
         $(".confirmarSenhaFuncionario").css("border","");
     });
    $( '.closeModalFrame' ).click(function (){  
          if($(this).hasClass('processamentoSalarioCloseFrame')=== false)
             $(".modalPage").hide('fade');
         
        return false;            
    });
   $(".cancelarTerminarSessao").click(function ()
   {
       $(".terminarSessao").hide('fade');
       return false;
   });

    $(".alterarSenhaOK").click(function ()
    {
         $(".changePass").fadeOut();
    });
    
   $('body').on('click','.xpert-alert .close',  function(event) {
    $('.xpert-alert').removeClass('show');
    });


    $(".editarSenha").click(function ()
    {
        var senhaAtual =  $(".senhaAtualFuncionario").val();
        var novaSenha = $(".novaSenhaFuncionario").val();
        var confirmar = $(".confirmarSenhaFuncionario").val();
        if(senhaAtual ==="")
            $(".senhaAtualFuncionario").css("border","1px solid red");
        else
             $(".senhaAtualFuncionario").css("border","");
        if(novaSenha ==="")
            $(".novaSenhaFuncionario").css("border","1px solid red");
        else
            $(".novaSenhaFuncionario").css("border","");
        if(confirmar ==="")
            $(".confirmarSenhaFuncionario").css("border","1px solid red");
        else
            $(".confirmarSenhaFuncionario").css("border","");
            
    });
    $(".cancelar").click(function ()
    {
         $(".modalPage").hide();
    });
    $(".alterarSenha").click(function ()
    {
          $(".senhaAtualFuncionario").focus();
         $(".editActual").fadeIn();
         senhaAtualUtilizadorValida();
            $(".senhasDiferentes").css("display","none");
    });  
    
    $(".justNumeric").keyup(function(e)
    {
        e.preventDefault();
        if (!$.isNumeric($(this).val()))
            $(this).val("");
   });
 

   $(".justIntiger").keyup(function (e)
    {
        e.preventDefault();
        var expre = /[^0-9]/g;
        if ($(this).val().match(expre))
        {$(this).val($(this).val().replace(expre, ''));}
    });
    
    $('.mf-bt-ko').click(function () {
        $(this).closest('.modalPage').fadeOut(300);
        return false;
    });
    $('.ui-datatable label').click(function () {
        //$(this).closest('tr').trigger('click');
    });
    
    $('.removeRow').click(function (){
        var row = $(this).closest('tr');
        row.remove();
    });
    $('.cleanTable').click(function (){
        var rows = $(this).closest('tbody').find('tr');
        rows.remove();
        return false;
    });
    $('.mi-values').width( $('.mi-values').parent().width());
    /**
     * estava em commentario por pq tinha error
     */
    var divPos = {};
    var offset = $('.mi-values').parent().offset();
    $(document).mousemove(function(e){
        if (offset !== undefined) {
            divPos = {
                left: e.pageX - offset.left,
                top: e.pageY - offset.top
            };
            var position = $('.mi-values').parent().height() - divPos.top;
            if (position < 0) $('.mi-values').addClass('show');
        }
    });
    $('.close-mi-values').click(function (){
        $('.mi-values').removeClass('show');
    });
    
    $(".filter-table label").click(function ()
    {
         $(this).addClass('active').siblings().removeClass('active');
    });
});

//#################### REMOVE LINE AND/OR CLEAN TABLE ####################
    
//######################### END ########################

function modalAtivarUtilizador()
{
    $(".mp-editPassword").show($(".mp-editPassword").slideDown(400));
}

function eliminarCampo()
{
    $(".infoError").html("Palavras-passe não se correspondem!");
    $(".ativarUsenha").val("");
    $(".ativarConf").val("");
    $(".ativarUsenha").focus();     
}

function ocultarMensagem()
{
    $(".infoError").css("display","none");
}

function senhaAtualUtilizadorInvalida()
{
    $(".senhaAtual").html("Senha atual inválida");
     $(".senhaAtual").css("display","");
    $(".senhaAtualFuncionario").css("border","1px solid red");
}
function senhaAtualUtilizadorValida()
{
    $(".senhaAtualFuncionario").css("border","");
    $(".senhaAtual").html("");
    $(".senhaAtual").css("display","none");
}
function senhasErradas()
{
    $(".senhasDiferentes").html("Palavras-passe não se correspondem!");
    $(".senhasDiferentes").css("display","");
    $(".novaSenhaFuncionario").val("");
    $(".novaSenhaFuncionario").focus();
    $(".confirmarSenhaFuncionario").val("");   
}

function bordaVermelha()
{
     $(".senhaAtualFuncionario").css("border","1px solid red");
}
function senhasCorretas()
{
    $(".editActual").fadeOut();
    $(".senhaAtualFuncionario").val("");
    $(".novaSenhaFuncionario").css("border","");
    $(".novaSenhaFuncionario").val("");
    $(".confirmarSenhaFuncionario").css("border","");
    $(".confirmarSenhaFuncionario").val("");
    $(".senhasDiferentes").css("display","none");    
}

function menuAnalista(idDepartamento)
{
    $(".editarCliente").css("display", "none");
    $(".botaoContrato").css("display", "none");
    $(".imgbord").attr('disabled', true);
    var url = window.location.href;
    

   
}

function passado()
{
    $(".campoApolice").attr('disabled', false);
}
function admOperario()
{
    $(".campoApolice").attr('disabled', true);
}

function openAllDocument(camino)
{
    window.open(camino);
}

function moveTop()
{
    $('html, body').animate({ scrollTop: 0 }, 'slow');
}
function getRowCount( table ){
    $('.total-found b').html( $(table +" td").closest("tr").length);
}

function formatarValor(valor) 
{
  
    valor = valor.toString().replace(/\D/g,"");
    valor = valor.toString().replace(/(\d)(\d{8})$/,"$1.$2");
    valor = valor.toString().replace(/(\d)(\d{5})$/,"$1.$2");
    valor = valor.toString().replace(/(\d)(\d{2})$/,"$1,$2");
    return valor ;                   
}

function formatted(nStr)
{
    var num = nStr.val().replace(/(\s)/g, '');
    nStr.val(num.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1 "));
}

function unformatted(nStr) 
{
    var num = nStr.toString();
    if(num !== '')
        return parseFloat(num.replace(/\s/g , '').replace(/,/g, '.'));
    else
        return 0;
}

 function callXpertAlert(txt, type, time) {
    $('.xpert-alert').remove();
    $('body').append('<div class="xpert-alert ' + type + '"><i class="icon-' + type + '"></i><span class="txt">' + txt + '</span> <span class="close">X</span></div>')

    setTimeout(function () {
        $('.xpert-alert').addClass('show');
    }, 200);
    setTimeout(function () {
        $('.xpert-alert').removeClass('show');
    }, time);
}