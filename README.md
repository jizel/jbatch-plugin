# JBatch-plugin
### Hawtio plugin for managing JSR352 batch jobs. Optimized for Wildfly 10.

**Requirements:**
 - Running instance of hawtio-wildfly on a Wildfly 10 server (https://github.com/hawtio/hawtio/tree/master/hawtio-wildfly)

 ### Authentication
 - Create user(s) in ApplicationRealm on your server (i.e. via add-user.sh or add-user.bat)
 - There are 3 supported roles - admin (all rights), supervisor(cannot start new jobs) and user(read only). Assign one of these roles to your user.
 - If you are using a *hawtio-wildfly* instance then all config is **done**. Else (hawtio-web or anything other) you need to copy following to your standalone.xml:
 
 ```
    <system-properties>
        <property name="hawtio.authenticationEnabled" value="true"/>
        <property name="hawtio.realm" value="*"/>
        <property name="hawtio.roles" value="*"/>
        <property name="hawtio.rolePrincipalClasses" value="org.jboss.security.SimplePrincipal"/>
    </system-properties>
```
 
 - Now you can login to hawtio on your server and use the same credentials for REST authentication (TBD: Unify authentication with Hawtio so only one login is needed in one session)
  
 ### Build
 - Build project with *mvn clean install*
 - Wildfly 10 instance for arquillian unit tests is automatically downloaded into *./target* directory (takes about 130 MB of disk space)
 - If everything ends correctly, deploy the created war on your server together with the hawtio instance
 - Java 1.8 is supported


Plugin is discovered automatically by hawtio using Jolokia. REST api with json message format is implemented.
