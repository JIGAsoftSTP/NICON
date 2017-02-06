var tipoCliente;
$(document).ready(function(e)
{
    $(".editClient").click(function (e)
    {
       e.preventDefault();
       $(".tipoClienteS").html("");
        editar();
    });
    $(".clienteS").click(function (e)
    {
       e.preventDefault();
       $(".tipoClienteS").html("");
       if($(this).html()!=="(Nenhum cliente selecionado)")
       {
            dados();
            $('.formAddClient input').addClass('onlyInfoSelected');
            $('.formAddClient select').addClass('onlyInfoSelected');
            show();
       }
           
    });

 
});


function ClienteSelecionado(xhr, status, args)
{
    var nif = args.nifCliente;
    var telefoneS = args.telefoneCliente;
    var telemovels = args.telemovelCliente;
    var nome = args.nomeClienteSelecionado;
     tipoCliente = args.tipoClienteS;
    $(".clienteS").html(nome);
     if(tipoCliente !=="")
     {
        if(tipoCliente==="Pessoa")
        {
            $(".NifSingular").val(nif);
            if(telefoneS ==="NENHUM")
                 $(".telefoneS").val("");
            else  
              $(".telefoneS").val(telefoneS);
            if(telemovels ==="NENHUM")
               $(".telemovelS").val("");
            else
                $(".telemovelS").val(telemovels);
        }
        else
        {
            $(".nifColetivo").val(nif);
            if(telefoneS ==="NENHUM")
                 $(".telefoneColetivo").val("");
            else  
              $(".telefoneColetivo").val(telefoneS);
            if(telemovels ==="NENHUM")
               $(".telemovelColetivo").val("");
            else
                $(".telemovelColetivo").val(telemovels);
        }
    }
    
}

function editar()
{
    $('.buttons').css("display","");
    $('.clienteLimpar').css('display','none');
    $("input:radio[name='addClienteformulario:tipoCli']").attr('disabled',true);
    if(tipoCliente ==="Pessoa")
    {              
         //informações do cliente singular que não podem ser alteradas
        $(".clienteDoc").attr('disabled', true); 
        $(".clienteNumero").attr('disabled', true);
        $(".NomeSingular").attr('disabled', true);
        $(".ApelidoCliente").attr('disabled', true);
        $("input:text[name='addClienteformulario:clienteDataNasc_input']").attr('disabled', true);

         //informações do cliente singular que podem ser alteradas
        $(".editable").attr('disabled', false); 
        $('.editable').removeClass('onlyInfoSelected');
        $('.editable').removeClass('onlyInfoSelected');
    }
    else
    {    
        //informações do cliente coletivo que não podem ser alteradas
       $(".nomeColetivo").attr('disabled', true); 
       $(".nifColetivo").attr('disabled', true); 
       $(".paisCliente").attr('disabled', true); 

        //informações do cliente coletivo que podem ser alteradas
       $(".editable").attr('disabled', false); 
        $('.editable').removeClass('onlyInfoSelected');
        $('.editable').removeClass('onlyInfoSelected');
    }
   
}

function clienteAtualizado()
{
      $(".mp-addClient").fadeOut(); 
}


function dados()
{
    $('.formAddClient input').addClass('onlyInfoSelected');
     $('.formAddClient select').addClass('onlyInfoSelected');
     $(".tipoClienteS").html("");
     $("label").each(function ()
     {
         if ($(this).attr('for')==="addClienteformulario:tipoCli:0")
             $(this).show();                                                 
     });
     $("label").each(function ()
     {
         if ($(this).attr('for')==="addClienteformulario:tipoCli:1")
             $(this).show();                                                  
     });
     if(tipoCliente !=="")
     {
         $("input:radio[name='addClienteformulario:tipoCli']").attr('disabled',true);
         if(tipoCliente ==="Empresa")
         {
              $("input:radio[name='addClienteformulario:tipoCli']").each(function ()
              {
                 if($(this).val()==="Coletivo")
                 {
                      $(this).attr('checked', true);

                 }
                 $("label").each(function ()
                 {

                     if ($(this).attr('for')==="addClienteformulario:tipoCli:0")
                         $(this).hide();                                                  
                 });
              });
             $(".singular").hide($(".singular").fadeOut(00));
             $(".coletivo").show("slide");        
         }
         else
         {
             $("input:radio[name='addClienteformulario:tipoCli']").each(function ()
             {
                 if($(this).val()==="Singular")
                 {
                      $(this).attr('checked', true);
                 }
                 $("label").each(function ()
                 {
                     if ($(this).attr('for')==="addClienteformulario:tipoCli:1")
                         $(this).hide();
                 });
             });
             $(".coletivo").hide($(".coletivo").fadeOut(00));
             $(".singular").show("slide");  
         }
        $(":text",$("#addClienteformulario")).each(function ()
         {
               $(this).attr('disabled', true);              
               $(".clienteInfo").attr('disabled', true); 
               $('.buttons').css("display","none");
               $("input:text[name='addClienteformulario:clienteDataNasc_input']").attr('disabled', true);
        });
    }
 }
 
 function show()
 {
       $(".mp-addClient").fadeIn(400);
 }