package grailsejercicio3

class Agency {

    String agency_code
    String correspondent_id
    String description
    String disabled
    String distance
    String idAgency
    String payment_method_id
    String phone
    String site_id
    String terminal
    Address address

    static hasOne = [address: Address]

    static constraints = {
        agency_code nullable: false
        correspondent_id blank: true, nullable: true
        description nullable: false
        disabled nullable: false
        disabled nullable: false
        idAgency nullable: false
        payment_method_id nullable: false
        phone blank: true, nullable: true
        site_id nullable: false
        terminal nullable: false

    }
}
