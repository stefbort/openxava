package org.openxava.test.tests;

import org.openxava.tests.*;


/**
 * @author Javier Paniza
 */

public class Color2Test extends ModuleTestBase {
	
	public Color2Test(String testName) {
		super(testName, "Color2");		
	}
	
	public void testDescriptionsListInMyReport() throws Exception { 
		execute("ExtendedPrint.myReports");
		assertValueInCollection("columns", 4, 0, "Used to"); 
		execute("MyReport.editColumn", "row=4,viewObject=xava_view_columns");
		assertNotExists("comparator");
		assertNotExists("value");
		assertNotExists("dateValue");
		assertNotExists("booleanValue");
		assertNotExists("validValuesValue"); 
		assertExists("descriptionsListValue"); 
		assertExists("order");
		
		setValue("name", "number");
		assertExists("comparator"); 
		assertExists("value");
		assertNotExists("dateValue");
		assertNotExists("booleanValue");
		assertNotExists("validValuesValue"); 
		assertNotExists("descriptionsListValue"); 
		assertExists("order");
		
		setValue("name", "usedTo.name");
		assertNotExists("comparator");		
		assertNotExists("value");
		assertNotExists("dateValue");
		assertNotExists("booleanValue");
		assertNotExists("validValuesValue"); 
		assertExists("descriptionsListValue"); 
		assertExists("order");

		/* tmr
		String [][] validValues = {
			{ "", "" },
			{ "2:_:LAMPPOST", "LAMPPOST" },
			{ "0:_:HOUSE", "HOUSE" },
			{ "3:_:DOOR", "DOOR" },
			{ "1:_:CAR", "CAR" } 
		};
		*/
		// tmr ini
		String [][] validValues = {
			{ "", "" },
			{ "LAMPPOST", "LAMPPOST" },
			{ "HOUSE", "HOUSE" },
			{ "DOOR", "DOOR" },
			{ "CAR", "CAR" } 
		};		
		// tmr fin
		
		assertValidValues("descriptionsListValue", validValues); 
		assertValue("descriptionsListValue", "");
		// tmr setValue("descriptionsListValue", "1:_:CAR");  
		setValue("descriptionsListValue", "CAR"); // tmr En migration
		execute("MyReport.saveColumn");
		assertValueInCollection("columns", 4, 2, "CAR"); 
		
		execute("MyReport.editColumn", "row=4,viewObject=xava_view_columns");
		// tmr assertValue("descriptionsListValue", "1:_:CAR"); 		
		assertValue("descriptionsListValue", "CAR"); // tmr
		closeDialog();
		
		execute("MyReport.generatePdf"); 
		assertPopupPDFLinesCount(5);  
		assertPopupPDFLine(3, "0 ROJO FF0000 RED CAR 3 PLACES CAR");
		
		execute("ExtendedPrint.myReports");
		assertValueInCollection("columns", 4, 0, "Used to"); 
		assertValueInCollection("columns", 4, 2, "CAR");
		execute("MyReport.editColumn", "row=4,viewObject=xava_view_columns");
		// tmr assertValue("descriptionsListValue", "1:_:CAR"); 		
		assertValue("descriptionsListValue", "CAR"); // tmr
		closeDialog();
		
		execute("MyReport.remove", "xava.keyProperty=name");				
	}
	
	public void testFilterDescriptionsList_keyReferenceWithSameNameThatPropertyFather_twoReferencesToSameModelInList() throws Exception {  
		assertLabelInList(6, "Thing of characteristic thing"); 
		assertLabelInList(7, "Thing of another ct");
		assertValueInList(3, 6, "CAR");
		assertValueInList(3, 7, "LAMPPOST");
		assertLabelInList(4, "Used to"); 
		/* tmr
		String [][] validValues = {
			{ "", "" },
			{ "2:_:LAMPPOST", "LAMPPOST" },
			{ "0:_:HOUSE", "HOUSE" },
			{ "3:_:DOOR", "DOOR" },
			{ "1:_:CAR", "CAR" }
		};
		*/
		// tmr ini
		String [][] validValues = {
			{ "", "" },
			{ "LAMPPOST", "LAMPPOST" },
			{ "HOUSE", "HOUSE" },
			{ "DOOR", "DOOR" },
			{ "CAR", "CAR" }
		};		
		// tmr fin
		assertValidValues("conditionValue___3", validValues); 		
		assertValueInList(0, 4, "CAR");
		// tmr setConditionValues(new String[] { "", "", "", "1"} );
		setConditionValues(new String[] { "", "", "", "CAR"} ); // tmr
		// execute("List.filter"); // Not needed because filterOnChange=true
		assertListRowCount(1); 
	}
		
}