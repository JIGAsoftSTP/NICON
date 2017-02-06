$(document).ready(function (e)
{
    $(".coberturasResponsabilidade").attr('disabled',true);
    $(".RespPublicaPremio").attr('disabled', true);
     $(".RespPublicaBotao").click(function (e)
    {
       e.preventDefault();
         Seguinte();
    });
    $(".repPublicaAdd").click(function (e)
    {
        e.preventDefault();
        VerificarCamposAdicionar();
    });
    
      $(".RespPublicaNumero").keyup(function(e){
        // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
        if(!$.isNumeric($(this).val()))
            $(this).val("");
   });
   
   $(".valorCadaAcidenteResp").keyup(function (e)
   {
      e.preventDefault();
      $(".taxaAcidenteResp").val("");
      $(".premioAcidenteResp").val("");
      
   });
   
    $(".respPeriodoValor").keyup(function (e)
   {
      e.preventDefault();
      $(".respPeriodoTaxa").val("");
      $(".peridoSeguroPremio").val("");
      
   });
});


function VerificarCamposAdicionar()
{
    var empregado = $("input:text[name='respForm:SeguroRespPublicaEmpregado']").val();
    var profiisao = $("input:text[name='respForm:SeguroRespPublicaProfissao']").val();
    var endereco = $("input:text[name='respForm:SeguroRespPublicaEndereco']").val();
    
    if(empregado ==='')
        $("input:text[name='respForm:SeguroRespPublicaEmpregado']").css('border-color','red');
    else
         $("input:text[name='respForm:SeguroRespPublicaEmpregado']").css('border-color','');
     if(profiisao ==='')
         $("input:text[name='respForm:SeguroRespPublicaProfissao']").css('border-color','red');
     else
         $("input:text[name='respForm:SeguroRespPublicaProfissao']").css('border-color','');
     if(endereco ==='')
         $("input:text[name='respForm:SeguroRespPublicaEndereco']").css('border-color','red');
     else
         $("input:text[name='respForm:SeguroRespPublicaEndereco']").css('border-color','');
}

function Limpar()
{
    $("input:text[name='respForm:SeguroRespPublicaEmpregado']").val("");
    $("input:text[name='respForm:SeguroRespPublicaProfissao']").val("");
    $("input:text[name='respForm:SeguroRespPublicaEndereco']").val("");
    
  
}

function verificarNumeroRegistroRespPublica(xhr, status, args)
{
    var info = args.numeroR;
    if(info ==='já existe')
    {
        $("input:text[name='respForm:ResPublicanNumeroRegistro']").css("border","1px solid red");
        $("input:text[name='respForm:ResPublicanNumeroRegistro']").focus();
    }
    else
          $("input:text[name='respForm:ResPublicanNumeroRegistro']").css("border","");
    
}

function CoberturaSelecionada()
{
      $("input:checkbox[name='respForm:RespPublicaCoberturaIncendio']").focus();
}

function NumeroRegistroFoco()
{
     $('html, body').animate({ scrollTop: 0 }, 'fast');
     $("input:text[name='respForm:ResPublicanNumeroRegistro']").focus();
}

function Seguinte()
{
    var salarioDiretor = $(".respSalario1").val();
    var coberturaIncendio = $("input:checkbox[name='respForm:RespPublicaCoberturaIncendio']").is(":checked");
    var salarioEmpreiteiro = $(".respSalario2").val();
    var detalhes = $(".RespPublicaDetalhes").val();
    var outroValor = $(".RespPublicaValorOutro").val();
    var coberturaIntoxicacao =$("input:checkbox[name='respForm:RespPublicaIntoxicacao']").is(":checked");
    var valorIncendio = $(".RespPublicaValorIncendio").val();
    var valorIntoxicacao = $(".RespPublicaValorIntoxicacao").val();
    var coberturaOutro = $("input:checkbox[name='respForm:RespPublicaCoberturaOutro']").is(":checked");
    if(salarioDiretor ==='')
        $(".respSalario1").css('border','1px solid red');
    else
        $(".respSalario1").css('border','');
    if(salarioEmpreiteiro ==='')
        $(".respSalario2").css('border','1px solid red');
    else
        $(".respSalario2").css('border','');
    if(coberturaIncendio === true)
        if(valorIncendio ==='')
        {
            $(".RespPublicaValorIncendio").css('border','1px solid red');
            $(".apoliceRespPublica").focus();
        }
        else
             $(".RespPublicaValorIncendio").css('border','');
    else
        $(".RespPublicaValorIncendio").css('border','');
    if(coberturaIntoxicacao === true)
        if(valorIncendio ==='')
            if(valorIntoxicacao ==='')
            {
                $(".RespPublicaValorIntoxicacao").css('border','1px solid red');
                $(".RespPublicaValorIncendio").focus();
            }
            else
                $(".RespPublicaValorIntoxicacao").css('border','');
        else
        {
            
             if(valorIntoxicacao ==='')
            {
                $(".RespPublicaValorIntoxicacao").css('border','1px solid red');
                $(".RespPublicaValorIntoxicacao").focus();
            }
            else
                $(".RespPublicaValorIntoxicacao").css('border','');
        }
    else
    {
         $(".RespPublicaValorIntoxicacao").css('border','');
          $(".RespPublicaValorIntoxicacao").focus();
    }
    if(coberturaOutro === true)
    {      
        if(detalhes ==='')
            $(".RespPublicaDetalhes").css('border','1px solid red');
        else
            $(".RespPublicaDetalhes").css('border','');
        if(outroValor ==='')
            $(".RespPublicaValorOutro").css('border','1px solid red');
        else
             $(".RespPublicaValorOutro").css('border','');
    }
    else
    {
        $(".RespPublicaDetalhes").css('border','');
        $(".RespPublicaValorOutro").css('border','');
    }
    
    
}

