# JBatch-plugin
### External Hawtio plugin for managing JSR352 batch jobs. Optimized for Wildfly 10.

**Requirements:**
 - Running instance of hawtio on a Wildfly 10 server (https://github.com/hawtio/hawtio/tree/master/hawtio-wildfly)
 - Maven

 ### Build
 - Build project with *mvn clean install*
 - Wildfly 10 instance for arquillian unit tests is automatically downloaded into *./target* directory (takes about 130 MB of disk space)
 - If everything ends correctly, deploy the created war on your server together with the hawtio instance
 - Java 1.8 is supported


Plugin is discovered automatically by hawtio using Jolokia. REST api with json message format is implemented.
