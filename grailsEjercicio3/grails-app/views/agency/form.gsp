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
    .divForm {
        border-radius: 5px;
        background-color: #f2f2f2;
        padding: 20px;
    }

    .columnDiv {
        flex: 50%;
        text-align: center;
    }

    .rowDiv {
        display: flex;
    }

    h1 {
        display: block;
        font-size: 2em;
        margin-top: 0.67em;
        margin-bottom: 0.67em;
        font-weight: bold;
    }

    h3 {
        text-align: left;
    }

    .like {
        background-color: #4CAF50;
        border: none;
        color: white;
        cursor: pointer;
        font-size: 12px;
    }

    .unlike {
        background-color: #f44336;
        border: none;
        color: white;
        cursor: pointer;
        font-size: 12px;
    }

    .list {
    }
    </style>
    <script>
        function guardarAgencia(codigoAgencia) {
            console.log("Codigo agencia a guardar:" + codigoAgencia)
            var request = new XMLHttpRequest();
            request.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById(codigoAgencia).innerHTML = this.responseText;
                }
            };
            request.open("GET", "/agregar/" + codigoAgencia, true);
            request.send();
        }
    </script>
    <script>
        function borrarAgencia(codigoAgencia) {
            console.log("Codigo agencia a borrar:" + codigoAgencia)
            var request = new XMLHttpRequest();
            request.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById(codigoAgencia).innerHTML = this.responseText;
                }
            };
            request.open("GET", "/borrar/" + codigoAgencia, true);
            request.send();
        }
    </script>
    <script>
        function listarAgencias() {
            var request = new XMLHttpRequest();
            request.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("agenciasRecomendadas").innerHTML = this.responseText;
                }
            };
            request.open("GET", "/listar", true);
            request.send();
        }
    </script>
</head>

<body>
<div class="rowDiv">
    <div class="columnDiv">
        <h1 align="left">Formulario de carga:</h1>

        <div class="divForm" id="verticalDiv">
            <g:form name="formData" controller="agency" action="form">
                <label>Id del Sitio:</label>
                <g:textField name="site_id" value="${params.site_id}"/><br/>
                <label>Longitud:</label>
                <g:textField name="longitud" value="${params.longitud}"/><br/>
                <label>Latitud:</label>
                <g:textField name="latitud" value="${params.latitud}"/><br/>
                <label>Radio:</label>
                <g:textField name="radio" value="${params.radio}"/><br/>
                <label>Metodo de pago:</label>
                <g:textField name="payment_method_id" value="${params.payment_method_id}"/><br/>
                <label>Cantidad de agencias mas cercanas a mostrar (opcional):</label>
                <g:textField name="limit" value="${params.limit}"/><br/>
                <label>Cuantas de las agencias mas cercanas no queres mostrar (opcional):</label>
                <g:textField name="offset" value="${params.offset}"/><br/>
                <g:submitButton name="Buscar" value="BUSCAR"/>
            </g:form>
        </div>

        <div class="divAgencias">
            <h3>AGENCIAS:</h3>
            <ul type="circle">
                <g:each var="c" in="${result}">
                    <li>${c.description}
                        <button class="like" type="button" onclick="guardarAgencia('${c.agency_code}')">LIKE</button>
                        <button class="unlike" type="button"
                                onclick="borrarAgencia('${c.agency_code}');">UNLIKE</button>

                        <p id="${c.agency_code}"></p>
                    </li>
                </g:each>
            </ul>
        </div>
    </div>

    <div class="columnDiv">
        <h1>Listado de agencias recomendadas:</h1>
        <button class="list" type="button" onclick="listarAgencias()">LISTAR AGENCIAS</button>

        <p id="agenciasRecomendadas"></p>
    </div>
</div>
</body>
</html>