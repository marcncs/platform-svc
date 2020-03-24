/**
 * GetDataSetResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.winsafe.drp.ws;

public class GetDataSetResponse  implements java.io.Serializable {
    private com.winsafe.drp.ws.GetDataSetResponseGetDataSetResult getDataSetResult;

    public GetDataSetResponse() {
    }

    public GetDataSetResponse(
           com.winsafe.drp.ws.GetDataSetResponseGetDataSetResult getDataSetResult) {
           this.getDataSetResult = getDataSetResult;
    }


    /**
     * Gets the getDataSetResult value for this GetDataSetResponse.
     * 
     * @return getDataSetResult
     */
    public com.winsafe.drp.ws.GetDataSetResponseGetDataSetResult getGetDataSetResult() {
        return getDataSetResult;
    }


    /**
     * Sets the getDataSetResult value for this GetDataSetResponse.
     * 
     * @param getDataSetResult
     */
    public void setGetDataSetResult(com.winsafe.drp.ws.GetDataSetResponseGetDataSetResult getDataSetResult) {
        this.getDataSetResult = getDataSetResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetDataSetResponse)) return false;
        GetDataSetResponse other = (GetDataSetResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getDataSetResult==null && other.getGetDataSetResult()==null) || 
             (this.getDataSetResult!=null &&
              this.getDataSetResult.equals(other.getGetDataSetResult())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getGetDataSetResult() != null) {
            _hashCode += getGetDataSetResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetDataSetResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetDataSetResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getDataSetResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetDataSetResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">>GetDataSetResponse>GetDataSetResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
