/*
 * Created by Hélcio Guadalupe
 * 
 */

$(document).ready(function (e)
{
    EstadoInicialFormulario();
    
       $(".numeroAC").keyup(function(e){
        e.preventDefault();
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if(!$.isNumeric($(this).val()))
            $(this).val("");
   });
       $(".din").keyup(function(e){
        e.preventDefault();
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if(!$.isNumeric($(this).val()))
            $(this).val("");
   });
});

function addTableDeCofre(xhr, status, args)
{
    var limpar= args.limparDetalheC;
    if ($('.dinheiroFabricante').val() === "")
    {
        $('.dinheiroFabricante').css("border", "1px solid red");
    }
    else
        $('.dinheiroFabricante').css("border", "");
    
    if ($('.dinheiroTamanho').val() === "")
    {
        $('.dinheiroTamanho').css("border", "1px solid red");
    }
    else
        $('.dinheiroTamanho').css("border", "");
    
    if ($('.pesoDinheiro').val() === "")
    {
        $('.pesoDinheiro').css("border", "1px solid red");
    }
    else
        $('.pesoDinheiro').css("border", "");
    
    if ($('.dinheiroChave').val() === "")
    {
        $('.dinheiroChave').css("border", "1px solid red");
    }
    else
        $('.dinheiroChave').css("border", "");
    
    if ($('.estrutura').val() === "")
    {
        $('.estrutura').css("border", "1px solid red");
    }
    else
        $('.estrutura').css("border", "");
    
    if ($('.dinheiroNome').val() === "")
    {
        $('.dinheiroNome').css("border", "1px solid red");
    }
    else
        $('.dinheiroNome').css("border", "");
    
    if(limpar)
    {
        $('.dinheiroNome').val("");
        $('.dinheiroFabricante').val("");
        $('.dinheiroTamanho').val("");
        $('.pesoDinheiro').val("");
        $('.dinheiroChave').val("");
         $("input:text[name='dinhFormID:Destrutura']").val("");
        $(".pesoDinheiro").val("");
        
    }
}
function checkedIntem1()
{
    var denhcheck1 = $('.denhcheck1').is(":checked");
  
    if(denhcheck1 === true)
    {
        DescricaoCofre('inativo');
        DesativarRisco('desativar 2');
        DesativarRisco('desativar 3');
        DesativarRisco('desativar 4');
        DesativarRisco('desativar 5');
        Limpar();
        $("input:text[name='dinhFormID:dValor1']").attr('disabled',false);
        $("input:text[name='dinhFormID:dLimite1']").attr('disabled',false);
        TempoPermanenciaEstado('ativo');
        
        DistanciaEntreEdificio('ativo','inativo','inativo');
    }
    else
        EstadoInicialFormulario();  
}

function voltar()
{
    window.location.assign("GestSeg_NovoSeguroApolice.xhtml");
}

function checkItem2()
{
    var denhcheck2 = $('.denhcheck2').is(":checked");
    if(denhcheck2 === true)
    {
        DescricaoCofre('inativo');
        DesativarRisco('desativar 1');
        DesativarRisco('desativar 3');
        DesativarRisco('desativar 4');
        DesativarRisco('desativar 5');
        Limpar();
        $("input:text[name='dinhFormID:dValor2']").attr('disabled',false);
        $("input:text[name='dinhFormID:dLimite2']").attr('disabled',false);
        DistanciaEntreEdificio('inativo','inativo','ativo');
        TempoPermanenciaEstado('ativo');
    }
    else
        EstadoInicialFormulario();
}

function checkItem3()
{
    var denhcheck3 = $('.denhcheck3').is(":checked");
     if(denhcheck3 === true)
    {
        DescricaoCofre('inativo');
        DesativarRisco('desativar 1');
        DesativarRisco('desativar 2');
        DesativarRisco('desativar 4');
        DesativarRisco('desativar 5');
        $("input:text[name='dinhFormID:dValor3']").attr('disabled',false);
        $("input:text[name='dinhFormID:dLimite3']").attr('disabled',false);
        DistanciaEntreEdificio('inativo','ativo','inativo');
        TempoPermanenciaEstado('inativo');
        Limpar();
    }
    else
        EstadoInicialFormulario();
    
}
function checkItem4()
{
    var denhcheck4 = $('.denhcheck4').is(":checked");
     if(denhcheck4 === true)
    {
        DescricaoCofre('ativo');
        DesativarRisco('desativar 1');
        DesativarRisco('desativar 2');
        DesativarRisco('desativar 3');
        DesativarRisco('desativar 5');
        Limpar();
        $("input:text[name='dinhFormID:dValor4']").attr('disabled',false);
        $("input:text[name='dinhFormID:dLimite4']").attr('disabled',false);
        DistanciaEntreEdificio('inativo','inativo','inativo');
        TempoPermanenciaEstado('inativo');
    }
    else
        EstadoInicialFormulario();
}

