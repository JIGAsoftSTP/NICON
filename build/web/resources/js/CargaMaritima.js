/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function(e)
{
//        $("#menuCarg1clik2").css("background","#ffffff");
//        $(".AvancarCarg").click(function(e){
//                    if(avancarCarga())
//                    {
//                        $("#menuCarg1").add("#menuCarg2").toggle('fade');
//                        $("#menuCarg1clik2").css("background","#ededef");
//                        $("#menuCarg1clik1").css("background","#ffffff");
//                        $(".RecuarCarg").toggle('fade');
//                        $(".ContinuarCarg").toggle('fade');
//                        $(".AvancarCarg").toggle('fade');
//                        $(".btBack").toggle('fade');
//                        $(".cargaNR").focus();
//                    }
//                    return false;
//                });
//                
//        $(".RecuarCarg").click(function(e){
//                    $("#menuCarg1").add("#menuCarg2").toggle('fade');
//                    $("#menuCarg1clik1").css("background","#ededef");
//                    $("#menuCarg1clik2").css("background","#ffffff");
//                    $(".AvancarCarg").toggle('fade');
//                    $(".ContinuarCarg").toggle('fade');
//                    $(".RecuarCarg").toggle('fade');
//                    $(".btBack").toggle('fade');
//                    $(".cargaNR").focus();
//                    return false;
//                });
                
               
    $(".cargaNumero").keyup(function(e)
    {
        e.preventDefault();
        if(!$.isNumeric($(this).val()))
            $(this).val("");
   });
   $(".cargaNumeroInteiro").keyup(function(e)
    {
         e.preventDefault();
                    var expre = /[^0-9]/g;
                    // REMOVE OS CARACTERES DA EXPRESSAO ACIMA
                    if ($(this).val().match(expre))
                            $(this).val($(this).val().replace(expre, ''));
   });
   $("input:text").click(function(e)
   {
      $(this).css("border","");
   });
   $("textArea").click(function(e)
   {
      $(this).css("border","");
   });
   $("select").click(function(e)
   {
      $(this).css("border","");
   });
   
   $(".cargaCP").blur(function(e)
    {
        e.preventDefault();
        var test=$(".cargaCP").val();
        //alert(test);
        if(test<10||test>20)
        {
            $(".cargaCP").val('');
           /// $(".cargaCP").focus();
        }    
        
   });     
    $(".cargaTLI").keyup(function(e)
    {
        e.preventDefault();
        var test=$(".cargaTLI").val();
        //alert(test);
        if(test<0||test>100)
        {
            $(".cargaTLI").val('');
        }
   });
   
    $(".cargaMTransportadaq5").click(function(e)
        {
           $(".cargaMTransportadaq1").attr("checked",false); 
           $(".cargaMTransportadaq2").attr("checked",false); 
           $(".cargaMTransportadaq3").attr("checked",false); 
           $(".cargaMTransportadaq4").attr("checked",false); 
           $(".cargaMTransportadaq5").attr("checked",true); 
        }
    );
    $(".cargaMTransportada").click(function(e)
    {
        //e.preventDefault();
        var test1=$(".cargaMTransportadaq1").is(":checked"); 
        var test2=$(".cargaMTransportadaq2").is(":checked"); 
        var test3=$(".cargaMTransportadaq3").is(":checked"); 
        var test4=$(".cargaMTransportadaq4").is(":checked"); 
        
        if(!test1&&!test2&&!test3&&!test4)  
        {
            $(".cargaMTransportadaq5").attr("checked",true);
        }
        else{
            $(".cargaMTransportadaq5").attr("checked",false);
        }
        
   });

});

function addModoInterreceCarga(xhr, status, args)
{
    var limpar= args.limparModoIterresse;
    var ic =$(".cargaIC").val();
    var ME =$(".cargaME").val();
    if(ic==="")
    {
        $(".cargaIC").css("border","1px solid red");
        $(".cargaIC").focus();
    }
    else
    {
       $(".cargaIC").css("border","");
    }
    
    if(ME==="")
    {
        $(".cargaME").css("border","1px solid red");
        $(".cargaME").focus();
    }
    else
    {
       $(".cargaME").css("border","");
    }
    
    if(limpar==='true')
    {
        $(".cargaME").val("");
        $(".cargaIC").val("");
    }
}

