<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Battleship</title>
    <link href="../../resources/style.css" rel="stylesheet">
</head>
<body onload = create();shoot()>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>
    function create() {
        $(document).ready(function () {
            $('#but').click(function () {
                $.get("/game/play", function (data) {
                    $.each(data, function (key, val) {
                        var a = JSON.stringify(val);
                        var c = JSON.parse(a);
                        var coord = c["coord"];
                        var action = c["action"];

                        paint(coord,action);

                    });
                    $('#but').classList.add('hidden');
                });
            });
        });

    }

    function shoot(){
        $(document).ready(function () {
            $("#shoot").click(function () {
                $.ajax({
                    method: "GET",
                    url: "/game/shoot",
                    dataType: 'json',
                    data: { turn: $('#shoottext').val()},
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
                            alert("Computer's turn!");

                            compshoot();

                        } else if(res == "player"){
                            alert("You won!");
                        } else if(res == "comp"){
                            alert("You lose!");
                        }
                    }
                });
            });
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
//                            alert(res);
            });
            if(res == "change"){
                setTimeout('alert("Players turn!")',500);
            } else if(res == "player"){
                alert("You won!");
            } else if(res == "comp"){
                alert("You lose!");
            } else{
                setTimeout('compshoot()',1000);
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
            cell.classList.add('has-ship');
        } else {
            cell.classList.add('empty');
        }

    }

</script>
    <h1 align="center">Battleship</h1>
    <div class = "container">
        <button id = "but">Play</button>
        <div class = "field my-field">
            <table class = "field-table" id = "playertable">
                <caption>Your field</caption>
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
        <div class = "shoot-form">
            <form action="" method="GET">
                <input id = "shoottext" type = "text" placeholder="Enter your turn!">
                <input id = "shoot" type = "button" value = "Shoot">
            </form>
        </div>
    </div>

</body>
</html>