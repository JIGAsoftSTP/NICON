/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var active, indexSelected, lastSelected;

$(document).ready(function(e){
    $('.label-active').addClass('active-multi-header'); //colocar como selecionado os 2 primeiros seguros (Roubo e Incendio)
    $('.section-active').addClass('display-block'); // colocar como visivel o seguro Roubo
    
    /* Evento click para tipos de seguro */
    $('.multi-header label').click(function () {

        countActive($(this)); 

    });
    estadoInicial();
    /* Evento a ser disparado quando clicar num dos menus/abas do seguro*/
    $('.seguros-1').click(function () {

        showActive2( $(this) , $('.seguros-1') , $('.seguros-2'));

    });
    /*
     * var para incrementar ou decrementar a medida que um seguro vai sendo adicionado ou removido
     * a variavel inicia com o valor "2" porque tem de haver no mínimo 2 seguros selecionado
     */
    active = 2;
    
});
    /*
     * função a ser chamado ao adicionar ou remover um seguro
     */
    function countActive( element ){
        //se ao clicar tiver a classe "active-multi-header" --> entao o seguro está selecionado e estamos a des_selecionar...
         if(element.hasClass('active-multi-header')){
            if(active > 2){ // ... se existem mais de dois seguros selecionados
                active--;   // ...decrementa a variavel,
                element.removeClass('active-multi-header'); //...revova a classe que indica que o seguro está selecionado, 
                showActive( element , $('.seguros-1') , false ); //...desativa a aba do seguro atual e oculte-o
                controlTab( element ); //...verifique qual seguro ficará ativo
                lastSelected = -1; //... atribua um valor diferente de todo possivel "index" que possa exister
            }
         }
         
        else{//se não, então estamos a selecionar  um novo seguro, logo...
            active ++; // incrementa a variavel
            element.addClass('active-multi-header'); //marca o seguro atual como selecionado
            showActive( element , $('.seguros-1') , true ); //... ativa a aba e mostra o formulario do seguro atual
            lastSelected = indexSelected; //atribuir ao "ultimo selecionado" o mesmo index do seguro atual
        }
        
    }
    /*
     * Função para marcar o seguro como selecionado, ativar a aba, e mostrar o formulario seguro selecionado
     */
    function showActive ( element , toShow , isShow){
        
        indexSelected = element.index(); // atribua a variavel global o index do item selecionado
        //se estamos a selecionar um novo seguro, então...
        if (isShow) {
            toShow.eq(indexSelected).addClass('display-block'); //... torne visivel a aba do seguro atual, 
            showActive2( element , $('.seguros-1') , $('.seguros-2') );//... ative a aba e mostre também o formulario seguro atual.
        }
        //senão,
        else{
            toShow.eq(indexSelected).removeClass('display-block');//...oculte a aba do menu atual
        }
                 
    }
    /*
     * Função disparada ao selecionar um seguro
     * Esta função também é utilizada para controlar que seguro aparecer ao selecionar uma aba
     */
    function showActive2 ( element , activeHeader , showContent ){
        
        indexSelected = element.index(); // atribua a variavel global o index do item selecionado
        //ative e mostre a aba e...
        activeHeader.eq(indexSelected).addClass('active-content-header').siblings().removeClass('active-content-header');     
        //... mostre também o formulário do seguro selecionado
        showContent.eq(indexSelected).addClass('display-block').siblings().removeClass('display-block');
    }
    /*
     * Esta função serve para saber que seguro mostrar a seguir depos de utilizador des_selecionar um seguro
     * Esta função só é chamada quando um seguro é removido da seleção
     */
    function controlTab( element ){
        //se o seguro que se pretende des_selecionar foi o ultimo a ser selecionado, e...
        if( indexSelected === lastSelected ){
            //... este seguro é o último em termos de elementos do DOM (Seguro Dinheiro), então,
            if(indexSelected > 1)                
                showActive2( element.prev() , $('.seguros-1') , $('.seguros-2')); //... ative o seguro anterior (Seguro Incendio)
            //senão, des_selecionamos o seguro atual e selecionamos e seguinte (Seguro Incendio ou Seguro Dinheiro)
             else
                showActive2( element.next() , $('.seguros-1') , $('.seguros-2')); 
            
        }
    }
    function estadoInicial()
    {
        $(".cabecalhoSegurosMulti").css('display','none');
        $(".seguroRouboCabecalho").css('display','none');
        $(".seguroIncendioCabecalho").css('display','none');
    }