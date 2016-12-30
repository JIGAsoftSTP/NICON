/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var usuarioValido = true;
$(document).ready(function (e)
{
    $(".cancelarAlterarNivel").click(function (e)
    {
       e.preventDefault();
       $(".mp-messages").fadeOut();
    });
    $(".cancelarEditarFuncionario").css("display","none");
    $(".cancelarFuncionarioOp").click(function(e)
    {
        e.preventDefault();
         $(".funcionarOperacao").fadeOut();
    });
    $(".numeroInterio").keyup(function (e)
        {
            e.preventDefault();
                    var expre = /[^0-9]/g;
                    // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
                    if ($(this).val().match(expre))
                            $(this).val($(this).val().replace(expre, ''));
        });
        $(".addUser").click(function (e)
        {
            e.preventDefault();
           ParteUilizador(); 
        });
     $(".FunUtilSenha1").keyup(function (e)
     {
        testeSenha($(".FunUtilSenha1"),$(".FunUtilSenha2"));
     });
      $(".FunUtilSenha2").keyup(function (e)
     {
        testeSenha($(".FunUtilSenha1"),$(".FunUtilSenha2"));
     });
     
   $(".alterarSenha").click(function (e)
   {
      e.preventDefault();
      $(".senhaAtualFuncionario").val("");
      $(".novaSenhaFuncionario").val("");
     $(".confirmarSenhaFuncionario").val("");
   });
 });

function Parte1Proximo()
{
   var teste0 = testeVasio($(".fucP1Morada"));
   
   var teste10= $(".fucP1ValideEmail").html();
   var teste11= $(".fucP1ValideNIF").html();
   var teste1=true;
   
   if(!soTeste($(".fucP1Email")))
   {
       soCor($(".fucP1Email"),"");
   }
   
   var teste2 = testeVasio($(".fucP1Sexo"));
   
   var teste3=true;
   if(teste11==="true")
        teste3 = testeVasio($(".fucP1Nif"));

   var teste4 = testeVasio($(".fucP1Apelido"));
   var teste5 = testeVasio($(".fucP1Telefone"));
   var teste7 = testeVasio($(".fucP1EstadoCivil"));
   var teste8 = testeVasio($("input:text[name='FunCform1:funcDadoDataNac_input']"));
   var teste9 = testeVasio($(".fucP1Nome"));
   
    var valido= ((teste0===false)?teste0:(teste1===false)?teste1:(teste2===false)?teste2:(teste3===false)?teste3:(teste4===false)?teste4:(teste5===false)?teste5
          :(teste7===false)?teste7:(teste8===false)?teste8:(teste9===false)?teste9:(teste10==="false")?false:(teste11==="false")?false:true);
    if(valido)
        proximo();  
}

function testeVasio(variavel)
{
    var teste=true;
    if(variavel.val()==="")
    {
        variavel.css("border","1px solid red");
        variavel.focus();
        teste=false;
    }
    else
    {
        variavel.css("border","");
    }
    
    return teste;
}

function soTeste(variavel)
{  
    return !(variavel.val()===""||variavel.val()==="0");
}

function Parte2Concluir()
{
  
   var teste2 = soTeste($(".fucP2NomeC"));
   var teste1 = soTeste($("input:text[name='FunCform2:fucP1DataNasC_input']"));
   var teste3 = soTeste($(".fucP2AplidoC"));
   var teste4 = soTeste($(".fucP2EstadoCivil"));
   
   var valido=true;
   
   if(teste1||teste2||teste3 || teste4)
   {
       teste2 = testeVasio($(".fucP2NomeC"));
       teste1 = testeVasio($("input:text[name='FunCform2:fucP1DataNasC_input']"));
       teste3 = testeVasio($(".fucP2AplidoC"));
       teste4 = testeVasio($(".fucP2EstadoCivil"));
       
       valido=((!teste1)?teste1:(!teste2)?teste2:(!teste3)?teste3:(!teste4)?teste4:true);
   }
   else
   {
       soCor($(".fucP2NomeC"),"");
       soCor($("input:text[name='FunCform2:fucP1DataNasC_input']"),"");
       soCor($(".fucP2AplidoC"),"");
       soCor($(".fucP2EstadoCivil"),"");   
       valido=true;
   }
    
    
   var teste5 = testeVasio($(".fucP2CodogoNicon"));
   var teste6 = testeVasio($(".fucP2Categoria"));
   var teste7 = testeVasio($(".fucP2Banco"));
   var teste8 = testeVasio($("input:text[name='FunCform2:fucP2DataEntrada_input']"));
   var teste9 = testeVasio($(".fucP2NunContaBanco"));
   var teste10 = testeVasio($(".dept"));
   var teste11 = testeVasio($(".nivelFuncionario"));
   
}
function ParteUilizador()
{
   
   var teste2= $(".FunUtilLis").val();
   var teste1 = $(".FunUtilNivel").val();
   
    if(teste2 ==="")
       $(".FunUtilLis").css("border","1px solid red");
   else
        $(".FunUtilLis").css("border","");
    if(teste1 ==="")
       $(".FunUtilNivel").css("border","1px solid red");
   else
        $(".FunUtilNivel").css("border","");
}

