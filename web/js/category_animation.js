$(document).ready(function(){
    $('a.categoryButton').hover(
        function () {$(this).animate({backgroundColor: '#b2d2d2'})},
        function () {$(this).animate({backgroundColor: '#d3ede8'})}
    );

    $('div.categoryBox').hover(over, out);

    function over() {
        var span = this.getElementsByTagName('span');
        $(span[0]).animate({opacity: 0.3});
        $(span[1]).animate({color: 'white'});

    }

    function out() {
        var span = this.getElementsByTagName('span');
        $(span[0]).animate({opacity: 0.7});
        $(span[1]).animate({color: '#444'});
    }
});
