
var preenchido = 0;
$(document).ready(function(e){
   
    $(".incluirRoubo").css("display","none");
    $(".incluirIncendio").css("display","none");
    $(".incluirDinheiro").css("display","none");
    $(".seguroDinheiroCabecalho").css("display","none");
    $(".seguroRouboCabecalho").css("display","none");
    $(".seguroIncendioCabecalho").css("display","none");
    $(".menuIncendio").css("display","none");
    $(".menuRoubo").css("display","none");
    $(".menuDinheiro").css("display","none");
     
     $(".multiOk").click(function (e)
     {
        e.preventDefault();
        $(".multiModal").fadeOut();
     });
   $(".incendio").click(function(e)
   {
        var Dinh = $("input:checkbox[name='multi:dinheiroComb']").is(':checked');
        var roub = $("input:checkbox[name='multi:rouboComb']").is(':checked');
        var Inc = $("input:checkbox[name='multi:incendioComb']").is(':checked');
        enviar();
        if(Inc ===true) // se o incêndio for o selecionado
        {
            // se for selecionado roubo e dinheiro inclui o menu de incêndio e a pagina.
            if(roub === false && Dinh === false)
            {
                $(".menuIncendio").css("background","#eee");
                $(".incluirIncendio").fadeToggle();
                $(".menuIncendio").fadeToggle();
            }
            else
            {
                  $(".menuIncendio").fadeToggle();
                  $(".menuIncendio").css("background","white");
            }  
        }
        else
        {
           var corMenuRoubo = $(".menuRoubo").css("background-color");
           var corMenuDinheiro = $(".menuDinheiro").css("background-color");
           if(roub === true)
           {
               if( corMenuRoubo!=='rgb(238, 238, 238)')
               {
                   $(".menuRoubo").css("background","#eee");
                   $(".incluirRoubo").fadeToggle();
               }
           }
           else
           {
                if(Dinh === true)
                {
                    if( corMenuDinheiro!=='rgb(238, 238, 238)')
                    {
                        $(".menuDinheiro").css("background","#eee");
                        $(".incluirDinheiro").fadeToggle();
                    }
                }
           }
            $(".incluirIncendio").fadeOut();
            $(".menuIncendio").fadeOut();
        }
   });
   $(".roubo").click(function(e)
   {
        var Dinh = $("input:checkbox[name='multi:dinheiroComb']").is(':checked');
        var roub = $("input:checkbox[name='multi:rouboComb']").is(':checked');
        var Inc = $("input:checkbox[name='multi:incendioComb']").is(':checked');
        enviar();
        if(roub ===true)
        {
            if(Inc === false && Dinh === false)
            {
                $(".menuRoubo").css("background","#eee");
                $(".incluirRoubo").fadeToggle();
                $(".menuRoubo").fadeToggle(); 
            }
            else
            {
                $(".menuRoubo").fadeToggle(); 
                $(".menuRoubo").css("background","white");
            }        
        }
        else
        {
            var corMenuIncendio = $(".menuIncendio").css("background-color");
            var corMenuDinheiro = $(".menuDinheiro").css("background-color");
            
            if(Inc === true)
            {
                if(corMenuIncendio!=='rgb(238, 238, 238)')
                {
                    $(".menuIncendio").css("background","#eee");
                    $(".incluirIncendio").fadeToggle();
                }
            }
            else
            {
                if(Dinh === true)
                {
                    if(corMenuDinheiro!=='rgb(238, 238, 238)')
                    {
                        $(".menuDinheiro").css("background","#eee");
                        $(".incluirDinheiro").fadeToggle(); 
                    }  
                }
            }
            $(".incluirRoubo").fadeOut();
            $(".menuRoubo").fadeOut();
        }
   });
   $(".dinheiro").click(function(e)
   {
        var Dinh = $("input:checkbox[name='multi:dinheiroComb']").is(':checked');
        var roub = $("input:checkbox[name='multi:rouboComb']").is(':checked');
        var Inc = $("input:checkbox[name='multi:incendioComb']").is(':checked');
        enviar();
        if(Dinh ===true)
        {
            if(roub === false && Inc === false)
            {
                $(".menuDinheiro").css("background","#eee");
                $(".incluirDinheiro").fadeToggle();
                $(".menuDinheiro").fadeToggle();
            }
            else
            {
               $(".menuDinheiro").fadeToggle(); 
               $(".menuDinheiro").css("background","white"); 
            }
        }
        else
        {
           var corMenuIncendio = $(".menuIncendio").css("background-color");
           var corMenuRoubo = $(".menuRoubo").css("background-color");
            if(Inc === true)
            {
                if(corMenuIncendio!=='rgb(238, 238, 238)')
                {
                    $(".menuIncendio").css("background","#eee");
                    $(".incluirIncendio").fadeToggle();
                }
            }
            else
            {
                if(roub === true)
                {
                    if( corMenuRoubo!=='rgb(238, 238, 238)')
                    {
                        $(".menuRoubo").css("background","#eee");
                        $(".incluirRoubo").fadeToggle();
                    }
                }
            }
            $(".incluirDinheiro").fadeOut();
            $(".menuDinheiro").fadeOut();
        }
   });
 
});

