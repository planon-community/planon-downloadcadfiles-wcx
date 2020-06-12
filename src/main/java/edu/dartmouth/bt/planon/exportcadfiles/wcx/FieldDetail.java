package edu.dartmouth.bt.planon.exportcadfiles.wcx;

import java.io.*;

/**
 * FieldDetail
 */
public class FieldDetail implements Serializable
{
  //~ Instance Variables ---------------------------------------------------------------------------
  private static final long serialVersionUID = 1L;
  private String fieldName;
  private String fieldValue;

  //~ Constructors ---------------------------------------------------------------------------------

  /**
   * Creates a new Name object.
   *
   * @param aFieldName  DOCUMENT ME!
   * @param aFieldValue DOCUMENT ME!
   */
  public FieldDetail(final String aFieldName, final String aFieldValue) {
    this.fieldName = aFieldName;
    this.fieldValue = aFieldValue;
  }

  //~ Methods --------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getFieldName() {
    return this.fieldName;
  }


  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getFieldValue() {
    return this.fieldValue;
  }


  /**
   * DOCUMENT ME!
   *
   * @param aFieldName firstName DOCUMENT ME!
   */
  public void setFieldName(final String aFieldName) {
    this.fieldName = aFieldName;
  }


  /**
   * DOCUMENT ME!
   *
   * @param aFieldValue lastName DOCUMENT ME!
   */
  public void setFieldValue(final String aFieldValue) {
    this.fieldValue = aFieldValue;
  }
}