function limpar()
{
    $(".FunUtilLis").val("");
   $(".FunUtilNivel").val("");
   $(".FunUtilName").val("");
}
function testeSenha(senha1,senha2)
{
    if(senha1.val()===senha2.val()&&senha1.val()!==""&&senha2.val()!=="")
    {
        soCor(senha1,'#39ee9c');
        soCor(senha2,'#39ee9c');
        usuarioValido=true;
    }
    else
    {
        soCor(senha1,'red');
        soCor(senha2,'red');
        usuarioValido=false;
    }
    
    return usuarioValido;
}

function resertDadosUtilizador()
{
    $(".FunUtilLisLimpar").val("");
    soCor( $(".FunUtilLisLimpar"),"");
}
function soCor(variavel,cor)
{
    if(cor!=="")
        variavel.css("border","1px solid "+cor);
    else
        variavel.css("border","");
  
}
function PartefuncioLimpar()
{
    $(".funcioLimpar").val("");
    $("input:text[name='FunCform2:fucP2DataEntrada_input']").val("");
    $("input:text[name='FunCform1:funcDadoDataNac_input']").val("");
    $("input:text[name='FunCform2:fucP1DataNasC_input']").val("");            
}

function modalRedifinirSenha()
{
    $(".funcionarOperacao").fadeIn();
    $(".titulo").html("Restaurar palavra-passe");
    $(".mensagemApresentada").html("Ao efetuar esta operação o utilizador terá que redifinir a sua senha\n\
     de acesso na próxima vez que iniciar a sessão. \n\nDeseja realmente restaurar a palavra-passe?");
    
}
function modalAtivarDesativar()
{
    var selecionado = $(".check").is(":checked");
    $(".titulo").html("Ativar/Desativar funcionário");
    if(selecionado === true)
    {
        $(".mensagemApresentada").html("Deseja ativar este utilizador?");
    }
    else
    {
        $(".mensagemApresentada").html("Deseja desativar este utilizador?");
    }
    $(".funcionarOperacao").fadeIn();
}


function modalFecharOperacao()
{
     $(".funcionarOperacao").fadeOut();
}

function proximo()
{
    $( '.form1' ).css("display","none");
    $( '.form2' ).css("display","block");
}

function anterior()
{
    $( '.form2' ).css("display","none");
    $( '.form1' ).css("display","block");
}


function dadosPessoaisFuncionario(nome,dataNasc,estadoCivil,numFilhos,telefone,apelido,nif,sexo,email,morada)
{
    var dataNascFunc = "input:text[name='FunCform1:funcDadoDataNac_input']";
    $(dataNascFunc).val(dataNasc);
    $(".fucP1Nome").val(nome);
    $(".fucP1EstadoCivil").val(estadoCivil);
    $(".fucP1NumFilho").val(numFilhos);
    $(".fucP1Telefone").val(telefone);
    $(".fucP1Apelido").val(apelido);
    $(".fucP1Nif").val(nif);
    $(".fucP1Sexo").val(sexo);
    $(".fucP1Email").val(email);
    $(".fucP1Morada").val(morada);
     
}
function dadosEmpresa(codigoNicon,dataEntrada,departamento, categoria,banco, numConta, numNivel)
{
    var dataEn = "input:text[name='FunCform2:fucP2DataEntrada_input']";
   
    console.log("conta "+numConta);
    $(".fucP2CodogoNicon").val(codigoNicon);
    $(dataEn).val(dataEntrada);
    $(".dept").val(departamento);
    $(".fucP2Categoria").val(categoria);
    $(".fucP2Banco").val(banco);
    $(".fucP2NunContaBanco").val(numConta);  
    $(".numNivel").val(numNivel);  
}

function dadosConjugue(nome, apelido, dataNascimento, estadoCivil)
{
       var dataNasc = "input:text[name='FunCform2:fucP1DataNasC_input']";
      
    $(".fucP2NomeC").val(nome);
    $(".fucP2AplidoC").val(apelido);
    $(dataNasc).val(dataNascimento);
    $(".fucP2EstadoCivil").val(estadoCivil);
    
    
}
 function mostrarProcessFunc()
{

    $(".modalProcess").show();
}
function fecharProcessFunc()
{
     $(".modalProcess").hide();
}

function cancelarEditar()
{
    $(".cancelarEditarFuncionario").css("display","none");
    $(".form1 input:text").val("");
    $(".form1 select").val("");
    $(".form1 select").val("");
    $(".form1 select").css("border","");
    $(".form1 input:text").css("border","");
    $(".form2 input:text").css("border","");
    $(".form2 select").css("border","");
    
}

function funcionarioNifFoco()
{
    $('.fucP1Nif').focus();
}
function funcionariocodigoBorda()
{
    $('.fucP2CodogoNicon').css("border","1px solid red");
    $('.fucP2CodogoNicon').focus(); 
}

function mostrarBotaoCancelarEditar()
{
    $(".cancelarEditarFuncionario").css("display","");
}

function  mostrarModalNivel()
{
    $(".nivel").fadeIn();
}
function  fecharModalNivel()
{
    $(".nivel").fadeOut();
}