function dados(incendio,roubo,dinheiro)
{
    if(incendio !=='')
        $(".multiDadosIncendio").html(incendio);
    if(roubo!=='')
       $(".multiDadosRoubo").html(roubo);
    if(dinheiro !=='')
        $(".multiDadosDinheiro").html(dinheiro);
}
function trocarPagina()
{
    var Dinh = $("input:checkbox[name='multi:dinheiroComb']").is(':checked');
    var roub = $("input:checkbox[name='multi:rouboComb']").is(':checked');
    var Inc = $("input:checkbox[name='multi:incendioComb']").is(':checked');
    var corMenuRoubo = $(".menuRoubo").css("background-color");
    var corMenuDinheiro = $(".menuDinheiro").css("background-color");
    var corMenuIncendio = $(".menuIncendio").css("background-color");
    
    
    if(Inc === true && roub === true && Dinh === true)
    {
    
        if(corMenuIncendio ==='rgb(238, 238, 238)')
        {
            if($(".multiDadosRoubo").html()!=='dados roubo' )
            {
                $(".menuIncendio").css("background","white");   
                $(".incluirIncendio").fadeOut(); 
                $(".menuRoubo").css("background","#eee");
                $(".incluirRoubo").fadeToggle();
            }
        }

        if(corMenuRoubo ==='rgb(238, 238, 238)')
        {
            if($(".multiDadosDinheiro").html()!=='dados dinheiro')
            {
                $(".menuRoubo").css("background","white");   
                $(".incluirRoubo").fadeOut(); 
                $(".menuDinheiro").css("background","#eee");
                $(".incluirDinheiro").fadeToggle();
            }
        }
        if(corMenuDinheiro ==='rgb(238, 238, 238)')
        {
            if($(".multiDadosIncendio").html()!=='dados incêndio' )
            {
                $(".menuDinheiro").css("background","white");   
                $(".incluirDinheiro").fadeOut(); 
                $(".menuIncendio").css("background","#eee");
                $(".incluirIncendio").fadeToggle();
            }
        }
       
    }
    else if(Inc === true && roub === true && Dinh === false)
    {
        if(corMenuIncendio ==='rgb(238, 238, 238)')
        {
            if($(".multiDadosRoubo").html()!=='dados roubo')
            {
                $(".menuIncendio").css("background","white");   
                $(".incluirIncendio").fadeOut(); 
                $(".menuRoubo").css("background","#eee");
                $(".incluirRoubo").fadeToggle();
            }
        }
        if(corMenuRoubo ==='rgb(238, 238, 238)')
        {
            if($(".multiDadosIncendio").html()!=='dados incêndio')
            {
                $(".menuRoubo").css("background","white");   
                $(".incluirRoubo").fadeOut(); 
                $(".menuIncendio").css("background","#eee");
                $(".incluirIncendio").fadeToggle();
            }
        }
    }
    else if(Inc === true && roub === false && Dinh === true)
    {
         if(corMenuIncendio ==='rgb(238, 238, 238)')
        {
            if($(".multiDadosDinheiro").html()!=='dados dinheiro')
            {
                $(".menuIncendio").css("background","white");   
                $(".incluirIncendio").fadeOut(); 
                $(".menuDinheiro").css("background","#eee");
                $(".incluirDinheiro").fadeToggle();
            }
        }
        if(corMenuDinheiro ==='rgb(238, 238, 238)')
        {
            if($(".multiDadosIncendio").html()!=='dados incêndio')
            {
                $(".menuDinheiro").css("background","white");   
                $(".incluirDinheiro").fadeOut(); 
                $(".menuIncendio").css("background","#eee");
                $(".incluirIncendio").fadeToggle();
            }
        }
    }
    else if(Inc === false && roub === true && Dinh === true)
    {
        if(corMenuRoubo ==='rgb(238, 238, 238)')
        {
            if($(".multiDadosDinheiro").html()!=='dados dinheiro')
            {
                $(".menuRoubo").css("background","white");   
                $(".incluirRoubo").fadeOut(); 
                $(".menuDinheiro").css("background","#eee");
                $(".incluirDinheiro").fadeToggle();
            }
        }
        if(corMenuDinheiro ==='rgb(238, 238, 238)')
        {
            if($(".multiDadosRoubo").html()!=='dados roubo')
            {
                $(".menuDinheiro").css("background","white");   
                $(".incluirDinheiro").fadeOut(); 
                $(".menuRoubo").css("background","#eee");
                $(".incluirRoubo").fadeToggle();
            }
        }
    }
}


