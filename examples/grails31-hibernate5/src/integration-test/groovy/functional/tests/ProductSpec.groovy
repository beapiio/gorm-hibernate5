package functional.tests

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import groovy.transform.NotYetImplemented
import spock.lang.Specification

/**
 * Created by graemerocher on 02/01/2017.
 */
@Integration
class ProductSpec extends Specification {

    @Rollback
    void "test that JPA entities can be treated as GORM entities"() {
        when:"A basic entity is persisted and validated"
        Product product = new Product(price: "6000.01", name: "iMac")
        product.save(flush:true, validate:false)

        def query = Product.where {
            name == 'Mac Pro'
        }
        then:"The object was saved"
        !product.errors.hasErrors()
        Product.count() == 2
        query.count() == 0
    }

    @Rollback
    @NotYetImplemented // this fails because Grails is overriding the validator
    void "test that JPA entities can use javax.validation"() {
        when:"A basic entity is persisted and validated"
        Product c = new Product(price: "Bad", name: "iMac")
        c.save(flush:true)

        def query = Product.where {
            name == 'iMac'
        }
        then:"The object was saved"
        c.errors.hasErrors()
        Product.count() == 1
        query.count() == 0
    }
}
