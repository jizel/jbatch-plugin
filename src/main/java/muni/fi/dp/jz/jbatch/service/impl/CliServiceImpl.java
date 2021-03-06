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
package muni.fi.dp.jz.jbatch.service.impl;

import muni.fi.dp.jz.jbatch.cli.CliBatchManagerBean;
import muni.fi.dp.jz.jbatch.service.CliService;

import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @author Zorz
 */
@Stateless
public class CliServiceImpl implements CliService {

    @EJB
    CliBatchManagerBean cliManager;

    @Override
    public String startJobCli(String deploymentName, String jobName) {
        return cliManager.startJobCli(deploymentName, jobName);
    }

    @Override
    public String getDeploymentInfo() {
        return cliManager.getDeploymentInfo();
    }

    @Override
    public String getBatchDeploymentsWithJobs() {
        return cliManager.getBatchDeploymentsWithJobs();
    }

    @Override
    public String getJobsFromDeployment(String deployment) {
        return cliManager.getJobsFromDeployment(deployment);
    }

    @Override
    public String startJobCli(String deploymentName, String jobName, Properties properties) {
        return cliManager.startJobCli(deploymentName, jobName, properties);
    }

}
