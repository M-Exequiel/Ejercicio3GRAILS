package grailsejercicio3

import grails.validation.ValidationException
import groovy.json.JsonSlurper
import org.hibernate.NonUniqueObjectException

import static org.springframework.http.HttpStatus.*

class AgencyController {

    AgencyService agencyService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", addAgencia: "GET"]

    static HashMap<String, Agency> agenciesMap = new HashMap<String, Agency>()

    def form() {
        String idSitio = params.site_id
        println("ID SITIO: " + idSitio)
        String metodoPago = params.payment_method_id
        String localidad = params.longitud + "," + params.latitud + "," + params.radio
        println("LOCALIDAD: " + localidad)
        String limite = params.limit
        println("LIMITE: " + limite)
        String offset = params.offset
        println("OFFSET: " + offset)
        String urlAEnviar = "http://localhost:4567/agencias?idSitio=" + idSitio +
                "&idMetodoPago=" + metodoPago + "&near_to=" + localidad
        if (limite != null & offset != null) {
            urlAEnviar = urlAEnviar + "&limit=" + limite + "&offset=" + offset
        }
        if (limite == null & offset != null) {
            urlAEnviar = urlAEnviar + "&offset=" + offset
        }
        if (limite != null & offset == null) {
            urlAEnviar = urlAEnviar + "&limit=" + limite
        }
        println(urlAEnviar)
        def url = new URL(urlAEnviar)
        def connection = (HttpURLConnection) url.openConnection()
        connection.setRequestMethod("GET")
        connection.setRequestProperty("Accept", "application/json")
        connection.setRequestProperty("User-Agent", "Mozilla/5.0")
        JsonSlurper json = new JsonSlurper()
        def result = json.parse(connection.getInputStream())
        println("Datos traidos: " + result.data)
        result.data.each {
            Address address = new Address()
            address.address_line = it.address.address_line
            println("Direccion del result: " + it.address.address_line)
            address.city = it.address.city
            address.country = it.address.country
            address.location = it.address.location
            address.other_info = it.address.other_info
            address.state = it.address.state
            address.zip_code = it.address.zip_code

            Agency agencia = new Agency()
            agencia.agency_code = it.agency_code
            println("Codigo de agencia: " + it.agency_code)
            agencia.address = address
            agencia.correspondent_id = it.correspondent_id
            agencia.description = it.description
            agencia.disabled = it.disabled
            agencia.distance = it.distance
            agencia.idAgency = it.id
            agencia.payment_method_id = it.payment_method_id
            agencia.phone = it.phone
            agencia.site_id = it.site_id
            agencia.terminal = it.terminal

            agenciesMap.put(agencia.agency_code, agencia)
        }
        [result: result.data]
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond agencyService.list(params), model: [agencyCount: agencyService.count()]
    }

    def show(Long id) {
        respond agencyService.get(id)
    }

    def create() {
        println("create")
        respond new Agency(params)
    }

    def save(Agency agency) {
        if (agency == null) {
            notFound()
            return
        }

        try {
            agencyService.save(agency)
        } catch (ValidationException e) {
            respond agency.errors, view: 'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'agency.label', default: 'Agency'), agency.id])
                redirect agency
            }
            '*' { respond agency, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond agencyService.get(id)
    }

    def update(Agency agency) {
        if (agency == null) {
            notFound()
            return
        }

        try {
            agencyService.save(agency)
        } catch (ValidationException e) {
            respond agency.errors, view: 'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'agency.label', default: 'Agency'), agency.id])
                redirect agency
            }
            '*' { respond agency, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        agencyService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'agency.label', default: 'Agency'), id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'agency.label', default: 'Agency'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def addAgencia() {
        Agency agenciaAGuardar = agenciesMap.get(params.codigoAgencia)
        String resultado = ""
        println("Code de agencia a guardar:" + agenciaAGuardar.agency_code)
        if (Agency.findByAgency_code(params.codigoAgencia) == null) {
            agenciaAGuardar.save(flush: true, failOnError: true)
            println("se guardo")
            render {
                div(id: "resultadoGuardado", "SE GUARDO CON EXITO.")
            }
            resultado = "SE GUARDO CON EXITO."
        } else {
            render {
                div(id: "resultadoGuardado", "YA EXISTE ESA AGENCIA EN LA BD.")
            }
            println("Ya existe la agencia")
        }
    }

    def deleteAgencia() {
        println(params.codigoAgencia)
        Agency agenciaABorrar = Agency.findByAgency_code(params.codigoAgencia)
        println("Id de agencia a borrar: " + agenciaABorrar)
        if (agenciaABorrar == null) {
            render {
                div(id: "resultadoBorrado", "NO EXISTE ESA AGENCIA EN LA BD.")
            }
            println("Ya existe la agencia")
        } else {
            agenciaABorrar.delete(flush: true)
            println("Se borro")
            render {
                div(id: "resultadoBorrado", "SE BORRO CON EXITO.")
            }
        }
    }

    def listAgencias() {
        println(Agency.getAll().class)
        String resultado = ""
        List<Agency> listadoAgencias = Agency.getAll()
        listadoAgencias.each {
            resultado = it.getDescription()
            render {
                li(id: "listAgencias", resultado)
            }
        }

    }
}