function checkItem5()
{
    var denhcheck5 = $('.denhcheck5').is(":checked");
    if(denhcheck5 === true)
    {
        DescricaoCofre('inativo');
        DesativarRisco('desativar 1');
        DesativarRisco('desativar 2');
        DesativarRisco('desativar 3');
        DesativarRisco('desativar 4');
        Limpar();
        $("input:text[name='dinhFormID:dValor5']").attr('disabled',false);
        $("input:text[name='dinhFormID:dLimite5']").attr('disabled',false);
        DistanciaEntreEdificio('inativo','inativo','ativo');
        TempoPermanenciaEstado('inativo');
    }
    else
        EstadoInicialFormulario();
}

function DescricaoCofre(estado)
{
    if(estado ==="ativo")
    {
        $("input:text[name='dinhFormID:Dnomefabricante']").val("");
        $("input:text[name='dinhFormID:Dnumerofabricante']").val("");
        $("input:text[name='dinhFormID:Dtamanho']").val("");
        $("input:text[name='dinhFormID:DpesoDinheiro']").val("");
        $("input:text[name='dinhFormID:DdetenterChave']").val("");
        $("input:text[name='dinhFormID:Destrutura']").val("");
        $("input:radio[name='dinhFormID:Dcontr']").attr('disabled',false);
        $("input:text[name='dinhFormID:Dnomefabricante']").attr('disabled',false);
        $("input:text[name='dinhFormID:Dnumerofabricante']").attr('disabled',false);
        $("input:text[name='dinhFormID:Dtamanho']").attr('disabled',false);
        $("input:text[name='dinhFormID:Dpeso']").attr('disabled',false);
        $("input:text[name='dinhFormID:DdetenterChave']").attr('disabled',false);
        $("input:text[name='dinhFormID:Destrutura']").attr('disabled',false);
        $(".dinheiroAdd").attr('disabled',false);
        $('.aboutTill').slideDown();

    }
    else
    {
        $(".dinheiroAdd").attr('disabled',true);
        $("input:radio[name='dinhFormID:Dcontr']").attr('disabled',true);
        $("input:text[name='dinhFormID:Dnomefabricante']").attr('disabled',true);
        $("input:text[name='dinhFormID:Dnumerofabricante']").attr('disabled', true);
        $("input:text[name='dinhFormID:Dtamanho']").attr('disabled', true);
        $("input:text[name='dinhFormID:Dpeso']").attr('disabled', true);
        $("input:text[name='dinhFormID:DdetenterChave']").attr('disabled', true);
        $("input:text[name='dinhFormID:Destrutura']").attr('disabled', true);
        
        $("input:text[name='dinhFormID:Dnomefabricante']").val("");
        $("input:text[name='dinhFormID:Dnumerofabricante']").val("");
        $("input:text[name='dinhFormID:Dtamanho']").val("");
        $("input:text[name='dinhFormID:Dpeso']").val("");
        $("input:text[name='dinhFormID:DdetenterChave']").val("");
        $("input:text[name='dinhFormID:Destrutura']").val("");
        
        $("input:text[name='dinhFormID:Dnomefabricante']").css('border','');
        $("input:text[name='dinhFormID:Dnumerofabricante']").css('border','');
        $("input:text[name='dinhFormID:Dtamanho']").css('border','');
        $("input:text[name='dinhFormID:DpesoDinheiro']").css('border','');
          $("input:text[name='dinhFormID:Dpeso']").css('border','');
        $("input:text[name='dinhFormID:DdetenterChave']").css('border','');
        $("input:text[name='dinhFormID:Destrutura']").css('border','');
        $('.aboutTill').hide('fade');
    }
}

