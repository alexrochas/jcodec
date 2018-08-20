import br.com.alex.cielo.qrcode.codec.HCodec
import br.com.alex.cielo.qrcode.codec.LLCodec
import spock.lang.Specification

class QRCodeDecoderSpecification extends Specification {

    def "Should decode string with size two without padding"() {
        given:
            def encodedString = """0212"""
            def codec = new HCodec([new LLCodec("first", "String with size 2")])
        when:
            def decode = codec.decode(encodedString)
        then:
            def fields = decode.left
            def remaining = decode.right
            fields["first"] == "12"
            remaining.isEmpty()
    }

    def "Should decode string with size two with padding two"() {
        given:
            def encodedString = """010212"""
            def codec = new HCodec([new LLCodec("01", "String with size 2", 2)])
        when:
            def decode = codec.decode(encodedString)
        then:
            def fields = decode.left
            def remaining = decode.right
            fields["01"] == "12"
            remaining.isEmpty()
    }

    def "Should decode string with size two without padding and with remaining"() {
        given:
            def encodedString = """0212Remaining"""
            def codec = new HCodec([new LLCodec("should be twelve", "String with size 2")])
        when:
            def decode = codec.decode(encodedString)
        then:
            def fields = decode.left
            def remaining = decode.right
            fields["should be twelve"] == "12"
            remaining == "Remaining"
    }

    def "Should throw exception when string is smaller than field size"() {
        given:
        def encodedString = """20smaller_than_ten"""
            def codec = new HCodec([new LLCodec("burning", "Large field with 20 chars")])
        when:
            def decode = codec.decode(encodedString)
        then:
            thrown IllegalArgumentException
    }

    def "Should decode string with two size two fields with padding two"() {
        given:
            def encodedString = """01021200011"""
            def codec = new HCodec([
                    new LLCodec("first", "String with size 2", 2),
                    new LLCodec("second", "String with size 2", 2)
            ])
        when:
            def decode = codec.decode(encodedString)
        then:
            def fields = decode.left
            def remaining = decode.right
            fields["first"] == "12"
            fields["second"] == "1"
            remaining.isEmpty()
    }

}

