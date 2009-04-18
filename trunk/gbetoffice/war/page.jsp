<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <body>
        <h1>Time: <%= new java.util.Date().toString() %> </h1>
        <h1>Round viewer</h1>
        <p>
            <form name="roundQuery" id="roundQuery" action="./gbetoffice" method="post">
                <div>Year:<input name="year" type="text" size="5" maxlength="5"/>
                </div>
                <div>Round:<input name="round" type="text" size="5" maxlength="5"/>
                </div>
                <div>
                    <input type="submit" value="Get data" />
                </div>
            </form>
        </p>
    </body>
</html>
