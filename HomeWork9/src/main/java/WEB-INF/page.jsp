<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Информация об авторе</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            color: #333;
            text-align: center;
            padding: 50px;
        }
        .card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            padding: 20px;
            display: inline-block;
            width: 300px;
        }
        img {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            object-fit: cover;
        }
    </style>
</head>
<body>

<div class="card">
<h1>Информация об авторе</h1>
    <p><strong>Фамилия:</strong> ${firstname}</p>
    <p><strong>Имя:</strong> ${lastname}</p>
    <p><strong>Отчество:</strong> ${midlename}</p>
    <p><strong>Телефон:</strong> ${phone}</p>
    <p><strong>Хобби:</strong> ${interests}</p>
    <p><strong>Bitbucket url:</strong> ${uri}</p>
</div>
</body>
</html>