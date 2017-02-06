/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    
    //activateMenuSec($('GC-MENU'));
    activateMenuPr( $('#AD-MENU-SC') , $('#AD-MENU-PR') , 'AD-MENU');
    activateMenuPr( $('#CT-MENU-SC') , $('#CT-MENU-PR') , 'CT-MENU');
    activateMenuPr( $('#GC-MENU-SC') , $('#GC-MENU-PR') , 'GC-MENU');
    activateMenuPr( $('#RH-MENU-SC') , $('#RH-MENU-PR') , 'RH-MENU');
    activateMenuPr( $('#SN-MENU-SC') , $('#SN-MENU-PR') , 'SN-MENU');    
    
    $('.notifications label').text( $( ".list-notification nav" ).length);
    $('.notifications').click(function () {
        $('.menuContabilidade').toggleClass('show');
    });
    $('.ant-page').click(function () {
        $('.menuContabilidade').removeClass('show');
    });
});

//############################## CONTROL PAGES AND MENUS -- ACTIVATE ###################
function activateMenuPr (menuSec , menuPrin , _class) {
    if(menuSec.hasClass(_class) && menuPrin.hasClass(_class))  menuPrin.addClass('ACTIVE-MENU-PRINCIPAL');
};