function multiNumeroRegistroFoco()
{
    $(".multiNumeroRegistro").focus();
   $('html, body').animate({ scrollTop: 0 }, 'fast');
}
function multiSeguroFoco()
{
    $(".incendio").focus();
}

function seguros()
{
    var Dinh = $("input:checkbox[name='multi:dinheiroComb']").is(':checked');
    var roub = $("input:checkbox[name='multi:rouboComb']").is(':checked');
    var Inc = $("input:checkbox[name='multi:incendioComb']").is(':checked');
    
    if(Inc === false && roub === false && Dinh === true)
        $(".multiModal").fadeIn();
    if(Inc === false && roub === true && Dinh === false)
        $(".multiModal").fadeIn();
    if(Inc === true && roub === false && Dinh === false)
        $(".multiModal").fadeIn();
}

function riscos()
{
    $("input:checkbox[name='multi:dinheiroComb']").attr('checked', false);
    $("input:checkbox[name='multi:rouboComb']").attr('checked', false);
    $("input:checkbox[name='multi:incendioComb']").attr('checked', false);
}

function enviar()
{
    var Dinh = $("input:checkbox[name='multi:dinheiroComb']").is(':checked');
    var roub = $("input:checkbox[name='multi:rouboComb']").is(':checked');
    var Inc = $("input:checkbox[name='multi:incendioComb']").is(':checked');
  
            
    if(Inc === true && roub === true && Dinh === true)
           segurosAdicionar([{name:'estado',value:'todos'}]);
     else if(Inc === true && roub === true && Dinh === false)
           segurosAdicionar([{name:'estado',value:'incêndio e roubo'}]);
     else if(Inc === true && roub === false && Dinh === true)
            segurosAdicionar([{name:'estado',value:'incêndio e dinheiro'}]);
     else if(Inc === false && roub === true && Dinh === true)
        segurosAdicionar([{name:'estado',value:'roubo e dinheiro'}]);
    else
         segurosAdicionar([{name:'estado',value:'inválido'}]);
}

function apoliceMultiRisco()
{
     $('html, body').animate({ scrollTop: 0 }, 'fast');
    $(".mulitRiscoApolice").css("border","1px solid red");
}

