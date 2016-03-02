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

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import muni.fi.dp.jz.jobservice.JobService;

/**
 *
 * @author jzelezny
 */
@Stateless
@LocalBean
@ApplicationPath("/jobs")
//TODO - rename
public class jobNameSessionBean extends Application{

    @EJB
    private JobService jobService;
//    private BatchExecutionBean batchExecutor;
        
    public List<String> getJobNames() {
		List<String> jobNameList = new ArrayList<>(jobService.getJobNames());
		return jobNameList;
	}
    
    public String getDummys() {        
        return "Dummmmyyy";
    }
}