function DistanciaEntreEdificio(a, b, c)
{
    if(a==='ativo')
    {
        $("input:text[name='dinhFormID:DinheiroBanco']").css('border','');
        $("input:text[name='dinhFormID:DinheiroBanco']").val("");
        $("input:text[name='dinhFormID:DinheiroBanco']").attr('disabled',false);
    }
    else
    {
        $("input:text[name='dinhFormID:DinheiroBanco']").val("");
        $("input:text[name='dinhFormID:DinheiroBanco']").css('border','');
        $("input:text[name='dinhFormID:DinheiroBanco']").attr('disabled', true);
    }
    if(b==='ativo')
    {
        $("input:text[name='dinhFormID:dinheiroCorreio']").val("");
        $("input:text[name='dinhFormID:dinheiroCorreio']").css('border','');
        $("input:text[name='dinhFormID:dinheiroCorreio']").attr('disabled',false);
    }
        
    else
    {
         $("input:text[name='dinhFormID:dinheiroCorreio']").val("");
         $("input:text[name='dinhFormID:dinheiroCorreio']").css('border','');
         $("input:text[name='dinhFormID:dinheiroCorreio']").attr('disabled', true);
    }
        
    if(c==='ativo')
    {
         $("input:text[name='dinhFormID:dinheiroOutro']").val("");
         $("input:text[name='dinhFormID:dinheiroOutro']").css('border','');
         $("input:text[name='dinhFormID:dinheiroOutro']").attr('disabled',false);
    }
       
    else
    {
        $("input:text[name='dinhFormID:dinheiroOutro']").css('border','');
        $("input:text[name='dinhFormID:dinheiroOutro']").val("");
        $("input:text[name='dinhFormID:dinheiroOutro']").attr('disabled', true);
    }
         
}

function TempoPermanenciaEstado( estado)
{
    if(estado ==='ativo')
    {
         $("input:radio[name='dinhFormID:dinheiroPagamento']").attr('disabled',false);
         $("input:text[name='dinhFormID:dinheiroTempoPermanenciaBanco']").val("");
         $("input:text[name='dinhFormID:dinheiroTempoPermanenciaBanco']").attr('disabled', false);
    }
    else
    {
        $("input:radio[name='dinhFormID:dinheiroPagamento']").attr('disabled',true);
        $("input:radio[name='dinhFormID:dinheiroPagamento']").attr('checked', false);
        $("input:text[name='dinhFormID:dinheiroTempoPermanenciaBanco']").val("");
        $("input:text[name='dinhFormID:dinheiroTempoPermanenciaBanco']").attr('disabled', true);
    }
          
        
}

function EstadoInicialFormulario()
{
    TempoPermanenciaEstado('inativo');
    DistanciaEntreEdificio('ativo','ativo','ativo');
    $(".dinheiroLimite").css('border','');
    $(".dinheiroLimite").attr('disabled',true);
    $(".dinheiroLimite").val("");
    $(".dinheiroValorRisco").val("");
    $(".dinheiroValorRisco").css('border','');
    $(".dinheiroValorRisco").attr('disabled',true);
    DescricaoCofre('inativo');
    $("input:radio[name='dinhFormID:dinheiroPagamento']").attr('checked', false); 
    $("input:radio[name='dinhFormID:dinheiroPagamento']").attr('disabled',true);
    $("input:text[name='dinhFormID:dinheiroTempoPermanenciaBanco']").attr('disabled',true);
    $(".dinheiroAdd").attr('disabled',true);
    $("input:text[name='dinhFormID:Dnomefabricante']").val("");
    $("input:text[name='dinhFormID:Dnumerofabricante']").val("");
    $("input:text[name='dinhFormID:DdinheiroTamanho']").val("");
    $("input:text[name='dinhFormID:DpesoDinheiro']").val("");
    $("input:text[name='dinhFormID:DdetenterChave']").val("");
    $("input:text[name='dinhFormID:Destrutura']").val("");
}

