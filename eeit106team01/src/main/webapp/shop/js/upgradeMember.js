$(".vipmember").mouseover(function(){
    $(".vipmember").css("transform-origin","0")
    $(".vipmember").css("transform","scale(1.2,1.2)")
})

$(".vipmember").mouseout(function(){
    $(".vipmember").css("transform-origin","0")
    $(".vipmember").css("transform","scale(1,1)")
})