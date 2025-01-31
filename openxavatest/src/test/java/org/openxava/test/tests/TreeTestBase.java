package org.openxava.test.tests;

import java.util.*;

import org.htmlunit.ElementNotFoundException;
import org.htmlunit.html.*;
import org.openxava.tests.*;
import org.openxava.util.*;

/**
 * 
 * @author Federico Alcantara
 */

public abstract class TreeTestBase extends ModuleTestBase {
	
	public TreeTestBase(String testName, String application, String module) {
		super(testName, application, module);
	}
	
	public TreeTestBase(String testName, String module) {
		super(testName, module);		
	}

	protected String getValueInTreeView(String collection, int row) {
		DomElement element = getTreeViewElementInRow(collection, row);
		// String value = element.getElementsByTagName("span").item(0) // item(0) does not compile in Eclipse with Java 11 and Maven
		String value = element.getElementsByTagName("span").get(0)
				.getTextContent().toString();
		return value.replace((char) 160, (char) 32); 
	}
		
	protected int getTreeViewRowCount(String collection) throws Exception {
		// HtmlDivision div = (HtmlDivision) getForm().getPage().getElementById("openxavaInput_" + collection); // It does not compile in Eclipse with Java 11 and Maven
		HtmlDivision div = getHtmlPage().getHtmlElementById("openxavaInput_" + collection); 
		// return div.getElementsByTagName("input").getLength(); // getLength() does not compile in Eclipse with Java 11 and Maven
		return div.getElementsByTagName("input").size(); 
	}
	
	
	protected int getTreeViewTabRow(String collection, int row) {
		HtmlInput element = getTreeViewXavaElement(collection, row);
		String tabRow = element.getValue();
		tabRow = tabRow.substring(tabRow.indexOf(":") + 1);
		return Integer.parseInt(tabRow);
	}
	
	protected void executeOnTreeViewItem(String collection, String action, int row) throws Exception {
		int tabRow = getTreeViewTabRow(collection, row);
		execute(action, "row=" + tabRow + ",viewObject=xava_view_" + collection);
	}
	
	protected void checkRowTreeView(String collection, int row) throws Exception {
		HtmlInput input = getTreeViewXavaElement(collection, row);
		input.setChecked(true);
	}
	
	/** 
	 * Find the tree view element in a treeview collection
	 * @param collection name of the collection represented as tree
	 * @param row row to be found
	 * @return DomElement containing the tree item details
	 * @throws ElementNotFoundException
	 */
	protected DomElement getTreeViewElementInRow(String collection, int row) throws ElementNotFoundException {
		HtmlDivision div = (HtmlDivision) getHtmlPage().getElementById("tree_" + collection);
		Iterator<HtmlElement> it = div.getHtmlElementDescendants().iterator(); 
		int count = 0;
		while (it.hasNext()) {
			HtmlElement element = it.next();
			if (element.getId().startsWith("ygtvcontentel")) {
				if (count++ == row) {
					return element;
				}
			}
		}
		throw new ElementNotFoundException("tree_" + collection, "row", Integer.toString(row));
	}

	/**
	 * Finds the mapping of the treeview item to the IXTableModel 
	 * @param collection name of the collection
	 * @param row row to be look up for
	 * @return the HtmlInput element containg the details of the mapping
	 * @throws ElementNotFoundException
	 */
	protected HtmlInput getTreeViewXavaElement(String collection, int row) throws ElementNotFoundException {
		// HtmlDivision div = (HtmlDivision) getForm().getPage().getElementById("openxavaInput_" + collection); // It does not compile in Eclipse with Java 11 and Maven
		HtmlDivision div = (HtmlDivision) getHtmlPage().getElementById("openxavaInput_" + collection); 
		Iterator<HtmlElement> elements = div.getHtmlElementDescendants().iterator(); 		
		int count = 0;
		while (elements.hasNext()) {
			HtmlElement element = elements.next();
			if (element instanceof HtmlInput) {
				if (count++ == row ) {
					return (HtmlInput) element;
				}
			}
		}
		throw new ElementNotFoundException("tree_" + collection, "row", Integer.toString(row));
	}

	protected void assertTreeViewRowCount(String collection, int expectedCount) throws Exception {
		assertEquals(XavaResources.getString("collection_row_count", collection), expectedCount, getTreeViewRowCount(collection));
	}
	
	protected void assertValueInTreeView(String collection, int row, String value) throws Exception {
		String rowValue = getValueInTreeView(collection, row);
		assertEquals(XavaResources.getString("unexpected_value_in_collection", new Integer(0), new Integer(row), collection), value, rowValue);
	}
	protected void assertValueInTreeViewIgnoreCase(String collection, int row, String value) throws Exception {
		String rowValue = getValueInTreeView(collection, row);
		assertEquals(XavaResources.getString("unexpected_value_in_collection", new Integer(0), new Integer(row), collection), value.toUpperCase(), rowValue.toUpperCase());
	}

	@Override
	protected void resetModule() throws Exception {
		super.resetModule();
	}

}
