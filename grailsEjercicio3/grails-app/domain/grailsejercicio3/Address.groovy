package grailsejercicio3

class Address {

    String address_line
    String city
    String country
    String location
    String other_info
    String state
    String zip_code

    static belongsTo = [agency: Agency]

    static constraints = {
        other_info blank: true, nullable: true
        zip_code blank: true, nullable: true
        address_line nullable: false
        city nullable: false
        country nullable: false
        location nullable: false
    }
}
