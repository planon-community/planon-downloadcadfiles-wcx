package edu.dartmouth.bt.planon.exportcadfiles.wcx;

import java.util.Objects;

import nl.planon.enterprise.service.api.IPnESContext;
import nl.planon.enterprise.service.api.IPnESContextCreator;
import nl.planon.enterprise.service.api.IPnESSession;
import nl.planon.enterprise.service.api.factory.PnESContextCreator;


public class Context {
    // Get Context for reference date aware field
    public IPnESContext getContext() {
        IPnESContextCreator contextCreator = PnESContextCreator.getInstance();
        return Context.getContextFromInstance(contextCreator, null);
    }

    static IPnESContext getContextFromInstance(IPnESContextCreator contextCreator, IPnESSession session) {
        return Objects.isNull(session) ? contextCreator.createContext() : contextCreator.createContext(session);
    }
}