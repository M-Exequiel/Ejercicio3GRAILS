<%--
  Created by IntelliJ IDEA.
  User: marclopez
  Date: 2019-04-26
  Time: 10:22
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Ejercicio 3</title>
    <style>
    .divForm{
        border-radius: 5px;
        background-color: #f2f2f2;
        padding: 20px;
    }
    h1 {
        text-align: left;
    }
    h3 {
        text-align: left;
    }
    </style>
    <script>
        function guardarAgencia(agencia) {
            var agency = agencia.valueOf();
            console.log("AGENCIA CLICKEADA: " + agency);
        }
    </script>
    <script>
        function borrarAgencia(agencia) {
            var agency = agencia.valueOf();
            console.log("AGENCIA CLICKEADA: " + agency);
        }
    </script>
</head>

<body>
<h1>Formulario de carga</h1>
<div class="divForm">
    <g:form name="formData" controller="agency" action="form">
    <label>Id del Sitio: </label>
    <g:textField name="site_id" value="${params.site_id}"/><br/>
    <label>Longitud: </label>
    <g:textField name="longitud" value="${params.longitud}" /><br/>
    <label>Latitud: </label>
    <g:textField name="latitud" value="${params.latitud}" /><br/>
    <label>Radio: </label>
    <g:textField name="radio" value="${params.radio}" /><br/>
    <label>Metodo de pago: </label>
    <g:textField name="payment_method_id" value="${params.payment_method_id}"/><br/>
    <label>Cantidad de agencias mas cercanas a mostrar (opcional): </label>
    <g:textField name="limit" value="${params.limit}" /><br/>
    <label>Cuantas de las agencias mas cercanas no queres mostrar (opcional): </label>
    <g:textField name="offset" value="${params.offset}"/><br/>
    <g:submitButton name="Buscar" value="Buscar"/>
</g:form>
</div>
<h3>AGENCIAS: </h3>
<div class="divAgencias">
    <ul type = "circle">
        <g:each var="c" in="${result}">
            <li>${c.description}
                <button id="like" type="button" onclick="guardarAgencia('${c}');">LIKE</button>
                <button id="unlike" type="button" onclick="borrarAgencia('${c}');">UNLIKE</button>
            </li>
        </g:each>
    </ul>
</div>
<div id="agenciaGuardada"></div>
</body>
</html>