function EmpregadoFoco()
{
    $("input:text[name='respForm:SeguroRespPublicaEmpregado']").focus();
}

function CoberturaSelecao()
{
    var coberturaIncendio = $("input:checkbox[name='respForm:RespPublicaCoberturaIncendio']").is(":checked");
    var coberturaIntoxicacao =$("input:checkbox[name='respForm:RespPublicaIntoxicacao']").is(":checked");
    var coberturaOutro = $("input:checkbox[name='respForm:RespPublicaCoberturaOutro']").is(":checked");
    if(coberturaIncendio === true)
        $(".RespPublicaValorIncendio").attr('disabled', false);
    else
    {
         $(".RespPublicaValorIncendio").val("");
        $(".RespPublicaValorIncendio").attr('disabled', true);
        
    }
        
    if(coberturaIntoxicacao === true)
        $(".RespPublicaValorIntoxicacao").attr('disabled', false);
    else
    {
        $(".RespPublicaValorIntoxicacao").val("");
        $(".RespPublicaValorIntoxicacao").attr('disabled', true);
        
    }
          
    if(coberturaOutro === true)
    {
        $(".RespPublicaDetalhes").attr('disabled', false);
        $(".RespPublicaValorOutro").attr('disabled', false);
    }
    else
    {
         $(".RespPublicaDetalhes").val("");
        $(".RespPublicaValorOutro").val("");
        $(".RespPublicaDetalhes").attr('disabled', true);
        $(".RespPublicaValorOutro").attr('disabled', true);
    }
    
}

function valorPremio(xhr, status, args)
{
    var info = args.info1;
    var infoR = args.info2;
    if(info !=='')
    {
       $(".premioAcidenteResp").val(info); 
    }
    if(infoR !=='')
    {
       $(".peridoSeguroPremio").val(infoR);  
    }
 
}

function coberturas()
{
    var i = 0;
    var veiculo = $(".veiculoValorResp").is(":disabled");
    var veiculoValor = $(".veiculoValorResp").val();
    var guindaste = $(".guindaste").is(":disabled");
    var guindasteValor = $(".guindaste").val();
    var explosao = $(".explosao").is(":disabled");
    var explosaoValor = $(".explosao").val();
    var inspecao = $(".inspecoes").is(":disabled");
    var inspecaoValor = $(".inspecoes").val();
    var licenca = $(".licenca").is(":disabled");
    var licencaValor = $(".licenca").val();
    var acidos = $(".acidos").is(":disabled");
    var acidoValor = $(".acidos").val();
    
    if(veiculo === false)
    {
        if(veiculoValor ==='')
        {
            $(".veiculoValorResp").css('border','1px solid red');
            i = 1;
        }
            
        else
            $(".veiculoValorResp").css('border','');
        
    }
    if(guindaste === false)
    {
        if(guindasteValor ==='')
        {
             $(".guindaste").css('border','1px solid red');
             i = 1;
        }
           
        else
            $(".guindaste").css('border','');
    }
    if(explosao === false)
    {
        if(explosaoValor ==='')
        {
             $(".explosao").css('border','1px solid red');
             i =1;
        }
           
        else
            $(".explosao").css('border','');
    }
    if(inspecao === false)
    {
        if(inspecaoValor ==='')
        {
            $(".inspecoes").css('border','1px solid red');
            i = 1;
        }
            
        else
            $(".inspecoes").css('border','');
    }
    if(licenca ===false)
    {
        if(licencaValor ==='')
        {
            $(".licenca").css('border','1px solid red');
            i =1;
        }
            
        else
            $(".licenca").css('border','');
    }
    if(acidos === false)
    {
        if(acidoValor ==='')
        {
            $(".acidos").css('border','1px solid red');
            i = 1;
        }
            
        else
            $(".acidos").css('border','');
    }
    if(i===0)
        window.location.href = "GestSeg_NovoSeguroApolice.xhtml";
}

function apoliceResp()
{
     $('html, body').animate({ scrollTop: 0 }, 'slow');
    $(".apoliceRespPublica").focus();
}

