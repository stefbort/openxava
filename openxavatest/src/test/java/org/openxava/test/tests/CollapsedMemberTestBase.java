package org.openxava.test.tests;

import java.util.*;
import java.util.prefs.*;

import org.openxava.tests.*;
import org.openxava.util.*;

import org.htmlunit.html.*;

/**
 * This test case verifies the @Collapsed annotation applied to 
 * a view member.
 * 
 * The @Collapsed annotation has effect only on module initialization. 
 * After that, the user preferences are stored to preserve the frame status. 
 * 
 * The test case assumes it will be run just once within a test 
 * suite execution. Once the module has been initialized, the test (step 1)
 * will fail. Run it repeatedly must be avoided.
 * 
 * @author paco valsera
 *
 */
public abstract class CollapsedMemberTestBase extends ModuleTestBase {
	
	private final static long SAFE_SYNC_TIMEOUT= 1000;
	
	private String collapsedMember;
	private String module;
	private String initFrameUserPref;
	
	public CollapsedMemberTestBase(String testName, String module, String collapsedMember) {
		super(testName, module);
		this.module= module;
		this.collapsedMember= collapsedMember;
	}
	
	@Override
	protected void setUp() throws Exception {
		Users.setCurrent("admin"); // Must match with autologinUser of naviox.properties
		
		//Resets the user preferences before loading the module for the first time.
		//That's why this must be done before calling setUp() method.
		//@Collapsed is only applicable on module initialization.
		Preferences prefs = Users.getCurrentPreferences().node(getFramePrefNode());
		initFrameUserPref= prefs.get(getFramePrefKey(), null);
		prefs.remove(getFramePrefKey());
		prefs.flush();

		
		super.setUp(); //loads the module
	}
	
	protected void resetModule() throws Exception { 
		super.resetModule();		
		getWebClient().getOptions().setCssEnabled(true);
		reload();
	}
	
	public void testCollapsedMember() throws Exception { 
		//step 1: ensures that a @Collapsed reference view is hidden 
		//on module initialization for the first time.
		waitForSafeSynchronization();
		assertFrameHtmlStatus(true); // This can fail if you execute this twice
		assertFramePrefStatus(true); // after restart Tomcat. Just restart Tomcat to test this.
		
		//step 2: checks that after clicking on expansion icon the frame
		//changes to opened.
		HtmlElement expansionIcon= HtmlUnitUtils.getAnchor(getHtmlPage(),  
				"javascript:openxava.showFrame('" + getFrameId() + "')");
		expansionIcon.click();		
		waitForSafeSynchronization();
		assertFrameHtmlStatus(false);
		assertFramePrefStatus(false);
		
		//step 3: reloads the module just to check if the user preferences
		//are kept.
		resetModule();
		waitForSafeSynchronization();		
		assertFrameHtmlStatus(false);
		assertFramePrefStatus(false);
	}
	
	@Override
	protected void tearDown() throws Exception {
		//User preferences restoration
		if (initFrameUserPref!=null) {
			Preferences prefs = Users.getCurrentPreferences().node(getFramePrefNode());				
			prefs.putBoolean(getFramePrefKey(), Boolean.valueOf(initFrameUserPref));
			prefs.flush();
		}
		
		super.tearDown();
	}
	
	////////////////////////////////////////////////////////////////////////
	
	private void assertFrameHtmlStatus(boolean closed) throws Exception{		
		String frameId= getFrameId();
		
		HtmlElement hiddenFrame= getHtmlPage().getHtmlElementById(frameId + "hide"); 
		
		HtmlElement shownFrame= getHtmlPage().getHtmlElementById(frameId + "show"); 
	
		//according to frameActions.jsp
		assertTrue(closed? 
			!hiddenFrame.isDisplayed():
			!shownFrame.isDisplayed());
	}
	
	private void assertFramePrefStatus(boolean closed) throws Exception {
		Preferences prefs = Users.getCurrentPreferences().node(getFramePrefNode());
		prefs.sync();
		String[] prefsKeys= prefs.keys();			
		if (Arrays.asList(prefsKeys).contains(getFramePrefKey())) {
			boolean framePref= prefs.getBoolean(getFramePrefKey(), false);
			assertEquals(framePref, closed);				
		} else {
			assertTrue(false); //pref is not set
		}
	}
	
	private void waitForSafeSynchronization() {
		try {
			Thread.sleep(SAFE_SYNC_TIMEOUT);
		} catch (InterruptedException ie) {}
	}
	
	private String getFrameId() {
		return "ox_" + getXavaJUnitProperty("application") + 
				"_" + module + "__frame_" + collapsedMember;
	}
	
	private String getFramePrefNode() {
		return "view." + module + ".."; 
	}
	
	private String getFramePrefKey() {
		return "frameClosed." + getFrameId();
	}	
}
