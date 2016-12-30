/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function (e) {
    $('.steps > section').click(function () {
        var index = $(this).index();
        if (StepOneReady()) {
            $('.content').eq(index).addClass('active-content').siblings().removeClass('active-content');
            $('.steps section').eq(1).toggleClass('active-step');
        }
    });
});
function StepOneReady() {
    
    //All validation code to go to STEP 2 here!
    return avancarCarga();
}