function avancarCarga()
{
    var apolice = $(".cmApolice").val();
    var apoliceEstado = $(".cmApolice").is(":disabled");
    var NR =$(".cargaNR").val();
    var PS =$(".cargaPS").val();
    var PDC =$(".cargaPDC").val();
    var PDD =$(".cargaPDD").val();
    var NNV =$(".cargaNNV").val();
    var PO =$(".cargaPO").val();
    var PD =$(".cargaPD").val();
    var P =$(".cargaP").val();
    
    var VLI =$(".cargaVLI").val();
    var TLI =$(".cargaTLI").val();
    
    var TN =$(".cargaTN").val();
    var CP =$(".cargaCP").val();
    
    var tt=true;
    if(apoliceEstado === false)
    {
         if(apolice ==="")
        {
            $(".cmApolice").css("border","1px solid red");
            $(".cmApolice").focus();
            tt = false;
        }
        else
            $(".cmApolice").css("border","");
    }
   
    if(NR==="")
    {
        $(".cargaNR").css("border","1px solid red");
        $(".cargaNR").focus();
        tt=false;
    }
    else
    {
        $(".cargaNR").css("border","");
    }
    
    if(PS==="")
    {
        $(".cargaPS").css("border","1px solid red");
        $(".cargaPS").focus();
        tt=false;
    }
    else
    {
       $(".cargaPS").css("border","");
    }
    
    if(PDC==="")
    {
        $(".cargaPDC").css("border","1px solid red");
        $(".cargaPDC").focus();
        tt=false;
    }
    else
    {
       $(".cargaPDC").css("border","");
    }
    
    if(PDD==="")
    {
        $(".cargaPDD").css("border","1px solid red");
        $(".cargaPDD").focus();
        tt=false;
    }
    else
    {
       $(".cargaPDD").css("border","");
    }
    
    if(NNV==="")
    {
        $(".cargaNNV").css("border","1px solid red");
        $(".cargaNNV").focus();
        tt=false;
    }
    else
    {
       $(".cargaNNV").css("border","");
    }
    
    if(PO==="(Pais Origem)")
    {
        $(".cargaPO").css("border","1px solid red");
        $(".cargaPO").focus();
        tt=false;
    }
    else
    {
       $(".cargaPO").css("border","");
    }
    
    if(PD==="(Pais Destino)")
    {
        $(".cargaPD").css("border","1px solid red");
        $(".cargaPD").focus();
        tt=false;
    }
    else
    {
       $(".cargaPD").css("border","");
    }
    
    if(P==="")
    {
        $(".cargaP").css("border","1px solid red");
        $(".cargaP").focus();
        tt=false;
    }
    else
    {
       $(".cargaP").css("border","");
    }
    
    if(TLI==="")
    {
        $(".cargaTLI").css("border","1px solid red");
        $(".cargaTLI").focus();
        tt=false;
    }
    else
    {
       $(".cargaTLI").css("border","");
    }
    
    if(VLI==="")
    {
        $(".cargaVLI").css("border","1px solid red");
        $(".cargaVLI").focus();
        tt=false;
    }
    else
    {
       $(".cargaVLI").css("border","");
    }
    
    if(CP==="")
    {
        $(".cargaCP").css("border","1px solid red");
        $(".cargaCP").focus();
        tt=false;
    }
    else
    {
       $(".cargaCP").css("border","");
    }
    
    
    if(TN==="")
    {
        $(".cargaTN").css("border","1px solid red");
        $(".cargaTN").focus();
        tt=false;
    }
    else
    {
       $(".cargaTN").css("border","");
    }
    
    $(".cargaMoeda").change();
    //testa se a tabela tem valores
    var testeTable= $(".validarTableCarga").val();
//    alert(testeTable);

    if(testeTable === "false")
    {
        $(".mesagemCarga").click();
    }
    return tt=((testeTable==="false")?false:tt);
}

