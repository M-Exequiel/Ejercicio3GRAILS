package grailsejercicio3

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/agency/form")
        "/save/$code"(controller: "agency", action: "save")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
