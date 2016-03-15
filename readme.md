Hawtio plugin for managing JSR352 batch jobs. Optimized for Wildfly 10.

Requirements: 
 - Running instance of hawtio-web on server (https://github.com/hawtio/hawtio/tree/master/hawtio-web)
 - Disabled hawtio security - set hawtio.authenticationEnabled to false in server's standalone.xml(will be changed in the future).

Plugin is discovered automatically by hawtio using Jolokia. Rest api with json message format is implemented.
