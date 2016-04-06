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
package muni.fi.dp.jz.jbatch.batchapi;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
import javax.ejb.EJB;
import javax.transaction.Transactional;
import muni.fi.dp.jz.jbatch.exception.BatchExecutionException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;

/**
 *
 * @author Zorz
 */
@RunWith(Arquillian.class)
public class BatchExecutionBeanTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @EJB
    private BatchExecutionBean batchExecutor;
    private static final Logger LOG = Logger.getLogger(BatchExecutionBeanTest.class.getName());

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class);
        war.addPackage("muni.fi.dp.jz.batchapi");
        war.addClass(BatchExecutionBean.class);
        war.addClass(BatchExecutionException.class);
        war.addAsResource("META-INF/batch.xml");
        war.addAsResource("META-INF/batch-jobs/example-batch-job.xml");
        return war;
    }

    /**
     * Test of submitJob method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(1)
    public void testSubmitJob() throws Exception {        
        System.out.println("submitJob");
        long expected = 1;
        String jobName = "example-batch-job";

        Assert.assertEquals(expected, batchExecutor.submitJob(jobName));
    }

    /**
     * Test of getJobExecution method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(2)
    public void testGetJobExecution() throws Exception {
        System.out.println("getJobExecution");
        long execId = 1;
        Assert.assertEquals("org.jberet.runtime.JobExecutionImpl@1", batchExecutor.getJobExecution(execId).toString());
    }

    /**
     * Test of restartJob method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(3)
    public void testRestartJob() throws Exception {
        System.out.println("restartJob");
        long newJobId = batchExecutor.restartJob(1);
         Assert.assertEquals(2, newJobId);
    }

    /**
     * Test of getJobNames method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(4)
    public void testGetJobNames() throws Exception {
        System.out.println("getJobNames");
        Set<String> expectedJobNames = new HashSet<>();
        expectedJobNames.add("simple-batchlet-job");
         Assert.assertEquals(expectedJobNames, batchExecutor.getJobNames());
    }

    /**
     * Test of getJobInstanceCount method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(4)
    public void testGetJobInstanceCount() throws Exception {
        System.out.println("getJobInstanceCount");
        Assert.assertEquals(1, batchExecutor.getJobInstanceCount("simple-batchlet-job"));
    }

    /**
     * Test of getRunningExecutions method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(4)
    public void testGetRunningExecutions() throws Exception {
        System.out.println("getRunningExecutions");
        List<Long> expectedExecutions = new ArrayList<>();
//        Get some batch with endless running state!
//        expectedExecutions.add((long)1);
//        expectedExecutions.add((long)2);
        
        Assert.assertEquals(expectedExecutions, batchExecutor.getRunningExecutions("simple-batchlet-job"));       
    }

    /**
     * Test of getJobInstances method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(4)
    public void testGetJobInstances_String() throws Exception {
        System.out.println("getJobInstances");
        String jobName = "simple-batchlet-job";
        String expected = "org.jberet.runtime.JobInstanceImpl@1";
        Assert.assertEquals(expected, batchExecutor.getJobInstances(jobName).get(0).toString());
       
    }

    /**
     * Test of getJobInstances method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(4)
    public void testGetJobInstances_3args() throws Exception {
        System.out.println("getJobInstances");
        String jobName = "simple-batchlet-job";
        int start = 0;
        int count = 2;
        String expected = "org.jberet.runtime.JobInstanceImpl@1";
       Assert.assertEquals(expected, batchExecutor.getJobInstances(jobName,start,count).get(0).toString());
    }

    /**
     * Test of getJobExecutions method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(4)
    @Transactional
    public void testGetJobExecutions() throws Exception {
        System.out.println("getJobExecutions");
        JobInstance tstInstance = batchExecutor.getJobInstances("simple-batchlet-job").get(0);                
        String expected = "[org.jberet.runtime.JobExecutionImpl@1, org.jberet.runtime.JobExecutionImpl@2]";
        
        Assert.assertEquals(expected, batchExecutor.getJobExecutions(tstInstance).toString());
    }

    /**
     * Test of getStepExecutions method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(4)
    public void testGetStepExecutions() throws Exception {
        System.out.println("getStepExecutions");
        long jobExecutionId = 1L;
        String expected = "[org.jberet.runtime.StepExecutionImpl@f9aff64a]";
        
        Assert.assertEquals(expected, batchExecutor.getStepExecutions(jobExecutionId).toString());        
    }

    /**
     * Test of getJobInstance method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(4)
    public void testGetJobInstance() throws Exception {
        System.out.println("getJobInstance");
        long executionId = 1L;
        String expected = "org.jberet.runtime.JobInstanceImpl@1";
       
        Assert.assertEquals(expected, (batchExecutor.getJobInstance(executionId)).toString()); 
    }
    
    @Test
    @InSequence(4)
    public void testGetParameters() throws Exception {
        System.out.println("getParameters");
        long execId = 1L;
        String expected = "{jberet.jobXmlName=example-batch-job}";
       
        Assert.assertEquals(expected, (batchExecutor.getParameters(execId)).toString()); 
    }
//
//    /**
//     * Test of stop method, of class BatchExecutionBean.
//     */
//    @Test
//    public void testStop() throws Exception {
//        System.out.println("stop");
//        long executionId = 0L;
//       
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of abandon method, of class BatchExecutionBean.
//     */
//    @Test
//    public void testAbandon() throws Exception {
//        System.out.println("abandon");
//        long executionId = 0L;
//       
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
    /**
     * Test of getParameters method, of class BatchExecutionBean.
     */
    

}
