/*
 * Copyright 2016 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package muni.fi.dp.jz.webservice;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author jzelezny
 */
@Stateless
@Path("test")
public class testRest {
    
    @EJB private jobNameSessionBean jobNameSB;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    
    public String getNames(){
        return jobNameSB.getDummys();
    }
    
    @GET    
    @Produces(MediaType.APPLICATION_JSON)
    @Path("names")
    public List<String> getRealNames() {
        List<String> jobNames = jobNameSB.getJobNames();
        jobNames.add("WooHoo");
        return jobNames;
}

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
