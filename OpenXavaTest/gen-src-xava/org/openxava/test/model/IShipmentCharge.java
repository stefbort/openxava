

// File generated by OpenXava: Thu Jul 23 20:22:33 CEST 2020
// Archivo generado por OpenXava: Thu Jul 23 20:22:33 CEST 2020

// WARNING: NO EDIT
// OJO: NO EDITAR
// Component: ShipmentCharge		Java interface for entity/Interfaz java para Entidad

package org.openxava.test.model;

import java.math.*;
import java.rmi.RemoteException;


public interface IShipmentCharge  extends org.openxava.model.IModel {	

	// Properties/Propiedades 	
	public static final String PROPERTY_mode = "mode"; 
	int getMode() throws RemoteException;
	void setMode(int mode) throws RemoteException; 	
	public static final String PROPERTY_amount = "amount"; 
	java.math.BigDecimal getAmount() throws RemoteException;
	void setAmount(java.math.BigDecimal amount) throws RemoteException; 	
	public static final String PROPERTY_slow = "slow"; 	
	boolean isSlow() throws RemoteException; 	
	public static final String PROPERTY_oid = "oid"; 	
	String getOid() throws RemoteException;		

	// References/Referencias 

	// Shipment : Reference/Referencia
	
	org.openxava.test.model.IShipment getShipment() throws RemoteException;
	void setShipment(org.openxava.test.model.IShipment newShipment) throws RemoteException;

	// Methods 


}