package grailsejercicio3

import grails.validation.ValidationException
import groovy.json.JsonSlurper

import static org.springframework.http.HttpStatus.*

class AgencyController {

    AgencyService agencyService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def form(){
        String idSitio = params.site_id
        println("ID SITIO: " + idSitio)
        String metodoPago=params.payment_method_id
        String localidad = params.longitud + "," + params.latitud + "," + params.radio
        println("LOCALIDAD: " + localidad)
        String limite= params.limit
        println("LIMITE: " + limite)
        String offset = params.offset
        println("OFFSET: " + offset)
        String urlAEnviar = "http://localhost:4567/agencias?idSitio=" + idSitio +
                "&idMetodoPago=" + metodoPago + "&near_to=" + localidad
        if(limite!=null & offset!=null){
            urlAEnviar=urlAEnviar + "&limit=" + limite + "&offset=" + offset
        }
        if (limite==null & offset!=null){
            urlAEnviar=urlAEnviar + "&offset=" + offset
        }
        if (limite!=null & offset==null){
            urlAEnviar=urlAEnviar + "&limit=" + limite
        }
        println(urlAEnviar)
        def url = new URL(urlAEnviar)
        def connection = (HttpURLConnection) url.openConnection()
        connection.setRequestMethod("GET")
        connection.setRequestProperty("Accept", "application/json")
        connection.setRequestProperty("User-Agent", "Mozilla/5.0")
        JsonSlurper json = new JsonSlurper()
        def result = json.parse(connection.getInputStream())
        println(result.data)
        [result: result.data]
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond agencyService.list(params), model:[agencyCount: agencyService.count()]
    }

    def show(Long id) {
        respond agencyService.get(id)
    }

    def create() {
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
            respond agency.errors, view:'create'
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
            respond agency.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'agency.label', default: 'Agency'), agency.id])
                redirect agency
            }
            '*'{ respond agency, [status: OK] }
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
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'agency.label', default: 'Agency'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
