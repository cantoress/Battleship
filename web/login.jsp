<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Battleship</title>
</head>
<body>
<h1 class="h1" align="center">Battleship</h1>
<div class="container">
    <form method="POST" action="<c:url value="/j_spring_security_check" />">
        <table>
            <tr>
                <td>Login </td>
                <td><input type='text' name='j_username' value=''></td>
            </tr>
            <tr>
                <td>Password </td>
            <td><input type='password' name='j_password'/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Login" /></td>
                <td><input type="reset" value="Reset" /></td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>
