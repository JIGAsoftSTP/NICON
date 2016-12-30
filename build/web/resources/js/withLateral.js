/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function(e){
    $('.menu-lateral').on('click', 'li', function () {
            
            getRowCount('.tabelaContratos');
            var actual = $(this);
            //se for menu singular
            if(actual.parent().hasClass('single-menu-lateral')){
                if(actual.hasClass('header-menu') !== true)
                    actual.addClass('active-item-menu').siblings().removeClass('active-item-menu');
            }
            //se for menu multi
            else{
                //se o click for num header do menu
                if(actual.hasClass('header-menu')){
                    
                    var brotherHeaders = actual.parent().siblings().find('.header-menu');
                    var firstItem = actual.next().find('li:nth-child(1)');
                    var firstItem1 = actual.next().find('li:nth-child(1) > label');
                    actual.addClass('active-header-menu');
                    brotherHeaders.removeClass('active-header-menu');
                    
                    actual.parent().find('li').removeClass('active-item-menu');
                    firstItem.addClass('active-item-menu');
                    
                    firstItem1.trigger( "click" );
                    
                    actual.next().slideToggle();
                    
                    actual.parent().siblings().find('div').slideUp();
                    
                }else {
                    actual.addClass('active-item-menu').siblings().removeClass('active-item-menu');
                }
            }
           
        });
        
        $('.hide-show-lateral').click(function (){
            $(this).toggleClass('is-hidden');
            $('.menu-lateral').toggleClass('shrink');
            $('aside').toggleClass('shrink');
        });
        

        
        $(".fechar").click(function (e)
        {
    
 
             $(".btPreviousRess").trigger('click');
             $(".pageModal").addClass('fecharModalV');
                        e.preventDefault();
        });
});
/*
 * Single menu lateral 
 * <div class = "lateral-menu-content-page">
 *      <div class = "menu-lateral">
 *          <ul class = "single-menu-lateral"> //aqui é definido o tipo de menu lateral (Single ou Multi)
*                <li class="header-menu>Menu here!</li>
*                <li class="active-item-menu">Item 1 (Activated) </li>
*                <li >Item 2</li>
*                <li >Item 3</li>
 *          </ul>
 *      </div>
 *      <div class = "content-lateral">
 *      
 *      </div>
 * </div>
 * 
 * 
 * Multi menu lateral 
 * <div class = "lateral-menu-content-page">
 *      <div class = "menu-lateral">
 *          <ul class = "multi-menu-lateral"> //aqui é definido o tipo de menu lateral (Single ou Multi)
 *              <section>
 *                  <li class="header-menu  active-header-menu">Menu here!</li>
 *                  <li class="active-item-menu">Item 1 (Activated) </li>
 *                  <li >Item 2</li>
 *                  <li >Item 3</li>
 *              </section>
 *              
 *              <section>
 *                  <li class="header-menu  active-header-menu">Menu here!</li>
 *                  <li class="active-item-menu">Item 1 (Activated) </li>
 *                  <li >Item 2</li>
 *                  <li >Item 3</li>
 *              </section>
 *              ...
 *          </ul>
 *      </div>
 *      <div class = "content-lateral">
 *      
 *      </div>
 * </div>
 */
$("input[id='dataIniSup']").css("border","1px solid red");
function contrato(titulo)
{
   if(titulo !== "fechar")
   {
        $("input[name='resist:dataFimSup_input']").attr("disabled", false);
        $(".contratoTitulo").html(titulo);
        if(titulo === "Suspender Contrato")
            $(".period-date").show();
        else if(titulo === 'Alterar Data Inicio Contrato')
        {
            $(".period-date").show();
             $("input[name='resist:dataFimSup_input']").attr("disabled", true);
        }
        else  $(".period-date").hide();
        $(".mp-contratos-1").fadeIn();
   }
   else
   { $(".mp-contratos-1").fadeOut(); }  
}

function ativarCampo()
{
    $('.contratoDataFIM').attr('disabled', false);
}

function dataSupenValide(i)
{
    if(i === 1 ) { $("input[name='resist:dataIniSup_input']").css("border","1px solid red"); }
    else if(i === 2){ $("input[name='resist:dataFimSup_input']").css("border","1px solid red"); }
    else if(i === 3){ $("textarea[name='resist:contratoJustificacao']").css("border","1px solid red"); }
    
    if(i === -1 ) { $("input[name='resist:dataIniSup_input']").css("border",""); }
    else if(i === -2){ $("input[name='resist:dataFimSup_input']").css("border",""); }
    else if(i === -3){ $("textarea[name='resist:contratoJustificacao']").css("border",""); }
}

//function mostrarModalContrato(operacao)
//{
//      $(".period-date").css("display","");
//    if(operacao==='mostrar')
//    $(".mp-contratos-1").fadeIn();
//    else
//         $(".mp-contratos-1").fadeOut(400);
//}

function adicionarResseguro(dataI, dataF)
{
    var dataInicio = "input:text[name='resseguroForm:resseguroDataI_input']";
    var dataFim = "input:text[name='resseguroForm:resseguroDataF_input']";
    $(dataInicio).val(dataI);
    $(dataFim).val(dataF);
    $(".btPreviousRess").trigger('click');
    $(".pageModal").removeClass('fecharModalV');
}