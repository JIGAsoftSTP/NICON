$(document).ready(function(e){
   
   $(".sair").click(function (e)
   {
      
       window.location.href = '../index.xhtml';
   });
   $(".linkTerminarSessao").click(function (e)
   {
      e.preventDefault();
      $(".terminarSessao").fadeIn();
   });

});