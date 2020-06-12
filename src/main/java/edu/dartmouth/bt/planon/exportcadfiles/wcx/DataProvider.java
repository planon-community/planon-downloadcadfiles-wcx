package edu.dartmouth.bt.planon.exportcadfiles.wcx;

import org.apache.wicket.extensions.markup.html.repeater.util.*;
import org.apache.wicket.model.*;

import java.text.DecimalFormat;
import java.util.*;

import nl.planon.zeus.clientextension.cxinterface.*;
import nl.planon.util.pnlogging.PnLogger;


public class DataProvider extends SortableDataProvider{
  // ~ Instance Variables
  // ---------------------------------------------------------------------------
  private static final long serialVersionUID = 1L;
  private final List<FieldDetails> list = new ArrayList<FieldDetails>();
  private static final PnLogger LOG = PnLogger.getLogger(DataProvider.class);
  DecimalFormat formatter = new DecimalFormat("00");
  // ~ Constructors
  // ---------------------------------------------------------------------------------

  /**
   * Creates a new DataProvider object.
   *
   * @param aClientContext
   */
  public DataProvider(ICXContext aClientContext) {
    for (ICXBusinessObject businessObject : aClientContext.getSelectedBOs()) {

      ICXField fieldByPnName = businessObject.getFieldByName("FloorAttributeAutoCADDrawingRef");
      String valueAsString = fieldByPnName.getValueAsString();
      this.list.add(new FieldDetails(new FieldDetail("FloorAttributeAutoCADDrawingRef: ", valueAsString)));
    }
  }

  // ~ Methods
  // --------------------------------------------------------------------------------------

  public List<FieldDetails> getFieldDetailsList(){
    return this.list;
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<FieldDetails> iterator(final long aFirst, final long aCount) {
    List<FieldDetails> newList = new ArrayList<FieldDetails>(this.list);
    return newList.subList((int) aFirst, (int) (aFirst + aCount)).iterator();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModel model(final Object aObject) {
    return new AbstractReadOnlyModel() {
      private static final long serialVersionUID = 1L;

      @Override
      public FieldDetails getObject() {
        return (FieldDetails) aObject;
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long size() {
    return this.list.size();
  }

}
