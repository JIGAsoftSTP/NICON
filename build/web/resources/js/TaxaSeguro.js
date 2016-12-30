/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(e)
        { 
              $(".soValorTaxaSeguro").keyup(function (e)
              {
                  e.preventDefault();
                  if(!$.isNumeric($(this).val()))
                      $(this).val("");
                  if($(this).val()<0)
                      $(this).val("");
                   
              });
              $(".taxaValor").keyup(function (e)
              {
                  e.preventDefault();
                  var taxa= $(this).val();
                  if(taxa>100||taxa<0)
                      $(this).val("");
              });
             $(".closeItTaxa").click(function (e)
              {
                  limarTaxaValor();
              });
              $(".closeItSeguro").click(function (e)
              {
                  limarSeguroValor();
              });
        });
        function limarTaxaValor()
        {
            $('.regTaxaLimpar').val("");
        }
              
        function limarSeguroValor()
        {
            $('.regValorLimpar').val("");
        }
