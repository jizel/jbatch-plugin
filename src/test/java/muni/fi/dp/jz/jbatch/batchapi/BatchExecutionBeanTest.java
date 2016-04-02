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

import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
import javax.batch.runtime.StepExecution;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Zorz
 */
public class BatchExecutionBeanTest {
    
    public BatchExecutionBeanTest() {
    }
    
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

    /**
     * Test of submitJob method, of class BatchExecutionBean.
     */
    @Test
    public void testSubmitJob() throws Exception {
        System.out.println("submitJob");
        String jobName = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        BatchExecutionBean instance = (BatchExecutionBean)container.getContext().lookup("java:global/classes/BatchExecutionBean");
        long expResult = 0L;
        long result = instance.submitJob(jobName);
        assertEquals(expResult, result);
        container.close();
//         TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getJobExecution method, of class BatchExecutionBean.
     */
    @Test
    public void testGetJobExecution() throws Exception {
        System.out.println("getJobExecution");
        Long executionId = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        BatchExecutionBean instance = (BatchExecutionBean)container.getContext().lookup("java:global/classes/BatchExecutionBean");
        JobExecution expResult = null;
        JobExecution result = instance.getJobExecution(executionId);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of restartJob method, of class BatchExecutionBean.
     */
    @Test
    public void testRestartJob() throws Exception {
        System.out.println("restartJob");
        long executionId = 0L;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        BatchExecutionBean instance = (BatchExecutionBean)container.getContext().lookup("java:global/classes/BatchExecutionBean");
        long expResult = 0L;
        long result = instance.restartJob(executionId);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getJobNames method, of class BatchExecutionBean.
     */
    @Test
    public void testGetJobNames() throws Exception {
        System.out.println("getJobNames");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        BatchExecutionBean instance = (BatchExecutionBean)container.getContext().lookup("java:global/classes/BatchExecutionBean");
        Set<String> expResult = null;
        Set<String> result = instance.getJobNames();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getJobInstanceCount method, of class BatchExecutionBean.
     */
    @Test
    public void testGetJobInstanceCount() throws Exception {
        System.out.println("getJobInstanceCount");
        String jobName = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        BatchExecutionBean instance = (BatchExecutionBean)container.getContext().lookup("java:global/classes/BatchExecutionBean");
        int expResult = 0;
        int result = instance.getJobInstanceCount(jobName);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRunningExecutions method, of class BatchExecutionBean.
     */
    @Test
    public void testGetRunningExecutions() throws Exception {
        System.out.println("getRunningExecutions");
        String jobName = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        BatchExecutionBean instance = (BatchExecutionBean)container.getContext().lookup("java:global/classes/BatchExecutionBean");
        List<Long> expResult = null;
        List<Long> result = instance.getRunningExecutions(jobName);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getJobInstances method, of class BatchExecutionBean.
     */
    @Test
    public void testGetJobInstances_String() throws Exception {
        System.out.println("getJobInstances");
        String jobName = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        BatchExecutionBean instance = (BatchExecutionBean)container.getContext().lookup("java:global/classes/BatchExecutionBean");
        List<JobInstance> expResult = null;
        List<JobInstance> result = instance.getJobInstances(jobName);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getJobInstances method, of class BatchExecutionBean.
     */
    @Test
    public void testGetJobInstances_3args() throws Exception {
        System.out.println("getJobInstances");
        String jobName = "";
        int start = 0;
        int count = 0;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        BatchExecutionBean instance = (BatchExecutionBean)container.getContext().lookup("java:global/classes/BatchExecutionBean");
        List<JobInstance> expResult = null;
        List<JobInstance> result = instance.getJobInstances(jobName, start, count);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getJobExecutions method, of class BatchExecutionBean.
     */
    @Test
    public void testGetJobExecutions() throws Exception {
        System.out.println("getJobExecutions");
        JobInstance instance_2 = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        BatchExecutionBean instance = (BatchExecutionBean)container.getContext().lookup("java:global/classes/BatchExecutionBean");
        List<JobExecution> expResult = null;
        List<JobExecution> result = instance.getJobExecutions(instance_2);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStepExecutions method, of class BatchExecutionBean.
     */
    @Test
    public void testGetStepExecutions() throws Exception {
        System.out.println("getStepExecutions");
        long jobExecutionId = 0L;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        BatchExecutionBean instance = (BatchExecutionBean)container.getContext().lookup("java:global/classes/BatchExecutionBean");
        List<StepExecution> expResult = null;
        List<StepExecution> result = instance.getStepExecutions(jobExecutionId);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getJobInstance method, of class BatchExecutionBean.
     */
    @Test
    public void testGetJobInstance() throws Exception {
        System.out.println("getJobInstance");
        long executionId = 0L;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        BatchExecutionBean instance = (BatchExecutionBean)container.getContext().lookup("java:global/classes/BatchExecutionBean");
        JobInstance expResult = null;
        JobInstance result = instance.getJobInstance(executionId);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stop method, of class BatchExecutionBean.
     */
    @Test
    public void testStop() throws Exception {
        System.out.println("stop");
        long executionId = 0L;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        BatchExecutionBean instance = (BatchExecutionBean)container.getContext().lookup("java:global/classes/BatchExecutionBean");
        instance.stop(executionId);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of abandon method, of class BatchExecutionBean.
     */
    @Test
    public void testAbandon() throws Exception {
        System.out.println("abandon");
        long executionId = 0L;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        BatchExecutionBean instance = (BatchExecutionBean)container.getContext().lookup("java:global/classes/BatchExecutionBean");
        instance.abandon(executionId);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getParameters method, of class BatchExecutionBean.
     */
    @Test
    public void testGetParameters() throws Exception {
        System.out.println("getParameters");
        long l = 0L;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        BatchExecutionBean instance = (BatchExecutionBean)container.getContext().lookup("java:global/classes/BatchExecutionBean");
        Properties expResult = null;
        Properties result = instance.getParameters(l);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
