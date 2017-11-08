package org.monsterzlab.jangson.jsonmatcher

import spock.lang.Specification


class MatcherSpec extends Specification {
    def "match json"(){
        when:
        def expected = """
        /*
        {
          "message": "Validation Failed",
        }
        */
        {
          "message": "Validation Failed"
        //  "errors": [
        //    {
        //      "resource": "Issue",
        //      "field": "title",
        //      "code": "missing_field"
        //    }
        //  ]
        }
        """

        def actual = """
        {
          "message": "Validation Failed",
          "errors": [
            {
              "resource": "Issue",
              "field": "title",
              "code": "missing_field"
            }
          ]
        }
        """

        then:
        JsonMatcher.match(expected, actual) == true
    }
}