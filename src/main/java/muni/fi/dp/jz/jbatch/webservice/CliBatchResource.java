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

import muni.fi.dp.jz.jbatch.service.CliService;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Zorz
 */
@Stateless
@Path("cli")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CliBatchResource {

    @EJB
    private CliService cliService;
    private static final Logger LOG = Logger.getLogger(CliBatchResource.class.getName());


    @GET
    @Path("start/{deployment}/{jobName}")
    public Response startJobCli(@PathParam("deployment") String deploymentName, @PathParam("jobName") String jobName) {
        String resp = cliService.startJobCli(deploymentName, jobName);

        return Response.ok(resp, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("start/{deployment}/{jobName}/{properties}")
    public Response startJobCli(@PathParam("deployment") String deploymentName, @PathParam("jobName") String jobName, @PathParam("properties") String properties) {
        Properties props = new Properties();
        JSONObject jsonResp = new JSONObject();
        try {
            props.load(new StringReader(properties));
        } catch (IOException ex) {
            LOG.error("Invalid job properties caused an exception: " + ex.toString());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (ForbiddenException ex) {
            jsonResp.put("outcome", "failed");
            jsonResp.put("description", "User not allowed to start the job: " + ex.toString());
            LOG.error("Unauthorized operation: start job via cli: " + ex.toString());
            return Response.ok(jsonResp, MediaType.APPLICATION_JSON).build();
        }
        String resp = cliService.startJobCli(deploymentName, jobName, props);
        LOG.info("Job " + jobName + " started via cli! Server response returned.\n");

        return Response.ok(resp, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("deployments")
    public Response getDeploymentInfo() {
        String resp = cliService.getDeploymentInfo();

        return Response.ok(resp, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("batchDepl")
    public Response getBatchDeployments() {
        String resp = cliService.getBatchDeploymentsWithJobs();

        return Response.ok(resp, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("deploymentJobs/{deployment}")
    public Response getDeploymentJobs(@PathParam("deployment") String deployment) {
        String deploymentJobs = cliService.getJobsFromDeployment(deployment);
//       TODO: Take just the job part from resp??

        return Response.ok(deploymentJobs, MediaType.APPLICATION_JSON).build();
    }
}
