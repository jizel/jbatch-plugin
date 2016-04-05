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

import javax.annotation.Resource;
import javax.batch.runtime.JobInstance;
import javax.ejb.EJB;
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
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class);
        jar.addPackage("muni.fi.dp.jz.batchapi");
        jar.addClass(BatchExecutionBean.class);
        jar.addClass(BatchExecutionException.class);
        return jar;
    }

    /**
     * Test of submitJob method, of class BatchExecutionBean.
     */
    @Test
    public void testSubmitJob() throws Exception {
        System.out.println("submitJob");
        long expected = 1;
        String jobName = "example-batch-job";
       Assert.assertEquals(expected, batchExecutor.submitJob(jobName));        
        Assert.assertEquals("true","true");
    }
//
//    /**
//     * Test of getJobExecution method, of class BatchExecutionBean.
//     */
//    @Test
//    public void testGetJobExecution() throws Exception {
//        System.out.println("getJobExecution");
//        
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of restartJob method, of class BatchExecutionBean.
//     */
//    @Test
//    public void testRestartJob() throws Exception {
//        System.out.println("restartJob");
//        
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getJobNames method, of class BatchExecutionBean.
//     */
//    @Test
//    public void testGetJobNames() throws Exception {
//        System.out.println("getJobNames");
//        
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getJobInstanceCount method, of class BatchExecutionBean.
//     */
//    @Test
//    public void testGetJobInstanceCount() throws Exception {
//        System.out.println("getJobInstanceCount");
//        
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRunningExecutions method, of class BatchExecutionBean.
//     */
//    @Test
//    public void testGetRunningExecutions() throws Exception {
//        System.out.println("getRunningExecutions");
//       
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getJobInstances method, of class BatchExecutionBean.
//     */
//    @Test
//    public void testGetJobInstances_String() throws Exception {
//        System.out.println("getJobInstances");
//        String jobName = "";
//       
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getJobInstances method, of class BatchExecutionBean.
//     */
//    @Test
//    public void testGetJobInstances_3args() throws Exception {
//        System.out.println("getJobInstances");
//        String jobName = "";
//        int start = 0;
//        int count = 0;
//       
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getJobExecutions method, of class BatchExecutionBean.
//     */
//    @Test
//    public void testGetJobExecutions() throws Exception {
//        System.out.println("getJobExecutions");
//        JobInstance instance_2 = null;
//        
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getStepExecutions method, of class BatchExecutionBean.
//     */
//    @Test
//    public void testGetStepExecutions() throws Exception {
//        System.out.println("getStepExecutions");
//        long jobExecutionId = 0L;
//        
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getJobInstance method, of class BatchExecutionBean.
//     */
//    @Test
//    public void testGetJobInstance() throws Exception {
//        System.out.println("getJobInstance");
//        long executionId = 0L;
//       
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
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
//    /**
//     * Test of getParameters method, of class BatchExecutionBean.
//     */
//    @Test
//    public void testGetParameters() throws Exception {
//        System.out.println("getParameters");
//        long l = 0L;
//       
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

}
