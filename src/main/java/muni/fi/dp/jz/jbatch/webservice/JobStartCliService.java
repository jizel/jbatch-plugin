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
package muni.fi.dp.jz.jbatch.webservice;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import muni.fi.dp.jz.jbatch.jobservice.JobService;
import org.apache.log4j.Logger;

/**
 *
 * @author Zorz
 */
@Stateless
@Path("start")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class JobStartCliService {
    
    @EJB
    private JobService jobService;
//    private BatchExecutionBean batchExecutor;
    private static final Logger LOG = Logger.getLogger( JobResource.class.getName() );
    
    @GET
    @Path("tst")
    public Response startJobCli(){
        jobService.startJob("not_used_now");
        return Response.ok("RestReader started?", MediaType.APPLICATION_JSON).build();
        
    }
}
