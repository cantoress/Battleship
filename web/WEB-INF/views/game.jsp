<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Battleship</title>
    <link href="../../resources/style.css" rel="stylesheet">
</head>
<body onload = create();shoot();checkshow()>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>

    var gamenum;
    var status;

    function checkshow(){
        $(document).ready(function () {
            $('#needshow').click(function () {
                if(document.getElementById('needshow').checked){
                    document.querySelectorAll('.enemy-field')[0].classList.add('show');
                    var enemy = document.querySelectorAll('.enemy-field td.has-ship');
                    var k=0;
                    while(k<20) {
                        enemy[k].classList.remove('empty');
                        k++;
                    }

                } else{
                    document.querySelectorAll('.enemy-field')[0].classList.remove('show');
                    var enemy = document.querySelectorAll('.enemy-field td.has-ship');;
                    var k=0;
                    while(k<20) {
                        enemy[k].classList.add('empty');
                        k++;
                    }

                }
            });
        });
    }

    function create() {
        $(document).ready(function () {
            $('#but').click(function () {
                if(status=="newgame"){
                    increaseGame();
                    status = "game";
                } else if (status = "game"){
                    deleteGame(gamenum);
//                    increaseGame();
                    status = "game";
                }
                if(document.getElementById('needshow').checked){
                    document.querySelectorAll('.enemy-field')[0].classList.add('show');
                } else{
                    document.querySelectorAll('.enemy-field')[0].classList.remove('show');
                }
                $.get("/game/play", function (data) {
                    $.each(data, function (key, val) {
                        var a = JSON.stringify(val);
                        var c = JSON.parse(a);
                        var coord = c["coord"];
                        var action = c["action"];

                        paint(coord,action);

                    });
                    $("#game-info").empty();
                    $("<div>Game started!</div>").appendTo("#game-info");
                });
            });
        });

        $.get("/gamenum", function (data) {
            $("#gamen").empty();
            gamenum = data;
            $("<div id = 'gamen'>Game " + gamenum + "</div>").appendTo("#header");
        });

    }

    function deleteGame(thisgame){
        $.ajax({
            method: "GET",
            url: "/game/del",
            dataType: 'json',
            data: {game: thisgame}
        });
    }

    function shoot(){

        $(document).ready(function () {
            $("#shoot").click(function () {
                makeshoot($('#shoottext').val());
            });

            $('[id^="td"]').click(function () {
                var name = this.id;
                name = name.substr(3);
                makeshoot(name);
//                alert(this.id);
            });

        });

    }

    function makeshoot(getturn){

        $.ajax({
            method: "GET",
            url: "/game/shoot",
            dataType: 'json',
            data: { turn: getturn},
            success: function(data) {
                var res;
                $.each(data, function (key, val) {
                    var a = JSON.stringify(val);
                    var c = JSON.parse(a);
                    var coord = c["coord"];
                    var action = c["action"];
                    var stat = c["stat"];

                    paint(coord,action);

                    if(stat == 0){
                        res = "change";
                    } else if(stat == 2){
                        res = "player";
                    } else if(stat ==-2){
                        res = "comp";
                    }
//                            alert(res);
                });
                if(res == "change"){
                    $("#game-info").empty();
                    $("<div>Computer's turn!</div>").appendTo("#game-info");
                    setTimeout(compshoot(), 500);
                } else if(res == "player"){
                    $("#game-info").empty();
                    $("<div>You won!</div>").appendTo("#game-info");
                    status = "newgame";
                } else if(res == "comp"){
                    $("#game-info").empty();
                    $("<div>You lose!</div>").appendTo("#game-info");
                    status = "newgame";
                }
            }
        });

    }

    function compshoot(){
        $.get("/game/compshoot", function (data) {
            var res;
            $.each(data, function (key, val) {
                var a = JSON.stringify(val);
                var c = JSON.parse(a);
                var coord = c["coord"];
                var action = c["action"];
                var stat = c["stat"];

                paint(coord,action);

                if(stat == 0){
                    res = "change";
                } else if(stat == 2){
                    res = "player";
                } else if(stat ==-2){
                    res = "comp";
                }
            });
            if(res == "change"){
                setTimeout($("#game-info").empty(),1000);
                setTimeout('$("<div>Your turn!</div>").appendTo("#game-info")',500);
            } else if(res == "player"){
                $("#game-info").empty();
                $("<div>You won!</div>").appendTo("#game-info");
                status = "newgame";
            } else if(res == "comp"){
                $("#game-info").empty();
                $("<div>You lose!</div>").appendTo("#game-info");
                status = "newgame";
            } else{
                setTimeout('compshoot()',1000);
            }
        });
    }

    function increaseGame(){
        $.ajax({
            method: "GET",
            url: "/gamenum",
//            dataType: 'json',
//            data: {user: winner},
            success: function (data) {
                $("#gamen").empty();
                gamenum = data;
                $("<div id = 'gamen'>Game " + gamenum + "</div>").appendTo("#header");
            }
        });
    }

    function paint(c,act){

        var x;
        var y;
        var f;
        var st;
        var cell;
        var q;

        if (c < 100) {
            x = Math.floor(c / 10);
            y = c % 10;
            x = x + 1;
            y = y + 1;
            f = document.querySelectorAll('.my-field');
            st = f[0].getElementsByTagName('tr');
            q = st[y].getElementsByTagName('td');
            cell = q[x];
        } else {
            x = Math.floor(c / 10) % 10;
            y = c % 10;
            x = x + 1;
            y = y + 1;
            f = document.querySelectorAll('.enemy-field');
            st = f[0].getElementsByTagName('tr');
            q = st[y].getElementsByTagName('td');
            cell = q[x];
        }
        $(cell).removeClass();
        if (act == 2) {
            cell.classList.add('checked');
        } else if (act == 1) {
            cell.classList.add('checked');
            cell.classList.add('has-ship');
        } else if (act == 3) {
            if (c < 100) {
                cell.classList.add('has-ship');
            } else {
                if(f[0].classList.contains('show')) {
                    cell.classList.add('has-ship');
                } else{
                    cell.classList.add('has-ship');
                    cell.classList.add('empty');
                }
            }
        } else {
            cell.classList.add('empty');
        }

    }

