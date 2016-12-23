<html>
<head>
    <title>Battleship</title>
    <link href="../../resources/style.css" rel="stylesheet">

</head>
<body onload = log()>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script>
        function log() {
            $(document).ready(function () {
                $('#getLog').click(function () {
//                    alert("I'm pushed!");
                    $.ajax({
                        method: "GET",
                        url: "/info/log",
                        dataType: 'json',
                        data: {game: $('#gametext').val()},
                        success: function (data) {
                            $('#tbody').empty();
                            $.each(data, function (key, val) {
                                var a = JSON.stringify(val);
                                var c = JSON.parse(a);
                                var game = c["gameNum"];
                                var player = c["player"];
                                var shoot = c["shoot"];
                                var shootres = c["shootResult"];
                                var string = $('<tr><td>' + game+'</td><td>' + player+'</td><td>' + shoot+'</td><td>' + shootres+'</td></tr>');
                                string.appendTo('#tbody');
                            });
                        }
                    });
                });
            });
        }


    </script>

    <h1 align="center" id = "header">Battleship</h1>
    <a href = "/game" align = "left">To game</a>
    <div class = "container">
        Enter code: <input type = "text" id = "gametext">
        <button id = "getLog">Get</button>
        <table class = "log-table">
            <thead>
            <tr>
                <th>Game</th>
                <th>User</th>
                <th>Shoot</th>
                <th>Shoot result</th>
            </tr>
            </thead>
            <tbody id = "tbody">
            </tbody>
        </table>
    </div>

</body>
</html>
