$(".card").on("click", function() {
    $(".detail").addClass("activate")
})

$(".close-detail").on("click", function() {
    $(".detail").removeClass("activate")
})

$(".menu-bar").on("click", function() {
    $(".sidebar").addClass("activate")
})

$(".logo").on("click", function() {
    $(".sidebar").removeClass("activate")
})