</script>
    <h1 align="center" id = "header">Battleship</h1>
    <a href = "/info" align = "left">To log</a>
    <div class = "container">
        <button id = "but">Play</button>
        <form>
            <input type = "checkbox" id = "needshow"> Show enemy's field?
        </form>
        <div class = "field my-field">
            <table class = "field-table" id = "playertable">
                <caption>Your field</caption>
                <tr>
                    <td style = "background-color: white"></td>
                    <td>a</td>
                    <td>b</td>
                    <td>c</td>
                    <td>d</td>
                    <td>e</td>
                    <td>f</td>
                    <td>g</td>
                    <td>h</td>
                    <td>i</td>
                    <td>j</td>
                </tr>
                <tr>
                    <td>1</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>2</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>3</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>4</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>5</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>6</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>7</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>8</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>9</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>10</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>

            </table>
        </div>
        <div class = "field enemy-field">
            <table class = "field-table">
                <caption>Enemy's field</caption>
                <tr>
                    <td></td>
                    <td>a</td>
                    <td>b</td>
                    <td>c</td>
                    <td>d</td>
                    <td>e</td>
                    <td>f</td>
                    <td>g</td>
                    <td>h</td>
                    <td>i</td>
                    <td>j</td>
                </tr>
                <tr>
                    <td>1</td>
                    <td id = "td_a1"></td>
                    <td id = "td_b1"></td>
                    <td id = "td_c1"></td>
                    <td id = "td_d1"></td>
                    <td id = "td_e1"></td>
                    <td id = "td_f1"></td>
                    <td id = "td_g1"></td>
                    <td id = "td_h1"></td>
                    <td id = "td_i1"></td>
                    <td id = "td_j1"></td>
                </tr>
                <tr>
                    <td>2</td>
                    <td id = "td_a2"></td>
                    <td id = "td_b2"></td>
                    <td id = "td_c2"></td>
                    <td id = "td_d2"></td>
                    <td id = "td_e2"></td>
                    <td id = "td_f2"></td>
                    <td id = "td_g2"></td>
                    <td id = "td_h2"></td>
                    <td id = "td_i2"></td>
                    <td id = "td_j2"></td>
                </tr>
                <tr>
                    <td>3</td>
                    <td id = "td_a3"></td>
                    <td id = "td_b3"></td>
                    <td id = "td_c3"></td>
                    <td id = "td_d3"></td>
                    <td id = "td_e3"></td>
                    <td id = "td_f3"></td>
                    <td id = "td_g3"></td>
                    <td id = "td_h3"></td>
                    <td id = "td_i3"></td>
                    <td id = "td_j3"></td>
                </tr>
                <tr>
                    <td>4</td>
                    <td id = "td_a4"></td>
                    <td id = "td_b4"></td>
                    <td id = "td_c4"></td>
                    <td id = "td_d4"></td>
                    <td id = "td_e4"></td>
                    <td id = "td_f4"></td>
                    <td id = "td_g4"></td>
                    <td id = "td_h4"></td>
                    <td id = "td_i4"></td>
                    <td id = "td_j4"></td>
                </tr>
                <tr>
                    <td>5</td>
                    <td id = "td_a5"></td>
                    <td id = "td_b5"></td>
                    <td id = "td_c5"></td>
                    <td id = "td_d5"></td>
                    <td id = "td_e5"></td>
                    <td id = "td_f5"></td>
                    <td id = "td_g5"></td>
                    <td id = "td_h5"></td>
                    <td id = "td_i5"></td>
                    <td id = "td_j5"></td>
                </tr>
                <tr>
                    <td>6</td>
                    <td id = "td_a6"></td>
                    <td id = "td_b6"></td>
                    <td id = "td_c6"></td>
                    <td id = "td_d6"></td>
                    <td id = "td_e6"></td>
                    <td id = "td_f6"></td>
                    <td id = "td_g6"></td>
                    <td id = "td_h6"></td>
                    <td id = "td_i6"></td>
                    <td id = "td_j6"></td>
                </tr>
                <tr>
                    <td>7</td>
                    <td id = "td_a7"></td>
                    <td id = "td_b7"></td>
                    <td id = "td_c7"></td>
                    <td id = "td_d7"></td>
                    <td id = "td_e7"></td>
                    <td id = "td_f7"></td>
                    <td id = "td_g7"></td>
                    <td id = "td_h7"></td>
                    <td id = "td_i7"></td>
                    <td id = "td_j7"></td>
                </tr>
                <tr>
                    <td>8</td>
                    <td id = "td_a8"></td>
                    <td id = "td_b8"></td>
                    <td id = "td_c8"></td>
                    <td id = "td_d8"></td>
                    <td id = "td_e8"></td>
                    <td id = "td_f8"></td>
                    <td id = "td_g8"></td>
                    <td id = "td_h8"></td>
                    <td id = "td_i8"></td>
                    <td id = "td_j8"></td>
                </tr>
                <tr>
                    <td>9</td>
                    <td id = "td_a9"></td>
                    <td id = "td_b9"></td>
                    <td id = "td_c9"></td>
                    <td id = "td_d9"></td>
                    <td id = "td_e9"></td>
                    <td id = "td_f9"></td>
                    <td id = "td_g9"></td>
                    <td id = "td_h9"></td>
                    <td id = "td_i9"></td>
                    <td id = "td_j9"></td>
                </tr>
                <tr>
                    <td>10</td>
                    <td id = "td_a10"></td>
                    <td id = "td_b10"></td>
                    <td id = "td_c10"></td>
                    <td id = "td_d10"></td>
                    <td id = "td_e10"></td>
                    <td id = "td_f10"></td>
                    <td id = "td_g10"></td>
                    <td id = "td_h10"></td>
                    <td id = "td_i10"></td>
                    <td id = "td_j10"></td>
                </tr>
            </table>
        </div>
        <div id = "game-info">

        </div>
        <div class = "shoot-form">
            <form action="" method="GET">
                <input id = "shoottext" type = "text" placeholder="Enter your turn!">
                <input id = "shoot" type = "button" value = "Shoot">
            </form>
        </div>
    </div>
<script>
//    function start() {
//        $(document).ready(function () {
//            $.get("/game", function (data) {
//                gamenum = data.get();
//                $("<div>Game number " + gamenum + "</div>").appendTo("#header");
//            });
//        });
//    }
</script>
</body>
</html>