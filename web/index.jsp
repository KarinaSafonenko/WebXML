<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>First</title>
</head>
<body>
<form>
    Выберите язык для отображения результатов парсинга и парсер:
</form>
<form action="/ControllerServlet" method="POST">
    <input type="hidden" name="command" value="PARSER">
    <select name="locale">
        <option value="en_EN">английский</option>
        <option value="ru_RU">русский</option>
    </select>
    <select name="parser">
        <option value="DOM">DOM</option>
        <option value="SAX">SAX</option>
        <option value="STAX">STAX</option>
        <option value="JAXB">JAXB</option>
    </select>
    <input type="submit" value="Показать результат"/>
</form>
<form action="/ControllerServlet" method="POST">
    <input type="hidden" name="command" value="MAIL">
    <table>
        <tr>
            <td>Кому:</td>
            <td><input type="text" name="to"/></td>
        </tr>
        <tr>
            <td>Тема:</td>
            <td><input type="text" name="subject"/></td>
        </tr>
    </table>
    <br><br/>
    <textarea type="text" name="body" rows="5" cols="45">Введите сообщение</textarea>
    <br><br/>
    <input type="submit" value="Отправить"/>
</form>
<form action="/FileUploadServlet" method="POST" enctype="multipart/form-data">
    Выберите файл:
    <br><br/>
    <input type="file" name="fileName">
    <br><br/>
    <input type="submit" value="Загрузить">
</form>
</body>
</html>
