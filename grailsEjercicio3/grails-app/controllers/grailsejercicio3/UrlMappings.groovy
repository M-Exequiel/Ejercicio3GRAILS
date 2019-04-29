package grailsejercicio3

import javax.swing.text.View

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/agency/form")
        "/agregar/$codigoAgencia"(controller: "agency", action: "addAgencia")
        "/borrar/$codigoAgencia"(controller: "agency", action: "deleteAgencia")
        "/listar" (controller: "agency", action: "listAgencias")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
