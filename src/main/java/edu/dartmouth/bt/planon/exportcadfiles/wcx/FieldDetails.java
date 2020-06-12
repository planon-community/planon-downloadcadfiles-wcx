package edu.dartmouth.bt.planon.exportcadfiles.wcx;

import java.io.*;

/**
 * FieldDetails
 */
public class FieldDetails implements Serializable {

  //~ Instance Variables ---------------------------------------------------------------------------
  private static final long serialVersionUID = 1L;
  private final FieldDetail fieldDetail;

  //~ Constructors ---------------------------------------------------------------------------------

  /**
   * Creates a new Contact object.
   *
   * @param aFieldDetail DOCUMENT ME!
   */
  public FieldDetails(final FieldDetail aFieldDetail) {
    this.fieldDetail = aFieldDetail;
  }

  //~ Methods --------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public FieldDetail getFieldDetail() {
    return this.fieldDetail;
  }
}
