

// File generated by OpenXava: Thu Jul 23 20:22:32 CEST 2020
// Archivo generado por OpenXava: Thu Jul 23 20:22:32 CEST 2020

// WARNING: NO EDIT
// OJO: NO EDITAR
// Component: ConversionFactor		Java interface for entity/Interfaz java para Entidad

package org.openxava.test.model;

import java.math.*;
import java.rmi.RemoteException;


public interface IConversionFactor  extends org.openxava.model.IModel {	

	// Properties/Propiedades 	
	public static final String PROPERTY_reverseFactor = "reverseFactor"; 
	java.math.BigDecimal getReverseFactor() throws RemoteException;
	void setReverseFactor(java.math.BigDecimal reverseFactor) throws RemoteException; 	
	public static final String PROPERTY_toUnit = "toUnit"; 
	String getToUnit() throws RemoteException;
	void setToUnit(String toUnit) throws RemoteException; 	
	public static final String PROPERTY_id = "id"; 	
	Long getId() throws RemoteException; 	
	public static final String PROPERTY_factor = "factor"; 
	java.math.BigDecimal getFactor() throws RemoteException;
	void setFactor(java.math.BigDecimal factor) throws RemoteException; 	
	public static final String PROPERTY_fromUnit = "fromUnit"; 
	String getFromUnit() throws RemoteException;
	void setFromUnit(String fromUnit) throws RemoteException;		

	// References/Referencias

	// Methods 


}