function concluirCarga()
{
    var valido= $('.cargaValidoForme').val();
    var NR =$(".cargaNR").val();
    var DM =$(".cargaDM").val();
    var AAC =$(".cargaAAC").val();
     
    
    var tt=true;
    
    if(NR==="")
    {
        $(".cargaNR").css("border","red");
        $(".cargaNR").css("border-style","solid");
        $(".cargaNR").css("border-width","1px");
        $(".cargaNR").focus();
        tt=false;
    }
    else
    {
        $(".cargaNR").css("border","");
        $(".cargaNR").css("border-style","none");
        $(".cargaNR").css("border-width","0px");
        $(".cargaNR").css("border","");
    }
    
    if(DM==="")
    {
        $(".cargaDM").css("border","1px solid red");
        $(".cargaDM").focus();
        tt=false;
    }
    else
    {
       $(".cargaDM").css("border","");
    }
    
    if(AAC==="")
    {
        $(".cargaAAC").css("border","1px solid red");
        $(".cargaAAC").focus();
        tt=false;
    }
    else
    {
       $(".cargaAAC").css("border","");
    }
    
    if(valido==="false")
        tt=false;
    
    if(tt)
    {
        window.location.assign("GestSeg_NovoSeguroApolice.xhtml");
    }
}

function addTablaVeiculos(xhr, status, args)
{
    var limpar= args.limparDetalheV;
    var DVCA  =$(".cargaDVCA").val();
    var DVMCC =$(".cargaDVMCC").val();
    var DNR   =$(".cargaDNR").val();
    var DVCR  =$(".cargaDVCR").val();
    var DVMV  =$(".cargaDVMV").val();
    var DDM   =$("input:text[name='cargaForm:cargaDDM_input']").val();
//    var DDM   =$(".cargaDDM").val();
    
    if(DVCA==="")
    {
        $(".cargaDVCA").css("border","1px solid red");
        $(".cargaDVCA").focus();
    }
    else
    {
       $(".cargaDVCA").css("border","");
    }
    
    if(DVMCC==="")
    {
        $(".cargaDVMCC").css("border","1px solid red");
        $(".cargaDVMCC").focus();
    }
    else
    {
       $(".cargaDVMCC").css("border","");
    }
    
    if(DNR==="")
    {
        $(".cargaDNR").css("border","1px solid red");
        $(".cargaDNR").focus();
    }
    else
    {
       $(".cargaDNR").css("border","");
    }
    
    if(DVCR==="")
    {
        $(".cargaDVCR").css("border","1px solid red");
        $(".cargaDVCR").focus();
    }
    else
    {
       $(".cargaDVCR").css("border","");
    }
    
    if(DVMV==="")
    {
        $(".cargaDVMV").css("border","1px solid red");
        $(".cargaDVMV").focus();
    }
    else
    {
       $(".cargaDVMV").css("border","");
    }
    
    if(DDM==="")
    {
        $("input:text[name='cargaForm:cargaDDM_input']").css("border","1px solid red");
        $("input:text[name='cargaForm:cargaDDM_input']").focus();
    }
    else
    {
       $(".cargaDDM").css("border","");
    }
    
    if(limpar==='true')
    {
        $(".cargaDVCA").val("");
        $(".cargaDVMCC").val("");
        $(".cargaDNR").val("");
        $(".cargaDCR").val("");
        $(".cargaDVCR").val("");
        $(".cargaDVMV").val("");
//        $(".cargaDDM").val("");
        $("input:text[name='cargaForm:cargaDDM_input']").val("");
        $(".cargaDUPDC").attr("checked",false);
        $(".cargaDPT").attr("checked",false);
//        alert($("cargaCDN").is(":selected"));
            
         $("input:radio[name='cargaForm:cargaCDN']").each(function() 
         {
            //Verifica qual está selecionado
            if ($(this).val()==="N")
            {
                $(this).attr('checked',true);
            }
         });
    }

}

function cargaApoliceFoco()
{
    $(".cmApolice").css("border","1px solid red");
}
function carcular()
    {
        var test=$(".cargaTLI").val();
        
        if(test==='')
        {
            $(".cargaTLI").val('');
            $('.cargaP').val('');
        }  
        
        var test1=$(".cargaVLI").val();
        if(test1==='')
        {
            $(".cargaVLI").val('');
            $('.cargaP').val('');
        } 
    }

function mostarInfoCarga()
{$(".mp-infoTableCarga").fadeIn();}

function valideJaefectuou()
{
    if($("input:radio[name='cargaForm:cargaBoo']:checked").attr("value") === "false")
    { $(".cargaESEC").val("");}
}