function DesativarRisco( estado)
{
    if(estado ==='desativar 1')
    {
        $('.denhcheck1').attr('checked',false);
        $("input:text[name='dinhFormID:dValor1']").val("");
        $("input:text[name='dinhFormID:dLimite1']").val("");
        $("input:text[name='dinhFormID:dValor1']").attr('disabled',true);
        $("input:text[name='dinhFormID:dLimite1']").attr('disabled',true);
    }
    else
        if(estado ==='desativar 2')
        {
            $('.denhcheck2').attr('checked',false);
            $("input:text[name='dinhFormID:dValor2']").val("");
            $("input:text[name='dinhFormID:dLimite2']").val("");
            $("input:text[name='dinhFormID:dValor2']").attr('disabled',true);
            $("input:text[name='dinhFormID:dLimite2']").attr('disabled',true);
        }
        else
            if(estado ==='desativar 3')
            {
                $('.denhcheck3').attr('checked',false);
                $("input:text[name='dinhFormID:dValor3']").val("");
                $("input:text[name='dinhFormID:dLimite3']").val("");
                $("input:text[name='dinhFormID:dValor3']").attr('disabled',true);
                $("input:text[name='dinhFormID:dLimite3']").attr('disabled',true);
            }
            else
                 if(estado ==='desativar 4')
                {
                    $('.denhcheck4').attr('checked',false);
                    $("input:text[name='dinhFormID:dValor4']").val("");
                    $("input:text[name='dinhFormID:dLimite4']").val("");
                    $("input:text[name='dinhFormID:dValor4']").attr('disabled',true);
                    $("input:text[name='dinhFormID:dLimite4']").attr('disabled',true);
                }
                else
                {
                    
                    $('.denhcheck5').attr('checked',false);
                    $("input:text[name='dinhFormID:dValor5']").val("");
                    $("input:text[name='dinhFormID:dLimite5']").val("");
                    $("input:text[name='dinhFormID:dValor5']").attr('disabled',true);
                    $("input:text[name='dinhFormID:dLimite5']").attr('disabled',true);
                }
                    
}

function VerificarCampos()
{
    var estrutura=  $("input:text[name='dinhFormID:Destrutura']").val();
    if ($('.dinheiroFabricante').val() === "")
    {
        $('.dinheiroFabricante').css("border", "1px solid red");
    }
    else
        $('.dinheiroFabricante').css("border", "");
    
    if ($('.dinheiroTamanho').val() === "")
    {
        $('.dinheiroTamanho').css("border", "1px solid red");
    }
    else
        $('.dinheiroTamanho').css("border", "");
    
    if ($('.pesoDinheiro').val() === "")
    {
        $('.pesoDinheiro').css("border", "1px solid red");
    }
    else
        $('.pesoDinheiro').css("border", "");
    
    if ($('.dinheiroChave').val() === "")
    {
        $('.dinheiroChave').css("border", "1px solid red");
    }
    else
        $('.dinheiroChave').css("border", "");
    
    if (estrutura === "")
    {
        $("input:text[name='dinhFormID:Destrutura']").css("border","1px solid red");
    }
    else
        $("input:text[name='dinhFormID:Destrutura']").css("border","");
    
    if ($('.dinheiroNome').val() === "")
    {
        $('.dinheiroNome').css("border", "1px solid red");
    }
    else
        $('.dinheiroNome').css("border", "");
    
}

function Limpar()
{
     $("input:text[name='dinhFormID:dinheiroTransporte']").val("");
     $("input:text[name='dinhFormID:dinheiroPrecaucao']").val("");
}

function NumeroRegistro(xhr, status, args)
{
    var info = args.NumeroRegistro;
    if(info ==='já existe')
    {
         $("input:text[name='dinhFormID:dinheiroRegistro']").css('border','1px solid red');
     $('html, body').animate({ scrollTop: 0 }, 'fast');
    }       
    else
        $("input:text[name='dinhFormID:dinheiroRegistro']").css('border','');
}
function riscos()
{
    $("input:checkbox[name='multi:dinheiroComb']").attr('checked', false);
    $("input:checkbox[name='multi:rouboComb']").attr('checked', false);
    $("input:checkbox[name='multi:incendioComb']").attr('checked', false);
}
function apoliceDinheiro()
{
    $('html, body').animate({ scrollTop: 0 }, 'slow');
    $(".dinheiroApolice").focus();
}
function topo()
{
    $(".DregistroC").focus();
     $('html, body').animate({ scrollTop: 0 }, 'slow');
}