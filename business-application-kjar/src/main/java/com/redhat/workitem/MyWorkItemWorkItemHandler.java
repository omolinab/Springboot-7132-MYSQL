package com.redhat.workitem;

import org.jbpm.process.workitem.core.AbstractLogOrThrowWorkItemHandler;
import org.jbpm.process.workitem.core.util.RequiredParameterValidator;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.lang.Thread;

public class MyWorkItemWorkItemHandler extends AbstractLogOrThrowWorkItemHandler {

	protected static Logger _log = LoggerFactory.getLogger(MyWorkItemWorkItemHandler.class);

    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
      try {
        RequiredParameterValidator.validate(this.getClass(), workItem);
  
        // sample parameters
        String sampleParam = (String) workItem.getParameter("SampleParam");
        String sampleParamTwo = (String) workItem.getParameter("SampleParamTwo");
  
        // complete workitem impl...

        Thread currentThread = Thread.currentThread();
        // Get the name of the current thread
        String threadName = currentThread.getName();

        _log.info("Thread sleep: " + threadName + " - 31 secs");
        Thread.sleep(31000); 
        _log.info("Thread sleep " + threadName + " END.");

      } catch (Exception genEx){
        _log.error("Error occured in " + Thread.currentThread().getStackTrace()[1].getMethodName() +" due to: ", genEx);
        _log.error("Exception "+genEx.getMessage());
      } catch(Throwable cause) {
        _log.error("Error occured in " + Thread.currentThread().getStackTrace()[1].getMethodName() +" due to: ", cause);
        handleException(cause);
      } finally{
        _log.info("MyWorkItemHandler finished.");
        // return results
        String sampleResult = "sample result";
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("SampleResult", sampleResult);
        manager.completeWorkItem(workItem.getId(), results);
      } 
  }
  
  @Override
  public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
      // similar
      Thread currentThread = Thread.currentThread();
      // Get the name of the current thread
      String threadName = currentThread.getName();
      _log.info("MyWorkItemHandler ABORTED in thread: " + threadName);
  }
}
