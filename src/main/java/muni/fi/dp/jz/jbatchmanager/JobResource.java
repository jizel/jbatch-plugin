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
package muni.fi.dp.jz.jbatchmanager;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Zorz
 */

@Stateless
@ApplicationPath("/jobs")
//subclassing Application and using ApplicationPath -> no web.xml conf needed (https://issues.jboss.org/browse/AS7-1674)
public class JobResource extends Application{
    
    @EJB
    private BatchExecutionBean batchExecutor;
    
    @GET
    @Path("/names")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getJobNames() {
		List<String> jobNameList = new ArrayList<>(batchExecutor.getJobNames());
		return jobNameList;
	}
    
    @GET
    @Path("/dummy")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getDummy() {
        List<String> dummyList = new ArrayList<>();
        dummyList.add("Str1");
        dummyList.add("Str2");
        dummyList.add("Str3");
        return dummyList;
    }
}
